package com.hyperdash.firmaciv.client.model.entity;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.common.entity.RowboatEntity;
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

public class RowboatEntityModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(Firmaciv.MOD_ID, "rowboat_entity"), "main");
    private final ModelPart waterocclusion;
    private final ModelPart hull;
    private final ModelPart oar_port;
    private final ModelPart oar_starboard;
    private final ModelPart bow_floor;
    private final ModelPart seats;
    private final ModelPart bow;
    private final ModelPart port_bow;
    private final ModelPart starboard_bow;
    private final ModelPart port;
    private final ModelPart starboard;
    private final ModelPart oarlocks;
    private final ModelPart keel;
    private final ModelPart transom;

    public RowboatEntityModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.waterocclusion = root.getChild("waterocclusion");
        this.hull = root.getChild("hull");
        this.oar_port = root.getChild("oar_port");
        this.oar_starboard = root.getChild("oar_starboard");
        this.bow_floor = root.getChild("bow_floor");
        this.seats = root.getChild("seats");
        this.bow = root.getChild("bow");
        this.port_bow = root.getChild("port_bow");
        this.starboard_bow = root.getChild("starboard_bow");
        this.port = root.getChild("port");
        this.starboard = root.getChild("starboard");
        this.oarlocks = root.getChild("oarlocks");
        this.keel = root.getChild("keel");
        this.transom = root.getChild("transom");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition waterocclusion = partdefinition.addOrReplaceChild("waterocclusion", CubeListBuilder.create(),
                PartPose.offset(0.0123F, 13.0F, -8.7623F));

        PartDefinition cube_r1 = waterocclusion.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(192, 50)
                        .addBox(-15.513F, -1.0F, -15.539F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0061F, 0.0F, 2.6189F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r2 = waterocclusion.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(200, 71)
                        .addBox(-9.8566F, -1.0F, -11.9816F, 4.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                        .texOffs(62, 224).addBox(-5.8566F, -1.0F, -12.9816F, 33.0F, 2.0F, 26.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0061F, 0.0F, 2.6189F, 0.0F, -1.5708F, 0.0F));

        PartDefinition hull = partdefinition.addOrReplaceChild("hull", CubeListBuilder.create(),
                PartPose.offset(0.0F, 22.5F, 7.1667F));

        PartDefinition cube_r3 = hull.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(154, 0)
                        .addBox(-12.5F, -2.25F, -12.0F, 27.0F, 3.0F, 24.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 113).addBox(-14.5F, -0.25F, 1.0F, 27.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.25F, 0.3333F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r4 = hull.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(59, 113)
                        .addBox(-12.5F, -0.25F, 1.0F, 27.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.25F, 0.3333F, 0.0F, 1.5708F, 0.0F));

        PartDefinition oar_starboard = partdefinition.addOrReplaceChild("oar_starboard",
                CubeListBuilder.create().texOffs(129, 194)
                        .addBox(-21.875F, -0.5F, -0.5F, 30.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 189).addBox(8.125F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 202).addBox(-35.875F, -0.5F, -2.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(115, 197).addBox(-24.875F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-14.4971F, 10.7636F, -1.2288F, -0.3441F, 0.8192F, -0.456F));

        PartDefinition oar_port = partdefinition.addOrReplaceChild("oar_port", CubeListBuilder.create().texOffs(65, 194)
                        .addBox(-8.125F, -0.5F, -0.5F, 30.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(119, 189).addBox(-9.125F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(95, 202).addBox(24.875F, -0.5F, -2.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 197).addBox(21.875F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.4971F, 10.7636F, -1.2288F, 2.7975F, -0.8192F, 0.456F));

        PartDefinition bow_floor = partdefinition.addOrReplaceChild("bow_floor", CubeListBuilder.create(),
                PartPose.offset(0.0F, 19.8333F, -13.1111F));

        PartDefinition cube_r5 = bow_floor.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(125, 9)
                        .addBox(-22.4826F, 2.6196F, 13.5743F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(135, 8).addBox(-19.4826F, 3.6196F, 12.5743F, 2.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(145, 7).addBox(-17.4826F, 4.6196F, 11.5743F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(155, 6).addBox(-15.4826F, 5.6196F, 10.5743F, 2.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                        .texOffs(155, 5).addBox(-13.4826F, 5.6196F, 9.5743F, 2.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                        .texOffs(155, 3).addBox(-9.4826F, 5.6196F, 7.5743F, 2.0F, 1.0F, 18.0F, new CubeDeformation(0.0F))
                        .texOffs(155, 2).addBox(-7.4826F, 5.6196F, 6.5743F, 2.0F, 1.0F, 20.0F, new CubeDeformation(0.0F))
                        .texOffs(155, 1).addBox(-5.4826F, 5.6196F, 5.5743F, 1.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                        .texOffs(155, 4).addBox(-11.4826F, 5.6196F, 8.5743F, 2.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.5743F, -5.4529F, 12.5937F, 0.0F, -1.5708F, 0.0F));

        PartDefinition seats = partdefinition.addOrReplaceChild("seats", CubeListBuilder.create(),
                PartPose.offset(0.0F, 15.5F, -13.9286F));

        PartDefinition mid_seat_r1 = seats.addOrReplaceChild("mid_seat_r1", CubeListBuilder.create().texOffs(0, 233)
                        .addBox(-3.0F, -0.5F, -11.0F, 6.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 18.9286F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r6 = seats.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(5, 209)
                        .addBox(-1.0F, -0.5F, -11.0F, 2.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 2.6786F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r7 = seats.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(8, 187)
                        .addBox(-1.0F, -0.5F, -10.0F, 2.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.6786F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r8 = seats.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(7, 167)
                        .addBox(-1.5F, -0.5F, -9.0F, 3.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -1.8214F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r9 = seats.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(17, 139)
                        .addBox(-0.5F, -0.5F, -6.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -6.8214F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r10 = seats.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(14, 153)
                        .addBox(-1.5F, -0.5F, -6.0F, 3.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -4.8214F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r11 = seats.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(17, 127)
                        .addBox(-1.5F, -0.5F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -8.8214F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bow = partdefinition.addOrReplaceChild("bow", CubeListBuilder.create().texOffs(21, 34)
                        .addBox(-1.5F, 4.6527F, 1.5894F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 15.0973F, -27.5894F));

        PartDefinition cube_r12 = bow.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(99, 9)
                        .addBox(-6.8433F, 0.8391F, -3.0F, 7.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(9, 7).addBox(-4.3433F, -6.1609F, -5.0F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0636F, -0.2538F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r13 = bow.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(217, 234)
                        .addBox(-1.0F, -0.3045F, -6.7929F, 2.0F, 4.0F, 15.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0636F, -0.2538F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r14 = bow.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(4, 33)
                        .addBox(-2.0F, -5.7847F, -7.6899F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0636F, -0.2538F, -0.2182F, 0.0F, 0.0F));

        PartDefinition port_bow = partdefinition.addOrReplaceChild("port_bow", CubeListBuilder.create(),
                PartPose.offset(4.7771F, 18.625F, -15.5328F));

        PartDefinition cube_r15 = port_bow.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(129, 150)
                        .addBox(-10.9973F, 4.75F, -7.9375F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 128).addBox(-4.9973F, 4.75F, -6.9375F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(117, 141).addBox(2.0027F, 4.75F, -4.9375F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(113, 135).addBox(-1.9973F, 4.75F, -5.9375F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(79, 123).addBox(-11.2473F, 4.75F, -2.9375F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5197F, -2.125F, 0.7505F, 0.0F, 1.8762F, 0.0F));

        PartDefinition cube_r16 = port_bow.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(79, 106)
                        .addBox(-11.9803F, 3.75F, -2.6886F, 20.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 146).addBox(8.0197F, 3.75F, 0.3114F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(67, 96).addBox(-13.2303F, -1.25F, 0.0614F, 26.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(71, 91).addBox(-13.2303F, -2.25F, 1.5614F, 26.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(65, 48).addBox(-13.2303F, -4.25F, 2.5614F, 27.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(69, 44).addBox(-13.2303F, -5.25F, 4.5614F, 27.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(97, 40).addBox(0.7697F, -6.25F, 4.5614F, 13.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5197F, -2.125F, 0.7505F, 0.0F, 2.0508F, 0.0F));

        PartDefinition starboard_bow = partdefinition.addOrReplaceChild("starboard_bow", CubeListBuilder.create(),
                PartPose.offset(-4.7771F, 18.625F, -15.5328F));

        PartDefinition cube_r17 = starboard_bow.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(105, 150)
                        .addBox(4.9973F, 4.75F, -7.9375F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(113, 128).addBox(1.9973F, 4.75F, -6.9375F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 135).addBox(-2.0027F, 4.75F, -5.9375F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 141).addBox(-5.0027F, 4.75F, -4.9375F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 123).addBox(-10.7527F, 4.75F, -2.9375F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5197F, -2.125F, 0.7505F, 0.0F, -1.8762F, 0.0F));

        PartDefinition cube_r18 = starboard_bow.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(117, 146)
                        .addBox(-12.0197F, 3.75F, 0.3114F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 106).addBox(-8.0197F, 3.75F, -2.6886F, 20.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 96).addBox(-12.7697F, -1.25F, 0.0614F, 26.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 91).addBox(-12.7697F, -2.25F, 1.5614F, 26.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 48).addBox(-13.7697F, -4.25F, 2.5614F, 27.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 44).addBox(-13.7697F, -5.25F, 4.5614F, 27.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 40).addBox(-13.7697F, -6.25F, 4.5614F, 13.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5197F, -2.125F, 0.7505F, 0.0F, -2.0508F, 0.0F));

        PartDefinition port = partdefinition.addOrReplaceChild("port", CubeListBuilder.create(),
                PartPose.offset(13.375F, 15.375F, 7.0F));

        PartDefinition cube_r19 = port.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(67, 76)
                        .addBox(-13.25F, -1.0F, -2.5F, 28.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(75, 80).addBox(-8.25F, 0.0F, -3.5F, 21.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(91, 67).addBox(-0.25F, -4.0F, -0.5F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(93, 57).addBox(-13.25F, -5.0F, -1.5F, 13.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.125F, 0.625F, 0.75F, 0.0F, 1.5708F, 0.0F));

        PartDefinition starboard = partdefinition.addOrReplaceChild("starboard", CubeListBuilder.create(),
                PartPose.offset(-13.375F, 15.375F, 7.0F));

        PartDefinition cube_r20 = starboard.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(129, 80)
                        .addBox(-14.25F, -0.75F, 9.75F, 21.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 76).addBox(-16.25F, -1.75F, 10.75F, 28.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 67).addBox(-15.25F, -4.75F, 12.75F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 57).addBox(-1.25F, -5.75F, 11.75F, 13.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(13.125F, 1.375F, 2.25F, 0.0F, -1.5708F, 0.0F));

        PartDefinition oarlocks = partdefinition.addOrReplaceChild("oarlocks",
                CubeListBuilder.create().texOffs(129, 169)
                        .addBox(-16.0F, -2.1667F, -2.1667F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(113, 169)
                        .addBox(13.0F, -2.1667F, -2.1667F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 177)
                        .addBox(-15.0F, -0.1667F, 1.8333F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(117, 177).addBox(13.0F, -0.1667F, 1.8333F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(129, 165)
                        .addBox(-15.0F, -0.1667F, -4.1667F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(119, 165)
                        .addBox(13.0F, -0.1667F, -4.1667F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 11.1667F, -0.8333F));

        PartDefinition keel = partdefinition.addOrReplaceChild("keel", CubeListBuilder.create().texOffs(164, 211)
                        .addBox(-1.0F, 0.25F, -34.75F, 2.0F, 2.0F, 43.0F, new CubeDeformation(0.0F))
                        .texOffs(182, 236).addBox(-1.0F, -4.75F, 8.25F, 2.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 21.75F, 8.75F));

        PartDefinition transom = partdefinition.addOrReplaceChild("transom", CubeListBuilder.create().texOffs(190, 146)
                        .addBox(-13.0F, 0.25F, -5.25F, 26.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(193, 159).addBox(-13.0F, -5.75F, -0.25F, 26.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 15.75F, 21.25F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    private static void animatePaddle(RowboatEntity pBoat, int pSide, ModelPart pPaddle, float pLimbSwing) {
        float f = pBoat.getRowingTime(pSide, pLimbSwing);
        if (pSide == 0) {
            pPaddle.xRot = -Mth.clampedLerp(-2.0471975512f, -1.2617994F,
                    -(Mth.sin(-f) + 1.0F) / 2.0F);
            pPaddle.yRot = Mth.clampedLerp(0.7853981634f, -0.7853981634f,
                    (Mth.sin(-f + 1.0F) + 1.0F) / 2.0F);
        }
        if (pSide == 1) {
            pPaddle.xRot = -Mth.clampedLerp(-2.0471975512f, -1.2617994F,
                    (Mth.sin(-f) + 1.0F) / 2.0F);
            pPaddle.yRot = Mth.clampedLerp(0.7853981634f, -0.7853981634f,
                    (Mth.sin(-f + 1.0F) + 1.0F) / 2.0F);
            pPaddle.yRot = -pPaddle.yRot;
        }

    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw,
                          float pHeadPitch) {

    }

    public ModelPart getWaterocclusion() {
        return this.waterocclusion;
    }

    public ModelPart getOarPort() {
        return this.oar_port;
    }

    public ModelPart getOarStarboard() {
        return this.oar_starboard;
    }

    public void setupAnim(RowboatEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
                          float pNetHeadYaw, float pHeadPitch) {
        animatePaddle(pEntity, 0, this.getOarPort(), pLimbSwing);
        animatePaddle(pEntity, 1, this.getOarStarboard(), pLimbSwing);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        hull.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bow_floor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        seats.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        port_bow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        starboard_bow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        port.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        starboard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        oarlocks.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        keel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        transom.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}