package com.lumengrid.oritechthings.api;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Interface for DronePortEntity cross-dimensional functionality
 */
public interface CrossDimensionalDrone {
    
    /**
     * Set cross-dimensional target for drone transfers
     * @param targetPos The target position
     * @param targetDimension The target dimension
     * @return true if the target was set successfully, false otherwise
     */
    boolean oritechthings$setCrossDimensionalTarget(BlockPos targetPos, ResourceKey<Level> targetDimension);
}
