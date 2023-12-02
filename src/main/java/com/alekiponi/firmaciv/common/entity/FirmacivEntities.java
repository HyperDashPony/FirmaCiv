package com.alekiponi.firmaciv.common.entity;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.*;
import net.dries007.tfc.util.Helpers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public final class FirmacivEntities {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Firmaciv.MOD_ID);

    public static final Map<BoatVariant, RegistryObject<EntityType<CanoeEntity>>> CANOES =
            Helpers.mapOfKeys(BoatVariant.class, variant -> ENTITY_TYPES.register("dugout_canoe/" + variant.getName(),
                    () -> EntityType.Builder.of(CanoeEntity::new, MobCategory.MISC).sized(1.125F, 0.625F)
                            .build(new ResourceLocation(Firmaciv.MOD_ID,
                                    "dugout_canoe/" + variant.getName()).toString())));

    public static final Map<BoatVariant, RegistryObject<EntityType<RowboatEntity>>> ROWBOATS =
            Helpers.mapOfKeys(BoatVariant.class, variant -> ENTITY_TYPES.register("rowboat/" + variant.getName(),
                    () -> EntityType.Builder.of(RowboatEntity::new, MobCategory.MISC).sized(1.875F, 0.625F)
                            .build(new ResourceLocation(Firmaciv.MOD_ID, "rowboat/" + variant.getName()).toString())));

    public static final RegistryObject<EntityType<KayakEntity>> KAYAK_ENTITY = ENTITY_TYPES.register("kayak",
            () -> EntityType.Builder.of(KayakEntity::new, MobCategory.MISC).sized(0.79F, 0.625F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "kayak").toString()));


    public static final RegistryObject<EntityType<EmptyCompartmentEntity>> EMPTY_COMPARTMENT_ENTITY = ENTITY_TYPES.register(
            "compartment_empty",
            () -> EntityType.Builder.of(EmptyCompartmentEntity::new, MobCategory.MISC).sized(0.6F, 0.7F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "compartment_empty").toString()));

    public static final RegistryObject<EntityType<ChestCompartmentEntity>> CHEST_COMPARTMENT_ENTITY = ENTITY_TYPES.register(
            "compartment_chest",
            () -> EntityType.Builder.of(ChestCompartmentEntity::new, MobCategory.MISC).sized(0.6F, 0.7F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "compartment_chest").toString()));

    public static final RegistryObject<EntityType<WorkbenchCompartmentEntity>> WORKBENCH_COMPARTMENT_ENTITY = ENTITY_TYPES.register(
            "compartment_workbench",
            () -> EntityType.Builder.of(WorkbenchCompartmentEntity::new, MobCategory.MISC).sized(0.6F, 0.7F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "compartment_workbench").toString()));

    public static final RegistryObject<EntityType<VehiclePartEntity>> VEHICLE_PART_ENTITY = ENTITY_TYPES.register(
            "vehicle_part",
            () -> EntityType.Builder.of(VehiclePartEntity::new, MobCategory.MISC).sized(0.025F, 0.025F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "vehicle_part").toString()));

    public static final RegistryObject<EntityType<VehicleCleatEntity>> VEHICLE_CLEAT_ENTITY = ENTITY_TYPES.register(
            "vehicle_cleat",
            () -> EntityType.Builder.of(VehicleCleatEntity::new, MobCategory.MISC).sized(0.4F, 0.4F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "vehicle_cleat").toString()));

    /*
    public static final RegistryObject<EntityType<FirmacivBoatEntity>> OUTRIGGER = ENTITY_TYPES.register("outrigger",
            () -> EntityType.Builder.of(FirmacivBoatEntity::new, MobCategory.MISC).sized(0.79F, 0.625F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "outrigger").toString()));


    public static final RegistryObject<EntityType<SloopEntity>> SLOOP = ENTITY_TYPES.register("sloop",
            () -> EntityType.Builder.of(SloopEntity::new, MobCategory.MISC).sized(4F, 0.75F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "sloop").toString()));


     */

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }


}
