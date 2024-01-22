package com.alekiponi.firmaciv.client.model.entity;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.WindlassSwitchEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class SloopEntityModel extends EntityModel<SloopEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    protected final ModelPart mainsail;
    protected final ModelPart mainsail_deployed;
    protected final ModelPart mainsail_furled;
    protected final ModelPart rudder;
    protected final ModelPart rope_spiral;
    protected final ModelPart mainsheet_main;
    protected final ModelPart waterocclusion;
    protected final ModelPart jibsail_deployed;
    protected final ModelPart jibsail_furled;
    protected final ModelPart windlass;
    protected final ModelPart static_parts;
    protected final ModelPart jibsheet_transform_checker;
    private final ModelPart telltail;


    public SloopEntityModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.mainsail = root.getChild("mainsail");
        this.mainsail_deployed = root.getChild("mainsail_deployed");
        this.mainsail_furled = root.getChild("mainsail_furled");
        this.rudder = root.getChild("rudder");
        this.rope_spiral = root.getChild("rope_spiral");
        this.jibsheet_transform_checker = root.getChild("jibsheet_transform_checker");
        this.mainsheet_main = root.getChild("mainsheet_main");
        this.waterocclusion = root.getChild("waterocclusion");
        this.jibsail_deployed = root.getChild("jibsail_deployed");
        this.jibsail_furled = root.getChild("jibsail_furled");
        this.windlass = root.getChild("windlass");
        this.static_parts = root.getChild("static_parts");
        this.telltail = root.getChild("telltail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition mainsail = partdefinition.addOrReplaceChild("mainsail", CubeListBuilder.create(), PartPose.offset(0.0F, -18.0F, -32.0F));

        PartDefinition mainsail_deployed = partdefinition.addOrReplaceChild("mainsail_deployed", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -22.0F, -34.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition main_boom = mainsail_deployed.addOrReplaceChild("main_boom", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 63.9148F));

        PartDefinition main_boom_r1 = main_boom.addOrReplaceChild("main_boom_r1", CubeListBuilder.create().texOffs(352, 1016).addBox(-60.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(374, 1016).addBox(56.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(154, 882).addBox(22.0F, -1.0F, -4.0F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(353, 90).addBox(-62.0F, -2.0F, -1.0F, 123.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition main_boom_r2 = main_boom.addOrReplaceChild("main_boom_r2", CubeListBuilder.create().texOffs(202, 1013).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 23.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition main_boom_r3 = main_boom.addOrReplaceChild("main_boom_r3", CubeListBuilder.create().texOffs(202, 1013).addBox(-1.0F, -2.5F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 23.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition gaff_deployed = mainsail_deployed.addOrReplaceChild("gaff_deployed", CubeListBuilder.create(), PartPose.offset(0.0F, -112.4182F, 0.5F));

        PartDefinition gaff_r1 = gaff_deployed.addOrReplaceChild("gaff_r1", CubeListBuilder.create().texOffs(330, 1016).addBox(-2.0206F, -102.6087F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(297, 1016).addBox(27.7294F, -102.6087F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(363, 1016).addBox(57.9794F, -102.6087F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 104.0299F, 31.7328F, 1.5708F, -1.3265F, -1.5708F));

        PartDefinition shroud_starboard_r1 = gaff_deployed.addOrReplaceChild("shroud_starboard_r1", CubeListBuilder.create().texOffs(10, 866).mirror().addBox(-0.5F, -1.0F, -0.5F, 1.0F, 157.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -20.5818F, 4.0F, -0.0175F, 0.0F, 0.0175F));

        PartDefinition peak_halliard_r1 = gaff_deployed.addOrReplaceChild("peak_halliard_r1", CubeListBuilder.create().texOffs(75, 1001).addBox(-0.5F, -6.0F, -1.25F, 1.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.8465F, 18.059F, -1.4399F, 0.0F, 0.0F));

        PartDefinition mast2_r1 = gaff_deployed.addOrReplaceChild("mast2_r1", CubeListBuilder.create().texOffs(71, 912).addBox(-1.5F, 0.05F, -2.75F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0875F, -12.2099F, 26.4654F, -0.2618F, 0.0F, 0.0F));

        PartDefinition mast2_r2 = gaff_deployed.addOrReplaceChild("mast2_r2", CubeListBuilder.create().texOffs(180, 907).addBox(-1.0F, -4.5F, 1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(191, 907).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0875F, -12.308F, 24.0994F, -1.0472F, 0.0F, 0.0F));

        PartDefinition mast2_r3 = gaff_deployed.addOrReplaceChild("mast2_r3", CubeListBuilder.create().texOffs(70, 927).addBox(-1.5F, -0.5F, -5.5F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9125F, -21.6069F, 4.8502F, 0.0F, -0.2182F, 0.0F));

        PartDefinition mast2_r4 = gaff_deployed.addOrReplaceChild("mast2_r4", CubeListBuilder.create().texOffs(158, 907).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9125F, -21.6069F, 4.8502F, -0.2618F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r2 = gaff_deployed.addOrReplaceChild("peak_halliard_r2", CubeListBuilder.create().texOffs(100, 1010).addBox(-0.5F, 12.0F, 12.0F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.062F, 53.0626F, -2.4435F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r3 = gaff_deployed.addOrReplaceChild("peak_halliard_r3", CubeListBuilder.create().texOffs(50, 987).addBox(-0.5F, -16.0F, -7.5F, 1.0F, 36.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0735F, 47.501F, -1.6581F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r4 = gaff_deployed.addOrReplaceChild("peak_halliard_r4", CubeListBuilder.create().texOffs(80, 1002).addBox(-0.95F, -7.0F, -0.5F, 1.0F, 21.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.4561F, 17.7502F, -2.0075F, 0.0395F, -0.0184F));

        PartDefinition mast2_r5 = gaff_deployed.addOrReplaceChild("mast2_r5", CubeListBuilder.create().texOffs(67, 872).addBox(92.5F, -13.7926F, 2.0F, 9.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 876).addBox(94.5F, -14.7926F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(67, 872).addBox(92.5F, -13.7926F, -3.0F, 9.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 890).addBox(92.5F, -8.7926F, -2.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 104.9182F, 10.7074F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition gaff_r2 = gaff_deployed.addOrReplaceChild("gaff_r2", CubeListBuilder.create().texOffs(463, 78).addBox(-31.6967F, -3.1385F, -1.0F, 68.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0299F, 31.7328F, 1.5708F, -1.3265F, -1.5708F));

        PartDefinition mainsail_furled = partdefinition.addOrReplaceChild("mainsail_furled", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition furled_sail = mainsail_furled.addOrReplaceChild("furled_sail", CubeListBuilder.create(), PartPose.offset(-0.0429F, -46.4571F, -0.25F));

        PartDefinition main_boom_r4 = furled_sail.addOrReplaceChild("main_boom_r4", CubeListBuilder.create().texOffs(385, 1016).addBox(-60.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(407, 1016).addBox(-43.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(385, 1016).addBox(56.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(396, 1016).addBox(-10.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0429F, 2.4571F, 30.1648F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r1 = furled_sail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(1010, 1017).addBox(-2.9821F, -3.0429F, -31.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, -0.25F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r2 = furled_sail.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(1008, 955).addBox(-3.9821F, -4.0429F, -29.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(1004, 964).addBox(-4.9821F, -5.0429F, -26.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(966, 980).addBox(-2.9821F, -3.0429F, 75.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(978, 1001).addBox(-3.9821F, -4.0429F, 57.0F, 5.0F, 5.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(939, 982).addBox(-4.9821F, -5.0429F, 37.0F, 6.0F, 6.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(892, 958).addBox(-5.9821F, -6.0429F, -22.0F, 7.0F, 7.0F, 59.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition main_furled_lines = mainsail_furled.addOrReplaceChild("main_furled_lines", CubeListBuilder.create(), PartPose.offset(0.0F, -44.0F, 52.9148F));

        PartDefinition mast2_r6 = main_furled_lines.addOrReplaceChild("mast2_r6", CubeListBuilder.create().texOffs(169, 907).addBox(-1.0F, -1.3F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0875F, -16.8145F, 17.3728F, -0.3491F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r5 = main_furled_lines.addOrReplaceChild("peak_halliard_r5", CubeListBuilder.create().texOffs(90, 1007).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.01F, -9.1504F, 18.9124F, -2.789F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r6 = main_furled_lines.addOrReplaceChild("peak_halliard_r6", CubeListBuilder.create().texOffs(95, 1007).addBox(-0.5F, -4.0F, -0.2F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.01F, -9.5589F, 27.3972F, -2.2724F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r7 = main_furled_lines.addOrReplaceChild("peak_halliard_r7", CubeListBuilder.create().texOffs(15, 876).addBox(-0.49F, -146.0F, -0.5F, 1.0F, 147.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -122.7693F, -84.2033F, -2.3754F, 0.0F, 0.0F));

        PartDefinition cube_r3 = main_furled_lines.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(215, 1015).addBox(-4.9821F, -5.0429F, 47.0F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0429F, -2.4571F, -29.0648F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r4 = main_furled_lines.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(234, 1016).addBox(-1.5F, -2.0F, -0.5F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3535F, -5.6568F, 29.7852F, 0.0F, 0.0F, 0.7854F));

        PartDefinition main_boom_r5 = main_furled_lines.addOrReplaceChild("main_boom_r5", CubeListBuilder.create().texOffs(189, 1012).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition main_boom_r6 = main_furled_lines.addOrReplaceChild("main_boom_r6", CubeListBuilder.create().texOffs(202, 1013).addBox(-1.0F, -3.5F, -2.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition main_boom_furled = mainsail_furled.addOrReplaceChild("main_boom_furled", CubeListBuilder.create(), PartPose.offset(0.0F, -44.0F, 29.9148F));

        PartDefinition main_boom_r7 = main_boom_furled.addOrReplaceChild("main_boom_r7", CubeListBuilder.create().texOffs(154, 882).addBox(22.0F, -1.0F, -4.0F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(353, 90).addBox(-62.0F, -2.0F, -1.0F, 123.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition gaff_furled = mainsail_furled.addOrReplaceChild("gaff_furled", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -158.4182F, -33.5F, 0.0F, -0.0349F, 0.0F));

        PartDefinition gaff_furled_lines = gaff_furled.addOrReplaceChild("gaff_furled_lines", CubeListBuilder.create(), PartPose.offset(0.0875F, 87.792F, 30.0994F));

        PartDefinition mast2_r7 = gaff_furled_lines.addOrReplaceChild("mast2_r7", CubeListBuilder.create().texOffs(70, 919).addBox(-1.5F, -0.5F, -5.5F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -109.3989F, -25.2492F, 0.0F, -0.2182F, 0.0F));

        PartDefinition mast2_r8 = gaff_furled_lines.addOrReplaceChild("mast2_r8", CubeListBuilder.create().texOffs(202, 907).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -109.3989F, -25.2492F, -0.2618F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r8 = gaff_furled_lines.addOrReplaceChild("peak_halliard_r8", CubeListBuilder.create().texOffs(30, 913).addBox(-0.5F, -99.0F, -0.5F, 1.0F, 110.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6897F, -99.4675F, -21.2505F, -2.8453F, 0.0221F, 0.0F));

        PartDefinition shroud_starboard_r2 = gaff_furled_lines.addOrReplaceChild("shroud_starboard_r2", CubeListBuilder.create().texOffs(5, 866).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 157.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0875F, -108.3738F, -26.0994F, -0.0175F, 0.0F, 0.0175F));

        PartDefinition peak_halliard_r9 = gaff_furled_lines.addOrReplaceChild("peak_halliard_r9", CubeListBuilder.create().texOffs(35, 925).addBox(-0.5F, -49.0F, -1.0F, 1.0F, 98.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0875F, -50.0725F, -11.8727F, -2.7838F, 0.0F, 0.0F));

        PartDefinition bone = gaff_furled_lines.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r10 = bone.addOrReplaceChild("peak_halliard_r10", CubeListBuilder.create().texOffs(105, 1010).addBox(-0.5F, -5.7588F, -2.4659F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0875F, 6.109F, 7.496F, -2.8798F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r11 = bone.addOrReplaceChild("peak_halliard_r11", CubeListBuilder.create().texOffs(55, 991).addBox(-0.5F, -17.0765F, -2.274F, 1.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0875F, 2.0868F, 22.0193F, -1.789F, 0.0F, 0.0F));

        PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.4055F, 6.6242F, -0.6977F, -0.0387F, -0.0202F));

        PartDefinition mast2_r9 = bone2.addOrReplaceChild("mast2_r9", CubeListBuilder.create().texOffs(147, 907).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5542F, -2.0268F, -1.0472F, 0.0F, 0.0F));

        PartDefinition mast2_r10 = bone2.addOrReplaceChild("mast2_r10", CubeListBuilder.create().texOffs(213, 907).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5438F, 2.0713F, -1.0472F, 0.0F, 0.0F));

        PartDefinition mast2_r11 = bone2.addOrReplaceChild("mast2_r11", CubeListBuilder.create().texOffs(71, 905).addBox(-1.5F, -0.5F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0104F, -0.0446F, -0.2618F, 0.0F, 0.0F));

        PartDefinition gaff_furled_beam = gaff_furled.addOrReplaceChild("gaff_furled_beam", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition gaff_r3 = gaff_furled_beam.addOrReplaceChild("gaff_r3", CubeListBuilder.create().texOffs(319, 1016).addBox(-26.6967F, -3.6385F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(341, 1016).addBox(4.9533F, -3.6385F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(308, 1016).addBox(33.3033F, -3.6385F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(463, 78).addBox(-31.6967F, -3.1385F, -1.0F, 68.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 104.0299F, 31.7328F, 1.5708F, -1.5708F, -1.5708F));

        PartDefinition mast2_r12 = gaff_furled_beam.addOrReplaceChild("mast2_r12", CubeListBuilder.create().texOffs(67, 880).addBox(92.5F, -13.7926F, 2.0F, 9.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 883).addBox(94.5F, -14.7926F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(67, 888).addBox(92.5F, -13.7926F, -3.0F, 9.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 890).addBox(92.5F, -8.7926F, -2.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 199.9182F, 10.7074F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition rudder = partdefinition.addOrReplaceChild("rudder", CubeListBuilder.create(), PartPose.offset(0.0F, 11.1559F, 47.4742F));

        PartDefinition cube_r5 = rudder.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(327, 165).addBox(-1.0F, -11.0F, -0.5F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(126, 888).addBox(-1.0F, 3.0F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.5183F, -2.7718F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition cube_r6 = rudder.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(316, 169).addBox(-5.5F, -23.25F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(317, 184).addBox(-13.5F, -12.25F, -1.0F, 10.0F, 36.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.2985F, 9.965F, 1.5708F, -1.3963F, -1.5708F));

        PartDefinition rope_spiral = partdefinition.addOrReplaceChild("rope_spiral", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition spiral2 = rope_spiral.addOrReplaceChild("spiral2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spiral2_r1 = spiral2.addOrReplaceChild("spiral2_r1", CubeListBuilder.create().texOffs(115, 1011).addBox(-0.5F, -7.5F, -1.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.1857F, -11.2859F, 28.0757F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition spiral3 = rope_spiral.addOrReplaceChild("spiral3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spiral3_r1 = spiral3.addOrReplaceChild("spiral3_r1", CubeListBuilder.create().texOffs(125, 1014).addBox(-0.5F, -3.5F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.4335F, -11.2859F, 33.9855F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition spiral4 = rope_spiral.addOrReplaceChild("spiral4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spiral4_r1 = spiral4.addOrReplaceChild("spiral4_r1", CubeListBuilder.create().texOffs(130, 1015).addBox(-0.5F, -3.5F, -1.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.3519F, -11.2859F, 36.8682F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition spiral5 = rope_spiral.addOrReplaceChild("spiral5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spiral5_r1 = spiral5.addOrReplaceChild("spiral5_r1", CubeListBuilder.create().texOffs(140, 1016).addBox(-0.5F, 1.5F, -4.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.9214F, -11.2859F, 37.4292F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition spiral6 = rope_spiral.addOrReplaceChild("spiral6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spiral6_r1 = spiral6.addOrReplaceChild("spiral6_r1", CubeListBuilder.create().texOffs(145, 1017).addBox(-0.5F, 0.5F, -4.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.8041F, -11.2859F, 32.5109F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition spiral7 = rope_spiral.addOrReplaceChild("spiral7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spiral7_r1 = spiral7.addOrReplaceChild("spiral7_r1", CubeListBuilder.create().texOffs(150, 1018).addBox(-0.5F, -5.5F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.8772F, -11.2859F, 29.4976F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition spiral8 = rope_spiral.addOrReplaceChild("spiral8", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spiral8_r1 = spiral8.addOrReplaceChild("spiral8_r1", CubeListBuilder.create().texOffs(155, 1019).addBox(-0.5F, -2.0F, 4.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.8772F, -11.2859F, 29.4976F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition spiral9 = rope_spiral.addOrReplaceChild("spiral9", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spiral9_r1 = spiral9.addOrReplaceChild("spiral9_r1", CubeListBuilder.create().texOffs(160, 1020).addBox(-0.5F, -4.5F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.6604F, -11.2859F, 35.4463F, 1.7017F, 0.0F, 1.5708F));

        PartDefinition spiral10 = rope_spiral.addOrReplaceChild("spiral10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spiral10_r1 = spiral10.addOrReplaceChild("spiral10_r1", CubeListBuilder.create().texOffs(165, 1021).addBox(-0.5F, -1.0F, 3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.6604F, -11.2859F, 35.4463F, -3.0107F, 0.0F, 1.5708F));

        PartDefinition spiral11 = rope_spiral.addOrReplaceChild("spiral11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.416F));

        PartDefinition spiral11_r1 = spiral11.addOrReplaceChild("spiral11_r1", CubeListBuilder.create().texOffs(170, 1022).addBox(-0.5F, 2.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.6604F, -11.2859F, 35.8623F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition jibsheet_transform_checker = partdefinition.addOrReplaceChild("jibsheet_transform_checker", CubeListBuilder.create().texOffs(60, 990).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition mainsheet_main = partdefinition.addOrReplaceChild("mainsheet_main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition mainsheet = mainsheet_main.addOrReplaceChild("mainsheet", CubeListBuilder.create().texOffs(85, 1006).addBox(-0.5F, -16.3058F, 0.2108F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -26.0F, 52.0F));

        PartDefinition traveller = mainsheet_main.addOrReplaceChild("traveller", CubeListBuilder.create(), PartPose.offset(-12.0F, -24.8058F, 54.0699F));

        PartDefinition cube_r7 = traveller.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(105, 891).addBox(8.0F, -1.1942F, 0.9301F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r8 = traveller.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(91, 901).addBox(11.0F, -3.6942F, -1.5699F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, -0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition traveller_connector = mainsheet_main.addOrReplaceChild("traveller_connector", CubeListBuilder.create(), PartPose.offsetAndRotation(24.0F, -20.8058F, 51.0699F, 0.0F, 0.1309F, 0.0F));

        PartDefinition mainsheet_r1 = traveller_connector.addOrReplaceChild("mainsheet_r1", CubeListBuilder.create().texOffs(70, 999).addBox(-1.9301F, -24.6089F, -2.8356F, 1.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.0F, 1.0F, 3.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition other = mainsheet_main.addOrReplaceChild("other", CubeListBuilder.create(), PartPose.offset(0.0F, -24.8058F, 54.0699F));

        PartDefinition cube_r9 = other.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(88, 888).mirror().addBox(-1.5F, -0.904F, -4.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(19.0958F, -0.0344F, -0.1942F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r10 = other.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(137, 887).addBox(-1.5F, -0.904F, -4.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0958F, -0.0344F, -0.1942F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r11 = other.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(108, 905).addBox(-0.8058F, 11.5272F, 11.4784F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 2.3562F));

        PartDefinition cube_r12 = other.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(88, 880).addBox(-2.5F, -1.0F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.0988F, 3.5558F, -5.5492F, -0.6109F, 0.0F, -1.5708F));

        PartDefinition cube_r13 = other.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(121, 905).addBox(-1.25F, -2.75F, -1.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(25.0F, 3.3058F, -5.5699F, -0.6109F, 0.0F, -1.5708F));

        PartDefinition cube_r14 = other.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(134, 905).addBox(-1.1942F, 11.5272F, 11.4784F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -2.3562F));

        PartDefinition mainsheet_r2 = other.addOrReplaceChild("mainsheet_r2", CubeListBuilder.create().texOffs(45, 985).addBox(1.3199F, -19.0F, -0.3591F, 1.0F, 38.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 985).addBox(-2.4301F, -19.0F, -0.3591F, 1.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r3 = other.addOrReplaceChild("mainsheet_r3", CubeListBuilder.create().texOffs(120, 1013).addBox(1.378F, 1.196F, -0.516F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.06F, 10.6709F, -16.7444F, -1.3963F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r4 = other.addOrReplaceChild("mainsheet_r4", CubeListBuilder.create().texOffs(110, 1010).addBox(2.348F, -2.62F, -0.484F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.4649F, 3.5699F, -14.5405F, -1.3031F, -0.8547F, 1.3667F));

        PartDefinition mainsheet_r5 = other.addOrReplaceChild("mainsheet_r5", CubeListBuilder.create().texOffs(135, 1016).addBox(-0.5F, -11.0F, 0.75F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(25.6091F, 3.5699F, -15.25F, -1.3963F, 0.0F, 1.5708F));

        PartDefinition waterocclusion = partdefinition.addOrReplaceChild("waterocclusion", CubeListBuilder.create(), PartPose.offset(0.0F, -29.5F, 32.2074F));

        PartDefinition waterocclusion_r1 = waterocclusion.addOrReplaceChild("waterocclusion_r1", CubeListBuilder.create().texOffs(314, 963).addBox(-37.0F, -4.0F, -4.5F, 58.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.0623F, 46.5F, -26.5199F, 0.0F, 1.4835F, 0.0F));

        PartDefinition waterocclusion_r2 = waterocclusion.addOrReplaceChild("waterocclusion_r2", CubeListBuilder.create().texOffs(315, 964).addBox(-53.0F, -4.0F, -25.5F, 43.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 46.5F, -24.7926F, 0.0F, -1.0472F, 0.0F));

        PartDefinition waterocclusion_r3 = waterocclusion.addOrReplaceChild("waterocclusion_r3", CubeListBuilder.create().texOffs(431, 965).addBox(-25.7926F, 42.5F, -8.0F, 5.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(314, 963).addBox(-22.7926F, 42.5F, -8.0F, 22.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(303, 952).addBox(-3.7926F, 42.5F, -19.0F, 68.0F, 8.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -55.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition waterocclusion_r4 = waterocclusion.addOrReplaceChild("waterocclusion_r4", CubeListBuilder.create().texOffs(303, 952).mirror().addBox(-64.2074F, 42.5F, -19.0F, 68.0F, 8.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(314, 963).mirror().addBox(0.7926F, 42.5F, -8.0F, 22.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(431, 965).mirror().addBox(20.7926F, 42.5F, -8.0F, 5.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -55.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition waterocclusion_r5 = waterocclusion.addOrReplaceChild("waterocclusion_r5", CubeListBuilder.create().texOffs(315, 964).mirror().addBox(10.0F, -4.0F, -25.5F, 43.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.5F, 46.5F, -24.7926F, 0.0F, 1.0472F, 0.0F));

        PartDefinition waterocclusion_r6 = waterocclusion.addOrReplaceChild("waterocclusion_r6", CubeListBuilder.create().texOffs(333, 937).mirror().addBox(-21.0F, -4.0F, -4.5F, 58.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-21.0623F, 46.5F, -26.5199F, 0.0F, -1.4835F, 0.0F));

        PartDefinition jibsail_deployed = partdefinition.addOrReplaceChild("jibsail_deployed", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -17.0F, -110.0F, -0.5004F, 0.0F, 0.0F));

        PartDefinition jibsail = jibsail_deployed.addOrReplaceChild("jibsail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition jibsheet = jibsail.addOrReplaceChild("jibsheet", CubeListBuilder.create(), PartPose.offset(0.0F, -21.0F, 73.0F));

        PartDefinition jibsail_furled = partdefinition.addOrReplaceChild("jibsail_furled", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r15 = jibsail_furled.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(1000, 975).addBox(-14.0F, -7.0F, -14.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(996, 984).addBox(-13.0F, -5.0F, -13.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(992, 994).addBox(-12.0F, -3.0F, -12.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(988, 1005).addBox(-11.0F, -1.0F, -11.0F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, -43.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition windlass = partdefinition.addOrReplaceChild("windlass", CubeListBuilder.create(), PartPose.offsetAndRotation(14.5F, 6.5833F, -29.75F, 0.0F, 0.5236F, 0.0F));

        PartDefinition spool = windlass.addOrReplaceChild("spool", CubeListBuilder.create().texOffs(57, 935).addBox(-1.5F, -1.5F, -3.5F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -0.0833F, -5.75F));

        PartDefinition crank_arm = windlass.addOrReplaceChild("crank_arm", CubeListBuilder.create().texOffs(64, 985).addBox(-6.0F, -0.5F, 5.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(57, 985).addBox(-1.0F, -1.0F, 4.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 981).addBox(-8.0F, -0.5F, 4.5F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -0.0833F, -5.75F));

        PartDefinition bone4 = windlass.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(56, 969).addBox(-0.1986F, -2.25F, 1.125F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(68, 969).addBox(-0.1986F, -2.25F, -6.875F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.8014F, 0.1667F, -3.375F));

        PartDefinition cube_r16 = bone4.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(57, 975).addBox(-0.1986F, 1.75F, -6.859F, 3.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(68, 975).addBox(-0.1986F, 1.75F, 1.109F, 3.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition bone3 = windlass.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 0.0F));

        PartDefinition bone5 = bone3.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(63, 949).addBox(9.4113F, -5.2027F, -6.016F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(62, 963).addBox(8.4833F, -4.5667F, -6.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0167F, -0.0167F, -0.25F));

        PartDefinition cube_r17 = bone5.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(64, 956).addBox(-1.0F, -2.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.4833F, -1.0667F, -5.5F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r18 = bone5.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(55, 946).addBox(-5.5F, 0.8F, -0.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9833F, -4.0667F, -5.5F, 0.0F, 0.0F, -0.3491F));

        PartDefinition static_parts = partdefinition.addOrReplaceChild("static_parts", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition sided = static_parts.addOrReplaceChild("sided", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition port = sided.addOrReplaceChild("port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shrouds_port = port.addOrReplaceChild("shrouds_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shroud_starboard_r3 = shrouds_port.addOrReplaceChild("shroud_starboard_r3", CubeListBuilder.create().texOffs(60, 995).addBox(0.0F, -27.5F, 0.0F, 1.0F, 28.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.7094F, -157.5834F, -34.0664F, 0.0F, 0.0F, -0.4712F));

        PartDefinition shroud_starboard_r4 = shrouds_port.addOrReplaceChild("shroud_starboard_r4", CubeListBuilder.create().texOffs(25, 880).addBox(-0.5F, -145.0F, -0.5F, 1.0F, 143.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.946F, -12.4205F, -27.8829F, 0.0393F, 0.0F, -0.0524F));

        PartDefinition mast2_r13 = shrouds_port.addOrReplaceChild("mast2_r13", CubeListBuilder.create().texOffs(179, 188).addBox(-104.5F, -11.7926F, 2.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, 0.0F, 1.5708F));

        PartDefinition shackle = shrouds_port.addOrReplaceChild("shackle", CubeListBuilder.create().texOffs(46, 923).addBox(-14.85F, -12.75F, -31.95F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r19 = shackle.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(40, 918).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.1F, -14.25F, -31.45F, 0.0F, -0.4363F, 0.0F));

        PartDefinition sidewall_port = port.addOrReplaceChild("sidewall_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r20 = sidewall_port.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(139, 131).addBox(-65.2487F, 48.5F, 23.8121F, 59.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(139, 122).addBox(-54.2487F, 45.5F, 24.8121F, 49.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(139, 113).addBox(-53.2487F, 41.5F, 26.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(139, 104).addBox(-53.2487F, 37.5F, 30.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, 1.4835F, 0.0F));

        PartDefinition cube_r21 = sidewall_port.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 29).addBox(-68.2074F, 49.484F, 21.032F, 13.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-69.2074F, 45.484F, 20.032F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(-70.2074F, 41.484F, 22.032F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-68.2074F, 35.484F, 26.032F, 13.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, 1.5708F, 0.0F));

        PartDefinition sidewall_port_bow = port.addOrReplaceChild("sidewall_port_bow", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r22 = sidewall_port_bow.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 104).addBox(16.7511F, 34.282F, 22.5146F, 26.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 138).addBox(-21.0689F, 48.282F, 15.1946F, 51.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 129).addBox(-20.8289F, 45.282F, 16.5946F, 53.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 120).addBox(-21.9729F, 41.282F, 18.2266F, 57.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.275F, -22.7926F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r23 = sidewall_port_bow.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 110).addBox(-24.2649F, 36.282F, 21.5146F, 65.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.275F, -22.7926F, 3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r24 = sidewall_port_bow.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 148).addBox(-23.2F, -2.032F, -2.3F, 46.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.276F, -0.2728F, -33.5544F, 3.0181F, 1.0254F, 3.014F));

        PartDefinition stern_railing_port = port.addOrReplaceChild("stern_railing_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r25 = stern_railing_port.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 157).addBox(-5.25F, -1.0F, -0.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5232F, -20.7069F, 48.5199F, -1.5708F, -0.3054F, -1.5708F));

        PartDefinition cube_r26 = stern_railing_port.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(0, 165).addBox(0.5F, 3.0F, -0.5F, 15.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(25.1811F, -24.5F, 36.5199F, -1.6623F, 0.3042F, 1.5433F));

        PartDefinition cube_r27 = stern_railing_port.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(139, 146).addBox(-5.5F, -1.0F, -1.0F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(25.0517F, -24.75F, 37.0029F, 3.1416F, -1.4835F, 3.1416F));

        PartDefinition cube_r28 = stern_railing_port.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(139, 151).addBox(-24.0F, -0.99F, -0.75F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.75F, 49.9148F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition bow_railing_port = port.addOrReplaceChild("bow_railing_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r29 = bow_railing_port.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(105, 887).addBox(-5.5232F, -1.2931F, 0.4801F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9768F, -20.7069F, -66.4801F, -1.5708F, -0.3054F, -1.5708F));

        PartDefinition cube_r30 = bow_railing_port.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(139, 141).addBox(-5.0F, -1.282F, 27.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -23.702F, -37.0852F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r31 = bow_railing_port.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(0, 161).addBox(-5.25F, -1.0F, -0.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.5232F, -20.7069F, -28.5199F, 1.0264F, 0.2635F, -1.7272F));

        PartDefinition cube_r32 = bow_railing_port.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(139, 156).addBox(-9.4204F, -3.58F, -2.1204F, 43.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.8961F, -21.418F, -37.07F, -3.1416F, 1.0472F, -3.1416F));

        PartDefinition deck_port = port.addOrReplaceChild("deck_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r33 = deck_port.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(159, 77).addBox(-66.2074F, 42.5F, 0.0F, 27.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(171, 62).addBox(-39.2074F, 42.5F, 13.0F, 13.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(122, 174).addBox(-26.2074F, 42.5F, 0.0F, 2.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(170, 46).addBox(-24.2074F, 42.5F, 13.0F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(63, 173).addBox(-11.2074F, 42.5F, 0.0F, 2.0F, 2.0F, 27.0F, new CubeDeformation(0.0F))
                .texOffs(169, 29).addBox(-9.2074F, 42.5F, 13.0F, 13.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(164, 6).addBox(3.7926F, 42.5F, 0.0F, 26.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cleats_port = port.addOrReplaceChild("cleats_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cleat_port_aft2 = cleats_port.addOrReplaceChild("cleat_port_aft2", CubeListBuilder.create(), PartPose.offset(28.0F, -17.8173F, -21.3052F));

        PartDefinition cube_r34 = cleat_port_aft2.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(63, 922).addBox(62.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, -40.1667F, -64.2074F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r35 = cleat_port_aft2.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(63, 925).addBox(62.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 914).addBox(58.2074F, 39.0F, 27.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, -40.1667F, -61.2074F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cleat_port_aft = cleats_port.addOrReplaceChild("cleat_port_aft", CubeListBuilder.create(), PartPose.offset(56.0F, -59.0F, -25.7926F));

        PartDefinition cube_r36 = cleat_port_aft.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(63, 931).addBox(62.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r37 = cleat_port_aft.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(63, 928).addBox(62.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 918).addBox(58.2074F, 39.0F, 27.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition hull_port = port.addOrReplaceChild("hull_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r38 = hull_port.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(22, 2).addBox(-16.2101F, 51.9196F, -9.6088F, 22.0F, 4.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -3.1416F, -0.0873F, 3.0107F));

        PartDefinition cube_r39 = hull_port.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(0, 169).addBox(-2.0F, -2.896F, -13.628F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0714F, -0.7252F, -12.5852F, 0.0F, 1.5708F, -0.1745F));

        PartDefinition cube_r40 = hull_port.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(0, 42).addBox(-11.0F, -2.0F, -28.5F, 22.0F, 4.0F, 57.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5434F, -0.2772F, 15.8503F, 3.1372F, 0.0873F, 3.0107F));

        PartDefinition transom_port = port.addOrReplaceChild("transom_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r41 = transom_port.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(0, 81).addBox(-74.7714F, 30.8908F, 23.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.628F, -22.6806F, -1.5708F, 1.4399F, -1.5708F));

        PartDefinition cube_r42 = transom_port.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(0, 57).addBox(-75.7714F, 31.0508F, 14.0F, 6.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.788F, -22.6806F, -1.5708F, 1.4399F, -1.5708F));

        PartDefinition cube_r43 = transom_port.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(102, 56).addBox(-14.0F, 30.8908F, -76.7714F, 14.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.628F, -22.6806F, -3.0107F, 0.0F, -3.1416F));

        PartDefinition cube_r44 = transom_port.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(102, 42).addBox(-15.0F, 33.5F, -72.2074F, 15.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r45 = transom_port.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(0, 36).addBox(-71.2074F, 34.5F, 15.0F, 4.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, 1.5708F, 0.0F));

        PartDefinition starboard = sided.addOrReplaceChild("starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shrouds_starboard = starboard.addOrReplaceChild("shrouds_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shroud_starboard_r5 = shrouds_starboard.addOrReplaceChild("shroud_starboard_r5", CubeListBuilder.create().texOffs(65, 995).addBox(-1.0F, -27.5F, 0.0F, 1.0F, 28.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.7094F, -157.5834F, -34.0664F, 0.0F, 0.0F, 0.4712F));

        PartDefinition shroud_starboard_r6 = shrouds_starboard.addOrReplaceChild("shroud_starboard_r6", CubeListBuilder.create().texOffs(20, 880).addBox(-0.5F, -145.0F, -0.5F, 1.0F, 143.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.946F, -12.4205F, -27.8829F, 0.0393F, 0.0F, 0.0524F));

        PartDefinition mast2_r14 = shrouds_starboard.addOrReplaceChild("mast2_r14", CubeListBuilder.create().texOffs(817, 188).addBox(102.5F, -11.7926F, 2.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition shackle3 = shrouds_starboard.addOrReplaceChild("shackle3", CubeListBuilder.create().texOffs(46, 915).addBox(13.85F, -12.75F, -31.95F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r46 = shackle3.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(40, 910).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.1F, -14.25F, -31.45F, 0.0F, 0.4363F, 0.0F));

        PartDefinition sidewall_starboard = starboard.addOrReplaceChild("sidewall_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r47 = sidewall_starboard.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(759, 131).addBox(6.2487F, 48.5F, 23.8121F, 59.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(779, 122).addBox(5.2487F, 45.5F, 24.8121F, 49.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(781, 113).addBox(5.2487F, 41.5F, 26.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(781, 104).addBox(5.2487F, 37.5F, 30.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.4835F, 0.0F));

        PartDefinition cube_r48 = sidewall_starboard.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(994, 29).addBox(55.2074F, 49.484F, 21.032F, 13.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(990, 20).addBox(56.2074F, 45.484F, 20.032F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(986, 11).addBox(55.2074F, 41.484F, 22.032F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(990, 0).addBox(55.2074F, 35.484F, 26.032F, 13.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition sidewall_starboard_bow = starboard.addOrReplaceChild("sidewall_starboard_bow", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r49 = sidewall_starboard_bow.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(966, 104).addBox(-42.7511F, 34.282F, 22.5146F, 26.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(914, 138).addBox(-29.9311F, 48.282F, 15.1946F, 51.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(910, 129).addBox(-32.1711F, 45.282F, 16.5946F, 53.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(902, 120).addBox(-35.0271F, 41.282F, 18.2266F, 57.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.275F, -22.7926F, -3.1416F, -1.0472F, -3.1416F));

        PartDefinition cube_r50 = sidewall_starboard_bow.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(886, 110).addBox(-40.7351F, 36.282F, 21.5146F, 65.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.275F, -22.7926F, 3.1416F, -1.0472F, -3.1416F));

        PartDefinition cube_r51 = sidewall_starboard_bow.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(924, 148).addBox(-22.8F, -2.032F, -2.3F, 46.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.276F, -0.2728F, -33.5544F, 3.0181F, -1.0254F, -3.014F));

        PartDefinition stern_railing_starboard = starboard.addOrReplaceChild("stern_railing_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r52 = stern_railing_starboard.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(1004, 161).addBox(-3.75F, -1.0F, -0.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5232F, -20.7069F, 48.5199F, -1.5708F, 0.3054F, 1.5708F));

        PartDefinition cube_r53 = stern_railing_starboard.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(992, 165).addBox(-15.5F, 3.0F, -0.5F, 15.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.1811F, -24.5F, 36.5199F, -1.6623F, -0.3042F, -1.5433F));

        PartDefinition cube_r54 = stern_railing_starboard.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(832, 146).addBox(-18.5F, -1.0F, -1.0F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.0517F, -24.75F, 37.0029F, 3.1416F, 1.4835F, -3.1416F));

        PartDefinition cube_r55 = stern_railing_starboard.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(832, 151).addBox(0.0F, -0.99F, -0.75F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.75F, 49.9148F, -3.1416F, 0.0F, -3.1416F));

        PartDefinition bow_railing_starboard = starboard.addOrReplaceChild("bow_railing_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r56 = bow_railing_starboard.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(872, 141).addBox(1.0F, -1.282F, 27.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -23.702F, -37.0852F, -3.1416F, 0.0F, -3.1416F));

        PartDefinition cube_r57 = bow_railing_starboard.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(1004, 157).addBox(-3.75F, -1.0F, -0.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.5232F, -20.7069F, -28.5199F, 1.0264F, -0.2635F, 1.7272F));

        PartDefinition cube_r58 = bow_railing_starboard.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(793, 156).addBox(-33.5796F, -3.58F, -2.1204F, 43.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.8961F, -21.418F, -37.07F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition deck_starboard = starboard.addOrReplaceChild("deck_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r59 = deck_starboard.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(763, 77).addBox(39.2074F, 42.5F, 0.0F, 27.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(803, 62).addBox(26.2074F, 42.5F, 13.0F, 13.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(846, 174).addBox(24.2074F, 42.5F, 0.0F, 2.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(802, 46).addBox(11.2074F, 42.5F, 13.0F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(903, 173).addBox(9.2074F, 42.5F, 0.0F, 2.0F, 2.0F, 27.0F, new CubeDeformation(0.0F))
                .texOffs(801, 29).addBox(-3.7926F, 42.5F, 13.0F, 13.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(769, 6).addBox(-29.7926F, 42.5F, 0.0F, 26.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cleats_starboard = starboard.addOrReplaceChild("cleats_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cleat_port_aft3 = cleats_starboard.addOrReplaceChild("cleat_port_aft3", CubeListBuilder.create(), PartPose.offset(-28.0F, -17.8173F, -21.3052F));

        PartDefinition cube_r60 = cleat_port_aft3.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(53, 922).addBox(-63.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-28.0F, -40.1667F, -64.2074F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r61 = cleat_port_aft3.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(53, 925).addBox(-63.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 910).addBox(-64.2074F, 39.0F, 27.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-28.0F, -40.1667F, -61.2074F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cleat_port_aft4 = cleats_starboard.addOrReplaceChild("cleat_port_aft4", CubeListBuilder.create(), PartPose.offset(-56.0F, -59.0F, -25.7926F));

        PartDefinition cube_r62 = cleat_port_aft4.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(53, 931).addBox(-63.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r63 = cleat_port_aft4.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(53, 928).addBox(-63.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 906).addBox(-64.2074F, 39.0F, 27.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition hull_starboard = starboard.addOrReplaceChild("hull_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r64 = hull_starboard.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(888, 2).addBox(-5.7899F, 51.9196F, -9.6088F, 22.0F, 4.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -3.1416F, 0.0873F, -3.0107F));

        PartDefinition cube_r65 = hull_starboard.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(962, 169).addBox(-2.0F, -2.896F, -13.628F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.0714F, -0.7252F, -12.5852F, 0.0F, -1.5708F, 0.1745F));

        PartDefinition cube_r66 = hull_starboard.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(866, 42).addBox(-11.0F, -2.0F, -28.5F, 22.0F, 4.0F, 57.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.5434F, -0.2772F, 15.8503F, 3.1372F, -0.0873F, -3.0107F));

        PartDefinition transom_starboard = starboard.addOrReplaceChild("transom_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r67 = transom_starboard.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(1008, 81).addBox(69.7714F, 30.8908F, 23.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.628F, -22.6806F, -1.5708F, -1.4399F, 1.5708F));

        PartDefinition cube_r68 = transom_starboard.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(994, 57).addBox(-3.0F, -7.0F, -4.5F, 6.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.5F, -6.5642F, 44.5016F, -1.5708F, -1.4399F, 1.5708F));

        PartDefinition cube_r69 = transom_starboard.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(880, 56).addBox(-7.0F, -7.0F, -3.5F, 14.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -6.4975F, 45.0182F, -3.0107F, 0.0F, 3.1416F));

        PartDefinition cube_r70 = transom_starboard.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(882, 42).addBox(0.0F, 33.5F, -72.2074F, 15.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -3.1416F, 0.0F, -3.1416F));

        PartDefinition cube_r71 = transom_starboard.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(990, 36).addBox(67.2074F, 34.5F, 15.0F, 4.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition non_sided = static_parts.addOrReplaceChild("non_sided", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition forestay = non_sided.addOrReplaceChild("forestay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition forestay_r1 = forestay.addOrReplaceChild("forestay_r1", CubeListBuilder.create().texOffs(0, 858).addBox(-0.5F, -170.0F, -0.5F, 1.0F, 165.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -34.8348F, -113.8031F, -0.5004F, 0.0F, 0.0F));

        PartDefinition shackle2 = forestay.addOrReplaceChild("shackle2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r72 = shackle2.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(46, 931).addBox(-0.5F, -0.75F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.3256F, -112.4696F, -0.48F, 0.0F, 0.0F));

        PartDefinition cube_r73 = shackle2.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(40, 926).addBox(-1.5F, -5.75F, 2.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.25F, -116.5F, -0.5004F, 0.0F, 0.0F));

        PartDefinition keel = non_sided.addOrReplaceChild("keel", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r74 = keel.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(305, 140).addBox(-26.7926F, 49.5F, -3.0F, 95.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r75 = keel.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(427, 185).addBox(-33.2596F, -83.259F, -0.5F, 11.0F, 36.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -1.5708F, -1.2217F, -1.5708F));

        PartDefinition cube_r76 = keel.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(358, 166).addBox(0.1907F, -66.0673F, -0.5F, 46.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -1.5708F, -1.4399F, -1.5708F));

        PartDefinition cube_r77 = keel.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(395, 184).addBox(-2.7926F, -89.588F, -1.0F, 12.0F, 36.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.5708F, 3.1416F));

        PartDefinition mast = non_sided.addOrReplaceChild("mast", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition halliard_tie_r1 = mast.addOrReplaceChild("halliard_tie_r1", CubeListBuilder.create().texOffs(251, 1014).addBox(123.5F, -13.2926F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(266, 1014).addBox(112.5F, -13.2926F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(470, 31).addBox(-49.5F, -13.7926F, -3.0F, 15.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition main_mast = mast.addOrReplaceChild("main_mast", CubeListBuilder.create(), PartPose.offset(0.0F, -53.5F, -22.7926F));

        PartDefinition mast2_r15 = main_mast.addOrReplaceChild("mast2_r15", CubeListBuilder.create().texOffs(393, 0).addBox(38.5F, -12.7926F, -2.0F, 94.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(402, 15).addBox(-51.5F, -12.7926F, -2.0F, 90.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition mast_r1 = main_mast.addOrReplaceChild("mast_r1", CubeListBuilder.create().texOffs(380, 16).addBox(-1.0F, -3.0F, -5.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(373, 22).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(356, 8).addBox(-1.0F, 4.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(356, 16).addBox(-1.0F, 3.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(356, 0).addBox(-1.0F, -5.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(356, 24).addBox(-1.0F, -4.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(380, 24).addBox(-1.0F, -3.0F, 4.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(373, 12).addBox(-1.0F, -4.0F, 3.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 30.5F, -10.7926F, 2.3562F, 0.0F, -1.5708F));

        PartDefinition mast_r2 = main_mast.addOrReplaceChild("mast_r2", CubeListBuilder.create().texOffs(356, 32).addBox(-1.01F, 0.75F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(370, 32).addBox(-1.01F, -4.75F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 29.5F, -10.7926F, -1.5708F, 0.0F, -1.5708F));

        PartDefinition mast_r3 = main_mast.addOrReplaceChild("mast_r3", CubeListBuilder.create().texOffs(377, 32).addBox(-1.01F, 0.75F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(363, 32).addBox(-1.01F, -4.75F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 29.5F, -10.7926F, 0.0F, 0.0F, -1.5708F));

        PartDefinition bowsprit = non_sided.addOrReplaceChild("bowsprit", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r78 = bowsprit.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(503, 112).addBox(-3.0F, 34.0F, 39.7926F, 6.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r79 = bowsprit.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(571, 192).addBox(-13.717F, -51.8757F, -11.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(571, 198).addBox(-14.717F, -51.8757F, -10.0F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(530, 190).addBox(-14.717F, -51.8757F, -8.0F, 18.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(530, 201).addBox(4.283F, -51.8757F, -8.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(533, 165).addBox(-14.717F, -51.8757F, -6.0F, 33.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(530, 174).addBox(-15.717F, -52.8757F, -5.0F, 34.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(530, 180).addBox(-11.717F, -55.8757F, -2.0F, 31.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(560, 131).addBox(-4.717F, -59.8757F, -2.0F, 78.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(514, 146).addBox(-3.717F, -64.8757F, -2.0F, 36.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, 1.0908F, -1.5708F));

        PartDefinition cube_r80 = bowsprit.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(530, 198).addBox(-18.283F, -51.8757F, -8.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(533, 162).addBox(-18.283F, -51.8757F, -6.0F, 33.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(530, 168).addBox(-18.283F, -52.8757F, -5.0F, 34.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(530, 194).addBox(-3.283F, -51.8757F, -8.0F, 18.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(571, 195).addBox(3.717F, -51.8757F, -10.0F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(571, 190).addBox(10.717F, -51.8757F, -11.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, -1.0908F, 1.5708F));

        PartDefinition cube_r81 = bowsprit.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(566, 141).addBox(23.3335F, -65.4135F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(599, 149).addBox(23.3335F, -68.4135F, -1.0F, 38.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, 0.9599F, -1.5708F));

        PartDefinition hold_netting = non_sided.addOrReplaceChild("hold_netting", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r82 = hold_netting.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(89, 972).addBox(-4.2926F, 42.0F, -13.5F, 14.0F, 1.0F, 27.0F, new CubeDeformation(0.0F))
                .texOffs(89, 943).addBox(10.7074F, 42.0F, -13.5F, 14.0F, 1.0F, 27.0F, new CubeDeformation(0.0F))
                .texOffs(89, 914).addBox(25.7074F, 42.0F, -13.5F, 14.0F, 1.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition telltail = partdefinition.addOrReplaceChild("telltail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -159.92F, -33.6F, 0.0F, 0.0F, 0.0F));

        PartDefinition telltail_part_4 = telltail.addOrReplaceChild("telltail_part_4", CubeListBuilder.create().texOffs(678, 1019).addBox(-0.5F, -2.0F, -6.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 14.6F));

        PartDefinition telltail_part_3 = telltail.addOrReplaceChild("telltail_part_3", CubeListBuilder.create().texOffs(678, 1019).addBox(-0.5F, -2.0F, 1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.6F));

        PartDefinition telltail_part_2 = telltail.addOrReplaceChild("telltail_part_2", CubeListBuilder.create().texOffs(678, 1019).addBox(-0.5F, -2.0F, 5.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.4F));

        PartDefinition telltail_part_1 = telltail.addOrReplaceChild("telltail_part_1", CubeListBuilder.create().texOffs(678, 1019).addBox(-0.5F, -2.0F, 8.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -8.4F));

        PartDefinition telltail_part_5 = telltail.addOrReplaceChild("telltail_part_5", CubeListBuilder.create().texOffs(678, 1019).addBox(-0.5F, -2.0F, 11.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.6F));

        PartDefinition telltail_part_6 = telltail.addOrReplaceChild("telltail_part_6", CubeListBuilder.create().texOffs(678, 1019).addBox(-0.5F, -2.0F, 14.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.6F));

        // GENERATED MODEL PARTS, DO NOT REPLACE FROM BLOCKBENCH
        {
            generateMainsail(mainsail);
            generateJibsail(jibsail_deployed);
        }

        return LayerDefinition.create(meshdefinition, 1024, 1024);
    }

    private static final int mainsail_length = 120;
    private static final int mainsail_height = 120;
    private static final int mainsail_horizontal_sections = 30;

    private static final int mainsail_vertical_sections = 5;

    private static final int mainsail_section_widths = mainsail_length / mainsail_horizontal_sections;

    private static final int mainsail_section_heights = mainsail_height / mainsail_vertical_sections;

    private static void generateMainsail(PartDefinition mainsail_main) {

        for (int zindex = 0; zindex < mainsail_horizontal_sections; zindex++) {
            float zposition = (zindex * mainsail_section_widths);
            float height = 102;
            if (zindex < mainsail_horizontal_sections / 2) {
                height += ((zindex * mainsail_section_widths) / 3.0f);
            } else {
                height = mainsail_height;
                height -= ((zindex * mainsail_section_widths) - 60) * 2;
            }
            float yorigin = -4;
            for (int yindex = 0; yindex < mainsail_vertical_sections; yindex++) {
                yorigin = (yindex * mainsail_section_heights);
                float section_height = mainsail_section_heights;
                if (yindex == mainsail_vertical_sections - 1 && zindex <= mainsail_horizontal_sections / 2) {
                    section_height = 6 + zindex;
                    yorigin += section_height - mainsail_section_heights;
                } else {
                    if (height - (yorigin) <= mainsail_section_heights && height - (yorigin) > 0) {
                        section_height = (float) Math.floor(height - yorigin);
                        yorigin += section_height - mainsail_section_heights;
                    } else if (height - (yorigin) <= 0) {
                        break;
                    }
                }

                int textureOffsetx = 673 + zindex * mainsail_section_widths;
                int textureOffsetY = (996 - (int) yorigin);

                String name = "mainsail_part_" + zindex + "_" + yindex;
                mainsail_main.addOrReplaceChild(name, CubeListBuilder.create().texOffs(textureOffsetx, textureOffsetY).addBox(-1F, -yorigin - mainsail_section_heights - 4f, zposition + 0.5f, 2, section_height, mainsail_section_widths, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
            }
        }
    }

    private static final int jibsail_length = 52;
    private static final int jibsail_height = 120;
    private static final int jibsail_horizontal_sections = 13;
    private static final int jibsail_vertical_sections = 1;
    private static final int jibsail_section_widths = jibsail_length / jibsail_horizontal_sections;
    private static final int jibsail_section_heights = jibsail_height / jibsail_vertical_sections;

    private static void generateJibsail(PartDefinition jibsail_main) {

        for (int zindex = 0; zindex < jibsail_horizontal_sections; zindex++) {
            float zposition = (zindex * jibsail_section_widths);
            float height = jibsail_section_heights / 4.0f + jibsail_section_heights;
            height += zindex * (jibsail_section_heights / 4.0f);
            for (int yindex = 0; yindex < jibsail_vertical_sections; yindex++) {
                float section_height = (jibsail_section_heights - (9f * zindex));
                float yorigin = (yindex * section_height) + section_height + zindex * 3;

                /*if (height - (yorigin) <= jibsail_section_heights && height - (yorigin) > 0) {
                    section_height = Math.round(height - yorigin);
                    yorigin += section_height - jibsail_section_heights;
                } else if (height - (yorigin) <= 0) {
                    break;
                }*/

                int textureOffsetx = 673 + zindex * jibsail_section_widths;
                int textureOffsetY = (996 - (int) yorigin);

                String name = "jibsail_part_" + zindex + "_" + yindex;
                jibsail_main.getChild("jibsail").addOrReplaceChild(name, CubeListBuilder.create()
                                .texOffs(textureOffsetx, textureOffsetY)
                                .addBox(-1F, -yorigin, zposition, 2, section_height, jibsail_section_widths, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0F, 0.0F, 0.0F));
            }
        }
    }


    private static void animateMainsail(SloopEntity pBoat, float pPartialTicks, ModelPart mainsail_main, ModelPart mainsail, ModelPart[][] sails, float mastRotation, int animationTick) {

        if (pBoat.getMainsailActive()) {
            mainsail_main.yRot  = mastRotation;
            mainsail.yRot  = mastRotation;

            float windWorldAngle = Mth.wrapDegrees(pBoat.getLocalWindAngleAndSpeed()[0]);
            float windSpeed = pBoat.getLocalWindAngleAndSpeed()[1] * 20f;
            float sailWorldAngle = Mth.wrapDegrees(pBoat.getSailWorldRotation());
            int airFoilDirection = -1;

            float windDifference = Mth.wrapDegrees(Mth.degreesDifference(windWorldAngle, sailWorldAngle));
            if (windDifference > 0) {
                airFoilDirection = 1;
            }
            windDifference = Math.abs(windDifference);

            if (windSpeed < 0.1) {
                windSpeed = 0.1f;
            }
            float animationTickFloat = animationTick + pPartialTicks;
            animationTickFloat = (-windSpeed * animationTickFloat);


            for (int zindex = 0; zindex < mainsail_horizontal_sections; zindex++) {
                for (int yindex = 0; yindex < mainsail_vertical_sections; yindex++) {
                    if (sails[zindex][yindex] == null) {
                        break;
                    }
                    float luffFunction = (float) (5 * Mth.sin((float) (0.1 * ((zindex * mainsail_section_widths) + animationTickFloat + yindex * mainsail_section_widths))));
                    float squaredFunctionComponent = (zindex - 7) * (zindex - 7);
                    float airfoilFunction = (-0.20f * squaredFunctionComponent + 11) * airFoilDirection;
                    if (zindex > 7) {
                        airfoilFunction = (-0.018f * squaredFunctionComponent + 11) * airFoilDirection;
                    }

                    float ycurve = yindex * airFoilDirection;
                    if (yindex > 2) {
                        ycurve = -(yindex - 3) * airFoilDirection;
                    }
                    float falloff = 0.4f * (float) Math.log((zindex + 1.00f)) + 0.1f;
                    if (zindex > 15f) {
                        falloff = 0.4f * (float) Math.log((-zindex + 1.00f + 29f)) + 0.1f;
                    }
                    if (falloff > 1.0f) {
                        falloff = 1.0f;
                    }

                    luffFunction = luffFunction * falloff;
                    airfoilFunction = airfoilFunction * falloff;
                    ycurve = ycurve * falloff;

                    float mixFunction = 0;
                    if (windDifference > 30 && windDifference < 150) {
                        mixFunction = 0.92f;
                    } else if (windDifference > 150) {
                        windDifference = Math.abs(windDifference - 180);
                        mixFunction = windDifference / 30f;
                    } else {
                        mixFunction = windDifference / 30f;
                    }

                    if (windSpeed < 1) {
                        mixFunction *= 0.5;
                    }

                    mixFunction = Mth.clamp(mixFunction, 0, 0.92f);

                    if (mixFunction >= 0.9f) {
                        luffFunction = (float) (5 * Mth.sin((float) (0.1 * ((zindex * mainsail_section_widths) + animationTickFloat)))) * falloff;
                    }

                    float finalfunction = ((airfoilFunction) * mixFunction + (luffFunction * (1.0f - mixFunction))) + ycurve * mixFunction;

                    sails[zindex][yindex].x = (finalfunction);
                }

            }


            ModelPart gaff = mainsail_main.getChild("gaff_deployed");
            gaff.yRot = (float) Math.tan(sails[14][4].x / (15 * mainsail_section_widths));
        }


    }

    private static void animateJibsail(SloopEntity pBoat, float pPartialTicks, ModelPart jibsail_main, ModelPart jibsheet_transform_checker, ModelPart[][] sails, float mastRotation, int animationTick) {
        ModelPart jibsail = jibsail_main.getChild("jibsail");
        ModelPart jibsheet = jibsail.getChild("jibsheet");

        float windWorldAngle = pBoat.getLocalWindAngleAndSpeed()[0];
        float windSpeed = pBoat.getLocalWindAngleAndSpeed()[1] * 20f;
        float sailWorldAngle = Mth.wrapDegrees(pBoat.getSailWorldRotation());
        int airFoilDirection = 1;

        float windDifference = Mth.degreesDifference(windWorldAngle, sailWorldAngle);
        if (windDifference < 0) {
            airFoilDirection = -1;
        }
        windDifference = Math.abs(windDifference);
        /*
        float windDifferenceForAngle = windDifference;
        if (windDifferenceForAngle > 90) {
            windDifferenceForAngle = Math.abs(windDifference - 180);
        }
        //windDifferenceForAngle = airFoilDirection * (float) Math.toRadians(Mth.clamp(windDifferenceForAngle / 2, 0.0f, 30f));

         */

        if (windSpeed < 0.1) {
            windSpeed = 0.1f;
        }
        float animationTickFloat = -windSpeed * animationTick;

        float boatWorldAngle = Mth.wrapDegrees(pBoat.getYRot());
        float boatWindDifference = Mth.degreesDifferenceAbs(windWorldAngle, boatWorldAngle);
        if (boatWindDifference < 10 || boatWindDifference > 170 && pBoat.getMainsailActive()) {
            jibsail.yRot = -mastRotation;
            airFoilDirection *= -1;
        } else {
            jibsail.yRot = mastRotation;
        }

        if(pBoat.getJibsailActive()){
            for (int zindex = 0; zindex < jibsail_horizontal_sections; zindex++) {

                for (int yindex = 0; yindex < jibsail_vertical_sections; yindex++) {
                    if (sails[zindex][yindex] == null) {
                        break;
                    }

                    //TODO clean up the math

                    float luffFunction = (float) (5 * Mth.sin((float) (0.1 * ((zindex * jibsail_section_widths) + animationTickFloat + yindex * jibsail_section_widths))));
                    float squaredFunctionComponent = (float) Math.pow(zindex - 22f,2);

                    float falloff = 0.6f * (float) Math.log((zindex + 1.00f)) + 0.1f;
                    falloff = Mth.clamp(falloff, 0.0f, 1.0f);

                    float airfoilFunction = (-0.032f * squaredFunctionComponent + 16) * airFoilDirection;

                    luffFunction = luffFunction * falloff;
                    airfoilFunction = airfoilFunction * falloff;

                    float mixFunction = 0;
                    if (windDifference > 30 && windDifference < 150) {
                        mixFunction = 0.92f;
                    } else if (windDifference > 150) {
                        windDifference = Math.abs(windDifference - 180);
                        mixFunction = windDifference / 30f;
                    } else {
                        mixFunction = windDifference / 30f;
                    }

                    if (mixFunction >= 0.9f) {
                        luffFunction = (float) (5 * Mth.sin((float) (0.1 * ((zindex * mainsail_section_widths) + animationTickFloat)))) * falloff;
                    }

                    mixFunction = Mth.clamp(mixFunction, 0, 0.92f);

                    float finalfunction = ((airfoilFunction) * mixFunction + (luffFunction * (1.0f - mixFunction)));

                    sails[zindex][yindex].x = (finalfunction);
                }
            }
        }





        /*
        transform the local position of the last section of the jibsail into global space
            given the y rotation of the sail
            the x rotation of the forestay
            the global position of the main jibsail bone

        calculate the vector between the origin of the sheet and that position

        transform the sheet cube to conform to that vector
         */


        /*
        Vec3 jibsheetTarget = new Vec3(sails[jibsail_horizontal_sections - 1][0].x, jibsail_horizontal_sections * 3, jibsail_horizontal_sections * 4);
        float distance = (float) jibsheetTarget.length();

        float xRot = (float) Math.toRadians(28.67);
        float yRot = jibsail.yRot;
        float zRot = 0;
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotateXYZ(xRot, yRot, 0);
        Vector3f eulerRotations = quaternionf.getEulerAnglesXYZ(new Vector3f(0, 0, 0));
        xRot = eulerRotations.x;
        yRot = eulerRotations.y;
        zRot = eulerRotations.z;


        // transform the jibsail vector based on the x rotation of the sail
        float xRotatedY = (float) (0 + Math.sin(xRot) * jibsail_horizontal_sections * 4);
        float xRotatedZ = (float) (0 + Math.cos(xRot) * jibsail_horizontal_sections * 4);
        jibsheetTarget = jibsheetTarget.multiply(1, 0, 0).add(0, -xRotatedY, -xRotatedZ);

        // very stupid math ensues

        // transform the jibsail vector based on the y rotation of the sail

        float yRotatedX = (float) (0 + Math.sin(yRot) * distance);
        float yRotatedZ = (float) (0 + Math.cos(yRot) * distance);
        jibsheetTarget = jibsheetTarget.add(yRotatedX, 0, yRotatedZ);

        float zRotatedY = (float) (0 + Math.sin(zRot) * distance);
        float zRotatedX = (float) (0 + Math.cos(zRot) * distance);
        jibsheetTarget = jibsheetTarget.add(zRotatedY, zRotatedX, 0);

        // transform the jibsail vector based on the global position of the jibsail
        jibsheetTarget = jibsheetTarget.add(0, -30, 0);

        jibsheet_transform_checker.setPos((float) jibsheetTarget.x, (float) jibsheetTarget.y, (float) jibsheetTarget.z);
        //jibsheet_transform_checker.setPos(0, 0, 0);


        // transform the origin back to base

        Vec3 jibsheetOrigin = new Vec3(jibsheet.x, jibsheet.y, jibsheet.z);



        Vec3 conform = jibsheetOrigin.vectorTo(jibsheetTarget);

        jibsheet.yScale = (float) conform.length();

        jibsheet.xRot= (float) Math.toRadians(90);
        jibsheet.zRot=(float) Math.toRadians(0);

         */


    }

    private static void animateTelltail(SloopEntity pBoat, float pPartialTicks, ModelPart telltail, ModelPart[] telltailParts, int animationTick) {

        float windLocalAngle = Mth.wrapDegrees(pBoat.getWindLocalRotation()-180);
        float windSpeed = pBoat.getLocalWindAngleAndSpeed()[1] * 20f;

        if (windSpeed < 0.1) {
            windSpeed = 0.1f;
        }
        float animationTickFloat = animationTick + pPartialTicks;
        animationTickFloat = (-windSpeed * animationTickFloat);

        for (int zindex = 0; zindex < 6; zindex++) {
            if (telltailParts[zindex] == null) {
                break;
            }
            float luffFunction = (float) (2 * Mth.sin((float) (0.1 * ((zindex * 6) + animationTickFloat))));

            float falloff = 0.4f * (float) Math.log((zindex + 1.00f)) + 0.1f;
            if (falloff > 1.0f) {
                falloff = 1.0f;
            }

            telltailParts[zindex].x = (luffFunction * falloff);

        }

        telltail.yRot = (float) Math.toRadians(windLocalAngle);


    }

    private static void animateRudder(SloopEntity pBoat, float pPartialTicks, ModelPart rudder, float rudderRotation) {
        rudder.yRot = rudderRotation;
    }

    private static void animateWindlass(SloopEntity pBoat, float pPartialTicks, ModelPart windlass, float anchorDistance) {
        float degrees = (anchorDistance * 180);
        windlass.getChild("crank_arm").zRot = (float) Math.toRadians(degrees);
        if (degrees % 360 < 90) {
            windlass.getChild("spool").zRot = (float) Math.toRadians(0);
        } else if (degrees % 360 < 180) {
            windlass.getChild("spool").zRot = (float) Math.toRadians(90);
        } else if (degrees % 360 < 270) {
            windlass.getChild("spool").zRot = (float) Math.toRadians(180);
        } else {
            windlass.getChild("spool").zRot = (float) Math.toRadians(270);
        }

    }

    private static void animateMainsheet(SloopEntity pBoat, float pPartialTicks, ModelPart sheet, ModelPart spiral, float boomRotation) {
        //TODO clean up this absolute trashfire
        ModelPart mainsheet = sheet.getChild("mainsheet");
        ModelPart traveller = sheet.getChild("traveller");
        ModelPart connector = sheet.getChild("traveller_connector");
        if (pBoat.getMainsailActive()) {
            double thing = Math.toDegrees(boomRotation);
            float rotation = (float) mainSheetRotationLookupCauseImBadAtMath((float) Math.toDegrees(boomRotation));
            if (boomRotation > 0) {
                mainsheet.zRot = rotation;
                mainsheet.xRot = boomRotation * 0.6f;
            } else {
                rotation *= -1;
                mainsheet.zRot = rotation;
                mainsheet.xRot = -1 * boomRotation * 0.6f;
            }
            // oh my god this is bad
            if (Math.abs(thing) < 5) {
                mainsheet.yScale = 1.0f;
            } else if (Math.abs(thing) < 10) {
                mainsheet.yScale = 1.2f;
            } else if (Math.abs(thing) < 12.5) {
                mainsheet.yScale = 1.3f;
            } else if (Math.abs(thing) < 15) {
                mainsheet.yScale = 1.5f;
            } else if (Math.abs(thing) < 20) {
                mainsheet.yScale = 1.7f;
            } else if (Math.abs(thing) < 25) {
                mainsheet.yScale = 1.9f;
            } else if (Math.abs(thing) < 30) {
                mainsheet.yScale = 2.2f;
            } else if (Math.abs(thing) < 32.5) {
                mainsheet.yScale = 2.5f;
            } else if (Math.abs(thing) < 35) {
                mainsheet.yScale = 2.7f;
            } else if (Math.abs(thing) < 37.5) {
                mainsheet.yScale = 2.8f;
            } else if (Math.abs(thing) < 40) {
                mainsheet.yScale = 3.0f;
            } else if (Math.abs(thing) < 41) {
                mainsheet.yScale = 3.15f;
            } else if (Math.abs(thing) < 42) {
                mainsheet.yScale = 3.25f;
            } else if (Math.abs(thing) < 43) {
                mainsheet.yScale = 3.25f;
            } else if (Math.abs(thing) < 44) {
                mainsheet.yScale = 3.3f;
            } else if (Math.abs(thing) < 50) {
                mainsheet.yScale = 3.35f;
            }
            //sheet.yScale = ((float)Mth.clamp(Math.abs(thing), 15, 45)/45f)*3.85f;
            mainsheet.x = (float) Math.toDegrees(boomRotation) / 3.53f;
            traveller.x = (float) Math.toDegrees(boomRotation) / 3.8f - 12f;
            connector.xScale = (float) (1 + (-Math.toDegrees(boomRotation) / 45) * 0.5);

            int spiralIndex = Mth.clamp(12 - (int) pBoat.getMainsheetLength() / 4, 2, 11);
            for (int i = 2; i <= 11; i++) {
                if (i <= spiralIndex) {
                    //spiral.getChild("spiral"+i).y = -176.1f;
                    spiral.getChild("spiral" + i).y = -0f;
                } else {
                    //spiral.getChild("spiral"+i).y = -175.0f;
                    spiral.getChild("spiral" + i).y = 2.1f;
                }

            }
        } else {
            for (int i = 2; i <= 11; i++) {
                spiral.getChild("spiral" + i).y = -0f;
            }
            mainsheet.x = 0;
            traveller.x = -12f;
            connector.xScale = 1;
            mainsheet.yScale = 1f;
            mainsheet.zRot = 0;
            mainsheet.xRot = 0;
        }

    }

    private static float mainSheetRotationLookupCauseImBadAtMath(float angleDegrees) {
        angleDegrees = Math.abs(angleDegrees);
        float[] table = {
                1, 4.5f, 7.5f, 11.5f, 17, 21, 24.5f, 28, 31.6f, 34f, 37, 39, 42f, 44f, 46,
                48, 50, 52, 53, 55, 56.5f, 57.5f, 58.25f, 59f, 60.75f, 61.5f, 62f, 62.25f, 62.5f, 63f,
                64f, 65f, 65.0f, 66f, 66, 67.5f, 68f, 68.5f, 69, 69.5f, 70, 70.5f, 71, 71.5f, 71.7f, 72f
        };
        return (float) Math.toRadians(table[(int) angleDegrees]);
    }

    private static void animateWindIndicator(SloopEntity pBoat, float pPartialTicks, ModelPart wind_indicator) {
        float windAngle = (float) Math.toRadians(pBoat.getWindLocalRotation());
        wind_indicator.yRot = (float) (windAngle);
        wind_indicator.zScale = 1;
        wind_indicator.xScale = 1;
        wind_indicator.zScale = pBoat.getLocalWindAngleAndSpeed()[1] * 5;
    }

    private static void animateSailforceIndicator(SloopEntity pBoat, float pPartialTicks, ModelPart sail_force_indicator) {
        float sailForceAngle = (float) Math.toRadians(Mth.wrapDegrees(pBoat.getMainsailWindAngleAndForce()[0]) - pBoat.getYRot());
        float sailForce = Mth.clamp(pBoat.getMainsailWindAngleAndForce()[1], 0.1f, 10f);
        sail_force_indicator.yRot = (float) (sailForceAngle);
        sail_force_indicator.zScale = 1;
        sail_force_indicator.xScale = 1;
        sail_force_indicator.zScale = sailForce;
        sail_force_indicator.xScale = sailForce;
    }


    @Override
    public void setupAnim(SloopEntity pEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                          float headPitch) {
        if (mainSailParts[0][0] == null) {
            mainSailParts = getMainsailParts(this);
        }
        if (jibSailParts[0][0] == null) {
            jibSailParts = getJibsailParts(this);
        }
        if (telltailParts[0] == null) {
            telltailParts = getTelltailParts(this);
        }
        //(float) Math.toRadians(45f)
        animateMainsail(pEntity, limbSwing, mainsail_deployed, mainsail, mainSailParts, (float) Math.toRadians(pEntity.getMainBoomRotation()), pEntity.tickCount);
        animateMainsheet(pEntity, limbSwing, mainsheet_main, rope_spiral, (float) Math.toRadians(pEntity.getMainBoomRotation()));
        animateJibsail(pEntity, limbSwing, jibsail_deployed, jibsheet_transform_checker, jibSailParts, (float) Math.toRadians(pEntity.getMainBoomRotation()), pEntity.tickCount);
        animateRudder(pEntity, limbSwing, rudder, (float) Math.toRadians(pEntity.getRudderRotation()));
        animateTelltail(pEntity, limbSwing, telltail, telltailParts, pEntity.tickCount);

        float distance = 0;
        for (WindlassSwitchEntity windlassSwitch : pEntity.getWindlasses()) {
            distance = windlassSwitch.getAnchorDistance();
        }
        if(distance > 0){
            animateWindlass(pEntity, limbSwing, windlass, distance);
        }
    }

    private ModelPart[][] mainSailParts = new ModelPart[mainsail_horizontal_sections][mainsail_vertical_sections];


    public static ModelPart[][] getMainsailParts(SloopEntityModel sloopEntityModel) {

        ModelPart[][] sails = new ModelPart[mainsail_horizontal_sections][mainsail_vertical_sections];
        for (int zindex = 0; zindex < mainsail_horizontal_sections; zindex++) {
            String name = "mainsail_part_" + zindex + "_";
            for (int yindex = 0; yindex < mainsail_vertical_sections; yindex++) {
                if (sloopEntityModel.mainsail.hasChild(name + yindex)) {
                    sails[zindex][yindex] = sloopEntityModel.mainsail.getChild(name + yindex);
                } else {
                    sails[zindex][yindex] = null;
                }
            }
        }

        return sails;
    }

    private ModelPart[][] jibSailParts = new ModelPart[jibsail_horizontal_sections][jibsail_vertical_sections];

    public static ModelPart[][] getJibsailParts(SloopEntityModel sloopEntityModel) {

        ModelPart jibsail = sloopEntityModel.jibsail_deployed.getChild("jibsail");

        ModelPart[][] sails = new ModelPart[jibsail_horizontal_sections][jibsail_vertical_sections];
        for (int zindex = 0; zindex < jibsail_horizontal_sections; zindex++) {
            String name = "jibsail_part_" + zindex + "_";
            for (int yindex = 0; yindex < jibsail_vertical_sections; yindex++) {
                if (jibsail.hasChild(name + yindex)) {
                    sails[zindex][yindex] = jibsail.getChild(name + yindex);
                } else {
                    sails[zindex][yindex] = null;
                }
            }
        }

        return sails;
    }

    private ModelPart[] telltailParts = new ModelPart[6];

    public static ModelPart[] getTelltailParts(SloopEntityModel sloopEntityModel) {
        ModelPart[] telltailParts = new ModelPart[6];
        for (int i = 1; i <= 6; i++) {
            String name = "telltail_part_" + i;
            telltailParts[i-1] = sloopEntityModel.telltail.getChild(name);
        }
        return telltailParts;
    }
    public ModelPart getWaterocclusion() {
        return this.waterocclusion;
    }

    public ModelPart getJibsail() {
        return this.jibsail_deployed;
    }

    public ModelPart getJibsailFurled() {
        return this.jibsail_furled;
    }

    public ModelPart getMainsailDeployedParts() {
        return this.mainsail_deployed;
    }

    public ModelPart getMainsailFurledParts() {
        return this.mainsail_furled;
    }

    public ModelPart getMainsail() {
        return this.mainsail;
    }

    public ModelPart getRopeSpiral() {
        return this.rope_spiral;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        static_parts.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        windlass.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rudder.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mainsheet_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rope_spiral.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        telltail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        //jibsheet_transform_checker.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }


}