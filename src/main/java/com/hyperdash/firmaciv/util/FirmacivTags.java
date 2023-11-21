package com.hyperdash.firmaciv.util;

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
        public static final TagKey<Item> PLANKS = create("planks");
        public static final TagKey<Item> CHESTS = create("chests");
        public static final TagKey<Item> WORKBENCHES = create("workbenches");

        public static TagKey<Item> getFromTFC(String id) {
            return TagKey.create(Registries.ITEM, new ResourceLocation("tfc", id));
        }

        public static TagKey<Item> create(String id) {
            return TagKey.create(Registries.ITEM, new ResourceLocation("firmaciv", id));
        }
    }


}
