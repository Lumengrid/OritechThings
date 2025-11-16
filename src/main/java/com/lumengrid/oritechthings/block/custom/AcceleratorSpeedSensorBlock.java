package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.lumengrid.oritechthings.util.ShapeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class AcceleratorSpeedSensorBlock extends BaseEntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public AcceleratorSpeedSensorBlock() {
        super(Properties.of().strength(2f).requiresCorrectToolForDrops()
                .lightLevel(state -> state.getValue(POWERED) ? 1 : 0)
                .noOcclusion()
                .isRedstoneConductor((state, blockGetter, pos) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    public AcceleratorSpeedSensorBlock(BlockBehaviour.Properties p) {
        this();
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(0, 0, 0, 16, 8, 16);
    }

    @SuppressWarnings("null")
    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
            @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS_NO_ITEM_USED;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof AcceleratorSpeedSensorBlockEntity)) {
            return InteractionResult.SUCCESS_NO_ITEM_USED;
        }

        player.openMenu(state.getMenuProvider(level, pos), p -> p.writeBlockPos(pos));

        return InteractionResult.SUCCESS_NO_ITEM_USED;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new AcceleratorSpeedSensorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state,
            @NotNull BlockEntityType<T> type) {
        return AcceleratorSpeedSensorBlockEntity::tick;
    }

    @Override
    public int getSignal(@NotNull BlockState blockState, @NotNull BlockGetter blockAccess, @NotNull BlockPos pos,
            @NotNull Direction side) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public int getDirectSignal(@NotNull BlockState blockState, @NotNull BlockGetter level, @NotNull BlockPos pos,
            @NotNull Direction direction) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState state) {
        return true;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(AcceleratorSpeedSensorBlock::new);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context,
            @NotNull List<Component> tooltip,
            @NotNull TooltipFlag options) {
        tooltip.add(Component.translatable("tooltip.oritechthings.particle_accelerator_speed_sensor")
                .withStyle(net.minecraft.ChatFormatting.GRAY));
        tooltip.add(Component.empty());

        if (Screen.hasControlDown()) {
            tooltip.add(Component
                    .translatable("tooltip.oritechthings.particle_accelerator_speed_sensor.target_designator_usage")
                    .withStyle(net.minecraft.ChatFormatting.GRAY));
            tooltip.add(Component
                    .translatable("tooltip.oritechthings.particle_accelerator_speed_sensor.target_designator_step1")
                    .withStyle(net.minecraft.ChatFormatting.BLUE));
            tooltip.add(Component
                    .translatable("tooltip.oritechthings.particle_accelerator_speed_sensor.target_designator_step2")
                    .withStyle(net.minecraft.ChatFormatting.BLUE));
            tooltip.add(Component
                    .translatable("tooltip.oritechthings.particle_accelerator_speed_sensor.target_designator_step3")
                    .withStyle(net.minecraft.ChatFormatting.BLUE));
            tooltip.add(Component
                    .translatable("tooltip.oritechthings.particle_accelerator_speed_sensor.target_designator_benefit")
                    .withStyle(net.minecraft.ChatFormatting.GOLD));
        } else {
            tooltip.add(Component.translatable("tooltip.oritech.item_extra_info")
                    .withStyle(net.minecraft.ChatFormatting.DARK_GRAY).withStyle(net.minecraft.ChatFormatting.ITALIC));
        }

        super.appendHoverText(stack, context, tooltip, options);
    }
}
