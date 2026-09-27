package com.lumengrid.oritechthings.util;

import net.minecraft.client.Minecraft;

public class Utility {
    public static boolean isControlDown() {
        try {
            return Minecraft.getInstance().hasControlDown();
//            var method = Screen.class.getDeclaredMethod(
//                    "hasControlDown"
//            );
//
//            method.setAccessible(true);
//
//            return (boolean) method.invoke(null);
        } catch (Throwable ignored) {
            return false;
        }
    }

}
