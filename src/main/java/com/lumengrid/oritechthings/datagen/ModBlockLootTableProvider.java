package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.util.Constants;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

        for (DeferredBlock<?> data : Constants.getAll()) {
            dropSelf(data.get());
        }

        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_2.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_3.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_4.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_5.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_6.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_7.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_8.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_9.get());

        // dropSelf(ModBlocks.ADDON_BLOCK_SPEED_TIER_2.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_SPEED_TIER_3.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_SPEED_TIER_4.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_SPEED_TIER_5.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_SPEED_TIER_6.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_SPEED_TIER_7.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_SPEED_TIER_8.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_SPEED_TIER_9.get());

        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_2.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_3.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_4.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_5.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_6.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_7.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_8.get());
        // dropSelf(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_9.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
