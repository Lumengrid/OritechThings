package com.lumengrid.oritechthings.item;

import com.lumengrid.oritechthings.entity.custom.AcceleratorMagneticFieldBlockEntity;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.api.energy.EnergyApi;
import rearth.oritech.api.energy.containers.SimpleEnergyItemStorage;
import rearth.oritech.util.TooltipHelper;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

public class AcceleratorMagneticFieldBlockItem extends BlockItem implements EnergyApi.ItemProvider {
    
    public AcceleratorMagneticFieldBlockItem(Block block, Properties settings) {
        super(block, settings);
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag type) {
        long storedEnergy = stack.getOrDefault(EnergyApi.ITEM.getEnergyComponent(), 0L);
        
        if (storedEnergy != 0) {
            var text = Component.translatable("tooltip.oritech.energy_stored", TooltipHelper.getEnergyText(storedEnergy));
            tooltip.add(text.withStyle(ChatFormatting.GOLD));
        }
        
        super.appendHoverText(stack, context, tooltip, type);
    }
    
    @Override
    public boolean isBarVisible(ItemStack stack) {
        var contentEmpty = stack.getOrDefault(EnergyApi.ITEM.getEnergyComponent(), 0L) <= 0;
        return !contentEmpty;
    }
    
    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 0xff7007; // Orange color for energy bar
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        var capacity = AcceleratorMagneticFieldBlockEntity.BASE_ENERGY_CAPACITY;
        long fillAmount = stack.getOrDefault(EnergyApi.ITEM.getEnergyComponent(), 0L);
        if (fillAmount <= 0) {
            return 0;
        }
        if (fillAmount >= capacity) {
            return MAX_BAR_WIDTH / 100;
        }
        return Math.round((fillAmount * 100f / capacity) * MAX_BAR_WIDTH) / 100;
    }
    
    @Override
    public EnergyApi.EnergyStorage getEnergyStorage(ItemStack stack) {
        return new SimpleEnergyItemStorage(
            AcceleratorMagneticFieldBlockEntity.BASE_ENERGY_INSERTION, 
            AcceleratorMagneticFieldBlockEntity.BASE_ENERGY_EXTRACTION, 
            AcceleratorMagneticFieldBlockEntity.BASE_ENERGY_CAPACITY, 
            stack
        );
    }
}
