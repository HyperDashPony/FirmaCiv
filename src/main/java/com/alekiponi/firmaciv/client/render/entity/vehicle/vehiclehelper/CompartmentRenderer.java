package com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.AbstractVehiclePart;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CompartmentRenderer extends EntityRenderer<AbstractCompartmentEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public CompartmentRenderer(EntityRendererProvider.Context pContext/*, ModelLayerLocation pLayer*/) {
        super(pContext);
        this.shadowRadius = 0.0F;
        this.blockRenderer = pContext.getBlockRenderDispatcher();
    }

    public void render(final AbstractCompartmentEntity compartmentEntity, final float entityYaw,
            final float partialTicks, final PoseStack poseStack, final MultiBufferSource bufferSource,
            final int packedLight) {
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
        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        if (compartmentEntity.getTrueVehicle() instanceof CanoeEntity) {
            poseStack.scale(0.6F, 0.6F, 0.6F);
        } else {
            poseStack.scale(0.6875F, 0.6875F, 0.6875F);
        }

        poseStack.translate(-0.5F, 0, -0.5F);
        {
            if (compartmentEntity.getDisplayBlockState().getRenderShape() != RenderShape.INVISIBLE) {
                this.renderCompartmentContents(compartmentEntity, partialTicks,
                        compartmentEntity.getDisplayBlockState(), poseStack, bufferSource, packedLight);
            }
        }

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(final AbstractCompartmentEntity compartmentEntity) {
        return null;
    }

    protected void renderCompartmentContents(final AbstractCompartmentEntity compartmentEntity,
            final float partialTicks, final BlockState blockState, final PoseStack poseStack,
            final MultiBufferSource bufferSource, final int packedLight) {
        this.blockRenderer.renderSingleBlock(blockState, poseStack, bufferSource, packedLight,
                OverlayTexture.NO_OVERLAY);
    }
}