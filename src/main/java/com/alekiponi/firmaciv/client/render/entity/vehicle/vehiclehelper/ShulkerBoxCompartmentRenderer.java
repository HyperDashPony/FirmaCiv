package com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla.ShulkerBoxCompartmentEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.DyeColor;

public class ShulkerBoxCompartmentRenderer extends CompartmentRenderer<ShulkerBoxCompartmentEntity> {
    private final ShulkerModel<?> model;

    public ShulkerBoxCompartmentRenderer(final EntityRendererProvider.Context context) {
        super(context);
        this.model = new ShulkerModel<>(context.bakeLayer(ModelLayers.SHULKER));
    }

    @Override
    protected void renderCompartmentContents(final ShulkerBoxCompartmentEntity compartmentEntity,
            final float partialTicks, final PoseStack poseStack, final MultiBufferSource bufferSource,
            final int packedLight) {

        final DyeColor dyecolor = compartmentEntity.getColor();
        final Material material = dyecolor == null ? Sheets.DEFAULT_SHULKER_TEXTURE_LOCATION : Sheets.SHULKER_TEXTURE_LOCATION.get(
                dyecolor.getId());

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.scale(0.9995F, 0.9995F, 0.9995F);
        poseStack.scale(1, -1, -1);
        poseStack.translate(0, -1, 0);
        final ModelPart shulkerLid = this.model.getLid();
        shulkerLid.setPos(0, 24 - compartmentEntity.getOpenNess(partialTicks) * 0.5F * 16, 0);
        shulkerLid.yRot = 270 * compartmentEntity.getOpenNess(partialTicks) * ((float) Math.PI / 180);
        final VertexConsumer vertexconsumer = material.buffer(bufferSource, RenderType::entityCutoutNoCull);
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();
    }
}