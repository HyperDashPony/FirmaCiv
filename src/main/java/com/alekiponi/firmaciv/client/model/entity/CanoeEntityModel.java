package com.alekiponi.firmaciv.client.model.entity;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class CanoeEntityModel<T extends CanoeEntity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(Firmaciv.MOD_ID, "canoe_entity"), "main");
    private final ModelPart middle_stern;
    private final ModelPart middle_mid;
    private final ModelPart middle_bow;
    private final ModelPart stern;
    private final ModelPart bow;
    private final ModelPart water_occlusion_3;
    private final ModelPart water_occlusion_5;
    private final ModelPart water_occlusion_4;
    private final ModelPart bars;

    public CanoeEntityModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.middle_stern = root.getChild("middle_stern");
        this.middle_mid = root.getChild("middle_mid");
        this.middle_bow = root.getChild("middle_bow");
        this.stern = root.getChild("stern");
        this.bow = root.getChild("bow");
        this.water_occlusion_3 = root.getChild("water_occlusion_3");
        this.water_occlusion_5 = root.getChild("water_occlusion_5");
        this.water_occlusion_4 = root.getChild("water_occlusion_4");
        this.bars = root.getChild("bars");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition middle_stern = partdefinition.addOrReplaceChild("middle_stern", CubeListBuilder.create().texOffs(90, 0).addBox(-8.0F, -9.0F, 8.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(111, 5).addBox(-6.0F, -6.0F, 8.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(132, 10).addBox(-4.0F, -3.0F, 8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(153, 13).addBox(-2.0F, -2.0F, 8.0F, 4.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(178, 10).addBox(2.0F, -3.0F, 8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(199, 5).addBox(4.0F, -6.0F, 8.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(220, 0).addBox(6.0F, -9.0F, 8.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition middle_mid = partdefinition.addOrReplaceChild("middle_mid", CubeListBuilder.create().texOffs(90, 225).addBox(-8.0F, -9.0F, -8.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(111, 230).addBox(-6.0F, -6.0F, -8.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(132, 235).addBox(-4.0F, -3.0F, -8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(153, 238).addBox(-2.0F, -2.0F, -8.0F, 4.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(178, 235).addBox(2.0F, -3.0F, -8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(199, 230).addBox(4.0F, -6.0F, -8.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(220, 225).addBox(6.0F, -9.0F, -8.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition middle_bow = partdefinition.addOrReplaceChild("middle_bow", CubeListBuilder.create().texOffs(90, 112).addBox(-8.0F, -9.0F, -24.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(111, 117).addBox(-6.0F, -6.0F, -24.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(132, 122).addBox(-4.0F, -3.0F, -24.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(153, 125).addBox(-2.0F, -2.0F, -24.0F, 4.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(178, 122).addBox(2.0F, -3.0F, -24.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(199, 117).addBox(4.0F, -6.0F, -24.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(220, 112).addBox(6.0F, -9.0F, -24.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition stern = partdefinition.addOrReplaceChild("stern", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition stern_center = stern.addOrReplaceChild("stern_center", CubeListBuilder.create().texOffs(36, 87).addBox(-1.0F, -12.0F, 7.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 95).addBox(-1.0F, -7.0F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 100).addBox(-2.0F, -5.0F, 1.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 107).addBox(-1.0F, -3.0F, -5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(33, 117).addBox(-2.0F, -2.0F, -8.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 32.0F));

        PartDefinition port_stern = stern.addOrReplaceChild("port_stern", CubeListBuilder.create().texOffs(48, 117).addBox(2.0F, -3.0F, -8.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(59, 115).addBox(4.0F, -6.0F, -8.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(70, 115).addBox(6.0F, -9.0F, -8.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(41, 50).addBox(1.0F, -4.0F, -5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(41, 59).addBox(3.0F, -6.0F, -5.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(41, 69).addBox(5.0F, -9.0F, -5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(41, 36).addBox(2.0F, -5.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(41, 42).addBox(3.0F, -9.0F, 1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(41, 29).addBox(1.0F, -9.0F, 4.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(41, 20).addBox(1.0F, -11.0F, 6.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(41, 26).addBox(2.0F, -10.0F, 5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 32.0F));

        PartDefinition starboard_stern = stern.addOrReplaceChild("starboard_stern", CubeListBuilder.create().texOffs(22, 117).addBox(-4.0F, -3.0F, -8.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(11, 115).addBox(-6.0F, -6.0F, -8.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 115).addBox(-8.0F, -9.0F, -8.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(23, 50).addBox(-3.0F, -4.0F, -5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(23, 59).addBox(-5.0F, -6.0F, -5.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(23, 69).addBox(-7.0F, -9.0F, -5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(27, 36).addBox(-4.0F, -5.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(29, 42).addBox(-5.0F, -9.0F, 1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(29, 29).addBox(-4.0F, -9.0F, 4.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(33, 20).addBox(-3.0F, -11.0F, 6.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(33, 26).addBox(-4.0F, -10.0F, 5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 32.0F));

        PartDefinition bow = partdefinition.addOrReplaceChild("bow", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bow_center = bow.addOrReplaceChild("bow_center", CubeListBuilder.create().texOffs(36, 162).addBox(-1.0F, -12.0F, -41.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 157).addBox(-1.0F, -7.0F, -39.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 150).addBox(-2.0F, -5.0F, -37.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 141).addBox(-1.0F, -3.0F, -33.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(33, 135).addBox(-2.0F, -2.0F, -27.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition port_bow = bow.addOrReplaceChild("port_bow", CubeListBuilder.create().texOffs(48, 135).addBox(2.0F, -3.0F, -27.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(59, 133).addBox(4.0F, -6.0F, -27.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(70, 133).addBox(6.0F, -9.0F, -27.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(41, 198).addBox(1.0F, -4.0F, -33.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(41, 188).addBox(3.0F, -6.0F, -33.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(41, 177).addBox(5.0F, -9.0F, -33.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(41, 215).addBox(2.0F, -5.0F, -37.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(41, 207).addBox(3.0F, -9.0F, -36.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(41, 221).addBox(1.0F, -9.0F, -38.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(41, 231).addBox(1.0F, -11.0F, -39.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(41, 228).addBox(2.0F, -10.0F, -38.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition starboard_bow = bow.addOrReplaceChild("starboard_bow", CubeListBuilder.create().texOffs(22, 135).addBox(-4.0F, -3.0F, -27.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(11, 133).addBox(-6.0F, -6.0F, -27.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 133).addBox(-8.0F, -9.0F, -27.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(23, 198).addBox(-3.0F, -4.0F, -33.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(23, 188).addBox(-5.0F, -6.0F, -33.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(23, 177).addBox(-7.0F, -9.0F, -33.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(27, 215).addBox(-4.0F, -5.0F, -37.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(29, 207).addBox(-5.0F, -9.0F, -36.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(29, 221).addBox(-4.0F, -9.0F, -38.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(33, 231).addBox(-3.0F, -11.0F, -39.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(33, 228).addBox(-4.0F, -10.0F, -38.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cleat = bow.addOrReplaceChild("cleat", CubeListBuilder.create().texOffs(28, 237).addBox(-5.0F, -2.0F, -17.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -16.0F));

        PartDefinition water_occlusion_3 = partdefinition.addOrReplaceChild("water_occlusion_3", CubeListBuilder.create().texOffs(-32, -32).addBox(-6.0F, -9.0F, -17.0F, 12.0F, 2.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(-4, -4).addBox(-3.0F, -9.0F, 17.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(-4, -4).addBox(-3.0F, -9.0F, -23.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition water_occlusion_5 = partdefinition.addOrReplaceChild("water_occlusion_5", CubeListBuilder.create().texOffs(-64, -64).addBox(-6.0F, -9.0F, -33.0F, 12.0F, 2.0F, 66.0F, new CubeDeformation(0.0F))
                .texOffs(-4, -4).addBox(-3.0F, -9.0F, 33.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(-4, -4).addBox(-3.0F, -9.0F, -39.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition water_occlusion_4 = partdefinition.addOrReplaceChild("water_occlusion_4", CubeListBuilder.create().texOffs(-48, -48).addBox(-6.0F, -9.0F, -25.0F, 12.0F, 2.0F, 50.0F, new CubeDeformation(0.0F))
                .texOffs(-4, -4).addBox(-3.0F, -9.0F, 25.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(-4, -4).addBox(-3.0F, -9.0F, -31.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bars = partdefinition.addOrReplaceChild("bars", CubeListBuilder.create().texOffs(26, 237).addBox(-6.0F, -8.0F, 5.0F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 237).addBox(-6.0F, -8.0F, -7.0F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(CanoeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                          float headPitch) {
        adjustLength(entity, bow, middle_bow, middle_mid, middle_stern, stern);
    }

    private static void adjustLength(CanoeEntity canoe, ModelPart bow, ModelPart middle_bow, ModelPart middle_mid, ModelPart middle_stern, ModelPart stern) {
        if (canoe.getLength() == 4) {
            bow.z = 8;
            middle_bow.z = 8;
            middle_mid.z = 8;
            stern.z = -8;
            return;
        } else if (canoe.getLength() == 3) {
            bow.z = 16;
            middle_bow.z = 16;
            stern.z = -16;
            return;
        }
        bow.z = 0;
        middle_bow.z = 0;
        middle_mid.z = 0;
        middle_stern.z = 0;
        stern.z = 0;
    }

    public ModelPart getWaterocclusion3() {
        return this.water_occlusion_3;
    }

    public ModelPart getWaterocclusion4() {
        return this.water_occlusion_4;
    }

    public ModelPart getWaterocclusion5() {
        return this.water_occlusion_5;
    }

    public ModelPart getMiddleStern() {
        return this.middle_stern;
    }

    public ModelPart getMiddleMid() {
        return this.middle_mid;
    }

    public ModelPart getBars() {
        return this.bars;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        bow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        stern.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        middle_bow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}