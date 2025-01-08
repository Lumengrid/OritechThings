package com.lumengrid.oritechthings.item.custom;

import com.lumengrid.oritechthings.block.custom.AcceleratorSpeedSensorBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.block.blocks.accelerator.AcceleratorControllerBlock;
import rearth.oritech.init.ComponentContent;
import rearth.oritech.item.tools.LaserTargetDesignator;

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
}
