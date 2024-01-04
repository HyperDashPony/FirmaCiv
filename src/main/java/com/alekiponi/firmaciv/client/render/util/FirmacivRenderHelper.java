package com.alekiponi.firmaciv.client.render.util;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class FirmacivRenderHelper {
    public static void addVertexPair(VertexConsumer pConsumer, Matrix4f pMatrix, float p_174310_, float p_174311_,
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

    public static <E extends Entity> void renderRope(Entity pEntity, float pPartialTicks, PoseStack pPoseStack,
                                                     MultiBufferSource pBuffer, E pLeashHolder, int blockLightLevel) {
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
        int i = blockLightLevel;
        int j = pLeashHolder.level().getBrightness(LightLayer.BLOCK, blockpos);
        int k = pEntity.level().getBrightness(LightLayer.SKY, blockpos);
        int l = pEntity.level().getBrightness(LightLayer.SKY, blockpos1);

        for (int i1 = 0; i1 <= 24; ++i1) {
            FirmacivRenderHelper.addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false);
        }

        for (int j1 = 24; j1 >= 0; --j1) {
            FirmacivRenderHelper.addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
        }

        pPoseStack.popPose();
    }

    public static double[] getColorFromName(String name){
        switch (name) {
            case "white" -> {
                return new double[]{1,1,1};
            }
            case "orange" -> {
                return new double[]{
                        216.0/255.0,
                        137.0/255.0,
                        41.0/255.0};
            }
            case "magenta" -> {
                return new double[]{
                        201.0/255.0,
                        104.0/255.0,
                        195.0/255.0};
            }
            case "light_blue" -> {
                return new double[]{
                        141.0/255.0,
                        183.0/255.0,
                        241.0/255.0};
            }
            case "yellow" -> {
                return new double[]{
                        228.0/255.0,
                        228.0/255.0,
                        41.0/255.0};
            }
            case "lime" -> {
                return new double[]{
                        129.0/255.0,
                        209.0/255.0,
                        28.0/255.0};
            }
            case "pink" -> {
                return new double[]{
                        244.0/255.0,
                        178.0/255.0,
                        211.0/255.0};
            }
            case "gray" -> {
                return new double[]{0.33,0.33,0.33};
            }
            case "light_gray" -> {
                return new double[]{0.66,0.66,0.66};
            }
            case "cyan" -> {
                return new double[]{
                        59.0/255.0,
                        140.0/255.0,
                        74.0/255.0};
            }
            case "purple" -> {
                return new double[]{
                        128.0/255.0,
                        47.0/255.0,
                        176.0/255.0};
            }
            case "blue" -> {
                return new double[]{
                        51.0/255.0,
                        93.0/255.0,
                        193.0/255.0};
            }
            case "brown" -> {
                return new double[]{
                        121.0/255.0,
                        69.0/255.0,
                        33.0/255.0};
            }
            case "green" -> {
                return new double[]{
                        73.0/255.0,
                        106.0/255.0,
                        24.0/255.0};
            }
            case "red" -> {
                return new double[]{
                        207.0/255.0,
                        67.0/255.0,
                        62.0/255.0};
            }
            case "black" -> {
                return new double[]{0,0,0};
            }
        }
        return new double[]{0,0,0};
    }

}
