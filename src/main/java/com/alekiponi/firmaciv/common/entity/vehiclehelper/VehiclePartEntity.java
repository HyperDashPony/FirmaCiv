package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.FirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
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

        if (!this.isPassenger()) {
            selfDestructTicks++;
            if (selfDestructTicks++ >= 5) {
                this.ejectPassengers();
                this.remove(RemovalReason.DISCARDED);
            }
        }

        if(!(this.getVehicle() instanceof FirmacivBoatEntity))
        {
            return;
        }
        FirmacivBoatEntity vehicle = (FirmacivBoatEntity)this.getVehicle();


        /*
        if (tickCount < 10) {
            if (this.getVehicle() instanceof RowboatEntity rowboatEntity) {
                if (rowboatEntity.getPilotVehiclePartAsEntity() == this) {
                    compartmentRotation = 180;
                }
            }
        }

         */

        if (tickCount < 30) {
            if(vehicle.getPassengers().size() == vehicle.getPassengerNumber()){
                for(int[] i : vehicle.getCompartmentRotationsArray()){
                    if(vehicle.getPassengers().get(i[0]) == this){
                        this.compartmentRotation = i[1];
                    }
                }
            }

        }

        // Try not to be empty
        if (this.getPassengers().isEmpty()) {
            boolean shouldAddCleatInstead = false;

            if(vehicle.getPassengers().size() == ((FirmacivBoatEntity) this.getVehicle()).getPassengerNumber()){
                for(int i : vehicle.getCleats()){
                    if(vehicle.getPassengers().get(i).is(this)){

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

    }

    @Override
    protected void positionRider(final Entity passenger, final Entity.MoveFunction moveFunction) {

        if (!(this.getVehicle() instanceof FirmacivBoatEntity firmacivBoatEntity)) return;

        FirmacivBoatEntity thisVehicle = (FirmacivBoatEntity) this.getVehicle();

        final double riderOffset = ((this.isRemoved() ? 0.01 : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
        final Vec3 vec3 = (new Vec3(0, 0, 0)).yRot((float) (-this.getYRot() * Math.PI / 180 - Math.PI / 2));
        moveFunction.accept(passenger, this.getX() + vec3.x, this.getY() + riderOffset, this.getZ() + vec3.z);
        passenger.setPos(this.getX() + vec3.x, this.getY() + riderOffset, this.getZ() + vec3.z);

        if ((passenger instanceof CompartmentEntity || passenger instanceof VehicleCleatEntity)) {
            if(thisVehicle.isBeingTowed()){
            } else {
                passenger.setYRot(passenger.getYRot() + firmacivBoatEntity.getDeltaRotation());
                if (Math.abs(passenger.getYRot() - firmacivBoatEntity.getYRot() + compartmentRotation) > 1) {
                    if (tickCount < 10 || thisVehicle.getControllingPassenger() == null) {
                        this.setYRot(thisVehicle.getYRot());
                        passenger.setYRot(thisVehicle.getYRot() + compartmentRotation);
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