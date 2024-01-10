package com.alekiponi.firmaciv.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FirmacivTags {


    public static class Blocks {

        public static final TagKey<Block> CAN_MAKE_CANOE = create("can_make_canoe");

        public static final TagKey<Block> CANOE_COMPONENT_BLOCKS = create("canoe_component_blocks");
        public static final TagKey<Block> CAN_MAKE_CANOE_UNRESTRICTED = create("can_make_canoe_unrestricted");

        public static final TagKey<Block> WOODEN_BOAT_FRAMES = create("wooden_watercraft_frames");

        public Blocks() {
        }

        private static TagKey<Block> create(String id) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation("firmaciv", id));
        }

    }

    public static class Items {

        public static final TagKey<Item> SAWS = getFromTFC("saws");
        public static final TagKey<Item> AXES = getFromTFC("axes");
        public static final TagKey<Item> LUMBER = getFromTFC("lumber");

        public static final TagKey<Item> ANVILS = getFromTFC("anvils");

        public static final TagKey<Item> PLANKS = create("planks");
        public static final TagKey<Item> CHESTS = create("chests");
        public static final TagKey<Item> WORKBENCHES = create("workbenches");
        public static final TagKey<Item> SHULKER_BOX = create("shulker_box");
        public static final TagKey<Item> CAN_PLACE_IN_COMPARTMENTS = create("can_place_in_compartments");

        public static final TagKey<Item> PLANKS_THAT_MAKE_SHIPS = create("planks_that_make_ships");

        public static TagKey<Item> getFromTFC(String id) {
            return TagKey.create(Registries.ITEM, new ResourceLocation("tfc", id));
        }

        public static TagKey<Item> create(String id) {
            return TagKey.create(Registries.ITEM, new ResourceLocation("firmaciv", id));
        }
    }
}