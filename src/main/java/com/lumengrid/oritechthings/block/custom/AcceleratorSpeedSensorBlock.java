package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.lumengrid.oritechthings.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import org.jetbrains.annotations.NotNull;
import rearth.oritech.block.entity.accelerator.AcceleratorControllerBlockEntity;
import rearth.oritech.init.ComponentContent;

import javax.annotation.Nullable;

public class AcceleratorSpeedSensorBlock extends BaseEntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public AcceleratorSpeedSensorBlock() {
        super(Properties.of().strength(2f).requiresCorrectToolForDrops()
                .lightLevel(state -> state.getValue(POWERED) ? 1 : 0)
                .isRedstoneConductor((state, blockGetter, pos) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    public AcceleratorSpeedSensorBlock(BlockBehaviour.Properties p) {
        this();
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(
            @NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level,
            @NotNull BlockPos pos, @NotNull Player player,
            @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult
    ) {
        if (level.isClientSide) return ItemInteractionResult.SUCCESS;
        BlockEntity speedSensor = level.getBlockEntity(pos);
        if(!(speedSensor instanceof AcceleratorSpeedSensorBlockEntity speedSensorEntity)) {
            return ItemInteractionResult.SUCCESS;
        }

        if (!stack.is(ModItems.ACCELERATOR_TARGET_DESIGNATOR)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        BlockPos targetPos = stack.get(ComponentContent.TARGET_POSITION.get());
        BlockEntity blockEntity = targetPos == null ? null : level.getBlockEntity(targetPos);
        if(targetPos == null || !(blockEntity instanceof AcceleratorControllerBlockEntity)) {
            player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_speed_sensor.invalid_controller").withStyle(ChatFormatting.RED));
            return ItemInteractionResult.SUCCESS;
        }
        int distance = targetPos.distManhattan(pos);
        if (distance > 128) {
            player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_speed_sensor.invalid_controller.to_far")
                            .append(Component.literal(" (" + distance + ")").withStyle(ChatFormatting.ITALIC)) .withStyle(ChatFormatting.RED));
            return ItemInteractionResult.SUCCESS;
        }
        speedSensorEntity.setTargetDesignator(targetPos);
        speedSensorEntity.setEnabled(true);
        level.playSound(player, pos, SoundEvents.ALLAY_AMBIENT_WITH_ITEM, SoundSource.BLOCKS, 1f, 1f);
        player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_speed_sensor.controller_set")
                        .append(Component.literal(targetPos.toShortString()).withStyle(ChatFormatting.BLUE)));
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return AcceleratorSpeedSensorBlockEntity::tick;
    }

    @Override
    public int getSignal(@NotNull BlockState blockState, @NotNull BlockGetter blockAccess, @NotNull BlockPos pos, @NotNull Direction side) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public int getDirectSignal(@NotNull BlockState blockState, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
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
}
