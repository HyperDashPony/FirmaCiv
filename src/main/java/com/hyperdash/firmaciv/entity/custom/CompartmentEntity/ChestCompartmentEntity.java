package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import com.hyperdash.firmaciv.entity.FirmacivEntities;
import net.dries007.tfc.common.container.RestrictedChestContainer;
import net.dries007.tfc.common.container.TFCContainerTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.*;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class ChestCompartmentEntity extends AbstractCompartmentEntity implements HasCustomInventoryScreen, ContainerEntity {


    private static final int CONTAINER_SIZE = 18;
    private NonNullList<ItemStack> itemStacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    @javax.annotation.Nullable
    private ResourceLocation lootTable;
    private long lootTableSeed;

    public ChestCompartmentEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected boolean canAddPassenger(Entity pPassenger) {
        return this.getPassengers().size() == 0; // and does not "contain" a block
    }


    protected float getSinglePassengerXOffset() {
        return 00F;
    }

    protected int getMaxPassengers() {
        return 1;
    }

    public void remove(Entity.RemovalReason pReason) {
        if (!this.level().isClientSide && pReason.shouldDestroy()) {
            Containers.dropContents(this.level(), this, this);
        }

        super.remove(pReason);
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        this.addChestVehicleSaveData(pCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.readChestVehicleSaveData(pCompound);
    }

    public boolean isPushable() {
        return false;
    }

    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {

        if (pPlayer.isHolding(Items.IRON_AXE)){
            EmptyCompartmentEntity newCompartment = FirmacivEntities.EMPTY_COMPARTMENT_ENTITY.get().create(pPlayer.level());

            newCompartment.setPos(this.position());
            newCompartment.setYRot(this.getYRot());
            newCompartment.setXRot(this.getXRot());
            newCompartment.setDeltaMovement(this.getDeltaMovement());
            this.level().addFreshEntity(newCompartment);
            Containers.dropContents(this.level(), this, this);
            newCompartment.setBlockTypeItem(ItemStack.EMPTY);
            this.playSound(SoundEvents.WOOD_BREAK, 1.0F, pPlayer.level().getRandom().nextFloat() * 0.1F + 0.9F);
            this.spawnAtLocation(this.getDropItem());
            this.discard();
            return InteractionResult.sidedSuccess(this.level().isClientSide);

        } else {
            InteractionResult interactionresult = this.interactWithContainerVehicle(pPlayer);
            if (interactionresult.consumesAction()) {

                this.gameEvent(GameEvent.CONTAINER_OPEN, pPlayer);
                if(blockTypeItem.is(Items.CHEST)){
                    PiglinAi.angerNearbyPiglins(pPlayer, true);
                }

            }

            return interactionresult;
        }
    }

    public void openCustomInventoryScreen(Player pPlayer) {
        pPlayer.openMenu(this);
        if (!pPlayer.level().isClientSide) {
            this.gameEvent(GameEvent.CONTAINER_OPEN, pPlayer);
            PiglinAi.angerNearbyPiglins(pPlayer, true);
        }

    }

    public void clearContent() {
        this.clearChestVehicleContent();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getItem(int pSlot) {
        return this.getChestVehicleItem(pSlot);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack removeItem(int pSlot, int pAmount) {
        return this.removeChestVehicleItem(pSlot, pAmount);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeItemNoUpdate(int pSlot) {
        return this.removeChestVehicleItemNoUpdate(pSlot);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int pSlot, ItemStack pStack) {
        this.setChestVehicleItem(pSlot, pStack);
    }

    public SlotAccess getSlot(int pSlot) {
        return this.getChestVehicleSlot(pSlot);
    }

    /**
     * For block entities, ensures the chunk containing the block entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    public void setChanged() {
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean stillValid(Player pPlayer) {
        return this.isChestVehicleStillValid(pPlayer);
    }

    @Nullable
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        if (this.getLootTable() != null && player.isSpectator()) {
            return null;
        } else {
            this.unpackLootTable(inv.player);
            return new RestrictedChestContainer((MenuType) TFCContainerTypes.CHEST_9x2.get(), windowId, inv, this, 2);
        }
    }

    public void unpackLootTable(@javax.annotation.Nullable Player pPlayer) {
        this.unpackChestVehicleLootTable(pPlayer);
    }

    @javax.annotation.Nullable
    public ResourceLocation getLootTable() {
        return this.lootTable;
    }

    public void setLootTable(@javax.annotation.Nullable ResourceLocation pLootTable) {
        this.lootTable = pLootTable;
    }

    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    public void setLootTableSeed(long pLootTableSeed) {
        this.lootTableSeed = pLootTableSeed;
    }

    public NonNullList<ItemStack> getItemStacks() {
        return this.itemStacks;
    }

    public void clearItemStacks() {
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    // Forge Start
    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this));

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.core.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this));
    }

    public void stopOpen(Player pPlayer) {
        this.level().gameEvent(GameEvent.CONTAINER_CLOSE, this.position(), GameEvent.Context.of(pPlayer));
    }

}
