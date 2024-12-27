package com.lumengrid.oritechthings.block;

import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;


import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(OritechThings.MOD_ID);

    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_2 = registerBlock("addon_block_speed_tier_2",
            () ->  SpeedAddonBlock(0.80f, 1.10f));

    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_3 = registerBlock("addon_block_speed_tier_3",
            () ->  SpeedAddonBlock(0.70f, 1.15f));

    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_4 = registerBlock("addon_block_speed_tier_4",
            () ->  SpeedAddonBlock(0.60f, 1.20f));

    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_5 = registerBlock("addon_block_speed_tier_5",
            () ->  SpeedAddonBlock(0.50f, 1.25f));

    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_6 = registerBlock("addon_block_speed_tier_6",
            () ->  SpeedAddonBlock(0.40f, 1.30f));

    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_7 = registerBlock("addon_block_speed_tier_7",
            () ->  SpeedAddonBlock(0.30f, 1.35f));

    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_8 = registerBlock("addon_block_speed_tier_8",
            () ->  SpeedAddonBlock(0.20f, 1.40f));

    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_9 = registerBlock("addon_block_speed_tier_9",
            () ->  SpeedAddonBlock(0.10f, 1.45f));

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_2 = registerBlock("addon_block_efficiency_tier_2",
            () ->  EfficiencyAddonBlock(0.79f));

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_3 = registerBlock("addon_block_efficiency_tier_3",
            () ->  EfficiencyAddonBlock(0.75f));

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_4 = registerBlock("addon_block_efficiency_tier_4",
            () ->  EfficiencyAddonBlock(0.70f));

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_5 = registerBlock("addon_block_efficiency_tier_5",
            () ->  EfficiencyAddonBlock(0.65f));

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_6 = registerBlock("addon_block_efficiency_tier_6",
            () ->  EfficiencyAddonBlock(0.60f));

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_7 = registerBlock("addon_block_efficiency_tier_7",
            () ->  EfficiencyAddonBlock(0.55f));

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_8 = registerBlock("addon_block_efficiency_tier_8",
            () ->  EfficiencyAddonBlock(0.50f));

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_9 = registerBlock("addon_block_efficiency_tier_9",
            () ->  EfficiencyAddonBlock(0.45f));

    private static Block SpeedAddonBlock(float speedMultiplier, float efficiencyMultiplier) {
        return new TierAddonBlock(
                MachineAddonBlock.AddonSettings.getDefaultSettings()
                        .withSpeedMultiplier(speedMultiplier)
                        .withEfficiencyMultiplier(efficiencyMultiplier)
                        .withNeedsSupport(false)
                        .withBoundingShape(MachineAddonBlock.MACHINE_SPEED_ADDON_SHAPE));
    }

    private static MachineAddonBlock EfficiencyAddonBlock(float efficiencyMultiplier) {;
        return new TierAddonBlock(
                MachineAddonBlock.AddonSettings.getDefaultSettings()
                        .withEfficiencyMultiplier(efficiencyMultiplier)
                        .withNeedsSupport(false)
                        .withBoundingShape(MachineAddonBlock.MACHINE_EFFICIENCY_ADDON_SHAPE));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
