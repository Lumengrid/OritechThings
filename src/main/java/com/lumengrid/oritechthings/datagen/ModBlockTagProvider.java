package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;
import com.lumengrid.oritechthings.util.ModTags;

import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.Nullable;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, java.util.concurrent.CompletableFuture<net.minecraft.core.HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, OritechThings.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(net.minecraft.core.HolderLookup.@org.jetbrains.annotations.NotNull Provider provider) {

        for (RegistryObject<?> data : Constants.getAllAddons()) {
            tag(ModTags.Blocks.ADDONS).add(((net.minecraft.world.level.block.Block) data.get()));
        }

        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(ModTags.Blocks.ADDONS);

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ACCELERATOR_SPEED_SENSOR.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.INFESTED_AMETHYST_BLOCK.get());

        for(RegistryObject<?> data: Constants.EFFICIENCY){
            tag(ModTags.Blocks.TIERED_ADDON_EFFICIENCY).add(((net.minecraft.world.level.block.Block) data.get()));
        }

        for(RegistryObject<?> data: Constants.SPEED){
            tag(ModTags.Blocks.TIERED_ADDON_SPEED).add(((net.minecraft.world.level.block.Block) data.get()));
        }

        for(RegistryObject<?> data: Constants.EFFICIENT){
            tag(ModTags.Blocks.TIERED_ADDON_EFFICIENT_SPEED).add(((net.minecraft.world.level.block.Block) data.get()));
        }

        for(RegistryObject<?> data: Constants.CAPACITOR){
            tag(ModTags.Blocks.TIERED_ADDON_CAPACITOR).add(((net.minecraft.world.level.block.Block) data.get()));
        }

        for(RegistryObject<?> data: Constants.ACCEPTOR){
            tag(ModTags.Blocks.TIERED_ADDON_ACCEPTOR).add(((net.minecraft.world.level.block.Block) data.get()));
        }

        for(RegistryObject<?> data: Constants.PROCESSING){
            tag(ModTags.Blocks.TIERED_ADDON_PROCESSING).add(((net.minecraft.world.level.block.Block) data.get()));
        }

        tag(ModTags.Blocks.PARTICLE_ACCELERATOR).add(ModBlocks.ACCELERATOR_SPEED_SENSOR.get());
        tag(ModTags.Blocks.PARTICLE_ACCELERATOR).add(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get());


    }
}
