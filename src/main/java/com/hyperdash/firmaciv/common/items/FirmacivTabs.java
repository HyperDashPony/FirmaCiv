package com.hyperdash.firmaciv.common.items;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.common.blocks.FirmacivBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FirmacivTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Firmaciv.MOD_ID);


    public static final RegistryObject<CreativeModeTab> FIRMACIV_TAB = CREATIVE_MODE_TABS.register("firmaciv_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(FirmacivItems.SEXTANT.get()))
                    .title(Component.translatable("creativetab.firmaciv_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(FirmacivItems.SEXTANT.get());
                        pOutput.accept(FirmacivItems.UNFINISHED_SEXTANT.get());
                        pOutput.accept(FirmacivItems.NAV_CLOCK.get());
                        pOutput.accept(FirmacivItems.UNFINISHED_NAV_CLOCK.get());
                        pOutput.accept(FirmacivItems.BAROMETER.get());
                        pOutput.accept(FirmacivItems.UNFINISHED_BAROMETER.get());

                        pOutput.accept(FirmacivItems.KAYAK.get());
                        pOutput.accept(FirmacivItems.KAYAK_PADDLE.get());
                        pOutput.accept(FirmacivItems.CANOE_PADDLE.get());
                        pOutput.accept(FirmacivItems.OAR.get());
                        pOutput.accept(FirmacivBlocks.OARLOCK.get());
                        pOutput.accept(FirmacivItems.COPPER_BOLT.get());

                        //pOutput.accept(FirmacivBlocks.BOAT_FRAME.get());
                        //pOutput.accept(FirmacivBlocks.THATCH_ROOFING.get());

                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register((eventBus));
    }

}
