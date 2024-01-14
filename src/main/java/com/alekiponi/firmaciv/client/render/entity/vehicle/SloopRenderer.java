package com.alekiponi.firmaciv.client.render.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.RowboatEntityModel;
import com.alekiponi.firmaciv.client.model.entity.SloopEntityModel;
import com.alekiponi.firmaciv.common.entity.vehicle.RowboatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.alekiponi.firmaciv.util.BoatVariant;
import com.google.common.collect.ImmutableMap;
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

import java.util.Map;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class SloopRenderer extends EntityRenderer<SloopEntity> {

    private final Map<BoatVariant, Pair<ResourceLocation, SloopEntityModel>> sloopResources;

    public SloopRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.8f;
        this.sloopResources = Stream.of(BoatVariant.values()).collect(ImmutableMap.toImmutableMap((variant) -> {
            return variant;
        }, (type) -> {
            return Pair.of(new ResourceLocation(Firmaciv.MOD_ID,
                            "textures/entity/watercraft/sloop/" + type.getName() + ".png"),
                    new SloopEntityModel());
        }));
    }

    @Override
    public void render(SloopEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack poseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.5D, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw));

        Pair<ResourceLocation, SloopEntityModel> pair = getModelWithLocation(pEntity);
        ResourceLocation resourcelocation = pair.getFirst();
        SloopEntityModel sloopModel = pair.getSecond();

        poseStack.translate(0.0f, 1.0625f, 0f);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
        if(pEntity.getDamage() > pEntity.getDamageThreshold()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(pEntity.getId()%30));
        }
        sloopModel.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(sloopModel.renderType(resourcelocation));
        if (pEntity.tickCount < 1) {
            poseStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, poseStack, pBuffer, pPackedLight);
            return;
        }

        //TODO this is setup code for distance sail LODs

        //TODO get the player from the minecraft instance instead

        //TODO also cancel animation when LOD is triggered
        /*
        if(this.level().isClientSide()){
            Player nearestPlayer = this.level().getNearestPlayer(this, 32*16);
            if(nearestPlayer != null){
                float distance = this.distanceTo(nearestPlayer);
                if(distance > 4*16){
                    //render the simple sail instead
                }
            }
        }*/


        sloopModel.renderToBuffer(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                1.0F, 1.0F);

        if (pEntity.getMainsailActive()) {
            sloopModel.getMainsailDeployedParts().render(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                    1.0F, 1.0F);
            if (pEntity.getMainsailDye().isEmpty()) {
                sloopModel.getMainsail().render(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                        1.0F, 1.0F);
            }
        } else {
            sloopModel.getMainsailFurledParts().render(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        if (pEntity.getJibsailDye().isEmpty()) {
            if (pEntity.getJibsailActive()) {

                sloopModel.getJibsail()
                        .render(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                                1.0F, 1.0F);

            } else {
                sloopModel.getJibsailFurled()
                        .render(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                                1.0F, 1.0F);
            }
        }
        if (!pEntity.isUnderWater() && pEntity.getDamage() < pEntity.getDamageThreshold()*0.9) {
            VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.waterMask());
            sloopModel.getWaterocclusion().render(poseStack, vertexconsumer1, pPackedLight, OverlayTexture.NO_OVERLAY);
        }

        if (!pEntity.getMainsailDye().isEmpty() && pEntity.getDyeColor(0) != null) {
            VertexConsumer mainsailVertexConsumer = pBuffer.getBuffer(RenderType.entityCutout(getMainsailTexture(pEntity)));
            if (pEntity.getMainsailActive()) {
                sloopModel.getMainsailDeployedParts().render(poseStack, mainsailVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                        1.0F, 1.0F);
                sloopModel.getMainsail().render(poseStack, mainsailVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                        1.0F, 1.0F);
            } else {
                sloopModel.getMainsailFurledParts().render(poseStack, mainsailVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

        if (!pEntity.getJibsailDye().isEmpty() && pEntity.getDyeColor(1) != null) {
            VertexConsumer jibsailVertexConsumer = pBuffer.getBuffer(RenderType.entityCutout(getJibsailTexture(pEntity)));
            if (pEntity.getJibsailActive()) {
                sloopModel.getJibsail().render(poseStack, jibsailVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            } else {
                sloopModel.getJibsailFurled().render(poseStack, jibsailVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

        if (!pEntity.getPaint().isEmpty() && pEntity.getPaintColor() != null) {
            VertexConsumer paintVertexConsumer = pBuffer.getBuffer(RenderType.entityTranslucent(getPaintTexture(pEntity)));
            sloopModel.renderToBuffer(poseStack, paintVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.9F);
        }

        if(pEntity.getDamage() > 0){
            VertexConsumer damageVertexConsumer = pBuffer.getBuffer(RenderType.entityTranslucent(getDamageTexture(pEntity)));
            float alpha = Mth.clamp((pEntity.getDamage()/(pEntity.getDamageThreshold()))*0.75f, 0, 0.75f);
            sloopModel.renderToBuffer(poseStack, damageVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);
        }

        poseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, poseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SloopEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/rowboat/" + pEntity.getVariant() + ".png");
    }

    public ResourceLocation getMainsailTexture(SloopEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/sloop/dye/" + pEntity.getDyeColor(0) + ".png");
    }

    public ResourceLocation getJibsailTexture(SloopEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/sloop/dye/" + pEntity.getDyeColor(1) + ".png");
    }

    public ResourceLocation getPaintTexture(SloopEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/sloop/paint/" + pEntity.getPaintColor() + ".png");
    }

    public Pair<ResourceLocation, SloopEntityModel> getModelWithLocation(SloopEntity sloop) {
        return this.sloopResources.get(sloop.getVariant());
    }

    public ResourceLocation getDamageTexture(SloopEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/sloop/" + "damage_overlay" + ".png");
    }

}
