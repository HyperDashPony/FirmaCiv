package com.alekiponi.firmaciv.common.entity;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.AbstractCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.EmptyCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehiclePartEntity;
import com.google.common.collect.Lists;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.BlockUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat.Status;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractFirmacivBoatEntity extends Entity {
    public static final int PADDLE_LEFT = 0;
    public static final int PADDLE_RIGHT = 1;
    public static final double PADDLE_SOUND_TIME = Math.PI / 4;
    protected static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(
            AbstractFirmacivBoatEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(
            AbstractFirmacivBoatEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(
            AbstractFirmacivBoatEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_LEFT = SynchedEntityData.defineId(
            AbstractFirmacivBoatEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_RIGHT = SynchedEntityData.defineId(
            AbstractFirmacivBoatEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Vector3f> DATA_ID_DELTA_MOVEMENT = SynchedEntityData.defineId(
            AbstractFirmacivBoatEntity.class, EntityDataSerializers.VECTOR3);


    protected static final float PADDLE_SPEED = ((float) Math.PI / 8F);
    public final int MAX_PASSENGER_NUMBER = 0;

    public final int[] CLEATS = {};
    public final int[] CAN_ADD_ONLY_BLOCKS = {};

    public final int[][] COMPARTMENT_ROTATIONS = {};

    protected final float TOP_SPEED = 80.0f;
    protected final float DAMAGE_THRESHOLD = 80.0f;
    protected final float PASSENGER_SIZE_LIMIT = 0.9F;
    protected final float DAMAGE_RECOVERY = 2.0f;
    protected final float[] paddlePositions = new float[2];
    protected float invFriction;
    protected float deltaRotation;
    protected int lerpSteps;
    protected double lerpX;
    protected double lerpY;
    protected double lerpZ;
    protected double lerpYRot;
    protected double lerpXRot;
    protected double waterLevel;
    protected float landFriction;
    @Nullable
    protected Status status;
    @Nullable
    protected Status oldStatus;
    protected double lastYd;

    protected double windAngle;

    protected double windSpeed;
    Vec2 windVector;

    public AbstractFirmacivBoatEntity(final EntityType<? extends AbstractFirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
        this.windVector = Climate.getWindVector(this.level(), this.blockPosition());
        this.updateWind(false);
    }

    public static boolean canVehicleCollide(final Entity vehicle, final Entity entity) {
        return (entity.canBeCollidedWith() || entity.isPushable()) && !vehicle.isPassengerOfSameVehicle(entity);
    }

    // consider these reference implementations

    public int getMaxPassengers() {
        return MAX_PASSENGER_NUMBER;
    }

    public int[] getCleats() {
        return CLEATS;
    }

    public int getCompartmentRotation(int i) {
        return COMPARTMENT_ROTATIONS[i][0];
    }

    public float getPassengerSizeLimit() {
        return PASSENGER_SIZE_LIMIT;
    }

    public int[][] getCompartmentRotationsArray() {
        return COMPARTMENT_ROTATIONS;
    }

    public int[] getCanAddOnlyBlocks() {
        return CAN_ADD_ONLY_BLOCKS;
    }

    @Override
    protected float getEyeHeight(final Pose pose, final EntityDimensions entityDimensions) {
        return entityDimensions.height;
    }

    protected float getDamageThreshold() {
        return this.DAMAGE_THRESHOLD;
    }

    protected float getDamageRecovery() {
        return this.DAMAGE_RECOVERY;
    }

    public float getDeltaRotation() {
        return deltaRotation;
    }

    public void setDeltaRotation(float deltaRotation) {
        this.deltaRotation = deltaRotation;
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_HURT, 0);
        this.entityData.define(DATA_ID_HURTDIR, 1);
        this.entityData.define(DATA_ID_DAMAGE, 0.0F);
        this.entityData.define(DATA_ID_PADDLE_LEFT, false);
        this.entityData.define(DATA_ID_PADDLE_RIGHT, false);
        this.entityData.define(DATA_ID_DELTA_MOVEMENT, new Vector3f(0,0,0));
    }

    @Override
    public boolean canCollideWith(final Entity other) {
        return canVehicleCollide(this, other);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }


    @Override
    protected Vec3 getRelativePortalPosition(final Direction.Axis axis, final BlockUtil.FoundRectangle portal) {
        return LivingEntity.resetForwardDirectionOfRelativePortalPosition(
                super.getRelativePortalPosition(axis, portal));
    }

    public boolean canIAcceptLargePassengers(EmptyCompartmentEntity compartmentEntity) {
        return true;
    }

    public boolean canIAcceptPassengersOrBlocks(EmptyCompartmentEntity compartmentEntity) {
        return true;
    }

    @Nullable
    public Entity getCompAsEntityFromIndex(int index) {
        if (this.getPassengers().size() == this.getMaxPassengers()) {
            if (this.getPassengers().get(index).isVehicle()) {
                return this.getPassengers().get(index).getFirstPassenger();
            }
        }
        return null;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0;
    }

    @Override
    public boolean hurt(final DamageSource damageSource, final float amount) {
        if (this.isInvulnerableTo(damageSource)) return false;

        if (this.level().isClientSide || this.isRemoved()) return true;

        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() + amount * 10.0F);
        this.markHurt();
        this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
        final boolean instantKill = damageSource.getEntity() instanceof Player && ((Player) damageSource.getEntity()).getAbilities().instabuild;

        if (instantKill || this.getDamage() > getDamageThreshold()) {
            if (!instantKill && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                this.spawnAtLocation(this.getDropItem());
            }
            this.discard();
        }

        return true;
    }

    @Override
    public void push(final Entity entity) {
        if (entity instanceof AbstractFirmacivBoatEntity) {
            if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.push(entity);
            }
        } else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.push(entity);
        }
    }

    /**
     * The item that should be dropped when this entity dies
     *
     * @return Item Instance
     */
    public Item getDropItem() {
        return Items.AIR;
    }

    /**
     * Sets up the entity to do the hurt animation. Only used by packets in multiplayer.
     */
    @Override
    public void animateHurt(final float pYaw) {
        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() * 11.0F);
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public void lerpTo(final double posX, final double posY, final double posZ, final float yaw, final float pitch,
            final int pPosRotationIncrements, final boolean teleport) {
        this.lerpX = posX;
        this.lerpY = posY;
        this.lerpZ = posZ;
        this.lerpYRot = yaw;
        this.lerpXRot = pitch;
        this.lerpSteps = 10;
    }

    @Override
    public Direction getMotionDirection() {
        return this.getDirection().getClockWise();
    }


    @Override
    public void tick() {

        /*
        if (this.getControllingPassenger() == null) {
            this.deltaRotation = 0;
        }*/
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
        /*
        if(this.level().isClientSide()){
            this.tickFloatBoat();
        }*/

        if (this.isControlledByLocalInstance()) {
            if (!(this.getFirstPassenger() instanceof Player)) {
                this.setPaddleState(false, false);
            }

            this.tickFloatBoat();
            if (this.level().isClientSide()) {
                this.tickControlBoat();
                this.level().sendPacketToServer(new ServerboundPaddleBoatPacket(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            //this.setDeltaMovement(Vec3.ZERO);
        }



        /*
        if(this.level().isClientSide()){
            this.level().sendPacketToServer();
        }*/

        this.tickPaddlingEffects();

        this.checkInsideBlocks();

        this.updateWind(true);

    }

    protected void tickWindInput(){
        if(this.status != Status.ON_LAND){
            Vec3 windMovement = new Vec3(this.getLocalWindVector().x, 0, this.getLocalWindVector().y).normalize();
            double windFunction = Mth.clamp(this.getLocalWindVector().length(), 0.0001, 0.002);
            windMovement = windMovement.multiply(windFunction, windFunction, windFunction);

            this.setDeltaMovement(this.getDeltaMovement().add(windMovement));
        }
    }

    protected void tickPaddleInput(){
        if (getControllingCompartment() != null) {
            boolean inputUp = this.getControllingCompartment().getInputUp();
            boolean inputDown = this.getControllingCompartment().getInputDown();
            boolean inputLeft = this.getControllingCompartment().getInputLeft();
            boolean inputRight = this.getControllingCompartment().getInputRight();
            float f = 0.0F;

            if (inputRight != inputLeft && !inputUp && !inputDown) {
                f += 0.005F;
            }

            if (inputUp) {
                f += 0.055F;
            }

            if (inputDown) {
                f -= 0.025F;
            }

            this.setDeltaMovement(this.getDeltaMovement()
                    .add(Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * f, 0.0D,
                            Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * f));
            this.setPaddleState(
                    inputRight && !inputLeft || inputUp, inputLeft && !inputRight || inputUp);
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

            if (Math.abs(deltaRotation) > 0) {
                float rotationalFriction = (Math.abs(deltaRotation) / 48.0F);

                float modifiedFriction = this.invFriction - rotationalFriction;
                if (modifiedFriction > 2.0F) {
                    modifiedFriction = 2.0F;
                } else if (modifiedFriction < 0.0F) {
                    modifiedFriction = 0.0F;
                }
                this.invFriction = modifiedFriction;
            }

            tickWindInput();
            //tickPaddleInput();
            //tickControlBoat();

            Vec3 vec3 = this.getDeltaMovement();

            this.setDeltaMovement(vec3.x * (double) this.invFriction, vec3.y + d1, vec3.z * (double) this.invFriction);

            if (this.getControllingCompartment() != null) {
                double turnSpeedFactor = this.getDeltaMovement().length() * 12.0F;


                if (this.getControllingCompartment().getInputLeft() || this.getControllingCompartment()
                        .getInputRight()) {
                    this.deltaRotation *= ((this.invFriction / 3.0F));
                    this.deltaRotation *= turnSpeedFactor;

                } else {
                    this.deltaRotation *= (this.invFriction / 2.0F);
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
            float f = 0.0F;
            if (inputLeft) {
                --this.deltaRotation;
            }

            if (inputRight) {
                ++this.deltaRotation;
            }

            if (inputRight != inputLeft && !inputUp && !inputDown) {
                f += 0.005F;
            }

            this.setYRot(this.getYRot() + this.deltaRotation);
            if (inputUp) {
                f += 0.055F;
            }

            if (inputDown) {
                f -= 0.025F;
            }

            this.setDeltaMovement(this.getDeltaMovement()
                    .add(Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * f, 0.0D,
                            Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * f));
            this.setPaddleState(
                    inputRight && !inputLeft || inputUp, inputLeft && !inputRight || inputUp);
        }
        if (this.isVehicle()) {

        }
    }

    protected void tickEffects(){
        if (this.status == Status.IN_WATER && !this.getPassengers().isEmpty()) {
            if (Math.abs(this.deltaRotation) > 2) {
                this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double) this.random.nextFloat(),
                        this.getY() + 0.7D, this.getZ() + (double) this.random.nextFloat(), 0.0D, 0.0D, 0.0D);
                if (this.random.nextInt(20) == 0) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimSound(),
                            this.getSoundSource(), 0.2F, 0.8F + 0.4F * this.random.nextFloat(), false);
                }
                if (this.getControllingCompartment() != null && Math.abs(
                        this.deltaRotation) > 5 && (this.getControllingCompartment()
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

    protected void tickPaddlingEffects(){
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

    protected void updateWind(boolean waitForWindUpdateTick){
        if(tickCount % 40 == 0 || !waitForWindUpdateTick){
            windVector = Climate.getWindVector(this.level(), this.blockPosition());
            double direction = Math.round(Math.toDegrees(Math.atan(windVector.x / windVector.y)));
            double speed = Math.abs(Math.round(windVector.length() * 320));
            this.windSpeed = speed;
            this.windAngle = Mth.wrapDegrees(direction);
        }
    }
    public float[] getLocalWindAngleAndSpeed() {
        return new float[]{(float) windAngle, (float) windSpeed};
    }
    public Vec2 getLocalWindVector(){
        return windVector;
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

    protected void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double) this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double) this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double) this.lerpSteps;
            double d3 = Mth.wrapDegrees(this.lerpYRot - (double) this.getYRot());
            this.setYRot(this.getYRot() + (float) d3 / (float) this.lerpSteps);
            this.setXRot(this.getXRot() + (float) (this.lerpXRot - (double) this.getXRot()) / (float) this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d1, d2);
            this.setRot(this.getYRot(), this.getXRot());
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

    protected Status getStatus() {
        final Status underwater = this.isUnderwater();

        if (underwater != null) {
            this.waterLevel = this.getBoundingBox().maxY;
            return underwater;
        }

        if (this.checkInWater()) {
            return Status.IN_WATER;
        }

        final float groundFriction = this.getGroundFriction();

        if (0 < groundFriction) {
            this.landFriction = groundFriction;
            return Status.ON_LAND;
        }

        return Status.IN_AIR;
    }

    public float getWaterLevelAbove() {
        final AABB boundingBox = this.getBoundingBox();
        final int minX = Mth.floor(boundingBox.minX);
        final int maxX = Mth.ceil(boundingBox.maxX);
        final int maxY = Mth.floor(boundingBox.maxY);
        final int l = Mth.ceil(boundingBox.maxY - this.lastYd);
        final int minZ = Mth.floor(boundingBox.minZ);
        final int maxZ = Mth.ceil(boundingBox.maxZ);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        topLoop:
        for (int k1 = maxY; k1 < l; ++k1) {
            float waterLevel = 0;

            for (int l1 = minX; l1 < maxX; ++l1) {
                for (int i2 = minZ; i2 < maxZ; ++i2) {
                    mutableBlockPos.set(l1, k1, i2);
                    final FluidState fluidstate = this.level().getFluidState(mutableBlockPos);
                    if (fluidstate.is(FluidTags.WATER)) {
                        waterLevel = Math.max(waterLevel, fluidstate.getHeight(this.level(), mutableBlockPos));
                    }

                    if (1 <= waterLevel) {
                        continue topLoop;
                    }
                }
            }

            if (1 > waterLevel) {
                return mutableBlockPos.getY() + waterLevel;
            }
        }

        return l + 1;
    }

    public float getGroundFriction() {
        AABB aabb = this.getBoundingBox();
        AABB aabb1 = new AABB(aabb.minX, aabb.minY - 0.001D, aabb.minZ, aabb.maxX, aabb.minY, aabb.maxZ);
        int i = Mth.floor(aabb1.minX) - 1;
        int j = Mth.ceil(aabb1.maxX) + 1;
        int k = Mth.floor(aabb1.minY) - 1;
        int l = Mth.ceil(aabb1.maxY) + 1;
        int i1 = Mth.floor(aabb1.minZ) - 1;
        int j1 = Mth.ceil(aabb1.maxZ) + 1;
        VoxelShape voxelshape = Shapes.create(aabb1);
        float f = 0.0F;
        int k1 = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int l1 = i; l1 < j; ++l1) {
            for (int i2 = i1; i2 < j1; ++i2) {
                int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
                if (j2 != 2) {
                    for (int k2 = k; k2 < l; ++k2) {
                        if (j2 <= 0 || k2 != k && k2 != l - 1) {
                            blockpos$mutableblockpos.set(l1, k2, i2);
                            BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos);
                            if (!(blockstate.getBlock() instanceof WaterlilyBlock) && Shapes.joinIsNotEmpty(
                                    blockstate.getCollisionShape(this.level(), blockpos$mutableblockpos)
                                            .move(l1, k2, i2), voxelshape, BooleanOp.AND)) {
                                f += blockstate.getFriction(this.level(), blockpos$mutableblockpos, this);
                                ++k1;
                            }
                        }
                    }
                }
            }
        }

        return f / k1;
    }

    protected boolean checkInWater() {
        AABB aabb = this.getBoundingBox();
        int i = Mth.floor(aabb.minX);
        int j = Mth.ceil(aabb.maxX);
        int k = Mth.floor(aabb.minY);
        int l = Mth.ceil(aabb.minY + 0.001D);
        int i1 = Mth.floor(aabb.minZ);
        int j1 = Mth.ceil(aabb.maxZ);
        boolean flag = false;
        this.waterLevel = -Double.MAX_VALUE;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    blockpos$mutableblockpos.set(k1, l1, i2);
                    FluidState fluidstate = this.level().getFluidState(blockpos$mutableblockpos);
                    if (fluidstate.is(FluidTags.WATER)) {
                        float f = (float) l1 + fluidstate.getHeight(this.level(), blockpos$mutableblockpos);
                        this.waterLevel = Math.max(f, this.waterLevel);
                        flag |= aabb.minY < (double) f;
                    }
                }
            }
        }

        return flag;
    }

    @Nullable
    protected Status isUnderwater() {
        final AABB aabb = this.getBoundingBox();
        final double d0 = aabb.maxY + 0.001D;
        final int i = Mth.floor(aabb.minX);
        final int j = Mth.ceil(aabb.maxX);
        final int k = Mth.floor(aabb.maxY);
        final int l = Mth.ceil(d0);
        final int i1 = Mth.floor(aabb.minZ);
        final int j1 = Mth.ceil(aabb.maxZ);
        boolean isUnderwater = false;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    mutableBlockPos.set(k1, l1, i2);
                    final FluidState fluidstate = this.level().getFluidState(mutableBlockPos);
                    if (fluidstate.is(FluidTags.WATER) &&
                            d0 < mutableBlockPos.getY() + fluidstate.getHeight(this.level(), mutableBlockPos)) {
                        if (!fluidstate.isSource()) {
                            return Status.UNDER_FLOWING_WATER;
                        }

                        isUnderwater = true;
                    }
                }
            }
        }

        return isUnderwater ? Status.UNDER_WATER : null;
    }


    public final List<Entity> getTruePassengers() {
        final List<Entity> truePassengers = Lists.newArrayList();

        for (final Entity vehiclePart : this.getPassengers()) {
            if (vehiclePart.isVehicle() && vehiclePart.getFirstPassenger() instanceof AbstractCompartmentEntity compartmentEntity) {
                if (compartmentEntity.isVehicle()) {
                    truePassengers.add(compartmentEntity.getFirstPassenger());
                }
            }
        }
        return truePassengers;
    }

    public final List<AbstractCompartmentEntity> getCompartments() {
        final List<AbstractCompartmentEntity> compartments = Lists.newArrayList();

        for (final Entity vehiclePart : this.getPassengers()) {
            if (vehiclePart.isVehicle() && vehiclePart.getFirstPassenger() instanceof AbstractCompartmentEntity compartmentEntity) {
                compartments.add(compartmentEntity);
            }
        }
        return compartments;
    }

    public final List<VehiclePartEntity> getVehicleParts() {
        final List<VehiclePartEntity> vehicleParts = Lists.newArrayList();

        for (final Entity vehiclePart : this.getPassengers()) {
            vehicleParts.add((VehiclePartEntity) vehiclePart);
        }
        return vehicleParts;
    }

    public boolean isBeingTowed() {
        if (this.getPassengers().size() == this.getMaxPassengers()) {
            for (int i : this.getCleats()) {
                if (this.getPassengers().get(i).getFirstPassenger() instanceof VehicleCleatEntity vehicleCleat) {
                    return vehicleCleat.isLeashed() && this.getDeltaMovement().length() != 0;
                }
            }
        }
        return false;
    }


    @Override
    protected void positionRider(final Entity passenger, final Entity.MoveFunction moveFunction) {
        if (this.hasPassenger(passenger)) {
            if (!(passenger instanceof VehiclePartEntity)) {
                passenger.stopRiding();
            }

            float localX = 0.0F;
            float localZ = 0.0F;
            float localY = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());

            if (this.getPassengers().size() > 1) {
                switch (this.getPassengers().indexOf(passenger)) {
                    case 0 -> {
                        localX = 0.3f;
                        localZ = 0.0f;
                    }
                    case 1 -> {
                        localX = -0.7f;
                        localZ = 0.0f;
                    }
                }
            }

            final Vec3 vec3 = this.positionVehiclePartEntityLocally(localX, localY, localZ);
            moveFunction.accept(passenger, this.getX() + vec3.x, this.getY() + (double) localY, this.getZ() + vec3.z);
            passenger.setPos(this.getX() + vec3.x, this.getY() + (double) localY, this.getZ() + vec3.z);

            if (!this.level().isClientSide() && passenger instanceof VehiclePartEntity) {
                passenger.setYRot(this.getYRot());
            }
        }
    }

    protected Vec3 positionVehiclePartEntityLocally(float localX, float localY, float localZ) {
        return (new Vec3(localX, 0, localZ)).yRot(
                -this.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
    }

    protected void clampRotation(final Entity entity) {
        entity.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        entity.yRotO += f1 - f;
        entity.setYRot(entity.getYRot() + f1 - f);
        entity.setYHeadRot(entity.getYRot());
    }

    @Override
    protected void checkFallDamage(final double fallDistance, final boolean onGround, final BlockState blockState,
            final BlockPos blockPos) {
        this.lastYd = this.getDeltaMovement().y;
        if (this.isPassenger()) return;

        if (!onGround) {
            if (!this.level().getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && fallDistance < 0) {
                this.fallDistance -= (float) fallDistance;
            }
            return;
        }

        if (this.fallDistance > 3) {

            if (this.status != Status.ON_LAND) {
                this.resetFallDistance();
                return;
            }

            this.causeFallDamage(this.fallDistance, 1, this.damageSources().fall());
            if (!this.level().isClientSide && !this.isRemoved()) {
                for (Entity passenger : this.getTruePassengers()) {
                    passenger.causeFallDamage(this.fallDistance, 1, this.damageSources().fall());
                }
                for (Entity passenger : this.getPassengers()) {
                    if (passenger.isVehicle()) {
                        passenger.getFirstPassenger().kill();
                    }
                }
                this.kill();
                if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                    this.spawnAtLocation(this.getDropItem());
                }
            }
        }

        this.resetFallDistance();
    }

    public boolean getPaddleState(final int side) {
        return this.entityData.<Boolean>get(
                side == 0 ? DATA_ID_PADDLE_LEFT : DATA_ID_PADDLE_RIGHT) && this.getControllingPassenger() != null;
    }

    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    public void setDamage(final float damage) {
        this.entityData.set(DATA_ID_DAMAGE, damage);
    }

    public int getHurtTime() {
        return this.entityData.get(DATA_ID_HURT);
    }

    public void setHurtTime(final int hurtTime) {
        this.entityData.set(DATA_ID_HURT, hurtTime);
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getHurtDir() {
        return this.entityData.get(DATA_ID_HURTDIR);
    }

    /**
     * Sets the forward direction of the entity.
     */
    public void setHurtDir(final int hurtDirection) {
        this.entityData.set(DATA_ID_HURTDIR, hurtDirection);
    }

    /*
    @Override
    public Vec3 getDeltaMovement() {
        return new Vec3(this.entityData.get(DATA_ID_DELTA_MOVEMENT));
    }

    public Vector3f getDeltaMovementFloat() {
        return this.entityData.get(DATA_ID_DELTA_MOVEMENT);
    }


    @Override
    public void setDeltaMovement(Vec3 pDeltaMovement) {
        Vector3f deltaMovement = new Vector3f((float) pDeltaMovement.x, (float) pDeltaMovement.y, (float) pDeltaMovement.z);
        this.entityData.set(DATA_ID_DELTA_MOVEMENT, deltaMovement);
    }

    @Override
    public void addDeltaMovement(Vec3 pAddend) {
        this.setDeltaMovement(this.getDeltaMovement().add(pAddend));
    }

    @Override
    public void setDeltaMovement(double pX, double pY, double pZ) {
        this.setDeltaMovement(new Vec3(pX, pY, pZ));
    }

     */

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        double x = pCompound.getDouble("dataMovementX");
        double y = pCompound.getDouble("dataMovementY");
        double z = pCompound.getDouble("dataMovementZ");
        this.setDeltaMovement(x,y,z);
        this.updateWind(false);
    }



    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putDouble("dataMovementX", this.getDeltaMovement().x);
        pCompound.putDouble("dataMovementY", this.getDeltaMovement().y);
        pCompound.putDouble("dataMovementZ", this.getDeltaMovement().z);
    }

    @Override
    protected boolean canAddPassenger(final Entity passenger) {
        return this.getPassengers().size() < this.getMaxPassengers() && !this.isRemoved() && passenger instanceof VehiclePartEntity;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getPilotVehiclePartAsEntity();
        if (entity instanceof VehiclePartEntity vehiclePart) {
            entity = vehiclePart.getFirstPassenger();
            if (entity instanceof EmptyCompartmentEntity emptyCompartmentEntity) {
                entity = emptyCompartmentEntity.getControllingPassenger();
            }
        }

        final LivingEntity livingentity1;
        if (entity instanceof LivingEntity livingentity) {
            livingentity1 = livingentity;
        } else {
            livingentity1 = null;
        }

        return livingentity1;
    }

    @Nullable
    public EmptyCompartmentEntity getControllingCompartment() {
        /*
        if(!this.level().isClientSide()){
            return null;
        }*/
        final Entity vehiclePart = this.getPilotVehiclePartAsEntity();

        if (!(vehiclePart instanceof VehiclePartEntity) || !vehiclePart.isVehicle())
        {
            return null;
        }

        if (!(vehiclePart.getFirstPassenger() instanceof EmptyCompartmentEntity emptyCompartmentEntity))
        {
            return null;
        }

        /*
        if (!emptyCompartmentEntity.isVehicle() || !(emptyCompartmentEntity.getFirstPassenger() instanceof Player))
        {
            return null;
        }*/

        return emptyCompartmentEntity;
    }

    @Nullable
    public Entity getPilotVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getMaxPassengers()) {
            return this.getPassengers().get(0);
        }
        return null;
    }

    @Override
    public boolean isUnderWater() {
        return this.status == Status.UNDER_WATER || this.status == Status.UNDER_FLOWING_WATER;
    }


    //TODO remove
    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (this.isControlledByLocalInstance() && this.lerpSteps > 0) {
            this.lerpSteps = 0;
            this.absMoveTo(this.lerpX, this.lerpY, this.lerpZ, (float) this.lerpYRot, (float) this.lerpXRot);
        }
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(this.getDropItem());
    }
}