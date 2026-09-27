package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import rearth.oritech.init.ToolsContent;
import rearth.oritech.item.tools.util.OritechEnergyItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = OritechThings.MOD_ID)
public class GameBusEvents {
    private static final Map<UUID, Boolean> wasWearingJetpack = new HashMap<>();

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        // 1. Corretto: uso del metodo isClientSide() invece del campo privato
        if (!event.getEntity().level().isClientSide()) {
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

            // 2. Corretto: uso di getItemBySlot(EquipmentSlot.CHEST) per accedere alla pettorina
            ItemStack currentArmor = player.getItemBySlot(EquipmentSlot.CHEST);
            boolean isWearingJetpackNow = (currentArmor.getItem() == ToolsContent.EXO_JETPACK.asItem());
            boolean wasWearingJetpackPrevTick = wasWearingJetpack.getOrDefault(playerId, false);

            if (isWearingJetpackNow) {
                long energy = 0;
                if (currentArmor.getItem() instanceof OritechEnergyItem energyItem) {
                    // ✅ Uso di ItemAccess.forStack(currentArmor) valido per NeoForge 1.21.2+
                    energy = energyItem.getStoredEnergy(currentArmor, ItemAccess.forStack(currentArmor));
                }
                if (energy <= ConfigLoader.getInstance().exoJetPackSettings.rfThreshold()) {
                    if (player.getAbilities().mayfly) {
                        player.sendSystemMessage(
                                Component.translatable(
                                        "message.exo_jetpack.energy_low"
                                ).withStyle(ChatFormatting.RED)
                        );
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