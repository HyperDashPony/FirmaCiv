package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.util.FirmacivHelper;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
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

import java.util.Map;
import java.util.function.Supplier;

public final class FirmacivBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Firmaciv.MOD_ID);

    public static final Map<RegistryWood, RegistryObject<CanoeComponentBlock>> CANOE_COMPONENT_BLOCKS = FirmacivHelper.TFCWoodMap(
            wood -> registerBlock("wood/canoe_component_block/" + wood.getSerializedName(),
                    () -> new CanoeComponentBlock(
                            BlockBehaviour.Properties.copy(wood.getBlock(Wood.BlockType.STRIPPED_LOG).get())
                                    .mapColor(wood.woodColor()), wood)));

    //TODO: swap between roofing types automatically on place
    /*
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

    public static final Map<RegistryWood, RegistryObject<Block>> WOODEN_BOAT_FRAME_ANGLED = FirmacivHelper.TFCWoodMap(
            wood -> registerBlock("wood/watercraft_frame_angled/" + wood.getSerializedName(),
                    () -> new WoodenBoatFrameBlock(wood, BlockBehaviour.Properties.copy(BOAT_FRAME_ANGLED.get()))));

    public static final RegistryObject<FlatBoatFrameBlock> BOAT_FRAME_FLAT = registerBlockWithItem(
            "watercraft_frame_flat",
            () -> new FlatBoatFrameBlock(BlockBehaviour.Properties.copy(BOAT_FRAME_ANGLED.get())));

    public static final Map<RegistryWood, RegistryObject<FlatWoodenBoatFrameBlock>> WOODEN_BOAT_FRAME_FLAT = FirmacivHelper.TFCWoodMap(
            wood -> registerBlock("wood/watercraft_frame_flat/" + wood.getSerializedName(),
                    () -> new FlatWoodenBoatFrameBlock(wood, BlockBehaviour.Properties.copy(BOAT_FRAME_FLAT.get()))));

    public static final RegistryObject<Block> OARLOCK = registerBlockWithItem("oarlock", () -> new OarlockBlock(
            BlockBehaviour.Properties.copy(
                    TFCBlocks.METALS.get(Metal.Default.WROUGHT_IRON).get(Metal.BlockType.BLOCK).get()).noOcclusion()));

    public static final RegistryObject<Block> CLEAT = registerBlockWithItem("cleat", () -> new CleatBlock(
            BlockBehaviour.Properties.copy(
                    TFCBlocks.METALS.get(Metal.Default.WROUGHT_IRON).get(Metal.BlockType.BLOCK).get()).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
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
