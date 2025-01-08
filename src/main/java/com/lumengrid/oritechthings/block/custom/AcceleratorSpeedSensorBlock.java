package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.lumengrid.oritechthings.item.ModItems;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
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

import javax.annotation.Nullable;
import java.util.List;

public class AcceleratorSpeedSensorBlock extends Block implements EntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public AcceleratorSpeedSensorBlock() {
        super(Properties.of().strength(2f).requiresCorrectToolForDrops()
                .lightLevel(state -> state.getValue(POWERED) ? 1 : 0)
                .isRedstoneConductor((state, blockGetter, pos) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(
            @NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult
    ) {
        System.out.println("useItemOn");
        if (level.isClientSide) return ItemInteractionResult.SUCCESS;
        BlockEntity speedSensor = level.getBlockEntity(pos);
        if(!(speedSensor instanceof AcceleratorSpeedSensorBlockEntity speedSensorEntity)) {
            System.out.println("useItemOn not on a AcceleratorSpeedSensorBlock");
            return ItemInteractionResult.SUCCESS;
        }
        if (stack.is(Items.REDSTONE_TORCH)) {
            speedSensorEntity.setEnabled(!speedSensorEntity.isEnabled());
            player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_speed_sensor." + (speedSensorEntity.isEnabled() ? "enable" : "disable"))
                    .withStyle(speedSensorEntity.isEnabled() ? ChatFormatting.GREEN : ChatFormatting.RED));
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
    public void attack(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player) {
        if (!level.isClientSide() && player.getMainHandItem().isEmpty()) {
            BlockEntity speedControl = level.getBlockEntity(pos);
            if(!(speedControl instanceof AcceleratorSpeedSensorBlockEntity speedControlEntity)) {
                System.out.println("attack not on a AcceleratorSpeedSensorBlock");
                return;
            }
            player.sendSystemMessage(
                            Component.translatable("block.oritechthings.accelerator_speed_sensor." + (speedControlEntity.isEnabled() ? "enable" : "disable"))
                                    .withStyle(speedControlEntity.isEnabled() ? ChatFormatting.GREEN : ChatFormatting.RED)
                            .append(Component.translatable("block.oritechthings.accelerator_speed_sensor.speed_set").withStyle(ChatFormatting.WHITE)
                                    .append(Component.literal(String.valueOf(speedControlEntity.getSpeedLimit())).withStyle(ChatFormatting.GOLD)))
                            .append(
                                    speedControlEntity.getTargetDesignator() == null
                                            ?
                                            Component.translatable("block.oritechthings.accelerator_speed_sensor.controller_not_set").withStyle(ChatFormatting.RED)
                                            :
                                            Component.translatable("block.oritechthings.accelerator_speed_sensor.controller_set").withStyle(ChatFormatting.WHITE)
                                                .append(Component.literal(speedControlEntity.getTargetDesignator().toShortString()).withStyle(ChatFormatting.BLUE))));
        }
    }


    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if(!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof AcceleratorSpeedSensorBlockEntity acceleratorEntity) {
                float speed = acceleratorEntity.getSpeedLimit();
                float toAdd = (player.isShiftKeyDown() || player.isCrouching()) ? 1000F : 100F;
                if (speed + toAdd > 20000F) speed = 0F;
                speed += toAdd;
                acceleratorEntity.setSpeedLimit(speed);
                level.playSound(player, pos, SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM, SoundSource.BLOCKS, 1f, 1f);
                player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_speed_sensor.speed_set")
                                .append(Component.literal(String.valueOf(speed)).withStyle(ChatFormatting.GOLD)));
            }
        }
        System.out.println("useWithoutItem");

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
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, List<Component> tooltip, @NotNull TooltipFlag options) {
        tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_speed_sensor"));
    }
}
