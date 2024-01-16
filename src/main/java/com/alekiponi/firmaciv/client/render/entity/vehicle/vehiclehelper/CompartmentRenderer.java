package com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.AbstractVehiclePart;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class CompartmentRenderer<CompartmentType extends AbstractCompartmentEntity> extends EntityRenderer<CompartmentType> {

    public CompartmentRenderer(final EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0;
    }

    @Override
    public void render(final CompartmentType compartmentEntity, final float entityYaw, final float partialTicks,
            final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight) {
        super.render(compartmentEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);

        if (compartmentEntity.tickCount < 2) {
            return;
        }

        final float rotation;
        if (compartmentEntity.getTrueVehicle() != null && compartmentEntity.getVehicle() instanceof AbstractVehiclePart vehiclePart && compartmentEntity.tickCount < 2) {
            rotation = compartmentEntity.getTrueVehicle().getYRot() + vehiclePart.getCompartmentRotation();
        } else {
            rotation = entityYaw;
        }

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(180 - rotation));
        if (compartmentEntity.getTrueVehicle() instanceof CanoeEntity) {
            poseStack.scale(0.6F, 0.6F, 0.6F);
        } else {
            poseStack.scale(0.6875F, 0.6875F, 0.6875F);
        }

        poseStack.translate(-0.5F, 0, -0.5F);

        this.renderCompartmentContents(compartmentEntity, partialTicks, poseStack, bufferSource, packedLight);

        poseStack.popPose();
    }

    /**
     * Render the compartment contents. This is pre-scaled, rotated and translated for ease of use
     */
    protected abstract void renderCompartmentContents(final CompartmentType compartmentEntity, final float partialTicks,
            final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight);

    @Override
    public ResourceLocation getTextureLocation(final CompartmentType compartmentEntity) {
        return MissingTextureAtlasSprite.getLocation();
    }
}