package com.lumengrid.oritechthings.network.packet;

import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record UpdateSpeedSensorC2SPacket(BlockPos pos, int speed, boolean active,
                                         boolean checkGreater, boolean automaticMode) {
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(speed);
        buf.writeBoolean(active);
        buf.writeBoolean(checkGreater);
        buf.writeBoolean(automaticMode);
    }

    public static UpdateSpeedSensorC2SPacket fromBytes(FriendlyByteBuf buf) {
        return new UpdateSpeedSensorC2SPacket(buf.readBlockPos(), buf.readInt(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
    }

    public static void handleDataOnServer(final UpdateSpeedSensorC2SPacket packet, final Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.setPacketHandled(true);
        context.enqueueWork(() -> {
            ServerPlayer sPlayer = context.getSender();
            if (sPlayer == null) return;
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
