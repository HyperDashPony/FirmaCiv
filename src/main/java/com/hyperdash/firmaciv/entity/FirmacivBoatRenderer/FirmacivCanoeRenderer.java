package com.hyperdash.firmaciv.entity.FirmacivBoatRenderer;

import com.google.common.collect.ImmutableMap;
import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.custom.BoatVariant;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import com.hyperdash.firmaciv.entity.custom.entitymodel.CanoeEntityModel;
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
public class FirmacivCanoeRenderer extends EntityRenderer<CanoeEntity> {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final Map<BoatVariant, Pair<ResourceLocation, CanoeEntityModel>> canoeResources;

    private static final ResourceLocation DOUGLAS_FIR =
            new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/douglas_fir.png");
    private static final ResourceLocation PINE =
            new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/pine.png");
    private static final ResourceLocation CHESTNUT =
            new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/chestnut.png");
    private static final ResourceLocation SPRUCE =
            new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/spruce.png");
    private static final ResourceLocation ASPEN =
            new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/aspen.png");
    private static final ResourceLocation KAPOK =
            new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/kapok.png");
    private static final ResourceLocation ROSEWOOD =
            new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/rosewood.png");
    private static final ResourceLocation WHITE_CEDAR =
            new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/white_cedar.png");
    private static final ResourceLocation WILLOW =
            new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/douglas_fir.png");

    public FirmacivCanoeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.7f;
        this.canoeResources = Stream.of(BoatVariant.values()).collect(ImmutableMap.toImmutableMap((variant) -> {
            return variant;
        }, (type) -> {
            return Pair.of(new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/" + type.getName() + ".png"),
                    new CanoeEntityModel());
        }));
    }

    @Override
    public void render(CanoeEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, 0.4375D, 0.0D);
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw));
        float f = (float)pEntity.getHurtTime() - pPartialTicks;
        float f1 = pEntity.getDamage() - pPartialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            pMatrixStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)pEntity.getHurtDir()));
        }

        float f2 = pEntity.getBubbleAngle(pPartialTicks);
        if (!Mth.equal(f2, 0.0F)) {
            pMatrixStack.mulPose((new Quaternionf()).setAngleAxis(pEntity.getBubbleAngle(pPartialTicks) * ((float)Math.PI / 180F), 1.0F, 0.0F, 1.0F));
        }

        Pair<ResourceLocation, CanoeEntityModel> pair = getModelWithLocation(pEntity);
        ResourceLocation resourcelocation = pair.getFirst();
        CanoeEntityModel canoeModel = pair.getSecond();

        pMatrixStack.translate(0.0f, 1.0625f, 0f);
        pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(0.0F));
        canoeModel.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(canoeModel.renderType(getTextureLocation(pEntity)));
        canoeModel.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        if (!pEntity.isUnderWater()) {
            VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.waterMask());
            canoeModel.getWaterocclusion().render(pMatrixStack, vertexconsumer1, pPackedLight, OverlayTexture.NO_OVERLAY);
        }

        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Deprecated // forge: override getModelWithLocation to change the texture / model
    public ResourceLocation getTextureLocation(CanoeEntity pEntity) {
        int id = pEntity.getId();
        ResourceLocation resloc = pEntity.getTextureLocation();
        resloc = new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/" + pEntity.getVariant() + ".png");
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/" + pEntity.getVariant() + ".png");
    }

    public Pair<ResourceLocation, CanoeEntityModel> getModelWithLocation(CanoeEntity canoe) { return this.canoeResources.get(canoe.getVariant()); }


}
