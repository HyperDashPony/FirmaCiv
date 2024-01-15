package com.alekiponi.firmaciv.client.render.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.SloopEntityModel;
import com.alekiponi.firmaciv.common.block.FirmacivBlocks;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopConstructionEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.alekiponi.firmaciv.util.BoatVariant;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class SloopConstructionRenderer extends EntityRenderer<SloopConstructionEntity> {

    private final Map<BoatVariant, Pair<ResourceLocation, SloopEntityModel>> sloopResources;

    private final BlockRenderDispatcher blockRenderer;

    public SloopConstructionRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.8f;
        this.blockRenderer = pContext.getBlockRenderDispatcher();
        this.sloopResources = Stream.of(BoatVariant.values()).collect(ImmutableMap.toImmutableMap((variant) -> {
            return variant;
        }, (type) -> {
            return Pair.of(new ResourceLocation(Firmaciv.MOD_ID,
                            "textures/entity/watercraft/sloop_construction/" + type.getName() + ".png"),
                    new SloopEntityModel());
        }));
    }

    @Override
    public ResourceLocation getTextureLocation(SloopConstructionEntity pEntity) {
        return null;
    }

    @Override
    public void render(SloopConstructionEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack poseStack,
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
        //sloopModel.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(sloopModel.renderType(resourcelocation));
        if (pEntity.tickCount < 1) {
            poseStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, poseStack, pBuffer, pPackedLight);
            return;
        }

        sloopModel.renderToBuffer(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                1.0F, 1.0F);

        BlockState blockstate = FirmacivBlocks.BOAT_FRAME_ANGLED.get().defaultBlockState();
        this.blockRenderer.renderSingleBlock(blockstate, poseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY);
        //render scaffolding blocks

        if(pEntity.getDamage() > 0){
            VertexConsumer damageVertexConsumer = pBuffer.getBuffer(RenderType.entityTranslucent(getDamageTexture(pEntity)));
            float alpha = Mth.clamp((pEntity.getDamage()/(pEntity.getDamageThreshold()))*0.75f, 0, 0.75f);
            sloopModel.renderToBuffer(poseStack, damageVertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);
        }

        poseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, poseStack, pBuffer, pPackedLight);
    }

    public Pair<ResourceLocation, SloopEntityModel> getModelWithLocation(SloopConstructionEntity sloop) {
        return this.sloopResources.get(sloop.getVariant());
    }

    public ResourceLocation getDamageTexture(SloopEntity pEntity) {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/sloop/" + "damage_overlay" + ".png");
    }


}
