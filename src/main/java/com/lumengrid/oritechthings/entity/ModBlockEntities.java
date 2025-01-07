package com.lumengrid.oritechthings.entity;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedControlBlockEntity;
import com.lumengrid.oritechthings.entity.custom.TierAddonBlockEntity;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> MOD_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, OritechThings.MOD_ID);

    public static final Supplier<BlockEntityType<AcceleratorSpeedControlBlockEntity>> ACCELERATOR_SPEED_CONTROL =
            MOD_ENTITIES.register("redstone_pulse_block_entity",
                    () -> BlockEntityType.Builder.of(AcceleratorSpeedControlBlockEntity::new,
                            ModBlocks.ACCELERATOR_SPEED_CONTROL.get()).build(null));

    @SuppressWarnings("null")
    public static final Supplier<BlockEntityType<TierAddonBlockEntity>> TIER_ADDON =
            MOD_ENTITIES.register("tier_addon",
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
                            ModBlocks.ADDON_BLOCK_PROCESSING_TIER_9.get()
                    ).build(null));
}