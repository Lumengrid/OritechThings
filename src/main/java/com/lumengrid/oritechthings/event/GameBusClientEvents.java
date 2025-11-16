package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.client.renderer.AcceleratorSpeedSensorBlockEntityRender;
import com.lumengrid.oritechthings.client.screen.AcceleratorSpeedSensorScreen;
import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.menu.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = OritechThings.MOD_ID, value = Dist.CLIENT)
public class GameBusClientEvents {

    @SubscribeEvent
    public static void clientSetup(final RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.SPEED_SENSOR_MENU.get(), AcceleratorSpeedSensorScreen::new);
    }

    @SubscribeEvent
    public static void onBlockEntityRender(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModEntities.accelerator_speed_sensor.get(),
                AcceleratorSpeedSensorBlockEntityRender::new);
    }
}
