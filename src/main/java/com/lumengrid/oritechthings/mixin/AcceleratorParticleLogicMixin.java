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
                if (canMagneticFieldAssistStatic(originalRequiredDist - combinedDist, speed)) {
                    cir.setReturnValue(Float.MIN_VALUE);
                    return;
                }
            }
        }
        cir.setReturnValue(originalRequiredDist);
    }

    private static boolean canMagneticFieldAssistStatic(float requiredDist, float speed) {
        // Check if magnetic fields are enabled in config
        var config = com.lumengrid.oritechthings.main.ConfigLoader.getInstance().magneticFieldSettings;
        if (!config.enabled()) {
            return false;
        }
        
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
            if (magnetEntity.receivedRedstoneSignal() > 0) {
                return false;
            }

            long currentTick = serverWorld.getGameTime();
            if (!lastTickLogged.containsKey(magnetEntity.getBlockPos())) {
                lastTickLogged.put(magnetEntity.getBlockPos(), -1L);
                callsThisTick.put(magnetEntity.getBlockPos(), 0);
            }
            
            if (lastTickLogged.get(magnetEntity.getBlockPos()) != currentTick) {
                lastTickLogged.put(magnetEntity.getBlockPos(), currentTick);
                callsThisTick.put(magnetEntity.getBlockPos(), 1);
                if (lastEnergyTick.containsKey(magnetEntity.getBlockPos())) {
                    lastEnergyTick.put(magnetEntity.getBlockPos(), -1L);
                }
            } else {
                callsThisTick.put(magnetEntity.getBlockPos(), callsThisTick.get(magnetEntity.getBlockPos()) + 1);
            }

            long energyCostLong = Math.round(calculateMagneticFieldEnergyCostStatic(requiredDist, speed, magnetEntity));
            if (!lastEnergyTick.containsKey(magnetEntity.getBlockPos())) {
                lastEnergyTick.put(magnetEntity.getBlockPos(), -1L);
            }

            boolean alreadyConsumedThisTick = lastEnergyTick.get(magnetEntity.getBlockPos()) == currentTick;
            if (alreadyConsumedThisTick) {
                return true;
            }
            
            magnetEntity.energyStorage.update();
            if (magnetEntity.energyStorage.getAmount() >= energyCostLong) {
                magnetEntity.energyStorage.amount -= energyCostLong;
                magnetEntity.energyStorage.update();

                lastEnergyTick.put(magnetEntity.getBlockPos(), currentTick);
                createMagneticFieldParticles(serverWorld, magnetEntity.getBlockPos());

                return true;
            }
        }

        return false;
    }

    private static float calculateMagneticFieldEnergyCostStatic(float requiredDist, float speed, AcceleratorMagneticFieldBlockEntity magnetEntity) {
        float totalCost = getTotalCost(requiredDist, speed);
        float efficiencyMultiplier = Math.max(0.1f, magnetEntity.getBaseAddonData().efficiency());

        return totalCost * efficiencyMultiplier;
    }

    private static float getTotalCost(float requiredDist, float speed) {
        var config = com.lumengrid.oritechthings.main.ConfigLoader.getInstance().magneticFieldSettings;
        float baseCost = config.baseCost();
        float speedCost = (speed * speed) / config.speedCostDivisor();
        float bendCost = (requiredDist * requiredDist);

        return baseCost + speedCost + bendCost;
    }

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
    }

    private static final ThreadLocal<AcceleratorControllerBlockEntity> currentAccelerator = new ThreadLocal<>();

    private static final java.util.Map<net.minecraft.core.BlockPos, Long> lastTickLogged = new java.util.HashMap<>();
    private static final java.util.Map<net.minecraft.core.BlockPos, Integer> callsThisTick = new java.util.HashMap<>();
    private static final java.util.Map<net.minecraft.core.BlockPos, Long> lastEnergyTick = new java.util.HashMap<>();

    @Inject(method = "update", at = @At("HEAD"))
    private void setCurrentAcceleratorContext(AcceleratorParticleLogic.ActiveParticle particle, CallbackInfo ci) {
        currentAccelerator.set(entity);
    }

    @Inject(method = "update", at = @At("RETURN"))
    private void clearCurrentAcceleratorContext(AcceleratorParticleLogic.ActiveParticle particle, CallbackInfo ci) {
        currentAccelerator.remove();
    }

}
