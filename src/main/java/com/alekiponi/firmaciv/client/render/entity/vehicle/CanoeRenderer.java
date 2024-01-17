package com.alekiponi.firmaciv.client.render.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.CanoeEntityModel;
import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
public class CanoeRenderer extends EntityRenderer<CanoeEntity> {

    public static final ResourceLocation DAMAGE_OVERLAY = new ResourceLocation(Firmaciv.MOD_ID,
            "textures/entity/watercraft/dugout_canoe/damage_overlay.png");
    protected final CanoeEntityModel canoeModel = new CanoeEntityModel();
    protected final ResourceLocation canoeTexture;

    /**
     * This is primarily for us as it hardcodes the Firmaciv namespace.
     * Use the constructor taking a {@link ResourceLocation} to provide a fully custom path
     *
     * @param woodName The name of the wood
     */
    public CanoeRenderer(final EntityRendererProvider.Context context, final String woodName) {
        this(context,
                new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/" + woodName + ".png"));
    }

    /**
     * Alternative constructor taking a resource location instead of a wood name
     *
     * @param canoeTexture The texture location. Must include file extension!
     */
    @SuppressWarnings("unused")
    public CanoeRenderer(final EntityRendererProvider.Context context, final ResourceLocation canoeTexture) {
        super(context);
        this.shadowRadius = 0.7F;
        this.canoeTexture = canoeTexture;
    }

    @Override
    public void render(final CanoeEntity canoeEntity, final float entityYaw, final float partialTicks,
            final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight) {

        poseStack.pushPose();
        poseStack.translate(0, 0.4375D, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(180 - entityYaw));

        poseStack.translate(0, 1.0625f, 0);
        poseStack.scale(-1, -1, 1);
        poseStack.mulPose(Axis.YP.rotationDegrees(0));

        this.canoeModel.setupAnim(canoeEntity, partialTicks, 0, -0.1F, 0, 0);
        final VertexConsumer vertexconsumer = bufferSource.getBuffer(
                this.canoeModel.renderType(getTextureLocation(canoeEntity)));

        if (canoeEntity.tickCount < 1) {
            poseStack.popPose();
            super.render(canoeEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
            return;
        }

        this.canoeModel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        if (canoeEntity.getLength() >= 4) {
            this.canoeModel.getMiddleMid().render(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }

        if (canoeEntity.getLength() == 5) {
            this.canoeModel.getMiddleStern().render(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            this.canoeModel.getBars().render(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }

        if (!canoeEntity.isUnderWater() && canoeEntity.getDamage() < canoeEntity.getDamageThreshold() * 0.9) {
            final VertexConsumer vertexconsumer1 = bufferSource.getBuffer(RenderType.waterMask());
            if (canoeEntity.getLength() == 3) {
                this.canoeModel.getWaterocclusion3()
                        .render(poseStack, vertexconsumer1, packedLight, OverlayTexture.NO_OVERLAY);
            } else if (canoeEntity.getLength() == 4) {
                this.canoeModel.getWaterocclusion4()
                        .render(poseStack, vertexconsumer1, packedLight, OverlayTexture.NO_OVERLAY);
            } else if (canoeEntity.getLength() == 5) {
                this.canoeModel.getWaterocclusion5()
                        .render(poseStack, vertexconsumer1, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }

        if (canoeEntity.getDamage() > 0) {
            final VertexConsumer damageVertexConsumer = bufferSource.getBuffer(
                    RenderType.entityTranslucent(DAMAGE_OVERLAY));
            float alpha = Mth.clamp((canoeEntity.getDamage() / (canoeEntity.getDamageThreshold())) * 0.75F, 0, 0.5F);
            this.canoeModel.renderToBuffer(poseStack, damageVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1,
                    1, 1, alpha);
        }

        poseStack.popPose();
        super.render(canoeEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Deprecated
    public ResourceLocation getTextureLocation(final CanoeEntity canoeEntity) {
        return this.canoeTexture;
    }
}