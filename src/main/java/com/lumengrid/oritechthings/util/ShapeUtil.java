package com.lumengrid.oritechthings.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShapeUtil {
    public static VoxelShape shapeFromDimension(float x1, float y1, float z1, float x2, float y2, float z2) {
        return Block.box(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }
}
