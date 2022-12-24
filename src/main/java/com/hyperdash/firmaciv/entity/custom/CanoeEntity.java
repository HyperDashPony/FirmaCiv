package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.block.custom.CanoeComponentBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CanoeEntity extends FirmacivBoatEntity{
    public enum WoodType {
        DOUGLAS_FIR(FirmacivBlocks.CANOE_COMPONENT_BLOCKS.get(CanoeComponentBlock.CanoeWoodType.DOUGLAS_FIR).get(), "douglas_fir"),
        PINE(FirmacivBlocks.CANOE_COMPONENT_BLOCKS.get(CanoeComponentBlock.CanoeWoodType.DOUGLAS_FIR).get(), "pine"),
        CHESTNUT(FirmacivBlocks.CANOE_COMPONENT_BLOCKS.get(CanoeComponentBlock.CanoeWoodType.DOUGLAS_FIR).get(), "chestnut"),
        SPRUCE(FirmacivBlocks.CANOE_COMPONENT_BLOCKS.get(CanoeComponentBlock.CanoeWoodType.DOUGLAS_FIR).get(), "spruce"),
        ASPEN(FirmacivBlocks.CANOE_COMPONENT_BLOCKS.get(CanoeComponentBlock.CanoeWoodType.DOUGLAS_FIR).get(), "aspen"),
        KAPOK(FirmacivBlocks.CANOE_COMPONENT_BLOCKS.get(CanoeComponentBlock.CanoeWoodType.DOUGLAS_FIR).get(), "kapok"),
        ROSEWOOD(FirmacivBlocks.CANOE_COMPONENT_BLOCKS.get(CanoeComponentBlock.CanoeWoodType.DOUGLAS_FIR).get(), "rosewood"),
        WHITE_CEDAR(FirmacivBlocks.CANOE_COMPONENT_BLOCKS.get(CanoeComponentBlock.CanoeWoodType.DOUGLAS_FIR).get(), "white_cedar"),
        WILLOW(FirmacivBlocks.CANOE_COMPONENT_BLOCKS.get(CanoeComponentBlock.CanoeWoodType.DOUGLAS_FIR).get(), "willow");

        private final String name;
        private final Block canoeComponentBlock;

        private WoodType(Block canoeComponentBlock, String pName) {
            this.name = pName;
            this.canoeComponentBlock = canoeComponentBlock;
        }

        public String getName() {
            return this.name;
        }

        public Block getCanoeComponentBlock() {
            return this.canoeComponentBlock;
        }

        public String toString() {
            return this.name;
        }
    }

    public CanoeComponentBlock.CanoeWoodType woodtype;

    public CanoeComponentBlock.CanoeWoodType getWoodtype(){
        return woodtype;
    }

    @Override
    public Item getDropItem() {
        return null;
    }

    public CanoeEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public ItemStack getPickResult() {
        return null;
    }

}
