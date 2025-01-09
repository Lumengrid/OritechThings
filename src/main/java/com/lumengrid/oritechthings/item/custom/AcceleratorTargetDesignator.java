package com.lumengrid.oritechthings.item.custom;

import com.lumengrid.oritechthings.block.custom.AcceleratorSpeedSensorBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.block.blocks.accelerator.AcceleratorControllerBlock;
import rearth.oritech.init.ComponentContent;
import rearth.oritech.item.tools.LaserTargetDesignator;

import java.util.List;
import java.util.Objects;

public class AcceleratorTargetDesignator extends LaserTargetDesignator {
    public AcceleratorTargetDesignator(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            BlockState targetBlockState = context.getLevel().getBlockState(context.getClickedPos());
            if (targetBlockState.getBlock() instanceof AcceleratorControllerBlock) {
                context.getItemInHand().set(ComponentContent.TARGET_POSITION.get(), context.getClickedPos());
                Objects.requireNonNull(context.getPlayer()).sendSystemMessage(Component.translatable("message.oritech.target_designator.position_stored"));
                return InteractionResult.SUCCESS;
            }
            if (targetBlockState.getBlock() instanceof AcceleratorSpeedSensorBlock) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_target_designator").withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, context, tooltip, type);
    }
}
