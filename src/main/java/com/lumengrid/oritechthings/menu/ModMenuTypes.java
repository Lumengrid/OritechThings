package com.lumengrid.oritechthings.menu;

import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, OritechThings.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<AcceleratorSpeedSensorMenu>> SPEED_SENSOR_MENU =
            registerMenuType(AcceleratorSpeedSensorMenu::new, "speed_sensor_menu");

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>,MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }
}