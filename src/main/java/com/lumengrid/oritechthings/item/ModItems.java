package com.lumengrid.oritechthings.item;

import com.lumengrid.oritechthings.item.custom.AcceleratorTargetDesignator;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OritechThings.MOD_ID);

    public static final DeferredItem<Item> ACCELERATOR_TARGET_DESIGNATOR = ITEMS.register("accelerator_target_designator",
            () -> new AcceleratorTargetDesignator(new Item.Properties().stacksTo(1)));
}