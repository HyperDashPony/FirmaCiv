package com.alekiponi.firmaciv.client.model.entity;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.alekiponi.firmaciv.common.entity.FirmacivBoatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class OutriggerEntityModel<T extends FirmacivBoatEntity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation("modid", "outrigger_canoe_v2"), "main");
    private final ModelPart hull;
    private final ModelPart cleat;
    private final ModelPart bow;
    private final ModelPart spars;
    private final ModelPart stern;
    private final ModelPart outrigger;
    private final ModelPart mast;
    private final ModelPart sail;
    private final ModelPart lashings;
    private final ModelPart netting;
    private final ModelPart waterocclusion;

    public OutriggerEntityModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.waterocclusion = root.getChild("waterocclusion");
        this.hull = root.getChild("hull");
        this.cleat = root.getChild("cleat");
        this.bow = root.getChild("bow");
        this.spars = root.getChild("spars");
        this.stern = root.getChild("stern");
        this.outrigger = root.getChild("outrigger");
        this.mast = root.getChild("mast");
        this.sail = root.getChild("sail");
        this.lashings = root.getChild("lashings");
        this.netting = root.getChild("netting");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition hull = partdefinition.addOrReplaceChild("hull", CubeListBuilder.create().texOffs(5, 67)
                        .addBox(-8.0F, -9.0F, -11.0F, 2.0F, 4.0F, 38.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 72).addBox(-6.0F, -6.0F, -11.0F, 2.0F, 4.0F, 38.0F, new CubeDeformation(0.0F))
                        .texOffs(171, 67).addBox(6.0F, -9.0F, -11.0F, 2.0F, 4.0F, 38.0F, new CubeDeformation(0.0F))
                        .texOffs(144, 72).addBox(4.0F, -6.0F, -11.0F, 2.0F, 4.0F, 38.0F, new CubeDeformation(0.0F))
                        .texOffs(117, 77).addBox(2.0F, -3.0F, -11.0F, 2.0F, 2.0F, 38.0F, new CubeDeformation(0.0F))
                        .texOffs(59, 77).addBox(-4.0F, -3.0F, -11.0F, 2.0F, 2.0F, 38.0F, new CubeDeformation(0.0F))
                        .texOffs(86, 80).addBox(-2.0F, -2.0F, -11.0F, 4.0F, 2.0F, 38.0F, new CubeDeformation(0.0F)),
                PartPose.offset(16.0F, 24.0F, -8.0F));

        PartDefinition cleat = partdefinition.addOrReplaceChild("cleat", CubeListBuilder.create(),
                PartPose.offset(16.0F, 24.0F, -44.0F));

        PartDefinition cube_r1 = cleat.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(33, 95)
                        .addBox(-17.0F, -9.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bow = partdefinition.addOrReplaceChild("bow", CubeListBuilder.create().texOffs(126, 19)
                        .addBox(3.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(125, 45).addBox(5.0F, -9.0F, -2.0F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(125, 35).addBox(3.0F, -6.0F, -2.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(125, 26).addBox(1.0F, -4.0F, -2.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(116, 83).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(107, 26).addBox(-3.0F, -4.0F, -2.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(107, 35).addBox(-5.0F, -6.0F, -2.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(107, 45).addBox(-7.0F, -9.0F, -2.0F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(126, 13).addBox(2.0F, -5.0F, -9.0F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(117, 77).addBox(-2.0F, -5.0F, -9.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(112, 13).addBox(-4.0F, -5.0F, -9.0F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(114, 19).addBox(-5.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 9).addBox(1.0F, -9.0F, -10.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(124, 75).addBox(-1.0F, -7.0F, -11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 0).addBox(1.0F, -9.0F, -11.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(124, 67).addBox(-1.0F, -10.0F, -13.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(117, 9).addBox(-4.0F, -9.0F, -10.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(121, 0).addBox(-3.0F, -9.0F, -11.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(16.0F, 24.0F, -27.0F));

        PartDefinition spars = partdefinition.addOrReplaceChild("spars", CubeListBuilder.create(),
                PartPose.offset(10.0F, 14.5F, 10.5F));

        PartDefinition cube_r2 = spars.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 9)
                        .addBox(-1.5F, -0.5F, -17.0F, 3.0F, 1.0F, 31.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r3 = spars.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(23, 26)
                        .addBox(9.0F, -0.5F, -8.0F, 2.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-19.5433F, 2.2961F, -10.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r4 = spars.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(33, 36)
                        .addBox(-0.5F, -0.5F, -15.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-41.75F, 4.5F, -0.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r5 = spars.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(33, 36)
                        .addBox(-0.5F, -0.5F, -15.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-41.75F, 4.5F, -20.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r6 = spars.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(23, 26)
                        .addBox(-10.0F, -0.5F, -8.0F, 2.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-19.5433F, 2.2961F, -11.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r7 = spars.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(6, 9)
                        .addBox(-1.5F, -0.5F, -17.0F, 3.0F, 1.0F, 31.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -20.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition stern = partdefinition.addOrReplaceChild("stern", CubeListBuilder.create().texOffs(125, 45)
                        .addBox(5.0F, -9.0F, -5.0F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(125, 35).addBox(3.0F, -6.0F, -5.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(125, 26).addBox(1.0F, -4.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(116, 83).addBox(-1.0F, -3.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(107, 26).addBox(-3.0F, -4.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(107, 35).addBox(-5.0F, -6.0F, -5.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(107, 45).addBox(-7.0F, -9.0F, -5.0F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(114, 19).addBox(-5.0F, -9.0F, 5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(117, 77).addBox(-2.0F, -5.0F, 5.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(126, 13).addBox(2.0F, -5.0F, 5.0F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(126, 19).addBox(3.0F, -9.0F, 5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 9).addBox(1.0F, -9.0F, 11.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 0).addBox(1.0F, -9.0F, 13.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(124, 75).addBox(-1.0F, -7.0F, 12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(124, 67).addBox(-1.0F, -10.0F, 14.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(121, 0).addBox(-3.0F, -9.0F, 13.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(117, 9).addBox(-4.0F, -9.0F, 11.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(112, 13).addBox(-4.0F, -5.0F, 5.0F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(16.0F, 24.0F, 24.0F));

        PartDefinition outrigger = partdefinition.addOrReplaceChild("outrigger", CubeListBuilder.create(),
                PartPose.offset(-15.75F, 18.0F, -5.0F));

        PartDefinition cube_r8 = outrigger.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(32, 35)
                        .addBox(1.0F, -1.0F, -15.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(28, 31).addBox(-1.0F, -1.0F, -19.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(30, 33).addBox(-0.5F, -0.5F, -17.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 35).addBox(-1.0F, 1.0F, -15.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 35).addBox(1.0F, -1.0F, 20.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 35).addBox(-1.0F, 1.0F, 20.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(30, 33).addBox(-0.5F, -0.5F, 20.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(28, 31).addBox(-1.0F, -1.0F, 20.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(7, 10).addBox(-1.0F, -1.0F, -10.0F, 5.0F, 5.0F, 30.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition mast = partdefinition.addOrReplaceChild("mast", CubeListBuilder.create(),
                PartPose.offset(16.0F, 2.0F, -15.0F));

        PartDefinition cube_r9 = mast.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-0.5F, -0.5F, 3.0F, 2.0F, 1.0F, 53.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r10 = mast.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-0.5F, -0.5F, 3.0F, 2.0F, 2.0F, 73.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, 23.0F, 2.5F, 0.0F, -1.5708F, 1.5708F));

        PartDefinition cube_r11 = mast.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(31, 93)
                        .addBox(-17.0F, -9.0F, -6.0F, 3.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 22.0F, -15.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition sail = partdefinition.addOrReplaceChild("sail", CubeListBuilder.create(),
                PartPose.offsetAndRotation(15.75F, -27.5F, -10.5F, 0.0F, 0.0873F, 0.0F));

        PartDefinition cube_r12 = sail.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(136, 240)
                        .addBox(-26.0F, 0.0F, -25.5F, 52.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, 3.0F, 25.0F, 2.8798F, 0.0F, 1.5708F));

        PartDefinition cube_r13 = sail.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(136, 205)
                        .addBox(-26.0F, 0.0F, 9.5F, 52.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, 3.0F, 25.0F, 2.9234F, 0.0F, 1.5708F));

        PartDefinition cube_r14 = sail.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(133, 221)
                        .addBox(-26.0F, 0.0F, -9.5F, 52.0F, 0.0F, 19.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.25F, 3.0F, 25.0F, -2.9671F, 0.0F, 1.5708F));

        PartDefinition lashings = partdefinition.addOrReplaceChild("lashings", CubeListBuilder.create(),
                PartPose.offset(24.0F, 16.0F, -9.25F));

        PartDefinition cube_r15 = lashings.addOrReplaceChild("cube_r15",
                CubeListBuilder.create().texOffs(-1, -1).mirror()
                        .addBox(-1.0F, -3.0F, 17.75F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(-1, -1).mirror()
                        .addBox(-1.0F, -3.0F, -2.25F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r16 = lashings.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-2.25F, -3.0F, -6.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-8.5F, 0.0F, -1.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r17 = lashings.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(-1, -1)
                        .addBox(-2.0F, -3.0F, -2.25F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(-1, -1).addBox(-2.0F, -3.0F, 17.75F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-16.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r18 = lashings.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-1.5F, -1.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-39.75F, 1.6464F, 19.8536F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r19 = lashings.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-1.5F, -1.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-39.75F, 1.6464F, -0.1464F, 0.0F, 0.0F, 0.7854F));

        PartDefinition netting = partdefinition.addOrReplaceChild("netting", CubeListBuilder.create(),
                PartPose.offset(5.6479F, 15.8992F, 0.5F));

        PartDefinition netting_r1 = netting.addOrReplaceChild("netting_r1", CubeListBuilder.create().texOffs(-8, 248)
                        .addBox(-8.5F, 0.0F, -3.5F, 17.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.5236F));

        PartDefinition netting_r2 = netting.addOrReplaceChild("netting_r2", CubeListBuilder.create().texOffs(-16, 233)
                        .addBox(-8.0F, 0.8539F, -10.8F, 17.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-14.6912F, 1.3969F, -0.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition waterocclusion = partdefinition.addOrReplaceChild("waterocclusion",
                CubeListBuilder.create().texOffs(-56, -56)
                        .addBox(10.0F, -9.0F, 38.0F, 12.0F, 3.0F, 58.0F, new CubeDeformation(0.0F))
                        .texOffs(-7, -7).addBox(13.0F, -9.0F, 96.0F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(-7, -7).addBox(13.0F, -9.0F, 29.0F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, -67.0F));


        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public ModelPart getWaterocclusion() {
        return this.waterocclusion;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
            float red, float green, float blue, float alpha) {
        hull.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cleat.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        spars.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        stern.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        outrigger.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mast.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        lashings.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        netting.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}