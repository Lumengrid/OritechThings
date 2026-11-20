package com.lumengrid.oritechthings.item;

import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.item.custom.AdvancedTargetDesignator;
import com.lumengrid.oritechthings.item.custom.FramePlacer;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ModItems {

        public static void register(IEventBus bus) {
                ITEMS.register(bus);
                ADDONS.register(bus);
                BLOCKITEMS.register(bus);
        }

        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(net.minecraftforge.registries.ForgeRegistries.ITEMS, OritechThings.MOD_ID);
        public static final DeferredRegister<Item> ADDONS = DeferredRegister.create(net.minecraftforge.registries.ForgeRegistries.ITEMS, OritechThings.MOD_ID);
        public static final DeferredRegister<Item> BLOCKITEMS = DeferredRegister.create(net.minecraftforge.registries.ForgeRegistries.ITEMS, OritechThings.MOD_ID);

        public static final RegistryObject<Item> FRAME_PLACER = ITEMS.register("frame_placer",
                        () -> new FramePlacer(new Item.Properties().stacksTo(1)));

        public static final RegistryObject<Item> ADVANCED_TARGET_DESIGNATOR = ITEMS.register("advanced_target_designator",
                        () -> new AdvancedTargetDesignator(new Item.Properties().stacksTo(1)));

        public static final RegistryObject<Item> AMETHYST_FISH_SPAWN_EGG = ITEMS.register("amethyst_fish_spawn_egg",
                        () -> new ForgeSpawnEggItem(ModEntities.AMETHYST_FISH, 0xed47a8, 0xfa92cf,
                                        new Item.Properties()));
}