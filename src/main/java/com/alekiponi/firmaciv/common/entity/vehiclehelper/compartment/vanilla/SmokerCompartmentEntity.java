package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.firmaciv.common.menu.AbstractFurnaceCompartmentMenu;
import com.alekiponi.firmaciv.common.menu.SmokerCompartmentMenu;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class SmokerCompartmentEntity extends AbstractFurnaceCompartmentEntity {

    public SmokerCompartmentEntity(final EntityType<? extends SmokerCompartmentEntity> entityType,
            final Level level) {
        super(entityType, level, RecipeType.SMOKING);
    }

    public SmokerCompartmentEntity(final CompartmentType<? extends SmokerCompartmentEntity> entityType,
            final Level level, final ItemStack itemStack) {
        super(entityType, level, RecipeType.SMOKING, itemStack);
    }

    @Override
    protected AbstractFurnaceCompartmentMenu createMenu(final int id, final Inventory playerInventory) {
        return new SmokerCompartmentMenu(id, playerInventory, this, this.dataAccess);
    }
}