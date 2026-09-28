package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Constants;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Optional;

public class ModBlockStateProvider extends ModelProvider {

    private static final ModelTemplate SPAWN_EGG_TEMPLATE = new ModelTemplate(
            Optional.of(Identifier.fromNamespaceAndPath("minecraft", "item/template_spawn_egg")),
            Optional.empty()
    );

    public ModBlockStateProvider(PackOutput output) {
        super(output, OritechThings.MOD_ID);
    }

    @Override
    protected void registerModels(
            BlockModelGenerators blockModels,
            ItemModelGenerators itemModels
    ) {
        for (DeferredBlock<?> data : Constants.getAllAddons()) {
            registerAddon(blockModels, data);
        }

        registerSimpleBlock(blockModels, ModBlocks.ACCELERATOR_MAGNETIC_FIELD);
        registerSimpleBlock(blockModels, ModBlocks.ACCELERATOR_SPEED_SENSOR);
        registerInfestedAmethyst(blockModels, ModBlocks.INFESTED_AMETHYST_BLOCK);

        itemModels.generateFlatItem(
                ModItems.ADVANCED_TARGET_DESIGNATOR.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.FRAME_PLACER.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.AMETHYST_FISH_SPAWN_EGG.get(),
                SPAWN_EGG_TEMPLATE
        );
    }

