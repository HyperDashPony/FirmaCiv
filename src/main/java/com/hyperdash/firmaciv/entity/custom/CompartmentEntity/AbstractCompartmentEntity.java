package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import com.hyperdash.firmaciv.entity.FirmacivEntities;
import net.dries007.tfc.common.container.RestrictedChestContainer;
import net.dries007.tfc.common.container.TFCContainerTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class AbstractCompartmentEntity extends Entity {
    protected ItemStack blockTypeItem = ItemStack.EMPTY;

    protected int passengerIndex = -1;

    public ItemStack getBlockTypeItem(){
        return blockTypeItem;
    }

    public void setBlockTypeItem(ItemStack blockTypeItem){
        this.blockTypeItem = blockTypeItem;
    }

    public void setPassengerIndex(int pIndex){
        this.passengerIndex = pIndex;
    }

    public int getPassengerIndex(){
        return this.passengerIndex;
    }

    public AbstractCompartmentEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        if(this.isPassenger()){
            passengerIndex = this.getVehicle().getPassengers().indexOf(this);
        }
    }


    public void remove(RemovalReason pReason) {
        super.remove(pReason);
    }

    public Item getDropItem() {
        return blockTypeItem.getItem();
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

    protected AbstractCompartmentEntity swapCompartments(AbstractCompartmentEntity newCompartment){
        newCompartment.setPos(this.position());
        newCompartment.setYRot(this.getYRot());
        newCompartment.setXRot(this.getXRot());
        newCompartment.setDeltaMovement(this.getDeltaMovement());
        newCompartment.setPassengerIndex(this.passengerIndex);
        this.spawnAtLocation(this.getDropItem());
        this.stopRiding();;
        this.discard();
        return newCompartment;
    }

    protected void juggleCompartments(){
        Entity vehicle = this.getVehicle();
        if (this.getPassengerIndex() != this.getVehicle().getPassengers().indexOf(this)){
            vehicle.ejectPassengers();
        } if(vehicle.getPassengers().size()-1 == this.getPassengerIndex()){

        }
    }

    public ItemStack getPickResult() {
        return blockTypeItem;
    }

    @Override
    protected void defineSynchedData() {

    }


    public boolean isPushable() {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }


    public boolean isPickable() {
        return !this.isRemoved();
    }





}
