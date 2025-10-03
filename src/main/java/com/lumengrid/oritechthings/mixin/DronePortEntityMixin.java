package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.main.ModDataComponents;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.api.CrossDimensionalDrone;
import com.lumengrid.oritechthings.block.ModBlocks;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
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
import rearth.oritech.util.MachineAddonController;

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

    @Shadow @Final protected DynamicEnergyStorage energyStorage;

    @Unique
    private ResourceKey<Level> targetDimension = null;

    @Unique
    private boolean hasCrossDimensionalAddon = false;

    /**
     * Public method to set cross-dimensional target from AdvancedTargetDesignator
     */
    @Override
    public boolean oritechthings$setCrossDimensionalTarget(BlockPos targetPos, ResourceKey<Level> targetDimension) {
        DronePortEntity self = (DronePortEntity) (Object) this;
        
        if (!ConfigLoader.getInstance().dimensionalDroneSettings.enabled()) {
            return self.setTargetFromDesignator(targetPos);
        }
        
        assert self.getLevel() != null;
        boolean isCrossDimensional = !targetDimension.equals(self.getLevel().dimension());
        
        if (isCrossDimensional) {
            validateCrossDimensionalAddonState();
            
            if (!hasCrossDimensionalAddon) {
                statusMessage = "message.oritechthings.drone.addon_required";
                OritechThings.LOGGER.warn("Player attempted to set cross-dimensional target at {} without Cross-Dimensional Addon at drone port {}", 
                        targetPos, self.getBlockPos());
                return false;
            }
            
            return setCrossDimensionalTarget(targetPos, targetDimension);
        } else {
            return self.setTargetFromDesignator(targetPos);
        }
    }

    /**
     * Inject into checkPositionCard to read dimension data from AdvancedTargetDesignator
     */
    @Inject(method = "checkPositionCard", at = @At("HEAD"), cancellable = true)
    private void checkPositionCard(CallbackInfo ci) {
        if (ConfigLoader.getInstance().dimensionalDroneSettings.enabled() && hasCrossDimensionalAddon) {
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
        if (self.getLevel() != null && self.getLevel().getGameTime() % 20 == 0) {
            validateCrossDimensionalAddonState();
        }
        
        if (!hasCrossDimensionalAddon) {
            return false;
        }
        if (targetDimension == null) return false;
        assert self.getLevel() != null;
        return !targetDimension.equals(self.getLevel().dimension());
    }
    
    @Unique
    private void validateCrossDimensionalAddonState() {
        DronePortEntity self = (DronePortEntity) (Object) this;
        boolean foundCrossDimensionalAddon = false;

        for (BlockPos addonPos : self.getConnectedAddons()) {
            if (self.getLevel() != null) {
                var blockState = self.getLevel().getBlockState(addonPos);
                if (blockState.getBlock().equals(ModBlocks.ADDON_BLOCK_CROSS_DIMENSIONAL.get())) {
                    foundCrossDimensionalAddon = true;
                    break;
                }
            }
        }

        if (hasCrossDimensionalAddon != foundCrossDimensionalAddon) {
            hasCrossDimensionalAddon = foundCrossDimensionalAddon;
            OritechThings.LOGGER.debug("Cross-dimensional addon state changed to: {} at {}", 
                    hasCrossDimensionalAddon, self.getBlockPos());
        }
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
     * Inject into isSendingDrone to handle cross-dimensional transfers
     */
    @Inject(method = "isSendingDrone", at = @At("RETURN"), cancellable = true)
    private void isSendingDrone(CallbackInfoReturnable<Boolean> cir) {
        if (isCrossDimensional()) {
            DronePortEntity self = (DronePortEntity) (Object) this;
            assert self.getLevel() != null;
            long diff = self.getLevel().getGameTime() - this.lastSentAt;
            cir.setReturnValue(diff < 40);
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
        nbt.putBoolean("has_cross_dimensional_addon", hasCrossDimensionalAddon);
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
        hasCrossDimensionalAddon = nbt.getBoolean("has_cross_dimensional_addon");
    }
    
    /**
     * Inject into getAdditionalStatFromAddon to detect cross-dimensional addon
     */
    @Inject(method = "getAdditionalStatFromAddon", at = @At("TAIL"))
    private void getAdditionalStatFromAddon(MachineAddonController.AddonBlock addonBlock, CallbackInfo ci) {
        if (addonBlock.state().getBlock().equals(ModBlocks.ADDON_BLOCK_CROSS_DIMENSIONAL.get())) {
            hasCrossDimensionalAddon = true;
        }
    }
    
    /**
     * Inject into resetAddons to reset cross-dimensional addon state
     */
    @Inject(method = "resetAddons", at = @At("HEAD"))
    private void resetAddons(CallbackInfo ci) {
        hasCrossDimensionalAddon = false;
    }
    
    /**
     * Additional check to ensure addon state is properly updated
     * This method is called every time addons are recalculated
     */
    @Inject(method = "initAddons", at = @At("TAIL"))
    private void initAddons(CallbackInfo ci) {
        DronePortEntity self = (DronePortEntity) (Object) this;
        boolean foundCrossDimensionalAddon = false;

        for (BlockPos addonPos : self.getConnectedAddons()) {
            if (self.getLevel() != null) {
                var blockState = self.getLevel().getBlockState(addonPos);
                if (blockState.getBlock().equals(ModBlocks.ADDON_BLOCK_CROSS_DIMENSIONAL.get())) {
                    foundCrossDimensionalAddon = true;
                    break;
                }
            }
        }
        
        hasCrossDimensionalAddon = foundCrossDimensionalAddon;
    }

    @Unique
    private boolean setCrossDimensionalTarget(BlockPos targetPos, ResourceKey<Level> targetDimension) {
        DronePortEntity self = (DronePortEntity) (Object) this;
        validateCrossDimensionalAddonState();
        if (!hasCrossDimensionalAddon) {
            statusMessage = "message.oritechthings.drone.addon_required";
            OritechThings.LOGGER.warn("Attempted to set cross-dimensional target without addon at drone port {}", 
                    self.getBlockPos());
            return false;
        }

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
     * @param dronePort The drone port entity
     * @param isArrival true for arrival effects, false for departure effects
     */
    @Unique
    private void triggerCrossDimensionalEffects(DronePortEntity dronePort, boolean isArrival) {
        if (!(dronePort.getLevel() instanceof ServerLevel serverLevel)) return;

        BlockPos centerPos = calculateMultiblockCenter(dronePort);
        
        double x = centerPos.getX() + 0.5;
        double y = centerPos.getY() + 0.5;
        double z = centerPos.getZ() + 0.5;
        
        for (int i = 0; i < 100; i++) {
            double angle = (i * 0.15) + (serverLevel.getGameTime() * 0.1);
            double radius = 1.5 + Math.sin(i * 0.08) * 1.0;
            double offsetY = Math.sin(i * 0.2) * 2.0;
            
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
 
            serverLevel.sendParticles(ParticleTypes.PORTAL,
                x + offsetX, y + offsetY, z + offsetZ,
                2, 0.1, 0.1, 0.1, 0.3);
        }
 
        for (int i = 0; i < 60; i++) {
            double angle = -(i * 0.3) + (serverLevel.getGameTime() * 0.15);
            double radius = 0.8 + Math.cos(i * 0.12) * 0.4;
            double offsetY = Math.cos(i * 0.15) * 1.5;
            
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
 
            serverLevel.sendParticles(ParticleTypes.PORTAL,
                x + offsetX, y + offsetY, z + offsetZ,
                1, 0.05, 0.05, 0.05, 0.4);
        }
 
        for (int i = 0; i < 40; i++) {
            double angle = Math.random() * Math.PI * 2;
            double radius = 0.5 + Math.random() * 2.0;
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            double offsetY = Math.random() * 2.5;
 
            serverLevel.sendParticles(ParticleTypes.END_ROD,
                x + offsetX, y + offsetY, z + offsetZ,
                1, 0.05, 0.05, 0.05, 0.1);
 
            serverLevel.sendParticles(ParticleTypes.REVERSE_PORTAL,
                x + offsetX, y + offsetY, z + offsetZ,
                1, 0.1, 0.1, 0.1, 0.2);
        }
 
        for (int i = 0; i < 30; i++) {
            double offsetX = (Math.random() - 0.5) * 3.0;
            double offsetZ = (Math.random() - 0.5) * 3.0;
            double offsetY = Math.random() * 3.0;
            
            serverLevel.sendParticles(ParticleTypes.WARPED_SPORE,
                x + offsetX, y + offsetY, z + offsetZ,
                1, 0.1, 0.1, 0.1, 0.05);
            
            serverLevel.sendParticles(ParticleTypes.CRIMSON_SPORE,
                x + offsetX, y + offsetY, z + offsetZ,
                1, 0.1, 0.1, 0.1, 0.05);
        }
        
        for (int i = 0; i < 20; i++) {
            double offsetX = (Math.random() - 0.5) * 4.0;
            double offsetZ = (Math.random() - 0.5) * 4.0;
            double offsetY = Math.random() * 3.0;

            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                x + offsetX, y + offsetY, z + offsetZ,
                1, 0.02, 0.02, 0.02, 0.02);
        }

        if (isArrival) {
            serverLevel.playSound(null, centerPos, SoundEvents.PORTAL_TRAVEL, SoundSource.BLOCKS, 1.2f, 1.0f);
            serverLevel.playSound(null, centerPos, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.BLOCKS, 1.0f, 1.2f);
            serverLevel.playSound(null, centerPos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 0.8f, 0.8f);
            serverLevel.playSound(null, centerPos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 0.6f, 1.8f);
            
            OritechThings.LOGGER.info("Cross-dimensional arrival effects triggered at {} in dimension {}", 
                    centerPos, serverLevel.dimension().location());
        } else {
            serverLevel.playSound(null, centerPos, SoundEvents.PORTAL_TRAVEL, SoundSource.BLOCKS, 1.5f, 0.7f);
            serverLevel.playSound(null, centerPos, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.BLOCKS, 1.2f, 0.8f);
            serverLevel.playSound(null, centerPos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 1.0f, 1.2f);
            serverLevel.playSound(null, centerPos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 0.8f, 1.5f);
        }
    }
    
    @Unique
    private BlockPos calculateMultiblockCenter(DronePortEntity dronePort) {
        BlockPos controllerPos = dronePort.getBlockPos();
        Direction facing = dronePort.getFacingForMultiblock();
        return controllerPos.relative(facing);
    }
    
    /**
     * Inject into checkIncomingAnimation to prevent normal landing animation for cross-dimensional transfers
     */
    @Inject(method = "checkIncomingAnimation", at = @At("HEAD"), cancellable = true)
    private void checkIncomingAnimation(CallbackInfo ci) {
        DronePortEntity self = (DronePortEntity) (Object) this;
        
        if (incomingPacket != null && isCrossDimensionalPacket()) {
            ci.cancel();
        }
    }
    
    @Unique
    private boolean isCrossDimensionalPacket() {
        if (incomingPacket == null) return false;
        
        DronePortEntity self = (DronePortEntity) (Object) this;
        long expectedNormalTime = takeOffTime + landTime;
        long expectedCrossDimensionalTime = landTime;
        long currentTime = self.getLevel().getGameTime();
        long timeSinceSent = currentTime - (incomingPacket.arrivesAt() - expectedNormalTime);
        long timeSinceCrossDimensional = currentTime - (incomingPacket.arrivesAt() - expectedCrossDimensionalTime);

        return Math.abs(timeSinceCrossDimensional) < Math.abs(timeSinceSent);
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

        long arriveTime = targetLevel.getGameTime() + 20; // since we just teleport the time to arrive should be really short
        var data = new DronePortEntity.DroneTransferData(self.inventory.getHeldStacks().stream().filter(stack -> !stack.isEmpty()).toList(), self.fluidStorage.getStack(), arriveTime);
        targetPort.setIncomingPacket(data);
        self.inventory.clearContent();
        self.fluidStorage.setStack(FluidStack.empty());
        lastSentAt = self.getLevel().getGameTime();
        energyStorage.amount -= calculateCrossDimensionalEnergyUsage();

        triggerCrossDimensionalEffects(self, false);
        scheduleCrossDimensionalArrival(targetPort, targetLevel, 20);
        
        targetPort.setChanged();
        self.setChanged();

        OritechThings.LOGGER.info("Cross-dimensional drone sent from {} to {} in dimension {} (arrives in {} ticks)", 
                self.getBlockPos(), targetPosition, targetDimension.location(),
                arriveTime - self.getLevel().getGameTime());
        
        return true;
    }
    
    @Unique
    private void scheduleCrossDimensionalArrival(DronePortEntity targetPort, Level targetLevel, int delayTicks) {
        if (targetLevel instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new net.minecraft.server.TickTask(
                serverLevel.getServer().getTickCount() + delayTicks,
                () -> {
                    triggerCrossDimensionalEffects(targetPort, true);
                }
            ));
        }
    }
}