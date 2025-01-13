package com.lumengrid.oritechthings.item;

import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.item.custom.AcceleratorTargetDesignator;
import com.lumengrid.oritechthings.item.custom.FramePlacer;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OritechThings.MOD_ID);

    public static final DeferredItem<Item> ACCELERATOR_TARGET_DESIGNATOR = ITEMS.register("accelerator_target_designator",
            () -> new AcceleratorTargetDesignator(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> FRAME_PLACER = ITEMS.register("frame_placer",
            () -> new FramePlacer(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> AMETHYST_FISH_SPAWN_EGG = ITEMS.register("amethyst_fish_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.AMETHYST_FISH, 0xed47a8, 0xfa92cf,
                    new Item.Properties()));
}