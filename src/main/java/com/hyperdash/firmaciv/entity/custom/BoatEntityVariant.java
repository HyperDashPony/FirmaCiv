package com.hyperdash.firmaciv.entity.custom;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Supplier;

public enum BoatEntityVariant {
    ACACIA(0, TFCBlocks.WOODS.get(Wood.ACACIA).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.ACACIA), CanoeEntity.Type.ACACIA),
    ASH(1, TFCBlocks.WOODS.get(Wood.ASH).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.ASH), CanoeEntity.Type.ASH),
    ASPEN(2, TFCBlocks.WOODS.get(Wood.ASPEN).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.ASPEN), CanoeEntity.Type.ASPEN),
    BIRCH(3, TFCBlocks.WOODS.get(Wood.BIRCH).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.BIRCH), CanoeEntity.Type.BIRCH),
    BLACKWOOD(4, TFCBlocks.WOODS.get(Wood.BLACKWOOD).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.BLACKWOOD), CanoeEntity.Type.BLACKWOOD),
    CHESTNUT(5, TFCBlocks.WOODS.get(Wood.CHESTNUT).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.CHESTNUT), CanoeEntity.Type.CHESTNUT),
    DOUGLAS_FIR(6, TFCBlocks.WOODS.get(Wood.DOUGLAS_FIR).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.DOUGLAS_FIR), CanoeEntity.Type.DOUGLAS_FIR),
    HICKORY(7, TFCBlocks.WOODS.get(Wood.HICKORY).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.HICKORY), CanoeEntity.Type.HICKORY),
    KAPOK(8, TFCBlocks.WOODS.get(Wood.KAPOK).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.KAPOK), CanoeEntity.Type.KAPOK),
    MAPLE(9, TFCBlocks.WOODS.get(Wood.MAPLE).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.MAPLE), CanoeEntity.Type.MAPLE),
    OAK(10, TFCBlocks.WOODS.get(Wood.OAK).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.OAK), CanoeEntity.Type.OAK),
    PALM(11, TFCBlocks.WOODS.get(Wood.PALM).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.PALM), CanoeEntity.Type.PALM),
    PINE(12, TFCBlocks.WOODS.get(Wood.PINE).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.PINE), CanoeEntity.Type.PINE),
    ROSEWOOD(13, TFCBlocks.WOODS.get(Wood.ROSEWOOD).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.ROSEWOOD), CanoeEntity.Type.ROSEWOOD),
    SEQUOIA(14, TFCBlocks.WOODS.get(Wood.SEQUOIA).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.SEQUOIA), CanoeEntity.Type.SEQUOIA),
    SPRUCE(15, TFCBlocks.WOODS.get(Wood.SPRUCE).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.SPRUCE), CanoeEntity.Type.SPRUCE),
    SYCAMORE(16, TFCBlocks.WOODS.get(Wood.SYCAMORE).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.SYCAMORE), CanoeEntity.Type.SYCAMORE),
    WHITE_CEDAR(17,TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.WHITE_CEDAR), CanoeEntity.Type.WHITE_CEDAR),
    WILLOW(18, TFCBlocks.WOODS.get(Wood.WILLOW).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.WILLOW), CanoeEntity.Type.WILLOW);

    private static final BoatEntityVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(BoatEntityVariant::getId)).toArray(BoatEntityVariant[]::new);

    private final int id;


    public final Supplier<? extends Item> lumber;

    public final Supplier<? extends Block> stripped;

    public final CanoeEntity.Type canoe;

    BoatEntityVariant(int id, Supplier<? extends Block> stripped, Supplier<? extends Item> lumber, CanoeEntity.Type canoe)
    {
        this.id = id;
        this.lumber = lumber;
        this.stripped = stripped;
        this.canoe = canoe;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static BoatEntityVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

}
