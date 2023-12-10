package com.alekiponi.firmaciv.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

/**
 * Essentially a wrapper of {@link CraftingMenu} as {@link CraftingMenu#stillValid(Player)} is hardcoded for the crafting table
 */
public class CompartmentCraftingMenu extends CraftingMenu {

    private final ContainerLevelAccess access;

    public CompartmentCraftingMenu(final int pContainerId, final Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }

    public CompartmentCraftingMenu(final int pContainerId, final Inventory pPlayerInventory,
            final ContainerLevelAccess pAccess) {
        super(pContainerId, pPlayerInventory, pAccess);
        this.access = pAccess;
    }

    @Override
    public boolean stillValid(final Player player) {
        return this.access.evaluate(
                (level, blockPos) -> player.distanceToSqr(blockPos.getX() + 0.5, blockPos.getY() + 0.5,
                        blockPos.getZ() + 0.5) <= 64, false);
    }
}