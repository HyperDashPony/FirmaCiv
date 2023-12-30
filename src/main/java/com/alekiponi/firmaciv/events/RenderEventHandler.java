package com.alekiponi.firmaciv.events;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.CanoeEntityModel;
import com.alekiponi.firmaciv.client.model.entity.KayakEntityModel;
import com.alekiponi.firmaciv.client.model.entity.RowboatEntityModel;
import com.alekiponi.firmaciv.client.render.entity.*;
import com.alekiponi.firmaciv.util.BoatVariant;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Firmaciv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class RenderEventHandler {



    private RenderEventHandler() {
    }



    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CanoeEntityModel.LAYER_LOCATION, CanoeEntityModel::createBodyLayer);
        event.registerLayerDefinition(KayakEntityModel.LAYER_LOCATION, KayakEntityModel::createBodyLayer);
        event.registerLayerDefinition(RowboatEntityModel.LAYER_LOCATION, RowboatEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (final BoatVariant boatVariant : BoatVariant.values()) {
            event.registerEntityRenderer(FirmacivEntities.CANOES.get(boatVariant).get(), CanoeRenderer::new);
            event.registerEntityRenderer(FirmacivEntities.ROWBOATS.get(boatVariant).get(), RowboatRenderer::new);
        }
        event.registerEntityRenderer(FirmacivEntities.KAYAK_ENTITY.get(), KayakRenderer::new);

        event.registerEntityRenderer(FirmacivEntities.EMPTY_COMPARTMENT_ENTITY.get(), CompartmentRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.CHEST_COMPARTMENT_ENTITY.get(), CompartmentRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.WORKBENCH_COMPARTMENT_ENTITY.get(), CompartmentRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.ANVIL_COMPARTMENT_ENTITY.get(), CompartmentRenderer::new);

        event.registerEntityRenderer(FirmacivEntities.VEHICLE_PART_ENTITY.get(), VehiclePartRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.VEHICLE_CLEAT_ENTITY.get(), VehicleCleatRenderer::new);
        event.registerEntityRenderer(FirmacivEntities.VEHICLE_COLLISION_ENTITY.get(), VehicleCollisionEntityRenderer::new);

        //event.registerEntityRenderer(FirmacivEntities.OUTRIGGER.get(), OutriggerRenderer::new);

        event.registerEntityRenderer(FirmacivEntities.SLOOP.get(), SloopRenderer::new);

    }


}
