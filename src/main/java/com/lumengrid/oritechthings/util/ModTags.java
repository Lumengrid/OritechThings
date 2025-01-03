package com.lumengrid.oritechthings.util;

import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> TIERED_ADDON_SPEED = createTag("tiered_addon_speed");
        public static final TagKey<Item> TIERED_ADDON_EFFICIENT_SPEED = createTag("tiered_addon_efficient_speed");
        public static final TagKey<Item> TIERED_ADDON_EFFICIENCY = createTag("tiered_addon_efficiency");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, name));
        }
    }
}
