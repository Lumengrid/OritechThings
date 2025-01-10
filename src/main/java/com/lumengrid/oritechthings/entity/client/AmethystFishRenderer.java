package com.lumengrid.oritechthings.entity.client;

import com.lumengrid.oritechthings.entity.custom.AmethystFishEntity;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Silverfish;
import org.jetbrains.annotations.NotNull;

public class AmethystFishRenderer extends MobRenderer<AmethystFishEntity, AmethystFishModel<AmethystFishEntity>> {
    private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(OritechThings.MOD_ID,"textures/entity/amethystfish.png");

    public AmethystFishRenderer(EntityRendererProvider.Context p_174378_) {
        super(p_174378_, new AmethystFishModel<>(p_174378_.bakeLayer(ModelLayers.SILVERFISH)), 0.3F);
    }

    protected float getFlipDegrees(Silverfish livingEntity) {
        return 180.0F;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AmethystFishEntity entity) {
        return TEXTURE_LOCATION;
    }
}
