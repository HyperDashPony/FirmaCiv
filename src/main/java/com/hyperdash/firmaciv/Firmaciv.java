package com.hyperdash.firmaciv;

import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.block.blockentity.FirmacivBlockEntities;
import com.hyperdash.firmaciv.client.FirmacivClientEvents;
import com.hyperdash.firmaciv.events.config.FirmacivConfig;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.item.FirmacivItems;
import com.hyperdash.firmaciv.item.FirmacivTabs;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Firmaciv.MOD_ID)
public class Firmaciv
{
    public static final String MOD_ID = "firmaciv";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Firmaciv()
    {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        FirmacivTabs.register(eventBus);

        FirmacivItems.register(eventBus);
        FirmacivBlocks.register(eventBus);
        FirmacivBlockEntities.register(eventBus);
        FirmacivEntities.ENTITY_TYPES.register(eventBus);

        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        eventBus.addListener(this::addCreative);
        FirmacivConfig.init();
        if(FMLEnvironment.dist == Dist.CLIENT){
            FirmacivClientEvents.init();
        }
    }

    /*
    public static final CreativeModeTab FIRMACIV_TAB = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return FirmacivItems.SEXTANT.get().getDefaultInstance();
        }
    };
    */
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    /*
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
        }


    }

     */
}
