package com.lumengrid.oritechthings.menu;

import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(net.minecraft.core.registries.Registries.MENU, OritechThings.MOD_ID);

    // Client side: built from the FriendlyByteBuf written by NetworkHooks.openScreen (block position).
    public static final RegistryObject<MenuType<AcceleratorSpeedSensorMenu>> SPEED_SENSOR_MENU =
            MENUS.register("speed_sensor_menu",
                    () -> IForgeMenuType.create(AcceleratorSpeedSensorMenu::new));
}
