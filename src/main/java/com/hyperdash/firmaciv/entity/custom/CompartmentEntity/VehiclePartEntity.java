package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.FirmacivBoatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VehiclePartEntity extends Entity {

    private int emptyTicks = 0;
    private int selfDestructTicks = 5;

    public VehiclePartEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        emptyTicks = 0;
        selfDestructTicks = 5;
    }

    @Override
    public void tick() {

        if (this.getPassengers().isEmpty()) {
            emptyTicks++;
            if (emptyTicks > 2) {
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


        if (!this.isPassenger()) {
            selfDestructTicks--;
            if (selfDestructTicks == 0) {
                this.ejectPassengers();
                this.remove(RemovalReason.DISCARDED);
            }

        }

        super.tick();

    }


    @Override
    protected void positionRider(final Entity passenger, final Entity.MoveFunction moveFunction) {
        if (this.getVehicle() instanceof FirmacivBoatEntity firmacivBoatEntity) {
            if (this.hasPassenger(passenger)) {
                final float f = 0.0F;
                final float f1 = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
                Vec3 vec3 = (new Vec3((double) f, 0.0D, 0.0D)).yRot(-this.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
                moveFunction.accept(passenger, this.getX() + vec3.x, this.getY() + (double) f1, this.getZ() + vec3.z);
                passenger.setPos(this.getX() + vec3.x, this.getY() + (double) f1, this.getZ() + vec3.z);
                if (passenger instanceof AbstractCompartmentEntity) {
                    //pPassenger.setYRot(pPassenger.getYRot() + firmacivBoatEntity.getDeltaRotation());
                    passenger.setYRot(passenger.getYRot() + firmacivBoatEntity.getDeltaRotation());
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