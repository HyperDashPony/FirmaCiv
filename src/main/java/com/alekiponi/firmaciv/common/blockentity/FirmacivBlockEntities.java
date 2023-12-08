package com.alekiponi.firmaciv.common.blockentity;

import com.alekiponi.firmaciv.Firmaciv;
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

import static com.alekiponi.firmaciv.common.block.FirmacivBlocks.CANOE_COMPONENT_BLOCKS;

public final class FirmacivBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
            ForgeRegistries.BLOCK_ENTITY_TYPES, Firmaciv.MOD_ID);

    public static final RegistryObject<BlockEntityType<CanoeComponentBlockEntity>> CANOE_COMPONENT_BLOCK_ENTITY = register(
            "canoe_component_block_entity", CanoeComponentBlockEntity::new, CANOE_COMPONENT_BLOCKS.values().stream());

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(final String name,
            final BlockEntityType.BlockEntitySupplier<T> factory, final Supplier<? extends Block> block) {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(final String name,
            final BlockEntityType.BlockEntitySupplier<T> factory,
            final Stream<? extends Supplier<? extends Block>> blocks) {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }

    public static void register(final IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}