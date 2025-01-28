package com.lumengrid.oritechthings.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class ScreenOpener {
    public static void openFramePlacer(BlockPos pos, Direction facing) {
        Minecraft.getInstance().setScreen(new FramePlacerScreen(pos, facing));
    }
}
