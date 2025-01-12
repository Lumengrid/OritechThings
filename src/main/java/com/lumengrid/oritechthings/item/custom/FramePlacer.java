package com.lumengrid.oritechthings.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.block.base.entity.FrameInteractionBlockEntity;
import rearth.oritech.util.Geometry;
import java.util.List;
import java.util.Objects;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static rearth.oritech.block.base.block.MultiblockMachine.ASSEMBLED;

public class FramePlacer extends Item {
    public FramePlacer(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        BlockEntity targetEntity = level.getBlockEntity(pos);
        if (!(targetEntity instanceof FrameInteractionBlockEntity entity)) {
            Objects.requireNonNull(context.getPlayer()).sendSystemMessage(
                    Component.translatable("message.oritechthings.frame_placer.wrong_machine")
                            .withStyle(ChatFormatting.RED));
            return InteractionResult.FAIL;
        }
        BlockState targetState = level.getBlockState(pos);
        if (!targetState.getValue(ASSEMBLED)) {
            Objects.requireNonNull(context.getPlayer()).sendSystemMessage(
                    Component.translatable("message.oritechthings.frame_placer.not_assembled")
                            .withStyle(ChatFormatting.RED));
            return InteractionResult.FAIL;
        }
        Vec3i backRelative = new Vec3i(entity.getFrameOffset(), 0, 0);
        BlockPos searchStart = (BlockPos) Geometry.offsetToWorldPosition(targetState.getValue(FACING), backRelative, pos);
        System.out.println("frame entity detected " + searchStart);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltip, @NotNull TooltipFlag type) {
        tooltip.add(Component.translatable("tooltip.oritechthings.frame_placer").withStyle(ChatFormatting.ITALIC));
    }
}
