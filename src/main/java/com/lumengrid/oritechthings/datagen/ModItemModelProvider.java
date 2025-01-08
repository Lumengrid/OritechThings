package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OritechThings.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        for(DeferredBlock<?> data: Constants.getAllAddons()){
            customItemModel(data.getId().getPath());
        }
        customItemModel(ModBlocks.ACCELERATOR_SPEED_SENSOR.getId().getPath());
        basicItem(ModItems.ACCELERATOR_TARGET_DESIGNATOR.get());
    }

    private void customItemModel(String blockName) {
        String registryName = OritechThings.MOD_ID + ":" + blockName;
        withExistingParent(registryName, modLoc("block/" + blockName));
    }
}