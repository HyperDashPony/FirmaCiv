package com.hyperdash.firmaciv.mixins;

import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import com.hyperdash.firmaciv.entity.custom.CompartmentEntity.EmptyCompartmentEntity;
import com.hyperdash.firmaciv.entity.custom.CompartmentEntity.VehiclePartEntity;
import com.hyperdash.firmaciv.entity.custom.KayakEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
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
        if(pEntity.isPassenger() && pEntity.getVehicle() instanceof EmptyCompartmentEntity emptyCompartmentEntity){
            if(emptyCompartmentEntity.getTrueVehicle() instanceof CanoeEntity){
                if (this.riding) {
                    this.rightLeg.xRot = -1.570796F;
                    this.rightLeg.yRot = 0F;
                    this.rightLeg.zRot = 0F;

                    this.leftLeg.xRot = -1.570796F;
                    this.leftLeg.yRot = -0.002F;
                    this.leftLeg.zRot = -0.002F;

                    this.rightLeg.setPos(-1.9F, 11.5F, 0.0F);
                    this.leftLeg.setPos(01.9F, 11.5F, 0.0F);
                }

            } else if(emptyCompartmentEntity.getTrueVehicle() instanceof KayakEntity){
                if (this.riding) {
                    this.rightLeg.xRot = -1.570796F;
                    this.rightLeg.yRot = 0.1570796F;
                    this.rightLeg.zRot = 0F;

                    this.leftLeg.xRot = -1.570796F;
                    this.leftLeg.yRot = -0.1570796F;
                    this.leftLeg.zRot = -0F;

                    this.rightLeg.setPos(-1.9F, 13.6F, 1F);
                    this.leftLeg.setPos(01.9F, 13.6F, 1F);
                }
            }

        } else {
            this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
            this.leftLeg.setPos(01.9F, 12.0F, 0.0F);
            if (this.crouching) {
                this.rightLeg.z = 4.0F;
                this.leftLeg.z = 4.0F;
                this.rightLeg.y = 12.2F;
                this.leftLeg.y = 12.2F;
            }
        }

    }
}
