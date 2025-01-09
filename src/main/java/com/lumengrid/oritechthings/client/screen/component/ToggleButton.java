package com.lumengrid.oritechthings.client.screen.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ToggleButton extends Button {
    private boolean isEnabled;
    private final int ENABLE_COLOR;
    private final int DISABLE_COLOR;

    public ToggleButton(int x, int y, int width, int height, Component message, OnPress onPress, boolean initialState, int enableColor, int disableColor) {
        super(x, y, width, height, message, onPress, Button.DEFAULT_NARRATION);
        ENABLE_COLOR = enableColor;
        DISABLE_COLOR = disableColor;
        this.isEnabled = initialState;
    }
    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Draw black border
        int borderColor = 0xFF000000; // Black color
        guiGraphics.fill(this.getX() - 1, this.getY() - 1, this.getX() + this.width + 1, this.getY(), borderColor); // Top border
        guiGraphics.fill(this.getX() - 1, this.getY(), this.getX(), this.getY() + this.height, borderColor); // Left border
        guiGraphics.fill(this.getX() + this.width, this.getY(), this.getX() + this.width + 1, this.getY() + this.height, borderColor); // Right border
        guiGraphics.fill(this.getX() - 1, this.getY() + this.height, this.getX() + this.width + 1, this.getY() + this.height + 1, borderColor); // Bottom border

        // Draw button background
        int backgroundColor = this.isEnabled ? ENABLE_COLOR : DISABLE_COLOR;
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, backgroundColor);

        // Draw button text
        Minecraft mc = Minecraft.getInstance();
        guiGraphics.drawCenteredString(mc.font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, 0xFFFFFF);
    }

    @Override
    public void onPress() {
        super.onPress();
        this.isEnabled = !this.isEnabled; // Toggle the state
    }
}
