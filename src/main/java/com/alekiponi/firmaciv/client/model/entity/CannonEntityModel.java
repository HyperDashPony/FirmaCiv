package com.alekiponi.firmaciv.client.model.entity;

// Made with Blockbench 4.9.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

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

        PartDefinition barrel = partdefinition.addOrReplaceChild("barrel", CubeListBuilder.create(), PartPose.offset(0.1F, 17.1357F, -2.6069F));

        PartDefinition cube_r1 = barrel.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(50, 0).addBox(-2.0F, -2.5F, -14.5F, 1.0F, 4.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-1.0F, 0.5F, -14.5F, 2.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -2.5F, -14.5F, 3.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(48, 26).addBox(1.0F, -1.5F, -14.5F, 1.0F, 3.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.8643F, 2.6069F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r2 = barrel.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 51).addBox(-2.0F, -2.5F, -5.5F, 5.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6F, 0.3643F, 2.6069F, -0.1745F, 0.0F, 0.0F));

        PartDefinition fuse_lit = barrel.addOrReplaceChild("fuse_lit", CubeListBuilder.create(), PartPose.offset(-0.6F, -1.8857F, 8.1069F));

        PartDefinition cube_r3 = fuse_lit.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(40, 75).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5672F, 0.0F, 0.0F));

        PartDefinition fuse_unlit = barrel.addOrReplaceChild("fuse_unlit", CubeListBuilder.create(), PartPose.offset(-0.6F, -1.8857F, 8.1069F));

        PartDefinition cube_r4 = fuse_unlit.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(46, 75).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5672F, 0.0F, 0.0F));

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(64, 51).addBox(-3.5F, -2.99F, -9.0F, 8.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(-3.5F, -1.99F, -7.0F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(62, 68).addBox(-3.5F, -4.99F, -8.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(50, 68).addBox(3.5F, -4.99F, -8.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(30, 75).addBox(3.5F, -1.5F, -10.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(20, 75).addBox(3.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 75).addBox(-4.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 75).addBox(-4.5F, -1.5F, -10.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(34, 51).addBox(-1.5F, -2.0F, -10.0F, 4.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 22.5F, 4.5F));

        PartDefinition cube_r5 = base.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(26, 68).addBox(-5.5F, -0.5F, -0.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r6 = base.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(26, 70).addBox(-5.5F, -0.5F, -0.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, -9.0F, -1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        barrel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
