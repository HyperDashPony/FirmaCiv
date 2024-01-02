package com.alekiponi.firmaciv.client.render.entity;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.RowboatEntityModel;
import com.alekiponi.firmaciv.util.BoatVariant;
import com.alekiponi.firmaciv.common.entity.vehicle.RowboatEntity;
import com.google.common.collect.ImmutableMap;
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
import org.slf4j.Logger;

import java.util.Map;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class RowboatRenderer extends EntityRenderer<RowboatEntity> {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final Map<BoatVariant, Pair<ResourceLocation, RowboatEntityModel>> rowboatResources;

    private final RowboatEntityModel paintModel = new RowboatEntityModel();

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

        Pair<ResourceLocation, RowboatEntityModel> pair = getModelWithLocation(pEntity);
        RowboatEntityModel rowboatEntityModel = pair.getSecond();

        poseStack.translate(0.0f, 1.0625f, 0f);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
        if(pEntity.getDamage() > pEntity.getDamageThreshold()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(20));
        }
        rowboatEntityModel.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer baseVertexConsumer = pBuffer.getBuffer(rowboatEntityModel.renderType(getTextureLocation(pEntity)));

        if(pEntity.tickCount < 1){
            poseStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, poseStack, pBuffer, pPackedLight);
            return;
        }
        rowboatEntityModel.renderToBuffer(poseStack, baseVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F,
                1.0F, 1.0F, 1.0F);

        if (pEntity.getOars().getCount() >= 1) {
            rowboatEntityModel.getOarStarboard()
                    .render(poseStack, baseVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
        }
        if (pEntity.getOars().getCount() == 2) {
            rowboatEntityModel.getOarPort().render(poseStack, baseVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
        }
        if (!pEntity.isUnderWater()) {
            VertexConsumer waterMaskVertexConsumer = pBuffer.getBuffer(RenderType.waterMask());
            rowboatEntityModel.getWaterocclusion()
                    .render(poseStack, waterMaskVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
        }
        if(!pEntity.getPaint().isEmpty() && pEntity.getPaintColor() != null){
            VertexConsumer paintVertexConsumer = pBuffer.getBuffer(RenderType.entityTranslucent(getPaintTexture(pEntity)));
            rowboatEntityModel.renderToBuffer(poseStack, paintVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        if(pEntity.getDamage() > 0){
            VertexConsumer damageVertexConsumer = pBuffer.getBuffer(RenderType.entityTranslucent(getDamageTexture(pEntity)));
            float alpha = Mth.clamp((pEntity.getDamage()/(pEntity.getDamageThreshold()))*0.75f, 0, 0.5f);
            rowboatEntityModel.renderToBuffer(poseStack, damageVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);
        }



        poseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, poseStack, pBuffer, pPackedLight);
    }

    @Deprecated // forge: override getModelWithLocation to change the texture / model
    public ResourceLocation getTextureLocation(RowboatEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/rowboat/" + pEntity.getVariant() + ".png");
    }

    public ResourceLocation getPaintTexture(RowboatEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/rowboat/paint/" + pEntity.getPaintColor() + ".png");
    }

    public ResourceLocation getDamageTexture(RowboatEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/rowboat/" + "damage_overlay" + ".png");
    }

    public Pair<ResourceLocation, RowboatEntityModel> getModelWithLocation(RowboatEntity rowboat) {
        return this.rowboatResources.get(rowboat.getVariant());
    }


}