    private static void registerAddon(
            BlockModelGenerators blockModels,
            DeferredBlock<?> deferredBlock
    ) {
        Block block = deferredBlock.get();
        Identifier modelId = ModelLocationUtils.getModelLocation(block);

        blockModels.registerSimpleItemModel(block, modelId);

        if (!(block instanceof TierAddonBlock)) {
            blockModels.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(
                            block,
                            BlockModelGenerators.plainVariant(modelId)
                    )
            );
            return;
        }

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block).with(
                        PropertyDispatch.initial(
                                        TierAddonBlock.ADDON_USED,
                                        TierAddonBlock.FACING,
                                        TierAddonBlock.FACE
                                )
                                .select(Boolean.FALSE, Direction.NORTH, AttachFace.FLOOR, variant(modelId, Direction.NORTH, AttachFace.FLOOR))
                                .select(Boolean.FALSE, Direction.EAST, AttachFace.FLOOR, variant(modelId, Direction.EAST, AttachFace.FLOOR))
                                .select(Boolean.FALSE, Direction.SOUTH, AttachFace.FLOOR, variant(modelId, Direction.SOUTH, AttachFace.FLOOR))
                                .select(Boolean.FALSE, Direction.WEST, AttachFace.FLOOR, variant(modelId, Direction.WEST, AttachFace.FLOOR))
                                .select(Boolean.FALSE, Direction.NORTH, AttachFace.WALL, variant(modelId, Direction.NORTH, AttachFace.WALL))
                                .select(Boolean.FALSE, Direction.EAST, AttachFace.WALL, variant(modelId, Direction.EAST, AttachFace.WALL))
                                .select(Boolean.FALSE, Direction.SOUTH, AttachFace.WALL, variant(modelId, Direction.SOUTH, AttachFace.WALL))
                                .select(Boolean.FALSE, Direction.WEST, AttachFace.WALL, variant(modelId, Direction.WEST, AttachFace.WALL))
                                .select(Boolean.FALSE, Direction.NORTH, AttachFace.CEILING, variant(modelId, Direction.NORTH, AttachFace.CEILING))
                                .select(Boolean.FALSE, Direction.EAST, AttachFace.CEILING, variant(modelId, Direction.EAST, AttachFace.CEILING))
                                .select(Boolean.FALSE, Direction.SOUTH, AttachFace.CEILING, variant(modelId, Direction.SOUTH, AttachFace.CEILING))
                                .select(Boolean.FALSE, Direction.WEST, AttachFace.CEILING, variant(modelId, Direction.WEST, AttachFace.CEILING))
                                .select(Boolean.TRUE, Direction.NORTH, AttachFace.FLOOR, variant(modelId, Direction.NORTH, AttachFace.FLOOR))
                                .select(Boolean.TRUE, Direction.EAST, AttachFace.FLOOR, variant(modelId, Direction.EAST, AttachFace.FLOOR))
                                .select(Boolean.TRUE, Direction.SOUTH, AttachFace.FLOOR, variant(modelId, Direction.SOUTH, AttachFace.FLOOR))
                                .select(Boolean.TRUE, Direction.WEST, AttachFace.FLOOR, variant(modelId, Direction.WEST, AttachFace.FLOOR))
                                .select(Boolean.TRUE, Direction.NORTH, AttachFace.WALL, variant(modelId, Direction.NORTH, AttachFace.WALL))
                                .select(Boolean.TRUE, Direction.EAST, AttachFace.WALL, variant(modelId, Direction.EAST, AttachFace.WALL))
                                .select(Boolean.TRUE, Direction.SOUTH, AttachFace.WALL, variant(modelId, Direction.SOUTH, AttachFace.WALL))
                                .select(Boolean.TRUE, Direction.WEST, AttachFace.WALL, variant(modelId, Direction.WEST, AttachFace.WALL))
                                .select(Boolean.TRUE, Direction.NORTH, AttachFace.CEILING, variant(modelId, Direction.NORTH, AttachFace.CEILING))
                                .select(Boolean.TRUE, Direction.EAST, AttachFace.CEILING, variant(modelId, Direction.EAST, AttachFace.CEILING))
                                .select(Boolean.TRUE, Direction.SOUTH, AttachFace.CEILING, variant(modelId, Direction.SOUTH, AttachFace.CEILING))
                                .select(Boolean.TRUE, Direction.WEST, AttachFace.CEILING, variant(modelId, Direction.WEST, AttachFace.CEILING))
                )
        );
    }

    private static void registerSimpleBlock(
            BlockModelGenerators blockModels,
            DeferredBlock<?> deferredBlock
    ) {
        Block block = deferredBlock.get();
        Identifier modelId = ModelLocationUtils.getModelLocation(block);

        blockModels.registerSimpleItemModel(block, modelId);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(
                        block,
                        BlockModelGenerators.plainVariant(modelId)
                )
        );
    }

    private static void registerInfestedAmethyst(
            BlockModelGenerators blockModels,
            DeferredBlock<?> deferredBlock
    ) {
        Block block = deferredBlock.get();

        ModelTemplate amethystParent = new ModelTemplate(
                Optional.of(
                        Identifier.fromNamespaceAndPath(
                                "minecraft",
                                "block/amethyst_block"
                        )
                ),
                Optional.empty()
        );

        Identifier modelId = amethystParent.create(
                block,
                new TextureMapping(),
                blockModels.modelOutput
        );

        blockModels.registerSimpleItemModel(block, modelId);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(
                        block,
                        BlockModelGenerators.plainVariant(modelId)
                )
        );
    }

    private static MultiVariant variant(
            Identifier modelId,
            Direction facing,
            AttachFace face
    ) {
        return BlockModelGenerators.plainVariant(modelId)
                .with(yRotation(facing))
                .with(xRotation(face));
    }

    private static VariantMutator yRotation(
            Direction facing
    ) {
        return switch (facing) {
            case NORTH -> BlockModelGenerators.Y_ROT_180;
            case EAST -> BlockModelGenerators.Y_ROT_270;
            case SOUTH -> BlockModelGenerators.NOP;
            case WEST -> BlockModelGenerators.Y_ROT_90;
            default -> BlockModelGenerators.NOP;
        };
    }

    private static VariantMutator xRotation(
            AttachFace face
    ) {
        return switch (face) {
            case FLOOR -> BlockModelGenerators.NOP;
            case WALL -> BlockModelGenerators.X_ROT_90;
            case CEILING -> BlockModelGenerators.X_ROT_180;
        };
    }
}