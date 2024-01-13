package com.alekiponi.firmaciv.common.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;

public class SmokerCompartmentMenu extends AbstractFurnaceCompartmentMenu {
    public SmokerCompartmentMenu(final int id, final Inventory playerInventory, final Container container,
            final ContainerData containerData) {
        super(MenuType.SMOKER, RecipeType.SMOKING, RecipeBookType.SMOKER, id, playerInventory, container,
                containerData);
    }
}