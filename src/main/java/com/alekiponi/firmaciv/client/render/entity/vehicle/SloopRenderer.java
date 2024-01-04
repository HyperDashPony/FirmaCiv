package com.alekiponi.firmaciv.client.render.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.SloopEntityModel;
import com.alekiponi.firmaciv.client.render.util.FirmacivRenderHelper;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.dries007.tfc.client.RenderHelpers;
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

        Pair<ResourceLocation, SloopEntityModel> pair = sloopResources;
        ResourceLocation resourcelocation = pair.getFirst();
        SloopEntityModel sloopModel = pair.getSecond();

        poseStack.translate(0.0f, 1.0625f, 0f);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
        sloopModel.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(sloopModel.renderType(resourcelocation));
        if(pEntity.tickCount < 1){
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

        if(pEntity.getMainsailActive()){
            if(!pEntity.getPaint().isEmpty() && pEntity.getPaintColor() != null){
                double[] color = FirmacivRenderHelper.getColorFromName(pEntity.getPaintColor().getName());
                double factor = 1.0;
                if(color[0] == color[1] && color[1] == color[2]){
                    factor = 0.9;
                }
                double invFactor = 1-factor;
                color[0]*=factor;
                color[1]*=factor;
                color[2]*=factor;
                color[0]+=invFactor;
                color[1]+=invFactor;
                color[2]+=invFactor;
                sloopModel.getMainsailMain()
                        .render(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY,(float)color[0], (float)color[1],
                                (float)color[2], 1.0F);
            } else {
                sloopModel.getMainsailMain()
                        .render(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY,1.0F, 1.0F,
                                1.0F, 1.0F);
            }

        }
        if(pEntity.getJibsailActive()){
            sloopModel.getJibsailMain()
                    .render(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY,1.0F, 1.0F,
                            1.0F, 1.0F);
        }

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
