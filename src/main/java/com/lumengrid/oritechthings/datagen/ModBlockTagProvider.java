package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, OritechThings.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_2.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_3.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_4.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_5.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_6.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_7.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_8.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_9.get())
                .add(ModBlocks.ADDON_BLOCK_SPEED_TIER_2.get())
                .add(ModBlocks.ADDON_BLOCK_SPEED_TIER_3.get())
                .add(ModBlocks.ADDON_BLOCK_SPEED_TIER_4.get())
                .add(ModBlocks.ADDON_BLOCK_SPEED_TIER_5.get())
                .add(ModBlocks.ADDON_BLOCK_SPEED_TIER_6.get())
                .add(ModBlocks.ADDON_BLOCK_SPEED_TIER_7.get())
                .add(ModBlocks.ADDON_BLOCK_SPEED_TIER_8.get())
                .add(ModBlocks.ADDON_BLOCK_SPEED_TIER_9.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_2.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_3.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_4.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_5.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_6.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_7.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_8.get())
                .add(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_9.get());
    }
}
