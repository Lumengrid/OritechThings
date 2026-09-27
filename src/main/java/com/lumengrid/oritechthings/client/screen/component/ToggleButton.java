package com.lumengrid.oritechthings.client.screen.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class ToggleButton extends Button {

    private boolean enabled;

    private int enableColor;
    private int disableColor;

    public ToggleButton(
            int x,
            int y,
            int width,
            int height,
            Component message,
            OnPress onPress,
            boolean initialState,
            int enableColor,
            int disableColor
    ) {
        this(
                new TogglePressHandler(onPress),
                x,
                y,
                width,
                height,
                message,
                initialState,
                enableColor,
                disableColor
        );
    }

    private ToggleButton(
            TogglePressHandler pressHandler,
            int x,
            int y,
            int width,
            int height,
            Component message,
            boolean initialState,
            int enableColor,
            int disableColor
    ) {
        super(
                x,
                y,
                width,
                height,
                message,
                pressHandler,
                Button.DEFAULT_NARRATION
        );

        this.enabled = initialState;
        this.enableColor = enableColor;
        this.disableColor = disableColor;

        pressHandler.owner = this;
    }

    public void setColors(
            int enableColor,
            int disableColor
    ) {
        this.enableColor = enableColor;
        this.disableColor = disableColor;
    }

    public void setToggleState(boolean state) {
        this.enabled = state;
    }

    public boolean isToggleEnabled() {
        return enabled;
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
        int borderColor = 0xFF000000;

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
                enabled ? enableColor : disableColor
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
                0xFFFFFFFF,
                false
        );
    }

    private static final class TogglePressHandler implements OnPress {

        private final OnPress delegate;
        private ToggleButton owner;

        private TogglePressHandler(OnPress delegate) {
            this.delegate = delegate;
        }

        @Override
        public void onPress(Button button) {
            if (owner != null) {
                owner.enabled = !owner.enabled;
            }

            if (delegate != null) {
                delegate.onPress(button);
            }
        }
    }
}
