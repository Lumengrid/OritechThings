package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedControlBlockEntity;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.block.entity.accelerator.AcceleratorControllerBlockEntity;
import rearth.oritech.init.ComponentContent;
import rearth.oritech.init.ItemContent;

import javax.annotation.Nullable;

public class AcceleratorSpeedControlBlock extends Block implements EntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public AcceleratorSpeedControlBlock() {
        super(Properties.of().strength(2f).requiresCorrectToolForDrops()
                .lightLevel(state -> state.getValue(POWERED) ? 15 : 0)
                .isRedstoneConductor((state, blockGetter, pos) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(
            @NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult
    ) {
        BlockEntity speedControl = level.getBlockEntity(pos);
        if(!(speedControl instanceof AcceleratorSpeedControlBlockEntity speedControlEntity)) {
            System.out.println("useItemOn not on a AcceleratorSpeedControlBlock");
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
        if (!stack.is(ItemContent.TARGET_DESIGNATOR)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        BlockPos targetPos = stack.get(ComponentContent.TARGET_POSITION.get());
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(targetPos == null || !(blockEntity instanceof AcceleratorControllerBlockEntity)) {
            player.sendSystemMessage(
                    Component.translatable("block.oritechthings.accelerator_speed_control")
                            .append(Component.translatable("block.oritechthings.accelerator_speed_control.invalid_controller")
                                    .withStyle(ChatFormatting.RED)));
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
        if (targetPos.distManhattan(pos) > 128) {
            player.sendSystemMessage(
                    Component.translatable("block.oritechthings.accelerator_speed_control")
                            .append(Component.translatable("block.oritechthings.accelerator_speed_control.invalid_controller.to_far")
                                    .withStyle(ChatFormatting.RED)));
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
        speedControlEntity.setTargetDesignator(targetPos);
        level.playSound(player, pos, SoundEvents.ALLAY_THROW, SoundSource.BLOCKS, 1f, 1f);
        player.sendSystemMessage(
                Component.translatable("block.oritechthings.accelerator_speed_control")
                        .append(Component.translatable("block.oritechthings.accelerator_speed_control.controller_set"))
                        .append(Component.literal(String.valueOf(targetPos)).withStyle(ChatFormatting.GOLD)));
        return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if(!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof AcceleratorSpeedControlBlockEntity acceleratorEntity) {
                float speed = acceleratorEntity.getSpeedLimit();
                if (speed + 1F > 20000F) speed = 0F;
                speed += player.isShiftKeyDown() ? 1000F : 100F;
                acceleratorEntity.setSpeedLimit(speed);
                level.playSound(player, pos, SoundEvents.ALLAY_THROW, SoundSource.BLOCKS, 1f, 1f);
                player.sendSystemMessage(
                        Component.translatable("block.oritechthings.accelerator_speed_control")
                                .append(Component.translatable("block.oritechthings.accelerator_speed_control.speed_set"))
                                .append(Component.literal(String.valueOf(speed)).withStyle(ChatFormatting.RED)));
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new AcceleratorSpeedControlBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return AcceleratorSpeedControlBlockEntity::tick;
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
}
