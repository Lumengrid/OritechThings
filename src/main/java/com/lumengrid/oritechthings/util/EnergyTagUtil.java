package com.lumengrid.oritechthings.util;

import net.minecraft.world.item.ItemStack;
import rearth.oritech.api.energy.EnergyApi;

/**
 * Stored energy on item stacks. Uses Oritech's own energy component so the
 * value is shared with Oritech's item energy storage (SimpleEnergyItemStorage).
 */
public class EnergyTagUtil {

    public static void setEnergy(ItemStack stack, long amount) {
        EnergyApi.ITEM.getEnergyComponent().set(stack, amount);
    }

    public static long getEnergy(ItemStack stack) {
        var component = EnergyApi.ITEM.getEnergyComponent();
        if (!component.has(stack)) return 0L;
        Long value = component.get(stack);
        return value == null ? 0L : value;
    }
}
