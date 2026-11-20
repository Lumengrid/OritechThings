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

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(CHANNEL_NAME,
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    private static int id = 0;

    static {
        CHANNEL.registerMessage(id++, UpdateSpeedSensorC2SPacket.class,
                (msg, buf) -> msg.toBytes(buf),
                UpdateSpeedSensorC2SPacket::fromBytes,
                UpdateSpeedSensorC2SPacket::handleDataOnServer);

        CHANNEL.registerMessage(id++, FramePlacerPacket.class,
                (msg, buf) -> msg.toBytes(buf),
                FramePlacerPacket::fromBytes,
                FramePlacerPacket::handleDataOnServer);
        }

    /** Forces the static initializer (channel + messages) to run during mod construction. */
    public static void init() {
    }
}
