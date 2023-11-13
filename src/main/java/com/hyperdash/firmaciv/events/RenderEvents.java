package com.hyperdash.firmaciv.events;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.FirmacivBoatRenderer.FirmacivCanoeRenderer;
import com.hyperdash.firmaciv.entity.FirmacivBoatRenderer.FirmacivKayakRenderer;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.BoatVariant;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import com.hyperdash.firmaciv.entity.custom.FirmacivBoatEntity;
import com.hyperdash.firmaciv.entity.custom.KayakEntity;
import com.hyperdash.firmaciv.entity.custom.entitymodel.CanoeEntityModel;
import com.hyperdash.firmaciv.entity.custom.entitymodel.KayakEntityModel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.stream.Stream;


@Mod.EventBusSubscriber(modid = Firmaciv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class RenderEvents {

    private RenderEvents(){}

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(CanoeEntityModel.LAYER_LOCATION, CanoeEntityModel::createBodyLayer);
        event.registerLayerDefinition(KayakEntityModel.LAYER_LOCATION, KayakEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(FirmacivEntities.CANOES.get(Stream.of(BoatVariant.values()).findFirst().get()).get(), FirmacivCanoeRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.KAYAK_ENTITY.get(), FirmacivKayakRenderer::new);
    }



}
