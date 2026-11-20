package com.lumengrid.oritechthings.item;

import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;
import com.lumengrid.oritechthings.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, OritechThings.MOD_ID);

    public static final Supplier<CreativeModeTab> ORITECH_THINGS_TAB = CREATIVE_MODE_TAB.register(OritechThings.MOD_ID + "_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.ADDON_BLOCK_SPEED_TIER_8.get()))
                    .title(Component.translatable("itemGroup." + OritechThings.MOD_ID))
                    .displayItems((params, stack) -> {
                        stack.accept(new ItemStack(ModBlocks.ACCELERATOR_SPEED_SENSOR.get()));
                        stack.accept(new ItemStack(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get()));
                        stack.accept(new ItemStack(ModBlocks.INFESTED_AMETHYST_BLOCK.get()));

                        //addons
                        for (var data : Constants.getAllAddons()) {
                            stack.accept(new ItemStack((net.minecraft.world.level.ItemLike) data.get()));
                        }

                        stack.accept(new ItemStack(ModItems.FRAME_PLACER.get()));
                        stack.accept(new ItemStack(ModItems.ADVANCED_TARGET_DESIGNATOR.get()));
                        stack.accept(new ItemStack(ModItems.AMETHYST_FISH_SPAWN_EGG.get()));
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
