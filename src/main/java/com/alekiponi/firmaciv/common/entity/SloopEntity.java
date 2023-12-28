package com.alekiponi.firmaciv.common.entity;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.EmptyCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehiclePartEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SloopEntity extends AbstractFirmacivBoatEntity {

    public final int PASSENGER_NUMBER = 14;

    public final int[] CLEATS = {};

    protected static final EntityDataAccessor<Float> DATA_ID_MAIN_BOOM_ROTATION = SynchedEntityData.defineId(
            SloopEntity.class, EntityDataSerializers.FLOAT);

    protected static final EntityDataAccessor<Float> DATA_ID_RUDDER_ROTATION = SynchedEntityData.defineId(
            SloopEntity.class, EntityDataSerializers.FLOAT);

    protected static final EntityDataAccessor<Boolean> DATA_ID_MAINSAIL_ACTIVE = SynchedEntityData.defineId(
            SloopEntity.class, EntityDataSerializers.BOOLEAN);

    public final int[][] COMPARTMENT_ROTATIONS = {{7, 85}, {8, 85}, {9, 85}, {10, -85}, {11, -85}, {12, -85}};

    public final int[] CAN_ADD_ONLY_BLOCKS = {1, 2, 3, 4, 5, 6};
    protected final float PASSENGER_SIZE_LIMIT = 1.4F;

    public SloopEntity(EntityType<? extends AbstractFirmacivBoatEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public float getPassengerSizeLimit() {
        return PASSENGER_SIZE_LIMIT;
    }

    @Override
    public int getMaxPassengers() {
        return this.PASSENGER_NUMBER;
    }


    @Override
    public int[] getCleats() {
        return this.CLEATS;
    }

    @Override
    public int[] getCanAddOnlyBlocks() {
        return CAN_ADD_ONLY_BLOCKS;
    }

    @Override
    public int getCompartmentRotation(int i) {
        return COMPARTMENT_ROTATIONS[i][0];
    }

    @Override
    public int[][] getCompartmentRotationsArray() {
        return COMPARTMENT_ROTATIONS;
    }

    // sailing stuff
    @Override
    protected void positionRider(final Entity passenger, final Entity.MoveFunction moveFunction) {
        if (this.hasPassenger(passenger)) {
            float localX = 0.0F;
            float localZ = 0.0F;
            float localY = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
            if (this.getPassengers().size() > 1) {
                switch (this.getPassengers().indexOf(passenger)) {
                    case 0 -> {
                        // aft pilot / tiller seat
                        localZ = 0.6f;
                        localX = -2.3f;
                        localY += 0.6f;
                    }
                    case 1 -> {
                        // hold lower port
                        localZ = -0.35f;
                        localX = -0.4f;
                        localY += -0.1f;
                    }
                    case 2 -> {
                        // hold lower starboard
                        localZ = 0.35f;
                        localX = -0.4f;
                        localY += -0.1f;
                    }
                    case 3 -> {
                        // hold middle port
                        localZ = -0.35f;
                        localX = 0.475f;
                        localY += -0.1f;
                    }
                    case 4 -> {
                        //hold middle starboard
                        localZ = 0.35f;
                        localX = 0.475f;
                        localY += -0.1f;
                    }
                    case 5 -> {
                        //hold upper port
                        localZ = -0.35f;
                        localX = 1.35f;
                        localY += -0.1f;
                    }
                    case 6 -> {
                        //hold upper starboard
                        localZ = 0.35f;
                        localX = 1.35f;
                        localY += -0.1f;
                    }
                    case 7 -> {
                        //port side fore
                        localX = 0.3f;
                        localZ = -1.4f;
                        localY += 0.6f;
                    }
                    case 8 -> {
                        //port side mid
                        localX = -0.5f;
                        localZ = -1.33f;
                        localY += 0.6f;
                    }
                    case 9 -> {
                        //port side aft
                        localX = -1.3f;
                        localZ = -1.26f;
                        localY += 0.6f;

                    }
                    case 10 -> {
                        //starboard side fore
                        localX = 0.3f;
                        localZ = 1.4f;
                        localY += 0.6f;
                    }
                    case 11 -> {
                        //starboard side mid
                        localX = -0.5f;
                        localZ = 1.33f;
                        localY += 0.6f;
                    }
                    case 12 -> {
                        //starboard side aft
                        localX = -1.3f;
                        localZ = 1.26f;
                        localY += 0.6f;
                    }
                    case 13 -> {
                        //sailing station
                        localZ = -0.6f;
                        localX = -2.3f;
                        localY += 0.6f;
                    }
                }
            }
            final Vec3 vec3 = this.positionLocally(localX, localY, localZ);
            moveFunction.accept(passenger, this.getX() + vec3.x, this.getY() + (double) localY, this.getZ() + vec3.z);
            passenger.setPos(this.getX() + vec3.x, this.getY() + (double) localY, this.getZ() + vec3.z);
            if (!this.level().isClientSide() && passenger instanceof VehiclePartEntity) {
                passenger.setYRot(this.getYRot());
            } else if (!(passenger instanceof VehiclePartEntity)) {
                super.positionRider(passenger, moveFunction);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.tickSailBoat();
        this.tickDestroyPlants();
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_MAIN_BOOM_ROTATION, 0f);
        this.entityData.define(DATA_ID_RUDDER_ROTATION, 0f);
        this.entityData.define(DATA_ID_MAINSAIL_ACTIVE, false);
    }


    public float getSailWorldRotation() {
        return Mth.wrapDegrees(getMainBoomRotation() + Mth.wrapDegrees(this.getYRot()));
    }

    @Nullable
    public Entity getSailingVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getMaxPassengers()) {
            return this.getPassengers().get(13);
        }
        return null;
    }

    @Nullable
    public EmptyCompartmentEntity getSailingCompartment() {
        final Entity vehiclePart = this.getSailingVehiclePartAsEntity();

        if (!(vehiclePart instanceof VehiclePartEntity) || !vehiclePart.isVehicle()) {
            return null;
        }

        if (!(vehiclePart.getFirstPassenger() instanceof EmptyCompartmentEntity emptyCompartmentEntity)) {
            return null;
        }
        return emptyCompartmentEntity;
    }

    @Override
    protected void tickPaddlingEffects() {
    }
    @Override
    protected void tickControlBoat() {
        if (getControllingCompartment() != null) {
            boolean inputUp = this.getControllingCompartment().getInputUp();
            boolean inputDown = this.getControllingCompartment().getInputDown();
            boolean inputLeft = this.getControllingCompartment().getInputLeft();
            boolean inputRight = this.getControllingCompartment().getInputRight();
            if (inputLeft) {
                if(this.getRudderRotation() < 45){
                    this.setRudderRotation(this.getRudderRotation() + 1);
                }
            }

            if (inputRight) {
                if(this.getRudderRotation() > -45){
                    this.setRudderRotation(this.getRudderRotation() - 1);
                }
            }

            if(!inputRight && !inputLeft){
                if(this.getRudderRotation() > 0){
                    this.setRudderRotation(this.getRudderRotation()-1);
                }
                if(this.getRudderRotation() < 0){
                    this.setRudderRotation(this.getRudderRotation()+1);
                }
                if(Math.abs(this.getRudderRotation()) < 1){
                    this.setRudderRotation(0f);
                }
            }

        }
    }

    protected void tickSailBoat() {
        if (getSailingCompartment() != null) {
            boolean inputUp = this.getSailingCompartment().getInputUp();
            boolean inputDown = this.getSailingCompartment().getInputDown();
            boolean inputLeft = this.getSailingCompartment().getInputLeft();
            boolean inputRight = this.getSailingCompartment().getInputRight();
            if (inputLeft) {
                if (this.getMainBoomRotation() < 45) {
                    this.setMainBoomRotation(this.getMainBoomRotation()+1);
                }
            }

            if (inputRight) {
                if (this.getMainBoomRotation() > -45) {
                    this.setMainBoomRotation(this.getMainBoomRotation()-1);
                }
            }


        }
    }

    protected void tickWindInput() {
        if (this.status != Boat.Status.ON_LAND) {
            Vec3 windMovement = new Vec3(this.getWindVector().x, 0, this.getWindVector().y).normalize();
            double windFunction = Mth.clamp(this.getWindVector().length(), 0.0001, 0.010);
            windMovement = windMovement.multiply(windFunction, windFunction, windFunction);

            this.setDeltaMovement(this.getDeltaMovement().add(windMovement));
        }
    }

    public void setMainBoomRotation(float rotation){
        this.entityData.set(DATA_ID_MAIN_BOOM_ROTATION, Mth.clamp(rotation, -45, 45));
    }

    public float getMainBoomRotation(){
        return this.entityData.get(DATA_ID_MAIN_BOOM_ROTATION);
    }

    public void setMainsailActive(boolean mainsail){
        this.entityData.set(DATA_ID_MAINSAIL_ACTIVE, mainsail);
    }

    public boolean getMainsailActive(){
        return this.entityData.get(DATA_ID_MAINSAIL_ACTIVE);
    }

    public void setRudderRotation(float rotation){
        this.entityData.set(DATA_ID_RUDDER_ROTATION, Mth.clamp(rotation, -45, 45));
    }

    public float getRudderRotation(){
        return this.entityData.get(DATA_ID_RUDDER_ROTATION);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setMainBoomRotation(pCompound.getFloat("mainBoomRotation"));
        this.setRudderRotation(pCompound.getFloat("rudderRotation"));
        this.setMainsailActive(pCompound.getBoolean("mainsailActive"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("mainBoomRotation", this.getMainBoomRotation());
        pCompound.putFloat("rudderRotation", this.getRudderRotation());
        pCompound.putBoolean("mainsailActive", this.getMainsailActive());
    }
}
