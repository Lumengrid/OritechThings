package com.lumengrid.oritechthings.mixin;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rearth.oritech.block.blocks.accelerator.AcceleratorMotorBlock;

import java.util.List;

@Mixin(AcceleratorMotorBlock.class)
public class AcceleratorMotorTooltipMixin {

    @Inject(method = "appendHoverText", at = @At("TAIL"))
    private void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options, CallbackInfo ci) {
        if (Screen.hasControlDown()) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_motor.addon_info"));
            tooltip.add(Component.translatable("tooltip.oritechthings.accelerator_motor.addon_placement"));
        }
    }
}
