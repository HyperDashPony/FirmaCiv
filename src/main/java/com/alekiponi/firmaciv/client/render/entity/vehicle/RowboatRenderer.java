package com.alekiponi.firmaciv.client.render.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.RowboatEntityModel;
import com.alekiponi.firmaciv.common.entity.vehicle.RowboatEntity;
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
public class RowboatRenderer extends EntityRenderer<RowboatEntity> {

    public static final ResourceLocation DAMAGE_OVERLAY = new ResourceLocation(Firmaciv.MOD_ID,
            "textures/entity/watercraft/rowboat/damage_overlay.png");
    public static final Map<DyeColor, ResourceLocation> PAINT_TEXTURES = Helpers.mapOfKeys(DyeColor.class,
            dyeColor -> new ResourceLocation(Firmaciv.MOD_ID,
                    "textures/entity/watercraft/rowboat/paint/" + dyeColor.getSerializedName() + ".png"));
    protected final RowboatEntityModel rowboatModel = new RowboatEntityModel();
    protected final ResourceLocation rowboatTexture;

    /**
     * This is primarily for us as it hardcodes the Firmaciv namespace.
     * Use the constructor taking a {@link ResourceLocation} to provide a fully custom path
     *
     * @param woodName The name of the wood
     */
    public RowboatRenderer(final EntityRendererProvider.Context context, final String woodName) {
        this(context, new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/rowboat/" + woodName + ".png"));
    }

    /**
     * Alternative constructor taking a resource location instead of a wood name
     *
     * @param rowboatTexture The texture location. Must include file extension!
     */
    public RowboatRenderer(final EntityRendererProvider.Context context, final ResourceLocation rowboatTexture) {
        super(context);
        this.shadowRadius = 1;
        this.rowboatTexture = rowboatTexture;
    }

    @Override
    public void render(final RowboatEntity rowboatEntity, final float entityYaw, final float partialTicks,
            final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, 0.4375D, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(180 - entityYaw));

        poseStack.translate(0, 1.0625f, 0);
        poseStack.scale(-1, -1, 1);
        poseStack.mulPose(Axis.YP.rotationDegrees(0));

        if (rowboatEntity.getDamage() > rowboatEntity.getDamageThreshold()) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(rowboatEntity.getId() % 30));
        }

        this.rowboatModel.setupAnim(rowboatEntity, partialTicks, 0, -0.1F, 0, 0);

        final VertexConsumer baseVertexConsumer = bufferSource.getBuffer(
                this.rowboatModel.renderType(getTextureLocation(rowboatEntity)));

        if (rowboatEntity.tickCount < 1) {
            poseStack.popPose();
            super.render(rowboatEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
            return;
        }

        this.rowboatModel.renderToBuffer(poseStack, baseVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1,
                1);

        if (1 <= rowboatEntity.getOars().getCount()) {
            this.rowboatModel.getOarStarboard()
                    .render(poseStack, baseVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }

        if (2 == rowboatEntity.getOars().getCount()) {
            this.rowboatModel.getOarPort()
                    .render(poseStack, baseVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }

        if (!rowboatEntity.isUnderWater() && rowboatEntity.getDamage() < rowboatEntity.getDamageThreshold() * 0.9) {
            final VertexConsumer waterMaskVertexConsumer = bufferSource.getBuffer(RenderType.waterMask());
            this.rowboatModel.getWaterocclusion()
                    .render(poseStack, waterMaskVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }

        if (!rowboatEntity.getPaint().isEmpty() && rowboatEntity.getPaintColor() != null) {
            final VertexConsumer paintVertexConsumer = bufferSource.getBuffer(
                    RenderType.entityTranslucent(getPaintTexture(rowboatEntity)));
            this.rowboatModel.renderToBuffer(poseStack, paintVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1,
                    1, 1, 1);
        }

        if (0 < rowboatEntity.getDamage()) {
            final VertexConsumer damageVertexConsumer = bufferSource.getBuffer(
                    RenderType.entityTranslucent(DAMAGE_OVERLAY));
            float alpha = Mth.clamp((rowboatEntity.getDamage() / (rowboatEntity.getDamageThreshold())) * 0.75f, 0,
                    0.5f);
            this.rowboatModel.renderToBuffer(poseStack, damageVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1,
                    1, 1, alpha);
        }


        poseStack.popPose();
        super.render(rowboatEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(final RowboatEntity rowboatEntity) {
        return this.rowboatTexture;
    }

    public ResourceLocation getPaintTexture(final RowboatEntity rowboatEntity) {
        return PAINT_TEXTURES.get(rowboatEntity.getPaintColor());
    }
}