package com.lumengrid.oritechthings.block;

import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.ShapeUtil;
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

import java.util.function.Supplier;

public class ModBlocks {

    public static final VoxelShape ADDON_SHAPE = Shapes.or(
        Shapes.box(1.0,0, 1.0, 15.0, 2.0, 15.0),
        Shapes.box(3.0,2.0, 3.0, 13.0, 2.0, 13.0)
    );

    public static VoxelShape[][] USABLE_ADDON_SHAPE;
    static {
        USABLE_ADDON_SHAPE = new VoxelShape[Direction.values().length][AttachFace.values().length];

        for (Direction facing : Direction.values()) {
            if (facing.getAxis().isHorizontal()) {
                AttachFace[] faces = AttachFace.values();

                for (AttachFace face : faces) {
                    USABLE_ADDON_SHAPE[facing.ordinal()][face.ordinal()] = Shapes.or(
                        Geometry.rotateVoxelShape(ShapeUtil.shapeFromDimension(1, 0, 1, 14, 2, 14), facing, face),
                        Geometry.rotateVoxelShape(ShapeUtil.shapeFromDimension(3,2, 3, 10, 2, 10), facing, face)
                    );
                }
            }
        }
    }

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(OritechThings.MOD_ID);

    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_2 = registerBlock("addon_block_speed_tier_2", () -> speedAddonBlock("addon_block_speed_tier_2", 2));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_3 = registerBlock("addon_block_speed_tier_3", () -> speedAddonBlock("addon_block_speed_tier_3", 3));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_4 = registerBlock("addon_block_speed_tier_4", () -> speedAddonBlock("addon_block_speed_tier_4", 4));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_5 = registerBlock("addon_block_speed_tier_5", () -> speedAddonBlock("addon_block_speed_tier_5", 5));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_6 = registerBlock("addon_block_speed_tier_6", () -> speedAddonBlock("addon_block_speed_tier_6", 6));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_7 = registerBlock("addon_block_speed_tier_7", () -> speedAddonBlock("addon_block_speed_tier_7", 7));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_8 = registerBlock("addon_block_speed_tier_8", () -> speedAddonBlock("addon_block_speed_tier_8", 8));
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_9 = registerBlock("addon_block_speed_tier_9", () -> speedAddonBlock("addon_block_speed_tier_9", 9));

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_2 = registerBlock("addon_block_efficient_speed_tier_2", () -> efficientSpeedAddonBlock("addon_block_speed_tier_2", 2));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_3 = registerBlock("addon_block_efficient_speed_tier_3", () -> efficientSpeedAddonBlock("addon_block_speed_tier_3", 3));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_4 = registerBlock("addon_block_efficient_speed_tier_4", () -> efficientSpeedAddonBlock("addon_block_speed_tier_4", 4));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_5 = registerBlock("addon_block_efficient_speed_tier_5", () -> efficientSpeedAddonBlock("addon_block_speed_tier_5", 5));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_6 = registerBlock("addon_block_efficient_speed_tier_6", () -> efficientSpeedAddonBlock("addon_block_speed_tier_6", 6));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_7 = registerBlock("addon_block_efficient_speed_tier_7", () -> efficientSpeedAddonBlock("addon_block_speed_tier_7", 7));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_8 = registerBlock("addon_block_efficient_speed_tier_8", () -> efficientSpeedAddonBlock("addon_block_speed_tier_8", 8));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_9 = registerBlock("addon_block_efficient_speed_tier_9", () -> efficientSpeedAddonBlock("addon_block_speed_tier_9", 9));

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_2 = registerBlock("addon_block_efficiency_tier_2", () -> efficiencyAddonBlock("addon_block_efficiency_tier_2", 2));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_3 = registerBlock("addon_block_efficiency_tier_3", () -> efficiencyAddonBlock("addon_block_efficiency_tier_3", 3));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_4 = registerBlock("addon_block_efficiency_tier_4", () -> efficiencyAddonBlock("addon_block_efficiency_tier_4", 4));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_5 = registerBlock("addon_block_efficiency_tier_5", () -> efficiencyAddonBlock("addon_block_efficiency_tier_5", 5));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_6 = registerBlock("addon_block_efficiency_tier_6", () -> efficiencyAddonBlock("addon_block_efficiency_tier_6", 6));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_7 = registerBlock("addon_block_efficiency_tier_7", () -> efficiencyAddonBlock("addon_block_efficiency_tier_7", 7));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_8 = registerBlock("addon_block_efficiency_tier_8", () -> efficiencyAddonBlock("addon_block_efficiency_tier_8", 8));
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_9 = registerBlock("addon_block_efficiency_tier_9", () -> efficiencyAddonBlock("addon_block_efficiency_tier_9", 9));

    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_2 = registerBlock("addon_block_capacitor_tier_2", () -> capacitorAddonBlock("addon_block_capacitor_tier_2", 2));
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_3 = registerBlock("addon_block_capacitor_tier_3", () -> capacitorAddonBlock("addon_block_capacitor_tier_3", 3));
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_4 = registerBlock("addon_block_capacitor_tier_4", () -> capacitorAddonBlock("addon_block_capacitor_tier_4", 4));
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_5 = registerBlock("addon_block_capacitor_tier_5", () -> capacitorAddonBlock("addon_block_capacitor_tier_5", 5));
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_6 = registerBlock("addon_block_capacitor_tier_6", () -> capacitorAddonBlock("addon_block_capacitor_tier_6", 6));
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_7 = registerBlock("addon_block_capacitor_tier_7", () -> capacitorAddonBlock("addon_block_capacitor_tier_7", 7));
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_8 = registerBlock("addon_block_capacitor_tier_8", () -> capacitorAddonBlock("addon_block_capacitor_tier_8", 8));
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_9 = registerBlock("addon_block_capacitor_tier_9", () -> capacitorAddonBlock("addon_block_capacitor_tier_9", 9));

    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_2 = registerBlock("addon_block_acceptor_tier_2", () -> acceptorAddonBlock("addon_block_acceptor_tier_2", 2));
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_3 = registerBlock("addon_block_acceptor_tier_3", () -> acceptorAddonBlock("addon_block_acceptor_tier_3", 3));
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_4 = registerBlock("addon_block_acceptor_tier_4", () -> acceptorAddonBlock("addon_block_acceptor_tier_4", 4));
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_5 = registerBlock("addon_block_acceptor_tier_5", () -> acceptorAddonBlock("addon_block_acceptor_tier_5", 5));
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_6 = registerBlock("addon_block_acceptor_tier_6", () -> acceptorAddonBlock("addon_block_acceptor_tier_6", 6));
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_7 = registerBlock("addon_block_acceptor_tier_7", () -> acceptorAddonBlock("addon_block_acceptor_tier_7", 7));
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_8 = registerBlock("addon_block_acceptor_tier_8", () -> acceptorAddonBlock("addon_block_acceptor_tier_8", 8));
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_9 = registerBlock("addon_block_acceptor_tier_9", () -> acceptorAddonBlock("addon_block_acceptor_tier_9", 9));
    
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_2 = registerBlock("addon_block_processing_tier_2", () -> processingAddonBlock("addon_block_processing_tier_2", 2));
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_3 = registerBlock("addon_block_processing_tier_3", () -> processingAddonBlock("addon_block_processing_tier_3", 3));
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_4 = registerBlock("addon_block_processing_tier_4", () -> processingAddonBlock("addon_block_processing_tier_4", 4));
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_5 = registerBlock("addon_block_processing_tier_5", () -> processingAddonBlock("addon_block_processing_tier_5", 5));
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_6 = registerBlock("addon_block_processing_tier_6", () -> processingAddonBlock("addon_block_processing_tier_6", 6));
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_7 = registerBlock("addon_block_processing_tier_7", () -> processingAddonBlock("addon_block_processing_tier_7", 7));
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_8 = registerBlock("addon_block_processing_tier_8", () -> processingAddonBlock("addon_block_processing_tier_8", 8));
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_9 = registerBlock("addon_block_processing_tier_9", () -> processingAddonBlock("addon_block_processing_tier_9", 9));


