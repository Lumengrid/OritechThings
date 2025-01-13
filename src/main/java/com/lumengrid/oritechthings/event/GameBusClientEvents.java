package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.client.screen.AcceleratorSpeedSensorScreen;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.menu.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = OritechThings.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GameBusClientEvents {

    @SubscribeEvent
    public static void clientSetup(final RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.SPEED_SENSOR_MENU.get(), AcceleratorSpeedSensorScreen::new);
    }
}
