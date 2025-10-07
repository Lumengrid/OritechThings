package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.util.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;
import rearth.oritech.block.blocks.accelerator.AcceleratorMotorBlock;
import rearth.oritech.init.BlockContent;

@Mixin(MachineAddonBlock.class)
public class MachineAddonBlockMixin {

    @Inject(method = "setPlacedBy", at = @At("TAIL"))
    private void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if (world.isClientSide) return;

        var motorPos = pos.offset(Direction.UP.getNormal());
        var motorState = world.getBlockState(motorPos);
        
        if (motorState.getBlock() instanceof AcceleratorMotorBlock) {
            if (isCompatibleAddon(state)) {
                var newState = state.setValue(MachineAddonBlock.ADDON_USED, true);
                world.setBlockAndUpdate(pos, newState);
                world.updateNeighborsAt(pos, state.getBlock());
            }
        }
    }
    
    private boolean isCompatibleAddon(BlockState addonState) {
        var addonBlock = addonState.getBlock();

        if (addonBlock instanceof TierAddonBlock) {
            var addonType = addonState.getValue(TierAddonBlock.ADDON_TYPE);
            return addonType == Constants.AddonType.SPEED || 
                   addonType == Constants.AddonType.EFFICIENCY || 
                   addonType == Constants.AddonType.EFFICIENT_SPEED;
        }

        if (addonBlock instanceof MachineAddonBlock) {
            return addonBlock.equals(BlockContent.MACHINE_SPEED_ADDON) ||
                   addonBlock.equals(BlockContent.MACHINE_EFFICIENCY_ADDON) ||
                   addonBlock.equals(BlockContent.MACHINE_ULTIMATE_ADDON);
        }
        
        return false;
    }
}
