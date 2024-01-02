package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractVehicle;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class VehiclePartEntity extends AbstractInvisibleHelper {

    protected static final EntityDataAccessor<Float> DATA_ID_COMPARTMENT_ROTATION = SynchedEntityData.defineId(
            VehiclePartEntity.class, EntityDataSerializers.FLOAT);
    private int selfDestructTicks = 0;

    public VehiclePartEntity(final EntityType<?> entityType, final Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {

        // Most of vehiclepart's ticking actions should only be on the server
        if(!this.level().isClientSide()) {
            if (!this.isPassenger()) {
                selfDestructTicks++;
                if (selfDestructTicks++ >= 5) {
                    this.ejectPassengers();
                    this.remove(RemovalReason.DISCARDED);
                }
            }

            if (!(this.getVehicle() instanceof final AbstractVehicle vehicle)) {
                return;
            }

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
                boolean shouldAddColliderInstead = false;
                boolean shouldAddSailSwitchInstead = false;
                boolean shouldAddWindlassInstead = false;
                // Try adding a cleat
                if (vehicle.getPassengers().size() == vehicle.getMaxPassengers()) {
                    for (int i : vehicle.getCleatIndices()) {
                        if (vehicle.getPassengers().get(i).is(this) && !vehicle.getPassengers().get(i).isVehicle()) {

                            shouldAddCleatInstead = true;

                            final VehicleCleatEntity cleat = FirmacivEntities.VEHICLE_CLEAT_ENTITY.get()
                                    .create(this.level());
                            assert cleat != null;
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

                //Try adding a collider
                if(!shouldAddCleatInstead){
                    if (vehicle.getPassengers().size() == vehicle.getMaxPassengers()) {
                        for (int i : vehicle.getColliderIndices()) {
                            if (vehicle.getPassengers().get(i).is(this) && !vehicle.getPassengers().get(i).isVehicle()) {

                                shouldAddColliderInstead = true;

                                final VehicleCollisionEntity collider = FirmacivEntities.VEHICLE_COLLISION_ENTITY.get()
                                        .create(this.level());
                                assert collider != null;
                                collider.setPos(this.getX(), this.getY(), this.getZ());
                                if (!collider.startRiding(this)) {
                                    Firmaciv.LOGGER.error("New Collider: {} unable to ride Vehicle Part: {}", collider, this);
                                }
                                this.level().addFreshEntity(collider);
                                break;

                            }
                        }
                    }
                }

                // Try adding a switch
                if(!shouldAddCleatInstead && !shouldAddColliderInstead && this.getVehicle() instanceof AbstractFirmacivBoatEntity boatEntity){
                    if (vehicle.getPassengers().size() == vehicle.getMaxPassengers()) {
                        for (int i : boatEntity.getSailSwitchIndices()) {
                            if (vehicle.getPassengers().get(i).is(this) && !vehicle.getPassengers().get(i).isVehicle()) {

                                shouldAddSailSwitchInstead = true;

                                final AbstractSwitchEntity switchEntity = FirmacivEntities.SAIL_SWITCH_ENTITY.get()
                                        .create(this.level());
                                assert switchEntity != null;
                                switchEntity.setPos(this.getX(), this.getY(), this.getZ());
                                if (!switchEntity.startRiding(this)) {
                                    Firmaciv.LOGGER.error("New Switch: {} unable to ride Vehicle Part: {}", switchEntity, this);
                                }
                                this.level().addFreshEntity(switchEntity);
                                break;

                            }
                        }
                    }
                }

                // Try adding a Windlass
                if(!shouldAddCleatInstead && !shouldAddColliderInstead && !shouldAddSailSwitchInstead && this.getVehicle() instanceof AbstractFirmacivBoatEntity boatEntity){
                    if (boatEntity.getPassengers().size() == boatEntity.getMaxPassengers()) {
                        for (int i : boatEntity.getWindlassIndices()) {
                            if (boatEntity.getPassengers().get(i).is(this) && !boatEntity.getPassengers().get(i).isVehicle()) {

                                shouldAddWindlassInstead = true;

                                final WindlassSwitchEntity windlass = FirmacivEntities.WINDLASS_SWITCH_ENTITY.get()
                                        .create(this.level());
                                assert windlass != null;
                                windlass.setPos(this.getX(), this.getY(), this.getZ());
                                if (!windlass.startRiding(this)) {
                                    Firmaciv.LOGGER.error("New Windlass: {} unable to ride Vehicle Part: {}", windlass, this);
                                }
                                this.level().addFreshEntity(windlass);
                                break;

                            }
                        }
                    }
                }

                // Try adding a compartment
                if (vehicle.getPassengers().size() == vehicle.getMaxPassengers()) {
                    if (!shouldAddCleatInstead && !shouldAddColliderInstead && !shouldAddSailSwitchInstead && !shouldAddWindlassInstead) {
                        final EmptyCompartmentEntity newCompartment = FirmacivEntities.EMPTY_COMPARTMENT_ENTITY.get()
                                .create(this.level());

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

        }

        super.tick();
    }

    @Override
    protected void positionRider(final net.minecraft.world.entity.Entity passenger, final net.minecraft.world.entity.Entity.MoveFunction moveFunction) {

        if (!(this.getVehicle() instanceof AbstractVehicle abstractVehicle)) return;

        final double localY = ((this.isRemoved() ? 0.01 : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());

        moveFunction.accept(passenger, this.getX(), this.getY() + localY, this.getZ());
        passenger.setPos(this.getX(), this.getY() + localY, this.getZ());

        if ((passenger instanceof AbstractCompartmentEntity || passenger instanceof VehicleCleatEntity)) {
            this.setYRot(abstractVehicle.getYRot());
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