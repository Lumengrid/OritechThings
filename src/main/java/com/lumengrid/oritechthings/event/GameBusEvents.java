package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import rearth.oritech.init.ToolsContent;
import rearth.oritech.item.tools.util.OritechEnergyItem;

@EventBusSubscriber(modid = OritechThings.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class GameBusEvents {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            if (player.isCreative() || player.isSpectator())
                return;
            if (!ConfigLoader.getInstance().exoJetPackSettings.enabledCreativeFlight())
                return;
            ItemStack armor = player.getInventory().armor.get(2);
            if (armor.getItem() == ToolsContent.EXO_JETPACK.asItem()) {
                long energy = 0;
                if (armor.getItem() instanceof OritechEnergyItem energyItem) {
                    energy = energyItem.getStoredEnergy(armor);
                }
                if (energy <= ConfigLoader.getInstance().exoJetPackSettings.rfThreshold()) {
                    if (player.getAbilities().mayfly) {
                        player.displayClientMessage(Component.translatable("message.exojetpack.energy_low"), true);
                    }
                    setCreativeFlight(player, false);
                    return;
                }
                if (!player.getAbilities().mayfly) {
                    setCreativeFlight(player, true);
                }
            } else {
                if (player.getAbilities().mayfly) {
                    setCreativeFlight(player, false);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static void setCreativeFlight(Player player, Boolean bool) {
        if (!bool) player.getAbilities().flying = bool;
        player.getAbilities().mayfly = bool;
        player.onUpdateAbilities();
    }
}
