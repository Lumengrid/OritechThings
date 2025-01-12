package com.lumengrid.oritechthings.worldgen;

import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> CUSTOM_GEODE_PLACED_KEY = registerKey("custom_geode_placed");

    public static List<PlacementModifier> replacePlacement(int pChance, PlacementModifier pHeightRange) {
        return List.of(
                RarityFilter.onAverageOnceEvery(pChance),
                pHeightRange
        );
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(
                context,
                CUSTOM_GEODE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.CUSTOM_GEODE),
                replacePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)))
        );
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
