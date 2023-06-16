package com.hyperdash.firmaciv.item;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FirmacivItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Firmaciv.MOD_ID);

    public static final RegistryObject<Item> SEXTANT = ITEMS.register("sextant",
            () -> new SextantItem(new Item.Properties().tab(Firmaciv.FIRMACIV_TAB).stacksTo(1)));

    public static final RegistryObject<Item> NAV_CLOCK = ITEMS.register("nav_clock",
            () -> new ClockItem(new Item.Properties().tab(Firmaciv.FIRMACIV_TAB).stacksTo(1)));

    public static final RegistryObject<Item> BAROMETER = ITEMS.register("barometer",
            () -> new BarometerItem(new Item.Properties().tab(Firmaciv.FIRMACIV_TAB).stacksTo(1)));

    public static final RegistryObject<Item> LARGE_WATERPROOF_HIDE = ITEMS.register("large_waterproof_hide",
            () -> new Item(new Item.Properties().tab(Firmaciv.FIRMACIV_TAB)));

    public static final RegistryObject<Item> KAYAK_PADDLE = ITEMS.register("kayak_paddle",
            () -> new Item(new Item.Properties().tab(Firmaciv.FIRMACIV_TAB).stacksTo(1)));

    public static final RegistryObject<Item> CANOE_PADDLE = ITEMS.register("canoe_paddle",
            () -> new Item(new Item.Properties().tab(Firmaciv.FIRMACIV_TAB).stacksTo(1)));

    public static final RegistryObject<Item> KAYAK = ITEMS.register("kayak",
            () -> new KayakItem(new Item.Properties().tab(Firmaciv.FIRMACIV_TAB).stacksTo(1)));

    public static void  register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
