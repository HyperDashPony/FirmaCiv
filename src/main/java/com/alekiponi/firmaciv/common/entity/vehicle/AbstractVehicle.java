package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCollisionEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.AbstractVehiclePart;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.dries007.tfc.common.TFCTags;
import net.minecraft.BlockUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
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
import java.util.Random;
import java.util.stream.Collectors;

public abstract class AbstractVehicle extends net.minecraft.world.entity.Entity {
    protected static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(
            AbstractVehicle.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(
            AbstractVehicle.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(
            AbstractVehicle.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> DATA_ID_DELTA_ROTATION = SynchedEntityData.defineId(
            AbstractVehicle.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> DATA_ID_ACCELERATION = SynchedEntityData.defineId(
            AbstractVehicle.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<ItemStack> DATA_ID_PAINT = SynchedEntityData.defineId(AbstractVehicle.class,
            EntityDataSerializers.ITEM_STACK);


    private float randomRotation;

    public final int MAX_PASSENGER_NUMBER = 0;

    public final int[] CLEATS = {};

    public final int[] COLLIDERS = {};

    public final int[] CAN_ADD_ONLY_BLOCKS = {};

    public final int[][] COMPARTMENT_ROTATIONS = {};
    protected final float DAMAGE_THRESHOLD = 80.0f;
    protected final float PASSENGER_SIZE_LIMIT = 0.9F;
    protected final float DAMAGE_RECOVERY = 2.0f;

    protected float invFriction;
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

    private ImmutableList<net.minecraft.world.entity.Entity> passengers = ImmutableList.of();

    public AbstractVehicle(final EntityType entityType, final Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
        this.randomRotation = 0;
    }

    public abstract int getMaxPassengers();

    public abstract int[] getCleatIndices();

    public abstract int[] getColliderIndices();

    public abstract int[] getCanAddOnlyBlocksIndices();

    public ArrayList<VehicleCleatEntity> getCleats(){
        ArrayList<VehicleCleatEntity> list = new ArrayList<VehicleCleatEntity>();
        if(this.getPassengers().size() == this.getMaxPassengers()) {
            for (int i : this.getCleatIndices()) {
                if (this.getPassengers().get(i).getFirstPassenger() instanceof VehicleCleatEntity cleat) {
                    list.add(cleat);
                }
            }
        }
        return list;
    }



    public ArrayList<VehicleCollisionEntity> getColliders(){
        ArrayList<VehicleCollisionEntity> list = new ArrayList<VehicleCollisionEntity>();
        if(this.getPassengers().size() == this.getMaxPassengers()) {
            for (int i : this.getColliderIndices()) {
                if (this.getPassengers().get(i).getFirstPassenger() instanceof VehicleCollisionEntity collider) {
                    list.add(collider);
                }
            }
        }
        return list;
    }

    public ArrayList<AbstractCompartmentEntity> getCanAddOnlyBlocks(){
        ArrayList<AbstractCompartmentEntity> list = new ArrayList<AbstractCompartmentEntity>();
        if(this.getPassengers().size() == this.getMaxPassengers()) {
            for (int i : this.getCanAddOnlyBlocksIndices()) {
                if (this.getPassengers().get(i).getFirstPassenger() instanceof AbstractCompartmentEntity compartment) {
                    list.add(compartment);
                }
            }
        }
        return list;
    }


    public abstract int getCompartmentRotation(int i);

    public abstract float getPassengerSizeLimit();

    public abstract int[][] getCompartmentRotationsArray();



    @Override
    protected float getEyeHeight(final Pose pose, final EntityDimensions entityDimensions) {
        return entityDimensions.height;
    }

    @Override
    protected net.minecraft.world.entity.Entity.MovementEmission getMovementEmission() {
        return net.minecraft.world.entity.Entity.MovementEmission.EVENTS;
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_HURT, 0);
        this.entityData.define(DATA_ID_HURTDIR, 1);
        this.entityData.define(DATA_ID_DAMAGE, 0.0F);
        this.entityData.define(DATA_ID_DELTA_ROTATION, 0f);
        this.entityData.define(DATA_ID_ACCELERATION, 0f);
        this.entityData.define(DATA_ID_PAINT, ItemStack.EMPTY);
    }

    @Override
    public boolean canCollideWith(final net.minecraft.world.entity.Entity other) {
        return canVehicleCollide(this, other);
    }

    public static boolean canVehicleCollide(final net.minecraft.world.entity.Entity vehicle, final net.minecraft.world.entity.Entity entity) {
        return (entity.canBeCollidedWith() || entity.isPushable()) && !vehicle.isPassengerOfSameVehicle(entity);
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

    @Nullable
    public net.minecraft.world.entity.Entity getCompAsEntityFromIndex(int index) {
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
        this.setDamage(this.getDamage() + amount);
        this.markHurt();
        this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
        final boolean instantKill = damageSource.getEntity() instanceof Player && ((Player) damageSource.getEntity()).getAbilities().instabuild;

        if (instantKill) {
            if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                this.spawnAtLocation(this.getDropItem());
            }
            this.discard();
        }
        if (this.getDamage() > getDamageThreshold()) {
            for(Entity entity : passengers){
                entity.kill();
            }
        }

        return true;
    }

    @Override
    public void push(final net.minecraft.world.entity.Entity entity) {
        if (entity instanceof AbstractVehicle) {
            if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.push(entity);
            }
        } else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.push(entity);
        }
    }

    public Item getDropItem() {
        return Items.AIR;
    }

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

    protected void tickDestroyPlants(){
        BlockPos blockPos = this.blockPosition();
        int size = (int) Math.ceil(this.getBoundingBox().getXsize());
        if(size % 2 != 0){
            size++;
        }
        size = size/2;
        for(int x = -size;x <=size; x++){
            for(int z = -size;z <=size; z++){
                for(int y = 0; y <=1; y++){
                    if(this.level().getBlockState(blockPos.offset(x,y,z)).is(TFCTags.Blocks.PLANTS)){
                        this.level().destroyBlock(blockPos.offset(x,y,z),false);
                    }
                }

            }
        }

    }

    protected void tickTakeEntitiesForARide(){
        if(this instanceof AbstractFirmacivBoatEntity && (this.status == Status.UNDER_WATER || this.status == Status.UNDER_FLOWING_WATER)){
            return;
        }
        if(this.getDeltaMovement().length() > 0.01){
            List<net.minecraft.world.entity.Entity> entitiesToTakeWith = this.level()
                    .getEntities(this, this.getBoundingBox().inflate(0, -this.getBoundingBox().getYsize() + 2, 0).move(0, this.getBoundingBox().getYsize(), 0), EntitySelector.NO_SPECTATORS);

            for (VehicleCollisionEntity collider : this.getColliders()) {
                entitiesToTakeWith.addAll(this.level()
                        .getEntities(collider, collider.getBoundingBox().inflate(0, -collider.getBoundingBox().getYsize() + 2, 0).move(0, collider.getBoundingBox().getYsize(), 0), EntitySelector.NO_SPECTATORS));
            }

            entitiesToTakeWith = entitiesToTakeWith.stream().distinct().collect(Collectors.toList());

            if (!entitiesToTakeWith.isEmpty()) {
                for (final net.minecraft.world.entity.Entity entity : entitiesToTakeWith) {
                    if (entity instanceof LocalPlayer player && !entity.isPassenger()) {
                        if (this.level().isClientSide()) {
                            if (!player.input.jumping) {
                                player.move(MoverType.SELF, this.getDeltaMovement().multiply(1, 0, 1));
                            }
                            player.setDeltaMovement(player.getDeltaMovement().multiply(0.9, 1, 0.9));
                        }
                    } else if (!(entity instanceof AbstractFirmacivBoatEntity) && !entity.isPassenger()) {
                        entity.setDeltaMovement(entity.getDeltaMovement().add(this.getDeltaMovement().multiply(0.45, 0, 0.45)));
                    }
                }
            }
        }
    }

    protected abstract void tickCleatInput();
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
        }


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

    public final List<net.minecraft.world.entity.Entity> getTruePassengers() {
        final List<net.minecraft.world.entity.Entity> truePassengers = Lists.newArrayList();

        for (final net.minecraft.world.entity.Entity vehiclePart : this.getPassengers()) {
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

        for (final net.minecraft.world.entity.Entity vehiclePart : this.getPassengers()) {
            if (vehiclePart.isVehicle() && vehiclePart.getFirstPassenger() instanceof AbstractCompartmentEntity compartmentEntity) {
                compartments.add(compartmentEntity);
            }
        }
        return compartments;
    }

    public final List<AbstractVehiclePart> getVehicleParts() {
        final List<AbstractVehiclePart> vehicleParts = Lists.newArrayList();

        for (final net.minecraft.world.entity.Entity vehiclePart : this.getPassengers()) {
            vehicleParts.add((AbstractVehiclePart) vehiclePart);
        }
        return vehicleParts;
    }

    public boolean isBeingTowed() {
        if (this.getPassengers().size() == this.getMaxPassengers()) {
            for (int i : this.getCleatIndices()) {
                if (this.getPassengers().get(i).getFirstPassenger() instanceof VehicleCleatEntity vehicleCleat) {
                    return vehicleCleat.isLeashed() && this.getDeltaMovement().length() != 0;
                }
            }
        }
        return false;
    }

    @Override
    protected void positionRider(final net.minecraft.world.entity.Entity passenger, final net.minecraft.world.entity.Entity.MoveFunction moveFunction) {
        if (this.hasPassenger(passenger)) {
            if (!(passenger instanceof AbstractVehiclePart)) {
                passenger.stopRiding();
            }

            final Vec3 position = positionRiderByIndex(this.getPassengers().indexOf(passenger));
            final Vec3 vec3 = this.positionLocally((float) position.x, (float)position.y, (float)position.z);
            moveFunction.accept(passenger, this.getX() + vec3.x, this.getY() + position.y, this.getZ() + vec3.z);
            passenger.setPos(this.getX() + vec3.x, this.getY() + position.y, this.getZ() + vec3.z);
            passenger.setYRot(this.getYRot());
        }
    }

    protected Vec3 positionRiderByIndex(int index){
        float localX = 0.0F;
        float localZ = 0.0F;
        float localY = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()));
        switch (index) {
            case 0 -> {
                localX = 0.3f;
                localZ = 0.0f;
                localY += 0.0f;
            }
            case 1 -> {
                localX = -0.7f;
                localZ = 0.0f;
                localY += 0.0f;
            }
        }
        return new Vec3(localX, localY, localZ);
    }

