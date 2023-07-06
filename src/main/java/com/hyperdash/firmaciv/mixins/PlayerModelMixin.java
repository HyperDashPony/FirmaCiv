package com.hyperdash.firmaciv.mixins;

import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import com.hyperdash.firmaciv.entity.custom.FirmacivBoatEntity;
import com.hyperdash.firmaciv.entity.custom.KayakEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public class PlayerModelMixin <T extends LivingEntity> extends HumanoidModel<T>{

    public PlayerModelMixin(ModelPart pRoot) {
        super(pRoot);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "TAIL"))
    void injectRidingPoseChange(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci){
        if(pEntity.isPassenger() && pEntity.getVehicle() instanceof CanoeEntity)
        if (this.riding) {
            this.rightLeg.xRot = -1.518436F;
            this.rightLeg.yRot = 0.03490659F;
            this.rightLeg.zRot = 0.03490659F;

            this.leftLeg.xRot = -1.5184366F;
            this.leftLeg.yRot = -0.03490659F;
            this.leftLeg.zRot = -0.03490659F;
        }
    }
}
