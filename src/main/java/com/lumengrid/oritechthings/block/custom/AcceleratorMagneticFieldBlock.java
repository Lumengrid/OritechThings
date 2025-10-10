package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.entity.custom.AcceleratorMagneticFieldBlockEntity;
import com.lumengrid.oritechthings.main.ConfigLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rearth.oritech.api.energy.EnergyApi;
import rearth.oritech.block.base.entity.ExpandableEnergyStorageBlockEntity;
import rearth.oritech.util.ComparatorOutputProvider;
import rearth.oritech.util.MachineAddonController;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;

import java.util.List;
import java.util.Objects;

public class AcceleratorMagneticFieldBlock extends Block implements EntityBlock {
    
    public static final DirectionProperty TARGET_DIR = DirectionProperty.create("target_dir");
    
    public AcceleratorMagneticFieldBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(3.0f, 6.0f)
                .requiresCorrectToolForDrops()
                .noOcclusion());
        this.registerDefaultState(defaultBlockState().setValue(TARGET_DIR, Direction.NORTH));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TARGET_DIR);
    }
    
    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
        return Objects.requireNonNull(super.getStateForPlacement(ctx)).setValue(TARGET_DIR, ctx.getNearestLookingDirection().getOpposite());
    }
    
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new AcceleratorMagneticFieldBlockEntity(pos, state);
    }
    
    @Override
    protected boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }
    
    @Override
    protected int getAnalogOutputSignal(@NotNull BlockState state, Level world, @NotNull BlockPos pos) {
        return ((ComparatorOutputProvider) Objects.requireNonNull(world.getBlockEntity(pos))).getComparatorOutput();
    }
    
    @Override
    public boolean isSignalSource(@NotNull BlockState state) {
        return true;
    }
    
    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Block sourceBlock, @NotNull BlockPos sourcePos, boolean notify) {
        super.neighborChanged(state, world, pos, sourceBlock, sourcePos, notify);
        
        if (world.isClientSide) return;
        
        var isPowered = world.hasNeighborSignal(pos);
        
        var storageEntity = (ExpandableEnergyStorageBlockEntity) world.getBlockEntity(pos);
        assert storageEntity != null;
        storageEntity.setRedstonePowered(isPowered);
    }
    
    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
        
        if (!world.isClientSide) {
            
            var entity = world.getBlockEntity(pos);
            if (!(entity instanceof MachineAddonController machineEntity)) {
                return InteractionResult.SUCCESS;
            }
            
            machineEntity.initAddons();
            
            var handler = (ExtendedMenuProvider) world.getBlockEntity(pos);
            MenuRegistry.openExtendedMenu((ServerPlayer) player, handler);
        }
        
        return InteractionResult.SUCCESS;
    }
    
    @Override
    protected @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.@NotNull Builder builder) {
        var droppedStacks = super.getDrops(state, builder);

        var blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof AcceleratorMagneticFieldBlockEntity storageEntity) {
            droppedStacks.addAll(storageEntity.inventory.getHeldStacks());
            storageEntity.inventory.clearContent();
        }

        return droppedStacks;
    }
    
    @NotNull
    private static ItemStack getStackWithData(LevelReader world, BlockPos pos) {
        var stack = new ItemStack(com.lumengrid.oritechthings.block.ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get().asItem());
        
        var storageEntity = (AcceleratorMagneticFieldBlockEntity) world.getBlockEntity(pos);
        assert storageEntity != null;
        if (storageEntity.getEnergyStorage(null).getAmount() > 0) {
            stack.set(EnergyApi.ITEM.getEnergyComponent(), storageEntity.getEnergyStorage(null).getAmount());
        }
        
        return stack;
    }
    
    @Override
    public void setPlacedBy(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        
        long storedEnergyInStack = itemStack.getOrDefault(EnergyApi.ITEM.getEnergyComponent(), 0L);
        
        if (storedEnergyInStack > 0) {
            var storageEntity = (ExpandableEnergyStorageBlockEntity) world.getBlockEntity(pos);
            assert storageEntity != null;
            storageEntity.energyStorage.setAmount(storedEnergyInStack);
        }
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level world, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof BlockEntityTicker ticker)
                ticker.tick(world1, pos, state1, blockEntity);
        };
    }
    
    @Override
    public @NotNull BlockState playerWillDestroy(Level world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        
        if (!world.isClientSide) {
            var entity = world.getBlockEntity(pos);
            if (entity instanceof MachineAddonController machineEntity) {
                machineEntity.resetAddons();
            }
        }
        
        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltip,
                                TooltipFlag options) {
        if (!ConfigLoader.getInstance().magneticFieldSettings.enabled()) {
            tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field_disabled")
                    .withStyle(net.minecraft.ChatFormatting.RED));
            return;
        }
        
        tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field").withStyle(net.minecraft.ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field_desc").withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
        tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field_limitation").withStyle(net.minecraft.ChatFormatting.RED));
        tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field_addon_info").withStyle(net.minecraft.ChatFormatting.GOLD));
        tooltip.add(Component.empty());
        if (Screen.hasControlDown()) {
            tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field.target_designator_usage"));
            tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field.target_designator_step1"));
            tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field.target_designator_step2"));
            tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field.target_designator_step3"));
            tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field.target_designator_benefit"));
        } else {
            tooltip.add(Component.translatable("tooltip.oritech.item_extra_info").withStyle(net.minecraft.ChatFormatting.DARK_GRAY).withStyle(net.minecraft.ChatFormatting.ITALIC));
        }
    }
}
