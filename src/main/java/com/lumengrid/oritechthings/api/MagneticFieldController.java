package com.lumengrid.oritechthings.api;

import net.minecraft.core.BlockPos;

import java.util.List;

/**
 * Interface for entities that can control magnetic fields in particle accelerators.
 * This interface is implemented by mixins to avoid direct mixin class references.
 */
public interface MagneticFieldController {
    
    /**
     * Adds a magnetic field at the specified position to this controller.
     * @param magnetPos The position of the magnetic field block
     */
    void addMagneticField(BlockPos magnetPos);
    
    /**
     * Removes a magnetic field at the specified position from this controller.
     * @param magnetPos The position of the magnetic field block
     * @return true if the magnetic field was removed, false if it wasn't found
     */
    boolean removeMagneticField(BlockPos magnetPos);
    
    /**
     * Gets all linked magnetic field positions.
     * @return A list of BlockPos representing linked magnetic fields
     */
    List<BlockPos> getLinkedMagneticFields();
}
