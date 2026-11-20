package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, OritechThings.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<?> data : Constants.getAllAddons()) {
            addonBlockState(data);
        }
        
        simpleBlockState(ModBlocks.ACCELERATOR_SPEED_SENSOR);
        blockwithparentModel(ModBlocks.INFESTED_AMETHYST_BLOCK,"block/amethyst_block");
    }

    @SuppressWarnings("unused")
    private void blockWithItem(RegistryObject<?> deferredBlock) {
        simpleBlockWithItem(((net.minecraft.world.level.block.Block) deferredBlock.get()), cubeAll(((net.minecraft.world.level.block.Block) deferredBlock.get())));
    }

    private void blockwithparentModel(RegistryObject<?> deferredBlock,String parent) {
        simpleBlockWithItem(((net.minecraft.world.level.block.Block) deferredBlock.get()),
                models().withExistingParent(deferredBlock.getId().toString(), this.mcLoc(parent)));
    }

    private void addonBlockState(RegistryObject<?> deferredBlock) {
        ModelFile model = model(deferredBlock);
        simpleBlockItem(((net.minecraft.world.level.block.Block) deferredBlock.get()), model);
        
        var block = ((net.minecraft.world.level.block.Block) deferredBlock.get());
        if (block instanceof TierAddonBlock) {
            var variantBuilder = getVariantBuilder(block);
            
            // Only generate variants for properties that actually vary (ADDON_USED, FACING, FACE)
            // ADDON_TIER and ADDON_TYPE are constant for each block, so we don't need variants for them
            // This dramatically reduces the blockstate file size from ~5940 lines to ~48 lines per block
            for (boolean addonUsed : new boolean[]{false, true}) {
                for (Direction facing : Direction.values()) {
                    if (facing.getAxis().isHorizontal()) {
                        for (AttachFace face : AttachFace.values()) {
                            variantBuilder
                                    .partialState()
                                    .with(TierAddonBlock.ADDON_USED, addonUsed)
                                    .with(TierAddonBlock.FACING, facing)
                                    .with(TierAddonBlock.FACE, face)
                                    .modelForState()
                                    .modelFile(model)
                                    .rotationY((int) facing.getOpposite().toYRot())
                                    .rotationX(face.ordinal() * 90)
                                    .addModel();
                        }
                    }
                }
            }
        } else {
            // Fallback for non-TierAddonBlock blocks
            getVariantBuilder(block)
                    .forAllStates(state -> ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationY((int) state.getValue(TierAddonBlock.FACING).getOpposite().toYRot())
                            .rotationX(state.getValue(TierAddonBlock.FACE).ordinal() * 90)
                            .build());
        }
    }

    private void simpleBlockState(RegistryObject<?> deferredBlock) {
        ModelFile model = model(deferredBlock);
        simpleBlockItem(((net.minecraft.world.level.block.Block) deferredBlock.get()), model);
        getVariantBuilder(((net.minecraft.world.level.block.Block) deferredBlock.get()))
                .forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
    }

    private static ModelFile model(RegistryObject<?> deferredBlock) {
        ResourceLocation model = new ResourceLocation(deferredBlock.getId().getNamespace() + ":block/" + deferredBlock.getId().getPath());

        return new ModelFile.UncheckedModelFile(model);
    }
}
