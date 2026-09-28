package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.util.Utility;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rearth.oritech.block.blocks.accelerator.AcceleratorMotorBlock;

import java.util.function.Consumer;

@Mixin(AcceleratorMotorBlock.class)
public class AcceleratorMotorTooltipMixin {

    @Inject(
            method = "addToTooltip",
            at = @At("TAIL")
    )
    private void addToTooltip(
            Item.TooltipContext tooltipContext,
            Consumer<Component> consumer,
            TooltipFlag tooltipFlag,
            DataComponentGetter dataComponentGetter,
            CallbackInfo callbackInfo
    ) {
        if (!Utility.isControlDown()) {
            return;
        }

        consumer.accept(Component.empty());

        consumer.accept(
                Component.translatable(
                        "tooltip.oritechthings.accelerator_motor.addon_info"
                )
        );

        consumer.accept(
                Component.translatable(
                        "tooltip.oritechthings.accelerator_motor.addon_placement"
                )
        );
    }
}