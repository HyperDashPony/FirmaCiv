package com.alekiponi.firmaciv.client.render.entity;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.AnchorEntityModel;
import com.alekiponi.firmaciv.client.model.entity.CannonballEntityModel;
import com.alekiponi.firmaciv.client.render.util.FirmacivRenderHelper;
import com.alekiponi.firmaciv.common.entity.CannonballEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
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

public class CannonballRenderer extends EntityRenderer<Entity> {

    private static final ResourceLocation CANNONBALL = new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/cannonball.png");

    private final CannonballEntityModel<CannonballEntity> model;

    public CannonballRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new CannonballEntityModel<>();
    }

    public void render(Entity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pEntity.getYRot()));
        pPoseStack.translate(0f, -1.4f, 0.0f);
        this.model.setupAnim((CannonballEntity) pEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(CANNONBALL));
        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity pEntity) {
        return null;
    }
}
