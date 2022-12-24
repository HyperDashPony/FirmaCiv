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

		PartDefinition middle = partdefinition.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 20).addBox(-6.0F, -6.0F, -11.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 40).addBox(-4.0F, -3.0F, -11.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -2.0F, -11.0F, 4.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(72, 20).addBox(2.0F, -3.0F, -11.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(76, 0).addBox(4.0F, -6.0F, -11.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(40, 0).addBox(6.0F, -9.0F, -11.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(36, 20).addBox(-8.0F, -9.0F, -11.0F, 2.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone5 = middle.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(64, 58).addBox(-6.0F, -6.0F, 5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(48, 78).addBox(-4.0F, -3.0F, 5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(36, 40).addBox(-2.0F, -2.0F, 5.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(32, 78).addBox(2.0F, -3.0F, 5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(48, 58).addBox(4.0F, -6.0F, 5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(32, 58).addBox(6.0F, -9.0F, 5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(16, 58).addBox(-8.0F, -9.0F, 5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition end = partdefinition.addOrReplaceChild("end", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone6 = end.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(32, 94).addBox(2.0F, -5.0F, -9.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(22, 94).addBox(3.0F, -9.0F, -8.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(64, 68).addBox(3.0F, -6.0F, -5.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 68).addBox(5.0F, -9.0F, -5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(74, 94).addBox(1.0F, -9.0F, -10.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(50, 101).addBox(1.0F, -11.0F, -11.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 105).addBox(2.0F, -10.0F, -10.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -12.0F));

		PartDefinition bone2 = end.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 86).addBox(1.0F, -4.0F, -17.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(80, 78).addBox(-1.0F, -3.0F, -17.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(64, 78).addBox(-3.0F, -4.0F, -17.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone3 = end.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(32, 86).addBox(-2.0F, -5.0F, -21.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone7 = end.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(10, 94).addBox(-4.0F, -5.0F, -9.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 94).addBox(-5.0F, -9.0F, -8.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(48, 68).addBox(-5.0F, -6.0F, -5.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 58).addBox(-7.0F, -9.0F, -5.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(24, 101).addBox(-1.0F, -7.0F, -11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(64, 94).addBox(-4.0F, -9.0F, -10.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(44, 101).addBox(-3.0F, -11.0F, -11.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(8, 101).addBox(-1.0F, -12.0F, -13.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(56, 103).addBox(-4.0F, -10.0F, -10.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -12.0F));

		PartDefinition end2 = partdefinition.addOrReplaceChild("end2", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone4 = end2.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(80, 86).addBox(-4.0F, -5.0F, 5.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(70, 86).addBox(-5.0F, -9.0F, 5.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(32, 68).addBox(-5.0F, -6.0F, -1.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(56, 40).addBox(-7.0F, -9.0F, -1.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(54, 94).addBox(-4.0F, -9.0F, 8.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(38, 101).addBox(-3.0F, -11.0F, 10.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 101).addBox(-4.0F, -10.0F, 9.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

		PartDefinition bone8 = end2.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(16, 78).addBox(-3.0F, -4.0F, 11.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 78).addBox(-1.0F, -3.0F, 11.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(80, 68).addBox(1.0F, -4.0F, 11.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone9 = end2.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(16, 86).addBox(-2.0F, -5.0F, 17.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone10 = end2.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(58, 86).addBox(2.0F, -5.0F, 5.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 86).addBox(3.0F, -9.0F, 5.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(16, 68).addBox(3.0F, -6.0F, -1.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(16, 101).addBox(-1.0F, -7.0F, 9.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(44, 94).addBox(1.0F, -9.0F, 8.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 101).addBox(1.0F, -11.0F, 10.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(36, 48).addBox(5.0F, -9.0F, -1.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 101).addBox(-1.0F, -12.0F, 11.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(50, 106).addBox(2.0F, -10.0F, 9.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

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