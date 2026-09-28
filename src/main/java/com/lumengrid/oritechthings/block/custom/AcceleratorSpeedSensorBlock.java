package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.lumengrid.oritechthings.util.Utility;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
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
import java.util.function.Consumer;

public class AcceleratorSpeedSensorBlock extends BaseEntityBlock implements TooltipProvider {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public AcceleratorSpeedSensorBlock() {
        this(BlockBehaviour.Properties.of());
    }

    public AcceleratorSpeedSensorBlock(BlockBehaviour.Properties properties) {
        super(properties
                .strength(2.0F)
                .requiresCorrectToolForDrops()
                .lightLevel(state -> state.getValue(POWERED) ? 1 : 0)
                .noOcclusion()
                .isRedstoneConductor((state, blockGetter, pos) -> false));

        registerDefaultState(stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(0, 0, 0, 16, 8, 16);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        MenuProvider menuProvider = state.getMenuProvider(level, pos);
        if (menuProvider == null) {
            return InteractionResult.PASS;
        }

        player.openMenu(menuProvider);
        return InteractionResult.SUCCESS;
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
        return (tickerLevel, tickerPos, tickerState, blockEntity) -> {
            if (blockEntity instanceof AcceleratorSpeedSensorBlockEntity sensor) {
                AcceleratorSpeedSensorBlockEntity.tick(tickerLevel, tickerPos, tickerState, sensor);
            }
        };
    }

    @Override
    public int getSignal(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public int getDirectSignal(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return state.getValue(POWERED) ? 15 : 0;
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
    public void addToTooltip(
            Item.TooltipContext tooltipContext,
            Consumer<Component> consumer,
            TooltipFlag tooltipFlag,
            DataComponentGetter dataComponentGetter
    ) {
        consumer.accept(Component.translatable("tooltip.oritechthings.particle_accelerator_speed_sensor").withStyle(ChatFormatting.GRAY));
        consumer.accept(Component.empty());

        if (Utility.isControlDown()) {
            consumer.accept(Component.translatable("tooltip.oritechthings.particle_accelerator_speed_sensor.target_designator_usage").withStyle(ChatFormatting.GRAY));
            consumer.accept(Component.translatable("tooltip.oritechthings.particle_accelerator_speed_sensor.target_designator_step1").withStyle(ChatFormatting.BLUE));
            consumer.accept(Component.translatable("tooltip.oritechthings.particle_accelerator_speed_sensor.target_designator_step2").withStyle(ChatFormatting.BLUE));
            consumer.accept(Component.translatable("tooltip.oritechthings.particle_accelerator_speed_sensor.target_designator_step3").withStyle(ChatFormatting.BLUE));
            consumer.accept(Component.translatable("tooltip.oritechthings.particle_accelerator_speed_sensor.target_designator_benefit").withStyle(ChatFormatting.GOLD));
        } else {
            consumer.accept(Component.translatable("tooltip.oritech.item_extra_info").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
        }
    }
}