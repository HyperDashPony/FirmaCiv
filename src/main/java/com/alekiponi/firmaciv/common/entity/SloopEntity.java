package com.alekiponi.firmaciv.common.entity;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.EmptyCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehiclePartEntity;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SloopEntity extends FirmacivBoatEntity {


    public final int PASSENGER_NUMBER = 14;

    public final int[] CLEATS = {};

    public final int[][] COMPARTMENT_ROTATIONS = {{7, 85}, {8, 85}, {9, 85}, {10, -85}, {11, -85}, {12, -85}};

    public final int[] CAN_ADD_ONLY_BLOCKS = {1, 2, 3, 4, 5, 6};

    protected final float PASSENGER_SIZE_LIMIT = 1.4F;
    protected float sailRotation;
    protected int sailState;
    protected int sailAnimationTicks;

    protected double windAngle;

    protected double windSpeed;

    public SloopEntity(EntityType<? extends FirmacivBoatEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public float getPassengerSizeLimit() {
        return PASSENGER_SIZE_LIMIT;
    }

    @Override
    public int getPassengerNumber() {
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

    @Override
    public AABB getBoundingBoxForCulling() {
        float bbRadius = 10f;
        Vec3 startingPoint = new Vec3(this.getX() - bbRadius, this.getY() - bbRadius, this.getZ() - bbRadius);
        Vec3 endingPoint = new Vec3(this.getX() + bbRadius, this.getY() + bbRadius, this.getZ() + bbRadius);
        return new AABB(startingPoint, endingPoint);
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
            final Vec3 vec3 = this.positionVehiclePartEntityLocally(localX, localY, localZ);
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
        this.sailBoat();
        sailAnimationTicks = this.tickCount;
        if(tickCount % 40 == 0){
            Vec2 windVector = Climate.getWindVector(this.level(), this.blockPosition());
            double direction = Math.round(Math.toDegrees(Math.atan(windVector.x / windVector.y)));
            double speed = Math.abs(Math.round(windVector.length() * 320));
            this.windSpeed = speed;
            this.windAngle = Mth.wrapDegrees(direction);
        }
    }

    // method to get sailing controls

    // variable for sail angle

    // method for getting wind stuff?

    public float getSailRotation() {
        return Mth.wrapDegrees(sailRotation);
    }

    public float getSailWorldRotation() {
        return Mth.wrapDegrees(sailRotation + Mth.wrapDegrees(this.getYRot()));
    }

    public int getSailAnimationTicks(){ return sailAnimationTicks;};

    public float[] getWindAngleAndSpeed() {
        return new float[]{(float) windAngle, (float) windSpeed};
    }


    public int getSailState() {
        return sailState;
    }

    public void setSailState(int state) {
        sailState = state;
    }

    @Nullable
    public Entity getSailingVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getPassengerNumber()) {
            return this.getPassengers().get(13);
        }
        return null;
    }

    @Nullable
    public EmptyCompartmentEntity getSailingCompartment() {
        final Entity vehiclePart = this.getSailingVehiclePartAsEntity();

        if (!(vehiclePart instanceof VehiclePartEntity) || !vehiclePart.isVehicle()) return null;

        if (!(vehiclePart.getFirstPassenger() instanceof EmptyCompartmentEntity emptyCompartmentEntity))
            return null;

        if (!emptyCompartmentEntity.isVehicle() || !(emptyCompartmentEntity.getFirstPassenger() instanceof LocalPlayer))
            return null;

        return emptyCompartmentEntity;
    }

    protected void sailBoat() {
        if (this.isVehicle()) {
            if (getSailingCompartment() != null) {
                boolean inputUp = this.getSailingCompartment().getInputUp();
                boolean inputDown = this.getSailingCompartment().getInputDown();
                boolean inputLeft = this.getSailingCompartment().getInputLeft();
                boolean inputRight = this.getSailingCompartment().getInputRight();
                if (inputLeft) {
                    if (sailRotation > -45) {
                        --this.sailRotation;
                    }
                }

                if (inputRight) {
                    if (sailRotation < 45) {
                        ++this.sailRotation;
                    }
                }

                if (inputUp) {
                    if (sailState < 3) {
                        sailState++;
                    }
                }

                if (inputDown) {
                    if (sailState > 0) {
                        sailState--;
                    }
                }
            }
        }
    }

}
