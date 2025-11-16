package com.lumengrid.oritechthings.client.renderer;

import java.util.Random;

import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class AcceleratorSpeedSensorBlockEntityRender<T extends AcceleratorSpeedSensorBlockEntity>
        implements BlockEntityRenderer<T> {

    private ItemRenderer itemRender;
    private int timer;

    public AcceleratorSpeedSensorBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemRender = context.getItemRenderer();
        this.timer = new Random().nextInt(360);
    }

    @Override
    public void render(T be, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int light, int overlay) {

        timer++;
        if (timer > 360)
            timer = 0;

        poseStack.pushPose();

        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(timer));
        poseStack.scale(0.75F, 0.75F, 0.75F);

        itemRender.renderStatic(new ItemStack(Items.ENDER_EYE),
                ItemDisplayContext.GROUND, light, overlay, poseStack, buffer, be.getLevel(),
                be.getLevel().random.nextInt());

        poseStack.popPose();

    }

}
