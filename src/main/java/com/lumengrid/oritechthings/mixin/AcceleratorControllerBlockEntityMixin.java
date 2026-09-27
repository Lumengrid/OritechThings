package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.api.MagneticFieldController;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void saveMagneticFields(CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfo ci) {
        ListTag magneticFieldsTag = new ListTag();
        for (BlockPos pos : linkedMagneticFields) {
            CompoundTag posTag = new CompoundTag();
            posTag.putInt("x", pos.getX());
            posTag.putInt("y", pos.getY());
            posTag.putInt("z", pos.getZ());
            magneticFieldsTag.add(posTag);
        }
        nbt.put("linkedMagneticFields", magneticFieldsTag);
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void loadMagneticFields(CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfo ci) {
        linkedMagneticFields.clear();

        // ✅ 1. 'contains' prende 1 solo argomento
        if (nbt.contains("linkedMagneticFields")) {
            // ✅ 2. 'getList' prende 1 solo argomento
            ListTag magneticFieldsTag = nbt.getListOrEmpty("linkedMagneticFields");

            for (int i = 0; i < magneticFieldsTag.size(); i++) {
                // ✅ 3. getCompound(i) restituisce Optional<CompoundTag>
                magneticFieldsTag.getCompound(i).ifPresent(posTag -> {
                    // ✅ 4. getInt(...) restituisce Optional<Integer>, estraiamo con .orElse(0)
                    int x = posTag.getInt("x").orElse(0);
                    int y = posTag.getInt("y").orElse(0);
                    int z = posTag.getInt("z").orElse(0);

                    linkedMagneticFields.add(new BlockPos(x, y, z));
                });
            }
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