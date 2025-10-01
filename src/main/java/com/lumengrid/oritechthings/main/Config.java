package com.lumengrid.oritechthings.main;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // ExoJetPack Settings
    public static final ModConfigSpec.BooleanValue EXO_JETPACK_ENABLED = BUILDER
            .translation("configuration.oritechthings.exoJetPack.enabledCreativeFlight")
            .define("exoJetPack.enabledCreativeFlight", true);

    public static final ModConfigSpec.LongValue EXO_JETPACK_RF_THRESHOLD = BUILDER
            .translation("configuration.oritechthings.exoJetPack.rfThreshold")
            .defineInRange("exoJetPack.rfThreshold", 10000L, 0L, Long.MAX_VALUE);

    // Tier 0 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_0_SPEED_MULTIPLIER = BUILDER
            .translation("configuration.oritechthings.speedMultiplier")
            .defineInRange("tier0.speedMultiplier", 0.0, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_0_EFFICIENCY_DOWN = BUILDER
            .translation("configuration.oritechthings.efficiencyDown")
            .defineInRange("tier0.efficiencyDown", 1.40, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_0_EFFICIENCY_UP = BUILDER
            .translation("configuration.oritechthings.efficiencyUp")
            .defineInRange("tier0.efficiencyUp", 0.60, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_0_CAPACITOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.capacitorCapacity")
            .defineInRange("tier0.capacitorCapacity", 4_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_0_CAPACITOR_RATE = BUILDER
            .translation("configuration.oritechthings.capacitorRate")
            .defineInRange("tier0.capacitorRate", 2_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_0_ACCEPTOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.acceptorCapacity")
            .defineInRange("tier0.acceptorCapacity", 1_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_0_ACCEPTOR_RATE = BUILDER
            .translation("configuration.oritechthings.acceptorRate")
            .defineInRange("tier0.acceptorRate", 4000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_0_PROCESSING_EFFICIENCY = BUILDER
            .translation("configuration.oritechthings.processingEfficiency")
            .defineInRange("tier0.processingEfficiency", 2.0, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_0_PROCESSING_CHAMBERS = BUILDER
            .translation("configuration.oritechthings.processingChambers")
            .defineInRange("tier0.processingChambers", 2, 1, 100);

    // Tier 1 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_1_SPEED_MULTIPLIER = BUILDER
            .translation("configuration.oritechthings.speedMultiplier")
            .defineInRange("tier1.speedMultiplier", -0.5, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_1_EFFICIENCY_DOWN = BUILDER
            .translation("configuration.oritechthings.efficiencyDown")
            .defineInRange("tier1.efficiencyDown", 1.60, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_1_EFFICIENCY_UP = BUILDER
            .translation("configuration.oritechthings.efficiencyUp")
            .defineInRange("tier1.efficiencyUp", 0.40, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_1_CAPACITOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.capacitorCapacity")
            .defineInRange("tier1.capacitorCapacity", 6_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_1_CAPACITOR_RATE = BUILDER
            .translation("configuration.oritechthings.capacitorRate")
            .defineInRange("tier1.capacitorRate", 3_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_1_ACCEPTOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.acceptorCapacity")
            .defineInRange("tier1.acceptorCapacity", 1_500_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_1_ACCEPTOR_RATE = BUILDER
            .translation("configuration.oritechthings.acceptorRate")
            .defineInRange("tier1.acceptorRate", 6000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_1_PROCESSING_EFFICIENCY = BUILDER
            .translation("configuration.oritechthings.processingEfficiency")
            .defineInRange("tier1.processingEfficiency", 2.5, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_1_PROCESSING_CHAMBERS = BUILDER
            .translation("configuration.oritechthings.processingChambers")
            .defineInRange("tier1.processingChambers", 3, 1, 100);

    // Tier 2 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_2_SPEED_MULTIPLIER = BUILDER
            .translation("configuration.oritechthings.speedMultiplier")
            .defineInRange("tier2.speedMultiplier", -1.0, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_2_EFFICIENCY_DOWN = BUILDER
            .translation("configuration.oritechthings.efficiencyDown")
            .defineInRange("tier2.efficiencyDown", 1.80, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_2_EFFICIENCY_UP = BUILDER
            .translation("configuration.oritechthings.efficiencyUp")
            .defineInRange("tier2.efficiencyUp", 0.20, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_2_CAPACITOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.capacitorCapacity")
            .defineInRange("tier2.capacitorCapacity", 8_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_2_CAPACITOR_RATE = BUILDER
            .translation("configuration.oritechthings.capacitorRate")
            .defineInRange("tier2.capacitorRate", 4_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_2_ACCEPTOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.acceptorCapacity")
            .defineInRange("tier2.acceptorCapacity", 2_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_2_ACCEPTOR_RATE = BUILDER
            .translation("configuration.oritechthings.acceptorRate")
            .defineInRange("tier2.acceptorRate", 8000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_2_PROCESSING_EFFICIENCY = BUILDER
            .translation("configuration.oritechthings.processingEfficiency")
            .defineInRange("tier2.processingEfficiency", 3.0, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_2_PROCESSING_CHAMBERS = BUILDER
            .translation("configuration.oritechthings.processingChambers")
            .defineInRange("tier2.processingChambers", 4, 1, 100);

    // Tier 3 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_3_SPEED_MULTIPLIER = BUILDER
            .translation("configuration.oritechthings.speedMultiplier")
            .defineInRange("tier3.speedMultiplier", -1.5, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_3_EFFICIENCY_DOWN = BUILDER
            .translation("configuration.oritechthings.efficiencyDown")
            .defineInRange("tier3.efficiencyDown", 2.00, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_3_EFFICIENCY_UP = BUILDER
            .translation("configuration.oritechthings.efficiencyUp")
            .defineInRange("tier3.efficiencyUp", 0.0, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_3_CAPACITOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.capacitorCapacity")
            .defineInRange("tier3.capacitorCapacity", 10_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_3_CAPACITOR_RATE = BUILDER
            .translation("configuration.oritechthings.capacitorRate")
            .defineInRange("tier3.capacitorRate", 5_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_3_ACCEPTOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.acceptorCapacity")
            .defineInRange("tier3.acceptorCapacity", 2_500_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_3_ACCEPTOR_RATE = BUILDER
            .translation("configuration.oritechthings.acceptorRate")
            .defineInRange("tier3.acceptorRate", 10000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_3_PROCESSING_EFFICIENCY = BUILDER
            .translation("configuration.oritechthings.processingEfficiency")
            .defineInRange("tier3.processingEfficiency", 3.5, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_3_PROCESSING_CHAMBERS = BUILDER
            .translation("configuration.oritechthings.processingChambers")
            .defineInRange("tier3.processingChambers", 5, 1, 100);

    // Tier 4 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_4_SPEED_MULTIPLIER = BUILDER
            .translation("configuration.oritechthings.speedMultiplier")
            .defineInRange("tier4.speedMultiplier", -2.0, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_4_EFFICIENCY_DOWN = BUILDER
            .translation("configuration.oritechthings.efficiencyDown")
            .defineInRange("tier4.efficiencyDown", 2.20, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_4_EFFICIENCY_UP = BUILDER
            .translation("configuration.oritechthings.efficiencyUp")
            .defineInRange("tier4.efficiencyUp", -0.2, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_4_CAPACITOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.capacitorCapacity")
            .defineInRange("tier4.capacitorCapacity", 12_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_4_CAPACITOR_RATE = BUILDER
            .translation("configuration.oritechthings.capacitorRate")
            .defineInRange("tier4.capacitorRate", 6_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_4_ACCEPTOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.acceptorCapacity")
            .defineInRange("tier4.acceptorCapacity", 3_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_4_ACCEPTOR_RATE = BUILDER
            .translation("configuration.oritechthings.acceptorRate")
            .defineInRange("tier4.acceptorRate", 12000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_4_PROCESSING_EFFICIENCY = BUILDER
            .translation("configuration.oritechthings.processingEfficiency")
            .defineInRange("tier4.processingEfficiency", 4.0, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_4_PROCESSING_CHAMBERS = BUILDER
            .translation("configuration.oritechthings.processingChambers")
            .defineInRange("tier4.processingChambers", 6, 1, 100);

    // Tier 5 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_5_SPEED_MULTIPLIER = BUILDER
            .translation("configuration.oritechthings.speedMultiplier")
            .defineInRange("tier5.speedMultiplier", -2.5, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_5_EFFICIENCY_DOWN = BUILDER
            .translation("configuration.oritechthings.efficiencyDown")
            .defineInRange("tier5.efficiencyDown", 2.40, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_5_EFFICIENCY_UP = BUILDER
            .translation("configuration.oritechthings.efficiencyUp")
            .defineInRange("tier5.efficiencyUp", -0.40, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_5_CAPACITOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.capacitorCapacity")
            .defineInRange("tier5.capacitorCapacity", 14_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_5_CAPACITOR_RATE = BUILDER
            .translation("configuration.oritechthings.capacitorRate")
            .defineInRange("tier5.capacitorRate", 7_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_5_ACCEPTOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.acceptorCapacity")
            .defineInRange("tier5.acceptorCapacity", 3_500_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_5_ACCEPTOR_RATE = BUILDER
            .translation("configuration.oritechthings.acceptorRate")
            .defineInRange("tier5.acceptorRate", 14000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_5_PROCESSING_EFFICIENCY = BUILDER
            .translation("configuration.oritechthings.processingEfficiency")
            .defineInRange("tier5.processingEfficiency", 4.5, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_5_PROCESSING_CHAMBERS = BUILDER
            .translation("configuration.oritechthings.processingChambers")
            .defineInRange("tier5.processingChambers", 7, 1, 100);

    // Tier 6 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_6_SPEED_MULTIPLIER = BUILDER
            .translation("configuration.oritechthings.speedMultiplier")
            .defineInRange("tier6.speedMultiplier", -3.0, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_6_EFFICIENCY_DOWN = BUILDER
            .translation("configuration.oritechthings.efficiencyDown")
            .defineInRange("tier6.efficiencyDown", 2.60, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_6_EFFICIENCY_UP = BUILDER
            .translation("configuration.oritechthings.efficiencyUp")
            .defineInRange("tier6.efficiencyUp", -0.6, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_6_CAPACITOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.capacitorCapacity")
            .defineInRange("tier6.capacitorCapacity", 16_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_6_CAPACITOR_RATE = BUILDER
            .translation("configuration.oritechthings.capacitorRate")
            .defineInRange("tier6.capacitorRate", 8_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_6_ACCEPTOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.acceptorCapacity")
            .defineInRange("tier6.acceptorCapacity", 4_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_6_ACCEPTOR_RATE = BUILDER
            .translation("configuration.oritechthings.acceptorRate")
            .defineInRange("tier6.acceptorRate", 14000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_6_PROCESSING_EFFICIENCY = BUILDER
            .translation("configuration.oritechthings.processingEfficiency")
            .defineInRange("tier6.processingEfficiency", 5.0, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_6_PROCESSING_CHAMBERS = BUILDER
            .translation("configuration.oritechthings.processingChambers")
            .defineInRange("tier6.processingChambers", 8, 1, 100);

    // Tier 7 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_7_SPEED_MULTIPLIER = BUILDER
            .translation("configuration.oritechthings.speedMultiplier")
            .defineInRange("tier7.speedMultiplier", -3.5, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_7_EFFICIENCY_DOWN = BUILDER
            .translation("configuration.oritechthings.efficiencyDown")
            .defineInRange("tier7.efficiencyDown", 2.80, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_7_EFFICIENCY_UP = BUILDER
            .translation("configuration.oritechthings.efficiencyUp")
            .defineInRange("tier7.efficiencyUp", -0.8, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_7_CAPACITOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.capacitorCapacity")
            .defineInRange("tier7.capacitorCapacity", 18_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_7_CAPACITOR_RATE = BUILDER
            .translation("configuration.oritechthings.capacitorRate")
            .defineInRange("tier7.capacitorRate", 9_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_7_ACCEPTOR_CAPACITY = BUILDER
            .translation("configuration.oritechthings.acceptorCapacity")
            .defineInRange("tier7.acceptorCapacity", 4_500_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_7_ACCEPTOR_RATE = BUILDER
            .translation("configuration.oritechthings.acceptorRate")
            .defineInRange("tier7.acceptorRate", 18000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_7_PROCESSING_EFFICIENCY = BUILDER
            .translation("configuration.oritechthings.processingEfficiency")
            .defineInRange("tier7.processingEfficiency", 5.5, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_7_PROCESSING_CHAMBERS = BUILDER
            .translation("configuration.oritechthings.processingChambers")
            .defineInRange("tier7.processingChambers", 9, 1, 100);

    static final ModConfigSpec SPEC = BUILDER.build();
}
