package com.lumengrid.oritechthings.menu;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.entity.custom.AcceleratorSpeedSensorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class AcceleratorSpeedSensorMenu extends AbstractContainerMenu {

    public final AcceleratorSpeedSensorBlockEntity be;
    private final Level level;

    // Costruttore chiamato dal Server (o direttamente dal client se la BE è già nota)
    public AcceleratorSpeedSensorMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.SPEED_SENSOR_MENU.get(), pContainerId);

        this.be = (AcceleratorSpeedSensorBlockEntity) entity;
        this.level = inv.player.level();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    // Costruttore chiamato dal Client tramite la rete
    public AcceleratorSpeedSensorMenu(int pContainerId, Inventory inv, RegistryFriendlyByteBuf buf) {
        this(pContainerId, inv, getBlockEntityFromBuf(inv, buf));
    }

    private static BlockEntity getBlockEntityFromBuf(Inventory inv, RegistryFriendlyByteBuf buf) {
        if (buf != null && buf.readableBytes() > 0) {
            BlockPos pos = buf.readBlockPos();
            return inv.player.level().getBlockEntity(pos);
        }
        return null;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int pIndex) {
        return slots.get(pIndex).getItem();
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        if (be == null) return false;
        return stillValid(ContainerLevelAccess.create(level, be.getBlockPos()),
                pPlayer, ModBlocks.ACCELERATOR_SPEED_SENSOR.get());
    }
}