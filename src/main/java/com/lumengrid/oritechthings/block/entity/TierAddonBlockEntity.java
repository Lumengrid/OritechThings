package com.lumengrid.oritechthings.block.entity;

import com.lumengrid.oritechthings.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import rearth.oritech.block.entity.addons.AddonBlockEntity;

public class TierAddonBlockEntity extends AddonBlockEntity {

    public TierAddonBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TIER_ADDON.get(), pos, state);
    }
}
