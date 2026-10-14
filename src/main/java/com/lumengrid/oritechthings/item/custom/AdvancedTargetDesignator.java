package com.lumengrid.oritechthings.item.custom;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.entity.custom.AcceleratorMagneticFieldBlockEntity;
import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.main.ModDataComponents;
import com.lumengrid.oritechthings.api.CrossDimensionalDrone;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.util.Utility;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.block.blocks.processing.MachineCoreBlock;
import rearth.oritech.block.entity.interaction.DronePortEntity;
import rearth.oritech.block.entity.interaction.EndericLaserBlockEntity;
import rearth.oritech.init.BlockContent;
import rearth.oritech.init.ComponentContent;
import rearth.oritech.item.tools.LaserTargetDesignator;

import java.util.Objects;
import java.util.function.Consumer;

public class AdvancedTargetDesignator extends LaserTargetDesignator {
    public AdvancedTargetDesignator(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        try {
            if (context.getLevel().isClientSide()) {
                return InteractionResult.SUCCESS;
            }

            BlockPos clickedPos = context.getClickedPos();
            Level level = context.getLevel();
            Player player = context.getPlayer();
            BlockState clickedBlockState = level.getBlockState(clickedPos);

            // 1. Risoluzione dei Multiblock (Enderic Laser, Drone Port ecc.)
            if (clickedBlockState.getBlock() instanceof MachineCoreBlock && clickedBlockState.getValue(MachineCoreBlock.USED)) {
                BlockEntity machineEntity = MachineCoreBlock.getControllerEntity(level, clickedPos);
                if (machineEntity != null) {
                    clickedPos = machineEntity.getBlockPos();
                    clickedBlockState = level.getBlockState(clickedPos);
                }
            }

            BlockEntity clickedEntity = level.getBlockEntity(clickedPos);
            BlockPos targetPos = null;
            ResourceKey<Level> targetDimension = null;
            ItemStack itemInHand = context.getItemInHand();

            // 2. Lettura del target salvato nel designator
            if (itemInHand.has(ComponentContent.TARGET_GLOBAL_POSITION.get())) {
                GlobalPos globalPos = itemInHand.get(ComponentContent.TARGET_GLOBAL_POSITION.get());
                if (globalPos != null) {
                    targetPos = globalPos.pos();
                    targetDimension = globalPos.dimension();
                }
            } else if (itemInHand.has(ModDataComponents.TARGET_POSITION.get())) {
                targetPos = itemInHand.get(ModDataComponents.TARGET_POSITION.get());
                targetDimension = itemInHand.get(ModDataComponents.TARGET_DIMENSION.get());
            }

            // 3. Caso specifico per l'acceleratore di particelle (Salva SEMPRE)
            if (clickedBlockState.is(BlockContent.PARTICLE_ACCELERATOR.get())) {
                if (clickedEntity instanceof rearth.oritech.block.entity.accelerator.ParticleAcceleratorBlockEntity) {
                    saveTarget(itemInHand, clickedPos, level.dimension());
                    if (player != null) {
                        player.sendSystemMessage(Component.translatable("message.oritechthings.advanced_target_designator.accelerator_saved")
                                .append(Component.literal(clickedPos.toShortString()).withStyle(ChatFormatting.BLUE)));
                    }
                    return InteractionResult.SUCCESS;
                }
            }

            // 4. Caso Enderic Laser: Comportamento nativo di Oritech
            if (clickedBlockState.is(BlockContent.ENDERIC_LASER.get()) && clickedEntity instanceof EndericLaserBlockEntity laserEntity) {
                if (laserEntity.hunterAddons > 0) {
                    laserEntity.cycleHunterTargetMode();
                    if (player != null) {
                        player.sendSystemMessage(Component.translatable("message.oritech.target_designator.hunter_target",
                                Component.translatable(laserEntity.hunterTargetMode.message)));
                    }
                    return InteractionResult.SUCCESS;
                } else if (targetPos != null) {
                    return setTargetFromDesignator(clickedEntity, targetPos, targetDimension, player, level.dimension());
                }
            }

            // 5. Assegnazione del target alle altre macchine
            if (targetPos != null) {
                if (clickedBlockState.is(BlockContent.DRONE_PORT.get()) && clickedEntity instanceof DronePortEntity) {
                    return setTargetFromDesignator(clickedEntity, targetPos, targetDimension, player, level.dimension());
                }
                if (clickedBlockState.is(ModBlocks.ACCELERATOR_SPEED_SENSOR.get()) && clickedEntity instanceof AcceleratorSpeedSensorBlockEntity sensorEntity) {
                    return setTargetFromDesignator(sensorEntity, targetPos, targetDimension, player, level.dimension());
                }
                if (clickedBlockState.is(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get()) && clickedEntity instanceof AcceleratorMagneticFieldBlockEntity magEntity) {
                    return setTargetFromDesignator(magEntity, targetPos, targetDimension, player, level.dimension());
                }
            }

            // 6. Registrazione generica: Memorizza la posizione del blocco
            if (!clickedBlockState.is(Blocks.AIR)) {
                saveTarget(itemInHand, clickedPos, level.dimension());
                if (player != null) {
                    player.sendSystemMessage(Component.translatable("message.oritech.target_designator.position_stored"));
                }
                return InteractionResult.SUCCESS;
            }
        } catch (Exception e) {
            OritechThings.LOGGER.error("AdvancedTargetDesignator.useOn: {}", e.getMessage());
        }

        return super.useOn(context);
    }

