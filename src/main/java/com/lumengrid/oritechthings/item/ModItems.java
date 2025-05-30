package com.lumengrid.oritechthings.item;

import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.item.custom.AdvancedTargetDesignator;
import com.lumengrid.oritechthings.item.custom.FramePlacer;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
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

        public static final DeferredItem<Item> FRAME_PLACER = ITEMS.register("frame_placer",
                        () -> new FramePlacer(new Item.Properties().stacksTo(1)));

        public static final DeferredItem<Item> ADVANCED_TARGET_DESIGNATOR = ITEMS.register("advanced_target_designator",
                        () -> new AdvancedTargetDesignator(new Item.Properties().stacksTo(1)));

        public static final DeferredItem<Item> AMETHYST_FISH_SPAWN_EGG = ITEMS.register("amethyst_fish_spawn_egg",
                        () -> new DeferredSpawnEggItem(ModEntities.AMETHYST_FISH, 0xed47a8, 0xfa92cf,
                                        new Item.Properties()));
}