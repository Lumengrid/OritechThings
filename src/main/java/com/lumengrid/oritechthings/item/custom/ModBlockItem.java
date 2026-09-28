package com.lumengrid.oritechthings.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class ModBlockItem extends BlockItem {

    public ModBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            Item.TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> tooltipOutput,
            TooltipFlag flag
    ) {
        super.appendHoverText(stack, context, display, tooltipOutput, flag);

        if (getBlock() instanceof TooltipProvider provider) {
            provider.addToTooltip(context, tooltipOutput, flag, stack);
        }
    }
}