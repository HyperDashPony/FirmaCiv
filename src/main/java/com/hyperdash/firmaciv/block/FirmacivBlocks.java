package com.hyperdash.firmaciv.block;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.block.custom.CanoeComponentBlock;
import com.hyperdash.firmaciv.block.custom.CanoeFire;
import com.hyperdash.firmaciv.item.FirmacivItems;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class FirmacivBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Firmaciv.MOD_ID);

    public static final Map<CanoeComponentBlock.CanoeWoodType, RegistryObject<CanoeComponentBlock>> CANOE_COMPONENT_BLOCKS =
            Helpers.mapOfKeys(CanoeComponentBlock.CanoeWoodType.class, canoeWoodType -> registerBlockWithoutItem("canoe_component_block/" + canoeWoodType.name().toLowerCase(Locale.ROOT),
                    () -> new CanoeComponentBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_HYPHAE).noOcclusion().color(MaterialColor.COLOR_BROWN), canoeWoodType.stripped, canoeWoodType.lumber)));

    public static final RegistryObject<Block> CANOE_FIRE = registerBlockWithItem("canoe_fire",
            () -> new CanoeFire(BlockBehaviour.Properties.copy(Blocks.FIRE)),
            CreativeModeTab.TAB_TRANSPORTATION);

    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return FirmacivItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
