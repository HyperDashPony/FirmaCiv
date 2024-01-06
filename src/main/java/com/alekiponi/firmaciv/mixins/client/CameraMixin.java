package com.alekiponi.firmaciv.mixins.client;

import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.RowboatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow
    public abstract Entity getEntity();

    @Shadow
    protected abstract void setRotation(float pYRot, float pXRot);

    @Shadow
    protected abstract void setPosition(double pX, double pY, double pZ);

    @Shadow
    protected abstract void move(double pDistanceOffset, double pVerticalOffset, double pHorizontalOffset);

    @Shadow
    protected abstract double getMaxZoom(double pStartingDistance);

    @Shadow
    private float xRot;
    @Shadow
    private float yRot;

    @Shadow
    private float eyeHeight;
    @Shadow
    private float eyeHeightOld;


    @Inject(method = "setup", at = @At(value = "TAIL"), cancellable = true)
    public void injectCameraChanges(BlockGetter pLevel, Entity pEntity, boolean pDetached, boolean pThirdPersonReverse, float pPartialTick, CallbackInfo ci){

        if(pEntity.getVehicle() instanceof EmptyCompartmentEntity compartment && pDetached && !pThirdPersonReverse){
            if(compartment.getTrueVehicle() != null){
                AbstractFirmacivBoatEntity boat = compartment.getTrueVehicle();

                double boatSize = boat.getBbWidth();
                double cameraDistance = 4.0f;
                if(boatSize >1){
                    cameraDistance = cameraDistance * (boatSize);
                }
                this.setRotation(pEntity.getViewYRot(pPartialTick), pEntity.getViewXRot(pPartialTick));
                this.setPosition(Mth.lerp((double)pPartialTick, boat.xo, boat.getX()), Mth.lerp((double)pPartialTick, boat.yo, boat.getY()) + (double)Mth.lerp(pPartialTick, this.eyeHeightOld, this.eyeHeight), Mth.lerp((double)pPartialTick, boat.zo, boat.getZ()));
                if(boat instanceof RowboatEntity rowboatEntity){
                    if(rowboatEntity.getControllingCompartment() != null && rowboatEntity.getControllingCompartment().equals(compartment)){
                        this.setRotation(this.yRot + 180.0F, this.xRot);
                    }
                    if (pThirdPersonReverse){
                        this.setRotation(this.yRot, -this.xRot);
                    }
                }
                else if (pThirdPersonReverse) {
                    this.setRotation(this.yRot + 180.0F, -this.xRot);
                }

                this.move(-this.getMaxZoom(cameraDistance), 0.0, 0.0);
            }
        }


    }


}
