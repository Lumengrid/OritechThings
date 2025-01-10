package com.lumengrid.oritechthings.network;

import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.network.packet.UpdateSpeedSensorC2SPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = OritechThings.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModNetwork {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
            UpdateSpeedSensorC2SPacket.TYPE,
            UpdateSpeedSensorC2SPacket.STREAM_CODEC,
            UpdateSpeedSensorC2SPacket::handleDataOnServer
        );
    }

}