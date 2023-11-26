package com.hyperdash.firmaciv.common.entity.vehiclehelper;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.common.entity.FirmacivBoatEntity;
import com.hyperdash.firmaciv.common.entity.FirmacivEntities;
import com.hyperdash.firmaciv.common.entity.RowboatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VehiclePartEntity extends Entity {
    protected float compartmentRotation = 0;
    private int selfDestructTicks = 0;

    private int noPassengerTicks = 0;

    public VehiclePartEntity(final EntityType<?> entityType, final Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (tickCount < 10) {
            if (this.getVehicle() instanceof RowboatEntity rowboatEntity) {
                if (rowboatEntity.getPilotVehiclePartAsEntity() == this) {
                    compartmentRotation = 180;
                }
            }
        }

        // Try not to be empty
        if (this.getPassengers().isEmpty()) {
            boolean shouldAddCleatInstead = false;

            if(this.getVehicle() instanceof FirmacivBoatEntity vehicle){

                if(this.getVehicle().getPassengers().size() == ((FirmacivBoatEntity) this.getVehicle()).getPassengerNumber()){
                    for(int i : vehicle.getCleats()){
                        if(this.getVehicle().getPassengers().get(i).is(this)){

                            shouldAddCleatInstead = true;

                            final VehicleCleatEntity cleat = FirmacivEntities.VEHICLE_CLEAT_ENTITY.get()
                                    .create(this.level());
                            cleat.setPos(this.getX(), this.getY(), this.getZ());
                            if (!cleat.startRiding(this)) {
                                Firmaciv.LOGGER.error("New Cleat: {} unable to ride Vehicle Part: {}", cleat, this);
                            }
                            this.level().addFreshEntity(cleat);
                            break;


                        }
                    }
                }
            }


            if(!shouldAddCleatInstead){
                final EmptyCompartmentEntity newCompartment = FirmacivEntities.EMPTY_COMPARTMENT_ENTITY.get()
                        .create(this.level());

                // Can maybe just assert not null? Doesn't really matter I guess
                if (newCompartment != null) {
                    newCompartment.setYRot(this.getYRot() + compartmentRotation);
                    newCompartment.setPos(this.getX(), this.getY(), this.getZ());
                    if (!newCompartment.startRiding(this)) {
                        Firmaciv.LOGGER.error("New Compartment: {} unable to ride Vehicle Part: {}", newCompartment, this);
                    }
                    this.level().addFreshEntity(newCompartment);
                }
            }


        }

        // Try remove if I'm not a passenger of something
        if (!this.isPassenger()) {
            if (selfDestructTicks++ >= 5) {
                this.ejectPassengers();
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void positionRider(final Entity passenger, final Entity.MoveFunction moveFunction) {
        if (!(this.getVehicle() instanceof FirmacivBoatEntity firmacivBoatEntity)) return;
        final double riderOffset = ((this.isRemoved() ? 0.01 : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
        final Vec3 vec3 = (new Vec3(0, 0, 0)).yRot((float) (-this.getYRot() * Math.PI / 180 - Math.PI / 2));
        moveFunction.accept(passenger, this.getX() + vec3.x, this.getY() + riderOffset, this.getZ() + vec3.z);
        passenger.setPos(this.getX() + vec3.x, this.getY() + riderOffset, this.getZ() + vec3.z);
        if (passenger instanceof CompartmentEntity || passenger instanceof VehicleCleatEntity) {
            passenger.setYRot(passenger.getYRot() + firmacivBoatEntity.getDeltaRotation());
            if (Math.abs(passenger.getYRot() - firmacivBoatEntity.getYRot() + compartmentRotation) > 1) {
                if (tickCount < 10 || this.getVehicle().getControllingPassenger() == null) {
                    this.setYRot(this.getVehicle().getYRot());
                    passenger.setYRot(this.getYRot() + compartmentRotation);
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