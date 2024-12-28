package com.lumengrid.oritechthings.block;

import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;
import rearth.oritech.util.Geometry;


import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {

    public static final VoxelShape ADDON_SHAPE = Shapes.or(
        Shapes.box(1 / 16f,0, 1 / 16f, 15 / 16f, 2 / 16f, 15 / 16f),
        Shapes.box(3 / 16f,2 / 16f, 3 / 16f, 13 / 16f, 2 / 16f, 13 / 16f)
    );

    public static VoxelShape[][] USABLE_ADDON_SHAPE;
    static {
        USABLE_ADDON_SHAPE = new VoxelShape[Direction.values().length][AttachFace.values().length];

        for (Direction facing : Direction.values()) {
            if (facing.getAxis().isHorizontal()) {
                AttachFace[] faces = AttachFace.values();

                for (AttachFace face : faces) {
                    USABLE_ADDON_SHAPE[facing.ordinal()][face.ordinal()] = Shapes.or(
                        Geometry.rotateVoxelShape(ADDON_SHAPE, facing, face)
                    );
                }
            }
        }
    }

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(OritechThings.MOD_ID);

    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_2 = registerBlock("addon_block_speed_tier_2", () -> SpeedAddonBlock("addon_block_speed_tier_2"));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_3 = registerBlock("addon_block_speed_tier_3", () -> SpeedAddonBlock("addon_block_speed_tier_3"));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_4 = registerBlock("addon_block_speed_tier_4", () -> SpeedAddonBlock("addon_block_speed_tier_4"));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_5 = registerBlock("addon_block_speed_tier_5", () -> SpeedAddonBlock("addon_block_speed_tier_5"));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_6 = registerBlock("addon_block_speed_tier_6", () -> SpeedAddonBlock("addon_block_speed_tier_6"));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_7 = registerBlock("addon_block_speed_tier_7", () -> SpeedAddonBlock("addon_block_speed_tier_7"));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_8 = registerBlock("addon_block_speed_tier_8", () -> SpeedAddonBlock("addon_block_speed_tier_8"));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_9 = registerBlock("addon_block_speed_tier_9", () -> SpeedAddonBlock("addon_block_speed_tier_9"));

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_2 = registerBlock("addon_block_efficiency_tier_2", () -> EfficiencyAddonBlock("addon_block_efficiency_tier_2"));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_3 = registerBlock("addon_block_efficiency_tier_3", () -> EfficiencyAddonBlock("addon_block_efficiency_tier_3"));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_4 = registerBlock("addon_block_efficiency_tier_4", () -> EfficiencyAddonBlock("addon_block_efficiency_tier_4"));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_5 = registerBlock("addon_block_efficiency_tier_5", () -> EfficiencyAddonBlock("addon_block_efficiency_tier_5"));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_6 = registerBlock("addon_block_efficiency_tier_6", () -> EfficiencyAddonBlock("addon_block_efficiency_tier_6"));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_7 = registerBlock("addon_block_efficiency_tier_7", () -> EfficiencyAddonBlock("addon_block_efficiency_tier_7"));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_8 = registerBlock("addon_block_efficiency_tier_8", () -> EfficiencyAddonBlock("addon_block_efficiency_tier_8"));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_9 = registerBlock("addon_block_efficiency_tier_9", () -> EfficiencyAddonBlock("addon_block_efficiency_tier_9"));

    private static Block SpeedAddonBlock(String name) {
        String[] split = name.split("_");
        int i = Integer.parseInt(split[split.length - 1]) - 2;
        float speedMultiplier = ConfigLoader.getInstance().speedAddonSpeedMultiplier.get(i);
        float efficiencyMultiplier = ConfigLoader.getInstance().speedAddonEfficiencyMultiplier.get(i);
        return new TierAddonBlock(
                MachineAddonBlock.AddonSettings.getDefaultSettings()
                        .withSpeedMultiplier(speedMultiplier)
                        .withEfficiencyMultiplier(efficiencyMultiplier)
                        .withNeedsSupport(false)
                        .withBoundingShape(USABLE_ADDON_SHAPE));
    }

    private static MachineAddonBlock EfficiencyAddonBlock(String name) {
        String[] split = name.split("_");
        int i = Integer.parseInt(split[split.length - 1]) - 2;
        float efficiencyMultiplier = ConfigLoader.getInstance().efficiencyAddonEfficiencyMultiplier.get(i);
        return new TierAddonBlock(
                MachineAddonBlock.AddonSettings.getDefaultSettings()
                        .withEfficiencyMultiplier(efficiencyMultiplier)
                        .withNeedsSupport(false)
                        .withBoundingShape(USABLE_ADDON_SHAPE));
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
