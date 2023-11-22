package com.hyperdash.firmaciv.common.entity.vehiclehelper;

import com.hyperdash.firmaciv.util.FirmacivTags;
import net.dries007.tfc.common.container.RestrictedChestContainer;
import net.dries007.tfc.common.container.TFCContainerTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class ChestCompartmentEntity extends CompartmentEntity implements HasCustomInventoryScreen, ContainerEntity {
    private static final int CONTAINER_SIZE = 18;
    private NonNullList<ItemStack> itemStacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    @Nullable
    private ResourceLocation lootTable;
    private long lootTableSeed;
    private LazyOptional<?> itemHandler = LazyOptional.of(() -> new InvWrapper(this));

    public ChestCompartmentEntity(final EntityType<?> entityType, final Level level) {
        super(entityType, level);
    }

    @Override
    public void remove(final RemovalReason removalReason) {
        if (!this.level().isClientSide && removalReason.shouldDestroy()) {
            this.playSound(SoundEvents.WOOD_BREAK, 1.0F, this.level().getRandom().nextFloat() * 0.1F + 0.9F);
            Containers.dropContents(this.level(), this, this);
        }

        super.remove(removalReason);
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
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        final InteractionResult interactionResult = this.interactWithContainerVehicle(player);
        if (interactionResult.consumesAction()) {
            this.gameEvent(GameEvent.CONTAINER_OPEN, player);
            if (this.getBlockTypeItem().is(FirmacivTags.Items.CHESTS)) {
                PiglinAi.angerNearbyPiglins(player, true);
            }
        }

        return interactionResult;
    }

    @Override
    public void openCustomInventoryScreen(final Player player) {
        player.openMenu(this);
        if (!player.level().isClientSide) {
            this.gameEvent(GameEvent.CONTAINER_OPEN, player);
            PiglinAi.angerNearbyPiglins(player, true);
        }
    }

    @Override
    public void clearContent() {
        this.clearChestVehicleContent();
    }

    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    public ItemStack getItem(final int slotIndex) {
        return this.getChestVehicleItem(slotIndex);
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
    public SlotAccess getSlot(final int slotIndex) {
        return this.getChestVehicleSlot(slotIndex);
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(final Player player) {
        return this.isChestVehicleStillValid(player);
    }

    @Override
    public AbstractContainerMenu createMenu(final int windowId, final Inventory inventory, final Player player) {
        if (this.getLootTable() != null && player.isSpectator()) return null;

        this.unpackLootTable(inventory.player);
        return new RestrictedChestContainer(TFCContainerTypes.CHEST_9x2.get(), windowId, inventory, this, 2);
    }

    public void unpackLootTable(final @Nullable Player player) {
        this.unpackChestVehicleLootTable(player);
    }

    @Override
    @Nullable
    public ResourceLocation getLootTable() {
        return this.lootTable;
    }

    @Override
    public void setLootTable(final @Nullable ResourceLocation lootTable) {
        this.lootTable = lootTable;
    }

    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    @Override
    public void setLootTableSeed(final long lootTableSeed) {
        this.lootTableSeed = lootTableSeed;
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

    @Override
    public void stopOpen(final Player player) {
        this.level().gameEvent(GameEvent.CONTAINER_CLOSE, this.position(), GameEvent.Context.of(player));
    }
}