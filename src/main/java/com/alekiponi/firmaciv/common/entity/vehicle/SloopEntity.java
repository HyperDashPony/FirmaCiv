package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.*;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import com.alekiponi.firmaciv.util.FirmacivHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SloopEntity extends AbstractFirmacivBoatEntity {

    public final int PASSENGER_NUMBER = 25;

    public final int[] CLEATS = {18, 19, 20, 21};
    public final int[] COLLIDERS = {14, 15, 16};
    public final int[] SAIL_SWITCHES = {17,24};

    public final int[] WINDLASSES = {22};

    public final int[] MASTS = {23};

    protected static final EntityDataAccessor<Float> DATA_ID_MAIN_BOOM_ROTATION = SynchedEntityData.defineId(
            SloopEntity.class, EntityDataSerializers.FLOAT);

    protected static final EntityDataAccessor<Float> DATA_ID_MAINSHEET_LENGTH = SynchedEntityData.defineId(
            SloopEntity.class, EntityDataSerializers.FLOAT);

    protected static final EntityDataAccessor<Float> DATA_ID_RUDDER_ROTATION = SynchedEntityData.defineId(
            SloopEntity.class, EntityDataSerializers.FLOAT);

    protected static final EntityDataAccessor<Boolean> DATA_ID_MAINSAIL_ACTIVE = SynchedEntityData.defineId(
            SloopEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> DATA_ID_JIBSAIL_ACTIVE = SynchedEntityData.defineId(
            SloopEntity.class, EntityDataSerializers.BOOLEAN);

    public final int[][] COMPARTMENT_ROTATIONS = {{7, 85}, {8, 85}, {9, 85}, {10, -85}, {11, -85}, {12, -85}};

    public final int[] CAN_ADD_ONLY_BLOCKS = {1, 2, 3, 4, 5, 6};

    protected final float PASSENGER_SIZE_LIMIT = 1.4F;

    protected final float DAMAGE_THRESHOLD = 512.0f;
    protected final float DAMAGE_RECOVERY = 2.0f;

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
    public int[] getCleatIndices() {
        return this.CLEATS;
    }

    @Override
    public int[] getSailSwitchIndices() {
        return SAIL_SWITCHES;
    }

    @Override
    public int[] getMastIndices() { return MASTS; }

    @Override
    public int[] getColliderIndices() {
        return COLLIDERS;
    }

    @Override
    public int[] getCanAddOnlyBlocksIndices() {
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
    protected Vec3 positionRiderByIndex(int index) {
        float localX = 0.0F;
        float localZ = 0.0F;
        float localY = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()));
        switch (index) {
            case 0 -> {
                // aft pilot / tiller seat
                localZ = 0.6f;
                localX = -2.3f;
                localY += 0.625f;
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
                localY += 0.625f;
            }
            case 8 -> {
                //port side mid
                localX = -0.5f;
                localZ = -1.33f;
                localY += 0.625f;
            }
            case 9 -> {
                //port side aft
                localX = -1.3f;
                localZ = -1.26f;
                localY += 0.625f;

            }
            case 10 -> {
                //starboard side fore
                localX = 0.3f;
                localZ = 1.4f;
                localY += 0.625f;
            }
            case 11 -> {
                //starboard side mid
                localX = -0.5f;
                localZ = 1.33f;
                localY += 0.625f;
            }
            case 12 -> {
                //starboard side aft
                localX = -1.3f;
                localZ = 1.26f;
                localY += 0.625f;
            }
            case 13 -> {
                //sailing station
                localZ = -0.6f;
                localX = -2.3f;
                localY += 0.625f;
            }
            case 14 -> {
                //collider 1
                localZ = 0.6f;
                localX = -2.0f;
                localY += -0.00f;
            }
            case 15 -> {
                //collider 2
                localZ = -0.6f;
                localX = -2.0f;
                localY += -0.00f;
            }
            case 16 -> {
                //collider 3
                localZ = 0f;
                localX = 2.0f;
                localY += -0.00f;
            }
            case 17 -> {
                //mainsail switch
                localZ = 0f;
                localX = 2f;
                localY += 2.625f;
            }
            case 18 -> {
                //cleat port fore
                localZ = -1.8f;
                localX = 1.2f;
                localY += 1.0f;
            }
            case 19 -> {
                //cleat starboard fore
                localZ = 1.8f;
                localX = 1.2f;
                localY += 1.0f;
            }
            case 20 -> {
                //cleat port aft
                localZ = -1.8f;
                localX = -2.25f;
                localY += 1.2f;
            }
            case 21 -> {
                //cleat starboard aft
                localZ = 1.8f;
                localX = -2.25f;
                localY += 1.2f;
            }
            case 22 -> {
                //windlass
                localZ = -1.2f;
                localX = 2.7f;
                localY += 1.0f;
            }
            case 23 -> {
                //mast
                localZ = 0f;
                localX = 2.1f;
                localY += 2.9f;
            }
            case 24 -> {
                // jibsail switch
                localZ = 0f;
                localX = 3.1f;
                localY += 1.0f;
            }
        }
        return new Vec3(localX, localY, localZ);
    }

    @Override
    protected float getDamageThreshold() {
        return DAMAGE_THRESHOLD;
    }

    @Override
    protected float getDamageRecovery() {
        return DAMAGE_RECOVERY;
    }

    @Override
    public void tick() {

        if (this.status == Status.IN_WATER || this.status == Status.IN_AIR) {
            float rotationImpact = 0;

            float windDifference = Mth.degreesDifference(getMainsailWindAngleAndForce()[0], Mth.wrapDegrees(this.getYRot()));

            if (windDifference > 4) {
                rotationImpact = 1f;
            } else if (windDifference < -4) {
                rotationImpact = -1f;
            }

            rotationImpact = (float) (rotationImpact * this.getDeltaMovement().length());

            this.setDeltaRotation(this.getDeltaRotation() + rotationImpact);

            float boomWindDifference = Mth.degreesDifference(this.getLocalWindAngleAndSpeed()[0], Mth.wrapDegrees(this.getSailWorldRotation()));

            float sheet = this.getMainsheetLength();
            float boom = this.getMainBoomRotation();

            if (sheet > Math.abs(boom)) {
                if (boom < 0) {
                    boom--;
                } else if (boom != 0) {
                    boom++;
                }
            }

            if (boomWindDifference < -171) {
                boomWindDifference = Mth.wrapDegrees(boomWindDifference - 180);
            }
            if (boomWindDifference > 9) {
                boom += 2f;
            } else if (boomWindDifference < -9) {
                boom -= 2f;
            }

            this.setMainBoomRotation(boom);
        }

        this.tickDestroyPlants();
        if (this.status == Status.IN_WATER) {
            this.setDeltaRotation((float) (-1 * this.getRudderRotation() * 0.25f * this.getDeltaMovement().length()));
            this.setDeltaRotation(Mth.clamp(this.getDeltaRotation(),-1,1));
        }

        super.tick();
        int ind = 0;
        for (SailSwitchEntity switchEntity : this.getSailSwitches()) {
            if(ind == 0){
                this.setMainsailActive(switchEntity.getSwitched());
            }
            if(ind == 1){
                this.setJibsailActive(switchEntity.getSwitched());
            }
            ind++;
        }
        if(!this.getMainsailActive() && !this.getJibsailActive()){
            this.setMainsheetLength(0);
        }
    }

    @Override
    protected void tickCleatInput() {
        if (this.getMainsailActive()) {
            return;
        }
        int count = 0;
        ArrayList<VehicleCleatEntity> cleats = this.getCleats();
        ArrayList<VehicleCleatEntity> leashedCleats = new ArrayList<VehicleCleatEntity>();
        for (VehicleCleatEntity cleat : cleats) {
            if (cleat.isLeashed()) {
                leashedCleats.add(cleat);
                count++;
            }
        }
        if (count == 2) {
            VehicleCleatEntity cleat1 = leashedCleats.get(0);
            VehicleCleatEntity cleat2 = leashedCleats.get(1);
            net.minecraft.world.entity.Entity leashHolder1 = cleat1.getLeashHolder();
            net.minecraft.world.entity.Entity leashHolder2 = cleat2.getLeashHolder();
            if (leashHolder1 != null && leashHolder2 != null) {
                if (leashHolder1.is(leashHolder2)) {
                    count = 1;
                } else {
                    double d0 = leashHolder1.getPosition(0).x - leashHolder2.getX();
                    double d2 = leashHolder1.getPosition(0).z - leashHolder2.getZ();

                    float finalRotation = Mth.wrapDegrees(
                            (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F);

                    float approach = Mth.approachDegrees(this.getYRot(), finalRotation, 0.25f);
                    if (Mth.degreesDifferenceAbs(this.getYRot(), finalRotation) < 1.0) {
                        this.setDeltaRotation(0);
                        this.setYRot(this.getYRot());
                    } else {
                        this.setDeltaRotation(-1 * (this.getYRot() - approach));
                    }

                    Vec3 vectorToVehicle1 = leashHolder1.getPosition(0).vectorTo(cleat1.getPosition(0)).normalize();

                    Vec3 vectorToVehicle2 = leashHolder2.getPosition(0).vectorTo(cleat2.getPosition(0)).normalize();

                    Vec3 movementVector1 = new Vec3(vectorToVehicle1.x * -0.002f, this.getDeltaMovement().y,
                            vectorToVehicle1.z * -0.005f);

                    Vec3 movementVector2 = new Vec3(vectorToVehicle2.x * -0.002f, this.getDeltaMovement().y,
                            vectorToVehicle1.z * -0.005f);

                    if (cleat1.distanceTo(leashHolder1) > 1.5) {
                        this.setDeltaMovement(movementVector1);
                    }
                    if (cleat2.distanceTo(leashHolder2) > 1.5) {
                        this.setDeltaMovement(movementVector2);
                    }

                }
            }

        }
        if (count == 1) {
            VehicleCleatEntity cleat = leashedCleats.get(0);
            net.minecraft.world.entity.Entity leashHolder = cleat.getLeashHolder();
            if (leashHolder != null) {
                if (leashHolder instanceof Player) {
                    if (this.distanceTo(leashHolder) > 4f) {
                        Vec3 vectorToVehicle = leashHolder.getPosition(0).vectorTo(this.getPosition(0)).normalize();
                        Vec3 movementVector = new Vec3(vectorToVehicle.x * -0.01f, this.getDeltaMovement().y,
                                vectorToVehicle.z * -0.01f);
                        double vehicleSize = Mth.clamp(this.getBbWidth(), 1, 100);
                        movementVector = movementVector.multiply(1 / vehicleSize, 0, 1 / vehicleSize);

                        this.setDeltaMovement(movementVector);

                    }
                }
                if (leashHolder instanceof HangingEntity) {
                    Vec3 vectorToVehicle = leashHolder.getPosition(0).vectorTo(this.getPosition(0)).normalize();
                    Vec3 movementVector = new Vec3(vectorToVehicle.x * -0.001f, this.getDeltaMovement().y,
                            vectorToVehicle.z * -0.001f);

                    if (cleat.distanceTo(leashHolder) > 1) {
                        this.setDeltaMovement(movementVector);
                    }

                }
            }
        }
        if (count != 1 && count != 2) {
            for (VehicleCleatEntity cleat : leashedCleats) {
                net.minecraft.world.entity.Entity leashHolder = cleat.getLeashHolder();
                if (leashHolder != null) {
                    Vec3 vectorToVehicle = leashHolder.getPosition(0).vectorTo(this.getPosition(0)).normalize();
                    Vec3 movementVector = new Vec3(vectorToVehicle.x * -0.01f / count, this.getDeltaMovement().y,
                            vectorToVehicle.z * -0.01f / count);

                    if (cleat.distanceTo(leashHolder) > 1) {
                        this.setDeltaMovement(movementVector);
                    }
                }
            }
        }

    }

    @Override
    protected void tickTurnSpeedFactor(){
        // TODO make this cleaner in the inheritance structure
        // shouldn't be used for larger boats...
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_MAIN_BOOM_ROTATION, 0f);
        this.entityData.define(DATA_ID_RUDDER_ROTATION, 0f);
        this.entityData.define(DATA_ID_MAINSAIL_ACTIVE, false);
        this.entityData.define(DATA_ID_JIBSAIL_ACTIVE, false);
        this.entityData.define(DATA_ID_MAINSHEET_LENGTH, 0f);
    }

    @Override
    public int[] getWindlassIndices() {
        return WINDLASSES;
    }

    public float getSailWorldRotation() {
        return Mth.wrapDegrees(getMainBoomRotation() + Mth.wrapDegrees(this.getYRot()));
    }


    @Nullable
    public net.minecraft.world.entity.Entity getSailingVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getMaxPassengers()) {
            return this.getPassengers().get(13);
        }
        return null;
    }

    @Nullable
    public EmptyCompartmentEntity getSailingCompartment() {
        final net.minecraft.world.entity.Entity vehiclePart = this.getSailingVehiclePartAsEntity();

        if (!(vehiclePart instanceof AbstractVehiclePart) || !vehiclePart.isVehicle()) {
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

            float rudder = this.getRudderRotation();
            if (inputLeft) {
                if (rudder < 45) {
                    rudder++;
                }
            }

            if (inputRight) {
                if (rudder > -45) {
                    rudder--;
                }
            }

            if (!inputRight && !inputLeft) {
                if (rudder > 0) {
                    rudder -= 0.8f;
                }
                if (rudder < 0) {
                    rudder += 0.8f;
                }
                if (Math.abs(rudder) < 1) {
                    rudder = 0;
                }
            }
            this.setRudderRotation(rudder);

            tickSailBoat();



            /*
            if (inputUp && !this.getMainsailActive()) {
                this.setMainsailActive(true);
            }

            if (inputDown && this.getMainsailActive()) {
                this.setMainsailActive(false);
            }*/
        }
    }

    @Override
    protected float getPaddleMultiplier() {
        return 0;
    }

    @Override
    protected float[] getPaddleAcceleration() {
        return new float[]{0, 0, 0};
    }

    @Override
    protected float getMomentumSubtractor() {
        return 0.003f;
    }

    protected void tickSailBoat() {
        if (getControllingCompartment() != null) {
            boolean inputUp = this.getControllingCompartment().getInputUp();
            boolean inputDown = this.getControllingCompartment().getInputDown();
            boolean inputLeft = this.getControllingCompartment().getInputLeft();
            boolean inputRight = this.getControllingCompartment().getInputRight();
            if (inputUp) {
                if (this.getMainBoomRotation() < 45) {
                    this.setMainsheetLength(this.getMainsheetLength() + 1);
                }
            }

            if (inputDown) {
                if (this.getMainsheetLength() > 0) {
                    this.setMainsheetLength(this.getMainsheetLength() - 1);
                }
            }

            /*
            if (inputUp && !this.getMainsailActive()) {
                this.setMainsailActive(true);
            }

            if (inputDown && this.getMainsailActive()) {
                this.setMainsailActive(false);
            }*/


        }
    }

    protected void tickWindInput() {
        super.tickWindInput();
        if (this.status == Status.IN_WATER || this.status == Status.IN_AIR) {
            //this.setMainsailActive(true);
            if (this.getMainsailActive()) {
                double windFunction = Mth.clamp(this.getWindVector().length(), 0.02, 1.0) * 0.3;

                float sailForce = this.getMainsailWindAngleAndForce()[1];
                float sailForceAngle = this.getMainsailWindAngleAndForce()[0];

                if (sailForce > this.getAcceleration()) {
                    this.setAcceleration(sailForce);
                } else {
                    this.setAcceleration(this.getAcceleration() - this.getMomentumSubtractor());
                    sailForce = this.getAcceleration();
                }

                this.setDeltaMovement(this.getDeltaMovement()
                        .add(Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * windFunction * 0.75 * sailForce, 0.0D,
                                Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * windFunction * 0.75 * sailForce));

                this.setDeltaMovement(this.getDeltaMovement()
                        .add(Mth.sin(-(Mth.wrapDegrees(sailForceAngle)) * ((float) Math.PI / 180F)) * windFunction * 0.25 * sailForce, 0.0D,
                                Mth.cos((Mth.wrapDegrees(sailForceAngle)) * ((float) Math.PI / 180F)) * windFunction * 0.25 * sailForce));
            }
            for(WindlassSwitchEntity windlass : this.getWindlasses()){
                if(windlass.getAnchored()){
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.5,1,0.5));
                }
            }
        }
    }

    @Override
    protected void tickAnchorInput() {
        for (WindlassSwitchEntity windlass : this.getWindlasses()) {
            if (windlass.getAnchored() && !this.getMainsailActive()) {
                this.setDeltaMovement(Vec3.ZERO);
            }
        }
    }

    public float[] getMainsailWindAngleAndForce() {
        //float windDifference = Mth.degreesDifference(Mth.wrapDegrees(this.getWindLocalRotation() -180), Mth.wrapDegrees(this.getMainBoomRotation()-this.getYRot()));

        //windForceAngle = sail relative to wind angle * 2

        float windDifference = Mth.degreesDifference(this.getLocalWindAngleAndSpeed()[0], Mth.wrapDegrees(this.getSailWorldRotation()));

        float windForceAngle = Mth.wrapDegrees(1 * windDifference - 180);


        float windForce = FirmacivHelper.sailForceMultiplierTable(windForceAngle);
        return new float[]{(float) windForceAngle, (float) windForce};
    }

    public float getMainBoomRotation() {
        return Mth.wrapDegrees(this.entityData.get(DATA_ID_MAIN_BOOM_ROTATION));
    }

    public void setMainBoomRotation(float rotation) {
        this.entityData.set(DATA_ID_MAIN_BOOM_ROTATION, Mth.clamp(rotation, -1 * getMainsheetLength(), getMainsheetLength()));
    }

    public float getMainsheetLength() {
        return Mth.wrapDegrees(this.entityData.get(DATA_ID_MAINSHEET_LENGTH));
    }

    public void setMainsheetLength(float length) {
        this.entityData.set(DATA_ID_MAINSHEET_LENGTH, Mth.clamp(length, 0, 45));
    }

    public void setMainsailActive(boolean mainsail) {
        this.entityData.set(DATA_ID_MAINSAIL_ACTIVE, mainsail);
    }

    public boolean getMainsailActive() {
        return this.entityData.get(DATA_ID_MAINSAIL_ACTIVE);
    }

    public void setJibsailActive(boolean jibsail) {
        this.entityData.set(DATA_ID_JIBSAIL_ACTIVE, jibsail);
    }

    public boolean getJibsailActive() {
        return this.entityData.get(DATA_ID_JIBSAIL_ACTIVE);
    }

    public void setRudderRotation(float rotation) {
        this.entityData.set(DATA_ID_RUDDER_ROTATION, Mth.clamp(rotation, -45, 45));
    }

    public float getRudderRotation() {
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
