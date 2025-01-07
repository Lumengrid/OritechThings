package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.entity.custom.TierAddonBlockEntity;
import com.lumengrid.oritechthings.util.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;
import rearth.oritech.util.TooltipHelper;

import java.util.List;

public class TierAddonBlock extends MachineAddonBlock {
    public static final EnumProperty<Constants.AddonType> ADDON_TYPE = EnumProperty.create("addon_type", Constants.AddonType.class);
    public static final IntegerProperty ADDON_TIER = IntegerProperty.create("tier", 2, 9);

    public TierAddonBlock(AddonSettings addonSettings, int tier, Constants.AddonType type) {
        super(Properties.of().strength(2f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(ADDON_USED) ? 10 : 0), addonSettings);
        this.registerDefaultState(this.stateDefinition.any().setValue(ADDON_USED, false).setValue(ADDON_TIER, tier).setValue(ADDON_TYPE, type));
    }

    public IntegerProperty getAddonTier() {
        return ADDON_TIER;
    }
    public EnumProperty<Constants.AddonType> getAddonType() {
        return ADDON_TYPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        try {
            return new TierAddonBlockEntity(pos, state);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction dir = switch (ctx.getClickedFace()) {
            case DOWN, UP, NORTH -> Direction.NORTH;
            case SOUTH -> Direction.SOUTH;
            case WEST -> Direction.WEST;
            case EAST -> Direction.EAST;
        };
        Direction face = ctx.getClickedFace();
        AttachFace f = switch (face) {
            case DOWN -> AttachFace.CEILING;
            case UP -> AttachFace.FLOOR;
            case NORTH, EAST, WEST, SOUTH -> AttachFace.WALL;
        };

        return defaultBlockState().setValue(FACING, dir).setValue(FACE, f);
    }

    @NotNull
    public Class<? extends BlockEntity> getBlockEntityType() {
        return TierAddonBlockEntity.class;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.addonSettings.boundingShape()[state.getValue(FACING).ordinal()][state.getValue(FACE).ordinal()];
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ADDON_USED, FACING, FACE, ADDON_TIER, ADDON_TYPE);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        tooltip.add(Component.literal("Tier " + this.defaultBlockState().getValue(ADDON_TIER)).withStyle(ChatFormatting.AQUA));
        if (Screen.hasControlDown()) {
            if (addonSettings.speedMultiplier() != 1) {
                var displayedNumber = (int) ((1 / addonSettings.speedMultiplier()) * 100) - 100;
                tooltip.add(Component.translatable("tooltip.oritech.addon_speed_desc").withStyle(ChatFormatting.DARK_GRAY)
                        .append(TooltipHelper.getFormattedValueChangeTooltip(displayedNumber)));
            }

            if (addonSettings.efficiencyMultiplier() != 1) {
                var displayedNumber = (int) ((1 / addonSettings.efficiencyMultiplier()) * 100) - 100;
                tooltip.add(Component.translatable("tooltip.oritech.addon_efficiency_desc").withStyle(ChatFormatting.DARK_GRAY)
                        .append(TooltipHelper.getFormattedValueChangeTooltip(displayedNumber)));
            }

            if (addonSettings.addedCapacity() != 0) {
                tooltip.add(
                        Component.translatable("tooltip.oritech.addon_capacity_desc").withStyle(ChatFormatting.DARK_GRAY)
                                .append(TooltipHelper.getFormattedEnergyChangeTooltip(addonSettings.addedCapacity(), " RF")));
            }

            if (addonSettings.addedInsert() != 0) {
                tooltip.add(Component.translatable("tooltip.oritech.addon_transfer_desc").withStyle(ChatFormatting.DARK_GRAY)
                        .append(TooltipHelper.getFormattedEnergyChangeTooltip(addonSettings.addedInsert(), " RF/t")));
            }

            if (addonSettings.chamberCount() > 1) {
                tooltip.add(Component.translatable("tooltip.oritechthings.tiered_addons.chambers_desc").withStyle(ChatFormatting.DARK_GRAY)
                        .append(Component.literal("+" + (addonSettings.chamberCount() - 1)).withStyle(ChatFormatting.GREEN)));
            }
        } else {
            tooltip.add(Component.translatable("tooltip.oritech.item_extra_info").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }
    }

}
