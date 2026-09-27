package com.lumengrid.oritechthings.client.screen;

import com.lumengrid.oritechthings.client.screen.component.CustomButton;
import com.lumengrid.oritechthings.client.screen.component.Slider;
import com.lumengrid.oritechthings.network.packet.FramePlacerPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

import static com.lumengrid.oritechthings.main.OritechThings.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class FramePlacerScreen extends Screen {
    private final BlockPos startPos;
    private final Direction facing;
    private Slider xSlider, ySlider, offsetSlider;
    private CustomButton abortButton, confirmButton;
    private CustomButton xDecrementButton, xIncrementButton;
    private CustomButton yDecrementButton, yIncrementButton;
    private CustomButton offsetDecrementButton, offsetIncrementButton;
    private int xValue = 5, yValue = 5, offsetValue = 2;
    private int frameCountRequired;

    public FramePlacerScreen(BlockPos pos, Direction facing) {
        super(Component.translatable("item.oritechthings.frame_placer"));
        this.startPos = pos;
        this.facing = facing;
    }

    @Override
    protected void init() {
        int widthSlider = 124;
        this.xSlider = new Slider(width / 2 - widthSlider / 2, height / 2 - 40, widthSlider, 14, 3,
                64, Component.literal("X "), xValue, slider -> {
            this.xValue = slider.getValueInt();
            updateOffsetSlider();
            updateFrameCount();
        });
        this.xDecrementButton = new CustomButton(this.xSlider.getX() - 20, this.xSlider.getY(), 20, 14,
                Component.literal("-"), button -> decrementX(), Color.GRAY.getRGB(), Color.WHITE.getRGB(), Color.BLACK.getRGB());
        this.xIncrementButton = new CustomButton(this.xSlider.getX() + widthSlider + 2, this.xSlider.getY(), 20, 14,
                Component.literal("+"), button -> incrementX(), Color.GRAY.getRGB(), Color.WHITE.getRGB(), Color.BLACK.getRGB());

        this.ySlider = new Slider(width / 2 - widthSlider / 2, height / 2 - 20, widthSlider, 14, 3,
                64, Component.literal("Y "), yValue, slider -> {
            this.yValue = slider.getValueInt();
            updateFrameCount();
        });
        this.yDecrementButton = new CustomButton(this.ySlider.getX() - 20, this.ySlider.getY(), 20, 14,
                Component.literal("-"), button -> decrementY(), Color.GRAY.getRGB(), Color.WHITE.getRGB(), Color.BLACK.getRGB());
        this.yIncrementButton = new CustomButton(this.ySlider.getX() + widthSlider + 2, this.ySlider.getY(), 20, 14,
                Component.literal("+"), button -> incrementY(), Color.GRAY.getRGB(), Color.WHITE.getRGB(), Color.BLACK.getRGB());

        this.offsetSlider = new Slider(width / 2 - widthSlider / 2, height / 2, widthSlider, 14, 1,
                63, Component.translatable("message." + MOD_ID + ".frame_placer.offset"), offsetValue, slider -> this.offsetValue = slider.getValueInt());
        this.offsetDecrementButton = new CustomButton(this.offsetSlider.getX() - 20, this.offsetSlider.getY(), 20, 14,
                Component.literal("-"), button -> decrementOffset(), Color.GRAY.getRGB(), Color.WHITE.getRGB(), Color.BLACK.getRGB());
        this.offsetIncrementButton = new CustomButton(this.offsetSlider.getX() + widthSlider + 2, this.offsetSlider.getY(), 20, 14,
                Component.literal("+"), button -> incrementOffset(), Color.GRAY.getRGB(), Color.WHITE.getRGB(), Color.BLACK.getRGB());

        this.abortButton = new CustomButton(this.width / 2 - 60, this.height / 2 + 40, 50, 20,
                Component.translatable("message." + MOD_ID + ".frame_placer.abort"), button -> abort(),
                Color.RED.getRGB(), Color.WHITE.getRGB(), Color.BLACK.getRGB());

        this.confirmButton = new CustomButton(this.width / 2 + 10, this.height / 2 + 40, 50, 20,
                Component.translatable("message." + MOD_ID + ".frame_placer.confirm"), button -> confirmDimensions(),
                Color.GREEN.getRGB(), Color.WHITE.getRGB(), Color.BLACK.getRGB());

        updateFrameCount();

        this.addRenderableWidget(xSlider);
        this.addRenderableWidget(ySlider);
        this.addRenderableWidget(offsetSlider);
        this.addRenderableWidget(xDecrementButton);
        this.addRenderableWidget(xIncrementButton);
        this.addRenderableWidget(yDecrementButton);
        this.addRenderableWidget(yIncrementButton);
        this.addRenderableWidget(offsetDecrementButton);
        this.addRenderableWidget(offsetIncrementButton);
        this.addRenderableWidget(abortButton);
        this.addRenderableWidget(confirmButton);
    }

    private void updateOffsetSlider() {
        int maxOffset = xValue - 1;
        offsetSlider.setMax(maxOffset);
        if (offsetValue > maxOffset) {
            offsetValue = maxOffset;
        }
    }

    private void decrementX() {
        if (xValue > 3) {
            xValue--;
            xSlider.setValue(xValue);
            updateOffsetSlider();
        }
    }

    private void incrementX() {
        if (xValue < 64) {
            xValue++;
            xSlider.setValue(xValue);
            updateOffsetSlider();
        }
    }

    private void decrementY() {
        if (yValue > 3) {
            yValue--;
            ySlider.setValue(yValue);
        }
    }

    private void incrementY() {
        if (yValue < 64) {
            yValue++;
            ySlider.setValue(yValue);
        }
    }

    private void decrementOffset() {
        if (offsetValue > 1) {
            offsetValue--;
            offsetSlider.setValue(offsetValue);
        }
    }

    private void incrementOffset() {
        int maxOffset = xValue - 1;
        offsetSlider.setMax(maxOffset);
        if (offsetValue < maxOffset) {
            offsetValue++;
            offsetSlider.setValue(offsetValue);
        }
    }

    private void updateFrameCount() {
        frameCountRequired = 2 * (xValue + yValue) - 4;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mx, int my, float partialTicks) {
        super.extractRenderState(graphics, mx, my, partialTicks);

        var font = Minecraft.getInstance().font;
        var sizeMsg = Component.translatable("message." + MOD_ID + ".frame_placer.frame_size");
        var reqMsg = Component.translatable("message." + MOD_ID + ".frame_placer.blocks_required").append(String.valueOf(frameCountRequired));

        int sizeX = this.width / 2 - font.width(sizeMsg) / 2;
        int reqX = this.width / 2 - font.width(reqMsg) / 2;

        graphics.text(font, sizeMsg, sizeX, this.height / 2 - 60, Color.GREEN.getRGB(), false);
        graphics.text(font, reqMsg, reqX, this.height / 2 - 80, Color.WHITE.getRGB(), false);
    }

    private void abort() {
        Minecraft.getInstance().setScreen(null);
    }

    private void confirmDimensions() {
        ClientPacketDistributor.sendToServer(new FramePlacerPacket(startPos, xValue, yValue, offsetValue, facing));
        Minecraft.getInstance().setScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}