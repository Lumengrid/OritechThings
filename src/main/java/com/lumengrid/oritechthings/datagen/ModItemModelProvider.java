package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OritechThings.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        customItemModel(ModBlocks.ADDON_BLOCK_SPEED_TIER_2.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_SPEED_TIER_3.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_SPEED_TIER_4.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_SPEED_TIER_5.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_SPEED_TIER_6.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_SPEED_TIER_7.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_SPEED_TIER_8.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_SPEED_TIER_9.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_2.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_3.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_4.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_5.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_6.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_7.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_8.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_9.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_2.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_3.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_4.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_5.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_6.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_7.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_8.getId().getPath());
        customItemModel(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_9.getId().getPath());
    }

    private void customItemModel(String blockName) {
        String registryName = OritechThings.MOD_ID + ":" + blockName;
        withExistingParent(registryName, modLoc("block/" + blockName));
    }
}
