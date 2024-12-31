package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, OritechThings.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_2);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_3);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_4);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_5);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_6);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_7);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_8);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_9);
        
        addonBlockState(ModBlocks.ADDON_BLOCK_SPEED_TIER_2);
        addonBlockState(ModBlocks.ADDON_BLOCK_SPEED_TIER_3);
        addonBlockState(ModBlocks.ADDON_BLOCK_SPEED_TIER_4);
        addonBlockState(ModBlocks.ADDON_BLOCK_SPEED_TIER_5);
        addonBlockState(ModBlocks.ADDON_BLOCK_SPEED_TIER_6);
        addonBlockState(ModBlocks.ADDON_BLOCK_SPEED_TIER_7);
        addonBlockState(ModBlocks.ADDON_BLOCK_SPEED_TIER_8);
        addonBlockState(ModBlocks.ADDON_BLOCK_SPEED_TIER_9);

        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_2);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_3);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_4);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_5);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_6);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_7);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_8);
        addonBlockState(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_9);
    }

    private void addonBlockState(DeferredBlock<?> deferredBlock) {
        ResourceLocation path = ResourceLocation.parse(deferredBlock.getId().getNamespace() + ":block/" + deferredBlock.getId().getPath());
        ModelFile model = model(path);
        simpleBlockItem(deferredBlock.get(), model);
        getVariantBuilder(deferredBlock.get())
            .forAllStates(state ->
                    ConfiguredModel.builder()
                        .modelFile(model)
                        .rotationY((int) state.getValue(TierAddonBlock.FACING).getOpposite().toYRot())
                        .rotationX(state.getValue(TierAddonBlock.FACE).ordinal() * 90)
                        .build()
                );

    }

    private static ModelFile model(ResourceLocation model) {
        return new ModelFile.UncheckedModelFile(model);
    }
}
