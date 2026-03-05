package com.lumengrid.oritechthings.entity.custom;

import com.lumengrid.oritechthings.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import rearth.oritech.api.energy.EnergyApi;
import rearth.oritech.api.energy.containers.DelegatingEnergyStorage;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;
import rearth.oritech.block.entity.addons.AddonBlockEntity;
import rearth.oritech.util.MachineAddonController;

import java.util.Objects;

public class TierAddonBlockEntity extends AddonBlockEntity implements EnergyApi.BlockProvider {
    private final DelegatingEnergyStorage delegatedStorage = new DelegatingEnergyStorage(this::getMainStorage, this::isConnected);

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

    private EnergyApi.EnergyStorage getMainStorage() {
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

        // Keep parity with Oritech's base behavior: if the addon is marked as used,
        // controller lookup is expected to succeed.
        return (MachineAddonController) Objects.requireNonNull(controllerEntity);
    }

    @Override
    public EnergyApi.EnergyStorage getEnergyStorage(Direction direction) {
        return delegatedStorage;
    }
}
