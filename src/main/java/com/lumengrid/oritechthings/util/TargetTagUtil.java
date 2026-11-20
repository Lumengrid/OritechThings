package com.lumengrid.oritechthings.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * 1.20.1 has no data components (1.20.5+ API), so the target designator target
 * is stored in the item's NBT tag instead of a {@code DataComponentType}.
 */
public class TargetTagUtil {
    public static final String TAG = "oritechthings";
    private static final String TARGET_POS = "TargetPos";
    private static final String TARGET_DIM = "TargetDim";

    public static void saveTarget(ItemStack stack, @Nullable BlockPos pos, @Nullable ResourceKey<Level> dimension) {
        CompoundTag tag = stack.getOrCreateTag();
        if (pos == null && dimension == null) {
            tag.remove(TAG);
            return;
        }
        CompoundTag inner = tag.contains(TAG) ? tag.getCompound(TAG) : new CompoundTag();
        if (pos != null) {
            inner.putLong(TARGET_POS, pos.asLong());
        }
        if (dimension != null) {
            inner.putString(TARGET_DIM, dimension.location().toString());
        }
        tag.put(TAG, inner);
    }

    public static boolean hasTarget(ItemStack stack) {
        return getTargetPos(stack) != null;
    }

    public static @Nullable BlockPos getTargetPos(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return null;
        }
        CompoundTag inner = tag.getCompound(TAG);
        return inner.contains(TARGET_POS) ? BlockPos.of(inner.getLong(TARGET_POS)) : null;
    }

    public static @Nullable ResourceKey<Level> getTargetDim(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return null;
        }
        CompoundTag inner = tag.getCompound(TAG);
        String dim = inner.getString(TARGET_DIM);
        if (dim.isEmpty()) {
            return null;
        }
        return ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION, new ResourceLocation(dim));
    }
}
