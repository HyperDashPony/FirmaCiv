package com.alekiponi.firmaciv.client.render.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.SloopEntityModel;
import com.alekiponi.firmaciv.common.block.FirmacivBlocks;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopConstructionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

@OnlyIn(Dist.CLIENT)
public class SloopConstructionRenderer extends EntityRenderer<SloopConstructionEntity> {

    protected final SloopEntityModel sloopModel = new SloopEntityModel();
    protected final ResourceLocation sloopTexture;
    private final BlockRenderDispatcher blockRenderer;

    /**
     * This is primarily for us as it hardcodes the Firmaciv namespace.
     * Use the constructor taking a {@link ResourceLocation} to provide a fully custom path
     *
     * @param woodName The name of the wood
     */
    public SloopConstructionRenderer(final EntityRendererProvider.Context context, final String woodName) {
        this(context, new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/sloop_construction/" + woodName + ".png"));
    }

    /**
     * Alternative constructor taking a resource location instead of a wood name
     *
     * @param sloopTexture The texture location. Must include file extension!
     */
    public SloopConstructionRenderer(final EntityRendererProvider.Context context,
            final ResourceLocation sloopTexture) {
        super(context);
        this.shadowRadius = 0.8F;
        this.sloopTexture = sloopTexture;
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(final SloopConstructionEntity constructionEntity, final float entityYaw,
            final float partialTicks, final PoseStack poseStack, final MultiBufferSource bufferSource,
            final int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, 0.5, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(180 - entityYaw));

        poseStack.translate(0, 1.0625F, 0);
        poseStack.scale(-1, -1, 1);
        poseStack.mulPose(Axis.YP.rotationDegrees(0));
        if (constructionEntity.getDamage() > constructionEntity.getDamageThreshold()) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(constructionEntity.getId() % 30));
        }
        //sloopModel.setupAnim(constructionEntity, partialTicks, 0, -0.1F, 0, 0);
        final VertexConsumer vertexConsumer = bufferSource.getBuffer(
                this.sloopModel.renderType(getTextureLocation(constructionEntity)));
        if (constructionEntity.tickCount < 1) {
            poseStack.popPose();
            super.render(constructionEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
            return;
        }

        this.sloopModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        poseStack.pushPose();
        poseStack.translate(0, 0.5, 2.25);
        BlockState blockstate = FirmacivBlocks.BOAT_FRAME_ANGLED.get().defaultBlockState();
        this.blockRenderer.renderSingleBlock(blockstate, poseStack, bufferSource, packedLight,
                OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(-1, 0.5, 2.25);
        blockstate = FirmacivBlocks.BOAT_FRAME_ANGLED.get().defaultBlockState();
        this.blockRenderer.renderSingleBlock(blockstate, poseStack, bufferSource, packedLight,
                OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        //render scaffolding blocks

        if (constructionEntity.getDamage() > 0) {
            final VertexConsumer damageVertexConsumer = bufferSource.getBuffer(
                    RenderType.entityTranslucent(SloopRenderer.DAMAGE_OVERLAY));
            final float alpha = Mth.clamp(
                    (constructionEntity.getDamage() / (constructionEntity.getDamageThreshold())) * 0.75f, 0, 0.75f);
            this.sloopModel.renderToBuffer(poseStack, damageVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1,
                    1, 1, alpha);
        }

        poseStack.popPose();
        super.render(constructionEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(final SloopConstructionEntity constructionEntity) {
        return this.sloopTexture;
    }
}