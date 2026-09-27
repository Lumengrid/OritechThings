package com.lumengrid.oritechthings.client.renderer;

import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class AcceleratorSpeedSensorBlockEntityRender implements BlockEntityRenderer<AcceleratorSpeedSensorBlockEntity, AcceleratorSpeedSensorBlockEntityRender.SpeedSensorRenderState> {
    private static final ItemStack DISPLAY_ITEM = new ItemStack(Items.ENDER_EYE);

    private final ItemModelResolver itemModelResolver;

    public AcceleratorSpeedSensorBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public SpeedSensorRenderState createRenderState() {
        return new SpeedSensorRenderState();
    }

    @Override
    public void extractRenderState(AcceleratorSpeedSensorBlockEntity blockEntity, SpeedSensorRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

        var level = blockEntity.getLevel();
        if (level == null) return;

        double renderTime = level.getGameTime() + (double) partialTicks;
        state.rotation = (float) ((renderTime * 4.0) % 360.0);

        this.itemModelResolver.updateForTopItem(
                state.itemRenderState,
                DISPLAY_ITEM,
                ItemDisplayContext.GROUND,
                level,
                null,
                0
        );
    }

    @Override
    public void submit(SpeedSensorRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        poseStack.pushPose();

        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(state.rotation));
        poseStack.scale(0.75F, 0.75F, 0.75F);

        state.itemRenderState.submit(
                poseStack,
                collector,
                15728880,
                OverlayTexture.NO_OVERLAY,
                0
        );

        poseStack.popPose();
    }

    public static class SpeedSensorRenderState extends BlockEntityRenderState {
        public final ItemStackRenderState itemRenderState = new ItemStackRenderState();
        public float rotation;
    }
}