package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.item.custom.AdvancedTargetDesignator;
import com.lumengrid.oritechthings.main.ModDataComponents;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.RenderBlockUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import rearth.oritech.init.ComponentContent;

@EventBusSubscriber(modid = OritechThings.MOD_ID, value = Dist.CLIENT)
public class RenderWorldLastEvent {

    private static boolean loggedNoTarget = false;

    @SubscribeEvent
    public static void renderWorldLastEvent(RenderLevelStageEvent.AfterTranslucentBlocks evt) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        checkTargetDesignator(evt, player, InteractionHand.MAIN_HAND);
        checkTargetDesignator(evt, player, InteractionHand.OFF_HAND);
    }

    private static void checkTargetDesignator(RenderLevelStageEvent.AfterTranslucentBlocks evt, Player player, InteractionHand hand) {
        try {
            ItemStack item = player.getItemInHand(hand);
            if (item.isEmpty() || !(item.getItem() instanceof AdvancedTargetDesignator)) {
                return;
            }

            BlockPos targetPos = null;

            if (item.has(ModDataComponents.TARGET_POSITION.get())) {
                targetPos = item.get(ModDataComponents.TARGET_POSITION.get());
            } else if (item.has(ComponentContent.TARGET_POSITION.get())) {
                targetPos = item.get(ComponentContent.TARGET_POSITION.get());
            } else {
                if (!loggedNoTarget) {
                    loggedNoTarget = true;
                }
                return;
            }

            loggedNoTarget = false;

            if (targetPos != null) {
                if (item.has(ModDataComponents.TARGET_DIMENSION.get())) {
                    ResourceKey<Level> dimension = item.get(ModDataComponents.TARGET_DIMENSION.get());
                    if (dimension == null || dimension == player.level().dimension()) {
                        RenderBlockUtils.createBox(evt.getPoseStack(), targetPos);
                    }
                } else {
                    RenderBlockUtils.createBox(evt.getPoseStack(), targetPos);
                }
            }
        } catch (Exception e) {
            OritechThings.LOGGER.error("[DESIGNATOR RENDER ERROR] Exception in checkTargetDesignator: {}", e.getMessage(), e);
        }
    }
}