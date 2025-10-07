package com.lumengrid.oritechthings.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rearth.oritech.block.blocks.accelerator.AcceleratorMotorBlock;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;

@Mixin(AcceleratorMotorBlock.class)
public class AcceleratorMotorBlockMixin {

    @Inject(method = "onRemove", at = @At("HEAD"))
    private void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
        if (world.isClientSide) return;

        var addonPos = pos.offset(Direction.DOWN.getNormal());
        var addonState = world.getBlockState(addonPos);
        
        if (addonState.getBlock() instanceof MachineAddonBlock) {
            var newAddonState = addonState.setValue(MachineAddonBlock.ADDON_USED, false);
            world.setBlockAndUpdate(addonPos, newAddonState);
            world.updateNeighborsAt(addonPos, addonState.getBlock());
        }
    }
}
