package com.hyperdash.firmaciv.common.entity.vehiclehelper;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class VehicleCleatEntity extends Mob {

    @Nullable
    protected VehiclePartEntity ridingThisPart = null;

    public VehicleCleatEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2000D).add(Attributes.FOLLOW_RANGE, 30D).add(Attributes.MOVEMENT_SPEED, 0.0D);
    }


    public void baseTick() {
        super.baseTick();
        this.level().getProfiler().push("mobBaseTick");

        this.heal(this.getMaxHealth());
        if(this.getVehicle() == null && ridingThisPart != null && !ridingThisPart.isVehicle()){
            this.startRiding(ridingThisPart);
        }

        /*
        if(this.isLeashed()){
            if(this.getLeashHolder() instanceof Player player){
                if(this.distanceTo(player) > 4f){
                    Vec3 movementVector = player.getPosition(0).vectorTo(this.getPosition(0)).normalize();
                    movementVector = new Vec3(movementVector.x*-0.1f, movementVector.y*-0.1f, movementVector.z*-0.1f);
                    if(player.getY() < this.getY()){
                        movementVector = new Vec3(movementVector.x*-0.1f, this.getDeltaMovement().y, movementVector.z*-0.1f);
                    }
                    this.setDeltaMovement(movementVector);
                }
            }
        }
        *
         */

        this.level().getProfiler().pop();
    }

    /*
    @Override
    public void setLeashedTo(Entity pLeashHolder, boolean pBroadcastPacket) {
        super.setLeashedTo(pLeashHolder, pBroadcastPacket);
    }

    @Override
    public boolean startRiding(Entity pVehicle) {
        if(pVehicle instanceof VehiclePartEntity vehiclePartEntity){
            ridingThisPart = vehiclePartEntity;
        }
        return this.startRiding(pVehicle, false);
    }

    public boolean startRiding(Entity pVehicle, boolean pForce) {
        Entity thisAsEntity = (Entity)this;
        return thisAsEntity.startRiding(pVehicle,pForce);
    }

     */
    @Override
    public void playHurtSound(DamageSource pSource) {
    }

}
