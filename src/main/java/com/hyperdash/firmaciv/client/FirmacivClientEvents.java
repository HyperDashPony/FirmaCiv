package com.hyperdash.firmaciv.client;

import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.entity.FirmacivBoatRenderer.FirmacivBoatRenderer;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class FirmacivClientEvents {

    public static void init(){
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(FirmacivClientEvents::clientSetup);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get(), RenderType.cutout());
        EntityRenderers.register(FirmacivEntities.CANOE_ENTITY.get(), FirmacivBoatRenderer::new);

    }
}
