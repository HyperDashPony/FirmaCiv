package com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.AnchorEntityModel;
import com.alekiponi.firmaciv.client.model.entity.ConstructionEntityModel;
import com.alekiponi.firmaciv.client.render.util.FirmacivRenderHelper;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.AnchorEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.ConstructionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ConstructionRenderer extends EntityRenderer<Entity> {

    private static final ResourceLocation HAMMER = new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/construction.png");

    private final ConstructionEntityModel<ConstructionEntity> model;

    public ConstructionRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        model = new ConstructionEntityModel<>();
    }

    public void render(Entity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.getVehicle()== null){
            return;
        }
        if(pEntity.getVehicle().getVehicle() == null){
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        assert mc.player != null;
        if(mc.player.distanceTo(pEntity.getRootVehicle()) > 5){
            return;
        }

        pPoseStack.pushPose();
        pPoseStack.scale(1,1,1);
        pPoseStack.translate(0f, 1.75f, 0);

        float f3 = ((ConstructionEntity) pEntity).getSpin(pPartialTicks);
        pPoseStack.mulPose(Axis.YP.rotation(f3));
        pPoseStack.mulPose(Axis.ZP.rotation((float) Math.toRadians(180)));

        VertexConsumer vertexconsumer = pBuffer.getBuffer(
                RenderType.entityCutout(HAMMER));
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
