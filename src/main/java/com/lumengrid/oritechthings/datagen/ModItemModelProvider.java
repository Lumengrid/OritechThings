package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ModelProvider {

    public ModItemModelProvider(PackOutput output) {
        super(output, OritechThings.MOD_ID);
    }

    @Override
    protected void registerModels(
            BlockModelGenerators blockModels,
            ItemModelGenerators itemModels
    ) {
        for (DeferredBlock<?> data : Constants.getAllAddons()) {
            registerBlockItem(blockModels, data);
        }

        registerBlockItem(blockModels, ModBlocks.ACCELERATOR_SPEED_SENSOR);
        registerBlockItem(blockModels, ModBlocks.ACCELERATOR_MAGNETIC_FIELD);

        itemModels.generateFlatItem(
                ModItems.ADVANCED_TARGET_DESIGNATOR.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.FRAME_PLACER.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.AMETHYST_FISH_SPAWN_EGG.get(),
                ModelTemplates.FLAT_ITEM
        );
    }

    private static void registerBlockItem(
            BlockModelGenerators blockModels,
            DeferredBlock<?> deferredBlock
    ) {
        Block block = deferredBlock.get();
        blockModels.registerSimpleItemModel(
                block,
                ModelLocationUtils.getModelLocation(block)
        );
    }
}
