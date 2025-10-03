package com.lumengrid.oritechthings.item.custom;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import com.lumengrid.oritechthings.main.ConfigLoader;
import com.lumengrid.oritechthings.main.ModDataComponents;
import com.lumengrid.oritechthings.api.CrossDimensionalDrone;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.block.blocks.processing.MachineCoreBlock;
import rearth.oritech.block.entity.interaction.DronePortEntity;
import rearth.oritech.block.entity.interaction.LaserArmBlockEntity;
import rearth.oritech.init.BlockContent;
import rearth.oritech.init.ComponentContent;
import rearth.oritech.item.tools.LaserTargetDesignator;

import java.util.List;
import java.util.Objects;

public class AdvancedTargetDesignator extends LaserTargetDesignator {
    public AdvancedTargetDesignator(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
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
            if (machineEntity instanceof LaserArmBlockEntity) {
                clickedPos = clickedPos.below();
                clickedBlockState = level.getBlockState(clickedPos);
            }
        }
        BlockEntity clickedEntity = level.getBlockEntity(clickedPos);
        BlockPos targetPos = null;
        ResourceKey<Level> targetDimension = null;
        ItemStack itemInHand = context.getItemInHand();
        if (itemInHand.has(ComponentContent.TARGET_POSITION.get())) {
            targetPos = itemInHand.get(ComponentContent.TARGET_POSITION.get());
            targetDimension = itemInHand.get(ModDataComponents.TARGET_DIMENSION.get());
        }
        if (clickedBlockState.getBlock().equals(BlockContent.LASER_ARM_BLOCK)) {
            if (clickedEntity instanceof LaserArmBlockEntity) {
                return setTargetFromDesignator(clickedEntity, targetPos, targetDimension, player, level.dimension());
            }
        }
        if (clickedBlockState.getBlock().equals(BlockContent.DRONE_PORT_BLOCK)) {
            if (clickedEntity instanceof DronePortEntity) {
                return setTargetFromDesignator(clickedEntity, targetPos, targetDimension, player, level.dimension());
            }
        }
        if (clickedBlockState.getBlock().equals(ModBlocks.ACCELERATOR_SPEED_SENSOR.get())) {
            if (clickedEntity instanceof AcceleratorSpeedSensorBlockEntity) {
                return setTargetFromDesignator(clickedEntity, targetPos, targetDimension, player, level.dimension());
            }
        }
        if (!clickedBlockState.getBlock().equals(Blocks.AIR)) {
            itemInHand.set(ComponentContent.TARGET_POSITION.get(), context.getClickedPos());
            itemInHand.set(ModDataComponents.TARGET_DIMENSION.get(), level.dimension());
            Objects.requireNonNull(player).sendSystemMessage(Component.translatable("message.oritech.target_designator.position_stored"));
        }

        return InteractionResult.SUCCESS;
    }

    private InteractionResult setTargetFromDesignator(BlockEntity entity, BlockPos targetPos, ResourceKey<Level> targetDimension, Player player, ResourceKey<Level> actualDimension) {
        boolean success = false;
        if (entity instanceof DronePortEntity dronePortEntity) {
            var crossDimensionalDrone = (CrossDimensionalDrone) dronePortEntity;
            success = crossDimensionalDrone.oritechthings$setCrossDimensionalTarget(targetPos, targetDimension);
        } else {
            if (targetDimension != actualDimension) {
                Objects.requireNonNull(player).sendSystemMessage(Component.translatable("message.oritechthings.advanced_target_designator.different_dimension"));
                return InteractionResult.FAIL;
            }
            switch (entity) {
                case LaserArmBlockEntity laserEntity -> {
                    if (laserEntity.hunterAddons > 0) {
                        laserEntity.cycleHunterTargetMode();
                        player.sendSystemMessage(Component.translatable("message.oritech.target_designator.hunter_target",
                                Component.translatable(laserEntity.hunterTargetMode.message)));
                        return InteractionResult.SUCCESS;
                    }
                    success = laserEntity.setTargetFromDesignator(targetPos);
                }
                case AcceleratorSpeedSensorBlockEntity speedSensorEntity -> success = speedSensorEntity.setTargetDesignator(targetPos, player);
                default -> {
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
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        if (!stack.has(ComponentContent.TARGET_POSITION.get())) {
            tooltip.add(Component.translatable("tooltip.oritech.target_designator.no_target")
                    .withStyle(ChatFormatting.RED));
        } else {
            BlockPos position = stack.get(ComponentContent.TARGET_POSITION.get());
            if (ConfigLoader.getInstance().dimensionalDroneSettings.enabled()) {
                ResourceKey<Level> dimension = stack.get(ModDataComponents.TARGET_DIMENSION.get());
                tooltip.add(Component.translatable("tooltip.oritech.target_designator.set_to", Objects.requireNonNull(position).toShortString())
                        .append(Component.literal(getDimensionName(dimension)).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)));
            }
        }
        tooltip.add(Component.empty());
        if (Screen.hasControlDown()) {
            tooltip.add(Component.translatable("tooltip.oritechthings.advanced_target_designator.usage")
                    .withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.oritechthings.advanced_target_designator.speed_sensor")
                    .withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.translatable("tooltip.oritechthings.advanced_target_designator.drone_port")
                    .withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.translatable("tooltip.oritechthings.advanced_target_designator.laser_arm")
                    .withStyle(ChatFormatting.BLUE));
            if (ConfigLoader.getInstance().dimensionalDroneSettings.enabled()) {
                tooltip.add(Component.translatable("tooltip.oritechthings.advanced_target_designator.cross_dimensional")
                        .withStyle(ChatFormatting.GOLD));
            }
        } else {
            tooltip.add(Component.translatable("tooltip.oritech.item_extra_info").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }
    }

    private String getDimensionName(ResourceKey<Level> dimensionKey) {
        if (dimensionKey == null) {
            return "Unknown";
        }
        ResourceLocation location = dimensionKey.location();

        String namespace = location.getNamespace();
        String path = location.getPath();

        return " " + switch (path) {
            case "overworld" -> "Overworld";
            case "the_nether" -> "Nether";
            case "the_end" -> "End";
            default -> namespace + ":" + path;
        };
    }
}
