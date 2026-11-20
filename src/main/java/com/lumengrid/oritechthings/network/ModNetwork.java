package com.lumengrid.oritechthings.network;

import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.network.packet.FramePlacerPacket;
import com.lumengrid.oritechthings.network.packet.UpdateSpeedSensorC2SPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Forge 1.20.1 networking: a SimpleChannel instead of the NeoForge payload system.
 */
public class ModNetwork {

    public static final String PROTOCOL_VERSION = "1";
    public static final ResourceLocation CHANNEL_NAME = new ResourceLocation(OritechThings.MOD_ID, "main");

    public static final SimpleChannel CHANNEL = NetworkRegistry.newChannel(CHANNEL_NAME, PROTOCOL_VERSION);

    static {
        CHANNEL.registerMessage(UpdateSpeedSensorC2SPacket.class,
                UpdateSpeedSensorC2SPacket::toBytes,
                UpdateSpeedSensorC2SPacket::fromBytes,
                UpdateSpeedSensorC2SPacket::handleDataOnServer);

        CHANNEL.registerMessage(FramePlacerPacket.class,
                FramePlacerPacket::toBytes,
                FramePlacerPacket::fromBytes,
                FramePlacerPacket::handleDataOnServer);
    }
}
