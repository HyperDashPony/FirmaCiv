package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import com.hyperdash.firmaciv.entity.custom.FirmacivBoatEntity;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.world.biome.TFCBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Locale;

public class AbstractCompartmentEntity extends Entity {

    private static final EntityDataAccessor<ItemStack> DATA_BLOCK_TYPE_ITEM = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.FLOAT);

    public int lifespan = 6000;

    private static final float DAMAGE_TO_BREAK = 8.0f;
    private static final float DAMAGE_RECOVERY = 0.5f;


    protected VehiclePartEntity ridingThisPart = null;

    public ItemStack getBlockTypeItem() {
        return (ItemStack) this.entityData.get(DATA_BLOCK_TYPE_ITEM);
    }

    public void setBlockTypeItem(ItemStack stack) {
        this.entityData.set(DATA_BLOCK_TYPE_ITEM, stack.copy());
    }

    public AbstractCompartmentEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(false);
        notRidingTicks = 0;
    }

    public void remove(RemovalReason pReason) {
        super.remove(pReason);
    }

    protected void readAdditionalSaveData(CompoundTag tag) {
        this.setBlockTypeItem(ItemStack.of(tag.getCompound("dataBlockTypeItem")));
    }

    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("dataBlockTypeItem", this.getBlockTypeItem().save(new CompoundTag()));
        tag.putInt("Lifespan", this.lifespan);
        tag.putInt("notRidingTicks", this.notRidingTicks);
    }


    public Item getDropItem() {
        return this.getBlockTypeItem().getItem();
    }

    @Nullable
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

    public double getMyRidingOffset() {
        return 0.125D;
    }

    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset();
    }

    public FirmacivBoatEntity getTrueVehicle(){
        if(ridingThisPart != null && ridingThisPart.isPassenger() && ridingThisPart.getVehicle() instanceof FirmacivBoatEntity firmacivBoatEntity){
            return firmacivBoatEntity;
        } else {
            return null;
        }
    }

    private int notRidingTicks = 0;

    public void tick() {


        if (ridingThisPart == null && this.isPassenger() && this.getVehicle() instanceof VehiclePartEntity) {
            ridingThisPart = (VehiclePartEntity) this.getVehicle();
        }

        if (!this.isPassenger()) {

            if(!(this instanceof EmptyCompartmentEntity)){
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
                if (this.isInWater() ||
                        this.getY() < 63 && this.level().getFluidState(this.blockPosition()).is(TFCFluids.SALT_WATER.getSource())) {
                    this.setDeltaMovement(0.0D, -0.01D, 0.0D);
                    this.setYRot(this.getYRot() + 0.4f);
                }
                if (!this.onGround() || this.getDeltaMovement().horizontalDistanceSqr() > (double)1.0E-5F || (this.tickCount + this.getId()) % 4 == 0) {
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    float f1 = 0.98F;

                    this.setDeltaMovement(this.getDeltaMovement().multiply((double)f1, 0.98D, (double)f1));
                    if (this.onGround()) {
                        Vec3 vec31 = this.getDeltaMovement();
                        if (vec31.y < 0.0D) {
                            this.setDeltaMovement(vec31.multiply(1.0D, -0.5D, 1.0D));
                        }
                    }
                }
                if(!this.level().isClientSide()){
                    notRidingTicks++;
                    if (notRidingTicks > lifespan) {
                        this.spawnAtLocation(this.getDropItem());
                        this.discard();
                    }
                }

                this.updateInWaterStateAndDoFluidPushing();
            } else if(!this.level().isClientSide()) {
                notRidingTicks++;
                if (notRidingTicks > 1) {
                    this.spawnAtLocation(this.getDropItem());
                    this.discard();
                }
            }
        } else if (this.level().isClientSide()){
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
        newCompartment.setYRot(this.getYRot());
        newCompartment.setPos(this.getX(), this.getY(), this.getZ());
        newCompartment.ridingThisPart = this.ridingThisPart;
        newCompartment.startRiding(ridingThisPart);
        this.level().addFreshEntity(newCompartment);
        return newCompartment;
    }

    public ItemStack getPickResult() {
        return getBlockTypeItem();
    }

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

    public void setDamage(float pDamageTaken) {
        this.entityData.set(DATA_ID_DAMAGE, pDamageTaken);
    }


    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    public void setHurtTime(int pHurtTime) {
        this.entityData.set(DATA_ID_HURT, pHurtTime);
    }

    public int getHurtTime() {
        return this.entityData.get(DATA_ID_HURT);
    }

    protected void destroy(DamageSource pDamageSource) {
        this.spawnAtLocation(this.getDropItem());
    }

    public void setHurtDir(int pHurtDirection) {
        this.entityData.set(DATA_ID_HURTDIR, pHurtDirection);
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getHurtDir() {
        return this.entityData.get(DATA_ID_HURTDIR);
    }


    public boolean isPushable() {
        return false;
    }


    public boolean isPickable() {
        return !this.isRemoved();
    }


}
