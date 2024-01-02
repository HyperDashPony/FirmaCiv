package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment;

import net.dries007.tfc.common.container.RestrictedChestContainer;
import net.dries007.tfc.common.container.TFCContainerTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChestCompartmentEntity extends ContainerCompartmentEntity {

    public ChestCompartmentEntity(final EntityType<? extends ChestCompartmentEntity> entityType, final Level level) {
        super(entityType, level, 18);
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
            PiglinAi.angerNearbyPiglins(player, true);
        }

        return interactionResult;
    }

    @Override
    public void openCustomInventoryScreen(final Player player) {
        super.openCustomInventoryScreen(player);
        if (!player.level().isClientSide) {
            PiglinAi.angerNearbyPiglins(player, true);
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(final int id, final Inventory playerInventory) {
        return new RestrictedChestContainer(TFCContainerTypes.CHEST_9x2.get(), id, playerInventory, this, 2);
    }
}