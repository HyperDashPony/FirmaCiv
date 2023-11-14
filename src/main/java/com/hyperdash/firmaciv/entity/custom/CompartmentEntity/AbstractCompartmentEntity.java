package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import net.dries007.tfc.common.container.RestrictedChestContainer;
import net.dries007.tfc.common.container.TFCContainerTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.SlotAccess;
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

import javax.annotation.Nullable;

public class AbstractCompartmentEntity extends Entity {
    protected ItemStack blockTypeItem = ItemStack.EMPTY;

    public ItemStack getBlockTypeItem(){
        return blockTypeItem;
    }

    public void setBlockTypeItem(ItemStack blockTypeItem){
        this.blockTypeItem = blockTypeItem;
    }

    public AbstractCompartmentEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected boolean canAddPassenger(Entity pPassenger) {
        return this instanceof EmptyCompartmentEntity && this.getPassengers().size() == 0;
    }

    protected float getSinglePassengerXOffset() {
        return 00F;
    }

    protected int getMaxPassengers() {
        return 1;
    }

    public void tick() {
        super.tick();
        if(this.isPassenger()){
            Entity vehicle = this.getVehicle();
            this.setYRot(vehicle.getYRot());
        }
    }

    public void remove(RemovalReason pReason) {
        super.remove(pReason);
    }

    public Item getDropItem() {
        return blockTypeItem.getItem();
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
