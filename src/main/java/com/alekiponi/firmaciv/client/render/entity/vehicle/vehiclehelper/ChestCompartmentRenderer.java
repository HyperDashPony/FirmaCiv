package com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla.ChestCompartmentEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.Material;

import java.util.Calendar;

public class ChestCompartmentRenderer extends CompartmentRenderer<ChestCompartmentEntity> {

    private static final String BOTTOM = "bottom";
    private static final String LID = "lid";
    private static final String LOCK = "lock";
    private final ModelPart lid;
    private final ModelPart bottom;
    private final ModelPart lock;
    private boolean xmasTextures;

    public ChestCompartmentRenderer(final EntityRendererProvider.Context context) {
        super(context);

        final Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DAY_OF_MONTH) >= 24 && calendar.get(
                Calendar.DAY_OF_MONTH) <= 26) {
            this.xmasTextures = true;
        }

        final ModelPart modelpart = context.bakeLayer(ModelLayers.CHEST);
        this.bottom = modelpart.getChild(BOTTOM);
        this.lid = modelpart.getChild(LID);
        this.lock = modelpart.getChild(LOCK);
    }

    @Override
    protected void renderCompartmentContents(final ChestCompartmentEntity compartmentEntity, final float partialTicks,
            final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight) {

        float openAngle = compartmentEntity.getOpenNess(partialTicks);
        openAngle = 1 - openAngle;
        openAngle = 1 - openAngle * openAngle * openAngle;

        final Material material = this.xmasTextures ? Sheets.CHEST_XMAS_LOCATION : Sheets.CHEST_LOCATION;
        final VertexConsumer vertexConsumer = material.buffer(bufferSource, RenderType::entityCutout);

        this.render(poseStack, vertexConsumer, this.lid, this.lock, this.bottom, openAngle, packedLight);
    }

    private void render(final PoseStack poseStack, final VertexConsumer vertexConsumer, final ModelPart lidModel,
            final ModelPart lockModel, final ModelPart bottomModel, final float lidAngle, final int packedLight) {
        lidModel.xRot = (float) (-lidAngle * Math.PI / 2);
        lockModel.xRot = lidModel.xRot;
        lidModel.render(poseStack, vertexConsumer, packedLight, 655360);
        lockModel.render(poseStack, vertexConsumer, packedLight, 655360);
        bottomModel.render(poseStack, vertexConsumer, packedLight, 655360);
    }
}