package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;

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
        for (DeferredBlock<?> data : Constants.getAllAddons()) {
            addonBlockState(data);
        }
        simpleBlockState(ModBlocks.ACCELERATOR_SPEED_SENSOR);
        blockWithItem(ModBlocks.INFESTED_AMETHYST_BLOCK);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void addonBlockState(DeferredBlock<?> deferredBlock) {
        ModelFile model = model(deferredBlock);
        simpleBlockItem(deferredBlock.get(), model);
        getVariantBuilder(deferredBlock.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(model)
                        .rotationY((int) state.getValue(TierAddonBlock.FACING).getOpposite().toYRot())
                        .rotationX(state.getValue(TierAddonBlock.FACE).ordinal() * 90)
                        .build());

    }

    private void simpleBlockState(DeferredBlock<?> deferredBlock) {
        ModelFile model = model(deferredBlock);
        simpleBlockItem(deferredBlock.get(), model);
        getVariantBuilder(deferredBlock.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
    }

    private static ModelFile model(DeferredBlock<?> deferredBlock) {
        ResourceLocation model = ResourceLocation
                .parse(deferredBlock.getId().getNamespace() + ":block/" + deferredBlock.getId().getPath());

        return new ModelFile.UncheckedModelFile(model);
    }
}
