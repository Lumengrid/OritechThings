package com.lumengrid.oritechthings.item;

import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.item.custom.AdvancedTargetDesignator;
import com.lumengrid.oritechthings.item.custom.FramePlacer;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

        public static void register(IEventBus bus) {
                ITEMS.register(bus);
                ADDONS.register(bus);
                BLOCKITEMS.register(bus);
        }

        public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OritechThings.MOD_ID);
        public static final DeferredRegister.Items ADDONS = DeferredRegister.createItems(OritechThings.MOD_ID);
        public static final DeferredRegister.Items BLOCKITEMS = DeferredRegister.createItems(OritechThings.MOD_ID);

        public static final DeferredItem<Item> FRAME_PLACER = ITEMS.registerItem("frame_placer",
                properties -> new FramePlacer(properties.stacksTo(1)));

        public static final DeferredItem<Item> ADVANCED_TARGET_DESIGNATOR = ITEMS.registerItem("advanced_target_designator",
                properties -> new AdvancedTargetDesignator(properties.stacksTo(1)));

        public static final DeferredItem<Item> AMETHYST_FISH_SPAWN_EGG = ITEMS.registerItem("amethyst_fish_spawn_egg",
                properties -> new SpawnEggItem(
                        properties.spawnEgg(ModEntities.AMETHYST_FISH.get())
                )
        );
}