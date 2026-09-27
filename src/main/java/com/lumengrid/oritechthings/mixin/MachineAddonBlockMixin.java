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
import rearth.oritech.block.blocks.accelerator.AcceleratorMotorBlock;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;
import rearth.oritech.init.BlockContent;

@Mixin(MachineAddonBlock.class)
public class MachineAddonBlockMixin {

    @Inject(
            method = "setPlacedBy",
            at = @At("TAIL")
    )
    private void setPlacedBy(
            Level level,
            BlockPos pos,
            BlockState state,
            LivingEntity placer,
            ItemStack itemStack,
            CallbackInfo callbackInfo
    ) {
        if (level.isClientSide()) {
            return;
        }

        BlockPos motorPos =
                pos.relative(Direction.UP);

        BlockState motorState =
                level.getBlockState(motorPos);

        if (!(motorState.getBlock()
                instanceof AcceleratorMotorBlock)) {
            return;
        }

        if (!isCompatibleAddon(state)) {
            return;
        }

        BlockState newState =
                state.setValue(
                        MachineAddonBlock.ADDON_USED,
                        true
                );

        level.setBlockAndUpdate(pos, newState);
        level.updateNeighborsAt(
                pos,
                newState.getBlock()
        );
    }

    private boolean isCompatibleAddon(BlockState addonState) {
        var addonBlock = addonState.getBlock();

        if (addonBlock instanceof TierAddonBlock) {
            var addonType =
                    addonState.getValue(TierAddonBlock.ADDON_TYPE);

            return addonType == Constants.AddonType.SPEED
                    || addonType == Constants.AddonType.EFFICIENCY
                    || addonType == Constants.AddonType.EFFICIENT_SPEED;
        }

        if (addonBlock instanceof MachineAddonBlock) {
            return addonBlock.equals(
                    BlockContent.MACHINE_SPEED_ADDON
            )
                    || addonBlock.equals(
                    BlockContent.MACHINE_EFFICIENCY_ADDON
            );
            // todo removed machine ultimate addon
                    //|| addonBlock.equals(
                    //BlockContent.MACHINE_ULTIMATE_ADDON
            //);
        }

        return false;
    }
}
