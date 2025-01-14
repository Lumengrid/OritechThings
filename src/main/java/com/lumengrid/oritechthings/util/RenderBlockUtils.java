package com.lumengrid.oritechthings.util;

import com.lumengrid.oritechthings.client.renderer.ModRendererTypes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class RenderBlockUtils {
    public static void createBox(BlockPos pos) {
        Vec3 cameraPosition = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        PoseStack localPoseStack = new PoseStack();
        localPoseStack.pushPose();
        Minecraft mc = Minecraft.getInstance();
        createBox(mc.renderBuffers().bufferSource(), cameraPosition, localPoseStack, pos.getX(), pos.getY(), pos.getZ());
        localPoseStack.popPose();
    }

    private static void createBox(MultiBufferSource.BufferSource bufferSource, Vec3 cameraPosition, PoseStack poseStack, float x, float y, float z) {
        float[] color = {1.0F, 0.647F, 0.0F}; // Orange
        float offset = 1.0F;

        Vec3 vec = (new Vec3(x, y, z)).subtract(cameraPosition);
        if (vec.distanceTo(Vec3.ZERO) > 200.0F) {
            vec = vec.normalize().scale(200.0F);
            x = (float) ((double) x + vec.x);
            y = (float) ((double) y + vec.y);
            z = (float) ((double) z + vec.z);
        }
        RenderSystem.disableDepthTest();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(ModRendererTypes.BLOCK_LINES);
        poseStack.pushPose();
        poseStack.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
        Matrix4f pose = poseStack.last().pose();

        addLine(vertexConsumer, pose, x, y, z, x + offset, y, z, color);
        addLine(vertexConsumer, pose, x, y, z, x, y, z + offset, color);
        addLine(vertexConsumer, pose, x, y, z, x, y + offset, z, color);
        addLine(vertexConsumer, pose, x + offset, y, z, x + offset, y + offset, z, color);
        addLine(vertexConsumer, pose, x + offset, y, z, x + offset, y, z + offset, color);
        addLine(vertexConsumer, pose, x, y + offset, z, x, y + offset, z + offset, color);
        addLine(vertexConsumer, pose, x, y, z + offset, x + offset, y, z + offset, color);
        addLine(vertexConsumer, pose, x, y + offset, z, x + offset, y + offset, z, color);
        addLine(vertexConsumer, pose, x, y + offset, z, x, y + offset, z + offset, color);
        addLine(vertexConsumer, pose, x + offset, y + offset, z, x + offset, y + offset, z + offset, color);
        addLine(vertexConsumer, pose, x, y + offset, z + offset, x + offset, y + offset, z + offset, color);  // Top-front-right corner
        addLine(vertexConsumer, pose, x, y, z, x, y + offset, z, color);  // Left vertical
        addLine(vertexConsumer, pose, x + offset, y, z, x + offset, y + offset, z, color);  // Right vertical
        addLine(vertexConsumer, pose, x, y, z + offset, x, y + offset, z + offset, color);  // Back-left vertical
        addLine(vertexConsumer, pose, x + offset, y, z + offset, x + offset, y + offset, z + offset, color);  // Back-right vertical

        bufferSource.endBatch(ModRendererTypes.BLOCK_LINES);
        RenderSystem.enableDepthTest();
        poseStack.popPose();
    }

    private static void addLine(VertexConsumer vertexConsumer, Matrix4f pose, float x1, float y1, float z1, float x2, float y2, float z2, float[] color) {
        vertexConsumer.addVertex(pose, x1, y1, z1).setColor(color[0], color[1], color[2], 2.0F);
        vertexConsumer.addVertex(pose, x2, y2, z2).setColor(color[0], color[1], color[2], 2.0F);
    }
}
