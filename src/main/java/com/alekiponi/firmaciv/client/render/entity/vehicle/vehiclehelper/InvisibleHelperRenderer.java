package com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class InvisibleHelperRenderer extends EntityRenderer {
    public InvisibleHelperRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity pEntity) {
        return null;
    }
}
