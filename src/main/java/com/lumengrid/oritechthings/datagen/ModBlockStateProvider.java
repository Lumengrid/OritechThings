package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, OritechThings.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_2);
        blockWithItem(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_3);
        blockWithItem(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_4);
        blockWithItem(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_5);
        blockWithItem(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_6);
        blockWithItem(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_7);
        blockWithItem(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_8);
        blockWithItem(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_9);

        blockWithItem(ModBlocks.ADDON_BLOCK_SPEED_TIER_2);
        blockWithItem(ModBlocks.ADDON_BLOCK_SPEED_TIER_3);
        blockWithItem(ModBlocks.ADDON_BLOCK_SPEED_TIER_4);
        blockWithItem(ModBlocks.ADDON_BLOCK_SPEED_TIER_5);
        blockWithItem(ModBlocks.ADDON_BLOCK_SPEED_TIER_6);
        blockWithItem(ModBlocks.ADDON_BLOCK_SPEED_TIER_7);
        blockWithItem(ModBlocks.ADDON_BLOCK_SPEED_TIER_8);
        blockWithItem(ModBlocks.ADDON_BLOCK_SPEED_TIER_9);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }


    private void tierBlockAddon(DeferredBlock<?> deferredBlock) {
        getVariantBuilder(deferredBlock.get()).forAllStates(state -> {
            if(state.getValue(TierAddonBlock.ADDON_USED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(deferredBlock.getId().getPath() + "_used",
                        ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, "block/" +deferredBlock.getId().getPath() + "_used")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(deferredBlock.getId().getPath(),
                        ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, "block/" +deferredBlock.getId().getPath())))};
            }
        });

        simpleBlockItem(deferredBlock.get(), models().cubeAll(deferredBlock.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, "block/" + deferredBlock.getId().getPath())));
    }
}
