package com.lumengrid.oritechthings.menu;

import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredHolder;
import net.minecraftforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, OritechThings.MOD_ID);

    // The menu is actually opened through the block entity's MenuProvider (server side) and the
    // FriendlyByteBuf constructor (client side), so this factory is only a registration
    // placeholder: it is never invoked in the normal open/close flow.
    public static final DeferredHolder<MenuType<?>, MenuType<AcceleratorSpeedSensorMenu>> SPEED_SENSOR_MENU =
            MENUS.register("speed_sensor_menu",
                    () -> IForgeMenuType.create((id, inv, player) -> new AcceleratorSpeedSensorMenu(id, inv, null), null));
}
