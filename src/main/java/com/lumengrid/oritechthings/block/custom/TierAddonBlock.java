package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.block.entity.TierAddonBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;

public class TierAddonBlock extends MachineAddonBlock {
    public TierAddonBlock(AddonSettings addonSettings) {
        super(Properties.of().strength(2f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(ADDON_USED) ? 15 : 0), addonSettings);
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
        builder.add(ADDON_USED, FACING, FACE);
    }
}
