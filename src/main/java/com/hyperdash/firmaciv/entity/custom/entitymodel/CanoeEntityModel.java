package com.hyperdash.firmaciv.entity.custom.entitymodel;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
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
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Firmaciv.MOD_ID, "canoe_entity"), "main");
    private final ModelPart middle;
    private final ModelPart end;
    private final ModelPart end2;

    private final ModelPart waterocclusion;

	/*
	public CanoeEntityModel(ModelPart pRoot) {
		//ModelPart root = createBodyLayer().bakeRoot();
		this.middle = pRoot.getChild("middle");
		this.end = pRoot.getChild("end");
		this.end2 = pRoot.getChild("end2");
		this.waterocclusion = pRoot.getChild("waterocclusion");
	}

	 */

    public CanoeEntityModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.middle = root.getChild("middle");
        this.end = root.getChild("end");
        this.end2 = root.getChild("end2");
        this.waterocclusion = root.getChild("waterocclusion");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition middle = partdefinition.addOrReplaceChild("middle", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone = middle.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(21, 83).addBox(-8.0F, -9.0F, -11.0F, 2.0F, 4.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(48, 88).addBox(-6.0F, -6.0F, -11.0F, 2.0F, 4.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(75, 93).addBox(-4.0F, -3.0F, -11.0F, 2.0F, 2.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(102, 96).addBox(-2.0F, -2.0F, -11.0F, 4.0F, 2.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(133, 93).addBox(2.0F, -3.0F, -11.0F, 2.0F, 2.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(160, 88).addBox(4.0F, -6.0F, -11.0F, 2.0F, 4.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(187, 83).addBox(6.0F, -9.0F, -11.0F, 2.0F, 4.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition end = partdefinition.addOrReplaceChild("end", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone6 = end.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(129, 195).addBox(2.0F, -5.0F, -9.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(129, 187).addBox(3.0F, -9.0F, -8.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(129, 168).addBox(3.0F, -6.0F, -5.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(129, 157).addBox(5.0F, -9.0F, -5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(129, 201).addBox(1.0F, -9.0F, -10.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(129, 211).addBox(1.0F, -11.0F, -11.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(129, 208).addBox(2.0F, -10.0F, -10.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -12.0F));

        PartDefinition bone2 = end.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(129, 178).addBox(1.0F, -4.0F, -17.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(120, 121).addBox(-1.0F, -3.0F, -17.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(111, 178).addBox(-3.0F, -4.0F, -17.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone3 = end.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(120, 130).addBox(-2.0F, -5.0F, -21.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone7 = end.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(115, 195).addBox(-4.0F, -5.0F, -9.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(117, 187).addBox(-5.0F, -9.0F, -8.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(111, 168).addBox(-5.0F, -6.0F, -5.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(111, 157).addBox(-7.0F, -9.0F, -5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(124, 137).addBox(-1.0F, -7.0F, -11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(117, 201).addBox(-4.0F, -9.0F, -10.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(121, 211).addBox(-3.0F, -11.0F, -11.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(124, 142).addBox(-1.0F, -12.0F, -13.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(121, 208).addBox(-4.0F, -10.0F, -10.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -12.0F));

        PartDefinition end2 = partdefinition.addOrReplaceChild("end2", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone4 = end2.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(115, 16).addBox(-4.0F, -5.0F, 5.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(117, 22).addBox(-5.0F, -9.0F, 5.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(111, 39).addBox(-5.0F, -6.0F, -1.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(111, 49).addBox(-7.0F, -9.0F, -1.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(117, 9).addBox(-4.0F, -9.0F, 8.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(121, 0).addBox(-3.0F, -11.0F, 10.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(121, 6).addBox(-4.0F, -10.0F, 9.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

        PartDefinition bone8 = end2.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(111, 30).addBox(-3.0F, -4.0F, 11.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(120, 87).addBox(-1.0F, -3.0F, 11.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(129, 30).addBox(1.0F, -4.0F, 11.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone9 = end2.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(120, 80).addBox(-2.0F, -5.0F, 17.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone10 = end2.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(129, 16).addBox(2.0F, -5.0F, 5.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(129, 22).addBox(3.0F, -9.0F, 5.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(129, 39).addBox(3.0F, -6.0F, -1.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(124, 75).addBox(-1.0F, -7.0F, 9.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(129, 9).addBox(1.0F, -9.0F, 8.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(129, 0).addBox(1.0F, -11.0F, 10.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(129, 49).addBox(5.0F, -9.0F, -1.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(124, 67).addBox(-1.0F, -12.0F, 11.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(129, 6).addBox(2.0F, -10.0F, 9.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

        PartDefinition waterocclusion = partdefinition.addOrReplaceChild("waterocclusion", CubeListBuilder.create().texOffs(0, 91).addBox(-6.0F, -8.0F, -17.0F, 12.0F, 3.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(14, 113).addBox(-3.0F, -8.0F, 17.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(14, 119).addBox(-3.0F, -8.0F, -21.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public ModelPart getWaterocclusion() {
        return this.waterocclusion;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        middle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        end.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        end2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);

    }
}