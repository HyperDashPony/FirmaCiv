package com.alekiponi.firmaciv.client.model.entity;

// Made with Blockbench 4.9.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.alekiponi.firmaciv.common.entity.CannonEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.util.stream.Collectors;

public class CannonEntityModel<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "canon"), "main");
    private final ModelPart barrel;
    private final ModelPart base;

    public CannonEntityModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.barrel = root.getChild("barrel");
        this.base = root.getChild("base");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition barrel = partdefinition.addOrReplaceChild("barrel", CubeListBuilder.create().texOffs(0, 51).addBox(-2.5F, -5.4164F, -6.8127F, 5.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(48, 26).addBox(1.0F, -3.924F, -15.7258F, 1.0F, 3.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -4.924F, -15.7258F, 3.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-1.0F, -1.924F, -15.7258F, 2.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(50, 0).addBox(-2.0F, -4.924F, -15.7258F, 1.0F, 4.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.1357F, 1.3931F));

        //PartDefinition fuse_lit = barrel.addOrReplaceChild("fuse_lit", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -4.8857F, 4.1069F, 0.3491F, 0.0F, 0.0F));

        //PartDefinition cube_r1 = fuse_lit.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(40, 75).addBox(-0.5F, -3.2204F, -0.7705F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5672F, 0.0F, 0.0F));

        PartDefinition fuse_unlit = barrel.addOrReplaceChild("fuse_unlit", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -6.1357F, 4.1069F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r2 = fuse_unlit.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(46, 75).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5672F, 0.0F, 0.0F));

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(64, 51).addBox(-3.5F, -2.99F, -9.0F, 8.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(-3.5F, -1.99F, -7.0F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(62, 68).addBox(-3.5F, -4.99F, -8.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(50, 68).addBox(3.5F, -4.99F, -8.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(30, 75).addBox(3.5F, -1.5F, -10.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(20, 75).addBox(3.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 75).addBox(-4.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 75).addBox(-4.5F, -1.5F, -10.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(34, 51).addBox(-1.5F, -2.0F, -10.0F, 4.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 22.5F, 4.5F));

        PartDefinition cube_r3 = base.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(26, 68).addBox(-5.5F, -0.5F, -0.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r4 = base.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(26, 70).addBox(-5.5F, -0.5F, -0.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, -9.0F, -1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    private static void animateSailforceIndicator(CannonEntity cannon, float pPartialTicks, ModelPart barrel){
        float barrelAngle = (float) Math.toRadians(cannon.getXRot());
        barrel.xRot = barrelAngle;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        animateSailforceIndicator((CannonEntity)(entity), limbSwingAmount, barrel);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        barrel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
