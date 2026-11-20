package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.EventBusSubscriber;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = OritechThings.MOD_ID)
public class DataGenerators {

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
                DataGenerator generator = event.getGenerator();
                PackOutput packOutput = generator.getPackOutput();
                ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

                // server
                BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(packOutput, existingFileHelper);
                generator.addProvider(event.includeServer(), new ModDatapackProvider(packOutput));
                generator.addProvider(event.includeServer(), blockTagsProvider);
                generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput,
                                blockTagsProvider.contentsGetter(), existingFileHelper));
                generator.addProvider(event.includeServer(),
                                new LootTableProvider(packOutput, Collections.emptySet(), List.of(
                                                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new,
                                                                LootContextParamSets.BLOCK),
                                                new LootTableProvider.SubProviderEntry(ModEntityLootTableProvider::new,
                                                                LootContextParamSets.ENTITY))));

                // client
                generator.addProvider(event.includeClient(), new ModLangProvider(packOutput));
                generator.addProvider(event.includeClient(), new ModLangProviderZhCn(packOutput));
                generator.addProvider(event.includeClient(), new ModLangProviderRuRu(packOutput));
                generator.addProvider(event.includeClient(), new ModLangProviderPtBr(packOutput));
                generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
                generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
        }
}
