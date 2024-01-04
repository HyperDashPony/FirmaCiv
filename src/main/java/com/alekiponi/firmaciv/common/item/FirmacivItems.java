package com.alekiponi.firmaciv.common.item;

import com.alekiponi.firmaciv.Firmaciv;
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
            () -> new NavClockItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BAROMETER = ITEMS.register("barometer",
            () -> new BarometerItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> UNFINISHED_SEXTANT = ITEMS.register("unfinished_sextant",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> UNFINISHED_NAV_CLOCK = ITEMS.register("unfinished_nav_clock",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> UNFINISHED_BAROMETER = ITEMS.register("unfinished_barometer",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FIRMACIV_COMPASS = ITEMS.register("firmaciv_compass",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> LARGE_WATERPROOF_HIDE = ITEMS.register("large_waterproof_hide",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> KAYAK_PADDLE = ITEMS.register("kayak_paddle",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CANOE_PADDLE = ITEMS.register("canoe_paddle",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CANNONBALL = ITEMS.register("cannonball",
            () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> CANNON = ITEMS.register("cannon",
            () -> new CannonItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> OAR = ITEMS.register("oar",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> COPPER_BOLT = ITEMS.register("copper_bolt",
            () -> new Item(new Item.Properties().stacksTo(64)));


    public static final RegistryObject<Item> KAYAK = ITEMS.register("kayak",
            () -> new KayakItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> KAYAK_WITH_PADDLE_ICON_ONLY = ITEMS.register("kayak_with_paddle_icon_only",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CANOE_ICON_ONLY = ITEMS.register("canoe_icon_only",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CANOE_WITH_PADDLE_ICON_ONLY = ITEMS.register("canoe_with_paddle_icon_only",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ROWBOAT_ICON_ONLY = ITEMS.register("rowboat_icon_only",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
