package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.main.ModDataComponents;
import com.lumengrid.oritechthings.util.RenderBlockUtils;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.init.ComponentContent;
import rearth.oritech.item.tools.LaserTargetDesignator;

public class RenderWorldLastEvent {

    @SubscribeEvent
    static void renderWorldLastEvent(RenderLevelStageEvent evt) {
        if (evt.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            return;
        }
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        checkTargetDesignator(evt, player, InteractionHand.MAIN_HAND);
        // checkTargetDesignator(evt, player, InteractionHand.OFF_HAND);
    }

    private static void checkTargetDesignator(RenderLevelStageEvent event, Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (item.isEmpty() || !(item.getItem() instanceof LaserTargetDesignator)) {
            return;
        }
        BlockPos targetPos = item.get(ComponentContent.TARGET_POSITION.get());
        if (targetPos != null) {
            ResourceKey<Level> dimension = item.get(ModDataComponents.TARGET_DIMENSION.get());
            if (dimension == null || dimension == player.level().dimension()) {
                final Minecraft mc = Minecraft.getInstance();
                PoseStack matrix = getPoseStack(event, targetPos, mc, hand);
                RenderBlockUtils.createBox(matrix, targetPos);
            }
        }
    }
    private static @NotNull PoseStack getPoseStack(RenderLevelStageEvent event, BlockPos pos, Minecraft mc, InteractionHand hand) {
        Vec3 view = mc.gameRenderer.getMainCamera().getPosition();
        PoseStack matrix = event.getPoseStack();
        matrix.pushPose();
        matrix.translate(-view.x(), -view.y(), -view.z());
        matrix.pushPose();
        matrix.translate(pos.getX(), pos.getY(), pos.getZ());
//        if (hand == InteractionHand.MAIN_HAND) {
//            matrix.translate(pos.getX(), pos.getY(), pos.getZ());
//        } else if (hand == InteractionHand.OFF_HAND) {
//            matrix.translate(pos.getX(), pos.getY(), pos.getZ());
//            matrix.translate(0.2f, 0.2f, 0.2f); // Adjust this offset if necessary
//        }
        matrix.scale(1.01f, 1.01f, 1.01f);

        return matrix;
    }
}
