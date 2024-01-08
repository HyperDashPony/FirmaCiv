package com.alekiponi.firmaciv.client.model.entity;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class SloopEntityModel<T extends AbstractFirmacivBoatEntity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    private final ModelPart mainsail;
    private final ModelPart mainsail_deployed;
    private final ModelPart mainsail_furled;
    private final ModelPart rudder;
    private final ModelPart mainsheet;
    private final ModelPart waterocclusion;
    private final ModelPart jibsail_deployed;
    private final ModelPart jibsail_furled;
    private final ModelPart windlass;
    private final ModelPart static_parts;

    public SloopEntityModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.mainsail = root.getChild("mainsail");
        this.mainsail_deployed = root.getChild("mainsail_deployed");
        this.mainsail_furled = root.getChild("mainsail_furled");
        this.rudder = root.getChild("rudder");
        this.mainsheet = root.getChild("mainsheet");
        this.waterocclusion = root.getChild("waterocclusion");
        this.jibsail_deployed = root.getChild("jibsail_deployed");
        this.jibsail_furled = root.getChild("jibsail_furled");
        this.windlass = root.getChild("windlass");
        this.static_parts = root.getChild("static_parts");
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition mainsail = partdefinition.addOrReplaceChild("mainsail", CubeListBuilder.create(), PartPose.offset(0.0F, -18.0F, -32.0F));

        PartDefinition mainsail_deployed = partdefinition.addOrReplaceChild("mainsail_deployed", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -22.0F, -34.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition main_boom = mainsail_deployed.addOrReplaceChild("main_boom", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 63.9148F));

        PartDefinition main_boom_r1 = main_boom.addOrReplaceChild("main_boom_r1", CubeListBuilder.create().texOffs(719, 981).addBox(-60.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(719, 981).addBox(56.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-62.0F, -2.0F, -1.0F, 123.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition gaff_deployed = mainsail_deployed.addOrReplaceChild("gaff_deployed", CubeListBuilder.create(), PartPose.offset(0.0F, -112.4182F, 0.5F));

        PartDefinition gaff_r1 = gaff_deployed.addOrReplaceChild("gaff_r1", CubeListBuilder.create().texOffs(733, 981).addBox(-2.0206F, -102.6087F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(733, 981).addBox(27.7294F, -102.6087F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(733, 981).addBox(57.9794F, -102.6087F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 104.0299F, 31.7328F, 1.5708F, -1.3265F, -1.5708F));

        PartDefinition shroud_starboard_r1 = gaff_deployed.addOrReplaceChild("shroud_starboard_r1", CubeListBuilder.create().texOffs(136, 862).mirror().addBox(-0.5F, -1.0F, -0.5F, 1.0F, 157.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -20.5818F, 4.0F, -0.0175F, 0.0F, 0.0175F));

        PartDefinition peak_halliard_r1 = gaff_deployed.addOrReplaceChild("peak_halliard_r1", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -6.0F, -1.25F, 1.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.8465F, 18.059F, -1.4399F, 0.0F, 0.0F));

        PartDefinition mast2_r1 = gaff_deployed.addOrReplaceChild("mast2_r1", CubeListBuilder.create().texOffs(-1, 136).addBox(-1.5F, 0.05F, -2.75F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0875F, -12.2099F, 26.4654F, -0.2618F, 0.0F, 0.0F));

        PartDefinition mast2_r2 = gaff_deployed.addOrReplaceChild("mast2_r2", CubeListBuilder.create().texOffs(1, 138).addBox(-1.0F, -4.5F, 1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(1, 138).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0875F, -12.308F, 24.0994F, -1.0472F, 0.0F, 0.0F));

        PartDefinition mast2_r3 = gaff_deployed.addOrReplaceChild("mast2_r3", CubeListBuilder.create().texOffs(-2, 135).addBox(-1.5F, -0.5F, -5.5F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9125F, -21.6069F, 4.8502F, 0.0F, -0.2182F, 0.0F));

        PartDefinition mast2_r4 = gaff_deployed.addOrReplaceChild("mast2_r4", CubeListBuilder.create().texOffs(1, 138).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9125F, -21.6069F, 4.8502F, -0.2618F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r2 = gaff_deployed.addOrReplaceChild("peak_halliard_r2", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, 12.0F, 12.0F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.062F, 53.0626F, -2.4435F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r3 = gaff_deployed.addOrReplaceChild("peak_halliard_r3", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -16.0F, -7.5F, 1.0F, 36.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0735F, 47.501F, -1.6581F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r4 = gaff_deployed.addOrReplaceChild("peak_halliard_r4", CubeListBuilder.create().texOffs(145, 873).addBox(-0.95F, -7.0F, -0.5F, 1.0F, 21.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.4561F, 17.7502F, -2.0075F, 0.0395F, -0.0184F));

        PartDefinition mast2_r5 = gaff_deployed.addOrReplaceChild("mast2_r5", CubeListBuilder.create().texOffs(3, 140).addBox(92.5F, -13.7926F, 2.0F, 9.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 137).addBox(94.5F, -14.7926F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(3, 140).addBox(92.5F, -13.7926F, -3.0F, 9.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 137).addBox(92.5F, -8.7926F, -2.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 104.9182F, 10.7074F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition gaff_r2 = gaff_deployed.addOrReplaceChild("gaff_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-31.6967F, -3.1385F, -1.0F, 68.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0299F, 31.7328F, 1.5708F, -1.3265F, -1.5708F));

        PartDefinition mainsail_furled = partdefinition.addOrReplaceChild("mainsail_furled", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition main_boom_r2 = mainsail_furled.addOrReplaceChild("main_boom_r2", CubeListBuilder.create().texOffs(719, 981).addBox(-60.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(719, 981).addBox(56.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(719, 981).addBox(23.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(719, 981).addBox(-43.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(719, 981).addBox(-10.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-62.0F, -2.0F, -1.0F, 123.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -44.0F, 29.9148F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r1 = mainsail_furled.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(868, 966).addBox(-2.9821F, -3.0429F, -31.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0429F, -46.7071F, -0.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r2 = mainsail_furled.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(855, 953).addBox(-2.9821F, -3.0429F, 75.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(868, 966).addBox(-3.9821F, -4.0429F, -29.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(853, 951).addBox(-3.9821F, -4.0429F, 57.0F, 5.0F, 5.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(867, 965).addBox(-4.9821F, -5.0429F, -26.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(869, 967).addBox(-4.9821F, -5.0429F, 71.1F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(851, 949).addBox(-4.9821F, -5.0429F, 37.0F, 6.0F, 6.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(812, 910).addBox(-5.9821F, -6.0429F, -22.0F, 7.0F, 7.0F, 59.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0429F, -46.4571F, -0.25F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r3 = mainsail_furled.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(869, 967).addBox(-1.5F, -2.0F, -0.5F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3535F, -49.6568F, 82.7F, 0.0F, 0.0F, 0.7854F));

        PartDefinition peak_halliard_r5 = mainsail_furled.addOrReplaceChild("peak_halliard_r5", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.01F, -53.1504F, 71.8271F, -2.789F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r6 = mainsail_furled.addOrReplaceChild("peak_halliard_r6", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -4.0F, -0.2F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.01F, -53.5589F, 80.312F, -2.2724F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r7 = mainsail_furled.addOrReplaceChild("peak_halliard_r7", CubeListBuilder.create().texOffs(145, 873).addBox(-0.49F, -146.0F, -0.5F, 1.0F, 147.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -166.7693F, -31.2886F, -2.3754F, 0.0F, 0.0F));

        PartDefinition mast2_r6 = mainsail_furled.addOrReplaceChild("mast2_r6", CubeListBuilder.create().texOffs(1, 138).addBox(-1.0F, -1.3F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0875F, -60.8145F, 70.2875F, -0.3491F, 0.0F, 0.0F));

        PartDefinition gaff_furled = mainsail_furled.addOrReplaceChild("gaff_furled", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -158.4182F, -33.5F, 0.0F, -0.0349F, 0.0F));

        PartDefinition shroud_starboard_r2 = gaff_furled.addOrReplaceChild("shroud_starboard_r2", CubeListBuilder.create().texOffs(136, 862).mirror().addBox(-0.5F, -1.0F, -0.5F, 1.0F, 157.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -20.5818F, 4.0F, -0.0175F, 0.0F, 0.0175F));

        PartDefinition peak_halliard_r8 = gaff_furled.addOrReplaceChild("peak_halliard_r8", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -49.0F, -1.0F, 1.0F, 98.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 37.7195F, 18.2267F, -2.7838F, 0.0F, 0.0F));

        PartDefinition mast2_r7 = gaff_furled.addOrReplaceChild("mast2_r7", CubeListBuilder.create().texOffs(-2, 135).addBox(-1.5F, -0.5F, -5.5F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9125F, -21.6069F, 4.8502F, 0.0F, -0.2182F, 0.0F));

        PartDefinition mast2_r8 = gaff_furled.addOrReplaceChild("mast2_r8", CubeListBuilder.create().texOffs(1, 138).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9125F, -21.6069F, 4.8502F, -0.2618F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r9 = gaff_furled.addOrReplaceChild("peak_halliard_r9", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -99.0F, -0.5F, 1.0F, 110.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6022F, -11.6755F, 8.8489F, -2.8453F, 0.0395F, 0.0F));

        PartDefinition mast2_r9 = gaff_furled.addOrReplaceChild("mast2_r9", CubeListBuilder.create().texOffs(3, 140).addBox(92.5F, -13.7926F, 2.0F, 9.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 137).addBox(94.5F, -14.7926F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(3, 140).addBox(92.5F, -13.7926F, -3.0F, 9.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 137).addBox(92.5F, -8.7926F, -2.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 199.9182F, 10.7074F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition gaff_r3 = gaff_furled.addOrReplaceChild("gaff_r3", CubeListBuilder.create().texOffs(733, 981).addBox(-26.6967F, -3.6385F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(733, 981).addBox(4.9533F, -3.6385F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(733, 981).addBox(33.3033F, -3.6385F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-31.6967F, -3.1385F, -1.0F, 68.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 104.0299F, 31.7328F, 1.5708F, -1.5708F, -1.5708F));

        PartDefinition bone = gaff_furled.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0875F, 87.792F, 30.0994F, -0.2618F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r10 = bone.addOrReplaceChild("peak_halliard_r10", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -5.7588F, -2.4659F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0875F, 6.109F, 7.496F, -2.8798F, 0.0F, 0.0F));

        PartDefinition peak_halliard_r11 = bone.addOrReplaceChild("peak_halliard_r11", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -17.0765F, -2.274F, 1.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0875F, 2.0868F, 22.0193F, -1.789F, 0.0F, 0.0F));

        PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.4055F, 6.6242F, -0.6977F, -0.0387F, -0.0202F));

        PartDefinition mast2_r10 = bone2.addOrReplaceChild("mast2_r10", CubeListBuilder.create().texOffs(1, 138).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5542F, -2.0268F, -1.0472F, 0.0F, 0.0F));

        PartDefinition mast2_r11 = bone2.addOrReplaceChild("mast2_r11", CubeListBuilder.create().texOffs(1, 138).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5438F, 2.0713F, -1.0472F, 0.0F, 0.0F));

        PartDefinition mast2_r12 = bone2.addOrReplaceChild("mast2_r12", CubeListBuilder.create().texOffs(-1, 136).addBox(-1.5F, -0.5F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0104F, -0.0446F, -0.2618F, 0.0F, 0.0F));

        PartDefinition rudder = partdefinition.addOrReplaceChild("rudder", CubeListBuilder.create(), PartPose.offset(0.0F, 11.1559F, 47.4742F));

        PartDefinition cube_r4 = rudder.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(133, 363).addBox(-1.0F, -11.0F, -0.5F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(131, 361).addBox(-1.0F, 3.0F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.5183F, -2.7718F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition cube_r5 = rudder.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(132, 362).addBox(-5.5F, -23.25F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(132, 362).addBox(-13.5F, -12.25F, -1.0F, 10.0F, 36.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.2985F, 9.965F, 1.5708F, -1.3963F, -1.5708F));

        PartDefinition mainsheet = partdefinition.addOrReplaceChild("mainsheet", CubeListBuilder.create(), PartPose.offset(7.8189F, 0.6593F, 53.2336F));

        PartDefinition cube_r6 = mainsheet.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(-2, -2).mirror().addBox(-0.8058F, 11.5272F, 11.4784F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.8189F, -1.4651F, 0.8362F, 0.0F, 1.5708F, 2.3562F));

        PartDefinition cube_r7 = mainsheet.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(-2, -2).addBox(-1.25F, -2.75F, -1.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.1811F, 1.8407F, -4.7336F, -0.6109F, 0.0F, -1.5708F));

        PartDefinition cube_r8 = mainsheet.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(-2, -2).addBox(-1.1942F, 11.5272F, 11.4784F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.8189F, -1.4651F, 0.8362F, 0.0F, -1.5708F, -2.3562F));

        PartDefinition mainsheet_r1 = mainsheet.addOrReplaceChild("mainsheet_r1", CubeListBuilder.create().texOffs(145, 873).addBox(1.3199F, -19.0F, -0.3591F, 1.0F, 38.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(145, 873).addBox(-2.4301F, -19.0F, -0.3591F, 1.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.8189F, -1.4651F, 0.8362F, 0.0F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r2 = mainsheet.addOrReplaceChild("mainsheet_r2", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -11.0F, 0.75F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.2902F, 11.1048F, -15.4638F, -1.1781F, -1.0036F, 1.5708F));

        PartDefinition mainsheet_r3 = mainsheet.addOrReplaceChild("mainsheet_r3", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -11.0F, 0.75F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.7902F, 2.1048F, -14.4138F, -1.3963F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r4 = mainsheet.addOrReplaceChild("mainsheet_r4", CubeListBuilder.create().texOffs(145, 873).addBox(-1.9301F, -25.25F, -0.1091F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.8189F, 3.5349F, 0.8362F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition rope_spiral = mainsheet.addOrReplaceChild("rope_spiral", CubeListBuilder.create(), PartPose.offset(12.5555F, 12.0548F, -16.1063F));

        PartDefinition mainsheet_r5 = rope_spiral.addOrReplaceChild("mainsheet_r5", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, 0.5F, -10.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r6 = rope_spiral.addOrReplaceChild("mainsheet_r6", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, 0.5F, -4.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.4529F, 0.0F, 0.7179F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r7 = rope_spiral.addOrReplaceChild("mainsheet_r7", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, 0.5F, -4.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5703F, 0.0F, -4.2004F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r8 = rope_spiral.addOrReplaceChild("mainsheet_r8", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -4.5F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.714F, 0.0F, -1.265F, 1.7017F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r9 = rope_spiral.addOrReplaceChild("mainsheet_r9", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -1.0F, 3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.714F, 0.0F, -1.265F, -3.0107F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r10 = rope_spiral.addOrReplaceChild("mainsheet_r10", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, 1.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.714F, 0.0F, -1.265F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r11 = rope_spiral.addOrReplaceChild("mainsheet_r11", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -2.0F, 5.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4972F, 0.0F, -7.2137F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r12 = rope_spiral.addOrReplaceChild("mainsheet_r12", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -6.5F, 2.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4972F, 0.0F, -7.2137F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r13 = rope_spiral.addOrReplaceChild("mainsheet_r13", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -10.5F, 4.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.7582F, 0.0F, -9.1966F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r14 = rope_spiral.addOrReplaceChild("mainsheet_r14", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -8.5F, 4.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.5018F, 0.0F, -3.1562F, 0.1309F, 0.0F, 1.5708F));

        PartDefinition mainsheet_r15 = rope_spiral.addOrReplaceChild("mainsheet_r15", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.4399F, 0.0F, 1.5708F));

        PartDefinition bone6 = mainsheet.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(-19.8189F, -1.4651F, 0.8362F));

        PartDefinition cube_r9 = bone6.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(8.0F, -1.1942F, 0.9301F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r10 = bone6.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(-3, -3).addBox(11.0F, -3.1942F, -1.0699F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, -0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition waterocclusion = partdefinition.addOrReplaceChild("waterocclusion", CubeListBuilder.create(), PartPose.offset(0.0F, -29.5F, 32.2074F));

        PartDefinition waterocclusion_r1 = waterocclusion.addOrReplaceChild("waterocclusion_r1", CubeListBuilder.create().texOffs(362, 800).addBox(-37.0F, -4.0F, -4.5F, 58.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.0623F, 46.5F, -26.5199F, 0.0F, 1.4835F, 0.0F));

        PartDefinition waterocclusion_r2 = waterocclusion.addOrReplaceChild("waterocclusion_r2", CubeListBuilder.create().texOffs(363, 801).addBox(-53.0F, -4.0F, -25.5F, 43.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 46.5F, -24.7926F, 0.0F, -1.0472F, 0.0F));

        PartDefinition waterocclusion_r3 = waterocclusion.addOrReplaceChild("waterocclusion_r3", CubeListBuilder.create().texOffs(362, 800).addBox(-25.7926F, 42.5F, -8.0F, 5.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(362, 800).addBox(-22.7926F, 42.5F, -8.0F, 22.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(351, 789).addBox(-3.7926F, 42.5F, -19.0F, 68.0F, 8.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -55.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition waterocclusion_r4 = waterocclusion.addOrReplaceChild("waterocclusion_r4", CubeListBuilder.create().texOffs(351, 789).mirror().addBox(-64.2074F, 42.5F, -19.0F, 68.0F, 8.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(362, 800).mirror().addBox(0.7926F, 42.5F, -8.0F, 22.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(362, 800).mirror().addBox(20.7926F, 42.5F, -8.0F, 5.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -55.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition waterocclusion_r5 = waterocclusion.addOrReplaceChild("waterocclusion_r5", CubeListBuilder.create().texOffs(363, 801).mirror().addBox(10.0F, -4.0F, -25.5F, 43.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.5F, 46.5F, -24.7926F, 0.0F, 1.0472F, 0.0F));

        PartDefinition waterocclusion_r6 = waterocclusion.addOrReplaceChild("waterocclusion_r6", CubeListBuilder.create().texOffs(362, 800).mirror().addBox(-21.0F, -4.0F, -4.5F, 58.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-21.0623F, 46.5F, -26.5199F, 0.0F, -1.4835F, 0.0F));

        PartDefinition jibsail_deployed = partdefinition.addOrReplaceChild("jibsail_deployed", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -17.0F, -110.0F, -0.5004F, 0.0F, 0.0F));

        PartDefinition jibsail = jibsail_deployed.addOrReplaceChild("jibsail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition jibsail_furled = partdefinition.addOrReplaceChild("jibsail_furled", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r11 = jibsail_furled.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(797, 991).addBox(-14.0F, -7.0F, -14.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(796, 990).addBox(-13.0F, -5.0F, -13.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(795, 989).addBox(-12.0F, -3.0F, -12.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(794, 988).addBox(-11.0F, -1.0F, -11.0F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, -43.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition windlass = partdefinition.addOrReplaceChild("windlass", CubeListBuilder.create(), PartPose.offsetAndRotation(14.5F, 6.5833F, -29.75F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone4 = windlass.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(1, 1).addBox(-0.1986F, -2.25F, 1.125F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 1).addBox(-0.1986F, -2.25F, -6.875F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.8014F, 0.1667F, -3.375F));

        PartDefinition cube_r12 = bone4.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(1, 1).addBox(-0.1986F, 1.75F, -6.859F, 3.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 1).addBox(-0.1986F, 1.75F, 1.109F, 3.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition bone3 = windlass.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 0.0F));

        PartDefinition bone5 = bone3.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(133, 866).addBox(-1.5167F, -1.5667F, -9.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(770, 887).addBox(9.4113F, -5.2027F, -6.016F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(8.4833F, -4.5667F, -6.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(890, 613).addBox(-8.0167F, -0.5667F, -1.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(831, 632).addBox(-6.0167F, -0.5667F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(822, 650).addBox(-1.0167F, -1.0667F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0167F, -0.0167F, -0.25F));

        PartDefinition cube_r13 = bone5.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(1, 1).addBox(-1.0F, -2.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.4833F, -1.0667F, -5.5F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r14 = bone5.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(792, 923).addBox(-5.5F, 0.8F, -0.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9833F, -4.0667F, -5.5F, 0.0F, 0.0F, -0.3491F));

        PartDefinition static_parts = partdefinition.addOrReplaceChild("static_parts", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition sided = static_parts.addOrReplaceChild("sided", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition port = sided.addOrReplaceChild("port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shrouds_port = port.addOrReplaceChild("shrouds_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shroud_starboard_r3 = shrouds_port.addOrReplaceChild("shroud_starboard_r3", CubeListBuilder.create().texOffs(136, 862).addBox(0.0F, -27.5F, 0.0F, 1.0F, 28.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.7094F, -157.5834F, -34.0664F, 0.0F, 0.0F, -0.4712F));

        PartDefinition shroud_starboard_r4 = shrouds_port.addOrReplaceChild("shroud_starboard_r4", CubeListBuilder.create().texOffs(136, 862).addBox(-0.5F, -145.0F, -0.5F, 1.0F, 143.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.946F, -12.4205F, -27.8829F, 0.0393F, 0.0F, -0.0524F));

        PartDefinition mast2_r13 = shrouds_port.addOrReplaceChild("mast2_r13", CubeListBuilder.create().texOffs(-8, 129).mirror().addBox(-104.5F, -11.7926F, 2.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, 0.0F, 1.5708F));

        PartDefinition shackle = shrouds_port.addOrReplaceChild("shackle", CubeListBuilder.create().texOffs(1007, 615).addBox(-14.85F, -12.75F, -31.95F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r15 = shackle.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(1016, 626).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.1F, -14.25F, -31.45F, 0.0F, -0.4363F, 0.0F));

        PartDefinition sidewall_port = port.addOrReplaceChild("sidewall_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r16 = sidewall_port.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(420, 122).addBox(-65.2487F, 48.5F, 23.8121F, 59.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(316, 165).addBox(-54.2487F, 45.5F, 24.8121F, 49.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(108, 165).addBox(-53.2487F, 41.5F, 26.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 173).addBox(-53.2487F, 37.5F, 30.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, 1.4835F, 0.0F));

        PartDefinition cube_r17 = sidewall_port.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(70, 391).addBox(-68.2074F, 49.484F, 21.032F, 13.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(68, 389).addBox(-69.2074F, 45.484F, 20.032F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(68, 389).addBox(-70.2074F, 41.484F, 22.032F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(68, 389).addBox(-68.2074F, 35.484F, 26.032F, 13.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, 1.5708F, 0.0F));

        PartDefinition sidewall_port_bow = port.addOrReplaceChild("sidewall_port_bow", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r18 = sidewall_port_bow.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(224, 304).addBox(16.7511F, 34.282F, 22.5146F, 26.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(144, 122).addBox(-21.0689F, 48.282F, 15.1946F, 51.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(144, 122).addBox(-20.8289F, 45.282F, 16.5946F, 53.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(144, 122).addBox(-21.9729F, 41.282F, 18.2266F, 57.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.275F, -22.7926F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r19 = sidewall_port_bow.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(144, 122).addBox(-24.2649F, 36.282F, 21.5146F, 65.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.275F, -22.7926F, 3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r20 = sidewall_port_bow.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(119, 237).addBox(-23.2F, -2.032F, -2.3F, 46.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.276F, -0.2728F, -33.5544F, 3.0181F, 1.0254F, 3.014F));

        PartDefinition hull_port = port.addOrReplaceChild("hull_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r21 = hull_port.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(97, 166).addBox(-16.2101F, 51.9196F, -9.6088F, 22.0F, 4.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -3.1416F, -0.0873F, 3.0107F));

        PartDefinition cube_r22 = hull_port.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(90, 205).addBox(-2.0F, -2.896F, -13.628F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0714F, -0.7252F, -12.5852F, 0.0F, 1.5708F, -0.1745F));

        PartDefinition cube_r23 = hull_port.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(252, 0).addBox(-10.0F, -2.0F, -28.5F, 21.0F, 4.0F, 57.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5434F, -0.2772F, 15.8503F, 3.1372F, 0.0873F, 3.0107F));

        PartDefinition stern_railing_port = port.addOrReplaceChild("stern_railing_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r24 = stern_railing_port.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(226, 311).addBox(-5.25F, -1.0F, -0.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5232F, -20.7069F, 48.5199F, -1.5708F, -0.3054F, -1.5708F));

        PartDefinition cube_r25 = stern_railing_port.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(226, 311).addBox(0.5F, 3.0F, -0.5F, 15.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(25.1811F, -24.5F, 36.5199F, -1.6623F, 0.3042F, 1.5433F));

        PartDefinition cube_r26 = stern_railing_port.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(225, 310).addBox(-5.5F, -1.0F, -1.0F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(25.0517F, -24.75F, 37.0029F, 3.1416F, -1.4835F, 3.1416F));

        PartDefinition cube_r27 = stern_railing_port.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(116, 282).addBox(-24.0F, -0.99F, -0.75F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.75F, 49.9148F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition bow_railing_port = port.addOrReplaceChild("bow_railing_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r28 = bow_railing_port.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(226, 311).addBox(-5.5232F, -1.2931F, 0.4801F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9768F, -20.7069F, -66.4801F, -1.5708F, -0.3054F, -1.5708F));

        PartDefinition cube_r29 = bow_railing_port.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(116, 282).addBox(-5.0F, -1.282F, 27.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -23.702F, -37.0852F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r30 = bow_railing_port.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(226, 311).addBox(-5.25F, -1.0F, -0.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.5232F, -20.7069F, -28.5199F, 1.0264F, 0.2635F, -1.7272F));

        PartDefinition cube_r31 = bow_railing_port.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(225, 310).mirror().addBox(-9.4204F, -3.58F, -2.1204F, 43.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(20.8961F, -21.418F, -37.07F, -3.1416F, 1.0472F, -3.1416F));

        PartDefinition deck_port = port.addOrReplaceChild("deck_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r32 = deck_port.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(248, 131).addBox(-66.2074F, 42.5F, 0.0F, 27.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(2, 312).addBox(-39.2074F, 42.5F, 13.0F, 13.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(294, 205).addBox(-26.2074F, 42.5F, 0.0F, 2.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(169, 267).addBox(-24.2074F, 42.5F, 13.0F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(352, 208).addBox(-11.2074F, 42.5F, 0.0F, 2.0F, 2.0F, 27.0F, new CubeDeformation(0.0F))
                .texOffs(349, 342).addBox(-9.2074F, 42.5F, 13.0F, 13.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(52, 303).addBox(3.7926F, 42.5F, 0.0F, 26.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cleats_port = port.addOrReplaceChild("cleats_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cleat_port_aft2 = cleats_port.addOrReplaceChild("cleat_port_aft2", CubeListBuilder.create(), PartPose.offset(28.0F, -17.8173F, -21.3052F));

        PartDefinition cube_r33 = cleat_port_aft2.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(437, 986).addBox(62.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, -40.1667F, -64.2074F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r34 = cleat_port_aft2.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(437, 986).addBox(62.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(437, 986).addBox(58.2074F, 39.0F, 27.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, -40.1667F, -61.2074F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cleat_port_aft = cleats_port.addOrReplaceChild("cleat_port_aft", CubeListBuilder.create(), PartPose.offset(56.0F, -59.0F, -25.7926F));

        PartDefinition cube_r35 = cleat_port_aft.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(437, 986).addBox(62.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r36 = cleat_port_aft.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(437, 986).addBox(62.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(437, 986).addBox(58.2074F, 39.0F, 27.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition transom_port = port.addOrReplaceChild("transom_port", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r37 = transom_port.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(193, 367).addBox(-74.7714F, 30.8908F, 23.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(187, 361).addBox(-75.7714F, 30.8908F, 14.0F, 6.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.628F, -22.6806F, -1.5708F, 1.4399F, -1.5708F));

        PartDefinition cube_r38 = transom_port.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(180, 339).addBox(-14.0F, 30.8908F, -76.7714F, 14.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.628F, -22.6806F, -3.0107F, 0.0F, -3.1416F));

        PartDefinition cube_r39 = transom_port.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(63, 377).addBox(-15.0F, 33.5F, -72.2074F, 16.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r40 = transom_port.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(1, 342).addBox(-71.2074F, 34.5F, 15.0F, 4.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, 1.5708F, 0.0F));

        PartDefinition starboard = sided.addOrReplaceChild("starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shrouds_starboard = starboard.addOrReplaceChild("shrouds_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shroud_starboard_r5 = shrouds_starboard.addOrReplaceChild("shroud_starboard_r5", CubeListBuilder.create().texOffs(136, 862).mirror().addBox(-1.0F, -27.5F, 0.0F, 1.0F, 28.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.7094F, -157.5834F, -34.0664F, 0.0F, 0.0F, 0.4712F));

        PartDefinition shroud_starboard_r6 = shrouds_starboard.addOrReplaceChild("shroud_starboard_r6", CubeListBuilder.create().texOffs(136, 862).mirror().addBox(-0.5F, -145.0F, -0.5F, 1.0F, 143.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-19.946F, -12.4205F, -27.8829F, 0.0393F, 0.0F, 0.0524F));

        PartDefinition mast2_r14 = shrouds_starboard.addOrReplaceChild("mast2_r14", CubeListBuilder.create().texOffs(-8, 129).addBox(102.5F, -11.7926F, 2.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition shackle3 = shrouds_starboard.addOrReplaceChild("shackle3", CubeListBuilder.create().texOffs(1007, 615).mirror().addBox(13.85F, -12.75F, -31.95F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r41 = shackle3.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(1016, 626).mirror().addBox(-1.5F, -2.0F, -0.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(14.1F, -14.25F, -31.45F, 0.0F, 0.4363F, 0.0F));

        PartDefinition sidewall_starboard = starboard.addOrReplaceChild("sidewall_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r42 = sidewall_starboard.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(420, 122).mirror().addBox(6.2487F, 48.5F, 23.8121F, 59.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(316, 165).mirror().addBox(5.2487F, 45.5F, 24.8121F, 49.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(108, 165).mirror().addBox(5.2487F, 41.5F, 26.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 173).mirror().addBox(5.2487F, 37.5F, 30.8121F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.4835F, 0.0F));

        PartDefinition cube_r43 = sidewall_starboard.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(70, 391).mirror().addBox(55.2074F, 49.484F, 21.032F, 13.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(68, 389).mirror().addBox(56.2074F, 45.484F, 20.032F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(68, 389).mirror().addBox(55.2074F, 41.484F, 22.032F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(68, 389).mirror().addBox(55.2074F, 35.484F, 26.032F, 13.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition sidewall_starboard_bow = starboard.addOrReplaceChild("sidewall_starboard_bow", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r44 = sidewall_starboard_bow.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(224, 304).mirror().addBox(-42.7511F, 34.282F, 22.5146F, 26.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(144, 122).mirror().addBox(-29.9311F, 48.282F, 15.1946F, 51.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(144, 122).mirror().addBox(-32.1711F, 45.282F, 16.5946F, 53.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(144, 122).mirror().addBox(-35.0271F, 41.282F, 18.2266F, 57.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.275F, -22.7926F, -3.1416F, -1.0472F, -3.1416F));

        PartDefinition cube_r45 = sidewall_starboard_bow.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(144, 122).mirror().addBox(-40.7351F, 36.282F, 21.5146F, 65.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.275F, -22.7926F, 3.1416F, -1.0472F, -3.1416F));

        PartDefinition cube_r46 = sidewall_starboard_bow.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(119, 237).mirror().addBox(-22.8F, -2.032F, -2.3F, 46.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.276F, -0.2728F, -33.5544F, 3.0181F, -1.0254F, -3.014F));

        PartDefinition hull_starboard = starboard.addOrReplaceChild("hull_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r47 = hull_starboard.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(97, 166).mirror().addBox(-5.7899F, 51.9196F, -9.6088F, 22.0F, 4.0F, 35.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -3.1416F, 0.0873F, -3.0107F));

        PartDefinition cube_r48 = hull_starboard.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(90, 205).mirror().addBox(-2.0F, -2.896F, -13.628F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0714F, -0.7252F, -12.5852F, 0.0F, -1.5708F, 0.1745F));

        PartDefinition cube_r49 = hull_starboard.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(252, 0).mirror().addBox(-11.0F, -2.0F, -28.5F, 21.0F, 4.0F, 57.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-10.5434F, -0.2772F, 15.8503F, 3.1372F, -0.0873F, -3.0107F));

        PartDefinition stern_railing_starboard = starboard.addOrReplaceChild("stern_railing_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r50 = stern_railing_starboard.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(226, 311).mirror().addBox(-3.75F, -1.0F, -0.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.5232F, -20.7069F, 48.5199F, -1.5708F, 0.3054F, 1.5708F));

        PartDefinition cube_r51 = stern_railing_starboard.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(226, 311).mirror().addBox(-15.5F, 3.0F, -0.5F, 15.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-25.1811F, -24.5F, 36.5199F, -1.6623F, -0.3042F, -1.5433F));

        PartDefinition cube_r52 = stern_railing_starboard.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(225, 310).mirror().addBox(-18.5F, -1.0F, -1.0F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-25.0517F, -24.75F, 37.0029F, 3.1416F, 1.4835F, -3.1416F));

        PartDefinition cube_r53 = stern_railing_starboard.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(116, 282).mirror().addBox(0.0F, -0.99F, -0.75F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -24.75F, 49.9148F, -3.1416F, 0.0F, -3.1416F));

        PartDefinition bow_railing_starboard = starboard.addOrReplaceChild("bow_railing_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r54 = bow_railing_starboard.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(226, 311).mirror().addBox(-3.4768F, -1.2931F, 0.4801F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.9768F, -20.7069F, -66.4801F, -1.5708F, 0.3054F, 1.5708F));

        PartDefinition cube_r55 = bow_railing_starboard.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(116, 282).mirror().addBox(1.0F, -1.282F, 27.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -23.702F, -37.0852F, -3.1416F, 0.0F, -3.1416F));

        PartDefinition cube_r56 = bow_railing_starboard.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(226, 311).mirror().addBox(-3.75F, -1.0F, -0.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-24.5232F, -20.7069F, -28.5199F, 1.0264F, -0.2635F, 1.7272F));

        PartDefinition cube_r57 = bow_railing_starboard.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(225, 310).addBox(-33.5796F, -3.58F, -2.1204F, 43.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.8961F, -21.418F, -37.07F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition deck_starboard = starboard.addOrReplaceChild("deck_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r58 = deck_starboard.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(248, 131).mirror().addBox(39.2074F, 42.5F, 0.0F, 27.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(2, 312).mirror().addBox(26.2074F, 42.5F, 13.0F, 13.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(294, 205).mirror().addBox(24.2074F, 42.5F, 0.0F, 2.0F, 2.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(169, 267).mirror().addBox(11.2074F, 42.5F, 13.0F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(352, 208).mirror().addBox(9.2074F, 42.5F, 0.0F, 2.0F, 2.0F, 27.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(349, 342).mirror().addBox(-3.7926F, 42.5F, 13.0F, 13.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(52, 303).mirror().addBox(-29.7926F, 42.5F, 0.0F, 26.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cleats_starboard = starboard.addOrReplaceChild("cleats_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cleat_port_aft3 = cleats_starboard.addOrReplaceChild("cleat_port_aft3", CubeListBuilder.create(), PartPose.offset(-28.0F, -17.8173F, -21.3052F));

        PartDefinition cube_r59 = cleat_port_aft3.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(437, 986).mirror().addBox(-63.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, -40.1667F, -64.2074F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r60 = cleat_port_aft3.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(437, 986).mirror().addBox(-63.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(437, 986).mirror().addBox(-64.2074F, 39.0F, 27.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, -40.1667F, -61.2074F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cleat_port_aft4 = cleats_starboard.addOrReplaceChild("cleat_port_aft4", CubeListBuilder.create(), PartPose.offset(-56.0F, -59.0F, -25.7926F));

        PartDefinition cube_r61 = cleat_port_aft4.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(437, 986).mirror().addBox(-63.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r62 = cleat_port_aft4.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(437, 986).mirror().addBox(-63.2074F, 40.0F, 27.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(437, 986).mirror().addBox(-64.2074F, 39.0F, 27.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition transom_starboard = starboard.addOrReplaceChild("transom_starboard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r63 = transom_starboard.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(193, 367).mirror().addBox(69.7714F, 30.8908F, 23.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(187, 361).mirror().addBox(69.7714F, 30.8908F, 14.0F, 6.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.628F, -22.6806F, -1.5708F, -1.4399F, 1.5708F));

        PartDefinition cube_r64 = transom_starboard.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(180, 339).mirror().addBox(0.0F, 30.8908F, -76.7714F, 14.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.628F, -22.6806F, -3.0107F, 0.0F, 3.1416F));

        PartDefinition cube_r65 = transom_starboard.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(63, 377).mirror().addBox(-1.0F, 33.5F, -72.2074F, 16.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -3.1416F, 0.0F, -3.1416F));

        PartDefinition cube_r66 = transom_starboard.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(1, 342).mirror().addBox(67.2074F, 34.5F, 15.0F, 4.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition forestay = static_parts.addOrReplaceChild("forestay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition forestay_r1 = forestay.addOrReplaceChild("forestay_r1", CubeListBuilder.create().texOffs(145, 873).addBox(-0.5F, -170.0F, -0.5F, 1.0F, 165.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -34.8348F, -113.8031F, -0.5004F, 0.0F, 0.0F));

        PartDefinition shackle2 = forestay.addOrReplaceChild("shackle2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r67 = shackle2.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(1007, 615).addBox(-0.5F, -0.75F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.3256F, -112.4696F, -0.48F, 0.0F, 0.0F));

        PartDefinition cube_r68 = shackle2.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(1016, 626).addBox(-1.5F, -5.75F, 2.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.25F, -116.5F, -0.5004F, 0.0F, 0.0F));

        PartDefinition keel = static_parts.addOrReplaceChild("keel", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r69 = keel.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(0, 6).addBox(-26.7926F, 49.5F, -3.0F, 95.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r70 = keel.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(282, 304).addBox(-33.2596F, -83.259F, -0.5F, 11.0F, 36.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -1.5708F, -1.2217F, -1.5708F));

        PartDefinition cube_r71 = keel.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(0, 205).addBox(0.1907F, -66.0673F, -0.5F, 46.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -1.5708F, -1.4399F, -1.5708F));

        PartDefinition cube_r72 = keel.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(218, 266).addBox(-2.7926F, -90.5F, -1.0F, 12.0F, 36.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 0.0F, -1.5708F, 3.1416F));

        PartDefinition mast = static_parts.addOrReplaceChild("mast", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition halliard_tie_r1 = mast.addOrReplaceChild("halliard_tie_r1", CubeListBuilder.create().texOffs(684, 987).addBox(123.5F, -13.2926F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(684, 987).addBox(112.5F, -13.2926F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(148, 341).addBox(-49.5F, -13.7926F, -3.0F, 15.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition main_mast = mast.addOrReplaceChild("main_mast", CubeListBuilder.create(), PartPose.offset(0.0F, -53.5F, -22.7926F));

        PartDefinition mast2_r15 = main_mast.addOrReplaceChild("mast2_r15", CubeListBuilder.create().texOffs(0, 137).addBox(38.5F, -12.7926F, -2.0F, 94.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(154, 61).addBox(-51.5F, -12.7926F, -2.0F, 90.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition mast_r1 = main_mast.addOrReplaceChild("mast_r1", CubeListBuilder.create().texOffs(157, 64).addBox(-1.0F, -3.0F, -5.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 64).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(152, 59).addBox(-1.0F, 4.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(152, 59).addBox(-1.0F, 3.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(152, 59).addBox(-1.0F, -5.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(152, 59).addBox(-1.0F, -4.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(157, 64).addBox(-1.0F, -3.0F, 4.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 64).addBox(-1.0F, -4.0F, 3.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 30.5F, -10.7926F, 2.3562F, 0.0F, -1.5708F));

        PartDefinition mast_r2 = main_mast.addOrReplaceChild("mast_r2", CubeListBuilder.create().texOffs(156, 63).addBox(-1.01F, 0.75F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(156, 63).addBox(-1.01F, -4.75F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 29.5F, -10.7926F, -1.5708F, 0.0F, -1.5708F));

        PartDefinition mast_r3 = main_mast.addOrReplaceChild("mast_r3", CubeListBuilder.create().texOffs(156, 63).addBox(-1.01F, 0.75F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(156, 63).addBox(-1.01F, -4.75F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 29.5F, -10.7926F, 0.0F, 0.0F, -1.5708F));

        PartDefinition bowsprit = static_parts.addOrReplaceChild("bowsprit", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r73 = bowsprit.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(100, 266).addBox(-3.0F, 34.0F, 39.7926F, 6.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r74 = bowsprit.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(102, 293).addBox(-13.717F, -51.8757F, -11.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(101, 292).addBox(-14.717F, -51.8757F, -10.0F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(101, 292).addBox(-14.717F, -51.8757F, -8.0F, 18.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(101, 292).addBox(4.283F, -51.8757F, -8.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(102, 293).addBox(-14.717F, -51.8757F, -6.0F, 33.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(100, 291).addBox(-15.717F, -52.8757F, -5.0F, 34.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(316, 238).addBox(-11.717F, -55.8757F, -2.0F, 31.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(154, 69).addBox(-4.717F, -59.8757F, -2.0F, 78.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(214, 205).addBox(-3.717F, -64.8757F, -2.0F, 36.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, 1.0908F, -1.5708F));

        PartDefinition cube_r75 = bowsprit.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(101, 292).mirror().addBox(-18.283F, -51.8757F, -8.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(102, 293).mirror().addBox(-18.283F, -51.8757F, -6.0F, 33.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(100, 291).mirror().addBox(-18.283F, -52.8757F, -5.0F, 34.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(101, 292).mirror().addBox(-3.283F, -51.8757F, -8.0F, 18.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(101, 292).mirror().addBox(3.717F, -51.8757F, -10.0F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(102, 293).mirror().addBox(10.717F, -51.8757F, -11.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, -1.0908F, 1.5708F));

        PartDefinition cube_r76 = bowsprit.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(100, 295).addBox(23.3335F, -65.4135F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(100, 295).addBox(23.3335F, -68.4135F, -1.0F, 38.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -53.5F, -22.7926F, 1.5708F, 0.9599F, -1.5708F));

        PartDefinition shackle_extra = static_parts.addOrReplaceChild("shackle_extra", CubeListBuilder.create().texOffs(1016, 626).addBox(-17.5F, -17.0F, -70.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1007, 615).addBox(-16.5F, -14.0F, -70.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));


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
                if(yindex == mainsail_vertical_sections-1 && zindex <= mainsail_horizontal_sections/2){
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
        //mainsail_main.y = 200;

        if(pBoat.getMainsailActive()){
            mainsail_main.yRot = mastRotation;//Mth.rotLerp(pPartialTicks, mainsail_main.yRot, mastRotation);
            mainsail.yRot = mastRotation;
        }


        float windWorldAngle = Mth.wrapDegrees(pBoat.getLocalWindAngleAndSpeed()[0]);
        float windSpeed = pBoat.getLocalWindAngleAndSpeed()[1]*20f;
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
        float animationTickFloat = animationTick+pPartialTicks;
        animationTickFloat = (-windSpeed * animationTickFloat);


        for (int zindex = 0; zindex < mainsail_horizontal_sections; zindex++) {
            for (int yindex = 0; yindex < mainsail_vertical_sections; yindex++) {
                if (sails[zindex][yindex] == null) {
                    break;
                }
                float luffFunction = (float) (5 * Math.sin(0.1 * ((zindex * mainsail_section_widths) + animationTickFloat + yindex * mainsail_section_widths)));
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

                if(windSpeed < 1){
                    mixFunction*=0.5;
                }

                mixFunction = Mth.clamp(mixFunction,0, 0.92f);

                if (mixFunction >= 0.9f) {
                    luffFunction = (float) (5 * Math.sin(0.1 * ((zindex * mainsail_section_widths) + animationTickFloat))) * falloff;
                }

                float finalfunction = ((airfoilFunction) * mixFunction + (luffFunction * (1.0f - mixFunction))) + ycurve * mixFunction;

                sails[zindex][yindex].x = (finalfunction);
            }


        }

        if(pBoat.getMainsailActive()){
            ModelPart gaff = mainsail_main.getChild("gaff");
            gaff.yRot = (float) Math.tan(sails[14][4].x / (15 * mainsail_section_widths));
        }


    }

    private static void animateJibsail(SloopEntity pBoat, float pPartialTicks, ModelPart jibsail_main, ModelPart[][] sails, float mastRotation, int animationTick) {
        ModelPart jibsail = jibsail_main.getChild("jibsail");

        float windWorldAngle = pBoat.getLocalWindAngleAndSpeed()[0];
        float windSpeed = pBoat.getLocalWindAngleAndSpeed()[1]*20f;
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

        if(windSpeed < 2){
            mastRotation*= windSpeed/2;
        }

        float boatWorldAngle = Mth.wrapDegrees(pBoat.getYRot());
        float boatWindDifference = Mth.degreesDifferenceAbs(windWorldAngle, boatWorldAngle);
        if(boatWindDifference < 10 || boatWindDifference > 170 && pBoat.getMainsailActive()){
            jibsail.yRot = -mastRotation;
            airFoilDirection*=-1;
        } else {
            jibsail.yRot = mastRotation;
        }


        for (int zindex = 0; zindex < jibsail_horizontal_sections; zindex++) {

            for (int yindex = 0; yindex < jibsail_vertical_sections; yindex++) {
                if (sails[zindex][yindex] == null) {
                    break;
                }
                float luffFunction = (float) (5 * Math.sin(0.1 * ((zindex * jibsail_section_widths) + animationTickFloat + yindex * jibsail_section_widths)));
                float squaredFunctionComponent = (zindex - 22f) * (zindex - 22f);

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
                    luffFunction = (float) (5 * Math.sin(0.1 * ((zindex * mainsail_section_widths) + animationTickFloat))) * falloff;
                }

                mixFunction = Mth.clamp(mixFunction,0, 0.92f);

                float finalfunction = ((airfoilFunction) * mixFunction + (luffFunction * (1.0f - mixFunction)));

                sails[zindex][yindex].x = (finalfunction);
            }
        }

    }

    private static void animateRudder(SloopEntity pBoat, float pPartialTicks, ModelPart rudder, float rudderRotation) {
        rudder.yRot = rudderRotation;// Mth.rotLerp(pPartialTicks, rudder.yRot, rudderRotation);
    }

    private static void animateWindIndicator(SloopEntity pBoat, float pPartialTicks, ModelPart wind_indicator){
        float windAngle = (float) Math.toRadians(pBoat.getWindLocalRotation());
        wind_indicator.yRot = (float) (windAngle);
        wind_indicator.zScale = 1;
        wind_indicator.xScale = 1;
        wind_indicator.zScale = pBoat.getLocalWindAngleAndSpeed()[1]*5;
    }

    private static void animateSailforceIndicator(SloopEntity pBoat, float pPartialTicks, ModelPart sail_force_indicator){
        float sailForceAngle = (float) Math.toRadians(Mth.wrapDegrees(pBoat.getMainsailWindAngleAndForce()[0])-pBoat.getYRot());
        float sailForce = Mth.clamp(pBoat.getMainsailWindAngleAndForce()[1], 0.1f, 10f);
        sail_force_indicator.yRot =(float) (sailForceAngle);
        sail_force_indicator.zScale = 1;
        sail_force_indicator.xScale = 1;
        sail_force_indicator.zScale = sailForce;
        sail_force_indicator.xScale = sailForce;
    }


    public void setupAnim(SloopEntity pEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                          float headPitch) {
        if (mainSailParts[0][0] == null) {
            mainSailParts = getMainsailParts(this);
        }
        if (jibSailParts[0][0] == null) {
            jibSailParts = getJibsailParts(this);
        }
        animateMainsail(pEntity, limbSwing, mainsail_deployed,mainsail, mainSailParts, (float) Math.toRadians(pEntity.getMainBoomRotation()), pEntity.tickCount);
        animateJibsail(pEntity, limbSwing, jibsail_deployed, jibSailParts, (float) Math.toRadians(pEntity.getMainBoomRotation()), pEntity.tickCount);
        animateRudder(pEntity, limbSwing, rudder, (float) Math.toRadians(pEntity.getRudderRotation()));

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

    public ModelPart getWaterocclusion() {
        return this.waterocclusion;
    }

    public ModelPart getJibsail(){return this.jibsail_deployed;}

    public ModelPart getJibsailFurled(){return this.jibsail_furled;}

    public ModelPart getMainsailDeployedParts(){return this.mainsail_deployed;}

    public ModelPart getMainsailFurledParts(){return this.mainsail_furled;}

    public ModelPart getMainsail(){return this.mainsail;}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        mainsail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mainsail_deployed.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mainsail_furled.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rudder.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mainsheet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        waterocclusion.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        jibsail_deployed.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        jibsail_furled.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        windlass.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        static_parts.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);

    }
}