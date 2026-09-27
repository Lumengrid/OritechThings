package com.lumengrid.oritechthings.client.screen.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class CustomButton extends Button {

    private final int backgroundColor;
    private final int textColor;
    private final int borderColor;

    public CustomButton(
            int x,
            int y,
            int width,
            int height,
            Component message,
            OnPress onPress,
            int backgroundColor,
            int textColor,
            int borderColor
    ) {
        super(
                x,
                y,
                width,
                height,
                message,
                onPress,
                Button.DEFAULT_NARRATION
        );

        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.borderColor = borderColor;
    }

    @Override
    public void extractContents(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        int x = getX();
        int y = getY();

        graphics.fill(
                x - 1,
                y - 1,
                x + width + 1,
                y,
                borderColor
        );

        graphics.fill(
                x - 1,
                y,
                x,
                y + height,
                borderColor
        );

        graphics.fill(
                x + width,
                y,
                x + width + 1,
                y + height,
                borderColor
        );

        graphics.fill(
                x - 1,
                y + height,
                x + width + 1,
                y + height + 1,
                borderColor
        );

        graphics.fill(
                x,
                y,
                x + width,
                y + height,
                backgroundColor
        );

        Minecraft minecraft = Minecraft.getInstance();

        Component message = getMessage();
        int textX = x + (width - minecraft.font.width(message)) / 2;
        int textY = y + (height - 8) / 2;

        graphics.text(
                minecraft.font,
                message,
                textX,
                textY,
                opaque(textColor),
                false
        );
    }

    private static int opaque(int color) {
        return (color & 0xFF000000) == 0
                ? color | 0xFF000000
                : color;
    }
}
