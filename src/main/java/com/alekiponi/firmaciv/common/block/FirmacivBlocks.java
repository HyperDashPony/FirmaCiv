package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.util.BoatVariant;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryWood;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public final class FirmacivBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Firmaciv.MOD_ID);

    public static final Map<BoatVariant, RegistryObject<CanoeComponentBlock>> CANOE_COMPONENT_BLOCKS = Helpers.mapOfKeys(
            BoatVariant.class, boatVariant -> registerBlockWithoutItem(
                    "wood/canoe_component_block/" + boatVariant.name().toLowerCase(Locale.ROOT),
                    () -> new CanoeComponentBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).noOcclusion(),
                            boatVariant)));

    // TODO dirty fix remove this when we fix the map above as it's just manually registering a mangrove canoe component
    public static final RegistryObject<Block> TEMP = registerBlockWithItem("wood/canoe_component_block/mangrove",
            () -> new CanoeComponentBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).noOcclusion(),
                    BoatVariant.ACACIA));

    /*
    //TODO: swap between roofing types
    public static final RegistryObject<Block> THATCH_ROOFING = registerBlockWithItem("thatch_roofing",
            () -> new AngledRoofingBlock(BlockBehaviour.Properties.of().strength(0.6F, 0.4F).noOcclusion().isViewBlocking(TFCBlocks::never)
                            .sound(TFCSounds.THATCH).noCollission()));

    public static final RegistryObject<Block> THATCH_ROOFING_STAIRS = registerBlockWithItem("thatch_roofing_stairs",
            () -> new StairBlock(Blocks.ACACIA_STAIRS.defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(0.6F, 0.4F).noOcclusion().isViewBlocking(TFCBlocks::never)
                            .sound(TFCSounds.THATCH).noCollission()));

    public static final RegistryObject<Block> THATCH_ROOFING_SLAB = registerBlockWithItem("thatch_roofing_slab",
            () -> new SlabBlock(
                    BlockBehaviour.Properties.of().strength(0.6F, 0.4F).noOcclusion().isViewBlocking(TFCBlocks::never)
                            .sound(TFCSounds.THATCH).noCollission()));

     */
    public static final RegistryObject<Block> BOAT_FRAME_ANGLED = registerBlockWithItem("watercraft_frame_angled",
            () -> new AngledBoatFrameBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));

    public static final Map<RegistryWood, RegistryObject<Block>> WOODEN_BOAT_FRAME_ANGLED = registerWoodenBoatFrames();
    public static final RegistryObject<Block> OARLOCK = registerBlockWithItem("oarlock",
            () -> new OarlockBlock(BlockBehaviour.Properties.copy(
                    TFCBlocks.METALS.get(Metal.Default.WROUGHT_IRON).get(Metal.BlockType.BLOCK).get()).noOcclusion()));

    public static Map<RegistryWood, RegistryObject<Block>> registerWoodenBoatFrames() {
        final Map<RegistryWood, RegistryObject<Block>> map = new HashMap<>();

        for (final Wood tfcWood : Wood.values()) {
            map.put(tfcWood, registerBlockWithoutItem("wood/watercraft_frame_angled/" + tfcWood.getSerializedName(),
                    () -> new WoodenBoatFrameBlock(tfcWood, BlockBehaviour.Properties.copy(BOAT_FRAME_ANGLED.get()))));
        }

        return map;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block) {
        RegistryObject<T> blockRegistryObject = BLOCKS.register(name, block);
        registerBlockItem(name, blockRegistryObject);
        return blockRegistryObject;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        FirmacivItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
