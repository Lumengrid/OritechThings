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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
            var crossDimensionEnabled = ConfigLoader.getInstance().dimensionalDroneSettings.enabled();
            if (!crossDimensionEnabled) {
                return super.useOn(context);
            }
            BlockPos clickedPos = context.getClickedPos();
            Level level = context.getLevel();
            Player player = context.getPlayer();
            BlockState clickedBlockState = level.getBlockState(clickedPos);
            if (clickedBlockState.getBlock() instanceof MachineCoreBlock && clickedBlockState.getValue(MachineCoreBlock.USED)) {
                BlockEntity machineEntity = MachineCoreBlock.getControllerEntity(level, clickedPos);
                if (machineEntity instanceof EndericLaserBlockEntity) {
                    clickedPos = clickedPos.below();
                    clickedBlockState = level.getBlockState(clickedPos);
                }
            }
            BlockEntity clickedEntity = level.getBlockEntity(clickedPos);
            BlockPos targetPos = null;
            ResourceKey<Level> targetDimension = null;
            ItemStack itemInHand = context.getItemInHand();
            if (itemInHand.has(ModDataComponents.TARGET_POSITION)) {
                targetPos = itemInHand.get(ModDataComponents.TARGET_POSITION.get());
                targetDimension = itemInHand.get(ModDataComponents.TARGET_DIMENSION.get());
            }
            if (clickedBlockState.getBlock().equals(BlockContent.ENDERIC_LASER)) {
                if (clickedEntity instanceof EndericLaserBlockEntity) {
                    return setTargetFromDesignator(clickedEntity, targetPos, targetDimension, player, level.dimension());
                }
            }
            if (clickedBlockState.getBlock().equals(BlockContent.DRONE_PORT)) {
                if (clickedEntity instanceof DronePortEntity) {
                    return setTargetFromDesignator(clickedEntity, targetPos, targetDimension, player, level.dimension());
                }
            }
            if (clickedBlockState.getBlock().equals(ModBlocks.ACCELERATOR_SPEED_SENSOR.get())) {
                if (clickedEntity instanceof AcceleratorSpeedSensorBlockEntity) {
                    return setTargetFromDesignator(clickedEntity, targetPos, targetDimension, player, level.dimension());
                }
            }
            if (clickedBlockState.getBlock().equals(ModBlocks.ACCELERATOR_MAGNETIC_FIELD.get())) {
                if (clickedEntity instanceof com.lumengrid.oritechthings.entity.custom.AcceleratorMagneticFieldBlockEntity) {
                    return setTargetFromDesignator(clickedEntity, targetPos, targetDimension, player, level.dimension());
                }
            }
            // Check if clicking on a particle accelerator controller
            if (clickedBlockState.getBlock().equals(BlockContent.PARTICLE_ACCELERATOR)) {
                if (clickedEntity instanceof rearth.oritech.block.entity.accelerator.ParticleAcceleratorBlockEntity) {
                    // Save the accelerator position in the designator
                    itemInHand.set(ModDataComponents.TARGET_POSITION.get(), context.getClickedPos());
                    itemInHand.set(ModDataComponents.TARGET_DIMENSION.get(), level.dimension());
                    Objects.requireNonNull(player).sendSystemMessage(Component.translatable("message.oritechthings.advanced_target_designator.accelerator_saved")
                            .append(Component.literal(context.getClickedPos().toShortString()).withStyle(ChatFormatting.BLUE)));
                    return InteractionResult.SUCCESS;
                }
            }
            if (!clickedBlockState.getBlock().equals(Blocks.AIR)) {
                itemInHand.set(ModDataComponents.TARGET_POSITION.get(), context.getClickedPos());
                itemInHand.set(ModDataComponents.TARGET_DIMENSION.get(), level.dimension());
                Objects.requireNonNull(player).sendSystemMessage(Component.translatable("message.oritech.target_designator.position_stored"));
            }
        } catch (Exception e) {
            OritechThings.LOGGER.error("AdvancedTargetDesignator.useOn: {}", e.getMessage());
        }

        return InteractionResult.SUCCESS;
    }

    private InteractionResult setTargetFromDesignator(BlockEntity entity, BlockPos targetPos, ResourceKey<Level> targetDimension, Player player, ResourceKey<Level> actualDimension) {
        boolean success = false;
        if (entity instanceof DronePortEntity dronePortEntity) {
            var crossDimensionalDrone = (CrossDimensionalDrone) dronePortEntity;
            success = crossDimensionalDrone.oritechthings$setCrossDimensionalTarget(targetPos, targetDimension);
        } else {
            // Check for cross-dimensional targets
            if (targetDimension != actualDimension) {
                Objects.requireNonNull(player).sendSystemMessage(Component.translatable("message.oritechthings.advanced_target_designator.different_dimension"));
                return InteractionResult.FAIL;
            } else {
                // Same dimension - proceed normally
                switch (entity) {
                    case EndericLaserBlockEntity laserEntity -> {
                        if (laserEntity.hunterAddons > 0) {
                            laserEntity.cycleHunterTargetMode();
                            player.sendSystemMessage(Component.translatable("message.oritech.target_designator.hunter_target",
                                    Component.translatable(laserEntity.hunterTargetMode.message)));
                            return InteractionResult.SUCCESS;
                        }
                        success = laserEntity.setTargetFromDesignator(targetPos);
                    }
                    case AcceleratorSpeedSensorBlockEntity speedSensorEntity -> success = speedSensorEntity.setTargetDesignator(targetPos, player);
                    case AcceleratorMagneticFieldBlockEntity magneticFieldEntity -> success = magneticFieldEntity.setTargetDesignator(targetPos, player);
                    default -> {
                    }
                }
            }
        }
        Objects.requireNonNull(player).sendSystemMessage(
                Component.translatable(
                        success ? "message.oritech.target_designator.position_saved"
                                : "message.oritechthings.advanced_target_designator.position_invalid"));

        return success ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        if (stack.has(ModDataComponents.TARGET_POSITION.get())) {
            BlockPos data = stack.get(ModDataComponents.TARGET_POSITION.get());
            assert data != null;
            tooltip.accept(Component.translatable("tooltip.oritech.target_designator.set_to", data.toShortString()));
        } else {
            tooltip.accept(Component.translatable("tooltip.oritech.target_designator.no_target"));
        }
        if (ConfigLoader.getInstance().dimensionalDroneSettings.enabled()) {
            ResourceKey<Level> dimension = stack.get(ModDataComponents.TARGET_DIMENSION.get());
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