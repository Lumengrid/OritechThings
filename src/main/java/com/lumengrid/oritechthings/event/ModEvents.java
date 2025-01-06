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
import rearth.oritech.util.energy.EnergyApi;

@EventBusSubscriber(modid = OritechThings.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            if (player.isCreative() || player.isSpectator()) return;
            if (!ConfigLoader.getInstance().exoJetPackSettings.enabledCreativeFlight()) return;
            ItemStack armor = player.getInventory().armor.get(2);

            if (armor.getItem() == ToolsContent.EXO_JETPACK.asItem()) {
                long energy = 0;
                if (EnergyApi.ITEM != null && armor.getItem() instanceof OritechEnergyItem energyItem) {
                    energy = energyItem.getStoredEnergy(armor);
                }
                if (energy <= ConfigLoader.getInstance().exoJetPackSettings.rfThreshold()) {
                    if (player.getAbilities().mayfly) {
                        player.sendSystemMessage(Component.literal("Exo Jetpack - Energy Low"));
                    }
                    disableCreativeFlight(player);
                    return;
                }
                if (!player.getAbilities().mayfly) {
                    enableCreativeFlight(player);
                }
            }
        }
    }

    private static void enableCreativeFlight(Player player) {
        player.getAbilities().mayfly = true;
        player.onUpdateAbilities();
    }

    private static void disableCreativeFlight(Player player) {
        player.getAbilities().flying = false;
        player.getAbilities().mayfly = false;
        player.onUpdateAbilities();
    }
}
