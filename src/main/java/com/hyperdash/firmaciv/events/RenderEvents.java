package com.hyperdash.firmaciv.events;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.FirmacivEntityRenderer.*;
import com.hyperdash.firmaciv.entity.custom.BoatVariant;
import com.hyperdash.firmaciv.entity.custom.entitymodel.CanoeEntityModel;
import com.hyperdash.firmaciv.entity.custom.entitymodel.KayakEntityModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.stream.Stream;


@Mod.EventBusSubscriber(modid = Firmaciv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class RenderEvents {

    private RenderEvents() {
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CanoeEntityModel.LAYER_LOCATION, CanoeEntityModel::createBodyLayer);
        event.registerLayerDefinition(KayakEntityModel.LAYER_LOCATION, KayakEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FirmacivEntities.CANOES.get(Stream.of(BoatVariant.values()).findFirst().get()).get(), CanoeRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.KAYAK_ENTITY.get(), KayakRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.EMPTY_COMPARTMENT_ENTITY.get(), CompartmentRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.CHEST_COMPARTMENT_ENTITY.get(), CompartmentRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.WORKBENCH_COMPARTMENT_ENTITY.get(), CompartmentRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.VEHICLE_PART_ENTITY.get(), VehiclePartRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.VEHICLE_CLEAT_ENTITY.get(), VehicleCleatRenderer::new);
    }


}
