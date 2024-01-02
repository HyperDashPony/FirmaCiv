package com.alekiponi.firmaciv.client.model.entity;// Made with Blockbench 4.9.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class CleatEntityKnotModel<T extends VehicleCleatEntity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Firmaciv.MOD_ID, "cleat_knot_model"), "main");
	private final ModelPart sides;
	private final ModelPart middle;

	public CleatEntityKnotModel() {
		ModelPart root = createBodyLayer().bakeRoot();
		this.sides = root.getChild("sides");
		this.middle = root.getChild("middle");
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition sides = partdefinition.addOrReplaceChild("sides", CubeListBuilder.create(), PartPose.offset(0.0F, 21.5F, 0.0F));

		PartDefinition cube_r1 = sides.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 5).addBox(-2.5F, 1.5F, -2.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(4, 0).addBox(0.5F, 1.5F, 0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition middle = partdefinition.addOrReplaceChild("middle", CubeListBuilder.create(), PartPose.offset(0.0F, 21.25F, 0.0F));

		PartDefinition cube_r2 = middle.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -0.25F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r3 = middle.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 4).addBox(-2.5F, 0.0F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, 0.0F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	public ModelPart getSides() {
		return this.sides;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		middle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}