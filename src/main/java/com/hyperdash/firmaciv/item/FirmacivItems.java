package com.hyperdash.firmaciv.item;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.item.custom.BarometerItem;
import com.hyperdash.firmaciv.item.custom.ClockItem;
import com.hyperdash.firmaciv.item.custom.KayakItem;
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
            () -> new SextantItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> NAV_CLOCK = ITEMS.register("nav_clock",
            () -> new ClockItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BAROMETER = ITEMS.register("barometer",
            () -> new BarometerItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> LARGE_WATERPROOF_HIDE = ITEMS.register("large_waterproof_hide",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> KAYAK_PADDLE = ITEMS.register("kayak_paddle",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CANOE_PADDLE = ITEMS.register("canoe_paddle",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> OAR = ITEMS.register("oar",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> KAYAK = ITEMS.register("kayak",
            () -> new KayakItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