    private static Block processingAddonBlock(String name, int tier) {
        String[] split = name.split("_");
        int i = Integer.parseInt(split[split.length - 1]) - 2;
        float efficiency = ConfigLoader.getInstance().addonSettings.get(i).processingEfficiency();
        int chambers = ConfigLoader.getInstance().addonSettings.get(i).processingChambers();
        return new TierAddonBlock(
                MachineAddonBlock.AddonSettings.getDefaultSettings()
                        .withEfficiencyMultiplier(efficiency)
                        .withChambers(chambers)
                        .withNeedsSupport(true)
                        .withBoundingShape(USABLE_ADDON_SHAPE), tier);
    }

    private static Block capacitorAddonBlock(String name, int tier) {
        String[] split = name.split("_");
        int i = Integer.parseInt(split[split.length - 1]) - 2;
        long capacity = ConfigLoader.getInstance().addonSettings.get(i).capacitorCapacity();
        long rate = ConfigLoader.getInstance().addonSettings.get(i).capacitorRate();
        return new TierAddonBlock(
                MachineAddonBlock.AddonSettings.getDefaultSettings()
                        .withAddedCapacity(capacity)
                        .withAddedInsert(rate)
                        .withNeedsSupport(true)
                        .withBoundingShape(USABLE_ADDON_SHAPE), tier);
    }

