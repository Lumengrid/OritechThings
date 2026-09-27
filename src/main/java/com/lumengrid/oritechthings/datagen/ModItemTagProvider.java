package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;
import com.lumengrid.oritechthings.util.ModTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends TagsProvider<Item> {

    public ModItemTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(output, Registries.ITEM, lookupProvider, OritechThings.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addItemsToTag(
                Arrays.asList(Constants.getAllAddons()),
                ModTags.Items.ADDONS
        );
        addItemsToTag(
                Arrays.asList(Constants.EFFICIENCY),
                ModTags.Items.TIERED_ADDON_EFFICIENCY
        );
        addItemsToTag(
                Arrays.asList(Constants.SPEED),
                ModTags.Items.TIERED_ADDON_SPEED
        );
        addItemsToTag(
                Arrays.asList(Constants.EFFICIENT),
                ModTags.Items.TIERED_ADDON_EFFICIENT_SPEED
        );
        addItemsToTag(
                Arrays.asList(Constants.CAPACITOR),
                ModTags.Items.TIERED_ADDON_CAPACITOR
        );
        addItemsToTag(
                Arrays.asList(Constants.ACCEPTOR),
                ModTags.Items.TIERED_ADDON_ACCEPTOR
        );
        addItemsToTag(
                Arrays.asList(Constants.PROCESSING),
                ModTags.Items.TIERED_ADDON_PROCESSING
        );

        getOrCreateRawBuilder(ModTags.Items.PARTICLE_ACCELERATOR)
                .addElement(itemId(ModBlocks.ACCELERATOR_SPEED_SENSOR.get().asItem()))
                .addElement(itemId(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get().asItem()));
    }

    private void addItemsToTag(
            Iterable<? extends DeferredBlock<?>> blocks,
            net.minecraft.tags.TagKey<Item> tagKey
    ) {
        var builder = getOrCreateRawBuilder(tagKey);

        for (DeferredBlock<?> data : blocks) {
            builder.addElement(itemId(data.get().asItem()));
        }
    }

    private static Identifier itemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
