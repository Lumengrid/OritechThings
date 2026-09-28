package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Optional;

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
        registerBlockItem(blockModels, ModBlocks.INFESTED_AMETHYST_BLOCK);

        itemModels.generateFlatItem(
                ModItems.ADVANCED_TARGET_DESIGNATOR.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.FRAME_PLACER.get(),
                ModelTemplates.FLAT_ITEM
        );

        ModelTemplate spawnEggTemplate = new ModelTemplate(
                Optional.of(Identifier.fromNamespaceAndPath("minecraft", "item/template_spawn_egg")),
                Optional.empty()
        );

        spawnEggTemplate.create(
                ModelLocationUtils.getModelLocation(ModItems.AMETHYST_FISH_SPAWN_EGG.get()),
                new TextureMapping(),
                blockModels.modelOutput
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