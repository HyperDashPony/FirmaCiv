package com.alekiponi.firmaciv.client.render.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.KayakEntityModel;
import com.alekiponi.firmaciv.common.entity.vehicle.KayakEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KayakRenderer extends EntityRenderer<KayakEntity> {

    private static final ResourceLocation KAYAK =
            new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/kayak.png");
    private final Pair<ResourceLocation, KayakEntityModel> kayakResources;

    public KayakRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.4f;
        this.kayakResources = Pair.of(new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/kayak.png"),
                new KayakEntityModel());
    }

    @Override
    public void render(KayakEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
            MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, 0.5D, 0.0D);
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw));
        float f = (float) pEntity.getHurtTime() - pPartialTicks;
        float f1 = pEntity.getDamage() - pPartialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            pMatrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float) pEntity.getHurtDir()));
        }

        Pair<ResourceLocation, KayakEntityModel> pair = kayakResources;
        ResourceLocation resourcelocation = pair.getFirst();
        KayakEntityModel kayakModel = pair.getSecond();

        pMatrixStack.translate(0.0f, 1.0625f, 0f);
        pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(0.0F));
        kayakModel.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(kayakModel.renderType(resourcelocation));
        kayakModel.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                1.0F, 1.0F);

        if (!pEntity.isUnderWater()) {
            VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.waterMask());
            kayakModel.getWaterocclusion()
                    .render(pMatrixStack, vertexconsumer1, pPackedLight, OverlayTexture.NO_OVERLAY);
        }
        if (!pEntity.getTruePassengers().isEmpty()) {
            VertexConsumer vertexconsumer1 = pBuffer.getBuffer(kayakModel.renderType(resourcelocation));
            kayakModel.getCockpitCover().render(pMatrixStack, vertexconsumer1, pPackedLight, OverlayTexture.NO_OVERLAY);
        }


        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(KayakEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/kayak.png");
    }


}
