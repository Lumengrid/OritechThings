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

    @Inject(
            method = "onRemove",
            at = @At("HEAD")
    )
    private void onRemove(
            BlockState state,
            Level level,
            BlockPos pos,
            BlockState newState,
            boolean moved,
            CallbackInfo callbackInfo
    ) {
        if (level.isClientSide()) {
            return;
        }

        BlockPos addonPos =
                pos.relative(Direction.DOWN);

        BlockState addonState =
                level.getBlockState(addonPos);

        if (!(addonState.getBlock()
                instanceof MachineAddonBlock)) {
            return;
        }

        BlockState newAddonState =
                addonState.setValue(
                        MachineAddonBlock.ADDON_USED,
                        false
                );

        level.setBlockAndUpdate(addonPos, newAddonState);
        level.updateNeighborsAt(
                addonPos,
                newAddonState.getBlock()
        );
    }
}
