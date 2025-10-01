package com.lumengrid.oritechthings.block;

import com.lumengrid.oritechthings.block.custom.AcceleratorSpeedSensorBlock;
import com.lumengrid.oritechthings.block.custom.InfestedAmethystBlock;
import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.main.ConfigHelper;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;
import com.lumengrid.oritechthings.util.Constants.NameUtil;
import com.lumengrid.oritechthings.util.ShapeUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;
import rearth.oritech.util.Geometry;

import java.util.function.Supplier;

public class ModBlocks {

        public static void register(IEventBus bus){
                OTHER.register(bus);
                ADDONS.register(bus);
        }

    public static final DeferredRegister.Blocks OTHER = DeferredRegister.createBlocks(OritechThings.MOD_ID);
    public static final DeferredRegister.Blocks ADDONS = DeferredRegister.createBlocks(OritechThings.MOD_ID);

    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_2 = speedAddonBuilder(2);
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_3 = speedAddonBuilder(3);
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_4 = speedAddonBuilder(4);
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_5 = speedAddonBuilder(5);
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_6 = speedAddonBuilder(6);
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_7 = speedAddonBuilder(7);
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_8 = speedAddonBuilder(8);
    public static final DeferredBlock<Block> ADDON_BLOCK_SPEED_TIER_9 = speedAddonBuilder(9);

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_2 = efficientSpeedAddonBuilder(2);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_3 = efficientSpeedAddonBuilder(3);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_4 = efficientSpeedAddonBuilder(4);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_5 = efficientSpeedAddonBuilder(5);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_6 = efficientSpeedAddonBuilder(6);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_7 = efficientSpeedAddonBuilder(7);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_8 = efficientSpeedAddonBuilder(8);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENT_SPEED_TIER_9 = efficientSpeedAddonBuilder(9);

    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_2 = efficiencyAddonBuilder(2);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_3 = efficiencyAddonBuilder(3);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_4 = efficiencyAddonBuilder(4);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_5 = efficiencyAddonBuilder(5);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_6 = efficiencyAddonBuilder(6);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_7 = efficiencyAddonBuilder(7);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_8 = efficiencyAddonBuilder(8);
    public static final DeferredBlock<Block> ADDON_BLOCK_EFFICIENCY_TIER_9 = efficiencyAddonBuilder(9);

    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_2 = capacitorAddonBuilder(2);
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_3 = capacitorAddonBuilder(3);
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_4 = capacitorAddonBuilder(4);
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_5 = capacitorAddonBuilder(5);
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_6 = capacitorAddonBuilder(6);
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_7 = capacitorAddonBuilder(7);
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_8 = capacitorAddonBuilder(8);
    public static final DeferredBlock<Block> ADDON_BLOCK_CAPACITOR_TIER_9 = capacitorAddonBuilder(9);

    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_2 = acceptorAddonBuilder(2);
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_3 = acceptorAddonBuilder(3);
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_4 = acceptorAddonBuilder(4);
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_5 = acceptorAddonBuilder(5);
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_6 = acceptorAddonBuilder(6);
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_7 = acceptorAddonBuilder(7);
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_8 = acceptorAddonBuilder(8);
    public static final DeferredBlock<Block> ADDON_BLOCK_ACCEPTOR_TIER_9 = acceptorAddonBuilder(9);

    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_2 = processingAddonBuilder(2);
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_3 = processingAddonBuilder(3);
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_4 = processingAddonBuilder(4);
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_5 = processingAddonBuilder(5);
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_6 = processingAddonBuilder(6);
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_7 = processingAddonBuilder(7);
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_8 = processingAddonBuilder(8);
    public static final DeferredBlock<Block> ADDON_BLOCK_PROCESSING_TIER_9 = processingAddonBuilder(9);

    public static final DeferredBlock<Block> ACCELERATOR_SPEED_SENSOR = registerBlock(
            "particle_accelerator_speed_sensor", AcceleratorSpeedSensorBlock::new);

    public static final DeferredBlock<Block> INFESTED_AMETHYST_BLOCK = registerBlock(
            "infested_amethyst_block", () -> new InfestedAmethystBlock(Blocks.AMETHYST_BLOCK,
                    BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(1.5F).sound(SoundType.AMETHYST))
    );
    private static DeferredBlock<Block> processingAddonBuilder(int tier) {
        return registerAddon(
                NameUtil.genAddonName(NameUtil.Type.PROCESSING, tier), () -> {
                    var addonInfo = ConfigHelper.getAddonInfo(tier - 2);
                    return new TierAddonBlock(
                            MachineAddonBlock.AddonSettings.getDefaultSettings()
                                    .withEfficiencyMultiplier(addonInfo.processingEfficiency())
                                    .withChambers(addonInfo.processingChambers())
                                    .withNeedsSupport(true)
                                    .withBoundingShape(generateAddonShape(7)),
                            tier, Constants.AddonType.PROCESSING);
                });
    }

