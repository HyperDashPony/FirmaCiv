package com.alekiponi.firmaciv.common.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;

public class FurnaceCompartmentMenu extends AbstractFurnaceCompartmentMenu {

    public FurnaceCompartmentMenu(final int containerID, final Inventory inventory, final Container container,
            final ContainerData containerData) {
        super(MenuType.FURNACE, RecipeType.SMELTING, RecipeBookType.FURNACE, containerID, inventory, container,
                containerData);
    }
}