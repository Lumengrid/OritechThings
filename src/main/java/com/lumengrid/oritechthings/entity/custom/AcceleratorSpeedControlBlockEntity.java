package com.lumengrid.oritechthings.entity.custom;

import com.lumengrid.oritechthings.entity.ModBlockEntities;
import com.lumengrid.oritechthings.block.custom.AcceleratorSpeedControlBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import rearth.oritech.block.entity.accelerator.AcceleratorControllerBlockEntity;

public class AcceleratorSpeedControlBlockEntity extends BlockEntity {
    public AcceleratorSpeedControlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ACCELERATOR_SPEED_CONTROL.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T ignoredT) {
        if (level.isClientSide) return;
        boolean powered = false;
        BlockPos[] adjacentPositions = new BlockPos[]{pos.below(), pos.above(), pos.north(), pos.south(), pos.east(), pos.west()};
        for (BlockPos adjacentPos : adjacentPositions) {
            BlockEntity entity = level.getBlockEntity(adjacentPos);
            if (entity instanceof AcceleratorControllerBlockEntity accelerator) {
                var part = accelerator.getParticle();
                System.out.println(part);
                if (part == null) continue;
                if (part.velocity > 50F) {
                    powered = true;
                    break;
                }
            }
        }
        if (powered != state.getValue(AcceleratorSpeedControlBlock.POWERED)) {
            level.setBlock(pos, state.setValue(AcceleratorSpeedControlBlock.POWERED, powered), 3);
            notifyNeighbors(level, pos);
        }
    }

    private static void notifyNeighbors(Level level, BlockPos pos) {
        level.updateNeighborsAt(pos, level.getBlockState(pos).getBlock());
        for (Direction direction : Direction.values()) {
            level.updateNeighborsAt(pos.relative(direction), level.getBlockState(pos).getBlock());
        }
    }
}
