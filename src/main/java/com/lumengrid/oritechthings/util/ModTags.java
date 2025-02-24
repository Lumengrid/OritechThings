package com.lumengrid.oritechthings.util;

import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> TIERED_ADDON_PROCESSING = createTag("tiered_addon_processing");
        public static final TagKey<Item> TIERED_ADDON_ACCEPTOR = createTag("tiered_addon_acceptor");
        public static final TagKey<Item> TIERED_ADDON_CAPACITOR = createTag("tiered_addon_capacitor");
        public static final TagKey<Item> TIERED_ADDON_SPEED = createTag("tiered_addon_speed");
        public static final TagKey<Item> TIERED_ADDON_EFFICIENT_SPEED = createTag("tiered_addon_efficient_speed");
        public static final TagKey<Item> TIERED_ADDON_EFFICIENCY = createTag("tiered_addon_efficiency");
        public static final TagKey<Item> PARTICLE_ACCELERATOR = createTag("particle_accelerator");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, name));
        }
    }
}
