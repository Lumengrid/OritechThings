package com.lumengrid.oritechthings.item;

import com.lumengrid.oritechthings.entity.custom.AcceleratorMagneticFieldBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.init.ComponentContent;
import rearth.oritech.util.TooltipHelper;

import java.util.List;

public class AcceleratorMagneticFieldBlockItem extends BlockItem {

    public AcceleratorMagneticFieldBlockItem(Block block, Properties settings) {
        super(block, settings.component(ComponentContent.ENERGY, 0));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        int storedEnergy = stack.getOrDefault(ComponentContent.ENERGY, 0);
        return storedEnergy > 0;
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 0xFF7007;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        long capacity = AcceleratorMagneticFieldBlockEntity.BASE_ENERGY_CAPACITY;
        int fillAmount = stack.getOrDefault(ComponentContent.ENERGY, 0);

        if (fillAmount <= 0) {
            return 0;
        }
        if (fillAmount >= capacity) {
            return 13;
        }

        return Math.round((fillAmount * 13.0f) / capacity);
    }
}