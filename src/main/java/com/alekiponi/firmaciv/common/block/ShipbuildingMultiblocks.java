package com.alekiponi.firmaciv.common.block;

import net.minecraft.core.Direction;

import java.util.ArrayList;

public class ShipbuildingMultiblocks {
    public static ShipbuildingBlockValidator[][] sloopMultiblock = {
            {
                new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_EAST),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.STRAIGHT, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_EAST),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.STRAIGHT, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_EAST),
            },
            {},
            {},
            {},
            {},
            {},
            {}
    };

    public static ShipbuildingBlockValidator[][] rowboatMultiblock = {
            {new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.SOUTH_AND_EAST),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.SOUTH_AND_WEST)},
            {new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.STRAIGHT, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_SOUTH, Direction.EAST),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.STRAIGHT, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_SOUTH, Direction.WEST)},
            {new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_EAST),
                    new ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape.INNER, WoodenBoatFrameBlock.ConstantDirection.NORTH_AND_WEST)}
    };

}
