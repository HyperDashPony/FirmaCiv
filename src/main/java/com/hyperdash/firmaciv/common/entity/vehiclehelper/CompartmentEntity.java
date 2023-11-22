package com.hyperdash.firmaciv.common.entity.vehiclehelper;

import com.hyperdash.firmaciv.common.entity.FirmacivBoatEntity;
import com.hyperdash.firmaciv.common.entity.KayakEntity;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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

public abstract class CompartmentEntity extends Entity {

    private static final EntityDataAccessor<ItemStack> DATA_BLOCK_TYPE_ITEM = SynchedEntityData.defineId(
            CompartmentEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(
            CompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_HURT_DIR = SynchedEntityData.defineId(
            CompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(
            CompartmentEntity.class, EntityDataSerializers.FLOAT);
    private static final float DAMAGE_TO_BREAK = 8.0f;
    private static final float DAMAGE_RECOVERY = 0.5f;
    public int lifespan = 6000;
    @Nullable
    protected VehiclePartEntity ridingThisPart = null;
    private int notRidingTicks = 0;

    public CompartmentEntity(final EntityType<?> entityType, final Level level) {
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
    public FirmacivBoatEntity getTrueVehicle() {
        if (ridingThisPart != null && ridingThisPart.isPassenger() && ridingThisPart.getVehicle() instanceof FirmacivBoatEntity firmacivBoatEntity) {
            return firmacivBoatEntity;
        }
        return null;
    }

    @Override
    public void tick() {
        if (ridingThisPart == null && this.isPassenger() && this.getVehicle() instanceof VehiclePartEntity) {
            ridingThisPart = (VehiclePartEntity) this.getVehicle();
        }

        if (!this.isPassenger()) {

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
                        this.spawnAtLocation(this.getDropItem());
                        this.discard();
                    }
                }

                this.updateInWaterStateAndDoFluidPushing();
            } else if (!this.level().isClientSide()) {
                notRidingTicks++;
                if (notRidingTicks > 1) {
                    this.spawnAtLocation(this.getDropItem());
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
    }

    protected SoundEvent getHurtSound(final DamageSource damageSource) {
        return SoundEvents.WOOD_HIT;
    }

    protected void playHurtSound(final DamageSource pSource) {
        SoundEvent soundevent = this.getHurtSound(pSource);
        this.playSound(soundevent, 1.0f, this.level().getRandom().nextFloat() * 0.1F + 0.9F);
    }

    /**
     * Replaces this object with the passed in CompartmentEntity
     *
     * @param newCompartment The compartment entity which is replacing this object
     * @return The compartment passed in
     */
    protected CompartmentEntity swapCompartments(final CompartmentEntity newCompartment) {
        this.spawnAtLocation(this.getDropItem());
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
    public boolean hurt(final DamageSource damageSource, final float amount) {
        if (this instanceof EmptyCompartmentEntity && !(this.getTrueVehicle() instanceof KayakEntity)) return false;

        if (this.isInvulnerableTo(damageSource)) return false;

        if (this.level().isClientSide || this.isRemoved()) return true;

        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() + amount * 8);
        this.markHurt();
        this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
        final boolean instantKill = damageSource.getEntity() instanceof Player && ((Player) damageSource.getEntity()).getAbilities().instabuild;

        // Don't kill
        if (!instantKill && !(this.getDamage() > 10)) return true;

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
        this.spawnAtLocation(this.getDropItem());
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
}