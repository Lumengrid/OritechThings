package com.lumengrid.oritechthings.entity.custom;

import com.lumengrid.oritechthings.block.custom.AcceleratorSpeedSensorBlock;
import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.menu.AcceleratorSpeedSensorMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import rearth.oritech.init.recipes.RecipeContent;
import rearth.oritech.util.SimpleCraftingInventory;

import javax.annotation.Nullable;
import java.util.Objects;

public class AcceleratorSpeedSensorBlockEntity extends BlockEntity implements MenuProvider {
    private int speedLimit = 1000;
    private boolean enabled = false;
    private boolean checkGreater = true;
    private boolean automaticMode = false;

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
        super(ModEntities.accelerator_speed_sensor.get(), pos, state);
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speed) {
        speedLimit = speed;
    }


    public boolean isCheckGreater() {
        return checkGreater;
    }

    public void setCheckGreater(boolean checkGreater) {
        this.checkGreater = checkGreater;
        sync();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        sync();
    }

    public boolean isAutomaticMode() {
        return automaticMode;
    }

    public void setAutomaticMode(boolean automaticMode) {
        this.automaticMode = automaticMode;
        sync();
    }

    public void sync() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), AcceleratorSpeedSensorBlock.UPDATE_ALL);
        }
    }

    @Nullable
    public BlockPos getTargetDesignator() {
        return targetDesignator;
    }

    @SuppressWarnings("null")
    public boolean setTargetDesignator(@Nullable BlockPos targetPos, Player player) {
        BlockEntity blockEntity = targetPos == null ? null : Objects.requireNonNull(level).getBlockEntity(targetPos);
        if(targetPos == null || !(blockEntity instanceof AcceleratorControllerBlockEntity)) {
            player.sendSystemMessage(Component.translatable("block.oritechthings.particle_accelerator_speed_sensor.invalid_controller").withStyle(ChatFormatting.RED));
            return false;
        }
        int distance = targetPos.distManhattan(this.getBlockPos());
        if (distance > 128) {
            player.sendSystemMessage(Component.translatable("block.oritechthings.particle_accelerator_speed_sensor.invalid_controller.to_far")
                    .append(Component.literal(" (" + distance + ")").withStyle(ChatFormatting.ITALIC)) .withStyle(ChatFormatting.RED));
            return false;
        }
        this.targetDesignator = targetPos;
        this.setEnabled(true);
        level.playSound(player, this.getBlockPos(), SoundEvents.ALLAY_AMBIENT_WITH_ITEM, SoundSource.BLOCKS, 1f, 1f);
        player.sendSystemMessage(Component.translatable("block.oritechthings.particle_accelerator_speed_sensor.controller_set")
                .append(Component.literal(targetPos.toShortString()).withStyle(ChatFormatting.BLUE)));
        sync();

        return true;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
        super.saveAdditional(tag, registryLookup);
        tag.putInt("SpeedLimit", this.speedLimit);
        tag.putBoolean("Enabled", this.enabled);
        tag.putBoolean("CheckGreater", this.checkGreater);
        tag.putBoolean("AutomaticMode", this.automaticMode);
        if (this.targetDesignator != null) {
            tag.putLong("TargetDesignator", this.targetDesignator.asLong());
        }
        tag.put("Inventory", this.inventory.serializeNBT(registryLookup));
    }

    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
        super.loadAdditional(tag, registryLookup);
        this.speedLimit = tag.getInt("SpeedLimit");
        this.enabled = tag.getBoolean("Enabled");
        this.checkGreater = tag.getBoolean("CheckGreater");
        this.automaticMode = tag.getBoolean("AutomaticMode");
        if (tag.contains("TargetDesignator")) {
            this.targetDesignator = BlockPos.of(tag.getLong("TargetDesignator"));
        }
        inventory.deserializeNBT(registryLookup, tag.getCompound("Inventory"));
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T ignoredT) {
        if (level.isClientSide) return;
        if (!(level.getBlockEntity(pos) instanceof AcceleratorSpeedSensorBlockEntity speedControl)) return;
        if (!speedControl.isEnabled() || speedControl.getTargetDesignator() == null) {
            setPowered(level, pos, state, false);
            return;
        }
        @SuppressWarnings("null")
        BlockEntity entity = level.getBlockEntity(speedControl.getTargetDesignator());
        boolean powered = isPowered(speedControl, entity);
        if (powered != state.getValue(AcceleratorSpeedSensorBlock.POWERED)) {
            setPowered(level, pos, state, powered);
        }
    }

    private static void setPowered(Level level, BlockPos pos, BlockState state, boolean powered) {
        level.setBlock(pos, state.setValue(AcceleratorSpeedSensorBlock.POWERED, powered), 3);
        notifyNeighbors(level, pos);
    }

    private static boolean isPowered(AcceleratorSpeedSensorBlockEntity speedControl, BlockEntity entity) {
        boolean powered = false;
        if (entity instanceof AcceleratorControllerBlockEntity accelerator) {
            AcceleratorParticleLogic.ActiveParticle part = accelerator.getParticle();
            if (part != null) {
                int targetSpeed = speedControl.speedLimit;
                
                // In automatic mode, detect required velocity from recipe
                if (speedControl.isAutomaticMode()) {
                    targetSpeed = getRequiredVelocityFromRecipe(accelerator, speedControl.level);
                }
                
                if (speedControl.isCheckGreater() && part.velocity > targetSpeed) {
                    powered = true;
                } else {
                    if (!speedControl.isCheckGreater() && part.velocity < targetSpeed) {
                        powered = true;
                    }
                }
            }
        }
        return powered;
    }
    
    private static int getRequiredVelocityFromRecipe(AcceleratorControllerBlockEntity accelerator, Level level) {
        // Get the active particle item
        ItemStack activeItem = accelerator.activeItemParticle;
        
        if (activeItem == null || activeItem.isEmpty()) {
            return 1000; // Default fallback
        }
        
        // Try to find a recipe where this item collides with itself (most common case)
        var inputInv = new SimpleCraftingInventory(activeItem.copy(), activeItem.copy());
        var recipeOptional = level.getRecipeManager().getRecipeFor(RecipeContent.PARTICLE_COLLISION, inputInv, level);
        
        if (recipeOptional.isPresent()) {
            var recipe = recipeOptional.get().value();
            return recipe.getTime(); // Recipe time stores the required velocity
        }
        
        // If no recipe found for self-collision, try to find any recipe involving this item
        // This would require checking all recipes, which might be expensive
        // For now, return a default value
        return 1000;
    }

    private static void notifyNeighbors(Level level, BlockPos pos) {
        level.updateNeighborsAt(pos, level.getBlockState(pos).getBlock());
        for (Direction direction : Direction.values()) {
            level.updateNeighborsAt(pos.relative(direction), level.getBlockState(pos).getBlock());
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.oritechthings.particle_accelerator_speed_sensor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new AcceleratorSpeedSensorMenu(i, inventory, this);
    }
}
