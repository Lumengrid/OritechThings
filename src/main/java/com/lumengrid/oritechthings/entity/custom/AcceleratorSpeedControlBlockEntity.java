package com.lumengrid.oritechthings.entity.custom;

import com.lumengrid.oritechthings.entity.ModBlockEntities;
import com.lumengrid.oritechthings.block.custom.AcceleratorSpeedControlBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.block.entity.accelerator.AcceleratorControllerBlockEntity;

import javax.annotation.Nullable;

public class AcceleratorSpeedControlBlockEntity extends BlockEntity {
    private float speedLimit = 50F; // Speed (0 - 100,000)
    private boolean enabled = true; // Whether ticking is enabled
    @Nullable
    private BlockPos targetDesignator; // Position from the TargetDesignator item

    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged(); // Mark for saving
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return true; //check only for target designator from oritech
        }
    };
    public AcceleratorSpeedControlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ACCELERATOR_SPEED_CONTROL.get(), pos, state);
    }

    public float getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(float speedLimit) {
        this.speedLimit = Math.max(0, Math.min(speedLimit, 100000F));
        setChanged();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        setChanged();
    }

    @Nullable
    public BlockPos getTargetDesignator() {
        return targetDesignator;
    }

    public void setTargetDesignator(@Nullable BlockPos targetDesignator) {
        this.targetDesignator = targetDesignator;
        setChanged();
    }


    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
        super.saveAdditional(tag, registryLookup);
        tag.putFloat("SpeedLimit", this.speedLimit);
        tag.putBoolean("Enabled", this.enabled);
        if (this.targetDesignator != null) {
            tag.putLong("TargetDesignator", this.targetDesignator.asLong());
        }
        tag.put("Inventory", this.inventory.serializeNBT(registryLookup));
    }

    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
        super.loadAdditional(tag, registryLookup);
        this.speedLimit = tag.getFloat("SpeedLimit");
        this.enabled = tag.getBoolean("Enabled");
        if (tag.contains("TargetDesignator")) {
            this.targetDesignator = BlockPos.of(tag.getLong("TargetDesignator"));
        }
        inventory.deserializeNBT(registryLookup, tag.getCompound("Inventory"));
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T ignoredT) {
        if (level.isClientSide) return;
        if (!(level.getBlockEntity(pos) instanceof AcceleratorSpeedControlBlockEntity speedControl)) return;
        if (!speedControl.isEnabled()) return;
        boolean powered = false;
        BlockPos[] adjacentPositions = new BlockPos[]{pos.below(), pos.above(), pos.north(), pos.south(), pos.east(), pos.west()};
        for (BlockPos adjacentPos : adjacentPositions) {
            BlockEntity entity = level.getBlockEntity(adjacentPos);
            if (entity instanceof AcceleratorControllerBlockEntity accelerator) {
                var part = accelerator.getParticle();
                System.out.println(part);
                if (part == null) continue;
                if (part.velocity > speedControl.speedLimit) {
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
