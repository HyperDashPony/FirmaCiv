package com.hyperdash.firmaciv.entity.FirmacivEntityRenderer;

import com.mojang.datafixers.util.Pair;
import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.util.Helpers;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

public class ToyBoatRenderer extends BoatRenderer {
    private final Pair<ResourceLocation, ListModel<Boat>> location;

    public static ModelLayerLocation boatName(String name) {
        return RenderHelpers.modelIdentifier("toy_boat/" + name);
    }

    public ToyBoatRenderer(EntityRendererProvider.Context context, String name) {
        this(context, Pair.of(Helpers.identifier("textures/entity/boat/" + name + ".png"), name.equals("palm") ? new RaftModel(context.bakeLayer(boatName(name))) : new BoatModel(context.bakeLayer(boatName(name)))));
    }

    public ToyBoatRenderer(EntityRendererProvider.Context context, Pair<ResourceLocation, ListModel<Boat>> pair) {
        super(context, false);
        this.location = pair;
    }

    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        return this.location;
    }
}
