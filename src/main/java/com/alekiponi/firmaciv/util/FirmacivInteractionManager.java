package com.alekiponi.firmaciv.util;

import com.alekiponi.firmaciv.events.config.FirmacivConfig;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.crafting.Ingredient;

import net.dries007.tfc.util.InteractionManager;

public class FirmacivInteractionManager
{
    public static void init()
    {
        InteractionManager.register(Ingredient.of(ItemTags.BOATS), true, ((stack, context) -> {
            if (FirmacivConfig.SERVER.disableVanillaBoatFunctionality.get())
            {
                return InteractionResult.FAIL;
            }
            return stack.useOn(context);
        }));
    }
}
