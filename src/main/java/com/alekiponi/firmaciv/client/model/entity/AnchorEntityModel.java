package com.alekiponi.firmaciv.client.model.entity;

import com.alekiponi.firmaciv.Firmaciv;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class AnchorEntityModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(Firmaciv.MOD_ID, "anchor_entity"), "main");
    private final ModelPart rope;
    private final ModelPart anchor;

    public AnchorEntityModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.rope = root.getChild("rope");
        this.anchor = root.getChild("anchor");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rope = partdefinition.addOrReplaceChild("rope", CubeListBuilder.create(), PartPose.offset(0.0F, 21.25F, 5.75F));

        PartDefinition cube_r1 = rope.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(12, 60).addBox(-2.5F, 0.0F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.25F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r2 = rope.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 60).addBox(-2.5F, -0.25F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, -0.25F, 0.0F, -0.7854F, 0.0F));

        PartDefinition anchor = partdefinition.addOrReplaceChild("anchor", CubeListBuilder.create().texOffs(24, 47).addBox(-3.5316F, -0.5F, 1.2215F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 47).addBox(1.4684F, -0.5F, 1.2215F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 56).addBox(0.4684F, -0.5F, -3.7785F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 56).addBox(-2.5316F, -0.5F, -3.7785F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 48).addBox(-1.5316F, -0.5F, -9.7785F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(26, 29).addBox(-1.5316F, -0.5F, 3.2215F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5316F, 22.5F, 3.7785F));

        PartDefinition cube_r3 = anchor.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(44, 55).addBox(-4.5F, -0.5F, -0.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.4181F, 0.0F, -5.993F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r4 = anchor.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 35).addBox(-1.5F, 0.5F, 4.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-1.5F, -1.5F, 4.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 47).addBox(-1.5F, -0.5F, 1.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(24, 59).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5316F, 0.0F, -9.5285F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r5 = anchor.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(52, 29).addBox(0.5F, -1.5F, 4.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(52, 35).addBox(0.5F, 0.5F, 4.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5316F, 0.0F, -9.5285F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r6 = anchor.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(22, 36).addBox(-1.0F, -1.5F, -1.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 34).addBox(2.0F, -1.5F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8816F, 0.5F, 0.7215F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r7 = anchor.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(22, 41).addBox(-2.5F, -1.5F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 39).addBox(-2.5F, -1.5F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8816F, 0.5F, 0.7215F, 0.0F, 0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rope.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        anchor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
