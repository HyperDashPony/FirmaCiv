package com.hyperdash.firmaciv.block;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.block.custom.CanoeComponentBlock;
import com.hyperdash.firmaciv.item.FirmacivItems;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.util.Helpers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
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
                    () -> new CanoeComponentBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).noOcclusion(), canoeWoodType)));


    public static final RegistryObject<Block> THATCH_ROOFING = registerBlockWithItem("thatch_roofing",
            () -> new SquaredAngleBlock(Blocks.ACACIA_STAIRS.defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(0.6F, 0.4F).noOcclusion().isViewBlocking(TFCBlocks::never).sound(TFCSounds.THATCH).noCollission()));

    public static final RegistryObject<Block> THATCH_ROOFING_STAIRS = registerBlockWithItem("thatch_roofing_stairs",
            () -> new StairBlock(Blocks.ACACIA_STAIRS.defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(0.6F, 0.4F).noOcclusion().isViewBlocking(TFCBlocks::never).sound(TFCSounds.THATCH).noCollission()));

    public static final RegistryObject<Block> THATCH_ROOFING_SLAB = registerBlockWithItem("thatch_roofing_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(0.6F, 0.4F).noOcclusion().isViewBlocking(TFCBlocks::never).sound(TFCSounds.THATCH).noCollission()));


    public static final RegistryObject<Block> BOAT_FRAME = registerBlockWithItem("boat_frame",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block) {
        RegistryObject<T> blockRegistryObject = BLOCKS.register(name, block);
        registerBlockItem(name, blockRegistryObject);
        return blockRegistryObject;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        FirmacivItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
