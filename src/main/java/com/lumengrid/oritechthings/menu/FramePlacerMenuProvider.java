package com.lumengrid.oritechthings.menu;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FramePlacerMenuProvider implements MenuProvider {

    private final BlockPos pos;

    public FramePlacerMenuProvider(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("item.oritechthings.frame_placer");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeBlockPos(pos);
        return new FramePlacerMenu(containerId, playerInventory, buf);
    }
}
