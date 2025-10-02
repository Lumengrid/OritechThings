package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.main.ModDataComponents;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.api.CrossDimensionalDrone;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.SimpleContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rearth.oritech.block.blocks.interaction.DronePortBlock;
import rearth.oritech.block.blocks.processing.MachineCoreBlock;
import rearth.oritech.block.entity.MachineCoreEntity;
import rearth.oritech.block.entity.interaction.DronePortEntity;
import rearth.oritech.api.energy.containers.DynamicEnergyStorage;
import rearth.oritech.init.ComponentContent;

import java.util.Objects;

/**
 * Mixin that adds cross-dimensional functionality to DronePortEntity
 */
@Mixin(DronePortEntity.class)
public class DronePortEntityMixin implements CrossDimensionalDrone {
    @Shadow @Final private long baseEnergyUsage;
    @Shadow @Final private int takeOffTime;
    @Shadow @Final private int landTime;
    @Shadow private BlockPos targetPosition;
    @Shadow private long lastSentAt;
    @Shadow private DronePortEntity.DroneTransferData incomingPacket;
    @Shadow private String statusMessage;
    @Shadow protected SimpleContainer cardInventory;
    
    // Add shadow for energyStorage to access it
    @Shadow @Final protected DynamicEnergyStorage energyStorage;

    @Unique
    private ResourceKey<Level> targetDimension = null;

    /**
     * Public method to set cross-dimensional target from AdvancedTargetDesignator
     */
    @Override
    public boolean oritechthings$setCrossDimensionalTarget(BlockPos targetPos, ResourceKey<Level> targetDimension) {
        DronePortEntity self = (DronePortEntity) (Object) this;
        if (ConfigLoader.getInstance().dimensionalDroneSettings.enabled()) {
            assert self.getLevel() != null;
            boolean isCrossDimensional = !targetDimension.equals(self.getLevel().dimension());
            if (isCrossDimensional) {
                return setCrossDimensionalTarget(targetPos, targetDimension);
            }
        }
        return self.setTargetFromDesignator(targetPos);
    }

    /**
     * Inject into checkPositionCard to read dimension data from AdvancedTargetDesignator
     */
    @Inject(method = "checkPositionCard", at = @At("HEAD"), cancellable = true)
    private void checkPositionCard(CallbackInfo ci) {
        if (ConfigLoader.getInstance().dimensionalDroneSettings.enabled()) {
            DronePortEntity self = (DronePortEntity) (Object) this;
            var source = cardInventory.getItem(0);

            if (source != null && !source.isEmpty()) {
                if (source.has(ModDataComponents.TARGET_DIMENSION.get())) {
                    var targetDimension = source.get(ModDataComponents.TARGET_DIMENSION.get());
                    assert self.getLevel() != null;
                    assert targetDimension != null;
                    boolean isCrossDimensional = !targetDimension.equals(self.getLevel().dimension());
                    if (isCrossDimensional) {
                        var targetPos = source.get(ComponentContent.TARGET_POSITION.get());
                        boolean success = setCrossDimensionalTarget(targetPos, targetDimension);
                        if (success) {
                            cardInventory.setItem(1, source);
                            cardInventory.setItem(0, ItemStack.EMPTY);
                            cardInventory.setChanged();
                            self.setChanged();
                        }

                        ci.cancel();
                    } else {
                        this.targetDimension = null;
                    }
                } else {
                    this.targetDimension = null;
                }
            }
        }
    }
    
    /**
     * Check if this drone port can perform cross-dimensional transfers
     */
    private boolean isCrossDimensional() {
        if (!ConfigLoader.getInstance().dimensionalDroneSettings.enabled()) {
            return false;
        }
        DronePortEntity self = (DronePortEntity) (Object) this;
        if (targetDimension == null) return false;
        assert self.getLevel() != null;
        return !targetDimension.equals(self.getLevel().dimension());
    }

