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

    ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape constantShape, WoodenBoatFrameBlock.ConstantDirection constantDirection){
        this.constantShape = constantShape;
        this.constantDirection = constantDirection;
    }

    ShipbuildingBlockValidator(boolean flat){
        this.flat = flat;
        this.validatingThisBlock = !flat;
    }

    ShipbuildingBlockValidator(WoodenBoatFrameBlock.ConstantShape constantShape, WoodenBoatFrameBlock.ConstantDirection constantDirection, Direction direction){
        this.constantShape = constantShape;
        this.constantDirection = constantDirection;
        this.direction = direction;
    }

    public boolean validate(BlockState state, ItemStack plankItem, Direction structureDirection){
        if(!validatingThisBlock){
            return true;
        }
        if(flat){
            // TODO flat validation
            return true;
        }

        if(state.getBlock() instanceof WoodenBoatFrameBlock){
            if(WoodenBoatFrameBlock.getConstantDirection(state) == null){
                return false;
            }

            // straight validation
            if(this.constantShape == WoodenBoatFrameBlock.ConstantShape.STRAIGHT){
                if(direction == null){
                    return false;
                }
                Direction rotatedDirection = direction;
                if(structureDirection == Direction.SOUTH){
                    rotatedDirection = direction.getClockWise().getClockWise();
                }
                if(structureDirection == Direction.EAST){
                    rotatedDirection = direction.getClockWise();
                }
                if(structureDirection == Direction.WEST){
                    rotatedDirection = direction.getCounterClockWise();
                }
                if(state.getValue(FACING) == rotatedDirection){
                    return WoodenBoatFrameBlock.getConstantShape(state) == WoodenBoatFrameBlock.ConstantShape.STRAIGHT;
                }
                return false;
            }

            // angled validation
            WoodenBoatFrameBlock.ConstantDirection constantDirection = WoodenBoatFrameBlock.rotateConstantDirection(WoodenBoatFrameBlock.getConstantDirection(state), structureDirection);
            if(WoodenBoatFrameBlock.validateProcessed(state, plankItem)){
                if(constantDirection == this.constantDirection){
                    if(this.constantShape == WoodenBoatFrameBlock.getConstantShape(state)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
