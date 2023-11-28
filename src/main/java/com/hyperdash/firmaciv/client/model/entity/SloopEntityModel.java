package com.hyperdash.firmaciv.client.model.entity;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.hyperdash.firmaciv.common.entity.FirmacivBoatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class SloopEntityModel<T extends FirmacivBoatEntity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "sloop"), "main");
    private final ModelPart transom_port;
    private final ModelPart transom_starboard;
    private final ModelPart bowsprit;
    private final ModelPart hull_starboard;
    private final ModelPart mast;
    private final ModelPart keel;
    private final ModelPart sidewall_starboard;
    private final ModelPart sidewall_starboard_bow;
    private final ModelPart deck_starboard;
    private final ModelPart deck_port;
    private final ModelPart hull_port;
    private final ModelPart sidewall_port_bow;
    private final ModelPart sidewall_port;
    private final ModelPart waterocclusion;
    private final ModelPart bb_main;

    public SloopEntityModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.transom_port = root.getChild("transom_port");
        this.transom_starboard = root.getChild("transom_starboard");
        this.bowsprit = root.getChild("bowsprit");
        this.hull_starboard = root.getChild("hull_starboard");
        this.mast = root.getChild("mast");
        this.keel = root.getChild("keel");
        this.sidewall_starboard = root.getChild("sidewall_starboard");
        this.sidewall_starboard_bow = root.getChild("sidewall_starboard_bow");
        this.deck_starboard = root.getChild("deck_starboard");
        this.deck_port = root.getChild("deck_port");
        this.hull_port = root.getChild("hull_port");
        this.sidewall_port_bow = root.getChild("sidewall_port_bow");
        this.sidewall_port = root.getChild("sidewall_port");
        this.waterocclusion = root.getChild("waterocclusion");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition transom_port = partdefinition.addOrReplaceChild("transom_port", CubeListBuilder.create(), PartPose.offset(-19.5F, 18.0F, -34.5F));

        PartDefinition cube_r1 = transom_port.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(188, 362).addBox(-74.7714F, 30.8908F, 14.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.5F, -47.5F, 66.7074F, -1.5708F, 1.4399F, -1.5708F));

        PartDefinition cube_r2 = transom_port.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(190, 341).addBox(-14.0F, 30.8908F, -74.7714F, 14.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.5F, -47.5F, 66.7074F, -3.0107F, 0.0F, -3.1416F));

        PartDefinition cube_r3 = transom_port.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 378).addBox(-14.0F, 33.5F, -71.2074F, 14.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.5F, -47.5F, 66.7074F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r4 = transom_port.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 341).addBox(-71.2074F, 34.5F, 14.0F, 4.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.5F, -47.5F, 66.7074F, 0.0F, 1.5708F, 0.0F));

        PartDefinition transom_starboard = partdefinition.addOrReplaceChild("transom_starboard", CubeListBuilder.create(), PartPose.offset(19.5F, 18.0F, -34.5F));

        PartDefinition cube_r5 = transom_starboard.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(272, 362).addBox(70.7714F, 30.8908F, 14.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.5F, -47.5F, 66.7074F, -1.5708F, -1.4399F, 1.5708F));

        PartDefinition cube_r6 = transom_starboard.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(226, 341).addBox(0.0F, 30.8908F, -74.7714F, 14.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.5F, -47.5F, 66.7074F, -3.0107F, 0.0F, -3.1416F));

        PartDefinition cube_r7 = transom_starboard.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(152, 377).addBox(0.0F, 33.5F, -71.2074F, 14.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.5F, -47.5F, 66.7074F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r8 = transom_starboard.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(36, 341).addBox(67.2074F, 34.5F, 14.0F, 4.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.5F, -47.5F, 66.7074F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bowsprit = partdefinition.addOrReplaceChild("bowsprit", CubeListBuilder.create(), PartPose.offset(0.0F, 11.3142F, 85.104F));

        PartDefinition cube_r9 = bowsprit.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(100, 266).addBox(-3.0F, 34.0F, 39.7926F, 6.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.8142F, -52.8966F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r10 = bowsprit.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(352, 389).addBox(-12.717F, -52.8757F, 6.0F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(262, 357).addBox(-12.717F, -52.8757F, 5.0F, 24.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(100, 291).addBox(-13.717F, -52.8757F, 2.0F, 31.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(316, 238).addBox(-11.717F, -55.8757F, -2.0F, 31.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(154, 69).addBox(-4.717F, -59.8757F, -2.0F, 78.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(214, 205).addBox(-3.717F, -64.8757F, -2.0F, 36.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.8142F, -52.8966F, 1.5708F, 1.0908F, -1.5708F));

        PartDefinition cube_r11 = bowsprit.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(100, 295).addBox(23.3335F, -68.4135F, -1.0F, 30.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.8142F, -52.8966F, 1.5708F, 0.9599F, -1.5708F));

        PartDefinition hull_starboard = partdefinition.addOrReplaceChild("hull_starboard", CubeListBuilder.create(), PartPose.offset(11.5F, 23.5F, 35.0F));

        PartDefinition cube_r12 = hull_starboard.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(122, 402).addBox(13.2101F, 52.0476F, -12.6088F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(32, 413).addBox(2.2101F, 52.0476F, 12.3912F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(70, 413).addBox(2.2101F, 52.0476F, 14.3912F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(258, 402).addBox(-2.7899F, 52.0476F, 20.3912F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(194, 402).addBox(-2.7899F, 52.0476F, 18.3912F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(288, 402).addBox(2.2101F, 52.0476F, 10.3912F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(176, 173).addBox(-5.7899F, 52.0476F, -9.6088F, 8.0F, 4.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(278, 402).addBox(6.2101F, 52.0476F, 2.3912F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(206, 402).addBox(6.2101F, 52.0476F, 0.3912F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 402).addBox(10.2101F, 52.0476F, -12.6088F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(64, 362).addBox(6.2101F, 52.0476F, -11.6088F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(50, 266).addBox(2.2101F, 52.0476F, -10.6088F, 4.0F, 4.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(0, 413).addBox(-2.7899F, 52.0476F, 22.3912F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 413).addBox(-3.7899F, 52.0476F, 24.3912F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 413).addBox(6.2101F, 52.0476F, 4.3912F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 413).addBox(6.2101F, 52.0476F, 6.3912F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(268, 402).addBox(10.2101F, 52.0476F, -5.6088F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 413).addBox(10.2101F, 52.0476F, -3.6088F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(58, 413).addBox(10.2101F, 52.0476F, -1.6088F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.5F, -53.0F, -2.7926F, -3.1416F, 0.0873F, -3.0107F));

        PartDefinition cube_r13 = hull_starboard.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(152, 205).addBox(8.2074F, 51.0295F, -7.4289F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.5F, -53.0F, -2.7926F, 0.0F, -1.5708F, 0.1309F));

        PartDefinition cube_r14 = hull_starboard.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 61).addBox(-3.1391F, 52.0476F, -66.6902F, 20.0F, 4.0F, 57.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.5F, -53.0F, -2.7926F, 3.1416F, -0.0873F, -3.0107F));

        PartDefinition mast = partdefinition.addOrReplaceChild("mast", CubeListBuilder.create().texOffs(630, 812).addBox(-1.0F, -80.0F, -99.0F, 1.0F, 134.0F, 78.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -55.0F, 41.0F));

        PartDefinition cube_r15 = mast.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(148, 341).addBox(-49.5F, -13.7926F, -3.0F, 15.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 122).addBox(38.5F, -12.7926F, -2.0F, 68.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(154, 61).addBox(-51.5F, -12.7926F, -2.0F, 90.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 25.5F, -8.7926F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition cube_r16 = mast.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 0).addBox(-8.7926F, 7.5F, -1.0F, 124.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 25.5F, -8.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r17 = mast.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(794, 909).addBox(-7.5F, -8.7926F, 1.0F, 114.0F, 114.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 25.5F, -8.7926F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition keel = partdefinition.addOrReplaceChild("keel", CubeListBuilder.create(), PartPose.offset(-0.5F, 42.0F, 32.0F));

        PartDefinition cube_r18 = keel.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 6).addBox(-26.7926F, 49.5F, -3.0F, 95.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -71.5F, 0.2074F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r19 = keel.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(132, 362).addBox(49.2198F, 64.9485F, -1.0F, 8.0F, 25.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -71.5F, 0.2074F, 1.5708F, -1.3963F, -1.5708F));

        PartDefinition cube_r20 = keel.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(282, 304).addBox(-33.2596F, -83.259F, -0.5F, 11.0F, 36.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -71.5F, 0.2074F, -1.5708F, -1.2217F, -1.5708F));

        PartDefinition cube_r21 = keel.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 205).addBox(0.1907F, -66.0673F, -0.5F, 44.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -71.5F, 0.2074F, -1.5708F, -1.4399F, -1.5708F));

        PartDefinition cube_r22 = keel.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(218, 266).addBox(-2.7926F, -90.5F, -1.0F, 12.0F, 36.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -71.5F, 0.2074F, 0.0F, -1.5708F, 3.1416F));

        PartDefinition sidewall_starboard = partdefinition.addOrReplaceChild("sidewall_starboard", CubeListBuilder.create(), PartPose.offset(35.5F, 16.0F, 4.0F));

        PartDefinition cube_r23 = sidewall_starboard.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 131).addBox(5.2487F, 48.5F, 23.8121F, 58.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(420, 165).addBox(5.2487F, 45.5F, 24.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(212, 165).addBox(5.2487F, 41.5F, 26.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 181).addBox(5.2487F, 37.5F, 30.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.5F, -45.5F, 28.2074F, 0.0F, -1.4835F, 0.0F));

        PartDefinition cube_r24 = sidewall_starboard.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(156, 389).addBox(55.2074F, 36.0F, 26.0F, 12.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(34, 389).addBox(55.2074F, 41.0F, 22.0F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(220, 389).addBox(55.2074F, 45.0F, 20.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.5F, -45.5F, 28.2074F, 0.0F, -1.5708F, 0.0F));

        PartDefinition sidewall_starboard_bow = partdefinition.addOrReplaceChild("sidewall_starboard_bow", CubeListBuilder.create(), PartPose.offset(38.0F, 9.75F, 45.5F));

        PartDefinition cube_r25 = sidewall_starboard_bow.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(224, 309).addBox(-42.7511F, 34.25F, 22.3706F, 26.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(118, 157).addBox(-42.7511F, 36.25F, 21.3706F, 55.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(282, 122).addBox(-40.7511F, 37.25F, 21.3706F, 65.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(124, 131).addBox(-34.9903F, 41.25F, 17.913F, 57.0F, 4.0F, 4.5F, new CubeDeformation(0.0F))
                .texOffs(348, 157).addBox(-30.7438F, 45.0F, 16.0386F, 52.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 165).addBox(-28.848F, 48.5F, 14.9835F, 50.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 195).addBox(-27.098F, 51.75F, 13.9835F, 47.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 406).addBox(9.902F, 53.25F, 13.9835F, 10.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-38.0F, -39.25F, -13.2926F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r26 = sidewall_starboard_bow.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(120, 244).addBox(-29.6749F, 51.6362F, 14.7041F, 37.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-38.0F, -39.25F, -13.2926F, 3.0144F, -1.0427F, -3.0097F));

        PartDefinition deck_starboard = partdefinition.addOrReplaceChild("deck_starboard", CubeListBuilder.create(), PartPose.offset(35.5F, 16.0F, 4.0F));

        PartDefinition cube_r27 = deck_starboard.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(352, 131).addBox(38.2074F, 42.5F, 0.0F, 28.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(258, 238).addBox(34.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(176, 324).addBox(24.2074F, 42.5F, 11.0F, 10.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 238).addBox(20.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(168, 283).addBox(10.2074F, 42.5F, 11.0F, 10.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(60, 238).addBox(6.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(0, 362).addBox(5.2074F, 42.5F, 11.0F, 1.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(152, 362).addBox(2.2074F, 42.5F, 11.0F, 3.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(128, 389).addBox(-0.7926F, 42.5F, 11.0F, 3.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(328, 389).addBox(-3.7926F, 42.5F, 11.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(134, 304).addBox(-4.7926F, 42.5F, 0.0F, 1.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(176, 304).addBox(-8.7926F, 42.5F, 0.0F, 4.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(110, 341).addBox(-11.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(312, 341).addBox(-15.7926F, 42.5F, 0.0F, 4.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(242, 362).addBox(-18.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(278, 389).addBox(-21.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(402, 389).addBox(-24.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(86, 402).addBox(-27.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(154, 402).addBox(-30.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.5F, -45.5F, 28.2074F, 0.0F, -1.5708F, 0.0F));

        PartDefinition deck_port = partdefinition.addOrReplaceChild("deck_port", CubeListBuilder.create(), PartPose.offset(35.5F, 16.0F, 4.0F));

        PartDefinition cube_r28 = deck_port.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(248, 131).addBox(-66.2074F, 42.5F, 0.0F, 28.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(200, 238).addBox(-38.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(0, 304).addBox(-34.2074F, 42.5F, 11.0F, 10.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(294, 205).addBox(-24.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(168, 266).addBox(-20.2074F, 42.5F, 11.0F, 10.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(354, 205).addBox(-10.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(348, 341).addBox(-6.2074F, 42.5F, 11.0F, 1.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(100, 362).addBox(-5.2074F, 42.5F, 11.0F, 3.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(100, 389).addBox(-2.2074F, 42.5F, 11.0F, 3.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(304, 389).addBox(0.7926F, 42.5F, 11.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(48, 304).addBox(3.7926F, 42.5F, 0.0F, 1.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(90, 304).addBox(4.7926F, 42.5F, 0.0F, 4.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(72, 341).addBox(8.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(262, 341).addBox(11.7926F, 42.5F, 0.0F, 4.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(212, 362).addBox(15.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(252, 389).addBox(18.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(380, 389).addBox(21.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(66, 402).addBox(24.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(138, 402).addBox(27.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.5F, -45.5F, 28.2074F, 0.0F, 1.5708F, 0.0F));

        PartDefinition hull_port = partdefinition.addOrReplaceChild("hull_port", CubeListBuilder.create(), PartPose.offset(-11.5F, 23.5F, 35.0F));

        PartDefinition cube_r29 = hull_port.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(106, 402).addBox(-15.2101F, 52.0476F, -12.6088F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(298, 402).addBox(-4.2101F, 52.0476F, 12.3912F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 413).addBox(-3.2101F, 52.0476F, 14.3912F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(218, 402).addBox(-0.2101F, 52.0476F, 20.3912F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(170, 402).addBox(-1.2101F, 52.0476F, 18.3912F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(228, 402).addBox(-5.2101F, 52.0476F, 10.3912F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(104, 173).addBox(-2.2101F, 52.0476F, -9.6088F, 8.0F, 4.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(238, 402).addBox(-9.2101F, 52.0476F, 2.3912F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(182, 402).addBox(-10.2101F, 52.0476F, 0.3912F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 402).addBox(-13.2101F, 52.0476F, -12.6088F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(32, 362).addBox(-10.2101F, 52.0476F, -11.6088F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 266).addBox(-6.2101F, 52.0476F, -10.6088F, 4.0F, 4.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(306, 402).addBox(0.7899F, 52.0476F, 22.3912F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(314, 402).addBox(1.7899F, 52.0476F, 24.3912F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(322, 402).addBox(-8.2101F, 52.0476F, 4.3912F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 413).addBox(-7.2101F, 52.0476F, 6.3912F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(248, 402).addBox(-13.2101F, 52.0476F, -5.6088F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(330, 402).addBox(-12.2101F, 52.0476F, -3.6088F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 413).addBox(-11.2101F, 52.0476F, -1.6088F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.5F, -53.0F, -2.7926F, -3.1416F, -0.0873F, 3.0107F));

        PartDefinition cube_r30 = hull_port.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(90, 205).addBox(-12.2074F, 51.0295F, -7.4289F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.5F, -53.0F, -2.7926F, 0.0F, 1.5708F, -0.1309F));

        PartDefinition cube_r31 = hull_port.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(252, 0).addBox(-16.8609F, 52.0476F, -66.6902F, 20.0F, 4.0F, 57.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.5F, -53.0F, -2.7926F, 3.1416F, 0.0873F, 3.0107F));

        PartDefinition sidewall_port_bow = partdefinition.addOrReplaceChild("sidewall_port_bow", CubeListBuilder.create(), PartPose.offset(-38.0F, 9.75F, 45.5F));

        PartDefinition cube_r32 = sidewall_port_bow.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(224, 304).addBox(16.7511F, 34.25F, 22.3706F, 26.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 157).addBox(-12.2489F, 36.25F, 21.3706F, 55.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(144, 122).addBox(-24.2489F, 37.25F, 21.3706F, 65.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 140).addBox(-22.0097F, 41.25F, 17.913F, 57.0F, 4.0F, 4.5F, new CubeDeformation(0.0F))
                .texOffs(236, 157).addBox(-21.2562F, 45.0F, 16.0386F, 52.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(460, 157).addBox(-21.152F, 48.5F, 14.9835F, 50.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 189).addBox(-19.902F, 51.75F, 13.9835F, 47.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 402).addBox(-19.902F, 53.25F, 13.9835F, 10.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(38.0F, -39.25F, -13.2926F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r33 = sidewall_port_bow.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(120, 238).addBox(-7.3251F, 51.6362F, 14.7041F, 37.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(38.0F, -39.25F, -13.2926F, 3.0144F, 1.0427F, 3.0097F));

        PartDefinition sidewall_port = partdefinition.addOrReplaceChild("sidewall_port", CubeListBuilder.create(), PartPose.offset(-35.5F, 16.0F, 4.0F));

        PartDefinition cube_r34 = sidewall_port.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(420, 122).addBox(-63.2487F, 48.5F, 23.8121F, 58.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(316, 165).addBox(-53.2487F, 45.5F, 24.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(108, 165).addBox(-53.2487F, 41.5F, 26.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 173).addBox(-53.2487F, 37.5F, 30.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(35.5F, -45.5F, 28.2074F, 0.0F, 1.4835F, 0.0F));

        PartDefinition cube_r35 = sidewall_port.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(68, 389).addBox(-67.2074F, 36.0F, 26.0F, 12.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 389).addBox(-68.2074F, 41.0F, 22.0F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(188, 389).addBox(-67.2074F, 45.0F, 20.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(35.5F, -45.5F, 28.2074F, 0.0F, 1.5708F, 0.0F));

        PartDefinition waterocclusion = partdefinition.addOrReplaceChild("waterocclusion", CubeListBuilder.create(), PartPose.offset(0.0F, -29.5F, 32.2074F));

        PartDefinition waterocclusion_r1 = waterocclusion.addOrReplaceChild("waterocclusion_r1", CubeListBuilder.create().texOffs(0, 1000).addBox(-3.7926F, 42.5F, -11.0F, 38.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 1024, 1024);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public ModelPart getWaterocclusion() {
        return this.waterocclusion;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        transom_port.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        transom_starboard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bowsprit.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        hull_starboard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mast.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        keel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sidewall_starboard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sidewall_starboard_bow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        deck_starboard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        deck_port.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        hull_port.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sidewall_port_bow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sidewall_port.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}