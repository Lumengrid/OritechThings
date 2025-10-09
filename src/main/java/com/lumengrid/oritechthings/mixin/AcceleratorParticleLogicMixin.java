package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.api.MagneticFieldController;
import com.lumengrid.oritechthings.entity.custom.AcceleratorMagneticFieldBlockEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
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
            return false;
        }
        if (!(currentEntity instanceof MagneticFieldController)) {
            return false;
        }
        var linkedMagnets = ((MagneticFieldController) currentEntity).getLinkedMagneticFields();
        if (linkedMagnets.isEmpty()) {
            return false;
        }

        var world = currentEntity.getLevel();
        if (!(world instanceof ServerLevel serverWorld)) {
            return false;
        }

        if (serverWorld.getBlockEntity(linkedMagnets.getFirst()) instanceof AcceleratorMagneticFieldBlockEntity magnetEntity) {
            // Check if magnet is powered by redstone
            if (magnetEntity.receivedRedstoneSignal() > 0) {
                System.out.println("magnet disabled");
                return false;
            }
            
            float energyCost = calculateMagneticFieldEnergyCostStatic(requiredDist, speed, magnetEntity);
            if (magnetEntity.energyStorage.getAmount() >= energyCost) {
                System.out.println("applied magnet effect using " + energyCost);
                magnetEntity.energyStorage.amount -= (long) energyCost;
                
                // Create particle effects at the magnet location
                createMagneticFieldParticles(serverWorld, magnetEntity.getBlockPos());
                
                return true;
            }
            System.out.println("not enough energy " + energyCost);
        }
        System.out.println("you need to exit");
        return false;
    }

    // Static version of energy cost calculation
    private static float calculateMagneticFieldEnergyCostStatic(float requiredDist, float speed, AcceleratorMagneticFieldBlockEntity magnetEntity) {
        float bendDifficulty = Math.max(0, requiredDist - 1f);
        float totalCost = (speed / 2) + (bendDifficulty * 50f);

        float efficiencyMultiplier = getMagnetEfficiencyMultiplier(magnetEntity);
        totalCost = totalCost / efficiencyMultiplier;
        
        return totalCost;
    }
    
    // Helper method to get efficiency multiplier from magnet's addons
    private static float getMagnetEfficiencyMultiplier(AcceleratorMagneticFieldBlockEntity magnetEntity) {
        // Get the base addon data from the magnet entity
        var addonData = magnetEntity.getBaseAddonData();
        
        // Calculate efficiency multiplier from addons
        // Base efficiency is 1.0, addons provide bonuses
        float efficiencyMultiplier = 1.0f + addonData.efficiency();
        
        return Math.max(1.0f, efficiencyMultiplier); // Ensure minimum of 1.0
    }
    
    // Create particle effects when magnetic field is activated
    private static void createMagneticFieldParticles(ServerLevel world, net.minecraft.core.BlockPos magnetPos) {
        Vec3 centerPos = Vec3.atCenterOf(magnetPos);

        double offsetX = (world.random.nextDouble() - 0.5);
        double offsetY = world.random.nextDouble() * 0.5;
        double offsetZ = (world.random.nextDouble() - 0.5);

        Vec3 particlePos = centerPos.add(offsetX, offsetY, offsetZ);

        world.sendParticles(
                ParticleTypes.ELECTRIC_SPARK,
                particlePos.x, particlePos.y, particlePos.z,
                1,
                0.08, 0.08, 0.08,
                0.05
        );
        
        // Play a subtle sound effect
        // world.playSound(null, magnetPos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 0.3f, 1.5f);
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
