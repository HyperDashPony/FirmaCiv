package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;

public class BarrelCompartmentEntity extends ContainerCompartmentEntity {

    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        protected void onOpen(final Level level, final BlockPos blockPos, final BlockState blockState) {
            BarrelCompartmentEntity.this.playSound(SoundEvents.BARREL_OPEN);
            // TODO update the open state, need to rework compartment rendering first
        }

        protected void onClose(final Level level, final BlockPos blockPos, final BlockState blockState) {
            BarrelCompartmentEntity.this.playSound(SoundEvents.BARREL_CLOSE);
            // TODO update the open state, need to rework compartment rendering first
        }

        @Override
        protected void openerCountChanged(final Level level, final BlockPos blockPos, final BlockState blockState,
                final int count, final int openCount) {
        }

        protected boolean isOwnContainer(final Player player) {
            if (!(player.containerMenu instanceof ChestMenu)) return false;

            final Container container = ((ChestMenu) player.containerMenu).getContainer();
            return container == BarrelCompartmentEntity.this;
        }
    };


    public BarrelCompartmentEntity(final EntityType<? extends BarrelCompartmentEntity> entityType, final Level level) {
        super(entityType, level, 27);
    }

    public BarrelCompartmentEntity(final EntityType<? extends BarrelCompartmentEntity> entityType, final Level level,
            final ItemStack itemStack) {
        this(entityType, level);
        this.setBlockTypeItem(itemStack);
    }

    @Override
    public void remove(final RemovalReason removalReason) {
        if (!this.level().isClientSide && removalReason.shouldDestroy()) {
            this.playSound(SoundEvents.WOOD_BREAK, 1, this.level().getRandom().nextFloat() * 0.1F + 0.9F);
        }

        super.remove(removalReason);
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        final InteractionResult interactionResult = super.interact(player, hand);
        if (interactionResult.consumesAction()) {
            player.awardStat(Stats.OPEN_BARREL);
            PiglinAi.angerNearbyPiglins(player, true);
        }

        return interactionResult;
    }

    public void startOpen(final Player player) {
        if (!player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.level(), this.blockPosition(),
                    Blocks.AIR.defaultBlockState());
        }
    }

    public void stopOpen(final Player player) {
        if (!player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.level(), this.blockPosition(),
                    Blocks.AIR.defaultBlockState());
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(final int id, final Inventory playerInventory) {
        return ChestMenu.threeRows(id, playerInventory, this);
    }
}