package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public abstract class ContainerCompartmentEntity extends AbstractCompartmentEntity implements ContainerEntity, HasCustomInventoryScreen {

    private final int slotCount;
    private NonNullList<ItemStack> itemStacks;
    @Nullable
    private ResourceLocation lootTable;
    private long lootTableSeed;
    private LazyOptional<?> itemHandler = LazyOptional.of(() -> new InvWrapper(this));

    public ContainerCompartmentEntity(final EntityType<? extends ContainerCompartmentEntity> entityType,
            final Level level, final int slotCount) {
        super(entityType, level);
        this.slotCount = slotCount;
        this.itemStacks = NonNullList.withSize(slotCount, ItemStack.EMPTY);
    }

    public ContainerCompartmentEntity(final EntityType<? extends ContainerCompartmentEntity> entityType,
            final Level level, final int slotCount, final ItemStack itemStack) {
        this(entityType, level, slotCount);
        if (itemStack.hasCustomHoverName()) {
            this.setCustomName(itemStack.getHoverName());
        }

        if (itemStack.getItem() instanceof BlockItem blockItem) {
            this.setDisplayBlockState(blockItem.getBlock().defaultBlockState());
        }

        final CompoundTag blockEntityTag = itemStack.getTagElement("BlockEntityTag");
        if (blockEntityTag != null) ContainerHelper.loadAllItems(blockEntityTag, this.getItemStacks());
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        final InteractionResult interactionResult = this.interactWithContainerVehicle(player);
        if (interactionResult.consumesAction()) {
            this.gameEvent(GameEvent.CONTAINER_OPEN, player);
        }

        return interactionResult;
    }

    @Override
    protected void destroy(final DamageSource damageSource) {
        super.destroy(damageSource);
        this.chestVehicleDestroyed(damageSource, this.level(), this);
    }

    @Override
    public void remove(final RemovalReason removalReason) {
        if (!this.level().isClientSide && removalReason.shouldDestroy()) {
            Containers.dropContents(this.level(), this, this);
        }

        super.remove(removalReason);
    }

    @Override
    public boolean stillValid(final Player player) {
        return this.isChestVehicleStillValid(player);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(final int id, final Inventory playerInventory, final Player player) {
        if (this.lootTable != null && player.isSpectator()) return null;

        this.unpackChestVehicleLootTable(playerInventory.player);
        return this.createMenu(id, playerInventory);
    }

    abstract protected AbstractContainerMenu createMenu(final int id, final Inventory playerInventory);

    @Override
    public void openCustomInventoryScreen(final Player player) {
        this.interactWithContainerVehicle(player);
    }

    @Override
    public void stopOpen(final Player player) {
        this.level().gameEvent(GameEvent.CONTAINER_CLOSE, this.position(), GameEvent.Context.of(player));
    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.addChestVehicleSaveData(compoundTag);
    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.readChestVehicleSaveData(compoundTag);
    }

    @Override
    public void setChanged() {
    }

    @Override
    public void clearContent() {
        this.clearChestVehicleContent();
    }

    @Override
    public int getContainerSize() {
        return this.slotCount;
    }

    @Override
    public NonNullList<ItemStack> getItemStacks() {
        return this.itemStacks;
    }

    @Override
    public void clearItemStacks() {
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public ItemStack removeItem(final int slotIndex, final int amount) {
        return this.removeChestVehicleItem(slotIndex, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(final int slotIndex) {
        return this.removeChestVehicleItemNoUpdate(slotIndex);
    }

    @Override
    public void setItem(final int slotIndex, final ItemStack itemStack) {
        this.setChestVehicleItem(slotIndex, itemStack);
    }

    @Override
    public ItemStack getItem(final int slotIndex) {
        return this.getChestVehicleItem(slotIndex);
    }

    @Override
    public SlotAccess getSlot(final int slotIndex) {
        return this.getChestVehicleSlot(slotIndex);
    }

    @Nullable
    @Override
    public ResourceLocation getLootTable() {
        return this.lootTable;
    }

    @Override
    public void setLootTable(@Nullable final ResourceLocation lootTable) {
        this.lootTable = lootTable;
    }

    @Override
    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    @Override
    public void setLootTableSeed(final long lootTableSeed) {
        this.lootTableSeed = lootTableSeed;
    }

    @Override
    public <T> LazyOptional<T> getCapability(final Capability<T> capability, final @Nullable Direction facing) {
        if (this.isAlive() && capability == ForgeCapabilities.ITEM_HANDLER) return itemHandler.cast();
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
        itemHandler = LazyOptional.of(() -> new InvWrapper(this));
    }
}