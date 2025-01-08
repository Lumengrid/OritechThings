package com.lumengrid.oritechthings.item;

import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;
import com.lumengrid.oritechthings.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OritechThings.MOD_ID);

    public static final Supplier<CreativeModeTab> ORITECH_THINGS_TAB = CREATIVE_MODE_TAB.register(OritechThings.MOD_ID+"_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ADDON_BLOCK_SPEED_TIER_8.get()))
                    .title(Component.translatable("itemGroup."+OritechThings.MOD_ID))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.ACCELERATOR_SPEED_SENSOR.get());
                        for (DeferredBlock<?> data : Constants.getAllAddons()) {
                            output.accept(data.get());
                        }
                        output.accept(ModItems.ACCELERATOR_TARGET_DESIGNATOR);
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
