package com.lumengrid.oritechthings.main;

import com.lumengrid.oritechthings.main.ConfigLoader.AddonInfo;
import com.lumengrid.oritechthings.main.ConfigLoader.ExoJetPack;

public class ConfigHelper {
    
    public static ExoJetPack getExoJetPackSettings() {
        try {
            return new ExoJetPack(
                Config.EXO_JETPACK_ENABLED.get(),
                Config.EXO_JETPACK_RF_THRESHOLD.get()
            );
        } catch (IllegalStateException e) {
            // Config not loaded yet, return default values
            return new ExoJetPack(true, 10000L);
        }
    }
    
    public static AddonInfo getAddonInfo(int tier) {
        try {
            return switch (tier) {
                case 0 -> new AddonInfo(
                        Config.TIER_0_SPEED_MULTIPLIER.get().floatValue(),
                        Config.TIER_0_EFFICIENCY_DOWN.get().floatValue(),
                        Config.TIER_0_EFFICIENCY_UP.get().floatValue(),
                        Config.TIER_0_CAPACITOR_CAPACITY.get(),
                        Config.TIER_0_CAPACITOR_RATE.get(),
                        Config.TIER_0_ACCEPTOR_CAPACITY.get(),
                        Config.TIER_0_ACCEPTOR_RATE.get(),
                        Config.TIER_0_PROCESSING_EFFICIENCY.get().floatValue(),
                        Config.TIER_0_PROCESSING_CHAMBERS.get()
                );
                case 1 -> new AddonInfo(
                        Config.TIER_1_SPEED_MULTIPLIER.get().floatValue(),
                        Config.TIER_1_EFFICIENCY_DOWN.get().floatValue(),
                        Config.TIER_1_EFFICIENCY_UP.get().floatValue(),
                        Config.TIER_1_CAPACITOR_CAPACITY.get(),
                        Config.TIER_1_CAPACITOR_RATE.get(),
                        Config.TIER_1_ACCEPTOR_CAPACITY.get(),
                        Config.TIER_1_ACCEPTOR_RATE.get(),
                        Config.TIER_1_PROCESSING_EFFICIENCY.get().floatValue(),
                        Config.TIER_1_PROCESSING_CHAMBERS.get()
                );
                case 2 -> new AddonInfo(
                        Config.TIER_2_SPEED_MULTIPLIER.get().floatValue(),
                        Config.TIER_2_EFFICIENCY_DOWN.get().floatValue(),
                        Config.TIER_2_EFFICIENCY_UP.get().floatValue(),
                        Config.TIER_2_CAPACITOR_CAPACITY.get(),
                        Config.TIER_2_CAPACITOR_RATE.get(),
                        Config.TIER_2_ACCEPTOR_CAPACITY.get(),
                        Config.TIER_2_ACCEPTOR_RATE.get(),
                        Config.TIER_2_PROCESSING_EFFICIENCY.get().floatValue(),
                        Config.TIER_2_PROCESSING_CHAMBERS.get()
                );
                case 3 -> new AddonInfo(
                        Config.TIER_3_SPEED_MULTIPLIER.get().floatValue(),
                        Config.TIER_3_EFFICIENCY_DOWN.get().floatValue(),
                        Config.TIER_3_EFFICIENCY_UP.get().floatValue(),
                        Config.TIER_3_CAPACITOR_CAPACITY.get(),
                        Config.TIER_3_CAPACITOR_RATE.get(),
                        Config.TIER_3_ACCEPTOR_CAPACITY.get(),
                        Config.TIER_3_ACCEPTOR_RATE.get(),
                        Config.TIER_3_PROCESSING_EFFICIENCY.get().floatValue(),
                        Config.TIER_3_PROCESSING_CHAMBERS.get()
                );
                case 4 -> new AddonInfo(
                        Config.TIER_4_SPEED_MULTIPLIER.get().floatValue(),
                        Config.TIER_4_EFFICIENCY_DOWN.get().floatValue(),
                        Config.TIER_4_EFFICIENCY_UP.get().floatValue(),
                        Config.TIER_4_CAPACITOR_CAPACITY.get(),
                        Config.TIER_4_CAPACITOR_RATE.get(),
                        Config.TIER_4_ACCEPTOR_CAPACITY.get(),
                        Config.TIER_4_ACCEPTOR_RATE.get(),
                        Config.TIER_4_PROCESSING_EFFICIENCY.get().floatValue(),
                        Config.TIER_4_PROCESSING_CHAMBERS.get()
                );
                case 5 -> new AddonInfo(
                        Config.TIER_5_SPEED_MULTIPLIER.get().floatValue(),
                        Config.TIER_5_EFFICIENCY_DOWN.get().floatValue(),
                        Config.TIER_5_EFFICIENCY_UP.get().floatValue(),
                        Config.TIER_5_CAPACITOR_CAPACITY.get(),
                        Config.TIER_5_CAPACITOR_RATE.get(),
                        Config.TIER_5_ACCEPTOR_CAPACITY.get(),
                        Config.TIER_5_ACCEPTOR_RATE.get(),
                        Config.TIER_5_PROCESSING_EFFICIENCY.get().floatValue(),
                        Config.TIER_5_PROCESSING_CHAMBERS.get()
                );
                case 6 -> new AddonInfo(
                        Config.TIER_6_SPEED_MULTIPLIER.get().floatValue(),
                        Config.TIER_6_EFFICIENCY_DOWN.get().floatValue(),
                        Config.TIER_6_EFFICIENCY_UP.get().floatValue(),
                        Config.TIER_6_CAPACITOR_CAPACITY.get(),
                        Config.TIER_6_CAPACITOR_RATE.get(),
                        Config.TIER_6_ACCEPTOR_CAPACITY.get(),
                        Config.TIER_6_ACCEPTOR_RATE.get(),
                        Config.TIER_6_PROCESSING_EFFICIENCY.get().floatValue(),
                        Config.TIER_6_PROCESSING_CHAMBERS.get()
                );
                case 7 -> new AddonInfo(
                        Config.TIER_7_SPEED_MULTIPLIER.get().floatValue(),
                        Config.TIER_7_EFFICIENCY_DOWN.get().floatValue(),
                        Config.TIER_7_EFFICIENCY_UP.get().floatValue(),
                        Config.TIER_7_CAPACITOR_CAPACITY.get(),
                        Config.TIER_7_CAPACITOR_RATE.get(),
                        Config.TIER_7_ACCEPTOR_CAPACITY.get(),
                        Config.TIER_7_ACCEPTOR_RATE.get(),
                        Config.TIER_7_PROCESSING_EFFICIENCY.get().floatValue(),
                        Config.TIER_7_PROCESSING_CHAMBERS.get()
                );
                default -> {
                    System.err.println("Invalid tier " + tier + " requested, falling back to tier 0");
                    yield getAddonInfo(0);
                }
            };
        } catch (IllegalStateException e) {
            // Config not loaded yet, return default values based on tier
            return getDefaultAddonInfo(tier);
        }
    }
    
