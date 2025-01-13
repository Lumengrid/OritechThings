package com.lumengrid.oritechthings.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FramePlacerMenu extends AbstractContainerMenu {
    private final BlockPos pos;
    private int number1 = 3;
    private int number2 = 3;

    public FramePlacerMenu(int pContainerId, Inventory inv, FriendlyByteBuf buf) {
        super(ModMenuTypes.FRAME_PLACER_MENU.get(), pContainerId);
        this.pos = buf.readBlockPos();

        // Add two slots to input the numbers
        this.addSlot(new Slot(inv, 0, 80, 20));  // First number slot
        this.addSlot(new Slot(inv, 1, 80, 50));  // Second number slot

        // Set the player's inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Hotbar
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return slots.get(index).getItem();
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return true;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }
}
