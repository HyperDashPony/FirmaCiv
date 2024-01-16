package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.events.config.FirmacivConfig;
import com.alekiponi.firmaciv.util.FirmacivTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class ShipbuildingMultiblocks {
    public static ShipbuildingBlockValidator[][] sloopMultiblock = {
            {
                    new ShipbuildingBlockValidator(false),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.SOUTH_AND_EAST),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.SOUTH_AND_WEST),
                    new ShipbuildingBlockValidator(false),
            },
            {
                    new ShipbuildingBlockValidator(false),
                    new ShipbuildingBlockValidator(Direction.EAST),
                    new ShipbuildingBlockValidator(Direction.WEST),
                    new ShipbuildingBlockValidator(false),
            },
            {
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.SOUTH_AND_EAST),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.OUTER, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_WEST),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.OUTER, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_EAST),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.SOUTH_AND_WEST),
            },
            {
                    new ShipbuildingBlockValidator(Direction.EAST),
                    new ShipbuildingBlockValidator(true),
                    new ShipbuildingBlockValidator(true),
                    new ShipbuildingBlockValidator(Direction.WEST),
            },
            {
                    new ShipbuildingBlockValidator(Direction.EAST),
                    new ShipbuildingBlockValidator(true),
                    new ShipbuildingBlockValidator(true),
                    new ShipbuildingBlockValidator(Direction.WEST),
            },
            {
                    new ShipbuildingBlockValidator(Direction.EAST),
                    new ShipbuildingBlockValidator(true),
                    new ShipbuildingBlockValidator(true),
                    new ShipbuildingBlockValidator(Direction.WEST),
            },
            {
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_EAST),
                    new ShipbuildingBlockValidator(Direction.SOUTH),
                    new ShipbuildingBlockValidator(Direction.SOUTH),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_WEST),
            },
    };

    public static ShipbuildingBlockValidator[][] rowboatMultiblock = {
            {
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.SOUTH_AND_EAST),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.SOUTH_AND_WEST)
            },
            {
                    new ShipbuildingBlockValidator(Direction.EAST),
                    new ShipbuildingBlockValidator(Direction.WEST)
            },
            {
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_EAST),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_WEST)
            }
    };

    public static enum Multiblock {
        ROWBOAT,
        SLOOP
    }

    public static boolean validateShipHull(Level level, BlockPos thispos, Direction structureDirection, Multiblock multiblock, ItemStack plankItem) {
        Direction crossDirection = structureDirection.getClockWise();

        ShipbuildingBlockValidator[][] thisMultiblock;
        switch (multiblock) {
            case ROWBOAT -> {
                thisMultiblock = rowboatMultiblock;
            }
            case SLOOP -> {
                thisMultiblock = sloopMultiblock;
            }
            default -> {
                thisMultiblock = new ShipbuildingBlockValidator[0][0];
            }
        }

        for (int y = 0; y < thisMultiblock.length; y++) {
            for (int x = 0; x < thisMultiblock[0].length; x++) {
                BlockState state = level.getBlockState(thispos.relative(structureDirection.getOpposite(), y).relative(crossDirection, x));
                if (!thisMultiblock[y][x].validate(state, plankItem, structureDirection)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static ItemStack validatePlanks(BlockState frameState) {
        ItemStack plankItem = ItemStack.EMPTY;
        if (frameState.getBlock() instanceof WoodenBoatFrameBlock woodenBoatFrameBlock) {
            plankItem = woodenBoatFrameBlock.getPlankAsItemStack();
            if (FirmacivConfig.SERVER.shipWoodRestriction.get()) {
                if (!plankItem.is(FirmacivTags.Items.PLANKS_THAT_MAKE_SHIPS)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!plankItem.is(FirmacivTags.Items.PLANKS)) {
                    return ItemStack.EMPTY;
                }
            }
        } else {
            return ItemStack.EMPTY;
        }
        return plankItem;
    }

}
