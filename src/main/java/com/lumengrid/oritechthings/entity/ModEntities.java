package com.lumengrid.oritechthings.entity;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.lumengrid.oritechthings.entity.custom.AmethystFishEntity;
import com.lumengrid.oritechthings.entity.custom.TierAddonBlockEntity;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;
@SuppressWarnings("null")
public class ModEntities {

        public static void register(IEventBus bus){
                MOD_BLOCK_ENTITIES.register(bus);
                MOD_MOB_ENTITIES.register(bus);
        }

    public static final DeferredRegister<BlockEntityType<?>> MOD_BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, OritechThings.MOD_ID);
    public static final DeferredRegister<EntityType<?>> MOD_MOB_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, OritechThings.MOD_ID);

public static final Supplier<BlockEntityType<AcceleratorSpeedSensorBlockEntity>> accelerator_speed_sensor =
            MOD_BLOCK_ENTITIES.register("accelerator_speed_sensor_block_entity",
                    () -> BlockEntityType.Builder.of(AcceleratorSpeedSensorBlockEntity::new,
                            ModBlocks.ACCELERATOR_SPEED_SENSOR.get()).build(null));

    public static final Supplier<BlockEntityType<com.lumengrid.oritechthings.entity.custom.AcceleratorMagneticFieldBlockEntity>> ACCELERATOR_MAGNETIC_FIELD_BLOCK_ENTITY =
            MOD_BLOCK_ENTITIES.register("accelerator_magnetic_field_block_entity",
                    () -> BlockEntityType.Builder.of(com.lumengrid.oritechthings.entity.custom.AcceleratorMagneticFieldBlockEntity::new,
                            ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get()).build(null));

    public static final Supplier<EntityType<AmethystFishEntity>> AMETHYST_FISH =
            MOD_MOB_ENTITIES.register("amethyst_fish", () -> EntityType.Builder.of(AmethystFishEntity::new, MobCategory.MONSTER)
                    .sized(0.75f, 0.35f).build("amethyst_fish"));

    public static final Supplier<BlockEntityType<TierAddonBlockEntity>> TIER_ADDON =
            MOD_BLOCK_ENTITIES.register("tier_addon",
                    () -> BlockEntityType.Builder.of(TierAddonBlockEntity::new,
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
                    ).build(null));
}