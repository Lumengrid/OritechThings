package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.block.custom.TierAddonBlock;
import com.lumengrid.oritechthings.util.Constants;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rearth.oritech.block.blocks.addons.MachineAddonBlock;
import rearth.oritech.block.entity.accelerator.AcceleratorMotorBlockEntity;
import rearth.oritech.block.entity.accelerator.AcceleratorParticleLogic;
import rearth.oritech.block.entity.accelerator.ParticleAcceleratorBlockEntity;
import rearth.oritech.config.OritechConfig;
import rearth.oritech.init.BlockContent;

@Mixin(ParticleAcceleratorBlockEntity.class)
public class AcceleratorMotorInteractionMixin {
    @Shadow
    private AcceleratorParticleLogic.ActiveParticle particle;

    @Inject(method = "handleParticleMotorInteraction", at = @At("HEAD"))
    private void handleParticleMotorInteraction(BlockPos motorBlock, CallbackInfo ci) {
        if (particle == null) return;

        ParticleAcceleratorBlockEntity self = (ParticleAcceleratorBlockEntity) (Object) this;
        assert self.getLevel() != null;
        var entity = self.getLevel().getBlockEntity(motorBlock);
        if (!(entity instanceof AcceleratorMotorBlockEntity motorEntity)) return;

        AddonStats addonStats = findMotorAddon(motorBlock, self);

        // ✅ 1. getEnergyStorage() senza parametri
        var storage = motorEntity.getEnergyLookup(null);
        if (storage == null) return;

        var speed = particle.velocity;
        long baseRfCost = OritechConfig.accelerationRFCost.get();

        if (addonStats == null) {
            // ✅ 2. Uso corretto dell'API DynamicEnergyStorage
            long availableEnergy = storage.getAmountAsLong();
            long cost = (long) (speed * baseRfCost);
            if (availableEnergy >= cost) {
                var transaction = Transaction.openRoot();
                storage.extract((int) cost, transaction);
                particle.velocity += 1.0f;
            }

            return;
        }

        double baseMotorCost = speed * baseRfCost;
        float additionalVelocity = Math.max(Math.min(addonStats.speedBonus(), 10.0f), 0.0f);
        long totalCost = calculateEnergyCost(baseMotorCost, addonStats.energyCostMultiplier());
        long availableEnergy = storage.getAmountAsLong();

        if (availableEnergy >= totalCost) {
            var transaction = Transaction.openRoot();
            storage.extract((int) totalCost, transaction);
            particle.velocity += (long) (1.0f + additionalVelocity);
        }
    }

    @Unique
    private AddonStats findMotorAddon(BlockPos motorPos, ParticleAcceleratorBlockEntity controller) {
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
                    addonBlock.equals(BlockContent.MACHINE_EFFICIENCY_ADDON)) {

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