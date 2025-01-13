package com.lumengrid.oritechthings.client.screen.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class CustomButton extends Button {
    private final int BACKGROUND_COLOR;
    private final int TEXT_COLOR;
    private final int BORDER_COLOR;

    public CustomButton(int x, int y, int width, int height, Component message, OnPress onPress, int backGroundColor, int textColor, int borderColor) {
        super(x, y, width, height, message, onPress, Button.DEFAULT_NARRATION);
        BACKGROUND_COLOR = backGroundColor;
        TEXT_COLOR = textColor;
        BORDER_COLOR = borderColor;
    }
    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Draw black border
        guiGraphics.fill(this.getX() - 1, this.getY() - 1, this.getX() + this.width + 1, this.getY(), BORDER_COLOR); // Top border
        guiGraphics.fill(this.getX() - 1, this.getY(), this.getX(), this.getY() + this.height, BORDER_COLOR); // Left border
        guiGraphics.fill(this.getX() + this.width, this.getY(), this.getX() + this.width + 1, this.getY() + this.height, BORDER_COLOR); // Right border
        guiGraphics.fill(this.getX() - 1, this.getY() + this.height, this.getX() + this.width + 1, this.getY() + this.height + 1, BORDER_COLOR); // Bottom border

        // Draw button background
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, BACKGROUND_COLOR);

        // Draw button text
        Minecraft mc = Minecraft.getInstance();
        guiGraphics.drawCenteredString(mc.font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, TEXT_COLOR);
    }
}
