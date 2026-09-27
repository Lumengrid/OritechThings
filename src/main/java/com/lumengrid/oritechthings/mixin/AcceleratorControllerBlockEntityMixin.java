package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.api.MagneticFieldController;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rearth.oritech.block.entity.accelerator.ParticleAcceleratorBlockEntity;

import java.util.ArrayList;
import java.util.List;

@Mixin(ParticleAcceleratorBlockEntity.class)
public class AcceleratorControllerBlockEntityMixin implements MagneticFieldController {

    private final List<BlockPos> linkedMagneticFields = new ArrayList<>();

    // ✅ Aggiornato con ValueOutput per Oritech 1.21.2+
    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void saveMagneticFields(ValueOutput output, CallbackInfo ci) {
        if (!linkedMagneticFields.isEmpty()) {
            BlockPos pos = linkedMagneticFields.get(0);
            output.putInt("magnetX", pos.getX());
            output.putInt("magnetY", pos.getY());
            output.putInt("magnetZ", pos.getZ());
            output.putBoolean("hasMagnet", true);
        } else {
            output.putBoolean("hasMagnet", false);
        }
    }

    // ✅ Aggiornato con ValueInput per Oritech 1.21.2+
    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void loadMagneticFields(ValueInput input, CallbackInfo ci) {
        linkedMagneticFields.clear();
        boolean hasMagnet = input.getBooleanOr("hasMagnet", false);
        if (hasMagnet) {
            int x = input.getIntOr("magnetX", 0);
            int y = input.getIntOr("magnetY", 0);
            int z = input.getIntOr("magnetZ", 0);
            linkedMagneticFields.add(new BlockPos(x, y, z));
        }
    }

    @Override
    public void addMagneticField(BlockPos magnetPos) {
        linkedMagneticFields.clear();
        linkedMagneticFields.add(magnetPos);
    }

    @Override
    public boolean removeMagneticField(BlockPos magnetPos) {
        return linkedMagneticFields.remove(magnetPos);
    }

    @Override
    public List<BlockPos> getLinkedMagneticFields() {
        return linkedMagneticFields;
    }
}