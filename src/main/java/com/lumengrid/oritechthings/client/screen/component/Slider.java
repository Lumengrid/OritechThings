package com.lumengrid.oritechthings.client.screen.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;
import java.awt.*;
import java.util.function.Consumer;

public class Slider extends ExtendedSlider {
    private static final int BACKGROUND = createAlphaColor(Color.DARK_GRAY, 200).getRGB();
    private static final int SLIDER_BACKGROUND = createAlphaColor(Color.DARK_GRAY.darker(), 200).getRGB();
    private static final int SLIDER_COLOR = createAlphaColor(Color.DARK_GRAY.brighter().brighter(), 200).getRGB();

    public final Consumer<Slider> onUpdate;

    public Slider(int x, int y, int width, int height, double min, double max, Component prefix, double current, Consumer<Slider> onUpdate) {
        super(x, y, width, height, prefix, Component.empty(), min, max, current, 1D, 1, true);
        this.onUpdate = onUpdate;
    }

    public void setMax(int max) {
        this.maxValue = max;
        if (this.value > max) {
            this.setValue(max);
        }
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, BACKGROUND);
        this.drawBorderedRect(guiGraphics, (this.getX() + (int) (this.value * (double) (this.width - 8)) + 4) - 4, this.getY(), 8, this.height);
        this.renderText(guiGraphics);
    }

    private void renderText(GuiGraphics guiGraphics) {
        int color = !active ? 10526880 : (isHovered ? 16777120 : -1);

        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.drawCenteredString(minecraft.font, this.prefix.copy().append(this.getValueString()), getX() + getWidth() / 2, getY() + (getHeight() - 8) / 2, color);
    }

    private void drawBorderedRect(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        guiGraphics.fill(x, y, x + width, y + height, SLIDER_BACKGROUND);
        guiGraphics.fill(++x, ++y, x + width - 2, y + height - 2, SLIDER_COLOR);
    }

    @Override
    protected void applyValue() {
        this.onUpdate.accept(this);
    }

    @Override
    public void onRelease(double p_93609_, double p_93610_) {
    }

    @Override
    public void playDownSound(SoundManager p_93605_) {
    }

    @Override
    public boolean mouseReleased(double p_93684_, double p_93685_, int p_93686_) {
        return super.mouseReleased(p_93684_, p_93685_, p_93686_);
    }

    private static Color createAlphaColor(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}
