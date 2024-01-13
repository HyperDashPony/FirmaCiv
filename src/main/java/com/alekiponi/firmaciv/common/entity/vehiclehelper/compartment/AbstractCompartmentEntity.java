package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment;

import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.KayakEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.AbstractVehiclePart;
import com.alekiponi.firmaciv.util.FirmacivHelper;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public abstract class AbstractCompartmentEntity extends Entity {
    private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(
            AbstractCompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_HURT_DIR = SynchedEntityData.defineId(
            AbstractCompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(
            AbstractCompartmentEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> DATA_ID_DISPLAY_BLOCK = SynchedEntityData.defineId(
            AbstractCompartmentEntity.class, EntityDataSerializers.INT);
    private static final float DAMAGE_TO_BREAK = 8.0f;
    private static final float DAMAGE_RECOVERY = 0.5f;
    public int lifespan = 6000;
    protected int lerpSteps;
    protected double lerpX;
    protected double lerpY;
    protected double lerpZ;
    protected double lerpYRot;
    protected double lerpXRot;
    @Nullable
    protected AbstractVehiclePart ridingThisPart = null;
    private int notRidingTicks = 0;

    public AbstractCompartmentEntity(final EntityType<? extends AbstractCompartmentEntity> entityType,
            final Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_HURT, 0);
        this.entityData.define(DATA_ID_HURT_DIR, 1);
        this.entityData.define(DATA_ID_DAMAGE, 0F);
        this.entityData.define(DATA_ID_DISPLAY_BLOCK, Block.getId(Blocks.AIR.defaultBlockState()));
    }

    /**
     * Replaces this object with the passed in CompartmentEntity
     *
     * @param newCompartment The compartment entity which is replacing this object
     * @return The compartment passed in
     */
    protected AbstractCompartmentEntity swapCompartments(final AbstractCompartmentEntity newCompartment) {
        this.spawnAtLocation(this.getDropStack(), 1);
        this.stopRiding();
        this.discard();
        newCompartment.setYRot(this.getYRot());
        newCompartment.setPos(this.getX(), this.getY(), this.getZ());
        newCompartment.ridingThisPart = this.ridingThisPart;
        newCompartment.startRiding(ridingThisPart);
        this.level().addFreshEntity(newCompartment);
        return newCompartment;
    }

    @Override
    public void tick() {
        if (ridingThisPart == null && this.isPassenger() && this.getVehicle() instanceof AbstractVehiclePart) {
            ridingThisPart = (AbstractVehiclePart) this.getVehicle();
        }

        if (!this.isPassenger()) {
            this.checkInsideBlocks();
            if (!(this instanceof EmptyCompartmentEntity)) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, -0.04D, 0));
                if (this.isInWater() || this.level().getFluidState(this.blockPosition())
                        .is(TFCFluids.SALT_WATER.getSource())) {
                    if (this.getFluidTypeHeight(this.getEyeInFluidType()) > this.getEyeHeight() - 0.25) {
                        this.setDeltaMovement(0, this.getBuoyancy(), 0);
                    }
                    this.setYRot(this.getYRot() + 0.4f);
                }
                if (!this.onGround() || this.getDeltaMovement()
                        .horizontalDistanceSqr() > (double) 1.0E-5F || (this.tickCount + this.getId()) % 4 == 0) {
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    float f1 = 0.98F;

                    this.setDeltaMovement(this.getDeltaMovement().multiply(f1, 0.98D, f1));
                    if (this.onGround()) {
                        Vec3 vec31 = this.getDeltaMovement();
                        if (vec31.y < 0.0D) {
                            this.setDeltaMovement(vec31.multiply(1, -0.5D, 1));
                        }
                    }
                }
                if (!this.level().isClientSide()) {
                    notRidingTicks++;
                    if (notRidingTicks > lifespan) {
                        this.spawnAtLocation(this.getDropStack(), 1);
                        this.discard();
                    }
                }

                this.updateInWaterStateAndDoFluidPushing();
            } else if (!this.level().isClientSide()) {
                notRidingTicks++;
                if (notRidingTicks > 1) {
                    this.spawnAtLocation(this.getDropStack(), 1);
                    this.discard();
                }
            }
        } else if (this.level().isClientSide()) {
            notRidingTicks = 0;
        }

        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0) {
            this.setDamage(this.getDamage() - DAMAGE_RECOVERY);
        }

        super.tick();
        this.tickLerp();
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
            //this.setRot(this.getYRot(), this.getXRot());
        }
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

    protected SoundEvent getHurtSound(final DamageSource damageSource) {
        return SoundEvents.WOOD_HIT;
    }

    protected void playHurtSound(final DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1, this.level().getRandom().nextFloat() * 0.05F + 0.35F);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        if(pSource.is(DamageTypeTags.IS_EXPLOSION)){
            return true;
        }
        return super.isInvulnerableTo(pSource);
    }

    public boolean everyNthTickUnique(int n){
        return FirmacivHelper.everyNthTickUnique(this.getId(), this.tickCount, n);
    }

    @Override
    public boolean hurt(final DamageSource damageSource, final float amount) {
        if (this instanceof EmptyCompartmentEntity && !(this.getTrueVehicle() instanceof KayakEntity)) return false;

        if (this.isInvulnerableTo(damageSource)) return false;

        if (this.level().isClientSide || this.isRemoved()) return true;

        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() + amount * 5);

        this.markHurt();
        this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
        final boolean instantKill = damageSource.getEntity() instanceof Player && ((Player) damageSource.getEntity()).getAbilities().instabuild;

        // Don't kill
        if (!instantKill && !(this.getDamage() > 10)) {
            this.playHurtSound(damageSource);
            return true;
        }

        if (!instantKill && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.destroy(damageSource);
        }

        if (this.getRootVehicle() instanceof KayakEntity kayakEntity) {
            kayakEntity.spawnAtLocation(kayakEntity.getDropItem());
            kayakEntity.remove(RemovalReason.KILLED);
            kayakEntity.kill();
            this.getVehicle().kill();
        }

        this.discard();
        return true;
    }

    protected void destroy(final DamageSource damageSource) {
        this.spawnAtLocation(this.getDropStack(), 1);
    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {
        this.lifespan = compoundTag.getInt("Lifespan");
        this.notRidingTicks = compoundTag.getInt("notRidingTicks");
        this.setDisplayBlockState(NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK),
                compoundTag.getCompound("heldBlock")));
    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {
        compoundTag.putInt("Lifespan", this.lifespan);
        compoundTag.putInt("notRidingTicks", this.notRidingTicks);
        compoundTag.put("heldBlock", NbtUtils.writeBlockState(this.getDisplayBlockState()));
    }

    /**
     * Allows children to easily override the buoyancy.
     * This value is used as is so to have a compartment which floats you should return a positive value
     */
    public double getBuoyancy() {
        return -0.01;
    }

    @Override
    public double getMyRidingOffset() {
        return 0.125D;
    }

    @Nullable
    public AbstractFirmacivBoatEntity getTrueVehicle() {
        if (this.getRootVehicle() instanceof AbstractFirmacivBoatEntity firmacivBoatEntity) {
            return firmacivBoatEntity;
        }
        return null;
    }

    public BlockState getDisplayBlockState() {
        return Block.stateById(this.getEntityData().get(DATA_ID_DISPLAY_BLOCK));
    }

    public void setDisplayBlockState(final BlockState blockState) {
        this.getEntityData().set(DATA_ID_DISPLAY_BLOCK, Block.getId(blockState));
    }

    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    public void setDamage(final float damageTaken) {
        this.entityData.set(DATA_ID_DAMAGE, damageTaken);
    }

    public int getHurtTime() {
        return this.entityData.get(DATA_ID_HURT);
    }

    public void setHurtTime(final int hurtTime) {
        this.entityData.set(DATA_ID_HURT, hurtTime);
    }

    public int getHurtDir() {
        return this.entityData.get(DATA_ID_HURT_DIR);
    }

    public void setHurtDir(final int pHurtDirection) {
        this.entityData.set(DATA_ID_HURT_DIR, pHurtDirection);
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    /**
     * Gets the ItemStack that should be dropped when this compartment is destroyed
     * This is so compartments can easily control the output stack or add NBT
     *
     * @return The ItemStack that should be dropped in world when the compartment is destroyed
     */
    public ItemStack getDropStack() {
        return this.getDisplayBlockState().getBlock().asItem().getDefaultInstance();
    }

    @Override
    public ItemStack getPickResult() {
        return this.getDisplayBlockState().getBlock().asItem().getDefaultInstance();
    }
}