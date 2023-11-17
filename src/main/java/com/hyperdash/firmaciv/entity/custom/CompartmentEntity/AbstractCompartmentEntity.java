package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import com.hyperdash.firmaciv.entity.FirmacivEntities;
import net.dries007.tfc.common.container.RestrictedChestContainer;
import net.dries007.tfc.common.container.TFCContainerTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class AbstractCompartmentEntity extends Entity {

    private static final EntityDataAccessor<ItemStack> DATA_BLOCK_TYPE_ITEM = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(AbstractCompartmentEntity.class, EntityDataSerializers.FLOAT);

    protected VehiclePartEntity ridingThisPart = null;

    public ItemStack getBlockTypeItem() {
        return (ItemStack)this.entityData.get(DATA_BLOCK_TYPE_ITEM);
    }

    public void setBlockTypeItem(ItemStack stack) {
        this.entityData.set(DATA_BLOCK_TYPE_ITEM, stack.copy());
    }

    public AbstractCompartmentEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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
    }

    public Item getDropItem() {
        return this.getBlockTypeItem().getItem();
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        LivingEntity livingentity1;
        if (entity instanceof LivingEntity livingentity) {
            livingentity1 = livingentity;
        } else {
            livingentity1 = null;
        }

        return livingentity1;
    }

    private int notRidingTicks = 0;

    public void tick() {
        if(ridingThisPart == null && this.isPassenger() && this.getVehicle() instanceof VehiclePartEntity){
            ridingThisPart = (VehiclePartEntity)this.getVehicle();
        }
        if(!this.isPassenger()){
            notRidingTicks++;
            if(notRidingTicks > 1){
                this.spawnAtLocation(this.getDropItem());
                this.discard();
            }
        } else {
            notRidingTicks = 0;
        }
        super.tick();
    }

    protected AbstractCompartmentEntity swapCompartments(AbstractCompartmentEntity newCompartment){
        this.spawnAtLocation(this.getDropItem());
        this.stopRiding();
        this.discard();
        newCompartment.startRiding(ridingThisPart);
        this.level().addFreshEntity(newCompartment);
        return newCompartment;
    }

    public ItemStack getPickResult() {
        return getBlockTypeItem();
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if(!(this instanceof EmptyCompartmentEntity)){
            if (this.isInvulnerableTo(pSource)) {
                return false;
            } else if (!this.level().isClientSide && !this.isRemoved()) {
                this.setHurtDir(-this.getHurtDir());
                this.setHurtTime(10);
                this.setDamage(this.getDamage() + pAmount * 10.0F);
                this.markHurt();
                this.gameEvent(GameEvent.ENTITY_DAMAGE, pSource.getEntity());
                boolean flag = pSource.getEntity() instanceof Player && ((Player)pSource.getEntity()).getAbilities().instabuild;
                if (flag || this.getDamage() > 40.0F) {
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
