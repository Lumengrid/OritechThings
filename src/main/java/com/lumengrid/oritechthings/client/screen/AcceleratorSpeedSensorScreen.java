package com.lumengrid.oritechthings.client.screen;

import com.lumengrid.oritechthings.client.screen.component.ToggleButton;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.menu.AcceleratorSpeedSensorMenu;
import com.lumengrid.oritechthings.network.packet.UpdateSpeedSensorC2SPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

@SuppressWarnings("null")
public class AcceleratorSpeedSensorScreen extends AbstractContainerScreen<AcceleratorSpeedSensorMenu> {
        private final Component title = Component
                        .translatable("gui.oritechthings.particle_accelerator_speed_sensor.title");
        public static ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID,
                        "textures/gui/speed_sensor.png");
        
        private EditBox speedInput;
        private ToggleButton arrowToggle;
        private ToggleButton onOffButton;
        private ToggleButton modeButton;

        public AcceleratorSpeedSensorScreen(AcceleratorSpeedSensorMenu pMenu, Inventory pPlayerInventory,
                        Component pTitle) {
                super(pMenu, pPlayerInventory, pTitle);
        }

        @Override
        protected void init() {
                super.init();
                inventoryLabelY = 10000;

                // MODE TOGGLE (Auto/Manual) - Position: Top Left
                modeButton = new ToggleButton(
                                leftPos + 8, topPos + 20, 40, 18,
                                menu.be.isAutomaticMode()
                                                ? Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.auto")
                                                : Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.manual"),
                                button -> {
                                        boolean newState = !menu.be.isAutomaticMode();
                                        // Update client-side state immediately
                                        menu.be.setAutomaticMode(newState);
                                        // Send to server
                                        PacketDistributor.sendToServer(new UpdateSpeedSensorC2SPacket(
                                                        menu.be.getBlockPos(),
                                                        menu.be.getSpeedLimit(), menu.be.isEnabled(), menu.be.isCheckGreater(), newState));
                                        // Update button appearance
                                        button.setMessage(newState
                                                        ? Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.auto")
                                                        : Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.manual"));
                                        // Update button state and colors
                                        if (button instanceof ToggleButton toggleBtn) {
                                                toggleBtn.setToggleState(newState);
                                                toggleBtn.setColors(newState ? 0xFF4a86e8 : 0xFF999999, newState ? 0xFF4a86e8 : 0xFF999999);
                                        }
                                        updateTooltips();
                                        updateManualControlsVisibility();
                                },
                                menu.be.isEnabled(),
                                menu.be.isAutomaticMode() ? 0xFF4a86e8 : 0xFF999999,
                                menu.be.isAutomaticMode() ? 0xFF4a86e8 : 0xFF999999);
                addRenderableWidget(modeButton);

                // ON/OFF TOGGLE - Position: Top Right
                onOffButton = new ToggleButton(
                                leftPos + 135, topPos + 20, 28, 18,
                                menu.be.isEnabled()
                                                ? Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.on")
                                                : Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.off"),
                                button -> {
                                        boolean newState = !menu.be.isEnabled();
                                        // Update client-side state immediately
                                        menu.be.setEnabled(newState);
                                        // Send to server
                                        PacketDistributor.sendToServer(new UpdateSpeedSensorC2SPacket(
                                                        menu.be.getBlockPos(),
                                                        menu.be.getSpeedLimit(), newState, menu.be.isCheckGreater(), menu.be.isAutomaticMode()));
                                        // Update button appearance
                                        button.setMessage(newState
                                                        ? Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.on")
                                                        : Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.off"));
                                        // Update button state and colors
                                        if (button instanceof ToggleButton toggleBtn) {
                                                toggleBtn.setToggleState(newState);
                                                toggleBtn.setColors(newState ? 0xFF55FF55 : 0xFFFF5555, newState ? 0xFF55FF55 : 0xFFFF5555);
                                        }
                                        updateTooltips();
                                },
                                menu.be.isEnabled(),
                                menu.be.isEnabled() ? 0xFF55FF55 : 0xFFFF5555,
                                menu.be.isEnabled() ? 0xFF55FF55 : 0xFFFF5555);
                addRenderableWidget(onOffButton);

                // < > ARROW TOGGLE (only visible in manual mode)
                arrowToggle = new ToggleButton(
                                leftPos + 8, topPos + 50, 17, 17,
                                Component.literal(menu.be.isCheckGreater() ? ">" : "<"),
                                button -> {
                                        boolean newState = !menu.be.isCheckGreater();
                                        PacketDistributor.sendToServer(new UpdateSpeedSensorC2SPacket(
                                                        menu.be.getBlockPos(),
                                                        menu.be.getSpeedLimit(), menu.be.isEnabled(), newState, menu.be.isAutomaticMode()));
                                        button.setMessage(Component.literal(newState ? ">" : "<"));
                                },
                                menu.be.isEnabled(),
                                0xFF000000,
                                0xFF000000);
                addRenderableWidget(arrowToggle);

                // SPEED INPUT (only visible in manual mode)
                speedInput = new EditBox(this.font, this.leftPos + 28, this.topPos + 50,
                                60, 18, Component.translatable(
                                                "gui.oritechthings.particle_accelerator_speed_sensor.speed_input"));
                speedInput.setMaxLength(6);
                speedInput.setFilter(s -> s.matches("\\d*")); // Allow empty string now
                speedInput.setValue(String.valueOf(menu.be.getSpeedLimit()));
                speedInput.setResponder(this::onSpeedEntered);
                addRenderableWidget(speedInput);

                // Initialize tooltips and visibility
                updateTooltips();
                updateManualControlsVisibility();
        }

        private void updateTooltips() {
                // Mode button tooltip
                if (modeButton != null) {
                        Component modeTooltip = menu.be.isAutomaticMode()
                                        ? Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.auto.tooltip")
                                        : Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.manual.tooltip");
                        modeButton.setTooltip(Tooltip.create(modeTooltip));
                }

                // On/Off button tooltip
                if (onOffButton != null) {
                        Component onOffTooltip = menu.be.isEnabled()
                                        ? Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.on.tooltip")
                                        : Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.off.tooltip");
                        onOffButton.setTooltip(Tooltip.create(onOffTooltip));
                }
        }

        private void updateManualControlsVisibility() {
                boolean isAutoMode = menu.be.isAutomaticMode();

                if (arrowToggle != null) {
                        arrowToggle.visible = !isAutoMode;
                        arrowToggle.active = !isAutoMode;
                }

                if (speedInput != null) {
                        speedInput.setVisible(!isAutoMode);
                        speedInput.setEditable(!isAutoMode);
                }
        }

        private void onSpeedEntered(String input) {
                try {
                        // Allow empty input, default to 0
                        int value = input.isEmpty() ? 0 : Integer.parseInt(input);
                        value = Math.max(0, Math.min(value, 999999));
                        PacketDistributor.sendToServer(new UpdateSpeedSensorC2SPacket(menu.be.getBlockPos(), value,
                                        menu.be.isEnabled(), menu.be.isCheckGreater(), menu.be.isAutomaticMode()));
                } catch (NumberFormatException e) {
                        // If parsing fails, default to 0
                        PacketDistributor.sendToServer(new UpdateSpeedSensorC2SPacket(menu.be.getBlockPos(), 0,
                                        menu.be.isEnabled(), menu.be.isCheckGreater(), menu.be.isAutomaticMode()));
                }
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
                                256);
                renderParticleAcceleratorMessage(guiGraphics);
        }

        private void renderParticleAcceleratorMessage(GuiGraphics guiGraphics) {
                // Link status with color
                boolean isLinked = menu.be.getTargetDesignator() != null;
                Component statusText;
                
                if (isLinked) {
                        BlockPos target = menu.be.getTargetDesignator();
                        // Show "Linked [x, y, z]"
                        statusText = Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.linked")
                                        .append(" " + target.toShortString());
                } else {
                        statusText = Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.not_linked");
                }
                
                int statusColor = isLinked ? 0x55FF55 : 0xFF5555; // Green for linked, red for not linked

                // Draw the status text below the arrow toggle and speed input
                int statusX = leftPos + 8;
                int statusY = topPos + 72;  // Below the controls at topPos + 50
                guiGraphics.drawString(this.font, statusText, statusX, statusY, statusColor);
        }

        @Override
        protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
                gui.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0XFFFFFF);
        }
}
