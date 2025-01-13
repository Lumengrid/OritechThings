package com.lumengrid.oritechthings.network.packet;

import com.lumengrid.oritechthings.main.OritechThings;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.init.BlockContent;

public record FramePlacerPacket(BlockPos startPos, int xDimension, int yDimension, int offset,
                                Direction facing) implements CustomPacketPayload {
    public static final Type<FramePlacerPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, "frame_placer"));

    public static final StreamCodec<ByteBuf, FramePlacerPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            FramePlacerPacket::startPos,
            ByteBufCodecs.INT,
            FramePlacerPacket::xDimension,
            ByteBufCodecs.INT,
            FramePlacerPacket::yDimension,
            ByteBufCodecs.INT,
            FramePlacerPacket::offset,
            Direction.STREAM_CODEC,
            FramePlacerPacket::facing,
            FramePlacerPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleDataOnServer(final FramePlacerPacket packet, final IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer sPlayer)) return;
        ServerLevel level = sPlayer.serverLevel();
        BlockPos startPos = getOffsetPosition(packet.facing, packet.startPos, -packet.offset, 0);
        BlockPos playerPos = sPlayer.blockPosition();
        Direction playerFacing = sPlayer.getDirection();
        for (int x = 0; x < packet.xDimension; x++) {
            for (int y = 0; y < packet.yDimension; y++) {
                BlockPos currentPos = getOffsetPosition(packet.facing, startPos, x, y);
                BlockState state = level.getBlockState(currentPos);
                boolean isPerimeter = isPerimeter(x, y, packet.xDimension, packet.yDimension);
                if (isPerimeter && state.is(BlockContent.MACHINE_FRAME_BLOCK)) {
                    continue;
                }
                if (!state.isAir()) {
                    level.destroyBlock(currentPos, false);
                    dropBlockInFrontOfPlayer(level, playerPos, playerFacing, state);
                }
                if (isPerimeter && !placeFrameBlock(sPlayer, level, currentPos)) {
                    return;
                }
            }
        }
    }

    private static void dropBlockInFrontOfPlayer(ServerLevel level, BlockPos playerPos, Direction playerFacing, BlockState blockState) {
        ItemStack blockItem = new ItemStack(blockState.getBlock());
        BlockPos dropPos = playerPos.offset(playerFacing.getNormal());
        dropPos = dropPos.above();
        ItemEntity itemEntity = new ItemEntity(level, dropPos.getX(), dropPos.getY(), dropPos.getZ(), blockItem);
        level.addFreshEntity(itemEntity);
    }

    private static BlockPos getOffsetPosition(Direction facing, BlockPos startPos, int x, int y) {
        return switch (facing) {
            case NORTH -> startPos.offset(x, 0, -y);
            case WEST -> startPos.offset(-y, 0, -x);
            case EAST -> startPos.offset(y, 0, x);
            case SOUTH -> startPos.offset(-x, 0, y);
            default -> startPos.offset(x, 0, y);
        };
    }

    private static boolean isPerimeter(int x, int y, int xDimension, int yDimension) {
        return x == 0 || x == xDimension - 1 || y == 0 || y == yDimension - 1;
    }

    private static boolean placeFrameBlock(ServerPlayer player, ServerLevel level, BlockPos pos) {
        if (player.isCreative()) {
            BlockState frameBlockState = BlockContent.MACHINE_FRAME_BLOCK.defaultBlockState();
            level.setBlockAndUpdate(pos, frameBlockState);
            return true;
        }
        ItemStack frameBlockItem = findFrameBlockInInventory(player);
        if (frameBlockItem.isEmpty()) {
            player.sendSystemMessage(Component.translatable("message.oritechthings.frame_placer.missing_frame")
                    .withStyle(ChatFormatting.RED));
            return false;
        }
        BlockState frameBlockState = Block.byItem(frameBlockItem.getItem()).defaultBlockState();
        level.setBlockAndUpdate(pos, frameBlockState);
        frameBlockItem.shrink(1);
        return true;
    }

    private static ItemStack findFrameBlockInInventory(ServerPlayer player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == BlockContent.MACHINE_FRAME_BLOCK.asItem()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
