package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.entity.custom.TierAddonBlockEntity;
import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.util.Constants;
import com.lumengrid.oritechthings.util.Utility;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

import static com.lumengrid.oritechthings.main.OritechThings.MOD_ID;

import java.util.function.Consumer;

public class TierAddonBlock extends MachineAddonBlock {
    public static final EnumProperty<Constants.AddonType> ADDON_TYPE = EnumProperty.create("addon_type", Constants.AddonType.class);
    public static final IntegerProperty ADDON_TIER = IntegerProperty.create("tier", 2, 9);

    public TierAddonBlock(BlockBehaviour.Properties properties, AddonSettings addonSettings, int tier, Constants.AddonType type) {
        super(properties
                        .strength(2f)
                        .requiresCorrectToolForDrops()
                        .lightLevel(state -> state.getValue(ADDON_USED) ? 10 : 0),
                addonSettings);
        this.registerDefaultState(this.stateDefinition.any().setValue(ADDON_USED, false).setValue(ADDON_TIER, tier).setValue(ADDON_TYPE, type));
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
        return super.getStateForPlacement(ctx);
    }

    @NotNull
    public Class<? extends BlockEntity> getBlockEntityType() {
        return TierAddonBlockEntity.class;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.addonSettings.boundingShape()[state.getValue(FACING).ordinal()][state.getValue(FACE).ordinal()];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ADDON_USED, FACING, FACE, ADDON_TIER, ADDON_TYPE);
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        if (this.defaultBlockState().getValue(ADDON_TYPE).toString().equals(Constants.AddonType.CROSS_DIMENSIONAL.toString())) {
            if (!ConfigLoader.getInstance().dimensionalDroneSettings.enabled()) {
                consumer.accept(Component.translatable("tooltip." + MOD_ID + ".addon_block_cross_dimensional_disabled")
                        .withStyle(ChatFormatting.RED));
                return;
            }

            if (Utility.isControlDown()) {
                consumer.accept(Component.translatable("tooltip." + MOD_ID + ".addon_block_cross_dimensional").withStyle(ChatFormatting.DARK_GRAY));
            } else {
                consumer.accept(Component.translatable("tooltip.oritech.item_extra_info").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            }
        } else {
            consumer.accept(Component.translatable("tooltip." + MOD_ID + ".tier_addon").withStyle(ChatFormatting.AQUA).append(
                    Component.literal(this.defaultBlockState().getValue(ADDON_TIER).toString()).withStyle(ChatFormatting.AQUA)));
            if (Utility.isControlDown()) {
                if (addonSettings.speedMultiplier() != 1) {
                    var displayedNumber = Math.round((1 - addonSettings.speedMultiplier()) * 100);
                    consumer.accept(Component.translatable("tooltip.oritech.addon_speed_desc").withStyle(ChatFormatting.DARK_GRAY)
                            .append(TooltipHelper.getFormattedValueChangeTooltip(displayedNumber)));
                }

                if (addonSettings.efficiencyMultiplier() != 1) {
                    var displayedNumber = Math.round((1 - addonSettings.efficiencyMultiplier()) * 100);
                    consumer.accept(Component.translatable("tooltip.oritech.addon_efficiency_desc").withStyle(ChatFormatting.DARK_GRAY)
                            .append(TooltipHelper.getFormattedValueChangeTooltip(displayedNumber)));
                }

                if (addonSettings.addedCapacity() != 0) {
                    consumer.accept(
                            Component.translatable("tooltip.oritech.addon_capacity_desc").withStyle(ChatFormatting.DARK_GRAY)
                                    .append(TooltipHelper.getFormattedEnergyChangeTooltip(addonSettings.addedCapacity(), " RF")));
                }

                if (addonSettings.addedInsert() != 0) {
                    consumer.accept(Component.translatable("tooltip.oritech.addon_transfer_desc").withStyle(ChatFormatting.DARK_GRAY)
                            .append(TooltipHelper.getFormattedEnergyChangeTooltip(addonSettings.addedInsert(), " RF/t")));
                }

                if (addonSettings.chamberCount() > 1) {
                    consumer.accept(Component.translatable("tooltip.oritechthings.tiered_addons.chambers_desc").withStyle(ChatFormatting.DARK_GRAY)
                            .append(Component.literal("+" + (addonSettings.chamberCount() - 1)).withStyle(ChatFormatting.GREEN)));
                }
            } else {
                consumer.accept(Component.translatable("tooltip.oritech.item_extra_info").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            }
        }
    }
}