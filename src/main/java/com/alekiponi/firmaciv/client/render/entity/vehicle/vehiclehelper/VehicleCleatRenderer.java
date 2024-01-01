package com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.RowboatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.LeashKnotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class VehicleCleatRenderer extends EntityRenderer<VehicleCleatEntity> {

    private static final ResourceLocation KNOT_LOCATION = new ResourceLocation("textures/entity/lead_knot.png");
    private final LeashKnotModel<VehicleCleatEntity> model;

    public VehicleCleatRenderer(EntityRendererProvider.Context pContext) {

        super(pContext);
        this.model = new LeashKnotModel<VehicleCleatEntity>(pContext.bakeLayer(ModelLayers.LEASH_KNOT));
    }

    private static void addVertexPair(VertexConsumer pConsumer, Matrix4f pMatrix, float p_174310_, float p_174311_,
                                      float p_174312_, int pEntityBlockLightLevel, int pLeashHolderBlockLightLevel,
                                      int pEntitySkyLightLevel, int pLeashHolderSkyLightLevel, float p_174317_,
                                      float p_174318_, float p_174319_, float p_174320_, int pIndex,
                                      boolean p_174322_) {
        float f = (float) pIndex / 24.0F;
        int i = (int) Mth.lerp(f, (float) pEntityBlockLightLevel, (float) pLeashHolderBlockLightLevel);
        int j = (int) Mth.lerp(f, (float) pEntitySkyLightLevel, (float) pLeashHolderSkyLightLevel);
        int k = LightTexture.pack(i, j);
        float f1 = pIndex % 2 == (p_174322_ ? 1 : 0) ? 0.7F : 1.0F;
        float f2 = 0.5F * f1;
        float f3 = 0.4F * f1;
        float f4 = 0.3F * f1;
        float f5 = p_174310_ * f;
        float f6 = p_174311_ > 0.0F ? p_174311_ * f * f : p_174311_ - p_174311_ * (1.0F - f) * (1.0F - f);
        float f7 = p_174312_ * f;
        pConsumer.vertex(pMatrix, f5 - p_174319_, f6 + p_174318_, f7 + p_174320_).color(f2, f3, f4, 1.0F).uv2(k)
                .endVertex();
        pConsumer.vertex(pMatrix, f5 + p_174319_, f6 + p_174317_ - p_174318_, f7 - p_174320_).color(f2, f3, f4, 1.0F)
                .uv2(k).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(VehicleCleatEntity pEntity) {
        return null;
    }

    public void render(VehicleCleatEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        Entity entity = pEntity.getLeashHolder();
        if (entity != null) {
            float rotation = 0f;
            if (pEntity.getVehicle().getVehicle() instanceof AbstractFirmacivBoatEntity trueVehicle) {
                rotation = trueVehicle.getYRot();
            } else {
                rotation = pEntityYaw;
            }
            pPoseStack.pushPose();
            pPoseStack.scale(0.8F, 0.8F, 0.8F);
            pPoseStack.translate(-0.25f, 0.35f, 0.0f);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(90));
            if (pEntity.getVehicle().isPassenger() && pEntity.getVehicle().getVehicle() instanceof RowboatEntity) {
                //pPoseStack.scale(-0.8F, -0.8F, 0.8F);
                //pPoseStack.mulPose(Axis.YP.rotationDegrees(rotation));
            } else if(pEntity.getVehicle().isPassenger() && pEntity.getVehicle().getVehicle() instanceof CanoeEntity){
                pPoseStack.scale(0.8F, 0.8F, 0.8F);
                pPoseStack.translate(0f, -0.0625f, 0.0f);
            }
            this.model.setupAnim(pEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(KNOT_LOCATION));
            this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                    1.0F, 1.0F);
            pPoseStack.popPose();
            this.renderLeash(pEntity, pPartialTicks, pPoseStack, pBuffer, entity);
        }
    }

    private <E extends Entity> void renderLeash(VehicleCleatEntity pEntity, float pPartialTicks, PoseStack pPoseStack,
                                                MultiBufferSource pBuffer, E pLeashHolder) {
        pPoseStack.pushPose();
        Vec3 vec3 = pLeashHolder.getRopeHoldPosition(pPartialTicks);
        double d0 = (double) (Mth.lerp(pPartialTicks, pEntity.getYRot(),
                pEntity.getYRot()) * ((float) Math.PI / 180F)) + (Math.PI / 2D);
        Vec3 vec31 = pEntity.getLeashOffset(pPartialTicks);
        double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
        double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
        double d3 = Mth.lerp(pPartialTicks, pEntity.xo, pEntity.getX()) + d1;
        double d4 = Mth.lerp(pPartialTicks, pEntity.yo, pEntity.getY()) + vec31.y;
        double d5 = Mth.lerp(pPartialTicks, pEntity.zo, pEntity.getZ()) + d2;
        pPoseStack.translate(d1, vec31.y, d2);
        float f = (float) (vec3.x - d3);
        float f1 = (float) (vec3.y - d4);
        float f2 = (float) (vec3.z - d5);
        float f3 = 0.025F;
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.leash());
        Matrix4f matrix4f = pPoseStack.last().pose();
        float f4 = Mth.invSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        BlockPos blockpos = BlockPos.containing(pEntity.getEyePosition(pPartialTicks));
        BlockPos blockpos1 = BlockPos.containing(pLeashHolder.getEyePosition(pPartialTicks));
        int i = this.getBlockLightLevel(pEntity, blockpos);
        int j = pLeashHolder.level().getBrightness(LightLayer.BLOCK, blockpos);
        int k = pEntity.level().getBrightness(LightLayer.SKY, blockpos);
        int l = pEntity.level().getBrightness(LightLayer.SKY, blockpos1);

        for (int i1 = 0; i1 <= 24; ++i1) {
            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false);
        }

        for (int j1 = 24; j1 >= 0; --j1) {
            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
        }

        pPoseStack.popPose();
    }
}
