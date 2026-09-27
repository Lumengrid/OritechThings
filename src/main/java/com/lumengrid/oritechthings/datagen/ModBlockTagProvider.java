package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;
import com.lumengrid.oritechthings.util.ModTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {

    public ModBlockTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(output, lookupProvider, OritechThings.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (DeferredBlock<?> data : Arrays.asList(Constants.getAllAddons())) {
            tag(ModTags.Blocks.ADDONS).add(data.get());
        }

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(ModTags.Blocks.ADDONS)
                .add(ModBlocks.ACCELERATOR_SPEED_SENSOR.get())
                .add(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get())
                .add(ModBlocks.INFESTED_AMETHYST_BLOCK.get());

        addBlocksToTag(
                Arrays.asList(Constants.EFFICIENCY),
                ModTags.Blocks.TIERED_ADDON_EFFICIENCY
        );
        addBlocksToTag(
                Arrays.asList(Constants.SPEED),
                ModTags.Blocks.TIERED_ADDON_SPEED
        );
        addBlocksToTag(
                Arrays.asList(Constants.EFFICIENT),
                ModTags.Blocks.TIERED_ADDON_EFFICIENT_SPEED
        );
        addBlocksToTag(
                Arrays.asList(Constants.CAPACITOR),
                ModTags.Blocks.TIERED_ADDON_CAPACITOR
        );
        addBlocksToTag(
                Arrays.asList(Constants.ACCEPTOR),
                ModTags.Blocks.TIERED_ADDON_ACCEPTOR
        );
        addBlocksToTag(
                Arrays.asList(Constants.PROCESSING),
                ModTags.Blocks.TIERED_ADDON_PROCESSING
        );

        tag(ModTags.Blocks.PARTICLE_ACCELERATOR)
                .add(ModBlocks.ACCELERATOR_SPEED_SENSOR.get())
                .add(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get());
    }

    private void addBlocksToTag(
            Iterable<? extends DeferredBlock<?>> blocks,
            net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> tagKey
    ) {
        for (DeferredBlock<?> data : blocks) {
            tag(tagKey).add(data.get());
        }
    }
}
