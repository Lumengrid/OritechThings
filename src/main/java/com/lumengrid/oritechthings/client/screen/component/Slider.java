package com.lumengrid.oritechthings.client.screen.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;

import java.awt.Color;
import java.util.function.Consumer;

public class Slider extends ExtendedSlider {

    private static final int BACKGROUND =
            createAlphaColor(Color.DARK_GRAY, 200).getRGB();

    private static final int SLIDER_BACKGROUND =
            createAlphaColor(Color.DARK_GRAY.darker(), 200).getRGB();

    private static final int SLIDER_COLOR =
            createAlphaColor(
                    Color.DARK_GRAY.brighter().brighter(),
                    200
            ).getRGB();

    public final Consumer<Slider> onUpdate;

    public Slider(
            int x,
            int y,
            int width,
            int height,
            double min,
            double max,
            Component prefix,
            double current,
            Consumer<Slider> onUpdate
    ) {
        super(
                x,
                y,
                width,
                height,
                prefix,
                Component.empty(),
                min,
                max,
                current,
                1D,
                1,
                true
        );

        this.onUpdate = onUpdate;
    }

    public void setMax(int max) {
        this.maxValue = max;

        if (this.value > max) {
            this.setValue(max);
        }
    }

    @Override
    public void extractWidgetRenderState(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        int x = getX();
        int y = getY();

        graphics.fill(
                x,
                y,
                x + width,
                y + height,
                BACKGROUND
        );

        int sliderX =
                x + (int) (value * (double) (width - 8)) + 4;

        drawBorderedRect(
                graphics,
                sliderX - 4,
                y,
                8,
                height
        );

        renderText(graphics);
    }

    private void renderText(GuiGraphicsExtractor graphics) {
        int color = !active
                ? 0xFFA0A0A0
                : isHovered
                ? 0xFFFFFF20
                : 0xFFFFFFFF;

        Minecraft minecraft = Minecraft.getInstance();

        Component text = prefix.copy().append(getValueString());
        int textX = getX() + (getWidth() - minecraft.font.width(text)) / 2;
        int textY = getY() + (getHeight() - 8) / 2;

        graphics.text(
                minecraft.font,
                text,
                textX,
                textY,
                color,
                false
        );
    }

    private void drawBorderedRect(
            GuiGraphicsExtractor graphics,
            int x,
            int y,
            int width,
            int height
    ) {
        graphics.fill(
                x,
                y,
                x + width,
                y + height,
                SLIDER_BACKGROUND
        );

        graphics.fill(
                x + 1,
                y + 1,
                x + width - 1,
                y + height - 1,
                SLIDER_COLOR
        );
    }

    @Override
    protected void applyValue() {
        onUpdate.accept(this);
    }

    private static Color createAlphaColor(
            Color color,
            int alpha
    ) {
        return new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                alpha
        );
    }
}
