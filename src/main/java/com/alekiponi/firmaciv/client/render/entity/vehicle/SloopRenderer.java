package com.alekiponi.firmaciv.client.render.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.SloopEntityModel;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.dries007.tfc.util.Helpers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SloopRenderer extends EntityRenderer<SloopEntity> {

    public static final ResourceLocation DAMAGE_OVERLAY = new ResourceLocation(Firmaciv.MOD_ID,
            "textures/entity/watercraft/sloop/damage_overlay.png");
    public static final Map<DyeColor, ResourceLocation> SLOOP_PAINT_TEXTURES = Helpers.mapOfKeys(DyeColor.class,
            dyeColor -> new ResourceLocation(Firmaciv.MOD_ID,
                    "textures/entity/watercraft/sloop/paint/" + dyeColor.getSerializedName() + ".png"));
    public static final Map<DyeColor, ResourceLocation> SAIL_TEXTURES = Helpers.mapOfKeys(DyeColor.class,
            dyeColor -> new ResourceLocation(Firmaciv.MOD_ID,
                    "textures/entity/watercraft/sloop/dye/" + dyeColor.getSerializedName() + ".png"));
    protected final ResourceLocation sloopTexture;
    protected final SloopEntityModel sloopModel = new SloopEntityModel();

    /**
     * This is primarily for us as it hardcodes the Firmaciv namespace.
     * Use the constructor taking a {@link ResourceLocation} to provide a fully custom path
     *
     * @param woodName The name of the wood
     */
    public SloopRenderer(final EntityRendererProvider.Context context, final String woodName) {
        this(context, new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/sloop/" + woodName + ".png"));
    }

    /**
     * Alternative constructor taking a resource location instead of a wood name
     *
     * @param sloopTexture The texture location. Must include file extension!
     */
    public SloopRenderer(final EntityRendererProvider.Context context, final ResourceLocation sloopTexture) {
        super(context);
        this.shadowRadius = 0.8F;
        this.sloopTexture = sloopTexture;
    }

    @Override
    public void render(final SloopEntity sloopEntity, final float entityYaw, final float partialTicks,
            final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, 0.5, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(180 - entityYaw));

        poseStack.translate(0, 1.0625f, 0);
        poseStack.scale(-1, -1, 1);
        poseStack.mulPose(Axis.YP.rotationDegrees(0));

        if (sloopEntity.getDamage() > sloopEntity.getDamageThreshold()) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(sloopEntity.getId() % 30));
        }

        this.sloopModel.setupAnim(sloopEntity, partialTicks, 0, -0.1F, 0, 0);
        final VertexConsumer vertexconsumer = bufferSource.getBuffer(
                this.sloopModel.renderType(getTextureLocation(sloopEntity)));

        if (sloopEntity.tickCount < 1) {
            poseStack.popPose();
            super.render(sloopEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
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


        this.sloopModel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        if (sloopEntity.getMainsailActive()) {
            this.sloopModel.getMainsailDeployedParts()
                    .render(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            if (sloopEntity.getMainsailDye().isEmpty()) {
                this.sloopModel.getMainsail()
                        .render(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
        } else {
            this.sloopModel.getMainsailFurledParts()
                    .render(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }

        if (sloopEntity.getJibsailDye().isEmpty()) {
            if (sloopEntity.getJibsailActive()) {

                this.sloopModel.getJibsail()
                        .render(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

            } else {
                this.sloopModel.getJibsailFurled()
                        .render(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
        }
        if (!sloopEntity.isUnderWater() && sloopEntity.getDamage() < sloopEntity.getDamageThreshold() * 0.9) {
            final VertexConsumer vertexconsumer1 = bufferSource.getBuffer(RenderType.waterMask());
            this.sloopModel.getWaterocclusion()
                    .render(poseStack, vertexconsumer1, packedLight, OverlayTexture.NO_OVERLAY);
        }

        if (!sloopEntity.getMainsailDye().isEmpty() && sloopEntity.getDyeColor(0) != null) {
            final VertexConsumer mainsailVertexConsumer = bufferSource.getBuffer(
                    RenderType.entityCutout(getMainsailTexture(sloopEntity)));
            if (sloopEntity.getMainsailActive()) {
                this.sloopModel.getMainsailDeployedParts()
                        .render(poseStack, mainsailVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                this.sloopModel.getMainsail()
                        .render(poseStack, mainsailVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            } else {
                this.sloopModel.getMainsailFurledParts()
                        .render(poseStack, mainsailVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
        }

        if (!sloopEntity.getJibsailDye().isEmpty() && sloopEntity.getDyeColor(1) != null) {
            final VertexConsumer jibsailVertexConsumer = bufferSource.getBuffer(
                    RenderType.entityCutout(getJibsailTexture(sloopEntity)));
            if (sloopEntity.getJibsailActive()) {
                this.sloopModel.getJibsail()
                        .render(poseStack, jibsailVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            } else {
                this.sloopModel.getJibsailFurled()
                        .render(poseStack, jibsailVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
        }

        if (!sloopEntity.getPaint().isEmpty() && sloopEntity.getPaintColor() != null) {
            final VertexConsumer paintVertexConsumer = bufferSource.getBuffer(
                    RenderType.entityTranslucent(getPaintTexture(sloopEntity)));
            this.sloopModel.renderToBuffer(poseStack, paintVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1,
                    1, 0.9F);
        }

        if (sloopEntity.getDamage() > 0) {
            final VertexConsumer damageVertexConsumer = bufferSource.getBuffer(
                    RenderType.entityTranslucent(DAMAGE_OVERLAY));
            final float alpha = Mth.clamp((sloopEntity.getDamage() / (sloopEntity.getDamageThreshold())) * 0.75f, 0,
                    0.75f);
            this.sloopModel.renderToBuffer(poseStack, damageVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1,
                    1, 1, alpha);
        }

        poseStack.popPose();
        super.render(sloopEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(final SloopEntity sloopEntity) {
        return this.sloopTexture;
    }

    public ResourceLocation getMainsailTexture(final SloopEntity sloopEntity) {
        return SAIL_TEXTURES.get(sloopEntity.getDyeColor(0));
    }

    public ResourceLocation getJibsailTexture(final SloopEntity sloopEntity) {
        return SAIL_TEXTURES.get(sloopEntity.getDyeColor(1));
    }

    public ResourceLocation getPaintTexture(final SloopEntity sloopEntity) {
        return SLOOP_PAINT_TEXTURES.get(sloopEntity.getPaintColor());
    }
}