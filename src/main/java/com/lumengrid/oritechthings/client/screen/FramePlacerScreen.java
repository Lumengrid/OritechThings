package com.lumengrid.oritechthings.client.screen;

import com.lumengrid.oritechthings.main.OritechThings;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import com.lumengrid.oritechthings.menu.FramePlacerMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FramePlacerScreen extends AbstractContainerScreen<FramePlacerMenu> {
    public static ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, "textures/gui/frame_placer.png");

    public FramePlacerScreen(FramePlacerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        gui.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0XFFFFFF);
    }

    @Override
    protected void init() {
        super.init();
        int x = this.leftPos;
        int y = this.topPos;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(
                BACKGROUND,
                x,
                y,
                0,
                0,
                176,
                166,
                256,
                256
        );
    }
//
//    @Override
//    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//
//    }
//
//    @Override
//    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
//        super.renderLabels(matrixStack, mouseX, mouseY);
//        drawString(matrixStack, this.font, "Frame Placement Settings", 8, 6);
//        drawString(matrixStack, this.font, "Number 1: " + menu.getNumber1(), 70, 20);
//        drawString(matrixStack, this.font, "Number 2: " + menu.getNumber2(), 70, 50);
//    }
}
