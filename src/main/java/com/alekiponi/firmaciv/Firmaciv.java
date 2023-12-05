package com.alekiponi.firmaciv;

import com.alekiponi.firmaciv.client.FirmacivClientEvents;
import com.alekiponi.firmaciv.common.block.FirmacivBlocks;
import com.alekiponi.firmaciv.common.blockentity.FirmacivBlockEntities;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.common.item.FirmacivTabs;
import com.alekiponi.firmaciv.events.config.FirmacivConfig;
import com.alekiponi.firmaciv.util.FirmacivInteractionManager;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;


@Mod(Firmaciv.MOD_ID)
public class Firmaciv {
    public static final String MOD_ID = "firmaciv";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Firmaciv() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        FirmacivTabs.register(eventBus);

        FirmacivItems.register(eventBus);
        FirmacivBlocks.register(eventBus);
        FirmacivBlockEntities.register(eventBus);
        FirmacivEntities.ENTITY_TYPES.register(eventBus);

        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        eventBus.addListener(this::addCreative);
        FirmacivConfig.init();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            FirmacivClientEvents.init();
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            FirmacivInteractionManager.init();
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }


}
