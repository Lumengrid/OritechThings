package com.lumengrid.oritechthings.util;

import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> TIERED_ADDON_PROCESSING = createTag("tiered_addon_processing");
        public static final TagKey<Item> TIERED_ADDON_PROCESSING = createItemTag("tiered_addon_processing");
        public static final TagKey<Item> TIERED_ADDON_ACCEPTOR = createItemTag("tiered_addon_acceptor");
        public static final TagKey<Item> TIERED_ADDON_CAPACITOR = createItemTag("tiered_addon_capacitor");
        public static final TagKey<Item> TIERED_ADDON_SPEED = createItemTag("tiered_addon_speed");
        public static final TagKey<Item> TIERED_ADDON_EFFICIENT_SPEED = createItemTag("tiered_addon_efficient_speed");
        public static final TagKey<Item> TIERED_ADDON_EFFICIENCY = createItemTag("tiered_addon_efficiency");
        public static final TagKey<Item> PARTICLE_ACCELERATOR = createItemTag("particle_accelerator");

    }

    public static class Blocks {
        public static final TagKey<Block> TIERED_ADDON_PROCESSING = createBlockTag("tiered_addon_processing");
        public static final TagKey<Block> TIERED_ADDON_ACCEPTOR = createBlockTag("tiered_addon_acceptor");
        public static final TagKey<Block> TIERED_ADDON_CAPACITOR = createBlockTag("tiered_addon_capacitor");
        public static final TagKey<Block> TIERED_ADDON_SPEED = createBlockTag("tiered_addon_speed");
        public static final TagKey<Block> TIERED_ADDON_EFFICIENT_SPEED = createBlockTag("tiered_addon_efficient_speed");
        public static final TagKey<Block> TIERED_ADDON_EFFICIENCY = createBlockTag("tiered_addon_efficiency");
        public static final TagKey<Block> PARTICLE_ACCELERATOR = createBlockTag("particle_accelerator");
    }

    private static TagKey<Block> createBlockTag(String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, name));
    }

    private static TagKey<Item> createItemTag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, name));
    }
}
