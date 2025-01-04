package com.lumengrid.oritechthings.block;

import com.lumengrid.oritechthings.block.entity.TierAddonBlockEntity;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> TIER_ADDON_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, OritechThings.MOD_ID);

        


    @SuppressWarnings("null")
public static final Supplier<BlockEntityType<TierAddonBlockEntity>> TIER_ADDON =
            TIER_ADDON_ENTITIES.register("tier_addon",
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