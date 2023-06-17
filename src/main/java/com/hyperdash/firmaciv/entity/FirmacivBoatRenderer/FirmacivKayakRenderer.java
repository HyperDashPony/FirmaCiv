package com.hyperdash.firmaciv.entity.FirmacivBoatRenderer;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import com.hyperdash.firmaciv.entity.custom.KayakEntity;
import com.hyperdash.firmaciv.entity.custom.entitymodel.CanoeEntityModel;
import com.hyperdash.firmaciv.entity.custom.entitymodel.KayakEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FirmacivKayakRenderer extends EntityRenderer<KayakEntity> {

    private final Pair<ResourceLocation, KayakEntityModel> kayakResources;

    private static final ResourceLocation KAYAK =
            new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/kayak.png");

    public FirmacivKayakRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.4f;
        this.kayakResources = Pair.of(new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/kayak.png"),
                new KayakEntityModel());
    }

    @Override
    public void render(KayakEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, 0.5D, 0.0D);
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - pEntityYaw));
        float f = (float)pEntity.getHurtTime() - pPartialTicks;
        float f1 = pEntity.getDamage() - pPartialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)pEntity.getHurtDir()));
        }

        float f2 = pEntity.getBubbleAngle(pPartialTicks);
        if (!Mth.equal(f2, 0.0F)) {
            pMatrixStack.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), pEntity.getBubbleAngle(pPartialTicks), true));
        }

        Pair<ResourceLocation, KayakEntityModel> pair = kayakResources;
        ResourceLocation resourcelocation = pair.getFirst();
        KayakEntityModel kayakModel = pair.getSecond();

        pMatrixStack.translate(0.0f, 1.1875f, 0f);
        pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(0.0F));
        kayakModel.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(kayakModel.renderType(resourcelocation));
        kayakModel.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        if (!pEntity.isUnderWater()) {
            VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.waterMask());
            kayakModel.getWaterocclusion().render(pMatrixStack, vertexconsumer1, pPackedLight, OverlayTexture.NO_OVERLAY);
        }
        if(!pEntity.getPassengers().isEmpty()){
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
