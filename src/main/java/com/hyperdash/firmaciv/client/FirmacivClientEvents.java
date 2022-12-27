package com.hyperdash.firmaciv.client;

import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.entity.FirmacivBoatRenderer.FirmacivCanoeRenderer;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import net.dries007.tfc.common.blockentities.BarrelBlockEntity;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

import javax.swing.text.html.parser.Entity;
import java.util.Arrays;
import java.util.stream.Stream;

public class FirmacivClientEvents {

    public static void init(){
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(FirmacivClientEvents::clientSetup);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        //ItemBlockRenderTypes.setRenderLayer(FirmacivBlocks.CANOE_COMPONENT_BLOCKS.get(), RenderType.cutout());
        EntityRenderers.register(FirmacivEntities.CANOE_ENTITY.get(), FirmacivCanoeRenderer::new);

        for(CanoeEntity.Type type : CanoeEntity.Type.values()){
            EntityRenderers.register(FirmacivEntities.CANOES.get(type).get(), FirmacivCanoeRenderer::new);
        }

    }
}
