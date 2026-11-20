package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;
import com.lumengrid.oritechthings.util.ModTags;

import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredBlock;

import org.jetbrains.annotations.Nullable;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, OritechThings.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

        for (DeferredBlock<?> data : Constants.getAllAddons()) {
            tag(ModTags.Blocks.ADDONS).add(data.get());
        }

        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(ModTags.Blocks.ADDONS);

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ACCELERATOR_SPEED_SENSOR.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.INFESTED_AMETHYST_BLOCK.get());

        for(DeferredBlock<?> data: Constants.EFFICIENCY){
            tag(ModTags.Blocks.TIERED_ADDON_EFFICIENCY).add(data.get());
        }

        for(DeferredBlock<?> data: Constants.SPEED){
            tag(ModTags.Blocks.TIERED_ADDON_SPEED).add(data.get());
        }

        for(DeferredBlock<?> data: Constants.EFFICIENT){
            tag(ModTags.Blocks.TIERED_ADDON_EFFICIENT_SPEED).add(data.get());
        }

        for(DeferredBlock<?> data: Constants.CAPACITOR){
            tag(ModTags.Blocks.TIERED_ADDON_CAPACITOR).add(data.get());
        }

        for(DeferredBlock<?> data: Constants.ACCEPTOR){
            tag(ModTags.Blocks.TIERED_ADDON_ACCEPTOR).add(data.get());
        }

        for(DeferredBlock<?> data: Constants.PROCESSING){
            tag(ModTags.Blocks.TIERED_ADDON_PROCESSING).add(data.get());
        }

        tag(ModTags.Blocks.PARTICLE_ACCELERATOR).add(ModBlocks.ACCELERATOR_SPEED_SENSOR.get());
        tag(ModTags.Blocks.PARTICLE_ACCELERATOR).add(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get());


    }
}