    @Inject(method = "setTargetFromDesignator", at = @At("HEAD"), cancellable = true)
    private void setTargetFromDesignator(BlockPos targetPos, CallbackInfoReturnable<Boolean> cir) {
        setCrossDimensionalTarget(targetPos, targetDimension);
    }

    /**
     * Inject into canSend to check cross-dimensional energy requirements
     */
    @Inject(method = "canSend", at = @At("HEAD"), cancellable = true)
    private void canSend(CallbackInfoReturnable<Boolean> cir) {
        if (isCrossDimensional()) {
            DronePortEntity self = (DronePortEntity) (Object) this;

            if (self.disabledViaRedstone || targetPosition == null || targetDimension == null ||
                    (self.inventory.isEmpty() && self.fluidStorage.getAmount() == 0) || incomingPacket != null) {
                cir.setReturnValue(false);
                return;
            }

            long requiredEnergy = calculateCrossDimensionalEnergyUsage();
            long currentEnergy = self.getEnergyStorage().amount;
            if (currentEnergy < requiredEnergy) {
                OritechThings.LOGGER.warn("not enough energy to jump : Current: {} Required: {}", currentEnergy, requiredEnergy);
                cir.setReturnValue(false);
                return;
            }

            if (!validateCrossDimensionalTarget()) {
                OritechThings.LOGGER.warn("target dimension / position is not valid");
                cir.setReturnValue(false);
                return;
            }

            if (self.isSendingDrone()) {
                OritechThings.LOGGER.info("is still sending drone");
                cir.setReturnValue(false);
                return;
            }
            
            cir.setReturnValue(true);
        }
    }
    
    /**
     * Inject into sendDrone to handle cross-dimensional transfers
     */
    @Inject(method = "sendDrone", at = @At("HEAD"), cancellable = true)
    private void sendDrone(CallbackInfo ci) {
        if (isCrossDimensional() && sendCrossDimensionalDrone()) {
            ci.cancel();
        }
    }
    
    /**
     * Inject into calculateEnergyUsage to include cross-dimensional costs
     */
    @Inject(method = "calculateEnergyUsage", at = @At("RETURN"), cancellable = true)
    private void calculateEnergyUsage(CallbackInfoReturnable<Long> cir) {
        if (isCrossDimensional()) {
            cir.setReturnValue(calculateCrossDimensionalEnergyUsage());
        }
    }
    
