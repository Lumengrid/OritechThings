package com.lumengrid.oritechthings.entity;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.entity.custom.AcceleratorMagneticFieldBlockEntity;
import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.lumengrid.oritechthings.entity.custom.AmethystFishEntity;
import com.lumengrid.oritechthings.entity.custom.TierAddonBlockEntity;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class ModEntities {

    public static void register(IEventBus bus) {
        MOD_BLOCK_ENTITIES.register(bus);
        MOD_MOB_ENTITIES.register(bus);
        // ✅ EnergyApi rimosso: la registrazione della capability energetica
        // avvengono in automatico tramite le interfacce EnergyProvider / ExpandableEnergyStorageBlockEntity
    }

    public static final DeferredRegister<BlockEntityType<?>> MOD_BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, OritechThings.MOD_ID);

    public static final DeferredRegister<EntityType<?>> MOD_MOB_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, OritechThings.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AcceleratorSpeedSensorBlockEntity>> ACCELERATOR_SPEED_SENSOR =
            MOD_BLOCK_ENTITIES.register("accelerator_speed_sensor_block_entity",
                    () -> new BlockEntityType<>(AcceleratorSpeedSensorBlockEntity::new,
                            Set.of(ModBlocks.ACCELERATOR_SPEED_SENSOR.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AcceleratorMagneticFieldBlockEntity>> ACCELERATOR_MAGNETIC_FIELD_BLOCK_ENTITY =
            MOD_BLOCK_ENTITIES.register("accelerator_magnetic_field_block_entity",
                    () -> new BlockEntityType<>(AcceleratorMagneticFieldBlockEntity::new,
                            Set.of(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get())));

    // ✅ ResourceLocation.fromNamespaceAndPath per NeoForge 26.1+
    public static final DeferredHolder<EntityType<?>, EntityType<AmethystFishEntity>> AMETHYST_FISH =
            MOD_MOB_ENTITIES.register("amethyst_fish",
                    () -> EntityType.Builder.of(AmethystFishEntity::new, MobCategory.MONSTER)
                            .sized(0.75f, 0.35f)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(OritechThings.MOD_ID, "amethyst_fish"))));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TierAddonBlockEntity>> TIER_ADDON =
            MOD_BLOCK_ENTITIES.register("tier_addon",
                    () -> new BlockEntityType<>(TierAddonBlockEntity::new,
                            Set.of(
                                    ModBlocks.ADDON_BLOCK_SPEED_TIER_2.get(),
                                    ModBlocks.ADDON_BLOCK_SPEED_TIER_3.get(),
                                    ModBlocks.ADDON_BLOCK_SPEED_TIER_4.get(),
                                    ModBlocks.ADDON_BLOCK_SPEED_TIER_5.get(),
                                    ModBlocks.ADDON_BLOCK_SPEED_TIER_6.get(),
                                    ModBlocks.ADDON_BLOCK_SPEED_TIER_7.get(),
                                    ModBlocks.ADDON_BLOCK_SPEED_TIER_8.get(),
                                    ModBlocks.ADDON_BLOCK_SPEED_TIER_9.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_2.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_3.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_4.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_5.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_6.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_7.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_8.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_9.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_2.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_3.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_4.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_5.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_6.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_7.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_8.get(),
                                    ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_9.get(),
                                    ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_2.get(),
                                    ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_3.get(),
                                    ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_4.get(),
                                    ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_5.get(),
                                    ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_6.get(),
                                    ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_7.get(),
                                    ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_8.get(),
                                    ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_9.get(),
                                    ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_2.get(),
                                    ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_3.get(),
                                    ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_4.get(),
                                    ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_5.get(),
                                    ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_6.get(),
                                    ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_7.get(),
                                    ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_8.get(),
                                    ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_9.get(),
                                    ModBlocks.ADDON_BLOCK_PROCESSING_TIER_2.get(),
                                    ModBlocks.ADDON_BLOCK_PROCESSING_TIER_3.get(),
                                    ModBlocks.ADDON_BLOCK_PROCESSING_TIER_4.get(),
                                    ModBlocks.ADDON_BLOCK_PROCESSING_TIER_5.get(),
                                    ModBlocks.ADDON_BLOCK_PROCESSING_TIER_6.get(),
                                    ModBlocks.ADDON_BLOCK_PROCESSING_TIER_7.get(),
                                    ModBlocks.ADDON_BLOCK_PROCESSING_TIER_8.get(),
                                    ModBlocks.ADDON_BLOCK_PROCESSING_TIER_9.get(),
                                    ModBlocks.ADDON_BLOCK_CROSS_DIMENSIONAL.get()
                            )));
}