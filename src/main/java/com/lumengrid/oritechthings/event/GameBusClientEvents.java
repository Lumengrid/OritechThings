package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.client.screen.AcceleratorSpeedSensorScreen;
import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.menu.ModMenuTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.EventBusSubscriber;
import net.minecraftforge.client.event.RegisterMenuScreensEvent;
import net.minecraftforge.client.event.ResourceReloadEvent;

@EventBusSubscriber(modid = OritechThings.MOD_ID, value = Dist.CLIENT)
public class GameBusClientEvents {

    @SubscribeEvent
    public static void clientSetup(final RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.SPEED_SENSOR_MENU.get(), AcceleratorSpeedSensorScreen::new);
    }

    /**
     * Forge 1.20.1 has no RegisterClientReloadListenersEvent, so the config is
     * reloaded when the game reloads its resources instead.
     */
    @SubscribeEvent
    public static void onResourcesReload(ResourceReloadEvent event) {
        ConfigLoader.getInstance().load();
    }
}
