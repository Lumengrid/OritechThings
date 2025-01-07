package com.lumengrid.oritechthings.main;

import com.lumengrid.oritechthings.entity.ModBlockEntities;
import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.item.ModCreativeModeTabs;
import com.lumengrid.oritechthings.item.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@SuppressWarnings("unused")
@Mod(OritechThings.MOD_ID)
public class OritechThings
{
    public static final String MOD_ID = "oritechthings";
    private static final Logger LOGGER = LogUtils.getLogger();

    public OritechThings(IEventBus modEventBus, ModContainer modContainer)
    {
        ConfigLoader.getInstance().load();

        ModCreativeModeTabs.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.MOD_ENTITIES.register(modEventBus);
    }
}
