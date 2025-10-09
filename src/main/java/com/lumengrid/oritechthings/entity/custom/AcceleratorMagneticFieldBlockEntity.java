package com.lumengrid.oritechthings.entity.custom;

import com.lumengrid.oritechthings.api.MagneticFieldController;
import com.lumengrid.oritechthings.entity.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import rearth.oritech.block.entity.accelerator.AcceleratorControllerBlockEntity;
import rearth.oritech.block.base.entity.UpgradableMachineBlockEntity;
import rearth.oritech.client.init.ModScreens;
import rearth.oritech.init.recipes.OritechRecipeType;
import rearth.oritech.util.ComparatorOutputProvider;
import rearth.oritech.util.InventoryInputMode;
import rearth.oritech.util.InventorySlotAssignment;
import rearth.oritech.util.ScreenProvider;

import java.util.List;

public class AcceleratorMagneticFieldBlockEntity extends UpgradableMachineBlockEntity implements ComparatorOutputProvider, ScreenProvider, MenuProvider {
    
    // Configuration
    private static final int BASE_ENERGY_PER_TICK = 100;
    private static final int BASE_ENERGY_CAPACITY = 10000;
    private static final int BASE_ENERGY_INSERTION = 1000;

    
    public AcceleratorMagneticFieldBlockEntity(BlockPos pos, BlockState state) {
        super(ModEntities.ACCELERATOR_MAGNETIC_FIELD_BLOCK_ENTITY.get(), pos, state, BASE_ENERGY_PER_TICK);
    }

    public boolean setTargetDesignator(BlockPos acceleratorPos, Player player) {
        if (acceleratorPos == null) {
            player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_magnetic_field.invalid_controller").withStyle(ChatFormatting.RED));
            return false;
        }
        
        // Get the accelerator controller at the stored position
        BlockEntity acceleratorEntity = level.getBlockEntity(acceleratorPos);
        if (!(acceleratorEntity instanceof AcceleratorControllerBlockEntity)) {
            player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_magnetic_field.invalid_controller").withStyle(ChatFormatting.RED));
            return false;
        }
        
        // Check if magnetic field is within the particle accelerator area and at same Y level
        if (!isWithinAcceleratorArea(acceleratorPos, this.getBlockPos())) {
            player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_magnetic_field.outside_area").withStyle(ChatFormatting.RED));
            return false;
        }

        // Add this magnetic field to the accelerator controller
        ((MagneticFieldController) acceleratorEntity).addMagneticField(this.getBlockPos());
        
        level.playSound(player, this.getBlockPos(), SoundEvents.ALLAY_AMBIENT_WITH_ITEM, SoundSource.BLOCKS, 1f, 1f);
        player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_magnetic_field.controller_set")
                .append(Component.literal(acceleratorPos.toShortString()).withStyle(ChatFormatting.BLUE)));
        sync();

        return true;
    }
    
    
    private boolean isWithinAcceleratorArea(BlockPos acceleratorPos, BlockPos magnetPos) {
        // Check if magnet is at the same Y level as the accelerator
        if (magnetPos.getY() != acceleratorPos.getY()) {
            return false;
        }
        return true;
//        TODO Manage a proper function that checks if the magnet is inside the particle accelerator area
//        // Check if magnet is within the accelerator area (search for accelerator blocks in a reasonable radius)
//        int searchRadius = 32; // Maximum radius to search for accelerator components
//        int minDistance = Integer.MAX_VALUE;
//
//        // Search for accelerator components (controller, motor, guide ring, sensor)
//        for (int x = -searchRadius; x <= searchRadius; x++) {
//            for (int z = -searchRadius; z <= searchRadius; z++) {
//                BlockPos checkPos = acceleratorPos.offset(x, 0, z);
//                var blockState = level.getBlockState(checkPos);
//                var block = blockState.getBlock();
//
//                // Check if this is an accelerator component
//                if (isAcceleratorComponent(block)) {
//                    int distance = magnetPos.distManhattan(checkPos);
//                    minDistance = Math.min(minDistance, distance);
//                }
//            }
//        }
//
//        // Magnet must be within reasonable distance of accelerator components
//        return minDistance <= 16; // Within 16 blocks of accelerator components
    }
    
    private boolean isAcceleratorComponent(net.minecraft.world.level.block.Block block) {
        // Check if the block is part of the particle accelerator system
        String blockName = block.toString().toLowerCase();
        return blockName.contains("accelerator") || 
               blockName.contains("particle") ||
               blockName.contains("motor") ||
               blockName.contains("guide") ||
               blockName.contains("ring") ||
               blockName.contains("sensor");
    }
    
    @Override
    public int getComparatorOutput() {
        // Return signal based on energy level (0-15)
        float energyRatio = (float) energyStorage.getAmount() / energyStorage.getCapacity();
        return Math.min(15, (int) (energyRatio * 15));
    }

    @Override
    public boolean inputOptionsEnabled() {
        return false;
    }
    
    @Override
    public long getDefaultCapacity() {
        return BASE_ENERGY_CAPACITY;
    }

    @Override
    public long getDefaultInsertRate() {
        return BASE_ENERGY_INSERTION;
    }

    @Override
    public InventorySlotAssignment getSlotAssignments() {
        return new InventorySlotAssignment(0, 0, 0, 0);
    }

    @Override
    public List<GuiSlot> getGuiSlots() {
        return List.of();
    }

    @Override
    public boolean showProgress() {
        return false;
    }

    @Override
    public boolean hasRedstoneControlAvailable() {
        return true;
    }

    @Override
    public MenuType<?> getScreenHandlerType() {
        return ModScreens.PULVERIZER_SCREEN;
    }

    @Override
    public int getInventorySize() {
        return 0;
    }

    @Override
    protected OritechRecipeType getOwnRecipeType() {
        return null; // This entity doesn't process recipes
    }

    @Override
    public float getDisplayedEnergyUsage() {
        return BASE_ENERGY_PER_TICK;
    }

    @Override
    public float getProgress() {
        return 0.0f; // This entity doesn't have progress
    }

    @Override
    public InventoryInputMode getInventoryInputMode() {
        return InventoryInputMode.FILL_LEFT_TO_RIGHT;
    }

    @Override
    public Container getDisplayedInventory() {
        return inventory;
    }

    @Override
    public List<Vec3i> getAddonSlots() {
        return List.of(
          new Vec3i(1, 1, 1)
        );
    }
    
    public void sync() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
    
    // Methods for screen display
    public int getLastSpeed() {
        return (int) (energyStorage.getAmount() / 1000);
    }
    
    public String getLinkedAcceleratorUUID() {
        // Return a simple representation of linked accelerator
        return "Linked"; // This would be set when linking with target designator
    }
    
    public int getEnergyStored() {
        return (int) energyStorage.getAmount();
    }
    
    public int getMaxEnergyStored() {
        return (int) energyStorage.getCapacity();
    }
    
    // MenuProvider implementation
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.oritechthings.accelerator_magnetic_field");
    }
    
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new rearth.oritech.client.ui.UpgradableMachineScreenHandler(i, inventory, this);
    }

    @Override
    public float getCoreQuality() {
        return 2;
    }
}
