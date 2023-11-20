package com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities;

import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.FirmacivBoatEntity;
import com.hyperdash.firmaciv.entity.custom.RowboatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VehiclePartEntity extends Entity {

    private int emptyTicks = 0;
    private int selfDestructTicks = 5;

    protected float compartmentRotation;

    public VehiclePartEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        emptyTicks = 0;
        selfDestructTicks = 5;
        compartmentRotation = 0;
    }

    @Override
    public void tick() {
        if(tickCount < 10){
            if(this.getVehicle() instanceof RowboatEntity rowboatEntity){
                if(rowboatEntity.getPilotVehiclePartAsEntity() == this){
                    compartmentRotation = 180;
                }
            }
        }

        if (this.getPassengers().isEmpty()) {
            emptyTicks++;
            if (emptyTicks > 2) {
                this.ejectPassengers();
                EmptyCompartmentEntity newCompartment = FirmacivEntities.EMPTY_COMPARTMENT_ENTITY.get().create(this.level());
                newCompartment.setYRot(this.getYRot() + compartmentRotation);
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


    public void ejectMyCompartmentsPassenger() {

    }

    @Override
    protected void positionRider(final Entity passenger, final Entity.MoveFunction moveFunction) {
        if (this.getVehicle() instanceof FirmacivBoatEntity firmacivBoatEntity) {
            if (this.hasPassenger(passenger)) {
                final float f = 0.0F;
                final float f1 = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
                final Vec3 vec3 = (new Vec3(f, 0, 0)).yRot(-this.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
                moveFunction.accept(passenger, this.getX(), this.getY() + (double) f1, this.getZ());
                passenger.setPos(this.getX(), this.getY() + (double) f1, this.getZ());
                if (passenger instanceof AbstractCompartmentEntity) {
                    passenger.setYRot(passenger.getYRot() + firmacivBoatEntity.getDeltaRotation());
                    float thingy = passenger.getYRot() + firmacivBoatEntity.getDeltaRotation() + compartmentRotation;
                    if (Math.abs((passenger.getYRot()) - (firmacivBoatEntity.getYRot()+compartmentRotation)) > 1 && (tickCount < 10 || this.getVehicle().getControllingPassenger() == null)) {
                        this.setYRot(this.getVehicle().getYRot());
                        passenger.setYRot(this.getYRot() + compartmentRotation);
                    }
                }
            }
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {

    }
}