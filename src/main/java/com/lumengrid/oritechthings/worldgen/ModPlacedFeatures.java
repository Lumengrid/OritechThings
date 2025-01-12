package com.lumengrid.oritechthings.worldgen;

import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> INFESTED_AMETHYST_BLOCK_PLACED_KEY = registerKey("infested_amethyst_block_placed");

    public static List<PlacementModifier> replacePlacement(int pChance, PlacementModifier pHeightRange) {
        return List.of(
                RarityFilter.onAverageOnceEvery(pChance),
                pHeightRange
        );
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {

    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
