package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment;

import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.KayakEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.AbstractVehiclePart;
import com.alekiponi.firmaciv.util.FirmacivHelper;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public abstract class AbstractCompartmentEntity extends Entity {

    private static final EntityDataAccessor<ItemStack> DATA_BLOCK_TYPE_ITEM = SynchedEntityData.defineId(
            AbstractCompartmentEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(
            AbstractCompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_HURT_DIR = SynchedEntityData.defineId(
            AbstractCompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(
            AbstractCompartmentEntity.class, EntityDataSerializers.FLOAT);
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

    public AbstractCompartmentEntity(final EntityType<?> entityType, final Level level) {
        super(entityType, level);
    }

    public ItemStack getBlockTypeItem() {
        return this.entityData.get(DATA_BLOCK_TYPE_ITEM);
    }

    public void setBlockTypeItem(final ItemStack itemStack) {
        this.entityData.set(DATA_BLOCK_TYPE_ITEM, itemStack.copy());
    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {
        this.setBlockTypeItem(ItemStack.of(compoundTag.getCompound("dataBlockTypeItem")));
        this.lifespan = compoundTag.getInt("Lifespan");
        this.notRidingTicks = compoundTag.getInt("notRidingTicks");
    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {
        compoundTag.put("dataBlockTypeItem", this.getBlockTypeItem().save(new CompoundTag()));
        compoundTag.putInt("Lifespan", this.lifespan);
        compoundTag.putInt("notRidingTicks", this.notRidingTicks);
    }

    public Item getDropItem() {
        return this.getBlockTypeItem().getItem();
    }

    @Override
    public double getMyRidingOffset() {
        return 0.125D;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset();
    }

    @Nullable
    public AbstractFirmacivBoatEntity getTrueVehicle() {
        if(this.getRootVehicle() instanceof AbstractFirmacivBoatEntity firmacivBoatEntity){
            return firmacivBoatEntity;
        }
        return null;
    }

    @Override
    public void tick() {
        if (ridingThisPart == null && this.isPassenger() && this.getVehicle() instanceof AbstractVehiclePart) {
            ridingThisPart = (AbstractVehiclePart) this.getVehicle();
        }

        if (!this.isPassenger()) {
            this.checkInsideBlocks();
            if (!(this instanceof EmptyCompartmentEntity)) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
                if (this.isInWater() || this.level().getFluidState(this.blockPosition())
                        .is(TFCFluids.SALT_WATER.getSource())) {
                    this.setDeltaMovement(0.0D, -0.01D, 0.0D);
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
                            this.setDeltaMovement(vec31.multiply(1.0D, -0.5D, 1.0D));
                        }
                    }
                }
                if (!this.level().isClientSide()) {
                    notRidingTicks++;
                    if (notRidingTicks > lifespan) {
                        this.spawnAtLocation(this.getDropItem(), 1);
                        this.discard();
                    }
                }

                this.updateInWaterStateAndDoFluidPushing();
            } else if (!this.level().isClientSide()) {
                notRidingTicks++;
                if (notRidingTicks > 1) {
                    this.spawnAtLocation(this.getDropItem(), 1);
                    this.discard();
                }
            }
        } else if (this.level().isClientSide()) {
            notRidingTicks = 0;
        }

        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0.0F) {
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
        damageSource.getEntity();
        return SoundEvents.WOOD_HIT;
    }

    protected void playHurtSound(final DamageSource pSource) {
        SoundEvent soundevent = this.getHurtSound(pSource);
        this.playSound(soundevent, 1.0f, this.level().getRandom().nextFloat() * 0.05F + 0.35F);
    }

    /**
     * Replaces this object with the passed in CompartmentEntity
     *
     * @param newCompartment The compartment entity which is replacing this object
     * @return The compartment passed in
     */
    protected AbstractCompartmentEntity swapCompartments(final AbstractCompartmentEntity newCompartment) {
        this.spawnAtLocation(this.getDropItem(), 1);
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

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_HURT, 0);
        this.entityData.define(DATA_ID_HURT_DIR, 1);
        this.entityData.define(DATA_ID_DAMAGE, 0F);
        this.entityData.define(DATA_BLOCK_TYPE_ITEM, ItemStack.EMPTY);
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

    protected void destroy(final DamageSource damageSource) {
        this.spawnAtLocation(this.getDropItem(), 1);
    }

    /**
     * Gets the forward direction of the entity.
     */
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

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(this.getDropItem());
    }
}