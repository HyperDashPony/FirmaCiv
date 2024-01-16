package com.alekiponi.firmaciv.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.StairsShape;

import javax.annotation.Nullable;

import static com.alekiponi.firmaciv.common.block.SquaredAngleBlock.FACING;

public class ShipbuildingBlockValidator {

    private WoodenBoatFrameBlock.ConstantDirection constantDirection = null;

    private WoodenBoatFrameBlock.ConstantShape constantShape = null;

    private boolean flat = false;

    private boolean validatingThisBlock = true;

    @Nullable
    private Direction direction = null;

    /*
        Use this constructor for inner/outer angled frames
     */
    ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape constantShape, WoodenBoatFrameBlock.ConstantDirection constantDirection) {
        this.constantShape = constantShape;
        this.constantDirection = constantDirection;
    }

    /*
        Use this constructor for flat frames or for blocks we don't need to care about (false)
     */
    ShipbuildingBlockValidator(boolean flat) {
        this.flat = flat;
        this.validatingThisBlock = !flat;
    }

    /*
        Use this constructor for straight angled frames
     */
    ShipbuildingBlockValidator(Direction direction) {
        this.constantShape = WoodenBoatFrameBlock.ConstantShape.STRAIGHT;
        this.direction = direction;
    }

    public boolean validate(BlockState state, ItemStack plankItem, Direction structureDirection) {
        if (!validatingThisBlock) {
            return true;
        }
        if (flat) {
            // TODO flat validation
            return true;
        }

        if (state.getBlock() instanceof WoodenBoatFrameBlock) {
            if (WoodenBoatFrameBlock.getConstantDirection(state) == null) {
                return false;
            }

            // straight validation
            if (this.constantShape == WoodenBoatFrameBlock.ConstantShape.STRAIGHT) {
                if (direction == null) {
                    return false;
                }
                if (WoodenBoatFrameBlock.getConstantShape(state) == WoodenBoatFrameBlock.ConstantShape.STRAIGHT) {
                    Direction rotatedDirection = direction;
                    if (structureDirection == Direction.NORTH) {
                        rotatedDirection = direction.getOpposite();
                    }
                    if (structureDirection == Direction.EAST) {
                        rotatedDirection = direction.getCounterClockWise();
                    }
                    if (structureDirection == Direction.WEST) {
                        rotatedDirection = direction.getClockWise();
                    }
                    if (state.getValue(FACING) == rotatedDirection) {
                        return true;
                    }
                }
                return false;
            }

            // angled validation
            WoodenBoatFrameBlock.ConstantDirection constantDirection = WoodenBoatFrameBlock.getConstantDirection(state);
            if (WoodenBoatFrameBlock.validateProcessed(state, plankItem)) {
                if (constantDirection == WoodenBoatFrameBlock.rotateConstantDirection(this.constantDirection, structureDirection)) {
                    if (this.constantShape == WoodenBoatFrameBlock.getConstantShape(state)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
