package com.lumengrid.oritechthings.network.packet;

import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.IPayloadContext;

public record UpdateSpeedSensorC2SPacket(BlockPos pos, int speed, boolean active,
                                         boolean checkGreater, boolean automaticMode) {
    public static final StreamCodec<ByteBuf, UpdateSpeedSensorC2SPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            UpdateSpeedSensorC2SPacket::pos,
            ByteBufCodecs.INT,
            UpdateSpeedSensorC2SPacket::speed,
            ByteBufCodecs.BOOL,
            UpdateSpeedSensorC2SPacket::active,
            ByteBufCodecs.BOOL,
            UpdateSpeedSensorC2SPacket::checkGreater,
            ByteBufCodecs.BOOL,
            UpdateSpeedSensorC2SPacket::automaticMode,
            UpdateSpeedSensorC2SPacket::new
    );

    public FriendlyByteBuf toBytes(FriendlyByteBuf buf) {
        STREAM_CODEC.encode(buf, this);
        return buf;
    }

    public static UpdateSpeedSensorC2SPacket fromBytes(FriendlyByteBuf buf) {
        return STREAM_CODEC.decode(buf);
    }

    public static void handleDataOnServer(final UpdateSpeedSensorC2SPacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer sPlayer)) return;
            ServerLevel serverLevel = sPlayer.serverLevel();

            BlockEntity e = serverLevel.getBlockEntity(packet.pos());

            if (!(e instanceof AcceleratorSpeedSensorBlockEntity be)) return;

            be.setSpeedLimit(packet.speed());
            be.setEnabled(packet.active());
            be.setCheckGreater(packet.checkGreater());
            be.setAutomaticMode(packet.automaticMode());
        });
    }
}
