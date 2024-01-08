package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

// TODO opening the container needs to sync, which will likely require a custom menu wrapping PlayerEnderChestContainer
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
            final Level level, final ItemStack itemStack) {
        this(entityType, level);

        this.setDisplayBlockState(Blocks.ENDER_CHEST.defaultBlockState());
    }

    @Override
    public void tick() {
        super.tick();

        this.chestLidController.tickLid();

        if (!this.isRemoved() && this.level().isClientSide()) {
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

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(final int id, final Inventory playerInventory, final Player player) {
        return ChestMenu.threeRows(id, playerInventory, player.getEnderChestInventory());
    }
}