package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.*;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import com.alekiponi.firmaciv.network.PacketHandler;
import com.alekiponi.firmaciv.network.ServerBoundSailUpdatePacket;
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
    public final int[] SAIL_SWITCHES = {17, 24};
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

    protected static final EntityDataAccessor<Integer> DATA_ID_TICKS_NO_RIDERS = SynchedEntityData.defineId(
            SloopEntity.class, EntityDataSerializers.INT);

    public final int[][] COMPARTMENT_ROTATIONS = {{7, 85}, {8, 85}, {9, 85}, {10, -85}, {11, -85}, {12, -85}};

    public final int[] CAN_ADD_ONLY_BLOCKS = {1, 2, 3, 4, 5, 6};

    protected final float PASSENGER_SIZE_LIMIT = 1.4F;

    protected final int SAIL_TOGGLE_TICKS = 20;
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
    public int[] getMastIndices() {
        return MASTS;
    }

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
            if (this.status == Status.IN_WATER) {
                this.setDeltaRotation((float) (-1 * this.getRudderRotation() * 0.25f *
                        (Mth.clamp(this.getDeltaMovement().length(), 0.05f, 1))));
                this.setDeltaRotation(Mth.clamp(this.getDeltaRotation(), -1f, 1f));
            }

            if(this.getMainsailActive() || this.getJibsailActive()){
                float rotationImpact = 0;

                float windDifference = Mth.degreesDifference(getMainsailWindAngleAndForce()[0], Mth.wrapDegrees(this.getYRot()));

                if (windDifference > 4) {
                    rotationImpact = 1f;
                } else if (windDifference < -4) {
                    rotationImpact = -1f;
                }

                rotationImpact = Mth.clamp((float) (rotationImpact * this.getDeltaMovement().length()), 0, 0.5f);

                this.setDeltaRotation(this.getDeltaRotation() + rotationImpact);
            }

            float boomWindDifference = Mth.degreesDifference(this.getLocalWindAngleAndSpeed()[0], Mth.wrapDegrees(this.getSailWorldRotation()));

            float sheet = this.getMainsheetLength();
            float boom = this.getMainBoomRotation();


            if (boomWindDifference < -171) {
                boomWindDifference = Mth.wrapDegrees(boomWindDifference - 180);
            }
            if (boomWindDifference > 5 && Math.abs(sheet - boom) > 2) {
                boom += 2f;
            }
            if (boomWindDifference < -5 && Math.abs(sheet - boom) > 2) {
                boom -= 2f;
            }

            if (sheet > Math.abs(boom)) {
                if (boom < 2) {
                    boom--;
                } else if (boom > 2) {
                    boom++;
                }
            }

            this.setMainBoomRotation(boom);

        }

        this.tickDestroyPlants();


        int ind = 0;
        for (SailSwitchEntity switchEntity : this.getSailSwitches()) {
            if (ind == 0) {
                this.setMainsailActive(switchEntity.getSwitched());
            }
            if (ind == 1) {
                this.setJibsailActive(switchEntity.getSwitched());
            }
            ind++;

        }
        if (this.getEntitiesToTakeWith().isEmpty() && this.getTruePassengers().isEmpty()) {
            this.setTicksNoRiders(this.getTicksNoRiders() + 1);
            if (this.getTicksNoRiders() >= SAIL_TOGGLE_TICKS) {
                for (SailSwitchEntity switchEntity : this.getSailSwitches()) {
                    switchEntity.setSwitched(false);
                }
                this.setJibsailActive(false);
                this.setMainsailActive(false);
            }
        } else {
            this.setTicksNoRiders(0);
        }
        if (!this.getMainsailActive() && !this.getJibsailActive()) {
            this.setMainsheetLength(0);
        }

        super.tick();
    }

    @Override
    protected void tickCleatInput() {
        if (this.getMainsailActive() || this.getJibsailActive()) {
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
        for (VehicleCleatEntity leashedCleat : leashedCleats) {
            if (this.getEntitiesToTakeWith().contains(leashedCleat.getLeashHolder())) {
                return;
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
                    double d0 = leashHolder1.getX() - leashHolder2.getX();
                    double d2 = leashHolder1.getZ() - leashHolder2.getZ();

                    float finalRotation = Mth.wrapDegrees(
                            (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F);

                    float approach = Mth.approachDegrees(this.getYRot(), finalRotation, 0.25f);
                    if (Mth.degreesDifferenceAbs(this.getYRot(), finalRotation) < 1.0) {
                        this.setDeltaRotation(0);
                        this.setYRot(this.getYRot());
                    } else {
                        this.setDeltaRotation(-1 * (this.getYRot() - approach));
                    }

                    Vec3 averageLeashHolderPosition =
                            new Vec3(
                            (leashHolder1.getPosition(0).x + leashHolder2.getPosition(0).x)/2.0,
                            0,
                            (leashHolder1.getPosition(0).z + leashHolder2.getPosition(0).z)/2.0);

                    Vec3 averageCleatPosition = new Vec3(
                            (cleat1.getPosition(0).x + cleat2.getPosition(0).x)/2.0,
                            0,
                            (cleat1.getPosition(0).z + cleat2.getPosition(0).z)/2.0);

                    Vec3 vectorToVehicle = averageCleatPosition.vectorTo(averageLeashHolderPosition).normalize();

                    Vec3 movementVector = vectorToVehicle.multiply(0.008, 0, 0.008).add(0, this.getDeltaMovement().y, 0);

                    if (averageCleatPosition.distanceTo(averageLeashHolderPosition) > 1.0) {
                        this.setDeltaMovement(movementVector);
                    } else {
                        this.setDeltaMovement(0,this.getDeltaMovement().y,0);
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
                        Vec3 movementVector = new Vec3(vectorToVehicle.x * -0.03f, this.getDeltaMovement().y,
                                vectorToVehicle.z * -0.03f);
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
    protected void tickTurnSpeedFactor() {
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
        this.entityData.define(DATA_ID_TICKS_NO_RIDERS, 0);
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
                    if (rudder < 0) {
                        rudder += 2;
                    } else {
                        rudder += 1;
                    }
                }
            }

            if (inputRight) {
                if (rudder > -45) {
                    if (rudder > 0) {
                        rudder -= 2;
                    } else {
                        rudder -= 1;
                    }

                }
            }

            if (!inputRight && !inputLeft) {
                if (rudder > 0) {
                    rudder -= 0.3f;
                }
                if (rudder < 0) {
                    rudder += 0.3f;
                }
                if (Math.abs(rudder) < 1) {
                    rudder = 0;
                }
            }
            this.setRudderRotation(rudder);


            tickSailBoat();
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
        return 0.001f;
    }

    protected void tickSailBoat() {
        if (getControllingCompartment() != null) {
            boolean inputUp = this.getControllingCompartment().getInputUp();
            boolean inputDown = this.getControllingCompartment().getInputDown();
            boolean inputLeft = this.getControllingCompartment().getInputLeft();
            boolean inputRight = this.getControllingCompartment().getInputRight();
            float sheet = this.getMainsheetLength();
            if (inputUp) {
                if (sheet < 45) {
                    sheet++;
                }
            }

            if (inputDown) {
                if (sheet > 0) {
                    sheet--;
                }
            }

            this.setMainsheetLength(sheet);
            if (this.level().isClientSide() && (inputUp || inputDown)) {
                PacketHandler.clientSendPacket(new ServerBoundSailUpdatePacket(sheet, this.getId()));
            }
        }
    }

    protected void tickWindInput() {
        super.tickWindInput();
        if (this.status == Status.IN_WATER || this.status == Status.IN_AIR) {
            float windFunction = (float) (Mth.clamp(this.getWindVector().length(), 0.02, 1.0) * 0.45);

            float sailForce = this.getMainsailWindAngleAndForce()[1];
            float sailForceAngle = Mth.wrapDegrees(this.getMainsailWindAngleAndForce()[0]);

            float acceleration = windFunction * sailForce;
            if (this.getMainsailActive()) {
                if (acceleration > this.getAcceleration()) {
                    this.setAcceleration(acceleration);
                } else if (this.getAcceleration() > this.getMomentumSubtractor()) {
                    this.setAcceleration(this.getAcceleration() - this.getMomentumSubtractor());
                    acceleration = this.getAcceleration();
                }

                float keelFactor = 0.6f;
                float sailFactor = 1 - keelFactor;

                Vec3 sailAccelerationWithKeel = new Vec3(
                        Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * acceleration * keelFactor,
                        0.0D,
                        Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * acceleration * keelFactor);

                Vec3 sailAccelerationWithSail = new Vec3(
                        Mth.sin(-(Mth.wrapDegrees(sailForceAngle)) * ((float) Math.PI / 180F)) * acceleration * sailFactor,
                        0.0D,
                        Mth.cos((Mth.wrapDegrees(sailForceAngle)) * ((float) Math.PI / 180F)) * acceleration * sailFactor);

                this.setDeltaMovement(this.getDeltaMovement()
                        .add(sailAccelerationWithKeel).add(sailAccelerationWithSail));



            }
            if (this.getJibsailActive()) {
                windFunction = (float) (Mth.clamp(this.getWindVector().length(), 0.02, 1.0) * 0.1);
                acceleration = windFunction * sailForce;

                if(!this.getMainsailActive()){
                    if (acceleration > this.getAcceleration()) {
                        this.setAcceleration(acceleration);
                    } else if (this.getAcceleration() > this.getMomentumSubtractor()) {
                        this.setAcceleration(this.getAcceleration() - this.getMomentumSubtractor());
                        acceleration = this.getAcceleration();
                    }
                }

                float keelFactor = 0.8f;
                float sailFactor = 1 - keelFactor;

                Vec3 sailAccelerationWithKeel = new Vec3(
                        Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * acceleration * keelFactor,
                        0.0D,
                        Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * acceleration * keelFactor);

                Vec3 sailAccelerationWithSail = new Vec3(
                        Mth.sin(-(Mth.wrapDegrees(sailForceAngle)) * ((float) Math.PI / 180F)) * acceleration * sailFactor,
                        0.0D,
                        Mth.cos((Mth.wrapDegrees(sailForceAngle)) * ((float) Math.PI / 180F)) * acceleration * sailFactor);

                this.setDeltaMovement(this.getDeltaMovement()
                        .add(sailAccelerationWithKeel).add(sailAccelerationWithSail));
            }
            for (WindlassSwitchEntity windlass : this.getWindlasses()) {
                if (windlass.getAnchored()) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.5, 1, 0.5));
                }
            }
        }
    }

    @Override
    protected void tickAnchorInput() {
        for (WindlassSwitchEntity windlass : this.getWindlasses()) {
            if (windlass.getAnchored() && !this.getMainsailActive() && this.getJibsailActive()) {
                this.setDeltaMovement(Vec3.ZERO);
            }
        }
    }

    public float[] getMainsailWindAngleAndForce() {
        float windDifference = Mth.degreesDifference(this.getWindLocalRotation(), Mth.wrapDegrees(this.getMainBoomRotation()));

        // calculate wind force for lifting scenario
        float windForceAngle = Mth.wrapDegrees(2 * windDifference + this.getYRot());

        if (Math.abs(windDifference) < 120) {
            // calculate wind force for dragging scenario
            windForceAngle = this.getLocalWindAngleAndSpeed()[0];
        }

        float windForce = FirmacivHelper.sailForceMultiplierTable(windDifference);
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

    public void setTicksNoRiders(int ticks) {
        this.entityData.set(DATA_ID_TICKS_NO_RIDERS, ticks);
    }

    public int getTicksNoRiders() {
        return this.entityData.get(DATA_ID_TICKS_NO_RIDERS);
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
        this.setJibsailActive(pCompound.getBoolean("jibsailActive"));
        this.setTicksNoRiders(pCompound.getInt("ticksNoRiders"));
        this.setMainsheetLength(pCompound.getFloat("mainSheetLength"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("mainBoomRotation", this.getMainBoomRotation());
        pCompound.putFloat("rudderRotation", this.getRudderRotation());
        pCompound.putBoolean("mainsailActive", this.getMainsailActive());
        pCompound.putBoolean("jibsailActive", this.getJibsailActive());
        pCompound.putInt("ticksNoRiders", this.getTicksNoRiders());
        pCompound.putFloat("mainSheetLength", this.getMainsheetLength());
    }
}
