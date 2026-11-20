package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;
import com.lumengrid.oritechthings.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredBlock;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output,
                              CompletableFuture<TagLookup<Block>> blockTags,
                              @Nullable ExistingFileHelper existingFileHelper) {
        super(output, blockTags, OritechThings.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

        for (DeferredBlock<?> data : Constants.getAllAddons()) {
            tag(ModTags.Items.ADDONS).add(data.get().asItem());
        }

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
        tag(ModTags.Items.PARTICLE_ACCELERATOR).add(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get().asItem());
    }
}
