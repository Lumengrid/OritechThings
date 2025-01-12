package com.lumengrid.oritechthings.worldgen;

import com.lumengrid.oritechthings.block.ModBlocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class CustomGeodeFeature {
    public static GeodeConfiguration config() {
        GeodeBlockSettings geodeBlockSettings = new GeodeBlockSettings(
                BlockStateProvider.simple(Blocks.AIR),
                BlockStateProvider.simple(Blocks.AMETHYST_BLOCK),
                BlockStateProvider.simple(Blocks.BUDDING_AMETHYST),
                BlockStateProvider.simple(ModBlocks.INFESTED_AMETHYST_BLOCK.get()),
                BlockStateProvider.simple(Blocks.OBSIDIAN),
                List.of(
                        Blocks.MEDIUM_AMETHYST_BUD.defaultBlockState(),
                        Blocks.LARGE_AMETHYST_BUD.defaultBlockState(),
                        Blocks.AMETHYST_CLUSTER.defaultBlockState()
                ),
                BlockTags.FEATURES_CANNOT_REPLACE,
                BlockTags.GEODE_INVALID_BLOCKS
        );

        return new GeodeConfiguration(
                geodeBlockSettings,
                new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
                new GeodeCrackSettings(0.95, 2.0, 2),
                0.35,
                0.083,
                true,
                UniformInt.of(4, 6),
                UniformInt.of(3, 4),
                UniformInt.of(1, 2),
                -16,
                16,
                0.05,
                1
        );
    }
}
