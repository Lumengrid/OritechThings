package com.lumengrid.oritechthings.item.custom;

import com.lumengrid.oritechthings.client.screen.ScreenOpener;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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

    public FramePlacer(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(
            @NotNull Level level,
            @NotNull Player player,
            @NotNull InteractionHand hand
    ) {
        if (!level.isClientSide()) {
            return InteractionResult.PASS;
        }

        HitResult hit = player.pick(5.0D, 1.0F, false);

        if (!(hit instanceof BlockHitResult hitResult)) {
            return InteractionResult.FAIL;
        }

        BlockPos pos = hitResult.getBlockPos();
        BlockEntity targetEntity = level.getBlockEntity(pos);

        if (!(targetEntity instanceof FrameInteractionBlockEntity entity)) {
            // ✅ Sostituito displayClientMessage con sendSystemMessage
            player.sendSystemMessage(
                    Component.translatable(
                            "message.oritechthings.frame_placer.wrong_machine"
                    ).withStyle(ChatFormatting.RED)
            );

            return InteractionResult.FAIL;
        }

        BlockState targetState = level.getBlockState(pos);

        if (!targetState.getValue(ASSEMBLED)) {
            // ✅ Sostituito displayClientMessage con sendSystemMessage
            player.sendSystemMessage(
                    Component.translatable(
                            "message.oritechthings.frame_placer.not_assembled"
                    ).withStyle(ChatFormatting.RED)
            );

            return InteractionResult.FAIL;
        }

        Vec3i backRelative = new Vec3i(entity.getFrameOffset(), 0, 0);
        Direction facing = targetState.getValue(FACING);

        Vec3i worldPosition =
                Geometry.offsetToWorldPosition(facing, backRelative, pos);

        BlockPos searchStart = BlockPos.containing(
                worldPosition.getX(),
                worldPosition.getY(),
                worldPosition.getZ()
        );

        ScreenOpener.openFramePlacer(
                searchStart,
                facing.getOpposite()
        );

        return InteractionResult.SUCCESS;
    }

    // todo appendhovertext @Override
    public void appendHoverText(
            @NotNull ItemStack stack,
            @NotNull TooltipContext context,
            List<Component> tooltip,
            @NotNull TooltipFlag flag
    ) {
        tooltip.add(
                Component.translatable(
                        "tooltip.oritechthings.frame_placer"
                ).withStyle(ChatFormatting.ITALIC)
        );
    }
}