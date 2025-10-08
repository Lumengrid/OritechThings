package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.entity.custom.AcceleratorMagneticFieldBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AcceleratorMagneticFieldBlock extends Block implements EntityBlock {
    
    public AcceleratorMagneticFieldBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(3.0f, 6.0f)
                .requiresCorrectToolForDrops()
                .noOcclusion());
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AcceleratorMagneticFieldBlockEntity(pos, state);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof BlockEntityTicker ticker)
                ticker.tick(world1, pos, state1, blockEntity);
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip,
                                TooltipFlag options) {
        if (options.isAdvanced()) {
            tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field").withStyle(net.minecraft.ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field_desc").withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
            
            tooltip.add(Component.translatable("tooltip.oritechthings.item_extra_info").withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
            
            if (net.minecraft.client.Minecraft.getInstance().options.keySprint.isDown()) {
                tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field.target_designator_usage"));
                tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field.target_designator_step1"));
                tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field.target_designator_step2"));
                tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field.target_designator_step3"));
                tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_magnetic_field.target_designator_benefit"));
            }
        }
    }
}
