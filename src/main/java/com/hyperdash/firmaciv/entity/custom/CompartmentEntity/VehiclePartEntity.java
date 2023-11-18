package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.FirmacivBoatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VehiclePartEntity extends Entity {

    public VehiclePartEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        emptyTicks = 0;
        selfDestructTicks = 5;
    }

    private int emptyTicks = 0;
    private int selfDestructTicks = 5;
    @Override
    public void tick() {

        if(this.getPassengers().isEmpty()){
            emptyTicks++;
            if(emptyTicks > 2){
                this.ejectPassengers();
                EmptyCompartmentEntity newCompartment = FirmacivEntities.EMPTY_COMPARTMENT_ENTITY.get().create(this.level());
                newCompartment.setYRot(this.getYRot());
                newCompartment.setPos(this.getX(), this.getY(), this.getZ());
                newCompartment.startRiding(this);
                this.level().addFreshEntity(newCompartment);
            }
        } else {
            emptyTicks = 0;
        }


        if(!this.isPassenger()) {
            selfDestructTicks--;
            if(selfDestructTicks == 0){
                this.ejectPassengers();
                this.remove(RemovalReason.DISCARDED);
            }

        }

        super.tick();

    }


    public void ejectMyCompartmentsPassenger(){

    }
    @Override
    protected void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
        if(this.getVehicle() instanceof FirmacivBoatEntity firmacivBoatEntity) {
            if (this.hasPassenger(pPassenger)) {
                float f = 0.0F;
                float f1 = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()) + pPassenger.getMyRidingOffset());
                Vec3 vec3 = (new Vec3((double) f, 0.0D, 0.0D)).yRot(-this.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
                pCallback.accept(pPassenger, this.getX() + vec3.x, this.getY() + (double) f1, this.getZ() + vec3.z);
                pPassenger.setPos(this.getX() + vec3.x, this.getY() + (double) f1, this.getZ() + vec3.z);
                if (pPassenger instanceof AbstractCompartmentEntity) {
                    pPassenger.setYRot(pPassenger.getYRot() + firmacivBoatEntity.getDeltaRotation());

                    float thing = Math.abs(pPassenger.getYRot() - firmacivBoatEntity.getYRot());
                    if(Math.abs(pPassenger.getYRot() - firmacivBoatEntity.getYRot()) > 1 && (tickCount < 10 || this.getVehicle().getControllingPassenger() == null)){
                        this.setYRot(this.getVehicle().getYRot());
                        pPassenger.setYRot(this.getYRot());
                    }
                }

            }
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
}
