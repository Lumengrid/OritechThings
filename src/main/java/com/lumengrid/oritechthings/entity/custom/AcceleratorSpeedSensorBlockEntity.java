package com.lumengrid.oritechthings.entity.custom;

import com.lumengrid.oritechthings.block.custom.AcceleratorSpeedSensorBlock;
import com.lumengrid.oritechthings.entity.ModBlockEntities;
import com.lumengrid.oritechthings.menu.AcceleratorSpeedSensorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.block.entity.accelerator.AcceleratorControllerBlockEntity;
import rearth.oritech.block.entity.accelerator.AcceleratorParticleLogic;

import javax.annotation.Nullable;

public class AcceleratorSpeedSensorBlockEntity extends BlockEntity implements MenuProvider {
    private float speedLimit = 0F;
    private boolean enabled = false;
    @Nullable
    private BlockPos targetDesignator;

    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            sync();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return true;
        }
    };
    public AcceleratorSpeedSensorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.accelerator_speed_sensor.get(), pos, state);
    }

    public float getSpeedLimit() {
        return speedLimit;
    }

    public void increaseSpeedLimit(float amount) {
        this.speedLimit = Math.max(0, Math.min(speedLimit + amount, 100000F));
        sync();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        sync();
    }

    public void sync() {
        setChanged();
        if(level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), AcceleratorSpeedSensorBlock.UPDATE_ALL);
        }
    }

    @Nullable
    public BlockPos getTargetDesignator() {
        return targetDesignator;
    }

    public void setTargetDesignator(@Nullable BlockPos targetDesignator) {
        this.targetDesignator = targetDesignator;
        sync();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
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

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.oritechthings.accelerator_speed_sensor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new AcceleratorSpeedSensorMenu(i, inventory, this);
    }
}
