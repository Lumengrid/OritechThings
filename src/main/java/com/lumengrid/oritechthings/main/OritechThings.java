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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import com.electronwill.nightconfig.core.CommentedConfig;
import rearth.oritech.init.OritechStartupConfig;

@SuppressWarnings("unused")
@Mod(OritechThings.MOD_ID)
public class OritechThings
{
    public static final String MOD_ID = "oritechthings";
    public static final Logger LOGGER = LogUtils.getLogger();

    public OritechThings()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        loadOritechStartupConfigInDev();
        ConfigLoader.getInstance().load();
        ModCreativeModeTabs.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);
        com.lumengrid.oritechthings.network.ModNetwork.init();
    }


    // Oritech reads OritechStartupConfig while registering its blocks, before Forge has loaded that spec (always the
    // case in runData). Outside production Forge throws on that read, so feed the spec its defaults first.
    private static void loadOritechStartupConfigInDev() {
        if (!FMLEnvironment.production && !OritechStartupConfig.STARTUP_SPEC.isLoaded()) {
            OritechStartupConfig.STARTUP_SPEC.acceptConfig(CommentedConfig.inMemory());
        }
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.AMETHYST_FISH.get(), AmethystFishRenderer::new);
            MinecraftForge.EVENT_BUS.register(RenderWorldLastEvent.class);
        }
    }
}
