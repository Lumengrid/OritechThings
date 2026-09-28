package com.lumengrid.oritechthings.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class RenderBlockUtils {

    private static long lastLogTime = 0;

    private RenderBlockUtils() {
    }

    public static void createBox(PoseStack poseStack, BlockPos pos) {
        Minecraft minecraft = Minecraft.getInstance();

        long now = System.currentTimeMillis();
        if (now - lastLogTime > 2000) {
            lastLogTime = now;
        }

        Vec3 cameraPos = minecraft.gameRenderer.getMainCamera().position();
        MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();

        poseStack.pushPose();
        poseStack.translate(
                pos.getX() - cameraPos.x,
                pos.getY() - cameraPos.y,
                pos.getZ() - cameraPos.z
        );

        renderLineBox(poseStack, bufferSource);

        poseStack.popPose();

        bufferSource.endBatch(RenderTypes.lines());
    }

    private static void renderLineBox(PoseStack matrix, MultiBufferSource buffer) {
        float min = -0.002f;
        float max = 1.002f;

        VertexConsumer builder = buffer.getBuffer(RenderTypes.lines());

        Matrix4f matrix4f = matrix.last().pose();
        PoseStack.Pose pose = matrix.last();

        // Orange (R=255, G=165, B=0, A=255)
        int r = 255, g = 165, b = 0, a = 255;
        float lineWidth = 2.5f;

        builder.addVertex(matrix4f, min, min, min).setColor(r, g, b, a).setNormal(pose, 1.0F, 0.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, min, min).setColor(r, g, b, a).setNormal(pose, 1.0F, 0.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, min, min, min).setColor(r, g, b, a).setNormal(pose, 0.0F, 0.0F, 1.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, min, min, max).setColor(r, g, b, a).setNormal(pose, 0.0F, 0.0F, 1.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, min, min).setColor(r, g, b, a).setNormal(pose, 0.0F, 0.0F, 1.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, min, max).setColor(r, g, b, a).setNormal(pose, 0.0F, 0.0F, 1.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, min, min, max).setColor(r, g, b, a).setNormal(pose, 1.0F, 0.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, min, max).setColor(r, g, b, a).setNormal(pose, 1.0F, 0.0F, 0.0F).setLineWidth(lineWidth);

        builder.addVertex(matrix4f, min, min, min).setColor(r, g, b, a).setNormal(pose, 0.0F, 1.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, min, max, min).setColor(r, g, b, a).setNormal(pose, 0.0F, 1.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, min, min).setColor(r, g, b, a).setNormal(pose, 0.0F, 1.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, max, min).setColor(r, g, b, a).setNormal(pose, 0.0F, 1.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, min, min, max).setColor(r, g, b, a).setNormal(pose, 0.0F, 1.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, min, max, max).setColor(r, g, b, a).setNormal(pose, 0.0F, 1.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, min, max).setColor(r, g, b, a).setNormal(pose, 0.0F, 1.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, max, max).setColor(r, g, b, a).setNormal(pose, 0.0F, 1.0F, 0.0F).setLineWidth(lineWidth);

        builder.addVertex(matrix4f, min, max, min).setColor(r, g, b, a).setNormal(pose, 1.0F, 0.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, max, min).setColor(r, g, b, a).setNormal(pose, 1.0F, 0.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, min, max, min).setColor(r, g, b, a).setNormal(pose, 0.0F, 0.0F, 1.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, min, max, max).setColor(r, g, b, a).setNormal(pose, 0.0F, 0.0F, 1.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, max, min).setColor(r, g, b, a).setNormal(pose, 0.0F, 0.0F, 1.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, max, max).setColor(r, g, b, a).setNormal(pose, 0.0F, 0.0F, 1.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, min, max, max).setColor(r, g, b, a).setNormal(pose, 1.0F, 0.0F, 0.0F).setLineWidth(lineWidth);
        builder.addVertex(matrix4f, max, max, max).setColor(r, g, b, a).setNormal(pose, 1.0F, 0.0F, 0.0F).setLineWidth(lineWidth);
    }
}