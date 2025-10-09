package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.api.MagneticFieldController;
import com.lumengrid.oritechthings.entity.custom.AcceleratorMagneticFieldBlockEntity;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rearth.oritech.block.entity.accelerator.AcceleratorControllerBlockEntity;
import rearth.oritech.block.entity.accelerator.AcceleratorParticleLogic;

@Mixin(AcceleratorParticleLogic.class)
public class AcceleratorParticleLogicMixin {

    @Shadow
    @Final
    private AcceleratorControllerBlockEntity entity;

    @Shadow
    @Final
    private net.minecraft.server.level.ServerLevel world;

    @Inject(method = "getRequiredBendDist", at = @At("RETURN"), cancellable = true)
    private static void getRequiredBendDistWithMagneticField(float speed, CallbackInfoReturnable<Float> cir) {
        float originalRequiredDist = cir.getReturnValue();
        AcceleratorControllerBlockEntity currentEntity = currentAccelerator.get();

        if (currentEntity != null && currentEntity.getParticle() != null) {
            float combinedDist = currentEntity.getParticle().lastBendDistance + currentEntity.getParticle().lastBendDistance2;
            if (combinedDist <= originalRequiredDist) {
                System.out.println("need to check magnet");
                if (canMagneticFieldAssistStatic(originalRequiredDist, speed)) {
                    System.out.println("sto dentro 3");
                    // If magnetic field can help, return a very large negative value to prevent exit
                    cir.setReturnValue(Float.MIN_VALUE); // Very large negative value to prevent particle exit
                    return;
                }
            }
        }
        cir.setReturnValue(originalRequiredDist);
    }

    // Static helper method to check if magnetic field can assist with bend
    private static boolean canMagneticFieldAssistStatic(float requiredDist, float speed) {
        AcceleratorControllerBlockEntity currentEntity = currentAccelerator.get();
        if (currentEntity == null) {
            System.out.println("1");
            return false;
        }
        if (!(currentEntity instanceof MagneticFieldController)) {
            System.out.println("2");
            return false;
        }
        var linkedMagnets = ((MagneticFieldController) currentEntity).getLinkedMagneticFields();
        if (linkedMagnets.isEmpty()) {
            System.out.println("3");
            return false;
        }

        var world = currentEntity.getLevel();
        if (!(world instanceof ServerLevel serverWorld)) {
            System.out.println("4");
            return false;
        }

        if (serverWorld.getBlockEntity(linkedMagnets.getFirst()) instanceof AcceleratorMagneticFieldBlockEntity magnetEntity) {
            float energyCost = calculateMagneticFieldEnergyCostStatic(requiredDist, speed);
            System.out.println(energyCost);
            System.out.println(magnetEntity.getEnergyStorage().getAmount());
            if (magnetEntity.getEnergyStorage().getAmount() >= energyCost) {
                System.out.println("applied magnet effect");
                magnetEntity.getEnergyStorage().extract((long) energyCost, false);
                return true;
            }
        }
        System.out.println("you need to exit");
        return false;
    }

    // Static version of energy cost calculation
    private static float calculateMagneticFieldEnergyCostStatic(float requiredDist, float speed) {
        // Base cost increases with speed and bend difficulty
        float baseCost = speed * 10f; // Base cost proportional to speed
        float bendDifficulty = Math.max(0, requiredDist - 1f); // Extra cost for difficult bends
        float totalCost = baseCost + (bendDifficulty * 50f);
        
        // Apply efficiency addon reduction if available
        AcceleratorControllerBlockEntity currentEntity = currentAccelerator.get();
        if (currentEntity != null) {
            // Get efficiency multiplier from addons (if any)
            float efficiencyMultiplier = getEfficiencyMultiplier(currentEntity);
            totalCost = totalCost / efficiencyMultiplier; // Higher efficiency = lower cost
        }
        
        return totalCost;
    }
    
    // Helper method to get efficiency multiplier from addons
    private static float getEfficiencyMultiplier(AcceleratorControllerBlockEntity entity) {
        // This would need to be implemented based on how Oritech handles addon multipliers
        // For now, return 1.0f (no efficiency bonus)
        return 1.0f;
    }

    // Thread-local storage to track the current accelerator instance
    private static final ThreadLocal<AcceleratorControllerBlockEntity> currentAccelerator = new ThreadLocal<>();

    // Inject into update method to set the current accelerator context
    @Inject(method = "update", at = @At("HEAD"))
    private void setCurrentAcceleratorContext(AcceleratorParticleLogic.ActiveParticle particle, CallbackInfo ci) {
        currentAccelerator.set(entity);
    }

    // Inject into update method to clear the current accelerator context
    @Inject(method = "update", at = @At("RETURN"))
    private void clearCurrentAcceleratorContext(AcceleratorParticleLogic.ActiveParticle particle, CallbackInfo ci) {
        currentAccelerator.remove();
    }

}
