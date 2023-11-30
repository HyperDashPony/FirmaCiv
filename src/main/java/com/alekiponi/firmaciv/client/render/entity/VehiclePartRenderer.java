package com.alekiponi.firmaciv.client.render.entity;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehiclePartEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class VehiclePartRenderer extends EntityRenderer<VehiclePartEntity> {
    public VehiclePartRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(VehiclePartEntity pEntity) {
        return null;
    }

}
