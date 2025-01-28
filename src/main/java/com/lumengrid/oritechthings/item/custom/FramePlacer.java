package com.lumengrid.oritechthings.item.custom;

import com.lumengrid.oritechthings.client.screen.ScreenOpener;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.block.base.entity.FrameInteractionBlockEntity;
import rearth.oritech.util.Geometry;
import java.util.List;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static rearth.oritech.block.base.block.MultiblockMachine.ASSEMBLED;

public class FramePlacer extends Item {
    public FramePlacer(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand handIn) {
        if (!level.isClientSide()) {
            return InteractionResultHolder.pass(player.getItemInHand(handIn));
        }
        HitResult hit = player.pick(5.0, 1.0F, false);
        BlockHitResult hitResult = (BlockHitResult) hit;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hitResult.getBlockPos();
            BlockEntity targetEntity = level.getBlockEntity(pos);
            if (targetEntity instanceof FrameInteractionBlockEntity entity) {
                BlockState targetState = level.getBlockState(pos);
                if (targetState.getValue(ASSEMBLED)) {
                    Vec3i backRelative = new Vec3i(entity.getFrameOffset(), 0, 0);
                    Direction facing = targetState.getValue(FACING);
                    BlockPos searchStart = (BlockPos) Geometry.offsetToWorldPosition(facing, backRelative, pos);
                    ScreenOpener.openFramePlacer(searchStart, facing.getOpposite());
                    return InteractionResultHolder.success(player.getItemInHand(handIn));
                } else {
                    player.displayClientMessage(Component.translatable("message.oritechthings.frame_placer.not_assembled").withStyle(ChatFormatting.RED), true);
                }
            } else {
                player.displayClientMessage(Component.translatable("message.oritechthings.frame_placer.wrong_machine").withStyle(ChatFormatting.RED), true);
            }
        }
        return InteractionResultHolder.fail(player.getItemInHand(handIn));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltip, @NotNull TooltipFlag type) {
        tooltip.add(Component.translatable("tooltip.oritechthings.frame_placer").withStyle(ChatFormatting.ITALIC));
    }
}
