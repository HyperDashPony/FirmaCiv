package com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.AnchorEntityModel;
import com.alekiponi.firmaciv.client.render.util.FirmacivRenderHelper;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.RowboatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.AnchorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class AnchorRenderer extends EntityRenderer<Entity> {
    private static final ResourceLocation ANCHOR = new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/anchor.png");
    private final AnchorEntityModel<AnchorEntity> model;

    public AnchorRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new AnchorEntityModel<>();
    }

    public void render(Entity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        float rotation = 0f;
        if(pEntity.getVehicle()== null){
            return;
        }
        if(pEntity.getVehicle().getVehicle() == null){
            return;
        }
        if(pEntity.getVehicle().getVehicle().getVehicle() == null){
            return;
        }
        if (pEntity.getVehicle().getVehicle().getVehicle() instanceof AbstractFirmacivBoatEntity trueVehicle) {
            rotation = trueVehicle.getYRot();
        } else {
            rotation = pEntityYaw;
        }
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(60 - rotation));
        pPoseStack.translate(0f, 0f, 1.4f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        this.model.setupAnim((AnchorEntity) pEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(ANCHOR));
        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                1.0F, 1.0F);
        pPoseStack.popPose();
        Entity vehicle = pEntity.getVehicle();
        assert vehicle != null;
        FirmacivRenderHelper.renderRope(pEntity, pPartialTicks, pPoseStack, pBuffer, vehicle, this.getBlockLightLevel(pEntity, BlockPos.containing(pEntity.getEyePosition(pPartialTicks))));
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity pEntity) {
        return null;
    }


}

