package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, OritechThings.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("null")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        for (DeferredBlock<?> data : Constants.getAllAddons()) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(data.get());
        }
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ACCELERATOR_SPEED_CONTROL.get());
    }
}
