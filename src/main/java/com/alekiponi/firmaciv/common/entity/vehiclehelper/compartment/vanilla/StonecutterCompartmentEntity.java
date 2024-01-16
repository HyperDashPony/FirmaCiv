package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.BlockCompartmentEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiFunction;

public class StonecutterCompartmentEntity extends BlockCompartmentEntity implements HasCustomInventoryScreen, MenuProvider {

    public StonecutterCompartmentEntity(final EntityType<? extends StonecutterCompartmentEntity> entityType,
            final Level level) {
        super(entityType, level);
    }

    public StonecutterCompartmentEntity(final CompartmentType<? extends StonecutterCompartmentEntity> entityType,
            final Level level, final ItemStack itemStack) {
        super(entityType, level, itemStack);
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        this.openCustomInventoryScreen(player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void openCustomInventoryScreen(final Player player) {
        player.openMenu(this);
        player.awardStat(Stats.INTERACT_WITH_STONECUTTER);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(final int id, final Inventory playerInventory,
            final Player player) {
        return new StonecutterMenu(id, playerInventory, new ContainerLevelAccess() {
            @Override
            public <T> Optional<T> evaluate(final BiFunction<Level, BlockPos, T> function) {
                return Optional.of(function.apply(StonecutterCompartmentEntity.this.level(),
                        StonecutterCompartmentEntity.this.blockPosition()));
            }
        }) {
            @Override
            public boolean stillValid(final Player player) {
                final BlockPos blockPos = StonecutterCompartmentEntity.this.blockPosition();
                return player.distanceToSqr(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5) <= 64;
            }
        };
    }
}