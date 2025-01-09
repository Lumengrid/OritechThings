package com.lumengrid.oritechthings.entity.custom;

import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.block.custom.AcceleratorSpeedSensorBlock;
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
import rearth.oritech.block.entity.accelerator.AcceleratorParticleLogic;

import javax.annotation.Nullable;

public class AcceleratorSpeedSensorBlockEntity extends BlockEntity {
    private float speedLimit = 50F;
    private boolean enabled = false;
    @Nullable
    private BlockPos targetDesignator;

    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return true;
        }
    };
    public AcceleratorSpeedSensorBlockEntity(BlockPos pos, BlockState state) {
        super(ModEntities.accelerator_speed_sensor.get(), pos, state);
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

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T ignoredT) {
        if (level.isClientSide) return;
        if (!(level.getBlockEntity(pos) instanceof AcceleratorSpeedSensorBlockEntity speedControl)) return;
        if (!speedControl.isEnabled() || speedControl.getTargetDesignator() == null) return;
        boolean powered = false;
        BlockEntity entity = level.getBlockEntity(speedControl.getTargetDesignator());
        if (entity instanceof AcceleratorControllerBlockEntity accelerator) {
            AcceleratorParticleLogic.ActiveParticle part = accelerator.getParticle();
            if (part != null && part.velocity > speedControl.speedLimit) {
                powered = true;
            }
        }
        if (powered != state.getValue(AcceleratorSpeedSensorBlock.POWERED)) {
            level.setBlock(pos, state.setValue(AcceleratorSpeedSensorBlock.POWERED, powered), 3);
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
