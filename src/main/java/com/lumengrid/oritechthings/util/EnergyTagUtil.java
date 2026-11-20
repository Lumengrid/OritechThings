package com.lumengrid.oritechthings.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

/**
 * Stores the magnetic field's energy in the item NBT tag, replacing the
 * data-component based approach (Data Components do not exist in 1.20.1).
 *
 * TODO(1.20.1 oritech swap): if the oritech 1.20.1 build has a capability-based
 * item energy API, replace this NBT convention with it.
 */
public class EnergyTagUtil {
    public static final String TAG = "oritechthings";
    private static final String ENERGY = "Energy";

    public static void setEnergy(ItemStack stack, long amount) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag inner = tag.contains(TAG) ? tag.getCompound(TAG) : new CompoundTag();
        inner.putLong(ENERGY, amount);
        tag.put(TAG, inner);
    }

    public static long getEnergy(ItemStack stack) {
        if (!stack.hasTag()) return 0L;
        CompoundTag tag = stack.getTag();
        if (!tag.contains(TAG)) return 0L;
        return tag.getCompound(TAG).getLong(ENERGY);
    }
}
