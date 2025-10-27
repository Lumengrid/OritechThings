package com.lumengrid.oritechthings.event;

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
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import rearth.oritech.init.ComponentContent;
import rearth.oritech.item.tools.LaserTargetDesignator;

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
        checkTargetDesignator(player, InteractionHand.MAIN_HAND);
        checkTargetDesignator(player, InteractionHand.OFF_HAND);
    }

    private static void checkTargetDesignator(Player player, InteractionHand hand) {
        try {
            ItemStack item = player.getItemInHand(hand);
            if (item.isEmpty() || !(item.getItem() instanceof LaserTargetDesignator) || !item.has(ComponentContent.TARGET_POSITION.get())) {
                return;
            }
            BlockPos targetPos = item.get(ComponentContent.TARGET_POSITION.get());
            if (targetPos != null) {
                if (item.has(ModDataComponents.TARGET_DIMENSION)) {
                    ResourceKey<Level> dimension = item.get(ModDataComponents.TARGET_DIMENSION.get());
                    if (dimension == null || dimension == player.level().dimension()) {
                        RenderBlockUtils.createBox(targetPos);
                    }
                } else {
                    RenderBlockUtils.createBox(targetPos);
                }
            }
        } catch (Exception e) {
            OritechThings.LOGGER.error("checkTargetDesignator{}", e.getMessage());
        }
    }
}
