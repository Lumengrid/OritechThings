package com.lumengrid.oritechthings.client.screen;

import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.menu.AcceleratorSpeedSensorMenu;
import com.lumengrid.oritechthings.network.packet.UpdateSpeedSensorC2SPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

import java.text.DecimalFormat;
import java.util.List;

public class AcceleratorSpeedSensorScreen extends AbstractContainerScreen<AcceleratorSpeedSensorMenu> {

    public static ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID, "textures/gui/speed_sensor.png");

    public AcceleratorSpeedSensorScreen(AcceleratorSpeedSensorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    private static final List<SpeedButton> BUTTONS = List.of(
        new SpeedButton(25, 34, -10f, -500f),
        new SpeedButton(61, 34, -5f, -100f),
        new SpeedButton(97, 34, 5f, 100f),
        new SpeedButton(133, 34, 10, 500f)
    );

    @Override
    protected void init() {
        super.init();

        inventoryLabelY = 10000;
        titleLabelY = 10000;

        for (SpeedButton but : BUTTONS) {
            addWidget(
                Button.builder(Component.empty(), (b) -> {
                        PacketDistributor.sendToServer(
                            new UpdateSpeedSensorC2SPacket(
                                menu.be.getBlockPos(),
                                Screen.hasShiftDown() ? but.max : but.min,
                                menu.be.isEnabled()
                            )
                        );
                    }).pos(leftPos + but.x, topPos + but.y)
                    .size(18, 18)
                    .build()
            );
        }

        addWidget(
            Button.builder(
                Component.empty(),
                (b) -> PacketDistributor.sendToServer(
                    new UpdateSpeedSensorC2SPacket(
                        menu.be.getBlockPos(),
                        0,
                        !menu.be.isEnabled()
                    )
                )
            ).pos(leftPos + 150, topPos + 64)
                .size(18, 18)
                .build()
        );
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
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

        // Speed Buttons
        for (SpeedButton but : BUTTONS) {
            guiGraphics.blit(
                BACKGROUND,
                leftPos + but.x,
                topPos + but.y,
                but.max <= 0? 176: 194,
                0,
                18,
                18,
                256,
                256
            );
        }

        // Speed Amount
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(0);

        String text = df.format(menu.be.getSpeedLimit());

        guiGraphics.drawCenteredString(
            font,
            text,
            leftPos + 86,
            topPos + 66,
            0xFF555555
        );

        //On - Off Button
        guiGraphics.blit(
            BACKGROUND,
            leftPos + 151,
            topPos + 64,
            menu.be.isEnabled()? 176: 194,
            18,
            18,
            18,
            256,
            256
        );
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(0);

        for (SpeedButton but : BUTTONS) {
            if (!isMouseHovering(leftPos + but.x, topPos + but.y, 18, 18, x, y)) {
                continue;
            }
            
            String text = df.format(Screen.hasShiftDown() ? but.max : but.min);
            guiGraphics.renderTooltip(
                font,
                Component.literal(text),
                x,
                y
            );
        }
    }

    public static boolean isMouseHovering(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public record SpeedButton(int x, int y, float min, float max){}
}
