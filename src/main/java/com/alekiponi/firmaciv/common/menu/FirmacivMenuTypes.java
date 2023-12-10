package com.alekiponi.firmaciv.common.menu;

import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.alekiponi.firmaciv.Firmaciv.MOD_ID;

public final class FirmacivMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MOD_ID);

    public static final RegistryObject<MenuType<CompartmentCraftingMenu>> WORKBENCH = register("workbench",
            (windowId, inv, data) -> new CompartmentCraftingMenu(windowId, inv));

    @SuppressWarnings("SameParameterValue")
    private static <C extends AbstractContainerMenu> RegistryObject<MenuType<C>> register(final String name,
            final IContainerFactory<C> factory) {
        return RegistrationHelpers.registerContainer(MENUS, name, factory);
    }
}