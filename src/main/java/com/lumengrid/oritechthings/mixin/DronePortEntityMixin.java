package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.main.ModDataComponents;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.main.ConfigLoader;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
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
import rearth.oritech.block.entity.interaction.DronePortEntity;
import rearth.oritech.init.ComponentContent;

import java.util.List;

/**
 * Mixin that adds cross-dimensional functionality to DronePortEntity
 */
@Mixin(DronePortEntity.class)
public class DronePortEntityMixin {
    @Shadow @Final private long baseEnergyUsage;
    @Shadow @Final private int takeOffTime;
    @Shadow @Final private int landTime;
    @Shadow private BlockPos targetPosition;
    @Shadow private long lastSentAt;
    @Shadow private DronePortEntity.DroneTransferData incomingPacket;
    @Shadow private String statusMessage;
    @Shadow protected SimpleContainer cardInventory;

    @Unique
    private ResourceKey<Level> targetDimension = null;

    /**
     * Called by AdvancedTargetDesignator to set cross-dimensional target
     */
    public void oritechthings$setDimensionTarget(BlockPos targetPos, ResourceKey<Level> dimension) {
        this.targetDimension = dimension;
        this.targetPosition = targetPos;
    }

    /**
     * Inject into checkPositionCard to read dimension data from AdvancedTargetDesignator
     */
    @Inject(method = "checkPositionCard", at = @At("HEAD"), cancellable = true)
    private void checkPositionCard(CallbackInfo ci) {
        System.out.println("JOMO checkPositionCard");
        DronePortEntity self = (DronePortEntity) (Object) this;
        var source = cardInventory.getItem(0);

        if (source != null && !source.isEmpty() && source.has(ModDataComponents.TARGET_DIMENSION.get())) {
            System.out.println("JOMO checkPositionCard IF");
            var targetDimension = source.get(ModDataComponents.TARGET_DIMENSION.get());
            assert targetDimension != null;
            System.out.println("JOMO targetDimension" + targetDimension.toString());
            assert self.getLevel() != null;
            boolean isCrossDimensional = !this.targetDimension.equals(self.getLevel().dimension());
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
            }
        } else {
            System.out.println("JOMO checkPositionCard ELSE");
        }
    }
    
    /**
     * Check if this drone port can perform cross-dimensional transfers
     */
    public boolean isCrossDimensional() {
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
    public boolean setCrossDimensionalTarget(BlockPos targetPos, ResourceKey<Level> targetDimension) {
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

        int distance = targetPos.distManhattan(self.getBlockPos());
        if (distance < 50) {
            statusMessage = "message.oritechthings.drone.invalid_distance_cross_dimensional";
            return false;
        }

        BlockEntity targetEntity = targetLevel.getBlockEntity(targetPos);
        if (!(targetEntity instanceof DronePortEntity)) {
            statusMessage = "message.oritechthings.drone.target_not_drone_port";
            return false;
        }

        if (!(targetLevel.getBlockState(targetPos).getBlock() instanceof DronePortBlock)) {
            statusMessage = "message.oritechthings.drone.target_invalid_block";
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
        // self.energyStorage.amount -= calculateCrossDimensionalEnergyUsage();

        self.triggerAnim("machine", "takeoff");
        targetPort.setChanged();
        self.setChanged();

        OritechThings.LOGGER.info("Cross-dimensional drone sent from {} to {} in dimension {} (arrives in {} ticks)", 
                self.getBlockPos(), targetPosition, targetDimension.location(),
                arriveTime - self.getLevel().getGameTime());
        
        return true;
    }
    
    @Unique
    private List<ItemStack> oritechthings$getInventoryStacks() {
        DronePortEntity self = (DronePortEntity) (Object) this;
        try {
            var field = DronePortEntity.class.getDeclaredField("inventory");
            field.setAccessible(true);
            var inventory = field.get(self);
            
            var method = inventory.getClass().getMethod("getHeldStacks");
            @SuppressWarnings("unchecked")
            NonNullList<ItemStack> heldStacks = (NonNullList<ItemStack>) method.invoke(inventory);
            
            return heldStacks.stream().filter(stack -> !stack.isEmpty()).toList();
        } catch (Exception e) {
            OritechThings.LOGGER.error("Failed to access inventory", e);
            return List.of();
        }
    }
    
    @Unique
    private FluidStack oritechthings$getFluidStack() {
        DronePortEntity self = (DronePortEntity) (Object) this;
        try {
            var field = DronePortEntity.class.getDeclaredField("fluidStorage");
            field.setAccessible(true);
            var fluidStorage = field.get(self);
            
            var method = fluidStorage.getClass().getMethod("getStack");
            return (FluidStack) method.invoke(fluidStorage);
        } catch (Exception e) {
            OritechThings.LOGGER.error("Failed to access fluid storage", e);
            return FluidStack.empty();
        }
    }
}