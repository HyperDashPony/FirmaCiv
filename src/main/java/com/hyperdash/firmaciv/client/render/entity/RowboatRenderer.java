package com.hyperdash.firmaciv.client.render.entity;

import com.google.common.collect.ImmutableMap;
import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.client.model.entity.RowboatEntityModel;
import com.hyperdash.firmaciv.common.entity.BoatVariant;
import com.hyperdash.firmaciv.common.entity.RowboatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
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
import org.joml.Quaternionf;
import org.slf4j.Logger;

import java.util.Map;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class RowboatRenderer extends EntityRenderer<RowboatEntity> {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final Map<BoatVariant, Pair<ResourceLocation, RowboatEntityModel>> rowboatResources;

    public RowboatRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 1.0f;
        this.rowboatResources = Stream.of(BoatVariant.values()).collect(ImmutableMap.toImmutableMap((variant) -> {
            return variant;
        }, (type) -> {
            return Pair.of(new ResourceLocation(Firmaciv.MOD_ID,
                            "textures/entity/watercraft/rowboat/" + type.getName() + ".png"),
                    new RowboatEntityModel());
        }));
    }

    @Override
    public void render(RowboatEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack poseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.4375D, 0.0D);
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

        Pair<ResourceLocation, RowboatEntityModel> pair = getModelWithLocation(pEntity);
        RowboatEntityModel rowboatEntityModel = pair.getSecond();

        poseStack.translate(0.0f, 1.0625f, 0f);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
        rowboatEntityModel.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(rowboatEntityModel.renderType(getTextureLocation(pEntity)));
        rowboatEntityModel.renderToBuffer(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F,
                1.0F, 1.0F, 1.0F);
        if (pEntity.getOars().getCount() >= 1) {
            rowboatEntityModel.getOarStarboard()
                    .render(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
        }
        if (pEntity.getOars().getCount() == 2) {
            rowboatEntityModel.getOarPort().render(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
        }
        if (!pEntity.isUnderWater()) {
            VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.waterMask());
            rowboatEntityModel.getWaterocclusion()
                    .render(poseStack, vertexconsumer1, pPackedLight, OverlayTexture.NO_OVERLAY);
        }

        poseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, poseStack, pBuffer, pPackedLight);
    }

    @Deprecated // forge: override getModelWithLocation to change the texture / model
    public ResourceLocation getTextureLocation(RowboatEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/rowboat/" + pEntity.getVariant() + ".png");
    }

    public Pair<ResourceLocation, RowboatEntityModel> getModelWithLocation(RowboatEntity rowboat) {
        return this.rowboatResources.get(rowboat.getVariant());
    }


}
