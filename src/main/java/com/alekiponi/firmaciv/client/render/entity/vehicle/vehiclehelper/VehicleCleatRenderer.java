package com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.CleatKnotEntityModel;
import com.alekiponi.firmaciv.client.render.util.FirmacivRenderHelper;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.RowboatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class VehicleCleatRenderer extends EntityRenderer<VehicleCleatEntity> {

    private static final ResourceLocation CLEAT_KNOT = new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/cleat_knot.png");
    private final CleatKnotEntityModel model;

    public VehicleCleatRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new CleatKnotEntityModel();
    }



    @Override
    public ResourceLocation getTextureLocation(VehicleCleatEntity pEntity) {
        return null;
    }

    public void render(VehicleCleatEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        net.minecraft.world.entity.Entity entity = pEntity.getLeashHolder();
        if (entity != null) {
            float rotation = 0f;
            if (pEntity.getVehicle().getVehicle() instanceof AbstractFirmacivBoatEntity trueVehicle) {
                rotation = trueVehicle.getYRot();
            } else {
                rotation = pEntityYaw;
            }
            pPoseStack.pushPose();
            pPoseStack.translate(0f, 1.5f, 0f);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(180));

            if ((pEntity.getVehicle().isPassenger() && pEntity.getVehicle().getVehicle() instanceof RowboatEntity)) {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(rotation));
            } else {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(rotation + 90));
            }
            this.model.setupAnim(pEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(CLEAT_KNOT));
            this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                    1.0F, 1.0F);
            if (!(pEntity.getVehicle().isPassenger() && pEntity.getVehicle().getVehicle() instanceof CanoeEntity)) {
                model.getSides().render(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
            }
            pPoseStack.popPose();
            FirmacivRenderHelper.renderRope(pEntity, pPartialTicks, pPoseStack, pBuffer, entity, this.getBlockLightLevel(pEntity,  BlockPos.containing(pEntity.getEyePosition(pPartialTicks))));
        }
    }


}
