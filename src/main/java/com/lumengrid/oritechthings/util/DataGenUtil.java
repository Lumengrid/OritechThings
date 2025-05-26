package com.lumengrid.oritechthings.util;

import static com.lumengrid.oritechthings.main.OritechThings.MOD_ID;

import net.neoforged.neoforge.registries.DeferredHolder;

public class DataGenUtil {

    public static String getName(DeferredHolder<?,?> b){
        return b.getRegisteredName().replace(MOD_ID + ":", "");
    }

    /**
     * MOD_ID + ":a_bcd_e" -> "A Bcd E"
     */
    public static String formatted(String string) {
        StringBuilder result = new StringBuilder();
        for (String part : string.replace(MOD_ID + ":", "").split("_"))
            if (!part.isEmpty())
                result.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1))
                        .append(" ");
        return result.toString().trim();
    }
    /**
     * format all specific addon related stuff
     */
    public static String specificReplace(String string) {
        return formatted(string
                .replace("addon_block_", "")
                .replace("_tier_", " Addon Tier "));
    }
}
