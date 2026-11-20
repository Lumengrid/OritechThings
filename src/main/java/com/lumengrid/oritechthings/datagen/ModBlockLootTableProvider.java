package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.util.Constants;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredBlock;
import net.minecraftforge.registries.DeferredHolder;
import net.minecraftforge.registries.DeferredRegister;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.DEFAULT.allFlags());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
                blocks.addAll(getList(ModBlocks.ADDONS));
                blocks.addAll(getList(ModBlocks.OTHER));
                return blocks;
    }

    @SuppressWarnings("unchecked")
        private List<Block> getList(DeferredRegister.Blocks blocks) {
                return (List<Block>) blocks.getEntries().stream().map(DeferredHolder::get).toList();
        }

    @Override
    protected void generate() {

        for (DeferredBlock<?> data : Constants.getAllAddons()) {
            dropSelf(data.get());
        }
        
        dropSelf(ModBlocks.ACCELERATOR_SPEED_SENSOR.get());
        dropSelf(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get());
        dropOther(ModBlocks.INFESTED_AMETHYST_BLOCK.get(), Blocks.AMETHYST_BLOCK.asItem());
    }


}
