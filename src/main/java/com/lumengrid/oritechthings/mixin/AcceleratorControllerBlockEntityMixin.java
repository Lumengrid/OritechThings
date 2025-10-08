package com.lumengrid.oritechthings.mixin;

import com.lumengrid.oritechthings.api.MagneticFieldController;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rearth.oritech.block.entity.accelerator.AcceleratorControllerBlockEntity;

import java.util.ArrayList;
import java.util.List;

@Mixin(AcceleratorControllerBlockEntity.class)
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
        if (nbt.contains("linkedMagneticFields", Tag.TAG_LIST)) {
            ListTag magneticFieldsTag = nbt.getList("linkedMagneticFields", Tag.TAG_COMPOUND);
            for (int i = 0; i < magneticFieldsTag.size(); i++) {
                CompoundTag posTag = magneticFieldsTag.getCompound(i);
                BlockPos pos = new BlockPos(
                    posTag.getInt("x"),
                    posTag.getInt("y"),
                    posTag.getInt("z")
                );
                linkedMagneticFields.add(pos);
            }
        }
    }
    
    public void addMagneticField(BlockPos magnetPos) {
        linkedMagneticFields.clear();
        linkedMagneticFields.add(magnetPos);
    }
    
    public boolean removeMagneticField(BlockPos magnetPos) {
        return linkedMagneticFields.remove(magnetPos);
    }
    
    public List<BlockPos> getLinkedMagneticFields() {
        return linkedMagneticFields;
    }
}
