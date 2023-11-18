package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class AbstractCompartmentEntity extends Entity {

    private static final EntityDataAccessor<ItemStack> DATA_BLOCK_TYPE_ITEM = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.FLOAT);

    private static final float DAMAGE_TO_BREAK = 8.0f;
    private static final float DAMAGE_RECOVERY = 0.5f;
    @Nullable
    protected VehiclePartEntity ridingThisPart = null;
    private int notRidingTicks = 0;

    public AbstractCompartmentEntity(final EntityType<?> entityType, final Level level) {
        super(entityType, level);
        this.setNoGravity(false);
        notRidingTicks = 0;
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
    }

    public Item getDropItem() {
        return this.getBlockTypeItem().getItem();
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (!(this instanceof EmptyCompartmentEntity)) {
            return null;
        }

        final Entity entity = this.getFirstPassenger();
        final LivingEntity pilotEntity;
        if (entity instanceof LivingEntity livingentity) {
            pilotEntity = livingentity;
        } else {
            pilotEntity = null;
        }

        return pilotEntity;
    }

    @Override
    public double getMyRidingOffset() {
        return 0.125D;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset();
    }

    @Override
    public void tick() {
        if (ridingThisPart == null && this.isPassenger() && this.getVehicle() instanceof VehiclePartEntity) {
            ridingThisPart = (VehiclePartEntity) this.getVehicle();
        }

        if (this.isPassenger() && this.getYRot() == 0.0f && this.getVehicle().getYRot() != 0.0f) {
            this.setYRot(this.getVehicle().getYRot());
        }

        if (!this.level().isClientSide() && !this.isPassenger()) {
            notRidingTicks++;
            if (notRidingTicks > 1) {
                this.spawnAtLocation(this.getDropItem());
                this.discard();
            }
        } else {
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
    protected AbstractCompartmentEntity swapCompartments(final AbstractCompartmentEntity newCompartment) {
        this.spawnAtLocation(this.getDropItem());
        this.stopRiding();
        this.discard();
        newCompartment.startRiding(ridingThisPart);
        this.level().addFreshEntity(newCompartment);
        return newCompartment;
    }

    @Override
    public ItemStack getPickResult() {
        return getBlockTypeItem();
    }

    @Override
    public boolean hurt(final DamageSource damageSource, final float amount) {
        if (this instanceof EmptyCompartmentEntity) return false;

        if (this.isInvulnerableTo(damageSource)) return false;

        if (this.level().isClientSide || this.isRemoved()) return true;

        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() + amount * 8.0F);
        this.markHurt();
        this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
        final boolean instantKill = damageSource.getEntity() instanceof Player && ((Player) damageSource.getEntity()).getAbilities().instabuild;
        if (instantKill || this.getDamage() > 10.0F) {
            if (!instantKill && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                this.destroy(damageSource);
            }
            this.discard();
        }
        return true;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_HURT, 0);
        this.entityData.define(DATA_ID_HURTDIR, 1);
        this.entityData.define(DATA_ID_DAMAGE, 0.0F);
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
        return this.entityData.get(DATA_ID_HURTDIR);
    }

    public void setHurtDir(final int pHurtDirection) {
        this.entityData.set(DATA_ID_HURTDIR, pHurtDirection);
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }
}