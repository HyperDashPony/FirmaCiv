package com.alekiponi.firmaciv.common.entity;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Supplier;

public enum BoatVariant {
    ACACIA(0, Wood.ACACIA, TFCBlocks.WOODS.get(Wood.ACACIA).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.ACACIA)),
    ASH(1, Wood.ASH, TFCBlocks.WOODS.get(Wood.ASH).get(Wood.BlockType.STRIPPED_LOG), TFCItems.LUMBER.get(Wood.ASH)),
    ASPEN(2, Wood.ASPEN, TFCBlocks.WOODS.get(Wood.ASPEN).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.ASPEN)),
    BIRCH(3, Wood.BIRCH, TFCBlocks.WOODS.get(Wood.BIRCH).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.BIRCH)),
    BLACKWOOD(4, Wood.BLACKWOOD, TFCBlocks.WOODS.get(Wood.BLACKWOOD).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.BLACKWOOD)),
    CHESTNUT(5, Wood.CHESTNUT, TFCBlocks.WOODS.get(Wood.CHESTNUT).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.CHESTNUT)),
    DOUGLAS_FIR(6, Wood.DOUGLAS_FIR, TFCBlocks.WOODS.get(Wood.DOUGLAS_FIR).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.DOUGLAS_FIR)),
    HICKORY(7, Wood.HICKORY, TFCBlocks.WOODS.get(Wood.HICKORY).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.HICKORY)),
    KAPOK(8, Wood.KAPOK, TFCBlocks.WOODS.get(Wood.KAPOK).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.KAPOK)),
    MAPLE(9, Wood.MAPLE, TFCBlocks.WOODS.get(Wood.MAPLE).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.MAPLE)),
    OAK(10, Wood.OAK, TFCBlocks.WOODS.get(Wood.OAK).get(Wood.BlockType.STRIPPED_LOG), TFCItems.LUMBER.get(Wood.OAK)),
    PALM(11, Wood.PALM, TFCBlocks.WOODS.get(Wood.PALM).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.PALM)),
    PINE(12, Wood.PINE, TFCBlocks.WOODS.get(Wood.PINE).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.PINE)),
    ROSEWOOD(13, Wood.ROSEWOOD, TFCBlocks.WOODS.get(Wood.ROSEWOOD).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.ROSEWOOD)),
    SEQUOIA(14, Wood.SEQUOIA, TFCBlocks.WOODS.get(Wood.SEQUOIA).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.SEQUOIA)),
    SPRUCE(15, Wood.SPRUCE, TFCBlocks.WOODS.get(Wood.SPRUCE).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.SPRUCE)),
    SYCAMORE(16, Wood.SYCAMORE, TFCBlocks.WOODS.get(Wood.SYCAMORE).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.SYCAMORE)),
    WHITE_CEDAR(17, Wood.WHITE_CEDAR, TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.WHITE_CEDAR)),
    WILLOW(18, Wood.WILLOW, TFCBlocks.WOODS.get(Wood.WILLOW).get(Wood.BlockType.STRIPPED_LOG),
            TFCItems.LUMBER.get(Wood.WILLOW));
    private static final BoatVariant[] BY_ID = Arrays.stream(values())
            .sorted(Comparator.comparingInt(BoatVariant::getId)).toArray(BoatVariant[]::new);
    private final int id;
    private final Supplier<? extends Item> lumber;
    private final Supplier<? extends Block> stripped;

    BoatVariant(final int id, final Wood wood, final Supplier<? extends Block> stripped,
            final Supplier<? extends Item> lumber) {
        this.id = id;
        this.lumber = lumber;
        this.stripped = stripped;
    }

    public static BoatVariant byId(final int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static BoatVariant byName(final String name) {
        for (final BoatVariant boatVariant : values()) {
            if (boatVariant.getName().equals(name)) {
                return boatVariant;
            }
        }

        return ACACIA;
    }

    public int getId() {
        return this.id;
    }

    public Supplier<? extends Item> getLumber() {
        return this.lumber;
    }

    public Supplier<? extends Block> getStripped() {
        return this.stripped;
    }

    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}