package com.lumengrid.oritechthings.network.packet;

import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.lumengrid.oritechthings.main.OritechThings;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record UpdateSpeedSensorC2SPacket(
        BlockPos pos,
        int speed,
        boolean active,
        boolean checkGreater,
        boolean automaticMode
) implements CustomPacketPayload {

    public static final Type<UpdateSpeedSensorC2SPacket> TYPE =
            new Type<>(
                    Identifier.fromNamespaceAndPath(
                            OritechThings.MOD_ID,
                            "update_speed_sensor"
                    )
            );

    public static final StreamCodec<
            ByteBuf,
            UpdateSpeedSensorC2SPacket
            > STREAM_CODEC = StreamCodec.composite(
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

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleDataOnServer(
            UpdateSpeedSensorC2SPacket packet,
            IPayloadContext context
    ) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }

        context.enqueueWork(() -> {
            ServerLevel level = player.level();


            BlockEntity blockEntity =
                    level.getBlockEntity(packet.pos());

            if (!(blockEntity
                    instanceof AcceleratorSpeedSensorBlockEntity sensor)) {
                return;
            }

            sensor.setSpeedLimit(packet.speed());
            sensor.setEnabled(packet.active());
            sensor.setCheckGreater(packet.checkGreater());
            sensor.setAutomaticMode(packet.automaticMode());
        });
    }
}
