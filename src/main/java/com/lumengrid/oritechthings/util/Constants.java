package com.lumengrid.oritechthings.util;

import com.lumengrid.oritechthings.block.ModBlocks;

import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

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
    public static DeferredBlock<?>[] CAPACITOR = {
            ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_2,
            ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_3,
            ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_4,
            ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_5,
            ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_6,
            ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_7,
            ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_8,
            ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_9,
    };
    public static DeferredBlock<?>[] ACCEPTOR = {
            ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_2,
            ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_3,
            ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_4,
            ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_5,
            ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_6,
            ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_7,
            ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_8,
            ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_9,
    };
    public static DeferredBlock<?>[] PROCESSING = {
            ModBlocks.ADDON_BLOCK_PROCESSING_TIER_2,
            ModBlocks.ADDON_BLOCK_PROCESSING_TIER_3,
            ModBlocks.ADDON_BLOCK_PROCESSING_TIER_4,
            ModBlocks.ADDON_BLOCK_PROCESSING_TIER_5,
            ModBlocks.ADDON_BLOCK_PROCESSING_TIER_6,
            ModBlocks.ADDON_BLOCK_PROCESSING_TIER_7,
            ModBlocks.ADDON_BLOCK_PROCESSING_TIER_8,
            ModBlocks.ADDON_BLOCK_PROCESSING_TIER_9,
    };

    public static int size = SPEED.length+EFFICIENCY.length+EFFICIENT.length+CAPACITOR.length+ACCEPTOR.length+PROCESSING.length;

    public static DeferredBlock<?>[] getAll(){
        DeferredBlock<?>[] result = new DeferredBlock<?>[size];
        System.arraycopy(SPEED, 0, result, 0, SPEED.length);
        System.arraycopy(EFFICIENCY, 0, result, SPEED.length, EFFICIENCY.length);
        System.arraycopy(EFFICIENT, 0, result, SPEED.length + EFFICIENCY.length, EFFICIENT.length);
        System.arraycopy(CAPACITOR, 0, result, SPEED.length + EFFICIENCY.length + EFFICIENT.length, CAPACITOR.length);
        System.arraycopy(ACCEPTOR, 0, result, SPEED.length + EFFICIENCY.length + EFFICIENT.length + CAPACITOR.length, ACCEPTOR.length);
        System.arraycopy(PROCESSING, 0, result, SPEED.length + EFFICIENCY.length + EFFICIENT.length + CAPACITOR.length+ ACCEPTOR.length, PROCESSING.length);
            return result;
    }


    public enum AddonType implements StringRepresentable {
        SPEED("speed"),
        EFFICIENCY("efficiency"),
        EFFICIENT_SPEED("efficient_speed"),
        PROCESSING("processing"),
        ACCEPTOR("acceptor"),
        CAPACITOR("capacitor");

        private final String name;

        private AddonType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.getSerializedName();
        }

        public @NotNull String getSerializedName() {
            return this.name;
        }
    }

}
