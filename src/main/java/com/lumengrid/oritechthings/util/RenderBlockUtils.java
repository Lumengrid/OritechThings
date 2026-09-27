package com.lumengrid.oritechthings.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import rearth.oritech.util.ColorHelper;

public final class RenderBlockUtils {

    private static final VoxelShape CUBE_SHAPE = Shapes.block();

    private RenderBlockUtils() {
    }

    public static void createBox(BlockPos pos) {
        Minecraft minecraft = Minecraft.getInstance();

        // In 1.21.2+ usiamo la posizione della camera dal GameRenderer
        Vec3 cameraPos = minecraft.gameRenderer.getMainCamera().position();

        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();

        createBox(
                minecraft.renderBuffers().bufferSource(),
                cameraPos,
                poseStack,
                pos
        );

        poseStack.popPose();
    }

    private static void createBox(
            MultiBufferSource.BufferSource bufferSource,
            Vec3 cameraPos,
            PoseStack poseStack,
            BlockPos pos
    ) {
        // ✅ Usa RenderTypes.lines() (plurale!) come fa Oritech
        var consumer = bufferSource.getBuffer(RenderTypes.lines());

        poseStack.pushPose();

        // Traslazione rispetto alla telecamera
        poseStack.translate(
                pos.getX() - cameraPos.x,
                pos.getY() - cameraPos.y,
                pos.getZ() - cameraPos.z
        );

        // ✅ Disegno del cubo tramite ShapeRenderer (ARBG arancione, spessore linea 2.0f)
        ShapeRenderer.renderShape(
                poseStack,
                consumer,
                CUBE_SHAPE,
                0, 0, 0,
                ColorHelper.argb(1.0f, 1.0f, 0.647f, 0.0f),
                2.0f
        );

        poseStack.popPose();
    }
}