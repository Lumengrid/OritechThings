package com.lumengrid.oritechthings.main;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.entity.client.AmethystFishRenderer;
import com.lumengrid.oritechthings.event.RenderWorldLastEvent;
import com.lumengrid.oritechthings.item.ModCreativeModeTabs;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.menu.ModMenuTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.common.Forge;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraftforge.fml.eventbus.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod(OritechThings.MOD_ID)
public class OritechThings
{
    public static final String MOD_ID = "oritechthings";
    public static final Logger LOGGER = LogUtils.getLogger();

    public OritechThings(IEventBus modEventBus, ModContainer modContainer)
    {
        ConfigLoader.getInstance().load();
        ModCreativeModeTabs.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);
    }


    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.AMETHYST_FISH.get(), AmethystFishRenderer::new);
            Forge.EVENT_BUS.register(RenderWorldLastEvent.class);
        }
    }
}
