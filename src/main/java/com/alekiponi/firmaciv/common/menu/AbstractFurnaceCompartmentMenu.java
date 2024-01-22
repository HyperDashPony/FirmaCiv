package com.alekiponi.firmaciv.common.menu;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla.AbstractFurnaceCompartmentEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * This class does something somewhat abnormal by replacing {@link #RESULT_SLOT} with an anonymous slot which will
 * allow anything extending {@link AbstractFurnaceCompartmentEntity} to give xp like their block counterparts.
 * <p>
 * This was done to avoid a mixin into {@link FurnaceResultSlot}
 */
public class AbstractFurnaceCompartmentMenu extends AbstractFurnaceMenu {

    @SuppressWarnings("unused")
    protected AbstractFurnaceCompartmentMenu(final MenuType<?> menuType,
            final RecipeType<? extends AbstractCookingRecipe> recipeType, final RecipeBookType bookType, final int id,
            final Inventory playerInventory) {
        this(menuType, recipeType, bookType, id, playerInventory, new SimpleContainer(SLOT_COUNT),
                new SimpleContainerData(DATA_COUNT));
    }

    protected AbstractFurnaceCompartmentMenu(final MenuType<?> menuType,
            final RecipeType<? extends AbstractCookingRecipe> recipeType, final RecipeBookType bookType, final int id,
            final Inventory playerInventory, final Container container, final ContainerData containerData) {
        super(menuType, recipeType, bookType, id, playerInventory, container, containerData);

        this.slots.set(RESULT_SLOT, new FurnaceResultSlot(playerInventory.player, container, RESULT_SLOT, 116, 35) {
            @Override
            protected void checkTakeAchievements(final ItemStack itemStack) {
                super.checkTakeAchievements(itemStack);
                if (this.player instanceof ServerPlayer serverplayer) {
                    if (this.container instanceof AbstractFurnaceCompartmentEntity furnaceCompartment) {
                        furnaceCompartment.awardUsedRecipesAndPopExperience(serverplayer);
                    }
                }
            }
        });
    }
}