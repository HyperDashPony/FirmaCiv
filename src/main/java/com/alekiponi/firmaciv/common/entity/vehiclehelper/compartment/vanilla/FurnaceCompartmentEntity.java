package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.firmaciv.common.menu.AbstractFurnaceCompartmentMenu;
import com.alekiponi.firmaciv.common.menu.FurnaceCompartmentMenu;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class FurnaceCompartmentEntity extends AbstractFurnaceCompartmentEntity {

    public FurnaceCompartmentEntity(final EntityType<? extends FurnaceCompartmentEntity> entityType,
            final Level level) {
        super(entityType, level, RecipeType.SMELTING);
    }

    public FurnaceCompartmentEntity(final CompartmentType<? extends FurnaceCompartmentEntity> entityType,
            final Level level, final ItemStack itemStack) {
        super(entityType, level, RecipeType.SMELTING, itemStack);
    }

    @Override
    protected AbstractFurnaceCompartmentMenu createMenu(final int id, final Inventory playerInventory) {
        return new FurnaceCompartmentMenu(id, playerInventory, this, this.dataAccess);
    }
}