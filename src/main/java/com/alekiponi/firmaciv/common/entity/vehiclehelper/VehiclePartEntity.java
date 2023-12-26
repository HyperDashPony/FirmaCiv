package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.SloopEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VehiclePartEntity extends Entity {

    protected static final EntityDataAccessor<Float> DATA_ID_COMPARTMENT_ROTATION = SynchedEntityData.defineId(
            VehiclePartEntity.class, EntityDataSerializers.FLOAT);
    private int selfDestructTicks = 0;

    public VehiclePartEntity(final EntityType<?> entityType, final Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {

        //serverside stuff
        if(!this.level().isClientSide()) {
            if (!this.isPassenger()) {
                selfDestructTicks++;
                if (selfDestructTicks++ >= 5) {
                    this.ejectPassengers();
                    this.remove(RemovalReason.DISCARDED);
                }
            }

            if (!(this.getVehicle() instanceof AbstractFirmacivBoatEntity)) {
                return;
            }
            final AbstractFirmacivBoatEntity vehicle = (AbstractFirmacivBoatEntity) this.getVehicle();

            if (tickCount < 30) {
                if (vehicle.getPassengers().size() == vehicle.getMaxPassengers()) {
                    for (int[] i : vehicle.getCompartmentRotationsArray()) {
                        if (vehicle.getPassengers().get(i[0]) == this) {
                            this.setCompartmentRotation(i[1]);
                        }
                    }
                }

            }

            // Try not to be empty
            if (this.getPassengers().isEmpty()) {
                boolean shouldAddCleatInstead = false;

                if (vehicle.getPassengers().size() == ((AbstractFirmacivBoatEntity) this.getVehicle()).getMaxPassengers()) {
                    for (int i : vehicle.getCleats()) {
                        if (vehicle.getPassengers().get(i).is(this)) {

                            shouldAddCleatInstead = true;

                            final VehicleCleatEntity cleat = FirmacivEntities.VEHICLE_CLEAT_ENTITY.get()
                                    .create(this.level());
                            cleat.setPos(this.getX(), this.getY(), this.getZ());
                            cleat.setYRot(this.getVehicle().getYRot());
                            if (!cleat.startRiding(this)) {
                                Firmaciv.LOGGER.error("New Cleat: {} unable to ride Vehicle Part: {}", cleat, this);
                            }
                            this.level().addFreshEntity(cleat);
                            break;


                        }
                    }
                }


                if (!shouldAddCleatInstead) {
                    final EmptyCompartmentEntity newCompartment = FirmacivEntities.EMPTY_COMPARTMENT_ENTITY.get()
                            .create(this.level());

                    // Can maybe just assert not null? Doesn't really matter I guess
                    if (newCompartment != null) {
                        newCompartment.setYRot(this.getVehicle().getYRot() + this.getCompartmentRotation());
                        newCompartment.setPos(this.getX(), this.getY(), this.getZ());
                        if (!newCompartment.startRiding(this)) {
                            Firmaciv.LOGGER.error("New Compartment: {} unable to ride Vehicle Part: {}", newCompartment,
                                    this);
                        }
                        this.level().addFreshEntity(newCompartment);
                    }
                }


            }

        }

        super.tick();
    }

    @Override
    protected void positionRider(final Entity passenger, final Entity.MoveFunction moveFunction) {

        if (!(this.getVehicle() instanceof AbstractFirmacivBoatEntity firmacivBoatEntity)) return;

        final double localY = ((this.isRemoved() ? 0.01 : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());

        moveFunction.accept(passenger, this.getX(), this.getY() + localY, this.getZ());
        passenger.setPos(this.getX(), this.getY() + localY, this.getZ());

        if ((passenger instanceof AbstractCompartmentEntity || passenger instanceof VehicleCleatEntity)) {
            this.setYRot(firmacivBoatEntity.getYRot());
            passenger.setYRot(this.getYRot() + this.getCompartmentRotation());
        }
    }



    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_COMPARTMENT_ROTATION, 0f);
    }

    public void setCompartmentRotation(float rotation){
        this.entityData.set(DATA_ID_COMPARTMENT_ROTATION, rotation);
    }

    public float getCompartmentRotation(){
        return this.entityData.get(DATA_ID_COMPARTMENT_ROTATION);
    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {
        this.setCompartmentRotation(compoundTag.getFloat("compartmentRotation"));
    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {
        compoundTag.putFloat("compartmentRotation", this.getCompartmentRotation());
    }
}