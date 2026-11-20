package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitlesPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.EventBusSubscriber;
import net.minecraftforge.event.PlayerTickEvent;
import rearth.oritech.init.ToolsContent;
import rearth.oritech.item.tools.util.OritechEnergyItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = OritechThings.MOD_ID)
// TODO(1.20.1 oritech swap): OritechEnergyItem/ToolsContent API may differ in oritech 1.20.1.
public class GameBusEvents {
    private static final Map<UUID, Boolean> wasWearingJetpack = new HashMap<>();

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        // NeoForge 1.21.1 used a PlayerTickEvent.Post subtype; Forge 1.20.1 has one event with a phase
        if (event.getPhase() != PlayerTickEvent.Phase.END) {
            return;
        }
        if (!event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            UUID playerId = player.getUUID();

            if (player.isCreative() || player.isSpectator()) {
                wasWearingJetpack.remove(playerId);
                return;
            }
            if (!ConfigLoader.getInstance().exoJetPackSettings.enabledCreativeFlight()) {
                wasWearingJetpack.remove(playerId);
                return;
            }

            ItemStack currentArmor = player.getInventory().armor.get(2);
            boolean isWearingJetpackNow = (currentArmor.getItem() == ToolsContent.EXO_JETPACK.asItem());
            boolean wasWearingJetpackPrevTick = wasWearingJetpack.getOrDefault(playerId, false);

            if (isWearingJetpackNow) {
                long energy = 0;
                if (currentArmor.getItem() instanceof OritechEnergyItem energyItem) {
                    energy = energyItem.getStoredEnergy(currentArmor);
                }
                if (energy <= ConfigLoader.getInstance().exoJetPackSettings.rfThreshold()) {
                    if (player.getAbilities().mayfly) {
                        // 1.20.1: action bar via titles packet (displayClientMessage is 1.20.2+)
                        player.connection.send(new ClientboundSetTitlesPacket(ClientboundSetTitlesPacket.Action.ACTIONBAR,
                                Component.translatable("message.exo_jetpack.energy_low")));
                    }
                    setCreativeFlight(player, false);
                } else {
                    boolean grounded = player.onGround() || player.isUnderWater();
                    if (grounded) {
                        setCreativeFlight(player, false);
                    } else {
                        if (!player.getAbilities().mayfly) {
                            setCreativeFlight(player, true);
                        }
                    }
                }
            } else {
                if (wasWearingJetpackPrevTick && player.getAbilities().mayfly) {
                    setCreativeFlight(player, false);
                }
            }
            wasWearingJetpack.put(playerId, isWearingJetpackNow);
        }
    }

    @SuppressWarnings("deprecation")
    private static void setCreativeFlight(Player player, Boolean bool) {
        if (player.getAbilities().mayfly != bool) {
            if (!bool) player.getAbilities().flying = false;
            player.getAbilities().mayfly = bool;
            player.onUpdateAbilities();
        }
    }
}