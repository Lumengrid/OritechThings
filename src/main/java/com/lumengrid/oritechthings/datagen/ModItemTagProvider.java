package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;
import com.lumengrid.oritechthings.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags,
                              @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, OritechThings.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("null")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        for(DeferredBlock<?> data: Constants.EFFICIENCY){
            tag(ModTags.Items.TIERED_ADDON_EFFICIENCY).add(data.get().asItem());
        }

        for(DeferredBlock<?> data: Constants.SPEED){
            tag(ModTags.Items.TIERED_ADDON_SPEED).add(data.get().asItem());
        }

        for(DeferredBlock<?> data: Constants.EFFICIENT){
            tag(ModTags.Items.TIERED_ADDON_EFFICIENT_SPEED).add(data.get().asItem());
        }

        for(DeferredBlock<?> data: Constants.CAPACITOR){
            tag(ModTags.Items.TIERED_ADDON_CAPACITOR).add(data.get().asItem());
        }

        for(DeferredBlock<?> data: Constants.ACCEPTOR){
            tag(ModTags.Items.TIERED_ADDON_ACCEPTOR).add(data.get().asItem());
        }

        for(DeferredBlock<?> data: Constants.PROCESSING){
            tag(ModTags.Items.TIERED_ADDON_PROCESSING).add(data.get().asItem());
        }

        tag(ModTags.Items.PARTICLE_ACCELERATOR).add(ModBlocks.ACCELERATOR_SPEED_SENSOR.get().asItem());
        tag(ModTags.Items.PARTICLE_ACCELERATOR).add(ModItems.ACCELERATOR_TARGET_DESIGNATOR.get());
    }
}
