package com.lumengrid.oritechthings.worldgen;

import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    public static void bootstrap(BootstapContext<BiomeModifier> context) {

    }

    @SuppressWarnings("unused")
    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(OritechThings.MOD_ID, name));
    }
}
