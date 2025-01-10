package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.entity.custom.AmethystFishEntity;
import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import rearth.oritech.init.ToolsContent;
import rearth.oritech.item.tools.util.OritechEnergyItem;
import rearth.oritech.util.energy.EnergyApi;

@EventBusSubscriber(modid = OritechThings.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class GameBusEvents {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        Player player = event.getPlayer();
        if (player.isCreative()) {
            return;
        }
        if (!event.getState().is(Blocks.AMETHYST_BLOCK)) {
            return;
        }
        BlockPos pos = event.getPos();
        if (!AmethystFishEntity.checkAmethystFishSpawnRules(ModEntities.AMETHYST_FISH.get(), event.getLevel(),
                MobSpawnType.SPAWNER, pos, RandomSource.create())) {
            return;
        }
        Level level = player.level();
        if (level.random.nextFloat() >= 0.1f) {
            return;
        }
        AmethystFishEntity amethystFish = ModEntities.AMETHYST_FISH.get().create(level);
        if (amethystFish != null) {
            amethystFish.setPos(
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5
            );
            amethystFish.setDeltaMovement(
                    level.random.nextDouble() * 0.2 - 0.1,
                    0.2,
                    level.random.nextDouble() * 0.2 - 0.1
            );
            level.addFreshEntity(amethystFish);
        }
    }

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
                if (EnergyApi.ITEM != null && armor.getItem() instanceof OritechEnergyItem energyItem) {
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
