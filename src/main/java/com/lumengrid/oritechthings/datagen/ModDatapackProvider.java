package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.worldgen.ModBiomeModifiers;
import com.lumengrid.oritechthings.worldgen.ModConfiguredFeatures;
import com.lumengrid.oritechthings.worldgen.ModPlacedFeatures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder(FeatureFlags.DEFAULT)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    public ModDatapackProvider(PackOutput output) {
        super(output, BUILDER, Set.of(OritechThings.MOD_ID));
    }
}
