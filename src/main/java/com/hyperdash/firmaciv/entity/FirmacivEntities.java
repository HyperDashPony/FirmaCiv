package com.hyperdash.firmaciv.entity;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.block.blockentity.custom.CanoeComponentBlockEntity;
import com.hyperdash.firmaciv.block.custom.CanoeComponentBlock;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import net.dries007.tfc.common.blockentities.BarrelBlockEntity;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.entities.TFCBoat;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class FirmacivEntities {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, Firmaciv.MOD_ID);

    public static final RegistryObject<EntityType<CanoeEntity>> CANOE_ENTITY = ENTITY_TYPES.register("canoe_entity",
            () -> EntityType.Builder.of(CanoeEntity::new, MobCategory.MISC).sized(1.125F, 0.5625F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "canoe_entity").toString()));

    /*
    public static final RegistryObject<EntityType<CanoeEntity>> CANOES =
            ENTITY_TYPES.register("canoe/" + Stream.of(CanoeEntity.Type.class),
            () -> EntityType.Builder.of(CanoeEntity::new, MobCategory.MISC).sized(1.125F, 0.5625F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "canoe/" + Stream.of(CanoeEntity.Type.values()).toString()).toString()));

     */

    public static final Map<CanoeEntity.Type, RegistryObject<EntityType<CanoeEntity>>> CANOES =
            Helpers.mapOfKeys(CanoeEntity.Type.class, canoeWoodType -> ENTITY_TYPES.register("canoe/" + canoeWoodType.getName(),
                    () -> EntityType.Builder.of(CanoeEntity::new, MobCategory.MISC).sized(1.125F, 0.5625F)
                            .build(new ResourceLocation(Firmaciv.MOD_ID, "canoe/" + canoeWoodType.getName()).toString())));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    public static class ResLocation {
        public static final ResourceLocation CANOE_ENTITY = new ResourceLocation(Firmaciv.MOD_ID, "canoe_entity");
    }


}
