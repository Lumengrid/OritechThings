package com.lumengrid.oritechthings.worldgen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> INFESTED_AMETHYST_BLOCK_KEY = registerKey("infested_amethyst_block");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        BlockState infestedAmethystBlockState = ModBlocks.INFESTED_AMETHYST_BLOCK.get().defaultBlockState();
        register(context, INFESTED_AMETHYST_BLOCK_KEY, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(infestedAmethystBlockState)));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
