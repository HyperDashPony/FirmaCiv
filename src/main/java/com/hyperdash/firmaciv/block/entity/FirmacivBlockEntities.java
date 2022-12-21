package com.hyperdash.firmaciv.block.entity;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.block.custom.CanoeComponentBlock;
import com.hyperdash.firmaciv.block.entity.custom.CanoeComponentBlockEntity;
import net.dries007.tfc.common.blockentities.*;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.Registry;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FirmacivBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES;
    public static final RegistryObject<BlockEntityType<CanoeComponentBlockEntity>> CANOE_COMPONENT_BLOCK_ENTITIES;

    public FirmacivBlockEntities() {
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block) {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks) {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }



    static {
        BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, "tfc");
        CANOE_COMPONENT_BLOCK_ENTITIES = register("canoe_component_block_entities", CanoeComponentBlockEntity::new, TFCBlocks.WOODS.values().stream().map((map) -> {
            return (RegistryObject)map.get(Wood.BlockType.CHEST);
        }));
    }


}
