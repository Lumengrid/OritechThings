package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.item.custom.AdvancedTargetDesignator;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.RenderBlockUtils;
import com.lumengrid.oritechthings.util.TargetTagUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;

@OnlyIn(Dist.CLIENT)
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
        checkTargetDesignator(player, InteractionHand.MAIN_HAND, evt.getPoseStack());
        checkTargetDesignator(player, InteractionHand.OFF_HAND, evt.getPoseStack());
    }

    private static void checkTargetDesignator(Player player, InteractionHand hand, PoseStack poseStack) {
        try {
            ItemStack item = player.getItemInHand(hand);
            // Only render for AdvancedTargetDesignator items
            if (item.isEmpty() || !(item.getItem() instanceof AdvancedTargetDesignator) || !TargetTagUtil.hasTarget(item)) {
                return;
            }
            BlockPos targetPos = TargetTagUtil.getTargetPos(item);
            if (targetPos != null) {
                if (item.getTag() != null && TargetTagUtil.getTargetDim(item) != null) {
                    ResourceKey<Level> dimension = TargetTagUtil.getTargetDim(item);
                    if (dimension == null || dimension == player.level().dimension()) {
                        RenderBlockUtils.createBox(poseStack, targetPos);
                    }
                } else {
                    RenderBlockUtils.createBox(poseStack, targetPos);
                }
            }
        } catch (Exception e) {
            OritechThings.LOGGER.error("checkTargetDesignator: {}", e.getMessage());
        }
    }
}