    private void saveTarget(ItemStack stack, BlockPos pos, ResourceKey<Level> dimension) {
        GlobalPos globalPos = GlobalPos.of(dimension, pos);
        stack.set(ModDataComponents.TARGET_POSITION.get(), pos);
        stack.set(ModDataComponents.TARGET_DIMENSION.get(), dimension);
        stack.set(ComponentContent.TARGET_GLOBAL_POSITION.get(), globalPos);
    }

    private InteractionResult setTargetFromDesignator(BlockEntity entity, BlockPos targetPos, ResourceKey<Level> targetDimension, Player player, ResourceKey<Level> actualDimension) {
        if (targetPos == null) {
            if (player != null) {
                player.sendSystemMessage(Component.translatable("tooltip.oritech.target_designator.no_target"));
            }
            return InteractionResult.FAIL;
        }

        boolean success = false;
        if (entity instanceof DronePortEntity dronePortEntity) {
            var crossDimensionalDrone = (CrossDimensionalDrone) dronePortEntity;
            if (ConfigLoader.getInstance().dimensionalDroneSettings.enabled()) {
                success = crossDimensionalDrone.oritechthings$setCrossDimensionalTarget(targetPos, targetDimension);
            } else {
                success = dronePortEntity.setTargetFromDesignator(GlobalPos.of(actualDimension, targetPos));
            }
        } else {
            // Verifica dimensioni per macchine non cross-dimensionali
            if (targetDimension != null && !targetDimension.equals(actualDimension)) {
                if (player != null) {
                    player.sendSystemMessage(Component.translatable("message.oritechthings.advanced_target_designator.different_dimension"));
                }
                return InteractionResult.FAIL;
            } else {
                switch (entity) {
                    case EndericLaserBlockEntity laserEntity -> {
                        success = laserEntity.setTargetFromDesignator(targetPos);
                    }
                    case AcceleratorSpeedSensorBlockEntity speedSensorEntity -> success = speedSensorEntity.setTargetDesignator(targetPos, player);
                    case AcceleratorMagneticFieldBlockEntity magneticFieldEntity -> success = magneticFieldEntity.setTargetDesignator(targetPos, player);
                    default -> {
                    }
                }
            }
        }

        if (player != null) {
            player.sendSystemMessage(
                    Component.translatable(
                            success ? "message.oritech.target_designator.position_saved"
                                    : "message.oritechthings.advanced_target_designator.position_invalid"));
        }

        return success ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        BlockPos data = null;
        ResourceKey<Level> dimension = null;

        if (stack.has(ComponentContent.TARGET_GLOBAL_POSITION.get())) {
            GlobalPos globalPos = stack.get(ComponentContent.TARGET_GLOBAL_POSITION.get());
            if (globalPos != null) {
                data = globalPos.pos();
                dimension = globalPos.dimension();
            }
        } else if (stack.has(ModDataComponents.TARGET_POSITION.get())) {
            data = stack.get(ModDataComponents.TARGET_POSITION.get());
            dimension = stack.get(ModDataComponents.TARGET_DIMENSION.get());
        }

        if (data != null) {
            tooltip.accept(Component.translatable("tooltip.oritech.target_designator.set_to", data.toShortString()));
        } else {
            tooltip.accept(Component.translatable("tooltip.oritech.target_designator.no_target"));
        }

        if (ConfigLoader.getInstance().dimensionalDroneSettings.enabled() && dimension != null) {
            tooltip.accept(Component.translatable("tooltip.oritechthings.advanced_target_designator.dimension")
                    .append(Component.literal(getDimensionName(dimension)).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)));
        }

        tooltip.accept(Component.empty());
        if (Utility.isControlDown()) {
            tooltip.accept(Component.translatable("tooltip.oritechthings.advanced_target_designator.usage")
                    .withStyle(ChatFormatting.GRAY));
            tooltip.accept(Component.translatable("tooltip.oritechthings.advanced_target_designator.speed_sensor")
                    .withStyle(ChatFormatting.BLUE));
            if (ConfigLoader.getInstance().magneticFieldSettings.enabled()) {
                tooltip.accept(Component.translatable("tooltip.oritechthings.advanced_target_designator.magnetic_field")
                        .withStyle(ChatFormatting.BLUE));
            }
            tooltip.accept(Component.translatable("tooltip.oritechthings.advanced_target_designator.drone_port")
                    .withStyle(ChatFormatting.BLUE));
            tooltip.accept(Component.translatable("tooltip.oritechthings.advanced_target_designator.laser_arm")
                    .withStyle(ChatFormatting.BLUE));
            if (ConfigLoader.getInstance().dimensionalDroneSettings.enabled()) {
                tooltip.accept(Component.translatable("tooltip.oritechthings.advanced_target_designator.cross_dimensional")
                        .withStyle(ChatFormatting.GOLD));
            }
        } else {
            tooltip.accept(Component.translatable("tooltip.oritech.item_extra_info").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }
    }

    private String getDimensionName(ResourceKey<Level> dimensionKey) {
        if (dimensionKey == null) {
            return "Unknown";
        }

        Identifier identifier = dimensionKey.identifier();

        return switch (identifier.getPath()) {
            case "overworld" -> "Overworld";
            case "the_nether" -> "Nether";
            case "the_end" -> "End";
            default -> identifier.toString();
        };
    }
}
