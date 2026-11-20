package com.lumengrid.oritechthings.event;

import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.entity.custom.AmethystFishEntity;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

@EventBusSubscriber(modid = OritechThings.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.AMETHYST_FISH.get(), AmethystFishEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.AMETHYST_FISH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AmethystFishEntity::checkAmethystFishSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
