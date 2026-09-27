package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.main.OritechThings;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = OritechThings.MOD_ID)
public final class DataGenerators {

    private DataGenerators() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModBlockTagProvider::new);
        event.createProvider(ModItemTagProvider::new);

        event.createProvider((output, lookupProvider) ->
                new ModDatapackProvider(output, lookupProvider));

        event.createProvider((output, lookupProvider) ->
                new LootTableProvider(
                        output,
                        Collections.emptySet(),
                        List.of(
                                new LootTableProvider.SubProviderEntry(
                                        ModBlockLootTableProvider::new,
                                        LootContextParamSets.BLOCK
                                ),
                                new LootTableProvider.SubProviderEntry(
                                        ModEntityLootTableProvider::new,
                                        LootContextParamSets.ENTITY
                                )
                        ),
                        lookupProvider
                ));

        event.createProvider(ModRecipeProvider.Runner::new);

        event.createProvider(ModLangProvider::new);
        event.createProvider(ModLangProviderZhCn::new);
        event.createProvider(ModLangProviderRuRu::new);
        event.createProvider(ModLangProviderPtBr::new);
        event.createProvider(ModItemModelProvider::new);
        event.createProvider(ModBlockStateProvider::new);
    }
}
