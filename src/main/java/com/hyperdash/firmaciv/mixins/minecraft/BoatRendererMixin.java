package com.hyperdash.firmaciv.mixins.minecraft;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BoatRenderer.class)
public class BoatRendererMixin extends EntityRenderer<Boat> {


    protected BoatRendererMixin(EntityRendererProvider.Context pContext) {
        super(pContext);
    }


    public void injectUpsideDownBoat(Boat pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                                     MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.scale(0F, -1.0F, 0F);
    }

    @Shadow
    public ResourceLocation getTextureLocation(Boat pEntity) {
        return null;
    }

}
