package com.lumengrid.oritechthings.entity.client;

import com.lumengrid.oritechthings.entity.custom.AmethystFishEntity;
import com.lumengrid.oritechthings.main.OritechThings;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class AmethystFishRenderer extends MobRenderer<AmethystFishEntity, LivingEntityRenderState, AmethystFishModel<LivingEntityRenderState>> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(OritechThings.MOD_ID, "textures/entity/amethystfish.png");

    public AmethystFishRenderer(EntityRendererProvider.Context context) {
        super(context, new AmethystFishModel<>(context.bakeLayer(ModelLayers.SILVERFISH)), 0.3F);
    }

    @Override
    public @NotNull LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull LivingEntityRenderState state) {
        return TEXTURE_LOCATION;
    }
}