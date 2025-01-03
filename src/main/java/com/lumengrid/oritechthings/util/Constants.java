package com.lumengrid.oritechthings.util;

import com.lumengrid.oritechthings.block.ModBlocks;

import net.neoforged.neoforge.registries.DeferredBlock;

public class Constants {

    public static DeferredBlock<?>[] SPEED = {
        ModBlocks.ADDON_BLOCK_SPEED_TIER_2,
            ModBlocks.ADDON_BLOCK_SPEED_TIER_3,
            ModBlocks.ADDON_BLOCK_SPEED_TIER_4,
            ModBlocks.ADDON_BLOCK_SPEED_TIER_5,
            ModBlocks.ADDON_BLOCK_SPEED_TIER_6,
            ModBlocks.ADDON_BLOCK_SPEED_TIER_7,
            ModBlocks.ADDON_BLOCK_SPEED_TIER_8,
            ModBlocks.ADDON_BLOCK_SPEED_TIER_9,
    };
    public static DeferredBlock<?>[] EFFICIENT = {
        ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_2,
        ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_3,
        ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_4,
        ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_5,
        ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_6,
        ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_7,
        ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_8,
        ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_9,
    };
    public static DeferredBlock<?>[] EFFICIENCY = {
        ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_2,
        ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_3,
        ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_4,
        ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_5,
        ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_6,
        ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_7,
        ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_8,
        ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_9,
    };

    public static int size = SPEED.length+EFFICIENCY.length+EFFICIENT.length;

    public static DeferredBlock<?>[] getAll(){
        DeferredBlock<?>[] result = new DeferredBlock<?>[size];
        System.arraycopy(SPEED, 0, result, 0, SPEED.length);
        System.arraycopy(EFFICIENCY, 0, result, SPEED.length, EFFICIENCY.length);
        System.arraycopy(EFFICIENT, 0, result, SPEED.length + EFFICIENCY.length, EFFICIENT.length);
            return result;
    }

}
