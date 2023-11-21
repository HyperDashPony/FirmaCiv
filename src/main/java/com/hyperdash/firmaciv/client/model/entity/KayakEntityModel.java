package com.hyperdash.firmaciv.client.model.entity;

import com.hyperdash.firmaciv.Firmaciv;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class KayakEntityModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(Firmaciv.MOD_ID, "kayak_entity"), "main");
    private final ModelPart bow;
    private final ModelPart stern;
    private final ModelPart center;
    private final ModelPart cockpit;
    private final ModelPart bb_main;

    private final ModelPart waterocclusion;
    private final ModelPart cockpitcover;

    public KayakEntityModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.bow = root.getChild("bow");
        this.stern = root.getChild("stern");
        this.center = root.getChild("center");
        this.cockpit = root.getChild("cockpit");
        this.bb_main = root.getChild("bb_main");
        this.waterocclusion = root.getChild("waterocclusion");
        this.cockpitcover = root.getChild("cockpit_cover");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bow = partdefinition.addOrReplaceChild("bow", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, -8.0F));

        PartDefinition cube_r1 = bow.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(42, 110)
                        .addBox(-3.05F, -0.8F, -2.3F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r2 = bow.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(18, 99)
                        .addBox(-4.75F, -2.95F, -5.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.1F, -3.25F, 1.25F, -0.0022F, -0.2539F, -0.5813F));

        PartDefinition cube_r3 = bow.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(28, 110)
                        .addBox(1.05F, -0.8F, -2.3F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r4 = bow.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 99)
                        .addBox(2.75F, -2.95F, -5.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.1F, -3.25F, 1.25F, -0.0022F, 0.2539F, 0.5813F));

        PartDefinition cube_r5 = bow.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(32, 60)
                        .addBox(-1.0F, 1.25F, -15.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r6 = bow.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(106, 74)
                        .addBox(-3.0F, 1.25F, -12.75F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r7 = bow.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(66, 87)
                        .addBox(-2.0F, -3.0F, -7.75F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -2.5F, -5.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r8 = bow.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(26, 74)
                        .addBox(1.75F, -1.25F, -8.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -4.75F, 1.25F, 0.1309F, 0.2182F, 0.0F));

        PartDefinition cube_r9 = bow.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(40, 28)
                        .addBox(-3.5F, -2.0F, -11.0F, 7.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -4.75F, 2.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r10 = bow.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(52, 74)
                        .addBox(-4.75F, -1.25F, -8.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, -4.75F, 1.25F, 0.1309F, -0.2182F, 0.0F));

        PartDefinition stern = partdefinition.addOrReplaceChild("stern", CubeListBuilder.create().texOffs(56, 99)
                        .addBox(-0.5F, -1.8F, 3.7F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 8.0F));

        PartDefinition cube_r11 = stern.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 110)
                        .addBox(-3.05F, -0.8F, -2.7F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r12 = stern.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(88, 87)
                        .addBox(-4.75F, -2.95F, -1.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.1F, -3.25F, -1.25F, 0.0022F, 0.2539F, -0.5813F));

        PartDefinition cube_r13 = stern.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(60, 60)
                        .addBox(-4.75F, -1.25F, -2.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, -4.75F, -1.25F, -0.1309F, 0.2182F, 0.0F));

        PartDefinition cube_r14 = stern.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 28)
                        .addBox(-3.5F, -2.0F, -2.0F, 7.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -4.75F, -2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r15 = stern.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(44, 87)
                        .addBox(-2.0F, -3.0F, 0.75F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -2.5F, 5.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r16 = stern.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(78, 74)
                        .addBox(-3.0F, 1.25F, 4.75F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r17 = stern.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(86, 60)
                        .addBox(-1.0F, 1.25F, 4.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r18 = stern.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(14, 110)
                        .addBox(1.05F, -0.8F, -2.7F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r19 = stern.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(106, 87)
                        .addBox(2.75F, -2.95F, -1.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.1F, -3.25F, -1.25F, 0.0022F, -0.2539F, 0.5813F));

        PartDefinition cube_r20 = stern.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 74)
                        .addBox(1.75F, -1.25F, -2.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -4.75F, -1.25F, -0.1309F, -0.2182F, 0.0F));

        PartDefinition center = partdefinition.addOrReplaceChild("center", CubeListBuilder.create().texOffs(92, 45)
                        .addBox(-6.0F, -6.15F, 2.0F, 2.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 45).addBox(4.0F, -6.15F, 2.0F, 2.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-2.5F, -1.6F, -5.0F, 5.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 45).addBox(0.65F, -0.7F, 1.5F, 3.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 45).addBox(-3.65F, -0.7F, 1.5F, 3.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, -8.0F));

        PartDefinition cube_r21 = center.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(80, 28)
                        .addBox(-0.55F, 0.1F, -6.5F, 2.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.8F, -3.9F, 8.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition cube_r22 = center.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(110, 28)
                        .addBox(-1.45F, 0.1F, -6.5F, 2.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.8F, -3.9F, 8.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition cockpit = partdefinition.addOrReplaceChild("cockpit", CubeListBuilder.create().texOffs(22, 87)
                        .addBox(4.0F, -8.15F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 87).addBox(-5.0F, -8.15F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(70, 102).addBox(-4.0F, -8.15F, 4.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 103).addBox(-4.0F, -6.15F, 4.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 99).addBox(-4.0F, -6.15F, -6.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(70, 99).addBox(-4.0F, -8.15F, -5.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition waterocclusion = partdefinition.addOrReplaceChild("waterocclusion",
                CubeListBuilder.create().texOffs(86, 105)
                        .addBox(-4.0F, -7.15F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cockpit_cover = partdefinition.addOrReplaceChild("cockpit_cover",
                CubeListBuilder.create().texOffs(0, 60)
                        .addBox(-4.0F, -7.15F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(62, 0)
                        .addBox(-0.5F, -2.1F, -12.3F, 1.0F, 3.0F, 24.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                          float headPitch) {

    }

    public ModelPart getWaterocclusion() {
        return this.waterocclusion;
    }

    public ModelPart getCockpitCover() {
        return this.cockpitcover;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        bow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        stern.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        center.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cockpit.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}