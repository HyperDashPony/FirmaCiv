package com.alekiponi.firmaciv.common.entity;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.KayakEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.RowboatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCollisionEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehiclePartEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AnvilCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.ChestCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.WorkbenchCompartmentEntity;
import com.alekiponi.firmaciv.util.BoatVariant;
import net.dries007.tfc.util.Helpers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.Map;

import static com.alekiponi.firmaciv.Firmaciv.MOD_ID;

public final class FirmacivEntities {

    private static final int LARGE_VEHICLE_TRACKING = 20;
    private static final int VEHICLE_HELPER_TRACKING = LARGE_VEHICLE_TRACKING + 1;
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            Firmaciv.MOD_ID);

    public static final Map<BoatVariant, RegistryObject<EntityType<CanoeEntity>>> CANOES = Helpers.mapOfKeys(
            BoatVariant.class, variant -> ENTITY_TYPES.register("dugout_canoe/" + variant.getName(),
                    () -> EntityType.Builder.of(CanoeEntity::new, MobCategory.MISC).sized(1.125F, 0.625F)
                            .build(new ResourceLocation(Firmaciv.MOD_ID,
                                    "dugout_canoe/" + variant.getName()).toString())));

    public static final Map<BoatVariant, RegistryObject<EntityType<RowboatEntity>>> ROWBOATS = Helpers.mapOfKeys(
            BoatVariant.class, variant -> ENTITY_TYPES.register("rowboat/" + variant.getName(),
                    () -> EntityType.Builder.of(RowboatEntity::new, MobCategory.MISC).sized(1.875F, 0.625F)
                            .build(new ResourceLocation(Firmaciv.MOD_ID, "rowboat/" + variant.getName()).toString())));

    public static final RegistryObject<EntityType<KayakEntity>> KAYAK_ENTITY = ENTITY_TYPES.register("kayak",
            () -> EntityType.Builder.of(KayakEntity::new, MobCategory.MISC).sized(0.79F, 0.625F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "kayak").toString()));

    public static final RegistryObject<EntityType<SloopEntity>> SLOOP = ENTITY_TYPES.register("sloop",
            () -> EntityType.Builder.of(SloopEntity::new, MobCategory.MISC).sized(3F, 0.75F)
                    .setTrackingRange(LARGE_VEHICLE_TRACKING)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "sloop").toString()));

    public static final RegistryObject<EntityType<EmptyCompartmentEntity>> EMPTY_COMPARTMENT_ENTITY = ENTITY_TYPES.register(
            "compartment_empty",
            () -> EntityType.Builder.of(EmptyCompartmentEntity::new, MobCategory.MISC).sized(0.6F, 0.7F).noSummon()
                    .build(new ResourceLocation(MOD_ID, "compartment_empty").toString()));

    public static final RegistryObject<EntityType<ChestCompartmentEntity>> CHEST_COMPARTMENT_ENTITY = ENTITY_TYPES.register(
            "compartment_chest",
            () -> EntityType.Builder.of(ChestCompartmentEntity::new, MobCategory.MISC).sized(0.6F, 0.7F).noSummon()
                    .build(new ResourceLocation(MOD_ID, "compartment_chest").toString()));

    public static final RegistryObject<EntityType<WorkbenchCompartmentEntity>> WORKBENCH_COMPARTMENT_ENTITY = register(
            "compartment_workbench",
            EntityType.Builder.of(WorkbenchCompartmentEntity::new, MobCategory.MISC).sized(0.6F, 0.7F).noSummon());

    public static final RegistryObject<EntityType<AnvilCompartmentEntity>> ANVIL_COMPARTMENT_ENTITY = ENTITY_TYPES.register(
            "compartment_anvil",
            () -> EntityType.Builder.of(AnvilCompartmentEntity::new, MobCategory.MISC).sized(0.6F, 0.7F).noSummon()
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "compartment_anvil").toString()));

    public static final RegistryObject<EntityType<BoatVehiclePart>> BOAT_VEHICLE_PART = ENTITY_TYPES.register(
            "vehicle_part_boat", () -> EntityType.Builder.of(BoatVehiclePart::new, MobCategory.MISC).sized(0, 0)
                    .setTrackingRange(VEHICLE_HELPER_TRACKING).noSummon()
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "vehicle_part_boat").toString()));

    public static final RegistryObject<EntityType<VehicleCleatEntity>> VEHICLE_CLEAT_ENTITY = ENTITY_TYPES.register(
            "vehicle_cleat", () -> EntityType.Builder.of(VehicleCleatEntity::new, MobCategory.MISC).sized(0.4F, 0.2F)
                    .setTrackingRange(VEHICLE_HELPER_TRACKING).noSummon()
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "vehicle_cleat").toString()));

    public static final RegistryObject<EntityType<VehicleCollisionEntity>> VEHICLE_COLLISION_ENTITY = ENTITY_TYPES.register(
            "vehicle_collider",
            () -> EntityType.Builder.of(VehicleCollisionEntity::new, MobCategory.MISC).sized(1, 1).noSummon()
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "vehicle_collider").toString()));

    public static final RegistryObject<EntityType<SailSwitchEntity>> SAIL_SWITCH_ENTITY = ENTITY_TYPES.register(
            "vehicle_switch_sail",
            () -> EntityType.Builder.of(SailSwitchEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).noSummon()
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "vehicle_switch_sail").toString()));

    public static final RegistryObject<EntityType<AnchorEntity>> ANCHOR_ENTITY = ENTITY_TYPES.register("vehicle_anchor",
            () -> EntityType.Builder.of(AnchorEntity::new, MobCategory.MISC).sized(1, 1)
                    .clientTrackingRange(VEHICLE_HELPER_TRACKING).noSummon()
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "vehicle_anchor").toString()));

    public static final RegistryObject<EntityType<WindlassSwitchEntity>> WINDLASS_SWITCH_ENTITY = ENTITY_TYPES.register(
            "vehicle_switch_windlass",
            () -> EntityType.Builder.of(WindlassSwitchEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .setTrackingRange(VEHICLE_HELPER_TRACKING).noSummon()
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "vehicle_switch_windlass").toString()));

    public static final RegistryObject<EntityType<CannonballEntity>> CANNONBALL_ENTITY = ENTITY_TYPES.register(
            "cannonball",
            () -> EntityType.Builder.of(CannonballEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setTrackingRange(32)
                    .clientTrackingRange(32).noSummon()
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "cannonball").toString()));

    public static final RegistryObject<EntityType<CannonEntity>> CANNON_ENTITY = ENTITY_TYPES.register("cannon",
            () -> EntityType.Builder.of(CannonEntity::new, MobCategory.MISC).sized(0.8F, 0.8F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "cannon").toString()));

    public static final RegistryObject<EntityType<MastEntity>> MAST_ENTITY = ENTITY_TYPES.register("vehicle_mast",
            () -> EntityType.Builder.of(MastEntity::new, MobCategory.MISC).sized(0.3F, 8F)
                    .setTrackingRange(VEHICLE_HELPER_TRACKING).noSummon()
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "vehicle_mast").toString()));

    private static <E extends Entity> RegistryObject<EntityType<E>> register(final String name,
            final EntityType.Builder<E> builder) {
        return register(name, builder, true);
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> register(final String name,
            final EntityType.Builder<E> builder, final boolean serialize) {
        final String id = name.toLowerCase(Locale.ROOT);
        return ENTITY_TYPES.register(id, () -> {
            if (!serialize) builder.noSave();
            return builder.build(MOD_ID + ":" + id);
        });
    }
}