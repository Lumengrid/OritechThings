package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.api.CrossDimensionalDrone;
import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.ConfigLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

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

@Mixin(DronePortEntity.class)
public class DronePortEntityMixin implements CrossDimensionalDrone {

    @Shadow private String statusMessage;
    @Shadow private long lastSentAt;

    @Unique
    private ResourceKey<Level> targetDimension = null;

    @Unique
    private boolean hasCrossDimensionalAddon = false;

    @Override
    public boolean oritechthings$setCrossDimensionalTarget(BlockPos targetPos, ResourceKey<Level> targetDimension) {
        if (targetPos == null) {
            return false;
        }

        DronePortEntity self = (DronePortEntity) (Object) this;
        assert self.getLevel() != null;

        ResourceKey<Level> effectiveDimension = targetDimension != null ? targetDimension : self.getLevel().dimension();

        if (!ConfigLoader.getInstance().dimensionalDroneSettings.enabled()) {
            return self.setTargetFromDesignator(GlobalPos.of(effectiveDimension, targetPos));
        }

        boolean isCrossDimensional = !effectiveDimension.equals(self.getLevel().dimension());

        if (isCrossDimensional) {
            validateCrossDimensionalAddonState();

            if (!hasCrossDimensionalAddon) {
                statusMessage = "message.oritechthings.drone.addon_required";
                return false;
            }

            return setCrossDimensionalTarget(targetPos, effectiveDimension);
        } else {
            return self.setTargetFromDesignator(GlobalPos.of(self.getLevel().dimension(), targetPos));
        }
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void saveAdditional(ValueOutput output, CallbackInfo ci) {
        if (targetDimension != null) {
            output.putString("target_dimension", targetDimension.identifier().toString());
        }
        output.putBoolean("has_cross_dimensional_addon", hasCrossDimensionalAddon);
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void loadAdditional(ValueInput input, CallbackInfo ci) {
        String dimensionString = input.getStringOr("target_dimension", "");
        if (!dimensionString.isEmpty()) {
            try {
                Identifier dimensionLocation = Identifier.parse(dimensionString);
                targetDimension = ResourceKey.create(Registries.DIMENSION, dimensionLocation);
            } catch (Exception e) {
                targetDimension = null;
            }
        }
        hasCrossDimensionalAddon = input.getBooleanOr("has_cross_dimensional_addon", false);
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

        hasCrossDimensionalAddon = foundCrossDimensionalAddon;
    }

    @Unique
    private boolean setCrossDimensionalTarget(BlockPos targetPos, ResourceKey<Level> targetDimension) {
        DronePortEntity self = (DronePortEntity) (Object) this;
        validateCrossDimensionalAddonState();
        assert self.getLevel() != null;
        if (targetDimension == null) {
            targetDimension = self.getLevel().dimension();
        }

        if (!targetDimension.equals(self.getLevel().dimension()) && !hasCrossDimensionalAddon) {
            statusMessage = "message.oritechthings.drone.addon_required";
            return false;
        }

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

        var targetState = targetLevel.getBlockState(targetPos);
        if (targetState.getBlock() instanceof MachineCoreBlock && targetState.getValue(MachineCoreBlock.USED)) {
            var coreEntity = (MachineCoreEntity) targetLevel.getBlockEntity(targetPos);
            if (coreEntity != null && coreEntity.getControllerPos() != null) {
                targetPos = coreEntity.getControllerPos();
            }
        }

        BlockEntity targetEntity = targetLevel.getBlockEntity(targetPos);
        if (!(targetEntity instanceof DronePortEntity) || !(targetLevel.getBlockState(targetPos).getBlock() instanceof DronePortBlock)) {
            statusMessage = "message.oritech.drone.target_invalid";
            return false;
        }

        GlobalPos globalTarget = GlobalPos.of(targetDimension, targetPos);
        boolean posSet = self.setTargetFromDesignator(globalTarget);
        if (!posSet) {
            return false;
        }

        this.targetDimension = targetDimension;
        statusMessage = "message.oritechthings.drone.cross_dimensional_target_set";

        return true;
    }

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

    @Inject(method = "isSendingDrone", at = @At("RETURN"), cancellable = true)
    private void isSendingDrone(CallbackInfoReturnable<Boolean> cir) {
        if (isCrossDimensional()) {
            DronePortEntity self = (DronePortEntity) (Object) this;
            if (self.getLevel() != null) {
                long diff = self.getLevel().getGameTime() - this.lastSentAt;
                int cooldown = ConfigLoader.getInstance().dimensionalDroneSettings.cooldownTicks();
                cir.setReturnValue(diff < cooldown);
            }
        }
    }
}