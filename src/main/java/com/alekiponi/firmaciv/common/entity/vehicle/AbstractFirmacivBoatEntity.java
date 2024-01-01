package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCollisionEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehiclePartEntity;
import com.alekiponi.firmaciv.util.FirmacivHelper;
import net.dries007.tfc.common.entities.predator.Predator;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractFirmacivBoatEntity extends AbstractVehicle {
    public static final int PADDLE_LEFT = 0;
    public static final int PADDLE_RIGHT = 1;
    public static final double PADDLE_SOUND_TIME = Math.PI / 4;
    protected static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_LEFT = SynchedEntityData.defineId(
            AbstractFirmacivBoatEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_RIGHT = SynchedEntityData.defineId(
            AbstractFirmacivBoatEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Vector3f> DATA_ID_WIND_VECTOR = SynchedEntityData.defineId(
            AbstractFirmacivBoatEntity.class, EntityDataSerializers.VECTOR3);

    protected static final EntityDataAccessor<Float> DATA_ID_WIND_ANGLE = SynchedEntityData.defineId(
            AbstractFirmacivBoatEntity.class, EntityDataSerializers.FLOAT);

    protected static final EntityDataAccessor<Float> DATA_ID_WIND_SPEED = SynchedEntityData.defineId(
            AbstractFirmacivBoatEntity.class, EntityDataSerializers.FLOAT);


    protected static final float PADDLE_SPEED = ((float) Math.PI / 8F);

    public final int WIND_UPDATE_TICKS = 40;

    protected final float[] paddlePositions = new float[2];

    protected double windAngle;

    protected double windSpeed;
    protected double oldWindAngle;

    protected double oldWindSpeed;

    protected int windLerpTicks;

    protected Vec3 oldPosition;

    public AbstractFirmacivBoatEntity(final EntityType<? extends AbstractFirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);
        Vec2 windVector = Climate.getWindVector(this.level(), this.blockPosition());
        this.setWindVector(windVector);
        this.tickUpdateWind(false);
        windLerpTicks = 0;
        oldPosition = this.getPosition(0);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_PADDLE_LEFT, false);
        this.entityData.define(DATA_ID_PADDLE_RIGHT, false);

        this.entityData.define(DATA_ID_WIND_VECTOR, new Vector3f(0, 0, 0));
        this.entityData.define(DATA_ID_WIND_ANGLE, 0f);
        this.entityData.define(DATA_ID_WIND_SPEED, 0f);

    }

    @Override
    public void tick() {
        if (!this.level().isClientSide()) {
            if (this.getPassengers().size() < this.getMaxPassengers()) {
                final VehiclePartEntity newPart = FirmacivEntities.VEHICLE_PART_ENTITY.get().create(this.level());
                newPart.setPos(this.getX(), this.getY(), this.getZ());
                this.level().addFreshEntity(newPart);
                newPart.startRiding(this);
            }
        }


        this.oldStatus = this.status;
        this.status = this.getStatus();


        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - getDamageRecovery());
        }

        this.tickEffects();

        super.tick();
        this.tickLerp();

        this.tickWindInput();

        this.tickFloatBoat();
        this.tickControlBoat();
        if (this.isControlledByLocalInstance()) {
            if (this.level().isClientSide()) {

                this.level().sendPacketToServer(new ServerboundPaddleBoatPacket(this.getPaddleState(0), this.getPaddleState(1)));
            }
        }

        this.move(MoverType.SELF, this.getDeltaMovement());

        this.tickPaddlingEffects();

        this.checkInsideBlocks();

        this.tickUpdateWind(true);

        //This should ALWAYS be the only time the Y rotation is set during any given tick
        this.setYRot(this.getYRot() + this.getDeltaRotation());

        FirmacivHelper.tickHopPlayersOnboard(this);

        this.tickTakeEntitiesForARide();

    }



    protected void tickWindInput() {
        if (this.status == Status.IN_WATER || this.status == Status.IN_AIR) {
            double windFunction = Mth.clamp(this.getWindVector().length(), 0.001, 0.002 * this.getBoundingBox().getXsize());

            float windDifference = Mth.degreesDifference(this.getLocalWindAngleAndSpeed()[0], Mth.wrapDegrees(this.getYRot()));


            if (Math.abs(windDifference) < 90) {
                float angleMultiplier = Math.abs((Math.abs(windDifference) - 90) / 90);
                this.setDeltaMovement(this.getDeltaMovement()
                        .add(Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * windFunction * 0.45 * angleMultiplier, 0.0D,
                                Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * windFunction * 0.45 * angleMultiplier));
            }


            this.setDeltaMovement(this.getDeltaMovement()
                    .add(Mth.sin(-this.getLocalWindAngleAndSpeed()[0] * ((float) Math.PI / 180F)) * windFunction * 0.55, 0.0D,
                            Mth.cos(this.getLocalWindAngleAndSpeed()[0] * ((float) Math.PI / 180F)) * windFunction * 0.55));


            if (this.status == Status.IN_WATER) {
                if (windDifference > 1) {
                    this.setDeltaRotation(this.getDeltaRotation() - 0.1f);
                } else if (windDifference < -1) {
                    this.setDeltaRotation(this.getDeltaRotation() + 0.1f);
                }
            }

        }
    }

    protected void tickUpdateWind(boolean waitForWindUpdateTick) {
        if (tickCount % WIND_UPDATE_TICKS == 0 || !waitForWindUpdateTick) {
            Vec2 windVector = Climate.getWindVector(this.level(), this.blockPosition());
            //windVector = new Vec2(0,-0.5f);
            if (windVector.length() == 0) {
                windVector = new Vec2(-0.03f, 0f);
            }
            this.setWindVector(windVector);
            updateLocalWindAngleAndSpeed();
        }
        if (this.windLerpTicks > 0 && this.level().isClientSide()) {
            updateLocalWindAngleAndSpeed();
        }

    }

    protected void tickFloatBoat() {

        double gravAccel = -0.04F;
        double d1 = this.isNoGravity() ? 0.0D : (double) gravAccel;

        double d2 = 0.0D;
        this.invFriction = 0.05F;
        if (this.oldStatus == Status.IN_AIR && this.status != Status.IN_AIR && this.status != Status.ON_LAND) {
            this.waterLevel = this.getY(1.0D);
            this.setPos(this.getX(), (double) (this.getWaterLevelAbove() - this.getBbHeight()) + 0.101D, this.getZ());
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
            this.lastYd = 0.0D;
            this.status = Status.IN_WATER;
        } else {

            if (this.status == Status.IN_WATER) {
                d2 = ((this.waterLevel - this.getY()) / (double) this.getBbHeight()) + 0.1;
                this.invFriction = 0.9F;
            } else if (this.status == Status.UNDER_FLOWING_WATER) {
                d1 = -7.0E-4D;
                this.invFriction = 0.9F;
            } else if (this.status == Status.UNDER_WATER) {
                d2 = 0.01F;
                this.invFriction = 0.45F;
            } else if (this.status == Status.IN_AIR) {
                this.invFriction = 0.9F;
            } else if (this.status == Status.ON_LAND) {
                this.invFriction = this.landFriction;
                if (invFriction > 0.5F) {
                    invFriction = 0.5F;
                }
                if (this.getControllingPassenger() instanceof Player) {
                    this.landFriction /= 2.0F;
                }
            }

            if (Math.abs(this.getDeltaRotation()) > 0) {
                float rotationalFriction = (Math.abs(this.getDeltaRotation()) / 48.0F);

                float modifiedFriction = this.invFriction - rotationalFriction;
                if (modifiedFriction > 2.0F) {
                    modifiedFriction = 2.0F;
                } else if (modifiedFriction < 0.0F) {
                    modifiedFriction = 0.0F;
                }
                this.invFriction = modifiedFriction;
            }


            Vec3 vec3 = this.getDeltaMovement();

            this.setDeltaMovement(vec3.x * (double) this.invFriction, vec3.y + d1, vec3.z * (double) this.invFriction);

            if (this.getControllingCompartment() != null) {
                double turnSpeedFactor = this.getDeltaMovement().length() * 12.0F;


                if (this.getControllingCompartment().getInputLeft() || this.getControllingCompartment()
                        .getInputRight()) {
                    this.setDeltaRotation(((this.invFriction / 3.0F)) * this.getDeltaRotation());
                    this.setDeltaRotation((float) (turnSpeedFactor * this.getDeltaRotation()));

                } else {
                    this.setDeltaRotation(this.getDeltaRotation() * (this.invFriction / 2.0F));
                }
            }


            if (d2 > 0.0D) {
                Vec3 vec31 = this.getDeltaMovement();
                this.setDeltaMovement(vec31.x, (vec31.y + d2 * 0.06153846016296973D) * 0.75D, vec31.z);
            }


        }

    }


    protected void tickControlBoat() {
        if (getControllingCompartment() != null) {
            boolean inputUp = this.getControllingCompartment().getInputUp();
            boolean inputDown = this.getControllingCompartment().getInputDown();
            boolean inputLeft = this.getControllingCompartment().getInputLeft();
            boolean inputRight = this.getControllingCompartment().getInputRight();
            float acceleration = 0;
            float paddleMultiplier = this.getPaddleMultiplier();

            float forward = getPaddleAcceleration()[0];
            float backward = getPaddleAcceleration()[1];
            float turning = getPaddleAcceleration()[2];

            if (inputLeft) {
                this.setDeltaRotation(this.getDeltaRotation() - 1);
            }

            if (inputRight) {
                this.setDeltaRotation(this.getDeltaRotation() + 1);
            }

            if (inputRight != inputLeft && !inputUp && !inputDown) {
                acceleration += turning * paddleMultiplier;
            }


            if (inputUp) {
                acceleration += forward * paddleMultiplier;
            }

            if (inputDown) {
                acceleration -= backward * paddleMultiplier;
            }

            if (Math.abs(acceleration) > Math.abs(this.getAcceleration())) {
                this.setAcceleration(acceleration);
            } else {
                if (Math.abs(this.getAcceleration()) < 1) {
                    this.setAcceleration(0);
                } else if (this.getAcceleration() > 0) {
                    this.setAcceleration(this.getAcceleration() - this.getMomentumSubtractor());
                } else if (this.getAcceleration() < 0) {
                    this.setAcceleration(this.getAcceleration() + this.getMomentumSubtractor());
                }
                acceleration = this.getAcceleration();
            }

            this.setDeltaMovement(this.getDeltaMovement()
                    .add(Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * acceleration, 0.0D,
                            Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * acceleration));

            this.setPaddleState(
                    inputRight && !inputLeft || inputUp, inputLeft && !inputRight || inputUp);

        }
    }

    @Override
    protected void tickCleatInput(){
        if(this.getPassengers().size() == this.getMaxPassengers()){
            for (int i : this.getCleats()) {
                if (this.getPassengers().get(i).getFirstPassenger() instanceof VehicleCleatEntity cleat) {
                    if(cleat.isLeashed()){
                        Entity leashHolder = cleat.getLeashHolder();
                        if(leashHolder != null){
                            if (leashHolder instanceof Player) {
                                if (this.distanceTo(leashHolder) > 4f) {
                                    Vec3 vectorToVehicle = leashHolder.getPosition(0).vectorTo(this.getPosition(0)).normalize();
                                    Vec3 movementVector = new Vec3(vectorToVehicle.x * -0.1f, this.getDeltaMovement().y,
                                            vectorToVehicle.z * -0.1f);
                                    double vehicleSize = Mth.clamp(this.getBbWidth(), 1, 100);
                                    movementVector = movementVector.multiply(1 / vehicleSize, 0, 1 / vehicleSize);

                                    double d0 = leashHolder.getPosition(0).x - this.getX();
                                    double d2 = leashHolder.getPosition(0).z - this.getZ();

                                    float finalRotation = Mth.wrapDegrees(
                                            (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F);

                                    double difference = (leashHolder.getY()) - this.getY();
                                    if (leashHolder.getY() > this.getY() && difference >= 0.4 && difference <= 1.0 && this.getDeltaMovement()
                                            .length() < 0.02f && leashHolder instanceof Player) {
                                        this.setPos(this.getX(), this.getY() + 0.55f, this.getZ());
                                    }


                                    float approach = Mth.approachDegrees(this.getYRot(), finalRotation, 6);

                                    this.setDeltaMovement(this.getDeltaMovement().add(movementVector));
                                    this.setDeltaRotation(-1 * (this.getYRot() - approach));

                                }
                            }
                            if (leashHolder instanceof HangingEntity) {
                                Vec3 vectorToVehicle = leashHolder.getPosition(0).vectorTo(this.getPosition(0)).normalize();
                                Vec3 movementVector = new Vec3(vectorToVehicle.x * -0.005f, this.getDeltaMovement().y,
                                        vectorToVehicle.z * -0.005f);
                                double d0 = leashHolder.getPosition(0).x - this.getX();
                                double d2 = leashHolder.getPosition(0).z - this.getZ();

                                float finalRotation = Mth.wrapDegrees(
                                        (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F);

                                float approach = Mth.approachDegrees(this.getYRot(), finalRotation, 4f);
                                if (Mth.degreesDifferenceAbs(this.getYRot(), finalRotation) < 4) {
                                    this.setDeltaRotation(0);
                                    this.setYRot(this.getYRot());
                                } else {
                                    this.setDeltaRotation(-1 * (this.getYRot() - approach));
                                }
                                if (this.distanceTo(leashHolder) > 1) {
                                    this.setDeltaMovement(movementVector);
                                }

                            }
                        }

                    }
                }
            }
        }

    }

    protected abstract float getPaddleMultiplier();

    protected float[] getPaddleAcceleration() {
        float forward = 0.0275F;
        float backward = 0.0125F;
        float turning = 0.0025F;
        return new float[]{forward, backward, turning};
    }

    @Override
    public void playerTouch(Player pPlayer) {
        /*
        //if the player is touching from the side only
        */

    }

    protected abstract float getMomentumSubtractor();

    protected void tickEffects() {
        if (this.status == Status.IN_WATER && !this.getPassengers().isEmpty()) {
            if (Math.abs(this.getDeltaRotation()) > 2) {
                this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double) this.random.nextFloat(),
                        this.getY() + 0.7D, this.getZ() + (double) this.random.nextFloat(), 0.0D, 0.0D, 0.0D);
                if (this.random.nextInt(20) == 0) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimSound(),
                            this.getSoundSource(), 0.2F, 0.8F + 0.4F * this.random.nextFloat(), false);
                }
                if (this.getControllingCompartment() != null && Math.abs(
                        this.getDeltaRotation()) > 5 && (this.getControllingCompartment()
                        .getInputRight() || this.getControllingCompartment().getInputLeft())) {
                    this.level()
                            .playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimHighSpeedSplashSound(),
                                    this.getSoundSource(), 0.2F, 0.8F + 0.4F * this.random.nextFloat(), false);


                    Vec3 splashOffset = this.getDeltaMovement().yRot(45);
                    if (this.getControllingCompartment().getInputLeft()) {
                        splashOffset = this.getDeltaMovement().yRot(-45);
                    }
                    splashOffset.normalize();

                    for (int i = 0; i < 8; i++) {
                        this.level().addParticle(ParticleTypes.BUBBLE_POP,
                                this.getX() + (double) this.random.nextFloat() + splashOffset.x * 2 + this.getDeltaMovement().x * i,
                                this.getY() + 0.7D,
                                this.getZ() + (double) this.random.nextFloat() + splashOffset.z * 2 + this.getDeltaMovement().x * i,
                                0.0D, 0.0D, 0.0D);
                        this.level().addParticle(ParticleTypes.SPLASH,
                                this.getX() + (double) this.random.nextFloat() + splashOffset.x * 2 + this.getDeltaMovement().x * i,
                                this.getY() + 0.7D,
                                this.getZ() + (double) this.random.nextFloat() + splashOffset.z * 2 + this.getDeltaMovement().x * i,
                                0.0D, 0.0D, 0.0D);
                    }
                }

            } else if (this.getDeltaMovement().length() > 0.10) {
                if (this.random.nextInt(8) == 0) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimSound(),
                            this.getSoundSource(), 0.1F, 0.8F + 0.4F * this.random.nextFloat(), false);
                    this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double) this.random.nextFloat(),
                            this.getY() + 0.7D, this.getZ() + (double) this.random.nextFloat(), 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    protected void tickPaddlingEffects() {
        for (int i = 0; i <= 1; ++i) {
            if (this.getPaddleState(i)) {
                if (!this.isSilent() && (double) (this.paddlePositions[i] % ((float) Math.PI * 2F)) <= (double) ((float) Math.PI / 4F) && (double) ((this.paddlePositions[i] + ((float) Math.PI / 8F)) % ((float) Math.PI * 2F)) >= (double) ((float) Math.PI / 4F)) {
                    SoundEvent soundevent = this.getPaddleSound();
                    if (soundevent != null) {
                        Vec3 vec3 = this.getViewVector(1.0F);
                        double d0 = i == 1 ? -vec3.z : vec3.z;
                        double d1 = i == 1 ? vec3.x : -vec3.x;
                        this.level().playSound(null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent,
                                this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat());
                        this.level().gameEvent(this.getControllingPassenger(), GameEvent.SPLASH,
                                new BlockPos((int) (this.getX() + d0), (int) this.getY(), (int) (this.getZ() + d1)));
                    }
                }

                this.paddlePositions[i] += ((float) Math.PI / 8F);
            } else {
                this.paddlePositions[i] = 0.0F;
            }
        }
    }

    public void updateLocalWindAngleAndSpeed() {

        double newDirection = FirmacivHelper.vec2ToWrappedDegrees(this.getWindVector());
        double newSpeed = Math.abs(Math.round(this.getWindVector().length() * 320));

        if (this.level().isClientSide()) {
            if (this.windLerpTicks > 0) {
                float lerpStep = ((WIND_UPDATE_TICKS) - this.windLerpTicks) / ((float) WIND_UPDATE_TICKS);
                double lerpedRot = Math.round(Mth.rotLerp(lerpStep, (float) this.oldWindAngle, (float) newDirection));

                this.windLerpTicks--;

                this.windAngle = Mth.wrapDegrees((float) Math.round(lerpedRot));

                this.windSpeed = (float) this.oldWindSpeed;
                return;
            }

            if (newDirection != this.windAngle) {
                this.oldWindAngle = this.windAngle;
                this.oldWindSpeed = this.windSpeed;
                this.windLerpTicks = WIND_UPDATE_TICKS;
                return;
            }
        }

        this.windAngle = Mth.wrapDegrees((float) Math.round(newDirection));
        this.windSpeed = (float) newSpeed;
    }

    public float[] getLocalWindAngleAndSpeed() {
        return new float[]{(float) this.windAngle, (float) this.windSpeed};
    }

    @Nullable
    protected SoundEvent getPaddleSound() {
        switch (this.getStatus()) {
            case IN_WATER:
            case UNDER_WATER:
            case UNDER_FLOWING_WATER:
                return SoundEvents.BOAT_PADDLE_WATER;
            case ON_LAND:
                return SoundEvents.BOAT_PADDLE_LAND;
            case IN_AIR:
            default:
                return null;
        }
    }


    public void setPaddleState(boolean pLeft, boolean pRight) {
        this.entityData.set(DATA_ID_PADDLE_LEFT, pLeft);
        this.entityData.set(DATA_ID_PADDLE_RIGHT, pRight);
    }

    public float getRowingTime(int pSide, float pLimbSwing) {
        return this.getPaddleState(pSide) ? Mth.clampedLerp(this.paddlePositions[pSide] - ((float) Math.PI / 8F),
                this.paddlePositions[pSide], pLimbSwing) : 0.0F;
    }

    public boolean getPaddleState(final int side) {
        return this.entityData.<Boolean>get(
                side == 0 ? DATA_ID_PADDLE_LEFT : DATA_ID_PADDLE_RIGHT) && this.getControllingPassenger() != null;
    }


    public void setWindVector(final Vec2 windVector) {
        this.entityData.set(DATA_ID_WIND_VECTOR, new Vector3f(windVector.x, 0, windVector.y));
    }

    public Vec2 getWindVector() {
        float x = this.entityData.get(DATA_ID_WIND_VECTOR).x;
        float y = this.entityData.get(DATA_ID_WIND_VECTOR).z;
        return new Vec2(x, y);
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.tickUpdateWind(false);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("deltaRotation", this.getDeltaRotation());
    }

    public float getWindLocalRotation() {
        return Mth.wrapDegrees(getLocalWindAngleAndSpeed()[0] - Mth.wrapDegrees(this.getYRot()));
    }

}