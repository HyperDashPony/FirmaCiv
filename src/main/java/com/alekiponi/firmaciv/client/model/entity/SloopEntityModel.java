package com.alekiponi.firmaciv.client.model.entity;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.alekiponi.firmaciv.common.entity.FirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.SloopEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SloopEntityModel<T extends FirmacivBoatEntity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    private final ModelPart transom_port;
    private final ModelPart transom_starboard;
    private final ModelPart bowsprit;
    private final ModelPart hull_starboard;
    private final ModelPart main_boom;
    private final ModelPart mast;
    private final ModelPart keel;
    private final ModelPart sidewall_starboard;
    private final ModelPart bow_railing;
    private final ModelPart stern_railing;
    private final ModelPart sidewall_starboard_bow;
    private final ModelPart deck_starboard;
    private final ModelPart deck_port;
    private final ModelPart hull_port;
    private final ModelPart sidewall_port_bow;
    private final ModelPart mainsheet;
    private final ModelPart sidewall_port;
    private final ModelPart hold_netting;
    private final ModelPart waterocclusion;
    private final ModelPart shrouds;
    private final ModelPart jibsail_1;

    public SloopEntityModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.transom_port = root.getChild("transom_port");
        this.transom_starboard = root.getChild("transom_starboard");
        this.bowsprit = root.getChild("bowsprit");
        this.hull_starboard = root.getChild("hull_starboard");
        this.main_boom = root.getChild("main_boom");
        this.mast = root.getChild("mast");
        this.keel = root.getChild("keel");
        this.sidewall_starboard = root.getChild("sidewall_starboard");
        this.bow_railing = root.getChild("bow_railing");
        this.stern_railing = root.getChild("stern_railing");
        this.sidewall_starboard_bow = root.getChild("sidewall_starboard_bow");
        this.deck_starboard = root.getChild("deck_starboard");
        this.deck_port = root.getChild("deck_port");
        this.hull_port = root.getChild("hull_port");
        this.sidewall_port_bow = root.getChild("sidewall_port_bow");
        this.mainsheet = root.getChild("mainsheet");
        this.sidewall_port = root.getChild("sidewall_port");
        this.hold_netting = root.getChild("hold_netting");
        this.waterocclusion = root.getChild("waterocclusion");
        this.shrouds = root.getChild("shrouds");
        this.jibsail_1 = root.getChild("jibsail_1");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition transom_port = partdefinition.addOrReplaceChild("transom_port", CubeListBuilder.create(), PartPose.offset(-19.5F, 18.0F, -34.5F));

        PartDefinition cube_r1 = transom_port.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(188, 362).addBox(-74.7714F, 30.8908F, 14.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.5F, -47.5F, 11.7074F, -1.5708F, 1.4399F, -1.5708F));

        PartDefinition cube_r2 = transom_port.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(183, 342).addBox(-14.0F, 30.8908F, -74.7714F, 14.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.5F, -47.5F, 11.7074F, -3.0107F, 0.0F, -3.1416F));

        PartDefinition cube_r3 = transom_port.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 378).addBox(-14.0F, 33.5F, -71.2074F, 14.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.5F, -47.5F, 11.7074F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r4 = transom_port.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 341).addBox(-71.2074F, 34.5F, 14.0F, 4.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.5F, -47.5F, 11.7074F, 0.0F, 1.5708F, 0.0F));

        PartDefinition transom_starboard = partdefinition.addOrReplaceChild("transom_starboard", CubeListBuilder.create(), PartPose.offset(19.5F, 18.0F, -34.5F));

        PartDefinition cube_r5 = transom_starboard.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(272, 362).addBox(70.7714F, 30.8908F, 14.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.5F, -47.5F, 11.7074F, -1.5708F, -1.4399F, 1.5708F));

        PartDefinition cube_r6 = transom_starboard.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(219, 342).addBox(0.0F, 30.8908F, -74.7714F, 14.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.5F, -47.5F, 11.7074F, -3.0107F, 0.0F, -3.1416F));

        PartDefinition cube_r7 = transom_starboard.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(163, 382).addBox(0.0F, 33.5F, -71.2074F, 14.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.5F, -47.5F, 11.7074F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r8 = transom_starboard.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(36, 341).addBox(67.2074F, 34.5F, 14.0F, 4.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.5F, -47.5F, 11.7074F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bowsprit = partdefinition.addOrReplaceChild("bowsprit", CubeListBuilder.create(), PartPose.offset(0.0F, 11.3142F, 85.104F));

        PartDefinition cube_r9 = bowsprit.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(100, 266).addBox(-3.0F, 34.0F, 39.7926F, 6.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.8142F, -107.8966F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r10 = bowsprit.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(352, 389).mirror().addBox(2.717F, -52.8757F, 6.0F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(262, 357).mirror().addBox(-11.283F, -52.8757F, 5.0F, 24.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(100, 291).mirror().addBox(-17.283F, -52.8757F, 2.0F, 31.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -40.8142F, -107.8966F, 1.5708F, -1.0908F, 1.5708F));

        PartDefinition cube_r11 = bowsprit.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(352, 389).addBox(-12.717F, -52.8757F, 6.0F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(262, 357).addBox(-12.717F, -52.8757F, 5.0F, 24.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(100, 291).addBox(-13.717F, -52.8757F, 2.0F, 31.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(316, 238).addBox(-11.717F, -55.8757F, -2.0F, 31.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(154, 69).addBox(-4.717F, -59.8757F, -2.0F, 78.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(214, 205).addBox(-3.717F, -64.8757F, -2.0F, 36.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.8142F, -107.8966F, 1.5708F, 1.0908F, -1.5708F));

        PartDefinition cube_r12 = bowsprit.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(100, 295).addBox(23.3335F, -68.4135F, -1.0F, 30.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.8142F, -107.8966F, 1.5708F, 0.9599F, -1.5708F));

        PartDefinition hull_starboard = partdefinition.addOrReplaceChild("hull_starboard", CubeListBuilder.create(), PartPose.offset(11.5F, 23.5F, 35.0F));

        PartDefinition cube_r13 = hull_starboard.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(122, 402).addBox(13.2101F, 52.0476F, -12.6088F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
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
                .texOffs(58, 413).addBox(10.2101F, 52.0476F, -1.6088F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.5F, -53.0F, -57.7926F, -3.1416F, 0.0873F, -3.0107F));

        PartDefinition cube_r14 = hull_starboard.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(152, 205).addBox(8.2074F, 51.0295F, -7.4289F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.5F, -53.0F, -57.7926F, 0.0F, -1.5708F, 0.1309F));

        PartDefinition cube_r15 = hull_starboard.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 61).addBox(-3.1391F, 52.0476F, -66.6902F, 20.0F, 4.0F, 57.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.5F, -53.0F, -57.7926F, 3.1416F, -0.0873F, -3.0107F));

        PartDefinition main_boom = partdefinition.addOrReplaceChild("main_boom", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -18.0F, -32.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition main_boom_r1 = main_boom.addOrReplaceChild("main_boom_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-61.5F, -2.0F, -1.0F, 123.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 61.9148F, 0.0F, -1.5708F, 0.0F));

        PartDefinition gaff = main_boom.addOrReplaceChild("gaff", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -116.4182F, 0.9159F, 0.0F, -0.2094F, 0.0F));

        PartDefinition peak_halliard_r1 = gaff.addOrReplaceChild("peak_halliard_r1", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -34.0F, -0.5F, 1.0F, 64.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.6163F, 27.7348F, -1.5184F, 0.0F, 0.0F));

        PartDefinition gaff_r1 = gaff.addOrReplaceChild("gaff_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-33.0233F, -4.8366F, -1.0F, 70.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0299F, 29.3169F, 1.5708F, -1.2392F, -1.5708F));

        PartDefinition mainsail_1 = main_boom.addOrReplaceChild("mainsail_1", CubeListBuilder.create().texOffs(980, 884).addBox(-2.0F, -120.0F, 0.0F, 2.0F, 120.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.5F, 0.0F, -0.6545F, 0.0F));

        PartDefinition mainsail_gaff_1_r1 = mainsail_1.addOrReplaceChild("mainsail_gaff_1_r1", CubeListBuilder.create().texOffs(144, 872).addBox(-0.25F, 43.75F, -12.625F, 0.5F, 22.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -109.2524F, 65.6607F, -1.2392F, 0.0F, 0.0F));

        PartDefinition mainsail_2 = mainsail_1.addOrReplaceChild("mainsail_2", CubeListBuilder.create().texOffs(888, 884).addBox(-2.0F, -120.0F, 0.0F, 2.0F, 120.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 20.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition mainsail_gaff_2_r1 = mainsail_2.addOrReplaceChild("mainsail_gaff_2_r1", CubeListBuilder.create().texOffs(144, 872).addBox(-0.125F, 22.75F, -12.625F, 0.25F, 22.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -109.2524F, 45.6607F, -1.2392F, 0.0F, 0.0F));

        PartDefinition mainsail_3 = mainsail_2.addOrReplaceChild("mainsail_3", CubeListBuilder.create().texOffs(796, 884).addBox(-2.0F, -120.0F, 0.0F, 2.0F, 120.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 20.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition mainsail_gaff_3_r1 = mainsail_3.addOrReplaceChild("mainsail_gaff_3_r1", CubeListBuilder.create().texOffs(144, 872).addBox(-0.25F, 0.75F, -12.625F, 0.5F, 23.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -109.2524F, 25.6607F, -1.2392F, 0.0F, 0.0F));

        PartDefinition mainsail_4 = mainsail_3.addOrReplaceChild("mainsail_4", CubeListBuilder.create().texOffs(723, 884).addBox(-2.0F, -120.0F, 0.0F, 2.0F, 120.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 20.0F, 0.0F, 0.0873F, 0.0F));

        PartDefinition mainsail_leech_4_r1 = mainsail_4.addOrReplaceChild("mainsail_leech_4_r1", CubeListBuilder.create().texOffs(144, 872).addBox(-0.125F, -12.5F, -0.875F, 0.25F, 45.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -109.2524F, 5.6607F, 0.4637F, 0.0F, 0.0F));

        PartDefinition mainsail_5 = mainsail_4.addOrReplaceChild("mainsail_5", CubeListBuilder.create().texOffs(661, 884).addBox(-2.0F, -120.0F, 0.0F, 2.0F, 120.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 20.0F, 0.0F, -0.0436F, 0.0F));

        PartDefinition mainsail_leech_5_r1 = mainsail_5.addOrReplaceChild("mainsail_leech_5_r1", CubeListBuilder.create().texOffs(144, 872).addBox(-0.25F, 31.5F, -0.875F, 0.5F, 46.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -109.2524F, -14.3393F, 0.4637F, 0.0F, 0.0F));

        PartDefinition mainsail_6 = mainsail_5.addOrReplaceChild("mainsail_6", CubeListBuilder.create().texOffs(612, 884).addBox(-2.0F, -120.0F, 0.0F, 2.0F, 120.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 20.0F, 0.0F, -0.0873F, 0.0F));

        PartDefinition mainsail_leech_6_r1 = mainsail_6.addOrReplaceChild("mainsail_leech_6_r1", CubeListBuilder.create().texOffs(144, 872).addBox(-0.125F, 76.5F, -0.875F, 0.25F, 46.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -109.2524F, -34.3393F, 0.4637F, 0.0F, 0.0F));

        PartDefinition mast = partdefinition.addOrReplaceChild("mast", CubeListBuilder.create(), PartPose.offset(-2.0F, -55.0F, 41.0F));

        PartDefinition cube_r16 = mast.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(148, 341).addBox(-49.5F, -13.7926F, -3.0F, 15.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 25.5F, -63.7926F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition main_mast = mast.addOrReplaceChild("main_mast", CubeListBuilder.create(), PartPose.offset(2.0F, 25.5F, -63.7926F));

        PartDefinition mast2_r1 = main_mast.addOrReplaceChild("mast2_r1", CubeListBuilder.create().texOffs(0, 122).addBox(38.5F, -12.7926F, -2.0F, 75.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(154, 61).addBox(-51.5F, -12.7926F, -2.0F, 90.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition keel = partdefinition.addOrReplaceChild("keel", CubeListBuilder.create(), PartPose.offset(-0.5F, 42.0F, 32.0F));

        PartDefinition cube_r17 = keel.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 6).addBox(-26.7926F, 49.5F, -3.0F, 95.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -71.5F, -54.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r18 = keel.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(282, 304).addBox(-33.2596F, -83.259F, -0.5F, 11.0F, 36.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -71.5F, -54.7926F, -1.5708F, -1.2217F, -1.5708F));

        PartDefinition cube_r19 = keel.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 205).addBox(0.1907F, -66.0673F, -0.5F, 46.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -71.5F, -54.7926F, -1.5708F, -1.4399F, -1.5708F));

        PartDefinition cube_r20 = keel.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(218, 266).addBox(-2.7926F, -90.5F, -1.0F, 12.0F, 36.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -71.5F, -54.7926F, 0.0F, -1.5708F, 3.1416F));

        PartDefinition rudder = keel.addOrReplaceChild("rudder", CubeListBuilder.create(), PartPose.offset(0.5F, -30.8441F, 15.4742F));

        PartDefinition cube_r21 = rudder.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(133, 363).addBox(-1.0F, -11.0F, -0.5F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(131, 361).addBox(-1.0F, 3.0F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.5183F, -2.7718F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition cube_r22 = rudder.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(132, 362).addBox(-5.5F, -23.25F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(132, 362).addBox(-13.5F, -12.25F, -1.0F, 10.0F, 36.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.2985F, 9.965F, 1.5708F, -1.3963F, -1.5708F));

        PartDefinition sidewall_starboard = partdefinition.addOrReplaceChild("sidewall_starboard", CubeListBuilder.create(), PartPose.offset(35.5F, 16.0F, 4.0F));

        PartDefinition cube_r23 = sidewall_starboard.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 131).addBox(6.2487F, 48.5F, 23.8121F, 57.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(420, 165).addBox(5.2487F, 45.5F, 24.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(212, 165).addBox(5.2487F, 41.5F, 26.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 181).addBox(5.2487F, 37.5F, 30.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.5F, -45.5F, -26.7926F, 0.0F, -1.4835F, 0.0F));

        PartDefinition cube_r24 = sidewall_starboard.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(156, 389).addBox(55.2074F, 36.0F, 26.0F, 12.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(34, 389).addBox(55.2074F, 41.0F, 22.0F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(220, 389).addBox(55.2074F, 45.0F, 20.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.5F, -45.5F, -26.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bow_railing = partdefinition.addOrReplaceChild("bow_railing", CubeListBuilder.create(), PartPose.offset(-24.5232F, 3.2931F, -28.5199F));

        PartDefinition cube_r25 = bow_railing.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(226, 311).mirror().addBox(-3.75F, -1.0F, -0.25F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0264F, -0.2635F, 1.7272F));

        PartDefinition cube_r26 = bow_railing.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(226, 311).addBox(-5.5232F, -1.2931F, 0.4801F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.5465F, 0.0F, -37.9602F, -1.5708F, -0.3054F, -1.5708F));

        PartDefinition cube_r27 = bow_railing.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(116, 282).addBox(-5.0F, -1.25F, 27.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.5232F, -3.0431F, -8.5653F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r28 = bow_railing.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(225, 310).addBox(-33.8036F, -3.332F, -2.5204F, 43.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.6271F, -0.7111F, -8.5501F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r29 = bow_railing.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(226, 311).addBox(-5.25F, -1.0F, -0.25F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(49.0465F, 0.0F, 0.0F, 1.0264F, 0.2635F, -1.7272F));

        PartDefinition cube_r30 = bow_railing.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(225, 310).mirror().addBox(-9.1964F, -3.332F, -2.5204F, 43.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(45.4193F, -0.7111F, -8.5501F, -3.1416F, 1.0472F, -3.1416F));

        PartDefinition stern_railing = partdefinition.addOrReplaceChild("stern_railing", CubeListBuilder.create(), PartPose.offset(-5.5232F, 3.0431F, 48.5199F));

        PartDefinition cube_r31 = stern_railing.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(226, 311).addBox(-5.25F, -1.0F, -0.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0465F, 0.25F, 0.0F, -1.5708F, -0.3054F, -1.5708F));

        PartDefinition cube_r32 = stern_railing.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(226, 311).mirror().addBox(-3.75F, -1.0F, -0.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(226, 311).mirror().addBox(-3.75F, -1.0F, -0.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.25F, 0.0F, -1.5708F, 0.3054F, 1.5708F));

        PartDefinition cube_r33 = stern_railing.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(226, 311).addBox(0.5F, 3.0F, -0.5F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(30.8164F, -3.5431F, -12.0F, -1.6623F, 0.3042F, 1.5433F));

        PartDefinition cube_r34 = stern_railing.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(225, 310).addBox(-5.5F, -1.0F, -1.0F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(30.687F, -3.7931F, -11.517F, 3.1416F, -1.4835F, 3.1416F));

        PartDefinition cube_r35 = stern_railing.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(226, 311).mirror().addBox(-13.5F, 3.0F, -0.5F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-19.7699F, -3.5431F, -12.0F, -1.6623F, -0.3042F, -1.5433F));

        PartDefinition cube_r36 = stern_railing.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(225, 310).mirror().addBox(-18.5F, -1.0F, -1.0F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-19.6405F, -3.7931F, -11.517F, 3.1416F, 1.4835F, -3.1416F));

        PartDefinition cube_r37 = stern_railing.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(116, 282).addBox(-24.0F, -0.75F, -0.75F, 48.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5232F, -3.7931F, 1.3949F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition sidewall_starboard_bow = partdefinition.addOrReplaceChild("sidewall_starboard_bow", CubeListBuilder.create(), PartPose.offset(38.0F, 9.75F, 45.5F));

        PartDefinition cube_r38 = sidewall_starboard_bow.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(224, 309).addBox(-42.7511F, 34.25F, 22.3706F, 26.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(118, 157).addBox(-42.7511F, 36.25F, 21.3706F, 55.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(282, 122).addBox(-40.7511F, 37.25F, 21.3706F, 65.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(124, 132).addBox(-34.9903F, 41.25F, 17.413F, 57.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(348, 157).addBox(-30.7438F, 45.0F, 16.0386F, 52.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 165).addBox(-28.848F, 48.5F, 14.9835F, 50.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 195).addBox(-27.098F, 51.75F, 13.9835F, 47.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 406).addBox(9.902F, 53.25F, 13.9835F, 10.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-38.0F, -39.25F, -68.2926F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r39 = sidewall_starboard_bow.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(120, 244).addBox(-29.6749F, 51.6362F, 14.7041F, 37.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-38.0F, -39.25F, -68.2926F, 3.0144F, -1.0427F, -3.0097F));

        PartDefinition deck_starboard = partdefinition.addOrReplaceChild("deck_starboard", CubeListBuilder.create(), PartPose.offset(35.5F, 16.0F, 4.0F));

        PartDefinition cube_r40 = deck_starboard.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(154, 402).addBox(-30.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(352, 131).addBox(38.2074F, 42.5F, 0.0F, 28.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(258, 238).addBox(34.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(176, 330).addBox(24.2074F, 42.5F, 11.0F, 10.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 237).addBox(20.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(167, 285).addBox(10.2074F, 42.5F, 11.0F, 10.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(59, 245).addBox(6.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(0, 365).addBox(5.2074F, 42.5F, 11.0F, 1.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(152, 363).addBox(2.2074F, 42.5F, 11.0F, 3.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(128, 389).addBox(-0.7926F, 42.5F, 11.0F, 3.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(328, 391).addBox(-3.7926F, 42.5F, 11.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(138, 307).addBox(-4.7926F, 42.5F, 0.0F, 1.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(178, 305).addBox(-8.7926F, 42.5F, 0.0F, 4.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(112, 343).addBox(-11.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(312, 341).addBox(-15.7926F, 42.5F, 0.0F, 4.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(242, 363).addBox(-18.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(278, 389).addBox(-21.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(402, 391).addBox(-24.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(86, 412).addBox(-27.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.5F, -45.5F, -26.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition deck_port = partdefinition.addOrReplaceChild("deck_port", CubeListBuilder.create(), PartPose.offset(35.5F, 16.0F, 4.0F));

        PartDefinition cube_r41 = deck_port.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(248, 131).addBox(-66.2074F, 42.5F, 0.0F, 28.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(200, 238).addBox(-38.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(0, 310).addBox(-34.2074F, 42.5F, 11.0F, 10.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(294, 205).addBox(-24.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(167, 265).addBox(-20.2074F, 42.5F, 11.0F, 10.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(353, 209).addBox(-10.2074F, 42.5F, 0.0F, 4.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(348, 341).addBox(-6.2074F, 42.5F, 11.0F, 1.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(100, 359).addBox(-5.2074F, 42.5F, 11.0F, 3.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(103, 389).addBox(-2.2074F, 42.5F, 11.0F, 3.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(305, 391).addBox(0.7926F, 42.5F, 11.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(52, 303).addBox(3.7926F, 42.5F, 0.0F, 1.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(90, 301).addBox(4.7926F, 42.5F, 0.0F, 4.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(73, 339).addBox(8.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(262, 333).addBox(11.7926F, 42.5F, 0.0F, 4.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(212, 359).addBox(15.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(251, 389).addBox(18.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(382, 387).addBox(21.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(69, 400).addBox(24.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(139, 402).addBox(27.7926F, 42.5F, 0.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.5F, -45.5F, -26.7926F, 0.0F, 1.5708F, 0.0F));

        PartDefinition hull_port = partdefinition.addOrReplaceChild("hull_port", CubeListBuilder.create(), PartPose.offset(-11.5F, 23.5F, 35.0F));

        PartDefinition cube_r42 = hull_port.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(106, 402).addBox(-15.2101F, 52.0476F, -12.6088F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
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
                .texOffs(52, 413).addBox(-11.2101F, 52.0476F, -1.6088F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.5F, -53.0F, -57.7926F, -3.1416F, -0.0873F, 3.0107F));

        PartDefinition cube_r43 = hull_port.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(90, 205).addBox(-12.2074F, 51.0295F, -7.4289F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.5F, -53.0F, -57.7926F, 0.0F, 1.5708F, -0.1309F));

        PartDefinition cube_r44 = hull_port.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(252, 0).addBox(-16.8609F, 52.0476F, -66.6902F, 20.0F, 4.0F, 57.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.5F, -53.0F, -57.7926F, 3.1416F, 0.0873F, 3.0107F));

        PartDefinition sidewall_port_bow = partdefinition.addOrReplaceChild("sidewall_port_bow", CubeListBuilder.create(), PartPose.offset(-38.0F, 9.75F, 45.5F));

        PartDefinition cube_r45 = sidewall_port_bow.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(224, 304).addBox(16.7511F, 34.25F, 22.3706F, 26.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 157).addBox(-12.2489F, 36.25F, 21.3706F, 55.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(144, 122).addBox(-24.2489F, 37.25F, 21.3706F, 65.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 141).addBox(-22.0097F, 41.25F, 17.413F, 57.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(236, 157).addBox(-21.2562F, 45.0F, 16.0386F, 52.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(460, 157).addBox(-21.152F, 48.5F, 14.9835F, 50.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 189).addBox(-19.902F, 51.75F, 13.9835F, 47.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 402).addBox(-19.902F, 53.25F, 13.9835F, 10.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(38.0F, -39.25F, -68.2926F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r46 = sidewall_port_bow.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(120, 238).addBox(-7.3251F, 51.6362F, 14.7041F, 37.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(38.0F, -39.25F, -68.2926F, 3.0144F, 1.0427F, 3.0097F));

        PartDefinition mainsheet = partdefinition.addOrReplaceChild("mainsheet", CubeListBuilder.create(), PartPose.offset(7.8189F, 0.6593F, 53.2336F));

        PartDefinition cube_r47 = mainsheet.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(-2, -2).mirror().addBox(-0.8058F, 11.5272F, 11.4784F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.8189F, -1.4651F, 0.8362F, 0.0F, 1.5708F, 2.3562F));

        PartDefinition cube_r48 = mainsheet.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(-2, -2).addBox(-1.25F, -2.75F, -1.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.1811F, 1.8407F, -4.7336F, -0.6109F, 0.0F, -1.5708F));

        PartDefinition cube_r49 = mainsheet.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(-2, -2).addBox(-1.1942F, 11.5272F, 11.4784F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.8189F, -1.4651F, 0.8362F, 0.0F, -1.5708F, -2.3562F));

        PartDefinition cube_r50 = mainsheet.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(0, 0).addBox(8.0F, -1.1942F, 0.9301F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.8189F, -1.4651F, 0.8362F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r51 = mainsheet.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(-3, -3).addBox(11.0F, -3.1942F, -1.0699F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.8189F, -0.9651F, 0.3362F, -1.5708F, 0.0F, 0.0F));

        PartDefinition mainsheet_r1 = mainsheet.addOrReplaceChild("mainsheet_r1", CubeListBuilder.create().texOffs(145, 873).addBox(1.3199F, -19.0F, -0.3591F, 1.0F, 38.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(145, 873).addBox(-2.4301F, -19.0F, -0.3591F, 1.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.8189F, -1.4651F, 0.8362F, 0.0F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r2 = mainsheet.addOrReplaceChild("mainsheet_r2", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -11.0F, 0.75F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.2902F, 11.1048F, -15.4638F, -1.1781F, -1.0036F, 1.5708F));

        PartDefinition mainsheet_r3 = mainsheet.addOrReplaceChild("mainsheet_r3", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -16.0F, -3.25F, 1.0F, 37.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.8189F, -9.2049F, 14.7839F, 2.1817F, 0.0F, 0.0F));

        PartDefinition mainsheet_r4 = mainsheet.addOrReplaceChild("mainsheet_r4", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -11.0F, 0.75F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.7902F, 2.1048F, -14.4138F, -1.3963F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r5 = mainsheet.addOrReplaceChild("mainsheet_r5", CubeListBuilder.create().texOffs(145, 873).addBox(-1.9301F, -25.25F, -0.1091F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.8189F, 3.5349F, 0.8362F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition rope_spiral = mainsheet.addOrReplaceChild("rope_spiral", CubeListBuilder.create(), PartPose.offset(12.5555F, 12.0548F, -16.1063F));

        PartDefinition mainsheet_r6 = rope_spiral.addOrReplaceChild("mainsheet_r6", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, 0.5F, -10.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r7 = rope_spiral.addOrReplaceChild("mainsheet_r7", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, 0.5F, -4.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.4529F, 0.0F, 0.7179F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r8 = rope_spiral.addOrReplaceChild("mainsheet_r8", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, 0.5F, -4.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5703F, 0.0F, -4.2004F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r9 = rope_spiral.addOrReplaceChild("mainsheet_r9", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -4.5F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.714F, 0.0F, -1.265F, 1.7017F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r10 = rope_spiral.addOrReplaceChild("mainsheet_r10", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -1.0F, 3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.714F, 0.0F, -1.265F, -3.0107F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r11 = rope_spiral.addOrReplaceChild("mainsheet_r11", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, 1.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.714F, 0.0F, -1.265F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r12 = rope_spiral.addOrReplaceChild("mainsheet_r12", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -2.0F, 5.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4972F, 0.0F, -7.2137F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r13 = rope_spiral.addOrReplaceChild("mainsheet_r13", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -6.5F, 2.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4972F, 0.0F, -7.2137F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r14 = rope_spiral.addOrReplaceChild("mainsheet_r14", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -10.5F, 4.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.7582F, 0.0F, -9.1966F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r15 = rope_spiral.addOrReplaceChild("mainsheet_r15", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -8.5F, 4.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.5018F, 0.0F, -3.1562F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r16 = rope_spiral.addOrReplaceChild("mainsheet_r16", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition sidewall_port = partdefinition.addOrReplaceChild("sidewall_port", CubeListBuilder.create(), PartPose.offset(-35.5F, 16.0F, 4.0F));

        PartDefinition cube_r52 = sidewall_port.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(420, 122).addBox(-63.2487F, 48.5F, 23.8121F, 57.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(316, 165).addBox(-53.2487F, 45.5F, 24.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(108, 165).addBox(-53.2487F, 41.5F, 26.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 173).addBox(-53.2487F, 37.5F, 30.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(35.5F, -45.5F, -26.7926F, 0.0F, 1.4835F, 0.0F));

        PartDefinition cube_r53 = sidewall_port.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(68, 389).addBox(-67.2074F, 36.0F, 26.0F, 12.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 389).addBox(-68.2074F, 41.0F, 22.0F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(188, 389).addBox(-67.2074F, 45.0F, 20.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(35.5F, -45.5F, -26.7926F, 0.0F, 1.5708F, 0.0F));

        PartDefinition hold_netting = partdefinition.addOrReplaceChild("hold_netting", CubeListBuilder.create(), PartPose.offset(0.0F, -29.5F, -22.7926F));

        PartDefinition netting_r1 = hold_netting.addOrReplaceChild("netting_r1", CubeListBuilder.create().texOffs(-2, 929).addBox(-4.7926F, 42.0F, -12.0F, 12.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(-2, 929).addBox(23.2074F, 42.0F, -12.0F, 12.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(-2, 929).addBox(9.2074F, 42.0F, -12.0F, 12.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition waterocclusion = partdefinition.addOrReplaceChild("waterocclusion", CubeListBuilder.create(), PartPose.offset(0.0F, -29.5F, 32.2074F));

        PartDefinition waterocclusion_r1 = waterocclusion.addOrReplaceChild("waterocclusion_r1", CubeListBuilder.create().texOffs(0, 1000).addBox(-3.7926F, 42.5F, -11.0F, 38.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -55.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition shrouds = partdefinition.addOrReplaceChild("shrouds", CubeListBuilder.create(), PartPose.offset(-17.741F, -143.0F, -33.75F));

        PartDefinition shroud_starboard_r1 = shrouds.addOrReplaceChild("shroud_starboard_r1", CubeListBuilder.create().texOffs(136, 862).mirror().addBox(15.75F, -2.0583F, -0.5F, 1.0F, 161.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1308F, 0.0057F, 0.1749F));

        PartDefinition shroud_port_r1 = shrouds.addOrReplaceChild("shroud_port_r1", CubeListBuilder.create().texOffs(136, 862).addBox(-16.75F, -2.0583F, -0.5F, 1.0F, 161.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(35.482F, 0.0F, 0.0F, 0.1308F, -0.0057F, -0.1749F));

        PartDefinition jibsail_1 = partdefinition.addOrReplaceChild("jibsail_1", CubeListBuilder.create().texOffs(173, 912).addBox(-0.5F, -89.0F, -71.0F, 1.0F, 96.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -22.0F, 8.75F));

        PartDefinition jibsail_luff_1_r1 = jibsail_1.addOrReplaceChild("jibsail_luff_1_r1", CubeListBuilder.create().texOffs(144, 872).addBox(-0.125F, -44.0F, 3.25F, 0.25F, 29.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -54.9951F, -83.1351F, -0.588F, 0.0F, 0.0F));

        PartDefinition jib_sheet_r1 = jibsail_1.addOrReplaceChild("jib_sheet_r1", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -19.0F, 2.0F, 1.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 24.0725F, -52.0869F, 0.3229F, 0.0F, 0.0F));

        PartDefinition forestay_r1 = jibsail_1.addOrReplaceChild("forestay_r1", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -69.0F, 2.5F, 1.0F, 144.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -54.9951F, -82.3851F, -0.588F, 0.0F, 0.0F));

        PartDefinition jibsail_2 = jibsail_1.addOrReplaceChild("jibsail_2", CubeListBuilder.create().texOffs(217, 912).addBox(-0.5F, -89.0F, -71.0F, 1.0F, 96.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -16.0F));

        PartDefinition jibsail_luff_2_r1 = jibsail_2.addOrReplaceChild("jibsail_luff_2_r1", CubeListBuilder.create().texOffs(144, 872).addBox(-0.25F, -16.0F, 3.25F, 0.5F, 30.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -54.9951F, -67.1351F, -0.588F, 0.0F, 0.0F));

        PartDefinition jibsail_3 = jibsail_2.addOrReplaceChild("jibsail_3", CubeListBuilder.create().texOffs(309, 912).addBox(-0.5F, -89.0F, -71.0F, 1.0F, 96.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -16.0F));

        PartDefinition jibsail_luff_3_r1 = jibsail_3.addOrReplaceChild("jibsail_luff_3_r1", CubeListBuilder.create().texOffs(144, 872).addBox(-0.125F, 13.0F, 3.25F, 0.25F, 30.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -54.9951F, -51.1351F, -0.588F, 0.0F, 0.0F));

        PartDefinition jibsail_4 = jibsail_3.addOrReplaceChild("jibsail_4", CubeListBuilder.create().texOffs(263, 912).addBox(-0.5F, -89.0F, -71.0F, 1.0F, 96.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -16.0F));

        PartDefinition jibsail_luff_4_r1 = jibsail_4.addOrReplaceChild("jibsail_luff_4_r1", CubeListBuilder.create().texOffs(144, 872).addBox(-0.25F, 42.0F, 3.25F, 0.5F, 30.0F, 1.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -54.9951F, -35.1351F, -0.588F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 1024, 1024);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public ModelPart getWaterocclusion() {
        return this.waterocclusion;
    }

    private static void animateSail(SloopEntity pBoat, ModelPart boom, ModelPart[] sails, float mastRotation) {

        //boom.yRot = mastRotation;
        if(mastRotation < 0){
            boom.xScale = -1;
            boom.yRot = -mastRotation;

        } else {
            boom.xScale = 1;
            boom.yRot = -mastRotation;
        }
        /*
        for(ModelPart sail : sails){
            sail.yRot = mastRotation / 2;
        }*/
    }


    public ModelPart getBoom() {
        return this.main_boom;
    }

    public ModelPart[] getSails(){
        ModelPart[] sails = new ModelPart[6];
        sails[0] = this.main_boom.getChild("mainsail_1");
        sails[1] = sails[0].getChild("mainsail_2");
        sails[2] = sails[1].getChild("mainsail_3");
        sails[3] = sails[2].getChild("mainsail_4");
        sails[4] = sails[3].getChild("mainsail_5");
        sails[5] = sails[4].getChild("mainsail_6");
        return sails;
    }

    public void setupAnim(SloopEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
                          float pNetHeadYaw, float pHeadPitch) {
        animateSail(pEntity, this.getBoom(), this.getSails(), (float) Math.toRadians(pEntity.getSailRotation()));
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        transom_port.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        transom_starboard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bowsprit.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        hull_starboard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        main_boom.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mast.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        keel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sidewall_starboard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bow_railing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        stern_railing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sidewall_starboard_bow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        deck_starboard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        deck_port.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        hull_port.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sidewall_port_bow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mainsheet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sidewall_port.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        hold_netting.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        //waterocclusion.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        shrouds.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        jibsail_1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}