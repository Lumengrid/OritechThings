package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;
import com.lumengrid.oritechthings.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<net.minecraft.core.HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags,
                              @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, OritechThings.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(net.minecraft.core.HolderLookup.@org.jetbrains.annotations.NotNull Provider provider) {

        for (RegistryObject<?> data : Constants.getAllAddons()) {
            tag(ModTags.Items.ADDONS).add(((net.minecraft.world.level.block.Block) data.get()).asItem());
        }

        for(RegistryObject<?> data: Constants.EFFICIENCY){
            tag(ModTags.Items.TIERED_ADDON_EFFICIENCY).add(((net.minecraft.world.level.block.Block) data.get()).asItem());
        }

        for(RegistryObject<?> data: Constants.SPEED){
            tag(ModTags.Items.TIERED_ADDON_SPEED).add(((net.minecraft.world.level.block.Block) data.get()).asItem());
        }

        for(RegistryObject<?> data: Constants.EFFICIENT){
            tag(ModTags.Items.TIERED_ADDON_EFFICIENT_SPEED).add(((net.minecraft.world.level.block.Block) data.get()).asItem());
        }

        for(RegistryObject<?> data: Constants.CAPACITOR){
            tag(ModTags.Items.TIERED_ADDON_CAPACITOR).add(((net.minecraft.world.level.block.Block) data.get()).asItem());
        }

        for(RegistryObject<?> data: Constants.ACCEPTOR){
            tag(ModTags.Items.TIERED_ADDON_ACCEPTOR).add(((net.minecraft.world.level.block.Block) data.get()).asItem());
        }

        for(RegistryObject<?> data: Constants.PROCESSING){
            tag(ModTags.Items.TIERED_ADDON_PROCESSING).add(((net.minecraft.world.level.block.Block) data.get()).asItem());
        }

        tag(ModTags.Items.PARTICLE_ACCELERATOR).add(ModBlocks.ACCELERATOR_SPEED_SENSOR.get().asItem());
        tag(ModTags.Items.PARTICLE_ACCELERATOR).add(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get().asItem());
    }
}