    private static DeferredBlock<Block> capacitorAddonBuilder(int tier) {
        return registerAddon(
                NameUtil.genAddonName(NameUtil.Type.CAPACITOR, tier), () -> {
                    var addonInfo = ConfigHelper.getAddonInfo(tier - 2);
                    return new TierAddonBlock(
                            MachineAddonBlock.AddonSettings.getDefaultSettings()
                                    .withAddedCapacity(addonInfo.capacitorCapacity())
                                    .withAddedInsert(addonInfo.capacitorRate())
                                    .withNeedsSupport(true)
                                    .withBoundingShape(generateAddonShape(6)),
                            tier, Constants.AddonType.CAPACITOR);
                });
    }

    private static DeferredBlock<Block> acceptorAddonBuilder(int tier) {
        return registerAddon(
                NameUtil.genAddonName(NameUtil.Type.ACCEPTOR, tier), () -> {
                    var addonInfo = ConfigHelper.getAddonInfo(tier - 2);
                    return new TierAddonBlock(
                            MachineAddonBlock.AddonSettings.getDefaultSettings()
                                    .withAddedCapacity(addonInfo.acceptorCapacity())
                                    .withAddedInsert(addonInfo.acceptorRate())
                                    .withAcceptEnergy(true)
                                    .withNeedsSupport(true)
                                    .withBoundingShape(generateAddonShape(8)),
                            tier, Constants.AddonType.ACCEPTOR);
                });
    }

    private static DeferredBlock<Block> efficientSpeedAddonBuilder(int tier) {
        return registerAddon(
                NameUtil.genAddonName(NameUtil.Type.EFFICIENT + NameUtil.Type.SPEED, tier), () -> {
                    var addonInfo = ConfigHelper.getAddonInfo(tier - 2);
                    return new TierAddonBlock(
                            MachineAddonBlock.AddonSettings.getDefaultSettings()
                                    .withSpeedMultiplier(addonInfo.speedMultiplier())
                                    .withEfficiencyMultiplier(addonInfo.efficiencyUp())
                                    .withNeedsSupport(true)
                                    .withBoundingShape(generateAddonShape(2)),
                            tier, Constants.AddonType.EFFICIENT_SPEED);
                });
    }

    private static DeferredBlock<Block> speedAddonBuilder(int tier) {
        return registerAddon(
                NameUtil.genAddonName(NameUtil.Type.SPEED, tier), () -> {
                    var addonInfo = ConfigHelper.getAddonInfo(tier - 2);
                    return new TierAddonBlock(
                            MachineAddonBlock.AddonSettings.getDefaultSettings()
                                    .withSpeedMultiplier(addonInfo.speedMultiplier())
                                    .withEfficiencyMultiplier(addonInfo.efficiencyDown())
                                    .withNeedsSupport(true)
                                    .withBoundingShape(generateAddonShape(2)),
                            tier, Constants.AddonType.SPEED);
                });
    }

    private static DeferredBlock<Block> efficiencyAddonBuilder(int tier) {
        return registerAddon(
                NameUtil.genAddonName(NameUtil.Type.EFFICIENCY, tier), () -> {
                    var addonInfo = ConfigHelper.getAddonInfo(tier - 2);
                    return new TierAddonBlock(
                            MachineAddonBlock.AddonSettings.getDefaultSettings()
                                    .withEfficiencyMultiplier(addonInfo.efficiencyUp())
                                    .withNeedsSupport(true)
                                    .withBoundingShape(generateAddonShape(5)),
                            tier, Constants.AddonType.EFFICIENCY);
                });
    }

    private static VoxelShape[][] generateAddonShape(int y) {
        VoxelShape[][] shape = new VoxelShape[Direction.values().length][AttachFace.values().length];

        for (Direction facing : Direction.values()) {
            if (facing.getAxis().isHorizontal()) {
                AttachFace[] faces = AttachFace.values();

                for (AttachFace face : faces) {
                    shape[facing.ordinal()][face.ordinal()] = Shapes.or(
                            Geometry.rotateVoxelShape(ShapeUtil.shapeFromDimension(1, 0, 1, 14, y, 14), facing, face),
                            Geometry.rotateVoxelShape(ShapeUtil.shapeFromDimension(3, 2, 3, 10, y, 10), facing, face));
                }
            }
        }
        return shape;
    }

    private static <T extends Block> DeferredBlock<T> registerAddon(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = ADDONS.register(name, block);
        registerBlockItem(name, toReturn,ModItems.ADDONS);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = OTHER.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.BLOCKITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block,DeferredRegister.Items items) {
        items.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

}
