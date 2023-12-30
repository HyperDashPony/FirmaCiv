package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment;

import com.alekiponi.firmaciv.common.menu.CompartmentCraftingMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.BiFunction;

public class WorkbenchCompartmentEntity extends AbstractCompartmentEntity implements HasCustomInventoryScreen {
    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");

    public WorkbenchCompartmentEntity(final EntityType<?> entityType, final Level level) {
        super(entityType, level);
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
        this.openCustomInventoryScreen(player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void openCustomInventoryScreen(final Player player) {
        // Stupid, gross, dirty way of letting vanilla do most of the container work
        class WorkbenchLevelAccess implements ContainerLevelAccess {
            private final WorkbenchCompartmentEntity compartment;

            public WorkbenchLevelAccess(final WorkbenchCompartmentEntity compartment) {
                this.compartment = compartment;
            }

            @Override
            public <T> Optional<T> evaluate(final BiFunction<Level, BlockPos, T> function) {
                return Optional.of(function.apply(compartment.level(), compartment.blockPosition()));
            }
        }

        player.openMenu(new SimpleMenuProvider(
                (containerID, playerInventory, unused) -> new CompartmentCraftingMenu(containerID, playerInventory,
                        new WorkbenchLevelAccess(this)), CONTAINER_TITLE));
        player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
    }
}