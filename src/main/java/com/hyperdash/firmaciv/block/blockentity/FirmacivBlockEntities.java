package com.hyperdash.firmaciv.block.blockentity;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.block.blockentity.custom.CanoeComponentBlockEntity;
import com.hyperdash.firmaciv.entity.custom.BoatVariant;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.hyperdash.firmaciv.block.FirmacivBlocks.CANOE_COMPONENT_BLOCKS;

public class FirmacivBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES;

    public static final RegistryObject<BlockEntityType<CanoeComponentBlockEntity>> CANOE_COMPONENT_BLOCK_ENTITY;

    static {
        BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Firmaciv.MOD_ID);

        CANOE_COMPONENT_BLOCK_ENTITY = register("canoe_component_block_entity", CanoeComponentBlockEntity::new,
                Stream.of(BoatVariant.values()).map(CANOE_COMPONENT_BLOCKS::get));


    }

    public FirmacivBlockEntities() {
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name,
                                                                                       BlockEntityType.BlockEntitySupplier<T> factory,
                                                                                       Supplier<? extends Block> block) {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name,
                                                                                       BlockEntityType.BlockEntitySupplier<T> factory,
                                                                                       Stream<? extends Supplier<? extends Block>> blocks) {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }


}