    private static Block acceptorAddonBlock(String name, int tier) {
        String[] split = name.split("_");
        int i = Integer.parseInt(split[split.length - 1]) - 2;
        long capacity = ConfigLoader.getInstance().addonSettings.get(i).acceptorCapacity();
        long rate = ConfigLoader.getInstance().addonSettings.get(i).acceptorRate();
        return new TierAddonBlock(
                MachineAddonBlock.AddonSettings.getDefaultSettings()
                        .withAddedCapacity(capacity)
                        .withAddedInsert(rate)
                        .withAcceptEnergy(true)
                        .withNeedsSupport(true)
                        .withBoundingShape(USABLE_ADDON_SHAPE), tier);
    }


    private static Block efficientSpeedAddonBlock(String name, int tier) {
        String[] split = name.split("_");
        int i = Integer.parseInt(split[split.length - 1]) - 2;
        float speedMultiplier = ConfigLoader.getInstance().addonSettings.get(i).speedMultiplier();
        float efficiencyMultiplier = ConfigLoader.getInstance().addonSettings.get(i).efficiencyUp();
        return new TierAddonBlock(
                MachineAddonBlock.AddonSettings.getDefaultSettings()
                        .withSpeedMultiplier(speedMultiplier)
                        .withEfficiencyMultiplier(efficiencyMultiplier)
                        .withNeedsSupport(true)
                        .withBoundingShape(USABLE_ADDON_SHAPE), tier);
    }

    private static Block speedAddonBlock(String name, int tier) {
        String[] split = name.split("_");
        int i = Integer.parseInt(split[split.length - 1]) - 2;
        float speedMultiplier = ConfigLoader.getInstance().addonSettings.get(i).speedMultiplier();
        float efficiencyMultiplier = ConfigLoader.getInstance().addonSettings.get(i).efficiencyDown();
        return new TierAddonBlock(
                MachineAddonBlock.AddonSettings.getDefaultSettings()
                        .withSpeedMultiplier(speedMultiplier)
                        .withEfficiencyMultiplier(efficiencyMultiplier)
                        .withNeedsSupport(true)
                        .withBoundingShape(USABLE_ADDON_SHAPE), tier);
    }

    private static MachineAddonBlock efficiencyAddonBlock(String name, int tier) {
        String[] split = name.split("_");
        int i = Integer.parseInt(split[split.length - 1]) - 2;
        float efficiencyMultiplier = ConfigLoader.getInstance().addonSettings.get(i).efficiencyUp();
        return new TierAddonBlock(
                MachineAddonBlock.AddonSettings.getDefaultSettings()
                        .withEfficiencyMultiplier(efficiencyMultiplier)
                        .withNeedsSupport(true)
                        .withBoundingShape(USABLE_ADDON_SHAPE), tier);
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
