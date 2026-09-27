package com.lumengrid.oritechthings.entity.custom;

import com.lumengrid.oritechthings.block.custom.AcceleratorSpeedSensorBlock;
import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.menu.AcceleratorSpeedSensorMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AcceleratorSpeedSensorBlockEntity
        extends BlockEntity
        implements MenuProvider {

    private int speedLimit = 1000;
    private boolean enabled = false;
    private boolean checkGreater = true;
    private boolean automaticMode = false;

    @Nullable
    private BlockPos targetDesignator;

    public AcceleratorSpeedSensorBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(ModEntities.ACCELERATOR_SPEED_SENSOR.get(), pos, state);
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speed) {
        this.speedLimit = speed;
        sync();
    }

    public boolean isCheckGreater() {
        return checkGreater;
    }

    public void setCheckGreater(boolean checkGreater) {
        this.checkGreater = checkGreater;
        sync();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        sync();
    }

    public boolean isAutomaticMode() {
        return automaticMode;
    }

    public void setAutomaticMode(boolean automaticMode) {
        this.automaticMode = automaticMode;
        sync();
    }

    public void sync() {
        setChanged();

        if (level != null) {
            level.sendBlockUpdated(
                    getBlockPos(),
                    getBlockState(),
                    getBlockState(),
                    AcceleratorSpeedSensorBlock.UPDATE_ALL
            );
        }
    }

    @Nullable
    public BlockPos getTargetDesignator() {
        return targetDesignator;
    }

    public boolean setTargetDesignator(
            @Nullable BlockPos targetPos,
            Player player
    ) {
        if (targetPos == null || level == null) {
            return false;
        }

        BlockEntity targetEntity = level.getBlockEntity(targetPos);

        if (targetEntity == null) {
            player.sendSystemMessage(
                    Component.translatable(
                            "block.oritechthings.particle_accelerator_speed_sensor.invalid_controller"
                    ).withStyle(ChatFormatting.RED)
            );

            return false;
        }

        int distance = targetPos.distManhattan(getBlockPos());

        if (distance > 128) {
            player.sendSystemMessage(
                    Component.translatable(
                                    "block.oritechthings.particle_accelerator_speed_sensor.invalid_controller.to_far"
                            )
                            .append(
                                    Component.literal(" (" + distance + ")")
                                            .withStyle(ChatFormatting.ITALIC)
                            )
                            .withStyle(ChatFormatting.RED)
            );

            return false;
        }

        this.targetDesignator = targetPos;
        this.enabled = true;

        level.playSound(
                player,
                getBlockPos(),
                SoundEvents.ALLAY_AMBIENT_WITH_ITEM,
                SoundSource.BLOCKS,
                1.0F,
                1.0F
        );

        player.sendSystemMessage(
                Component.translatable(
                        "block.oritechthings.particle_accelerator_speed_sensor.controller_set"
                ).append(
                        Component.literal(targetPos.toShortString())
                                .withStyle(ChatFormatting.BLUE)
                )
        );

        sync();
        return true;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(
            HolderLookup.@NotNull Provider registries
    ) {
        return saveWithoutMetadata(registries);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.putInt("SpeedLimit", speedLimit);
        output.putBoolean("Enabled", enabled);
        output.putBoolean("CheckGreater", checkGreater);
        output.putBoolean("AutomaticMode", automaticMode);

        if (targetDesignator != null) {
            output.putInt("TargetX", targetDesignator.getX());
            output.putInt("TargetY", targetDesignator.getY());
            output.putInt("TargetZ", targetDesignator.getZ());
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        speedLimit = input
                .getInt("SpeedLimit")
                .orElse(1000);

        enabled = input
                .read("Enabled", Codec.BOOL)
                .orElse(false);

        checkGreater = input
                .read("CheckGreater", Codec.BOOL)
                .orElse(true);

        automaticMode = input
                .read("AutomaticMode", Codec.BOOL)
                .orElse(false);

        int x = input
                .getInt("TargetX")
                .orElse(Integer.MIN_VALUE);

        int y = input
                .getInt("TargetY")
                .orElse(Integer.MIN_VALUE);

        int z = input
                .getInt("TargetZ")
                .orElse(Integer.MIN_VALUE);

        if (x != Integer.MIN_VALUE
                && y != Integer.MIN_VALUE
                && z != Integer.MIN_VALUE) {
            targetDesignator = new BlockPos(x, y, z);
        } else {
            targetDesignator = null;
        }
    }


    public static <T extends BlockEntity> void tick(
            Level level,
            BlockPos pos,
            BlockState state,
            T ignored
    ) {
        if (level.isClientSide()) {
            return;
        }

        if (!(level.getBlockEntity(pos)
                instanceof AcceleratorSpeedSensorBlockEntity sensor)) {
            return;
        }

        if (!sensor.enabled || sensor.targetDesignator == null) {
            setPowered(level, pos, state, false);
            return;
        }

        BlockEntity target =
                level.getBlockEntity(sensor.targetDesignator);

        boolean powered = isPowered(sensor, target);

        if (powered != state.getValue(
                AcceleratorSpeedSensorBlock.POWERED
        )) {
            setPowered(level, pos, state, powered);
        }
    }

    private static boolean isPowered(
            AcceleratorSpeedSensorBlockEntity sensor,
            BlockEntity target
    ) {
        if (target == null) {
            return false;
        }

        Object particle = invokeNoArg(target, "getParticle");

        if (particle == null) {
            return false;
        }

        Number velocity = readNumber(
                particle,
                "velocity",
                "getVelocity"
        );

        if (velocity == null) {
            return false;
        }

        /*
         * La vecchia modalità automatica usava
         * SimpleCraftingInventory e RecipeContent.
         * Queste API non esistono più in Oritech 2.0.0-exp7.
         *
         * Temporaneamente viene usato speedLimit anche in automaticMode.
         */
        int targetSpeed = sensor.speedLimit;
        double currentVelocity = velocity.doubleValue();

        if (sensor.checkGreater) {
            return currentVelocity > targetSpeed;
        }

        return currentVelocity < targetSpeed;
    }

    private static void setPowered(
            Level level,
            BlockPos pos,
            BlockState state,
            boolean powered
    ) {
        level.setBlock(
                pos,
                state.setValue(
                        AcceleratorSpeedSensorBlock.POWERED,
                        powered
                ),
                3
        );

        notifyNeighbors(level, pos);
    }

    private static void notifyNeighbors(
            Level level,
            BlockPos pos
    ) {
        level.updateNeighborsAt(
                pos,
                level.getBlockState(pos).getBlock()
        );

        for (Direction direction : Direction.values()) {
            BlockPos neighbor = pos.relative(direction);

            level.updateNeighborsAt(
                    neighbor,
                    level.getBlockState(neighbor).getBlock()
            );
        }
    }

    @Nullable
    private static Object invokeNoArg(
            Object object,
            String methodName
    ) {
        Class<?> current = object.getClass();

        while (current != null) {
            try {
                Method method = current.getDeclaredMethod(methodName);
                method.setAccessible(true);
                return method.invoke(object);
            } catch (ReflectiveOperationException ignored) {
                current = current.getSuperclass();
            }
        }

        return null;
    }

    @Nullable
    private static Number readNumber(
            Object object,
            String fieldName,
            String getterName
    ) {
        Object getterValue = invokeNoArg(object, getterName);

        if (getterValue instanceof Number number) {
            return number;
        }

        Class<?> current = object.getClass();

        while (current != null) {
            try {
                Field field = current.getDeclaredField(fieldName);
                field.setAccessible(true);

                Object value = field.get(object);

                if (value instanceof Number number) {
                    return number;
                }
            } catch (ReflectiveOperationException ignored) {
                current = current.getSuperclass();
            }
        }

        return null;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(
                "block.oritechthings.particle_accelerator_speed_sensor"
        );
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(
            int containerId,
            @NotNull Inventory inventory,
            @NotNull Player player
    ) {
        return new AcceleratorSpeedSensorMenu(
                containerId,
                inventory,
                this
        );
    }
}
