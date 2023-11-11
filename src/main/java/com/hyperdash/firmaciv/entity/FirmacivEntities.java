package com.hyperdash.firmaciv.entity;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import com.hyperdash.firmaciv.entity.custom.KayakEntity;
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

    public static final Map<CanoeEntity.Type, RegistryObject<EntityType<CanoeEntity>>> CANOES =
            Helpers.mapOfKeys(CanoeEntity.Type.class, canoeWoodType -> ENTITY_TYPES.register("dugout_canoe/" + canoeWoodType.getName(),
                    () -> EntityType.Builder.of(CanoeEntity::new, MobCategory.MISC).sized(1.125F, 0.625F)
                            .build(new ResourceLocation(Firmaciv.MOD_ID, "dugout_canoe/" + canoeWoodType.getName()).toString())));

    public static final RegistryObject<EntityType<KayakEntity>> KAYAK_ENTITY = ENTITY_TYPES.register("kayak",
            () -> EntityType.Builder.of(KayakEntity::new, MobCategory.MISC).sized(0.8F, 0.625F)
                    .build(new ResourceLocation(Firmaciv.MOD_ID, "kayak").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    public static class ResLocation {
        public static final ResourceLocation CANOE_ENTITY = new ResourceLocation(Firmaciv.MOD_ID, "canoe_entity");
    }


}