    private static AddonInfo getDefaultAddonInfo(int tier) {
        // Return default values that match the original ConfigLoader values
        return switch (tier) {
            case 0 -> new AddonInfo(0f, 1.40f, 0.60f, 4_000_000L, 2_000L, 1_000_000L, 4000L, 2f, 2);
            case 1 -> new AddonInfo(-0.5f, 1.60f, 0.40f, 6_000_000L, 3_000L, 1_500_000L, 6000L, 2.5f, 3);
            case 2 -> new AddonInfo(-1f, 1.80f, 0.20f, 8_000_000L, 4_000L, 2_000_000L, 8000L, 3f, 4);
            case 3 -> new AddonInfo(-1.5f, 2.00f, 0f, 10_000_000L, 5_000L, 2_500_000L, 10000L, 3.5f, 5);
            case 4 -> new AddonInfo(-2f, 2.20f, -0.2f, 12_000_000L, 6_000L, 3_000_000L, 12000L, 4f, 6);
            case 5 -> new AddonInfo(-2.5f, 2.40f, -0.40f, 14_000_000L, 7_000L, 3_500_000L, 14000L, 4.5f, 7);
            case 6 -> new AddonInfo(-3f, 2.60f, -0.6f, 16_000_000L, 8_000L, 4_000_000L, 14000L, 5f, 8);
            case 7 -> new AddonInfo(-3.5f, 2.80f, -0.8f, 18_000_000L, 9_000L, 4_500_000L, 18000L, 5.5f, 9);
            default -> getDefaultAddonInfo(0); // Fallback to tier 0
        };
    }
}
