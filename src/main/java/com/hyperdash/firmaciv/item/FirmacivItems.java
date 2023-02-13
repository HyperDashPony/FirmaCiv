package com.hyperdash.firmaciv.item;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.item.custom.BarometerItem;
import com.hyperdash.firmaciv.item.custom.ClockItem;
import com.hyperdash.firmaciv.item.custom.NavToolkitItem;
import com.hyperdash.firmaciv.item.custom.SextantItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FirmacivItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Firmaciv.MOD_ID);

    public static final RegistryObject<Item> SEXTANT = ITEMS.register("sextant",
            () -> new SextantItem(new Item.Properties().tab(Firmaciv.FIRMACIV_TAB)));

    public static final RegistryObject<Item> NAV_CLOCK = ITEMS.register("nav_clock",
            () -> new ClockItem(new Item.Properties().tab(Firmaciv.FIRMACIV_TAB)));

    public static final RegistryObject<Item> BAROMETER = ITEMS.register("barometer",
            () -> new BarometerItem(new Item.Properties().tab(Firmaciv.FIRMACIV_TAB)));

    public static final RegistryObject<Item> NAV_TOOLKIT = ITEMS.register("nav_toolkit",
            () -> new NavToolkitItem(new Item.Properties().tab(Firmaciv.FIRMACIV_TAB)));

    public static void  register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
