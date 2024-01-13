package com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla.EnderChestCompartmentEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class EnderChestCompartmentRenderer extends CompartmentRenderer<EnderChestCompartmentEntity> {

    private static final String BOTTOM = "bottom";
    private static final String LID = "lid";
    private static final String LOCK = "lock";
    private final ModelPart lid;
    private final ModelPart bottom;
    private final ModelPart lock;

    public EnderChestCompartmentRenderer(final EntityRendererProvider.Context context) {
        super(context);

        final ModelPart modelpart = context.bakeLayer(ModelLayers.CHEST);
        this.bottom = modelpart.getChild(BOTTOM);
        this.lid = modelpart.getChild(LID);
        this.lock = modelpart.getChild(LOCK);
    }

    @Override
    protected void renderCompartmentContents(final EnderChestCompartmentEntity compartmentEntity,
            final float partialTicks, final PoseStack poseStack, final MultiBufferSource bufferSource,
            final int packedLight) {
        float openAngle = compartmentEntity.getOpenNess(partialTicks);
        openAngle = 1 - openAngle;
        openAngle = 1 - openAngle * openAngle * openAngle;

        final VertexConsumer vertexConsumer = Sheets.ENDER_CHEST_LOCATION.buffer(bufferSource,
                RenderType::entityCutout);

        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.translate(-1, 0, -1);
        this.render(poseStack, vertexConsumer, this.lid, this.lock, this.bottom, openAngle, packedLight);
    }

    private void render(final PoseStack poseStack, final VertexConsumer vertexConsumer, final ModelPart lidModel,
            final ModelPart lockModel, final ModelPart bottomModel, final float lidAngle, final int packedLight) {
        lidModel.xRot = (float) (-lidAngle * Math.PI / 2);
        lockModel.xRot = lidModel.xRot;
        lidModel.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        lockModel.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        bottomModel.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
    }
}