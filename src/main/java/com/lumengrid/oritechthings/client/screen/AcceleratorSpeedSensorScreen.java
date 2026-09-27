package com.lumengrid.oritechthings.client.screen;

import com.lumengrid.oritechthings.client.screen.component.ToggleButton;
import com.lumengrid.oritechthings.main.OritechThings;
import com.lumengrid.oritechthings.menu.AcceleratorSpeedSensorMenu;
import com.lumengrid.oritechthings.network.packet.UpdateSpeedSensorC2SPacket;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

@SuppressWarnings("null")
public class AcceleratorSpeedSensorScreen extends AbstractContainerScreen<AcceleratorSpeedSensorMenu> {
        private final Component title = Component
                .translatable("gui.oritechthings.particle_accelerator_speed_sensor.title");

        public static final Identifier BACKGROUND = Identifier.fromNamespaceAndPath(OritechThings.MOD_ID,
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

                // MODE TOGGLE
                modeButton = new ToggleButton(
                        leftPos + 8, topPos + 20, 40, 18,
                        menu.be.isAutomaticMode()
                                ? Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.auto")
                                : Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.manual"),
                        button -> {
                                boolean newState = !menu.be.isAutomaticMode();
                                menu.be.setAutomaticMode(newState);

                                ClientPacketDistributor.sendToServer(new UpdateSpeedSensorC2SPacket(
                                        menu.be.getBlockPos(),
                                        menu.be.getSpeedLimit(), menu.be.isEnabled(), menu.be.isCheckGreater(), newState));

                                button.setMessage(newState
                                        ? Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.auto")
                                        : Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.manual"));

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

                // ON/OFF TOGGLE
                onOffButton = new ToggleButton(
                        leftPos + 135, topPos + 20, 28, 18,
                        menu.be.isEnabled()
                                ? Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.on")
                                : Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.off"),
                        button -> {
                                boolean newState = !menu.be.isEnabled();
                                menu.be.setEnabled(newState);

                                ClientPacketDistributor.sendToServer(new UpdateSpeedSensorC2SPacket(
                                        menu.be.getBlockPos(),
                                        menu.be.getSpeedLimit(), newState, menu.be.isCheckGreater(), menu.be.isAutomaticMode()));

                                button.setMessage(newState
                                        ? Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.on")
                                        : Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.off"));

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

                // < > ARROW TOGGLE
                arrowToggle = new ToggleButton(
                        leftPos + 8, topPos + 50, 17, 17,
                        Component.literal(menu.be.isCheckGreater() ? ">" : "<"),
                        button -> {
                                boolean newState = !menu.be.isCheckGreater();
                                ClientPacketDistributor.sendToServer(new UpdateSpeedSensorC2SPacket(
                                        menu.be.getBlockPos(),
                                        menu.be.getSpeedLimit(), menu.be.isEnabled(), newState, menu.be.isAutomaticMode()));
                                button.setMessage(Component.literal(newState ? ">" : "<"));
                        },
                        menu.be.isEnabled(),
                        0xFF000000,
                        0xFF000000);
                addRenderableWidget(arrowToggle);

                // SPEED INPUT
                speedInput = new EditBox(this.font, this.leftPos + 28, this.topPos + 50,
                        60, 18, Component.translatable(
                        "gui.oritechthings.particle_accelerator_speed_sensor.speed_input"));
                speedInput.setMaxLength(6);
                speedInput.setFilter(s -> s.matches("\\d*"));
                speedInput.setValue(String.valueOf(menu.be.getSpeedLimit()));
                speedInput.setResponder(this::onSpeedEntered);
                addRenderableWidget(speedInput);

                updateTooltips();
                updateManualControlsVisibility();
        }

        private void updateTooltips() {
                if (modeButton != null) {
                        Component modeTooltip = menu.be.isAutomaticMode()
                                ? Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.auto.tooltip")
                                : Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.manual.tooltip");
                        modeButton.setTooltip(Tooltip.create(modeTooltip));
                }

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
                        int value = input.isEmpty() ? 0 : Integer.parseInt(input);
                        value = Math.max(0, Math.min(value, 999999));
                        ClientPacketDistributor.sendToServer(new UpdateSpeedSensorC2SPacket(menu.be.getBlockPos(), value,
                                menu.be.isEnabled(), menu.be.isCheckGreater(), menu.be.isAutomaticMode()));
                } catch (NumberFormatException e) {
                        ClientPacketDistributor.sendToServer(new UpdateSpeedSensorC2SPacket(menu.be.getBlockPos(), 0,
                                menu.be.isEnabled(), menu.be.isCheckGreater(), menu.be.isAutomaticMode()));
                }
        }

        @Override
        public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
                int x = (width - imageWidth) / 2;
                int y = (height - imageHeight) / 2;

                graphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        BACKGROUND,
                        x,
                        y,
                        0,
                        0,
                        176,
                        166,
                        256,
                        256);

                super.extractContents(graphics, mouseX, mouseY, partialTick);

                renderParticleAcceleratorMessage(graphics);
        }

        private void renderParticleAcceleratorMessage(GuiGraphicsExtractor graphics) {
                boolean isLinked = menu.be.getTargetDesignator() != null;
                Component statusText;

                if (isLinked) {
                        BlockPos target = menu.be.getTargetDesignator();
                        statusText = Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.linked")
                                .append(" " + target.toShortString());
                } else {
                        statusText = Component.translatable("gui.oritechthings.particle_accelerator_speed_sensor.not_linked");
                }

                int statusColor = isLinked ? 0x55FF55 : 0xFF5555;

                int statusX = leftPos + 8;
                int statusY = topPos + 72;

                graphics.text(this.font, statusText, statusX, statusY, statusColor, false);
        }

        @Override
        protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
                graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xFFFFFF, false);
        }
}