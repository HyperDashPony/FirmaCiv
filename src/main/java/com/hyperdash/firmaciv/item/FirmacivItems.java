package com.hyperdash.firmaciv.item;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.item.custom.SextantItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FirmacivItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Firmaciv.MOD_ID);

    /*
    public static final RegistryObject<Item> TESTITEM = ITEMS.register("testitem",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

     */

    public static final RegistryObject<Item> BRONZE_SEXTANT = ITEMS.register("bronze_sextant",
            () -> new SextantItem(new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).durability(64)));

    public static void  register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
