package com.alekiponi.firmaciv.client.render.entity;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.SloopEntityModel;
import com.alekiponi.firmaciv.common.entity.CanoeEntity;
import com.alekiponi.firmaciv.common.entity.SloopEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class SloopRenderer extends EntityRenderer<SloopEntity> {

    private final Pair<ResourceLocation, SloopEntityModel> sloopResources;

    public SloopRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.8f;
        this.sloopResources = Pair.of(new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/sloop.png"),
                new SloopEntityModel());
    }

    @Override
    public void render(SloopEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack poseStack,
            MultiBufferSource pBuffer, int pPackedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.5D, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw));
        float f = (float) pEntity.getHurtTime() - pPartialTicks;
        float f1 = pEntity.getDamage() - pPartialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float) pEntity.getHurtDir()));
        }

        float f2 = pEntity.getBubbleAngle(pPartialTicks);
        if (!Mth.equal(f2, 0.0F)) {
            poseStack.mulPose(
                    (new Quaternionf()).setAngleAxis(pEntity.getBubbleAngle(pPartialTicks) * ((float) Math.PI / 180F),
                            1.0F, 0.0F, 1.0F));
        }

        Pair<ResourceLocation, SloopEntityModel> pair = sloopResources;
        ResourceLocation resourcelocation = pair.getFirst();
        SloopEntityModel sloopModel = pair.getSecond();

        poseStack.translate(0.0f, 1.0625f, 0f);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
        sloopModel.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(sloopModel.renderType(resourcelocation));
        sloopModel.renderToBuffer(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                1.0F, 1.0F);


        if (!pEntity.isUnderWater()) {
            VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.waterMask());
            sloopModel.getWaterocclusion().render(poseStack, vertexconsumer1, pPackedLight, OverlayTexture.NO_OVERLAY);
        }

        poseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, poseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SloopEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/sloop.png");
    }

}
