package com.lumengrid.oritechthings.network.packet;

import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.lumengrid.oritechthings.main.OritechThings;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateSpeedSensorC2SPacket(BlockPos pos, float speed, boolean active) implements CustomPacketPayload {
    public static final Type<UpdateSpeedSensorC2SPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, "update_sensor"));

    public static final StreamCodec<ByteBuf, UpdateSpeedSensorC2SPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC,
        UpdateSpeedSensorC2SPacket::pos,
        ByteBufCodecs.FLOAT,
        UpdateSpeedSensorC2SPacket::speed,
        ByteBufCodecs.BOOL,
        UpdateSpeedSensorC2SPacket::active,
        UpdateSpeedSensorC2SPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleDataOnServer(final UpdateSpeedSensorC2SPacket packet, final IPayloadContext context) {
        if(!(context.player() instanceof ServerPlayer sPlayer)) return;
        ServerLevel serverLevel = sPlayer.serverLevel();

        BlockEntity e = serverLevel.getBlockEntity(packet.pos());

        if(!(e instanceof AcceleratorSpeedSensorBlockEntity be)) return;

        be.increaseSpeedLimit(packet.speed());
        be.setEnabled(packet.active());
    }
}
