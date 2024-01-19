package com.alekiponi.firmaciv.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;

public class SloopConstructionModel extends SloopEntityModel{

    // getters for each construction state


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        //static_parts.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        ModelPart non_sided = static_parts.getChild("non_sided");
        ModelPart port = static_parts.getChild("sided").getChild("port");
        ModelPart starboard = static_parts.getChild("sided").getChild("starboard");
        port.getChild("sidewall_port_bow").render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        port.getChild("sidewall_port").render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        port.getChild("cleats_port").render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        port.getChild("hull_port").render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        port.getChild("transom_port").render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);

        starboard.getChild("sidewall_starboard_bow").render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        starboard.getChild("sidewall_starboard").render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        starboard.getChild("cleats_starboard").render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        starboard.getChild("hull_starboard").render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        starboard.getChild("transom_starboard").render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    private ModelPart getSided(){return this.static_parts.getChild("sided");}

    private ModelPart getNonSided(){return this.static_parts.getChild("non_sided");}

    private ModelPart getPort(){return this.getSided().getChild("port");}

    private ModelPart getStarboard(){return this.getSided().getChild("starboard");}

    public ModelPart getKeel() {
        return this.getNonSided().getChild("keel");
    }

    public ModelPart getMast() {
        return this.getNonSided().getChild("mast");
    }

    public ModelPart getBowsprit() {
        return this.getNonSided().getChild("bowsprit");
    }

    public ModelPart[] getDeck() {
        ModelPart[] deck = new ModelPart[2];
        deck[0] = this.getPort().getChild("deck_port");
        deck[1] = this.getStarboard().getChild("deck_starboard");
        return deck;
    }

    public ModelPart[] getBoomGaff() {
        ModelPart[] boomgaff = new ModelPart[2];
        boomgaff[0] = this.mainsail_furled.getChild("main_boom_furled");
        boomgaff[1] = this.mainsail_furled.getChild("gaff_furled").getChild("gaff_furled_beam");
        return boomgaff;
    }

    @Override
    public ModelPart getMainsail(){
        return this.mainsail_furled.getChild("furled_sail");
    }

    @Override
    public ModelPart getJibsail(){
        return this.jibsail_furled;
    }

    public ModelPart[] getSternRailing(){
        ModelPart[] sternrail = new ModelPart[2];
        sternrail[0] = getPort().getChild("stern_railing_port");
        sternrail[1] = getStarboard().getChild("stern_railing_starboard");
        return sternrail;
    }

    public ModelPart[] getBowRailing(){
        ModelPart[] bowrail = new ModelPart[2];
        bowrail[0] = getPort().getChild("bow_railing_port");
        bowrail[1] = getStarboard().getChild("bow_railing_starboard");
        return bowrail;
    }



}
