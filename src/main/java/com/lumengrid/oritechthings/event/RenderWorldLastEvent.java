package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.main.ModDataComponents;
import com.lumengrid.oritechthings.util.RenderBlockUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
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
        checkTargetDesignator(player, InteractionHand.MAIN_HAND);
        checkTargetDesignator(player, InteractionHand.OFF_HAND);
    }

    private static void checkTargetDesignator(Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (item.isEmpty() || !(item.getItem() instanceof LaserTargetDesignator)) {
            return;
        }
        BlockPos targetPos = item.get(ComponentContent.TARGET_POSITION.get());
        if (targetPos != null) {
            ResourceKey<Level> dimension = item.get(ModDataComponents.TARGET_DIMENSION.get());
            if (dimension == null || dimension == player.level().dimension()) {
                RenderBlockUtils.createBox(targetPos);
            }
        }
    }
}