    /**
     * Inject into NBT saving to persist dimension data
     */
    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfo ci) {
        if (targetDimension != null) {
            nbt.putString("target_dimension", targetDimension.location().toString());
        }
    }
    
    /**
     * Inject into NBT loading to restore dimension data
     */
    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfo ci) {
        if (nbt.contains("target_dimension")) {
            String dimensionString = nbt.getString("target_dimension");
            try {
                ResourceLocation dimensionLocation = ResourceLocation.parse(dimensionString);
                targetDimension = ResourceKey.create(Registries.DIMENSION, dimensionLocation);
            } catch (Exception e) {
                OritechThings.LOGGER.warn("Failed to load target dimension from NBT: {}", dimensionString, e);
                targetDimension = null;
            }
        }
    }

    @Unique
    private boolean setCrossDimensionalTarget(BlockPos targetPos, ResourceKey<Level> targetDimension) {
        DronePortEntity self = (DronePortEntity) (Object) this;

        assert self.getLevel() != null;
        MinecraftServer server = self.getLevel().getServer();
        if (server == null) {
            statusMessage = "message.oritechthings.drone.server_unavailable";
            return false;
        }

        Level targetLevel = server.getLevel(targetDimension);
        if (targetLevel == null) {
            statusMessage = "message.oritechthings.drone.dimension_unavailable";
            return false;
        }

        var targetState = Objects.requireNonNull(targetLevel).getBlockState(targetPos);
        if (targetState.getBlock() instanceof MachineCoreBlock && targetState.getValue(MachineCoreBlock.USED)) {
            var coreEntity = (MachineCoreEntity) targetLevel.getBlockEntity(targetPos);
            var controllerPos = Objects.requireNonNull(coreEntity).getControllerPos();
            if (controllerPos != null) targetPos = controllerPos;
        }

        BlockEntity targetEntity = targetLevel.getBlockEntity(targetPos);
        if (!(targetEntity instanceof DronePortEntity)) {
            statusMessage = "message.oritech.drone.target_invalid";
            return false;
        }

        if (!(targetLevel.getBlockState(targetPos).getBlock() instanceof DronePortBlock)) {
            statusMessage = "message.oritech.drone.target_invalid";
            return false;
        }

        this.targetDimension = targetDimension;
        this.targetPosition = targetPos;
        statusMessage = "message.oritechthings.drone.cross_dimensional_target_set";

        return true;
    }
    
    @Unique
    private boolean validateCrossDimensionalTarget() {
        if (targetDimension == null || targetPosition == null) return false;
        
        DronePortEntity self = (DronePortEntity) (Object) this;
        MinecraftServer server = self.getLevel().getServer();
        if (server == null) return false;
        
        Level targetLevel = server.getLevel(targetDimension);
        if (targetLevel == null) return false;
        
        BlockEntity targetEntity = targetLevel.getBlockEntity(targetPosition);
        return targetEntity instanceof DronePortEntity;
    }
    
    @Unique
    private long calculateCrossDimensionalEnergyUsage() {
        return baseEnergyUsage + ConfigLoader.getInstance().dimensionalDroneSettings.energyToCross();
    }
    
    /**
     * Create special particle effects for cross-dimensional transfers
     */
    @Unique
    private void triggerCrossDimensionalEffects(DronePortEntity self) {
        if (!(self.getLevel() instanceof ServerLevel serverLevel)) return;
        
        BlockPos pos = self.getBlockPos();
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.0;
        double z = pos.getZ() + 0.5;
        
        // Create an intense portal vortex effect
        for (int i = 0; i < 120; i++) {
            double angle = (i * 0.2) + (serverLevel.getGameTime() * 0.15);
            double radius = 2.0 + Math.sin(i * 0.1) * 0.5;
            double offsetY = Math.sin(i * 0.3) * 1.5;
            
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            
            // Dense portal particles for the dimensional rift effect
            serverLevel.sendParticles(ParticleTypes.PORTAL,
                x + offsetX, y + offsetY, z + offsetZ,
                3, 0.1, 0.1, 0.1, 0.2);
        }
        
        // Create a secondary inner vortex
        for (int i = 0; i < 80; i++) {
            double angle = -(i * 0.4) + (serverLevel.getGameTime() * 0.2);
            double radius = 1.0 + Math.cos(i * 0.15) * 0.3;
            double offsetY = Math.cos(i * 0.2) * 1.0;
            
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            
            // Reverse spiral with more portal particles
            serverLevel.sendParticles(ParticleTypes.PORTAL,
                x + offsetX, y + offsetY, z + offsetZ,
                2, 0.05, 0.05, 0.05, 0.3);
        }
        
        // Add warped/crimson spore particles for otherworldly effect
        for (int i = 0; i < 40; i++) {
            double offsetX = (serverLevel.random.nextDouble() - 0.5) * 4.0;
            double offsetY = serverLevel.random.nextDouble() * 3.0;
            double offsetZ = (serverLevel.random.nextDouble() - 0.5) * 4.0;
            
            serverLevel.sendParticles(ParticleTypes.WARPED_SPORE,
                x + offsetX, y + offsetY, z + offsetZ,
                1, 0.0, 0.1, 0.0, 0.1);
                
            serverLevel.sendParticles(ParticleTypes.CRIMSON_SPORE,
                x + offsetX, y + offsetY, z + offsetZ,
                1, 0.0, 0.1, 0.0, 0.1);
        }
        
        // Add soul fire flame particles for mystical energy
        for (int i = 0; i < 25; i++) {
            double offsetX = (serverLevel.random.nextDouble() - 0.5) * 3.0;
            double offsetY = serverLevel.random.nextDouble() * 2.5;
            double offsetZ = (serverLevel.random.nextDouble() - 0.5) * 3.0;
            
            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                x + offsetX, y + offsetY, z + offsetZ,
                1, 0.0, 0.2, 0.0, 0.1);
        }
        
        // Add reverse portal particles shooting upward
        for (int i = 0; i < 30; i++) {
            double offsetX = (serverLevel.random.nextDouble() - 0.5) * 2.5;
            double offsetZ = (serverLevel.random.nextDouble() - 0.5) * 2.5;
            
            serverLevel.sendParticles(ParticleTypes.REVERSE_PORTAL,
                x + offsetX, y, z + offsetZ,
                1, 0.0, 0.5, 0.0, 0.2);
        }
        
        // Add electric spark effect with end rod particles
        for (int i = 0; i < 20; i++) {
            double offsetX = (serverLevel.random.nextDouble() - 0.5) * 3.5;
            double offsetY = serverLevel.random.nextDouble() * 2.0;
            double offsetZ = (serverLevel.random.nextDouble() - 0.5) * 3.5;
            
            serverLevel.sendParticles(ParticleTypes.END_ROD,
                x + offsetX, y + offsetY, z + offsetZ,
                1, 0.0, 0.3, 0.0, 0.4);
        }
        
        // Play multiple layered sound effects for dramatic impact
        serverLevel.playSound(null, pos, SoundEvents.PORTAL_TRAVEL, SoundSource.BLOCKS, 1.2f, 0.7f);
        serverLevel.playSound(null, pos, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.BLOCKS, 1.0f, 0.8f);
        serverLevel.playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 0.8f, 1.2f);
        serverLevel.playSound(null, pos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 0.6f, 1.5f);
    }
    
    @Unique
    private boolean sendCrossDimensionalDrone() {
        DronePortEntity self = (DronePortEntity) (Object) this;
        assert self.getLevel() != null;
        MinecraftServer server = self.getLevel().getServer();
        
        if (server == null) {
            OritechThings.LOGGER.error("Cannot perform cross-dimensional transfer: server is null");
            return false;
        }

        Level targetLevel = server.getLevel(targetDimension);
        if (targetLevel == null) {
            OritechThings.LOGGER.error("Cannot access target dimension: {}", targetDimension);
            return false;
        }

        BlockEntity targetEntity = targetLevel.getBlockEntity(targetPosition);
        if (!(targetEntity instanceof DronePortEntity targetPort)) {
            OritechThings.LOGGER.error("Target is not a drone port at: {} in {}", targetPosition, targetDimension);
            return false;
        }

        long arriveTime = targetLevel.getGameTime() + takeOffTime + landTime;;
        var data = new DronePortEntity.DroneTransferData(self.inventory.getHeldStacks().stream().filter(stack -> !stack.isEmpty()).toList(), self.fluidStorage.getStack(), arriveTime);
        targetPort.setIncomingPacket(data);
        self.inventory.clearContent();
        self.fluidStorage.setStack(FluidStack.empty());
        lastSentAt = self.getLevel().getGameTime();
        energyStorage.amount -= calculateCrossDimensionalEnergyUsage();

        triggerCrossDimensionalEffects(self);
        
        self.triggerAnim("machine", "landing");
        targetPort.setChanged();
        self.setChanged();

        OritechThings.LOGGER.info("Cross-dimensional drone sent from {} to {} in dimension {} (arrives in {} ticks)", 
                self.getBlockPos(), targetPosition, targetDimension.location(),
                arriveTime - self.getLevel().getGameTime());
        
        return true;
    }
}