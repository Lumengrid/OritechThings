package com.lumengrid.oritechthings.entity.custom;

import com.lumengrid.oritechthings.api.MagneticFieldController;
import com.lumengrid.oritechthings.entity.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import rearth.oritech.block.entity.accelerator.AcceleratorControllerBlockEntity;
import rearth.oritech.block.base.entity.ExpandableEnergyStorageBlockEntity;
import rearth.oritech.util.ComparatorOutputProvider;

import java.util.List;
import java.util.Objects;

public class AcceleratorMagneticFieldBlockEntity extends ExpandableEnergyStorageBlockEntity implements ComparatorOutputProvider {
    public static final long BASE_ENERGY_CAPACITY = 500000;
    public static final long BASE_ENERGY_INSERTION = 20000;
    public static final long BASE_ENERGY_EXTRACTION = 0;
    
    public AcceleratorMagneticFieldBlockEntity(BlockPos pos, BlockState state) {
        super(ModEntities.ACCELERATOR_MAGNETIC_FIELD_BLOCK_ENTITY.get(), pos, state);
    }

    public boolean setTargetDesignator(BlockPos acceleratorPos, Player player) {
        if (acceleratorPos == null) {
            player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_magnetic_field.invalid_controller").withStyle(ChatFormatting.RED));
            return false;
        }

        assert level != null;
        BlockEntity acceleratorEntity = level.getBlockEntity(acceleratorPos);
        if (!(acceleratorEntity instanceof AcceleratorControllerBlockEntity)) {
            player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_magnetic_field.invalid_controller").withStyle(ChatFormatting.RED));
            return false;
        }

        if (!isWithinAcceleratorArea(acceleratorPos, this.getBlockPos())) {
            player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_magnetic_field.outside_area").withStyle(ChatFormatting.RED));
            return false;
        }

        ((MagneticFieldController) acceleratorEntity).addMagneticField(this.getBlockPos());
        
        level.playSound(player, this.getBlockPos(), SoundEvents.ALLAY_AMBIENT_WITH_ITEM, SoundSource.BLOCKS, 1f, 1f);
        player.sendSystemMessage(Component.translatable("block.oritechthings.accelerator_magnetic_field.controller_set")
                .append(Component.literal(acceleratorPos.toShortString()).withStyle(ChatFormatting.BLUE)));
        setChanged();

        return true;
    }
    
    private boolean isWithinAcceleratorArea(BlockPos acceleratorPos, BlockPos magnetPos) {
        // Check if magnet is at the same Y level as the accelerator
        if (magnetPos.getY() != acceleratorPos.getY()) {
            return false;
        }
        
        // Get configurable values from the configuration
        var config = com.lumengrid.oritechthings.main.ConfigLoader.getInstance().magneticFieldSettings;
        int searchRadius = config.searchRadius();
        int maxAllowedDistance = config.minDistance();
        
        // Check if magnet is within the accelerator area (search for accelerator blocks in configurable radius)
        int minDistance = Integer.MAX_VALUE;

        for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int z = -searchRadius; z <= searchRadius; z++) {
                BlockPos checkPos = acceleratorPos.offset(x, 0, z);
                assert level != null;
                var blockState = level.getBlockState(checkPos);
                var block = blockState.getBlock();

                // Check if this is an accelerator component
                if (isAcceleratorComponent(block)) {
                    int distance = magnetPos.distManhattan(checkPos);
                    minDistance = Math.min(minDistance, distance);
                }
            }
        }

        // Magnet must be within configurable distance of accelerator components
        return minDistance <= maxAllowedDistance;
    }
    
    private boolean isAcceleratorComponent(net.minecraft.world.level.block.Block block) {
        // Check for base accelerator block types using proper type checking
        if (block instanceof rearth.oritech.block.blocks.accelerator.AcceleratorPassthroughBlock) {
            return true; // Covers: Motor, Ring, Sensor
        }
        
        // Check for controller (extends HorizontalDirectionalBlock directly)
        if (block instanceof rearth.oritech.block.blocks.accelerator.AcceleratorControllerBlock) {
            return true;
        }
        
        // Check for magnetic field (custom block)
        if (block instanceof com.lumengrid.oritechthings.block.custom.AcceleratorMagneticFieldBlock) {
            return true;
        }
        
        // Check for black hole (part of accelerator system)
        if (block instanceof rearth.oritech.block.blocks.accelerator.BlackHoleBlock) {
            return true;
        }
        
        return false;
    }
    
    @Override
    public int getComparatorOutput() {
        if (energyStorage.amount == 0) return 0;
        return (int) (1 + ((energyStorage.amount / (float) energyStorage.capacity) * 14));
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
    public long getDefaultExtractionRate() {
        return BASE_ENERGY_EXTRACTION;
    }

    @Override
    public List<Vec3i> getAddonSlots() {
        return List.of(
            new Vec3i(-1, 0, 0),
            new Vec3i(1, 0, 0),
            new Vec3i(0, -1, 0)
        );
    }
    
    @Override
    public float getCoreQuality() {
        return 2;
    }
    
    public Direction getFacing() {
        var state = getBlockState();
        if (state.hasProperty(com.lumengrid.oritechthings.block.custom.AcceleratorMagneticFieldBlock.TARGET_DIR)) {
            return state.getValue(com.lumengrid.oritechthings.block.custom.AcceleratorMagneticFieldBlock.TARGET_DIR);
        }
        return Direction.NORTH;
    }
    
    @Override
    public Direction getFacingForAddon() {
        var state = Objects.requireNonNull(level).getBlockState(getBlockPos());
        if (state.hasProperty(com.lumengrid.oritechthings.block.custom.AcceleratorMagneticFieldBlock.TARGET_DIR)) {
            var facing = state.getValue(com.lumengrid.oritechthings.block.custom.AcceleratorMagneticFieldBlock.TARGET_DIR);
            
            if (facing.equals(Direction.UP) || facing.equals(Direction.DOWN))
                return Direction.NORTH;
            
            return facing;
        }
        return Direction.NORTH;
    }
    
    @Override
    public Property<Direction> getBlockFacingProperty() {
        return com.lumengrid.oritechthings.block.custom.AcceleratorMagneticFieldBlock.TARGET_DIR;
    }
}