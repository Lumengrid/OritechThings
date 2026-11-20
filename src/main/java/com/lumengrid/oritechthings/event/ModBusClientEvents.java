package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.client.renderer.AcceleratorSpeedSensorBlockEntityRender;
import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = OritechThings.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusClientEvents {

    @SubscribeEvent
    public static void onBlockEntityRender(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModEntities.accelerator_speed_sensor.get(),
                AcceleratorSpeedSensorBlockEntityRender::new);
    }
}
