package com.hyperdash.firmaciv.entity.custom;

import com.google.common.collect.Lists;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities.AbstractCompartmentEntity;
import com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities.EmptyCompartmentEntity;
import com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities.VehiclePartEntity;
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
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
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
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FirmacivBoatEntity extends Entity {
    public static final int PADDLE_LEFT = 0;
    public static final int PADDLE_RIGHT = 1;
    public static final double PADDLE_SOUND_TIME = Math.PI / 4;
    public static final int BUBBLE_TIME = 60;
    protected static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(FirmacivBoatEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(FirmacivBoatEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(FirmacivBoatEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(FirmacivBoatEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_LEFT = SynchedEntityData.defineId(FirmacivBoatEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_RIGHT = SynchedEntityData.defineId(FirmacivBoatEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_ID_BUBBLE_TIME = SynchedEntityData.defineId(FirmacivBoatEntity.class, EntityDataSerializers.INT);
    protected static final int TIME_TO_EJECT = 60;
    protected static final float PADDLE_SPEED = ((float) Math.PI / 8F);
    public final int PASSENGER_NUMBER = 2;
    protected final float DAMAGE_THRESHOLD = 80.0f;
    protected final float DAMAGE_RECOVERY = 0.5f;
    protected final float[] paddlePositions = new float[2];
    protected List<VehiclePartEntity> vehicleParts = new ArrayList<>();
    protected float invFriction;
    protected float outOfControlTicks;
    protected float turnOutOfControlTicks;
    protected float deltaRotation;
    protected int lerpSteps;
    protected double lerpX;
    protected double lerpY;
    protected double lerpZ;
    protected double lerpYRot;
    protected double lerpXRot;
    protected double waterLevel;
    protected float landFriction;
    protected Status status;
    protected Status oldStatus;
    protected double lastYd;
    protected boolean isAboveBubbleColumn;
    protected boolean bubbleColumnDirectionIsDown;
    protected float bubbleMultiplier;
    protected float bubbleAngle;
    protected float bubbleAngleO;
    private int spawnTicks = 0;

    public FirmacivBoatEntity(final EntityType<? extends FirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
        spawnTicks = 0;
    }

    public static boolean canVehicleCollide(final Entity vehicle, final Entity entity) {
        return (entity.canBeCollidedWith() || entity.isPushable()) && !vehicle.isPassengerOfSameVehicle(entity);
    }

    public int getPassengerNumber() {
        return PASSENGER_NUMBER;
    }

    protected int getMaxPassengers(){return this.getPassengerNumber();}

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

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_HURT, 0);
        this.entityData.define(DATA_ID_HURTDIR, 1);
        this.entityData.define(DATA_ID_DAMAGE, 0.0F);
        this.entityData.define(DATA_ID_TYPE, Boat.Type.OAK.ordinal());
        this.entityData.define(DATA_ID_PADDLE_LEFT, false);
        this.entityData.define(DATA_ID_PADDLE_RIGHT, false);
        this.entityData.define(DATA_ID_BUBBLE_TIME, 0);
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
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected Vec3 getRelativePortalPosition(final Direction.Axis axis, final BlockUtil.FoundRectangle portal) {
        return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(axis, portal));
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
    public void onAboveBubbleCol(final boolean downwards) {
        if (!this.level().isClientSide) {
            this.isAboveBubbleColumn = true;
            this.bubbleColumnDirectionIsDown = downwards;
            if (this.getBubbleTime() == 0) {
                this.setBubbleTime(60);
            }
        }

        this.level().addParticle(ParticleTypes.SPLASH, this.getX() + this.random.nextDouble(), this.getY() + 0.7, this.getZ() + this.random.nextDouble(), 0, 0, 0);
        if (this.random.nextInt(20) == 0) {
            this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimSplashSound(), this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat(), false);
        }

        this.gameEvent(GameEvent.SPLASH, this.getControllingPassenger());
    }

    @Override
    public void push(final Entity entity) {
        if (entity instanceof FirmacivBoatEntity) {
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
     * Setups the entity to do the hurt animation. Only used by packets in multiplayer.
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
    public void lerpTo(final double posX, final double posY, final double posZ, final float yaw, final float pitch, final int pPosRotationIncrements, final boolean teleport) {
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

        if (this.getControllingPassenger() == null) {
            this.deltaRotation = 0;
        }

        this.oldStatus = this.status;
        this.status = this.getStatus();
        if (this.status != FirmacivBoatEntity.Status.UNDER_WATER && this.status != FirmacivBoatEntity.Status.UNDER_FLOWING_WATER) {
            this.outOfControlTicks = 0.0F;
        } else {
            ++this.outOfControlTicks;
        }


        if (!this.level().isClientSide) {
            if (this.getPassengers().size() < this.getPassengerNumber()) {
                final VehiclePartEntity newPart = FirmacivEntities.VEHICLE_PART_ENTITY.get().create(this.level());
                newPart.setPos(this.getX(), this.getY(), this.getZ());
                this.level().addFreshEntity(newPart);
                newPart.startRiding(this);
            }
        }


        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - getDamageRecovery());
        }

        super.tick();

        if (this.status == Status.IN_WATER && !this.getPassengers().isEmpty()) {
            if (Math.abs(this.deltaRotation) > 2) {
                this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double) this.random.nextFloat(), this.getY() + 0.7D, this.getZ() + (double) this.random.nextFloat(), 0.0D, 0.0D, 0.0D);
                if (this.random.nextInt(20) == 0) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimSound(), this.getSoundSource(), 0.2F, 0.8F + 0.4F * this.random.nextFloat(), false);
                }
                if (this.getControllingCompartment() != null && Math.abs(this.deltaRotation) > 5 && (this.getControllingCompartment().getInputRight() || this.getControllingCompartment().getInputLeft())) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimHighSpeedSplashSound(), this.getSoundSource(), 0.2F, 0.8F + 0.4F * this.random.nextFloat(), false);


                    Vec3 splashOffset = this.getDeltaMovement().yRot(45);
                    if (this.getControllingCompartment().getInputLeft()) {
                        splashOffset = this.getDeltaMovement().yRot(-45);
                    }
                    splashOffset.normalize();

                    for (int i = 0; i < 8; i++) {
                        this.level().addParticle(ParticleTypes.BUBBLE_POP, this.getX() + (double) this.random.nextFloat() + splashOffset.x * 2 + this.getDeltaMovement().x * i, this.getY() + 0.7D, this.getZ() + (double) this.random.nextFloat() + splashOffset.z * 2 + this.getDeltaMovement().x * i, 0.0D, 0.0D, 0.0D);
                        this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double) this.random.nextFloat() + splashOffset.x * 2 + this.getDeltaMovement().x * i, this.getY() + 0.7D, this.getZ() + (double) this.random.nextFloat() + splashOffset.z * 2 + this.getDeltaMovement().x * i, 0.0D, 0.0D, 0.0D);
                    }
                }

            } else if (this.getDeltaMovement().length() > 0.10) {
                if (this.random.nextInt(8) == 0) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimSound(), this.getSoundSource(), 0.1F, 0.8F + 0.4F * this.random.nextFloat(), false);
                    this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double) this.random.nextFloat(), this.getY() + 0.7D, this.getZ() + (double) this.random.nextFloat(), 0.0D, 0.0D, 0.0D);
                }
            }
        }

        this.tickLerp();
        if (this.isControlledByLocalInstance()) {
            if (!(this.getFirstPassenger() instanceof Player)) {
                this.setPaddleState(false, false);
            }

            this.floatBoat();
            if (this.level().isClientSide) {
                this.controlBoat();
                this.level().sendPacketToServer(new ServerboundPaddleBoatPacket(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            this.setDeltaMovement(Vec3.ZERO);
            this.deltaRotation = 0f;
        }

        this.tickBubbleColumn();

        for (int i = 0; i <= 1; ++i) {
            if (this.getPaddleState(i)) {
                if (!this.isSilent() && (double) (this.paddlePositions[i] % ((float) Math.PI * 2F)) <= (double) ((float) Math.PI / 4F) && (double) ((this.paddlePositions[i] + ((float) Math.PI / 8F)) % ((float) Math.PI * 2F)) >= (double) ((float) Math.PI / 4F)) {
                    SoundEvent soundevent = this.getPaddleSound();
                    if (soundevent != null) {
                        Vec3 vec3 = this.getViewVector(1.0F);
                        double d0 = i == 1 ? -vec3.z : vec3.z;
                        double d1 = i == 1 ? vec3.x : -vec3.x;
                        this.level().playSound(null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent, this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat());
                        this.level().gameEvent(this.getControllingPassenger(), GameEvent.SPLASH, new BlockPos((int) (this.getX() + d0), (int) this.getY(), (int) (this.getZ() + d1)));
                    }
                }

                this.paddlePositions[i] += ((float) Math.PI / 8F);
            } else {
                this.paddlePositions[i] = 0.0F;
            }
        }

        this.checkInsideBlocks();
        final List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(0.2F, -0.01F, 0.2F), EntitySelector.pushableBy(this));
        if (!list.isEmpty()) {
            boolean flag = !this.level().isClientSide && !(this.getControllingPassenger() instanceof Player);

        }
    }

    protected float getXForPart(float yaw, float degree) {
        return Mth.sin((float) (yaw + Math.toRadians(degree)));
    }

    protected float getZForPart(float yaw, float degree) {
        return -Mth.cos((float) (yaw + Math.toRadians(degree)));
    }

    protected void tickBubbleColumn() {
        if (this.level().isClientSide) {
            int i = this.getBubbleTime();
            if (i > 0) {
                this.bubbleMultiplier += 0.05F;
            } else {
                this.bubbleMultiplier -= 0.1F;
            }

            this.bubbleMultiplier = Mth.clamp(this.bubbleMultiplier, 0.0F, 1.0F);
            this.bubbleAngleO = this.bubbleAngle;
            this.bubbleAngle = 10.0F * (float) Math.sin(0.5F * (float) this.level().getGameTime()) * this.bubbleMultiplier;
        } else {
            if (!this.isAboveBubbleColumn) {
                this.setBubbleTime(0);
            }

            int k = this.getBubbleTime();
            if (k > 0) {
                --k;
                this.setBubbleTime(k);
                int j = 60 - k - 1;
                if (j > 0 && k == 0) {
                    this.setBubbleTime(0);
                    Vec3 vec3 = this.getDeltaMovement();
                    if (this.bubbleColumnDirectionIsDown) {
                        this.setDeltaMovement(vec3.add(0.0D, -0.7D, 0.0D));
                        this.ejectPassengers();
                    } else {
                        this.setDeltaMovement(vec3.x, this.hasPassenger((p_150274_) -> {
                            return p_150274_ instanceof Player;
                        }) ? 2.7D : 0.6D, vec3.z);
                    }
                }

                this.isAboveBubbleColumn = false;
            }
        }

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
        return this.getPaddleState(pSide) ? Mth.clampedLerp(this.paddlePositions[pSide] - ((float) Math.PI / 8F), this.paddlePositions[pSide], pLimbSwing) : 0.0F;
    }

    /**
     * Determines whether the boat is in water, gliding on land, or in air
     */
    protected FirmacivBoatEntity.Status getStatus() {
        final FirmacivBoatEntity.Status firmacivboat$status = this.isUnderwater();
        if (firmacivboat$status != null) {
            this.waterLevel = this.getBoundingBox().maxY;
            return firmacivboat$status;
        }

        if (this.checkInWater()) {
            return FirmacivBoatEntity.Status.IN_WATER;
        }

        final float groundFriction = this.getGroundFriction();
        if (0 < groundFriction) {
            this.landFriction = groundFriction;
            return FirmacivBoatEntity.Status.ON_LAND;
        }

        return FirmacivBoatEntity.Status.IN_AIR;
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

    /**
     * Decides how much the boat should be gliding on the land (based on any slippery blocks)
     */
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
                            if (!(blockstate.getBlock() instanceof WaterlilyBlock) && Shapes.joinIsNotEmpty(blockstate.getCollisionShape(this.level(), blockpos$mutableblockpos).move(l1, k2, i2), voxelshape, BooleanOp.AND)) {
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

    /**
     * Decides whether the boat is currently underwater.
     */
    @Nullable
    protected FirmacivBoatEntity.Status isUnderwater() {
        AABB aabb = this.getBoundingBox();
        double d0 = aabb.maxY + 0.001D;
        int i = Mth.floor(aabb.minX);
        int j = Mth.ceil(aabb.maxX);
        int k = Mth.floor(aabb.maxY);
        int l = Mth.ceil(d0);
        int i1 = Mth.floor(aabb.minZ);
        int j1 = Mth.ceil(aabb.maxZ);
        boolean flag = false;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    blockpos$mutableblockpos.set(k1, l1, i2);
                    FluidState fluidstate = this.level().getFluidState(blockpos$mutableblockpos);
                    if (fluidstate.is(FluidTags.WATER) && d0 < (double) ((float) blockpos$mutableblockpos.getY() + fluidstate.getHeight(this.level(), blockpos$mutableblockpos))) {
                        if (!fluidstate.isSource()) {
                            return FirmacivBoatEntity.Status.UNDER_FLOWING_WATER;
                        }

                        flag = true;
                    }
                }
            }
        }

        return flag ? FirmacivBoatEntity.Status.UNDER_WATER : null;
    }

    /**
     * Update the boat's speed, based on momentum.
     */
    protected void floatBoat() {
        double d0 = -0.04F;
        double d1 = this.isNoGravity() ? 0.0D : (double) -0.04F;
        double d2 = 0.0D;
        this.invFriction = 0.05F;
        if (this.oldStatus == FirmacivBoatEntity.Status.IN_AIR && this.status != FirmacivBoatEntity.Status.IN_AIR && this.status != FirmacivBoatEntity.Status.ON_LAND) {
            this.waterLevel = this.getY(1.0D);
            this.setPos(this.getX(), (double) (this.getWaterLevelAbove() - this.getBbHeight()) + 0.101D, this.getZ());
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
            this.lastYd = 0.0D;
            this.status = FirmacivBoatEntity.Status.IN_WATER;
        } else {
            if (this.status == FirmacivBoatEntity.Status.IN_WATER) {
                d2 = (this.waterLevel - this.getY()) / (double) this.getBbHeight();
                this.invFriction = 0.9F;
            } else if (this.status == FirmacivBoatEntity.Status.UNDER_FLOWING_WATER) {
                d1 = -7.0E-4D;
                this.invFriction = 0.9F;
            } else if (this.status == FirmacivBoatEntity.Status.UNDER_WATER) {
                d2 = 0.01F;
                this.invFriction = 0.45F;
            } else if (this.status == FirmacivBoatEntity.Status.IN_AIR) {
                this.invFriction = 0.9F;
            } else if (this.status == FirmacivBoatEntity.Status.ON_LAND) {
                this.invFriction = this.landFriction;
                if (invFriction > 0.5F) {
                    invFriction = 0.5F;
                }
                if (this.getControllingPassenger() instanceof Player) {
                    this.landFriction /= 2.0F;
                }
            }

            Vec3 vec3 = this.getDeltaMovement();


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


            this.setDeltaMovement(vec3.x * (double) this.invFriction, vec3.y + d1, vec3.z * (double) this.invFriction);

            if (this.getControllingCompartment() != null) {
                double turnSpeedFactor = this.getDeltaMovement().length() * 12.0F;

                if (this.getControllingCompartment().getInputLeft() || this.getControllingCompartment().getInputRight()) {
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

    public final List<Entity> getTruePassengers() {
        final List<Entity> truePassengers = Lists.newArrayList();

        for (final Entity vehiclePart : this.getPassengers()) {
            if (vehiclePart.isVehicle() && vehiclePart.getFirstPassenger() instanceof AbstractCompartmentEntity abstractCompartmentEntity) {
                if (abstractCompartmentEntity.isVehicle()) {
                    truePassengers.add(abstractCompartmentEntity.getFirstPassenger());
                }
            }
        }
        return truePassengers;
    }

    protected void controlBoat() {
        if (this.isVehicle()) {
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

                this.setDeltaMovement(this.getDeltaMovement().add(Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * f, 0.0D, Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * f));
                this.setPaddleState(inputRight && !inputLeft || inputUp, inputLeft && !inputRight || inputUp);
            }
        }
    }


    @Override
    protected void positionRider(final Entity passenger, final Entity.MoveFunction moveFunction) {
        if (this.hasPassenger(passenger)) {
            float f = 0.0F;
            float f1 = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
            if (this.getPassengers().size() > 1) {
                if (this.getPassengers().indexOf(passenger) == 0) {
                    f = 0.3F;
                } else {
                    f = -0.7F;
                }

            }
            final Vec3 vec3 = (new Vec3(f, 0, 0)).yRot(-this.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
            moveFunction.accept(passenger, this.getX() + vec3.x, this.getY() + (double) f1, this.getZ() + vec3.z);
            passenger.setPos(this.getX() + vec3.x, this.getY() + (double) f1, this.getZ() + vec3.z);
            if (!this.level().isClientSide() && passenger instanceof VehiclePartEntity) {
                passenger.setYRot(this.getYRot());
            } else if (!(passenger instanceof VehiclePartEntity)){
                super.positionRider(passenger, moveFunction);
            }
        }
    }

    protected Vec3 positionVehiclePartEntityLocally(float localX, float localY, float localZ){
        Vec3 vec3 = (new Vec3(localX, 0, localZ)).yRot(-this.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));

        return vec3;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(final LivingEntity passenger) {
        final Vec3 vec3 = getCollisionHorizontalEscapeVector(this.getBbWidth() * Mth.SQRT_OF_TWO, passenger.getBbWidth(), passenger.getYRot());
        final double d0 = this.getX() + vec3.x;
        final double d1 = this.getZ() + vec3.z;
        final BlockPos blockpos = BlockPos.containing(d0, this.getBoundingBox().maxY, d1);
        final BlockPos blockpos1 = blockpos.below();
        if (!this.level().isWaterAt(blockpos1)) {
            final List<Vec3> list = Lists.newArrayList();
            final double d2 = this.level().getBlockFloorHeight(blockpos);
            if (DismountHelper.isBlockFloorValid(d2)) {
                list.add(new Vec3(d0, (double) blockpos.getY() + d2, d1));
            }

            final double d3 = this.level().getBlockFloorHeight(blockpos1);
            if (DismountHelper.isBlockFloorValid(d3)) {
                list.add(new Vec3(d0, (double) blockpos1.getY() + d3, d1));
            }

            for (final Pose pose : passenger.getDismountPoses()) {
                for (final Vec3 vec31 : list) {
                    if (DismountHelper.canDismountTo(this.level(), vec31, passenger, pose)) {
                        passenger.setPose(pose);
                        return vec31;
                    }
                }
            }
        }

        return super.getDismountLocationForPassenger(passenger);
    }

    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    protected void clampRotation(final Entity entity) {
        entity.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        entity.yRotO += f1 - f;
        entity.setYRot(entity.getYRot() + f1 - f);
        entity.setYHeadRot(entity.getYRot());
    }

    @Override
    public void onPassengerTurned(Entity pEntityToUpdate) {
        this.clampRotation(pEntityToUpdate);
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
                this.kill();
                if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                    for (int i = 0; i < 3; ++i) {
                        this.spawnAtLocation(this.getBoatType().getPlanks());
                    }

                    for (int j = 0; j < 2; ++j) {
                        this.spawnAtLocation(Items.STICK);
                    }
                }
            }
        }

        this.resetFallDistance();
    }

    public boolean getPaddleState(final int side) {
        return this.entityData.<Boolean>get(side == 0 ? DATA_ID_PADDLE_LEFT : DATA_ID_PADDLE_RIGHT) && this.getControllingPassenger() != null;
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamage(final float damage) {
        this.entityData.set(DATA_ID_DAMAGE, damage);
    }

    /**
     * Gets the time since the last hit.
     */
    public int getHurtTime() {
        return this.entityData.get(DATA_ID_HURT);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setHurtTime(final int hurtTime) {
        this.entityData.set(DATA_ID_HURT, hurtTime);
    }

    protected int getBubbleTime() {
        return this.entityData.get(DATA_ID_BUBBLE_TIME);
    }

    private void setBubbleTime(final int bubbleTime) {
        this.entityData.set(DATA_ID_BUBBLE_TIME, bubbleTime);
    }

    public float getBubbleAngle(final float partialTicks) {
        return Mth.lerp(partialTicks, this.bubbleAngleO, this.bubbleAngle);
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

    public void setType(final Boat.Type boatType) {
        this.entityData.set(DATA_ID_TYPE, boatType.ordinal());
    }

    public Boat.Type getBoatType() {
        return Boat.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    protected boolean canAddPassenger(final Entity passenger) {
        return this.getPassengers().size() < this.getPassengerNumber() && !this.isRemoved();
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
        final Entity vehiclePart = this.getPilotVehiclePartAsEntity();

        if (!(vehiclePart instanceof VehiclePartEntity) || !vehiclePart.isVehicle()) return null;

        if (!(vehiclePart.getFirstPassenger() instanceof EmptyCompartmentEntity emptyCompartmentEntity))
            return null;

        if (!emptyCompartmentEntity.isVehicle() || !(emptyCompartmentEntity.getFirstPassenger() instanceof LocalPlayer))
            return null;

        return emptyCompartmentEntity;
    }

    @Nullable
    public Entity getPilotVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getPassengerNumber()) {
            return this.getPassengers().get(1);
        }
        return null;
    }

    @Override
    public boolean isUnderWater() {
        return this.status == Status.UNDER_WATER || this.status == Status.UNDER_FLOWING_WATER;
    }

    // Forge: Fix MC-119811 by instantly completing lerp on board
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

    public enum Status {
        IN_WATER,
        UNDER_WATER,
        UNDER_FLOWING_WATER,
        ON_LAND,
        IN_AIR
    }
}