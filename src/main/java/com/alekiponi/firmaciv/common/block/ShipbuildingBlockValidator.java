package com.alekiponi.firmaciv.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

import static com.alekiponi.firmaciv.common.block.SquaredAngleBlock.FACING;

public class ShipbuildingBlockValidator {

    private AngledWoodenBoatFrameBlock.ConstantDirection constantDirection = null;

    private AngledWoodenBoatFrameBlock.ConstantShape constantShape = null;

    private boolean flat = false;

    private boolean validatingThisBlock = false;

    @Nullable
    private Direction direction = null;

    /*
        Use this constructor for inner/outer angled frames
     */
    ShipbuildingBlockValidator(AngledWoodenBoatFrameBlock.ConstantShape constantShape, AngledWoodenBoatFrameBlock.ConstantDirection constantDirection) {
        this.constantShape = constantShape;
        this.constantDirection = constantDirection;
        this.validatingThisBlock = true;
    }

    /*
        Use this constructor for flat frames or for blocks we don't need to care about (false)
     */
    ShipbuildingBlockValidator(boolean flat) {
        this.flat = flat;
        this.validatingThisBlock = flat;
    }

    /*
        Use this constructor for straight angled frames
     */
    ShipbuildingBlockValidator(Direction direction) {
        this.constantShape = AngledWoodenBoatFrameBlock.ConstantShape.STRAIGHT;
        this.direction = direction;
        this.validatingThisBlock = true;
    }

    public boolean shouldDestroy(){
        return validatingThisBlock;
    }

    public boolean validate(BlockState state, ItemStack plankItem, Direction structureDirection) {
        if (!validatingThisBlock) {
            return true;
        }
        if (flat && state.getBlock() instanceof FlatWoodenBoatFrameBlock) {
            return FlatWoodenBoatFrameBlock.validateProcessed(state, plankItem);
        }

        if (state.getBlock() instanceof AngledWoodenBoatFrameBlock) {
            if (AngledWoodenBoatFrameBlock.getConstantDirection(state) == null) {
                return false;
            }

            // straight validation
            if (this.constantShape == AngledWoodenBoatFrameBlock.ConstantShape.STRAIGHT) {
                if (direction == null) {
                    return false;
                }
                if (AngledWoodenBoatFrameBlock.getConstantShape(state) == AngledWoodenBoatFrameBlock.ConstantShape.STRAIGHT) {
                    Direction rotatedDirection = direction;
                    Direction facing = state.getValue(FACING);
                    if (structureDirection == Direction.SOUTH) {
                        rotatedDirection = direction.getOpposite();
                    } else if (structureDirection == Direction.EAST) {
                        rotatedDirection = direction.getClockWise();
                    } else if (structureDirection == Direction.WEST) {
                        rotatedDirection = direction.getCounterClockWise();
                    }
                    if (facing == rotatedDirection && AngledWoodenBoatFrameBlock.validateProcessed(state, plankItem)) {
                        return true;
                    }
                }
                return false;
            }

            // angled validation
            AngledWoodenBoatFrameBlock.ConstantDirection frameConstantDirection = AngledWoodenBoatFrameBlock.getConstantDirection(state);
            if (AngledWoodenBoatFrameBlock.validateProcessed(state, plankItem)) {
                AngledWoodenBoatFrameBlock.ConstantDirection rotatedValidatorDirection = AngledWoodenBoatFrameBlock.rotateConstantDirection(this.constantDirection, structureDirection);
                if (frameConstantDirection == rotatedValidatorDirection) {
                    if (this.constantShape == AngledWoodenBoatFrameBlock.getConstantShape(state)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
