package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.api.CrossDimensionalDrone;
import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.ConfigLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

// ✅ 2. Import per la gestione NBT in 1.21.2+ (da ExpandableEnergyStorageBlockEntity)
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import rearth.oritech.api.transfer.energy.DynamicEnergyStorage;
import rearth.oritech.block.blocks.interaction.DronePortBlock;
import rearth.oritech.block.blocks.processing.MachineCoreBlock;
import rearth.oritech.block.entity.MachineCoreEntity;
import rearth.oritech.block.entity.interaction.DronePortEntity;

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
                return false;
            }

            return setCrossDimensionalTarget(targetPos, targetDimension);
        } else {
            return self.setTargetFromDesignator(targetPos);
        }
    }

    // ✅ 3. Signature aggiornata per ValueOutput (Oritech 1.21.2+)
    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void saveAdditional(ValueOutput output, CallbackInfo ci) {
        if (targetDimension != null) {
            output.putString("target_dimension", targetDimension.identifier().toString());
        }
        output.putBoolean("has_cross_dimensional_addon", hasCrossDimensionalAddon);
    }

    // ✅ 4. Signature aggiornata per ValueInput con ResourceLocation.parse(...)
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

        this.targetDimension = targetDimension;
        this.targetPosition = targetPos;
        statusMessage = "message.oritechthings.drone.cross_dimensional_target_set";

        return true;
    }
}