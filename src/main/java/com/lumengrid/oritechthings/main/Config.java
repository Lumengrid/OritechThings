package com.lumengrid.oritechthings.main;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // ExoJetPack Settings
    public static final ModConfigSpec.BooleanValue EXO_JETPACK_ENABLED = BUILDER
            .comment("Enable creative flight for ExoJetPack")
            .translation("oritechthings.configuration.exoJetPack.enabledCreativeFlight")
            .define("exoJetPack.enabledCreativeFlight", true);

    public static final ModConfigSpec.LongValue EXO_JETPACK_RF_THRESHOLD = BUILDER
            .comment("RF threshold for ExoJetPack")
            .translation("oritechthings.configuration.exoJetPack.rfThreshold")
            .defineInRange("exoJetPack.rfThreshold", 10000L, 0L, Long.MAX_VALUE);

    // Tier 0 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_0_SPEED_MULTIPLIER = BUILDER
            .comment("Tier 0 speed multiplier")
            .translation("oritechthings.configuration.speedMultiplier")
            .defineInRange("tier0.speedMultiplier", 0.0, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_0_EFFICIENCY_DOWN = BUILDER
            .comment("Tier 0 efficiency down")
            .translation("oritechthings.configuration.efficiencyDown")
            .defineInRange("tier0.efficiencyDown", 1.40, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_0_EFFICIENCY_UP = BUILDER
            .comment("Tier 0 efficiency up")
            .translation("oritechthings.configuration.efficiencyUp")
            .defineInRange("tier0.efficiencyUp", 0.60, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_0_CAPACITOR_CAPACITY = BUILDER
            .comment("Tier 0 capacitor capacity")
            .translation("oritechthings.configuration.capacitorCapacity")
            .defineInRange("tier0.capacitorCapacity", 4_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_0_CAPACITOR_RATE = BUILDER
            .comment("Tier 0 capacitor rate")
            .translation("oritechthings.configuration.capacitorRate")
            .defineInRange("tier0.capacitorRate", 2_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_0_ACCEPTOR_CAPACITY = BUILDER
            .comment("Tier 0 acceptor capacity")
            .translation("oritechthings.configuration.acceptorCapacity")
            .defineInRange("tier0.acceptorCapacity", 1_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_0_ACCEPTOR_RATE = BUILDER
            .comment("Tier 0 acceptor rate")
            .translation("oritechthings.configuration.acceptorRate")
            .defineInRange("tier0.acceptorRate", 4000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_0_PROCESSING_EFFICIENCY = BUILDER
            .comment("Tier 0 processing efficiency")
            .translation("oritechthings.configuration.processingEfficiency")
            .defineInRange("tier0.processingEfficiency", 2.0, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_0_PROCESSING_CHAMBERS = BUILDER
            .comment("Tier 0 processing chambers")
            .translation("oritechthings.configuration.processingChambers")
            .defineInRange("tier0.processingChambers", 2, 1, 100);

    // Tier 1 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_1_SPEED_MULTIPLIER = BUILDER
            .comment("Tier 1 speed multiplier")
            .defineInRange("tier1.speedMultiplier", -0.5, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_1_EFFICIENCY_DOWN = BUILDER
            .comment("Tier 1 efficiency down")
            .defineInRange("tier1.efficiencyDown", 1.60, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_1_EFFICIENCY_UP = BUILDER
            .comment("Tier 1 efficiency up")
            .defineInRange("tier1.efficiencyUp", 0.40, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_1_CAPACITOR_CAPACITY = BUILDER
            .comment("Tier 1 capacitor capacity")
            .defineInRange("tier1.capacitorCapacity", 6_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_1_CAPACITOR_RATE = BUILDER
            .comment("Tier 1 capacitor rate")
            .defineInRange("tier1.capacitorRate", 3_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_1_ACCEPTOR_CAPACITY = BUILDER
            .comment("Tier 1 acceptor capacity")
            .defineInRange("tier1.acceptorCapacity", 1_500_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_1_ACCEPTOR_RATE = BUILDER
            .comment("Tier 1 acceptor rate")
            .defineInRange("tier1.acceptorRate", 6000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_1_PROCESSING_EFFICIENCY = BUILDER
            .comment("Tier 1 processing efficiency")
            .defineInRange("tier1.processingEfficiency", 2.5, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_1_PROCESSING_CHAMBERS = BUILDER
            .comment("Tier 1 processing chambers")
            .defineInRange("tier1.processingChambers", 3, 1, 100);

    // Tier 2 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_2_SPEED_MULTIPLIER = BUILDER
            .comment("Tier 2 speed multiplier")
            .defineInRange("tier2.speedMultiplier", -1.0, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_2_EFFICIENCY_DOWN = BUILDER
            .comment("Tier 2 efficiency down")
            .defineInRange("tier2.efficiencyDown", 1.80, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_2_EFFICIENCY_UP = BUILDER
            .comment("Tier 2 efficiency up")
            .defineInRange("tier2.efficiencyUp", 0.20, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_2_CAPACITOR_CAPACITY = BUILDER
            .comment("Tier 2 capacitor capacity")
            .defineInRange("tier2.capacitorCapacity", 8_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_2_CAPACITOR_RATE = BUILDER
            .comment("Tier 2 capacitor rate")
            .defineInRange("tier2.capacitorRate", 4_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_2_ACCEPTOR_CAPACITY = BUILDER
            .comment("Tier 2 acceptor capacity")
            .defineInRange("tier2.acceptorCapacity", 2_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_2_ACCEPTOR_RATE = BUILDER
            .comment("Tier 2 acceptor rate")
            .defineInRange("tier2.acceptorRate", 8000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_2_PROCESSING_EFFICIENCY = BUILDER
            .comment("Tier 2 processing efficiency")
            .defineInRange("tier2.processingEfficiency", 3.0, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_2_PROCESSING_CHAMBERS = BUILDER
            .comment("Tier 2 processing chambers")
            .defineInRange("tier2.processingChambers", 4, 1, 100);

    // Tier 3 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_3_SPEED_MULTIPLIER = BUILDER
            .comment("Tier 3 speed multiplier")
            .defineInRange("tier3.speedMultiplier", -1.5, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_3_EFFICIENCY_DOWN = BUILDER
            .comment("Tier 3 efficiency down")
            .defineInRange("tier3.efficiencyDown", 2.00, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_3_EFFICIENCY_UP = BUILDER
            .comment("Tier 3 efficiency up")
            .defineInRange("tier3.efficiencyUp", 0.0, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_3_CAPACITOR_CAPACITY = BUILDER
            .comment("Tier 3 capacitor capacity")
            .defineInRange("tier3.capacitorCapacity", 10_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_3_CAPACITOR_RATE = BUILDER
            .comment("Tier 3 capacitor rate")
            .defineInRange("tier3.capacitorRate", 5_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_3_ACCEPTOR_CAPACITY = BUILDER
            .comment("Tier 3 acceptor capacity")
            .defineInRange("tier3.acceptorCapacity", 2_500_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_3_ACCEPTOR_RATE = BUILDER
            .comment("Tier 3 acceptor rate")
            .defineInRange("tier3.acceptorRate", 10000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_3_PROCESSING_EFFICIENCY = BUILDER
            .comment("Tier 3 processing efficiency")
            .defineInRange("tier3.processingEfficiency", 3.5, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_3_PROCESSING_CHAMBERS = BUILDER
            .comment("Tier 3 processing chambers")
            .defineInRange("tier3.processingChambers", 5, 1, 100);

    // Tier 4 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_4_SPEED_MULTIPLIER = BUILDER
            .comment("Tier 4 speed multiplier")
            .defineInRange("tier4.speedMultiplier", -2.0, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_4_EFFICIENCY_DOWN = BUILDER
            .comment("Tier 4 efficiency down")
            .defineInRange("tier4.efficiencyDown", 2.20, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_4_EFFICIENCY_UP = BUILDER
            .comment("Tier 4 efficiency up")
            .defineInRange("tier4.efficiencyUp", -0.2, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_4_CAPACITOR_CAPACITY = BUILDER
            .comment("Tier 4 capacitor capacity")
            .defineInRange("tier4.capacitorCapacity", 12_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_4_CAPACITOR_RATE = BUILDER
            .comment("Tier 4 capacitor rate")
            .defineInRange("tier4.capacitorRate", 6_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_4_ACCEPTOR_CAPACITY = BUILDER
            .comment("Tier 4 acceptor capacity")
            .defineInRange("tier4.acceptorCapacity", 3_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_4_ACCEPTOR_RATE = BUILDER
            .comment("Tier 4 acceptor rate")
            .defineInRange("tier4.acceptorRate", 12000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_4_PROCESSING_EFFICIENCY = BUILDER
            .comment("Tier 4 processing efficiency")
            .defineInRange("tier4.processingEfficiency", 4.0, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_4_PROCESSING_CHAMBERS = BUILDER
            .comment("Tier 4 processing chambers")
            .defineInRange("tier4.processingChambers", 6, 1, 100);

    // Tier 5 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_5_SPEED_MULTIPLIER = BUILDER
            .comment("Tier 5 speed multiplier")
            .defineInRange("tier5.speedMultiplier", -2.5, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_5_EFFICIENCY_DOWN = BUILDER
            .comment("Tier 5 efficiency down")
            .defineInRange("tier5.efficiencyDown", 2.40, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_5_EFFICIENCY_UP = BUILDER
            .comment("Tier 5 efficiency up")
            .defineInRange("tier5.efficiencyUp", -0.40, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_5_CAPACITOR_CAPACITY = BUILDER
            .comment("Tier 5 capacitor capacity")
            .defineInRange("tier5.capacitorCapacity", 14_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_5_CAPACITOR_RATE = BUILDER
            .comment("Tier 5 capacitor rate")
            .defineInRange("tier5.capacitorRate", 7_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_5_ACCEPTOR_CAPACITY = BUILDER
            .comment("Tier 5 acceptor capacity")
            .defineInRange("tier5.acceptorCapacity", 3_500_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_5_ACCEPTOR_RATE = BUILDER
            .comment("Tier 5 acceptor rate")
            .defineInRange("tier5.acceptorRate", 14000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_5_PROCESSING_EFFICIENCY = BUILDER
            .comment("Tier 5 processing efficiency")
            .defineInRange("tier5.processingEfficiency", 4.5, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_5_PROCESSING_CHAMBERS = BUILDER
            .comment("Tier 5 processing chambers")
            .defineInRange("tier5.processingChambers", 7, 1, 100);

    // Tier 6 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_6_SPEED_MULTIPLIER = BUILDER
            .comment("Tier 6 speed multiplier")
            .defineInRange("tier6.speedMultiplier", -3.0, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_6_EFFICIENCY_DOWN = BUILDER
            .comment("Tier 6 efficiency down")
            .defineInRange("tier6.efficiencyDown", 2.60, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_6_EFFICIENCY_UP = BUILDER
            .comment("Tier 6 efficiency up")
            .defineInRange("tier6.efficiencyUp", -0.6, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_6_CAPACITOR_CAPACITY = BUILDER
            .comment("Tier 6 capacitor capacity")
            .defineInRange("tier6.capacitorCapacity", 16_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_6_CAPACITOR_RATE = BUILDER
            .comment("Tier 6 capacitor rate")
            .defineInRange("tier6.capacitorRate", 8_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_6_ACCEPTOR_CAPACITY = BUILDER
            .comment("Tier 6 acceptor capacity")
            .defineInRange("tier6.acceptorCapacity", 4_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_6_ACCEPTOR_RATE = BUILDER
            .comment("Tier 6 acceptor rate")
            .defineInRange("tier6.acceptorRate", 14000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_6_PROCESSING_EFFICIENCY = BUILDER
            .comment("Tier 6 processing efficiency")
            .defineInRange("tier6.processingEfficiency", 5.0, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_6_PROCESSING_CHAMBERS = BUILDER
            .comment("Tier 6 processing chambers")
            .defineInRange("tier6.processingChambers", 8, 1, 100);

    // Tier 7 Addon Settings
    public static final ModConfigSpec.DoubleValue TIER_7_SPEED_MULTIPLIER = BUILDER
            .comment("Tier 7 speed multiplier")
            .defineInRange("tier7.speedMultiplier", -3.5, -10.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_7_EFFICIENCY_DOWN = BUILDER
            .comment("Tier 7 efficiency down")
            .defineInRange("tier7.efficiencyDown", 2.80, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue TIER_7_EFFICIENCY_UP = BUILDER
            .comment("Tier 7 efficiency up")
            .defineInRange("tier7.efficiencyUp", -0.8, -10.0, 10.0);

    public static final ModConfigSpec.LongValue TIER_7_CAPACITOR_CAPACITY = BUILDER
            .comment("Tier 7 capacitor capacity")
            .defineInRange("tier7.capacitorCapacity", 18_000_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_7_CAPACITOR_RATE = BUILDER
            .comment("Tier 7 capacitor rate")
            .defineInRange("tier7.capacitorRate", 9_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_7_ACCEPTOR_CAPACITY = BUILDER
            .comment("Tier 7 acceptor capacity")
            .defineInRange("tier7.acceptorCapacity", 4_500_000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.LongValue TIER_7_ACCEPTOR_RATE = BUILDER
            .comment("Tier 7 acceptor rate")
            .defineInRange("tier7.acceptorRate", 18000L, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIER_7_PROCESSING_EFFICIENCY = BUILDER
            .comment("Tier 7 processing efficiency")
            .defineInRange("tier7.processingEfficiency", 5.5, 0.0, 100.0);

    public static final ModConfigSpec.IntValue TIER_7_PROCESSING_CHAMBERS = BUILDER
            .comment("Tier 7 processing chambers")
            .defineInRange("tier7.processingChambers", 9, 1, 100);

    static final ModConfigSpec SPEC = BUILDER.build();
}
