package com.lumengrid.oritechthings.util;

import com.lumengrid.oritechthings.client.renderer.ModRendererTypes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RenderBlockUtils {
    // On 1.20.1 the camera rotation lives in the RenderLevelStageEvent pose stack, so it has to be passed in
    public static void createBox(PoseStack poseStack, BlockPos pos) {
        Vec3 cameraPosition = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        Minecraft mc = Minecraft.getInstance();
        createBox(mc.renderBuffers().bufferSource(), cameraPosition, poseStack, pos.getX(), pos.getY(), pos.getZ());
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
        Matrix3f normal = poseStack.last().normal();

        addLine(vertexConsumer, pose, normal, x, y, z, x + offset, y, z, color);
        addLine(vertexConsumer, pose, normal, x, y, z, x, y, z + offset, color);
        addLine(vertexConsumer, pose, normal, x, y, z, x, y + offset, z, color);
        addLine(vertexConsumer, pose, normal, x + offset, y, z, x + offset, y + offset, z, color);
        addLine(vertexConsumer, pose, normal, x + offset, y, z, x + offset, y, z + offset, color);
        addLine(vertexConsumer, pose, normal, x, y + offset, z, x, y + offset, z + offset, color);
        addLine(vertexConsumer, pose, normal, x, y, z + offset, x + offset, y, z + offset, color);
        addLine(vertexConsumer, pose, normal, x, y + offset, z, x + offset, y + offset, z, color);
        addLine(vertexConsumer, pose, normal, x, y + offset, z, x, y + offset, z + offset, color);
        addLine(vertexConsumer, pose, normal, x + offset, y + offset, z, x + offset, y + offset, z + offset, color);
        addLine(vertexConsumer, pose, normal, x, y + offset, z + offset, x + offset, y + offset, z + offset, color);  // Top-front-right corner
        addLine(vertexConsumer, pose, normal, x, y, z, x, y + offset, z, color);  // Left vertical
        addLine(vertexConsumer, pose, normal, x + offset, y, z, x + offset, y + offset, z, color);  // Right vertical
        addLine(vertexConsumer, pose, normal, x, y, z + offset, x, y + offset, z + offset, color);  // Back-left vertical
        addLine(vertexConsumer, pose, normal, x + offset, y, z + offset, x + offset, y + offset, z + offset, color);  // Back-right vertical

        bufferSource.endBatch(ModRendererTypes.BLOCK_LINES);
        RenderSystem.enableDepthTest();
        poseStack.popPose();
    }

    // The 1.20.1 lines shader expands each segment along its normal, which must be the line direction
    private static void addLine(VertexConsumer vertexConsumer, Matrix4f pose, Matrix3f normal, float x1, float y1, float z1, float x2, float y2, float z2, float[] color) {
        float dx = x2 - x1, dy = y2 - y1, dz = z2 - z1;
        float len = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
        dx /= len; dy /= len; dz /= len;
        vertexConsumer.vertex(pose, x1, y1, z1).color(color[0], color[1], color[2], 1.0F).normal(normal, dx, dy, dz).endVertex();
        vertexConsumer.vertex(pose, x2, y2, z2).color(color[0], color[1], color[2], 1.0F).normal(normal, dx, dy, dz).endVertex();
    }
}
