package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderChestCompartmentEntity extends AbstractCompartmentEntity implements MenuProvider, HasCustomInventoryScreen {
    public static final byte CONTAINER_OPEN = 1;
    public static final byte CONTAINER_CLOSE = 2;
    private final ChestLidController chestLidController = new ChestLidController();
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(final Level level, final BlockPos blockPos, final BlockState blockState) {
            EnderChestCompartmentEntity.this.playSound(SoundEvents.ENDER_CHEST_OPEN);
        }

        @Override
        protected void onClose(final Level level, final BlockPos blockPos, final BlockState blockState) {
            EnderChestCompartmentEntity.this.playSound(SoundEvents.ENDER_CHEST_CLOSE);
        }

        @Override
        protected void openerCountChanged(final Level level, final BlockPos blockPos, final BlockState blockState,
                final int count, final int openCount) {
            EnderChestCompartmentEntity.this.signalOpenCount(level, (byte) openCount);
        }

        @Override
        protected boolean isOwnContainer(final Player player) {

            return false;
        }
    };

    public EnderChestCompartmentEntity(final EntityType<? extends EnderChestCompartmentEntity> entityType,
            final Level level) {
        super(entityType, level);
    }

    public EnderChestCompartmentEntity(final CompartmentType<? extends EnderChestCompartmentEntity> entityType,
            final Level level, final ItemStack ignoredItemStack) {
        this(entityType, level);

        this.setDisplayBlockState(Blocks.ENDER_CHEST.defaultBlockState());
    }

    @Override
    public void tick() {
        super.tick();

        this.chestLidController.tickLid();

        if (!this.isRemoved() && this.level().isClientSide()) {
            for (int i = 0; i < 2; ++i) {
                this.level().addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D,
                        this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2, -this.random.nextDouble(),
                        (this.random.nextDouble() - 0.5D) * 2);
            }

            this.openersCounter.recheckOpeners(this.level(), this.blockPosition(), this.getDisplayBlockState());
        }
    }

    @Override
    public void handleEntityEvent(final byte dataID) {
        switch (dataID) {
            case CONTAINER_OPEN -> this.chestLidController.shouldBeOpen(true);
            case CONTAINER_CLOSE -> this.chestLidController.shouldBeOpen(false);
        }

        super.handleEntityEvent(dataID);
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        player.openMenu(this);
        this.gameEvent(GameEvent.CONTAINER_OPEN, player);
        player.awardStat(Stats.OPEN_ENDERCHEST);
        PiglinAi.angerNearbyPiglins(player, true);
        return InteractionResult.sidedSuccess(player.level().isClientSide());
    }

    public void startOpen(final Player player) {
        if (!this.isRemoved() && !player.isSpectator() || !this.isPassenger()) {
            this.openersCounter.incrementOpeners(player, this.level(), this.blockPosition(),
                    this.getDisplayBlockState());
        }
    }

    public void stopOpen(final Player player) {
        if (!this.isRemoved() && !player.isSpectator() || !this.isPassenger()) {
            this.openersCounter.decrementOpeners(player, this.level(), this.blockPosition(),
                    this.getDisplayBlockState());
        }
    }

    public float getOpenNess(final float partialTicks) {
        return this.chestLidController.getOpenness(partialTicks);
    }

    private void signalOpenCount(final Level level, final byte openCount) {
        level.broadcastEntityEvent(this, openCount > 0 ? CONTAINER_OPEN : CONTAINER_CLOSE);
    }

    @Override
    public void openCustomInventoryScreen(final Player player) {
        player.openMenu(this);
    }

    private boolean stillValid(final Player player) {
        return !this.isRemoved() && this.position().closerThan(player.position(), 8);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(final int id, final Inventory playerInventory, final Player player) {

        // Container that wraps the Player Ender Chest Container
        class EnderChestContainerWrapper extends SimpleContainer {

            private final PlayerEnderChestContainer enderChestInventory = player.getEnderChestInventory();

            @Override
            public ItemStack getItem(final int slotIndex) {
                return enderChestInventory.getItem(slotIndex);
            }

            @Override
            public List<ItemStack> removeAllItems() {
                return enderChestInventory.removeAllItems();
            }

            @Override
            public ItemStack removeItem(final int slotIndex, final int count) {
                return enderChestInventory.removeItem(slotIndex, count);
            }

            @Override
            public ItemStack removeItemType(final Item item, final int amount) {
                return enderChestInventory.removeItemType(item, amount);
            }

            @Override
            public ItemStack addItem(final ItemStack itemStack) {
                return enderChestInventory.addItem(itemStack);
            }

            @Override
            public boolean canAddItem(final ItemStack itemStack) {
                return enderChestInventory.canAddItem(itemStack);
            }

            @Override
            public ItemStack removeItemNoUpdate(final int slotIndex) {
                return enderChestInventory.removeItemNoUpdate(slotIndex);
            }

            @Override
            public void setItem(final int slotIndex, final ItemStack itemStack) {
                enderChestInventory.setItem(slotIndex, itemStack);
            }

            @Override
            public int getContainerSize() {
                return enderChestInventory.getContainerSize();
            }

            @Override
            public boolean isEmpty() {
                return enderChestInventory.isEmpty();
            }

            @Override
            public void setChanged() {
                enderChestInventory.setChanged();
            }

            @Override
            public boolean stillValid(final Player player) {
                return EnderChestCompartmentEntity.this.stillValid(player) && super.stillValid(player);
            }

            @Override
            public void clearContent() {
                enderChestInventory.clearContent();
            }

            @Override
            public void fillStackedContents(final StackedContents stackedContents) {
                enderChestInventory.fillStackedContents(stackedContents);
            }

            @Override
            public String toString() {
                return enderChestInventory.toString();
            }

            @Override
            public void fromTag(final ListTag containerNBT) {
                enderChestInventory.fromTag(containerNBT);
            }

            @Override
            public ListTag createTag() {
                return enderChestInventory.createTag();
            }

            @Override
            public void startOpen(final Player player) {
                EnderChestCompartmentEntity.this.startOpen(player);

                super.startOpen(player);
            }

            @Override
            public void stopOpen(final Player player) {
                EnderChestCompartmentEntity.this.stopOpen(player);

                super.stopOpen(player);
            }
        }

        return ChestMenu.threeRows(id, playerInventory, new EnderChestContainerWrapper());
    }
}