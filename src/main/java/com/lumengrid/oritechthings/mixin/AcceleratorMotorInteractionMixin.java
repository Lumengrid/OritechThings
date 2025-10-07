package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.util.Constants;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;
import rearth.oritech.init.BlockContent;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rearth.oritech.Oritech;
import rearth.oritech.block.entity.accelerator.AcceleratorControllerBlockEntity;
import rearth.oritech.block.entity.accelerator.AcceleratorMotorBlockEntity;
import rearth.oritech.block.entity.accelerator.AcceleratorParticleLogic;

@Mixin(AcceleratorControllerBlockEntity.class)
public class AcceleratorMotorInteractionMixin {
    @Shadow
    private AcceleratorParticleLogic.ActiveParticle particle;

    @Inject(method = "handleParticleMotorInteraction", at = @At("HEAD"))
    private void handleParticleMotorInteraction(BlockPos motorBlock, CallbackInfo ci) {
        if (particle == null) return;

        AcceleratorControllerBlockEntity self = (AcceleratorControllerBlockEntity) (Object) this;
        assert self.getLevel() != null;
        var entity = self.getLevel().getBlockEntity(motorBlock);
        if (!(entity instanceof AcceleratorMotorBlockEntity motorEntity)) return;

        AddonStats addonStats = findMotorAddon(motorBlock, self);
        var storage = motorEntity.getEnergyStorage(null);
        var speed = particle.velocity;
        if (addonStats == null) {
            var availableEnergy = storage.getAmount();
            var cost = speed * Oritech.CONFIG.accelerationRFCost();
            if (availableEnergy >= cost) {
                storage.extract((long) cost, false);
                storage.update();
                particle.velocity += 1;
            }

            return;
        }

        var baseMotorCost = speed * Oritech.CONFIG.accelerationRFCost();
        float additionalVelocity = Math.max(Math.min(addonStats.speedBonus(), 10.0f), 0.0f);
        var additionalCost = 0.0;
        if (additionalVelocity > 0) {
            additionalCost = speed * Oritech.CONFIG.accelerationRFCost() * additionalVelocity;
        }

        var totalCostBeforeEfficiency = baseMotorCost + additionalCost;
        var totalCost = calculateEnergyCost(totalCostBeforeEfficiency, addonStats.energyCostMultiplier());
        var availableEnergy = storage.getAmount();
        if (availableEnergy >= totalCost) {
            storage.extract(totalCost, false);
            storage.update();
            particle.velocity += 1.0f + additionalVelocity;
        }
    }

    @Unique
    private AddonStats findMotorAddon(BlockPos motorPos, AcceleratorControllerBlockEntity controller) {
        BlockPos addonPos = motorPos.below();
        assert controller.getLevel() != null;
        var addonState = controller.getLevel().getBlockState(addonPos);
        var addonBlock = addonState.getBlock();

        if (addonBlock instanceof TierAddonBlock tieredAddon) {
            var addonType = addonState.getValue(TierAddonBlock.ADDON_TYPE);

            if (addonType == Constants.AddonType.SPEED || 
                addonType == Constants.AddonType.EFFICIENCY || 
                addonType == Constants.AddonType.EFFICIENT_SPEED) {

                var addonSettings = getAddonSettings(tieredAddon);
                float speedBonus = calculateSpeedBonus(addonSettings.speedMultiplier());
                float energyCostMultiplier = addonSettings.efficiencyMultiplier();

                return new AddonStats(speedBonus, energyCostMultiplier);
            }
        }

        if (addonBlock instanceof MachineAddonBlock normalAddon) {
            if (addonBlock.equals(BlockContent.MACHINE_SPEED_ADDON) ||
                addonBlock.equals(BlockContent.MACHINE_EFFICIENCY_ADDON) ||
                addonBlock.equals(BlockContent.MACHINE_ULTIMATE_ADDON)) {
                
                var addonSettings = getAddonSettings(normalAddon);
                float speedBonus = calculateSpeedBonus(addonSettings.speedMultiplier());
                float energyCostMultiplier = addonSettings.efficiencyMultiplier();

                return new AddonStats(speedBonus, energyCostMultiplier);
            }
        }

        return null;
    }

    @Unique
    private MachineAddonBlock.AddonSettings getAddonSettings(MachineAddonBlock addonBlock) {
        try {
            var field = MachineAddonBlock.class.getDeclaredField("addonSettings");
            field.setAccessible(true);
            return (MachineAddonBlock.AddonSettings) field.get(addonBlock);
        } catch (Exception e) {
            return MachineAddonBlock.AddonSettings.getDefaultSettings();
        }
    }

    @Unique
    private float calculateSpeedBonus(float speedMultiplier) {
        return (1.0f - speedMultiplier);
    }

    @Unique
    private long calculateEnergyCost(double baseCost, float efficiencyMultiplier) {
        return (long) (baseCost * Math.max(efficiencyMultiplier, 0.0f));
    }

    @Unique
    private record AddonStats(float speedBonus, float energyCostMultiplier) {}
}