    protected Vec3 positionLocally(float localX, float localY, float localZ) {
        return (new Vec3(localX, 0, localZ)).yRot(
                -this.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
    }

    protected void clampRotation(final net.minecraft.world.entity.Entity entity) {
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
                for (net.minecraft.world.entity.Entity passenger : this.getTruePassengers()) {
                    passenger.causeFallDamage(this.fallDistance, 1, this.damageSources().fall());
                }
                for (net.minecraft.world.entity.Entity passenger : this.getPassengers()) {
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

    public int getHurtDir() {
        return this.entityData.get(DATA_ID_HURTDIR);
    }

    public void setHurtDir(final int hurtDirection) {
        this.entityData.set(DATA_ID_HURTDIR, hurtDirection);
    }

    protected abstract float getDamageThreshold();

    protected abstract float getDamageRecovery();

    public void setDeltaRotation(float deltaRotation) {
        this.entityData.set(DATA_ID_DELTA_ROTATION, deltaRotation);
    }

    public float getDeltaRotation() {
        return this.entityData.get(DATA_ID_DELTA_ROTATION);
    }

    public void setAcceleration(float acceleration) {
        this.entityData.set(DATA_ID_ACCELERATION, Mth.clamp(acceleration, -1, 1));
    }

    public float getAcceleration() {
        return this.entityData.get(DATA_ID_ACCELERATION);
    }

    public float getRandomRotation(){
        if(this.getDamage() > this.getDamageThreshold()){
            return randomRotation;
        } else {
            return 1.0f;
        }

    }

    public void setRandomRotation(float rotation){
        randomRotation = rotation;
    }

    public ItemStack getPaint() {
        return this.entityData.get(DATA_ID_PAINT);
    }

    public void setPaint(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_PAINT, itemStack.copy());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setDeltaRotation(pCompound.getFloat("deltaRotation"));
        this.setPaint(ItemStack.of(pCompound.getCompound("paint")));
        this.setHurtDir(pCompound.getInt("hurtDir"));
        this.setDamage(pCompound.getFloat("damage"));
        this.setHurtTime(pCompound.getInt("hurtTime"));
        this.setRandomRotation(pCompound.getFloat("randomRotation"));
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putFloat("deltaRotation", this.getDeltaRotation());
        pCompound.put("paint", this.getPaint().save(new CompoundTag()));
        pCompound.putInt("hurtDir", this.getHurtDir());
        pCompound.putFloat("damage", this.getDamage());
        pCompound.putInt("hurtTime", this.getHurtTime());
        pCompound.putFloat("randomRotation", this.getRandomRotation());
    }

    @Override
    protected boolean canAddPassenger(final net.minecraft.world.entity.Entity passenger) {
        if(this.getDamage() > this.getDamageThreshold()){
            return false;
        }
        return this.getPassengers().size() < this.getMaxPassengers() && !this.isRemoved() && passenger instanceof AbstractVehiclePart;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        net.minecraft.world.entity.Entity entity = this.getPilotVehiclePartAsEntity();
        if (entity instanceof AbstractVehiclePart vehiclePart) {
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
        final net.minecraft.world.entity.Entity vehiclePart = this.getPilotVehiclePartAsEntity();

        if (!(vehiclePart instanceof AbstractVehiclePart) || !vehiclePart.isVehicle()) {
            return null;
        }

        if (!(vehiclePart.getFirstPassenger() instanceof EmptyCompartmentEntity emptyCompartmentEntity)) {
            return null;
        }

        return emptyCompartmentEntity;
    }

    @Nullable
    public net.minecraft.world.entity.Entity getPilotVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getMaxPassengers()) {
            return this.getPassengers().get(0);
        }
        return null;
    }

    @Override
    public boolean isUnderWater() {
        return this.status == Status.UNDER_WATER || this.status == Status.UNDER_FLOWING_WATER;
    }


    @Override
    protected void addPassenger(net.minecraft.world.entity.Entity passenger) {
        if (passenger instanceof AbstractVehiclePart) {
            super.addPassenger(passenger);
        }
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(this.getDropItem());
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        float bbRadius = this.getBbWidth() * 3 + 1;
        Vec3 startingPoint = new Vec3(this.getX() - bbRadius, this.getY() - bbRadius, this.getZ() - bbRadius);
        Vec3 endingPoint = new Vec3(this.getX() + bbRadius, this.getY() + bbRadius, this.getZ() + bbRadius);
        return new AABB(startingPoint, endingPoint);
    }

    @Override
    public void onAboveBubbleCol(boolean pDownwards) {
    }

    @Override
    public void onInsideBubbleColumn(boolean pDownwards) {
    }

    public static enum Status {
        IN_WATER,
        UNDER_WATER,
        UNDER_FLOWING_WATER,
        ON_LAND,
        IN_AIR;
    }
}
