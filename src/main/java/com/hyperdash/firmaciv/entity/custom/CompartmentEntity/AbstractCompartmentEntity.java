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

    public AbstractCompartmentEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(false);
        notRidingTicks = 0;

    }

    public ItemStack getBlockTypeItem() {
        return this.entityData.get(DATA_BLOCK_TYPE_ITEM);
    }

    public void setBlockTypeItem(ItemStack stack) {
        this.entityData.set(DATA_BLOCK_TYPE_ITEM, stack.copy());
    }

    @Override
    public void remove(RemovalReason pReason) {
        super.remove(pReason);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.setBlockTypeItem(ItemStack.of(tag.getCompound("dataBlockTypeItem")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("dataBlockTypeItem", this.getBlockTypeItem().save(new CompoundTag()));
    }

    public Item getDropItem() {
        return this.getBlockTypeItem().getItem();
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this instanceof EmptyCompartmentEntity) {
            Entity entity = this.getFirstPassenger();
            LivingEntity livingentity1;
            if (entity instanceof LivingEntity livingentity) {
                livingentity1 = livingentity;
            } else {
                livingentity1 = null;
            }

            return livingentity1;
        } else {
            return null;
        }

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

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.WOOD_HIT;
    }

    protected void playHurtSound(DamageSource pSource) {
        SoundEvent soundevent = this.getHurtSound(pSource);
        if (soundevent != null) {
            this.playSound(soundevent, 1.0f, this.level().getRandom().nextFloat() * 0.1F + 0.9F);
        }

    }

    protected AbstractCompartmentEntity swapCompartments(AbstractCompartmentEntity newCompartment) {
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
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!(this instanceof EmptyCompartmentEntity)) {
            if (this.isInvulnerableTo(pSource)) {
                return false;
            } else if (!this.level().isClientSide && !this.isRemoved()) {
                this.setHurtDir(-this.getHurtDir());
                this.setHurtTime(10);
                this.setDamage(this.getDamage() + pAmount * 8.0F);
                this.markHurt();
                this.gameEvent(GameEvent.ENTITY_DAMAGE, pSource.getEntity());
                boolean flag = pSource.getEntity() instanceof Player && ((Player) pSource.getEntity()).getAbilities().instabuild;
                if (flag || this.getDamage() > 10.0F) {
                    if (!flag && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                        this.destroy(pSource);
                    }

                    this.discard();
                }

                return true;
            } else {
                return true;
            }
        }
        return false;
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

    public void setDamage(float pDamageTaken) {
        this.entityData.set(DATA_ID_DAMAGE, pDamageTaken);
    }

    public int getHurtTime() {
        return this.entityData.get(DATA_ID_HURT);
    }

    public void setHurtTime(int pHurtTime) {
        this.entityData.set(DATA_ID_HURT, pHurtTime);
    }

    protected void destroy(DamageSource pDamageSource) {
        this.spawnAtLocation(this.getDropItem());
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getHurtDir() {
        return this.entityData.get(DATA_ID_HURTDIR);
    }

    public void setHurtDir(int pHurtDirection) {
        this.entityData.set(DATA_ID_HURTDIR, pHurtDirection);
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }
}