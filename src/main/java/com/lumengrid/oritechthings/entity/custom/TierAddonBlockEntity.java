package com.lumengrid.oritechthings.entity.custom;

import com.lumengrid.oritechthings.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import org.jetbrains.annotations.Nullable;
import rearth.oritech.api.transfer.energy.EnergyProvider;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;
import rearth.oritech.block.entity.addons.AddonBlockEntity;
import rearth.oritech.util.MachineAddonController;

public class TierAddonBlockEntity extends AddonBlockEntity implements EnergyProvider {

    private MachineAddonController cachedController;

    public TierAddonBlockEntity(BlockPos pos, BlockState state) {
        super(ModEntities.TIER_ADDON.get(), pos, state);
    }

    private boolean isConnected() {
        if (!acceptsEnergy()) {
            return false;
        }
        var isUsed = this.getBlockState().getValue(MachineAddonBlock.ADDON_USED);
        return isUsed && getCachedController() != null;
    }

    private EnergyHandler getMainStorage() {
        if (!acceptsEnergy()) {
            return null;
        }

        var isUsed = this.getBlockState().getValue(MachineAddonBlock.ADDON_USED);
        if (!isUsed) {
            return null;
        }

        var controllerEntity = getCachedController();
        return controllerEntity == null ? null : controllerEntity.getStorageForAddon();
    }

    private boolean acceptsEnergy() {
        return getBlockState().getBlock() instanceof MachineAddonBlock machineAddonBlock
                && machineAddonBlock.getAddonSettings().acceptEnergy();
    }

    private MachineAddonController getCachedController() {
        if (cachedController != null) {
            return cachedController;
        }
        if (level == null) {
            return null;
        }

        var controllerEntity = level.getBlockEntity(getControllerPos());
        if (controllerEntity instanceof MachineAddonController machineAddonController) {
            cachedController = machineAddonController;
            return cachedController;
        }

        if (controllerEntity == null) {
            return null;
        }
        return (MachineAddonController) controllerEntity;
    }

    // ✅ Implementazione corretta per Oritech 1.21.2+ usando EnergyHandler
    @Override
    public EnergyHandler getEnergyLookup(@Nullable Direction direction) {
        if (!isConnected()) {
            return null;
        }
        return getMainStorage();
    }
}