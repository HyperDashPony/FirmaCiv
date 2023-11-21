package com.hyperdash.firmaciv.block.custom;

import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.block.OarlockBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AngledWatercraftFrameBlock extends SquaredAngleBlock{
    public AngledWatercraftFrameBlock(BlockState pBaseState, Properties pProperties) {
        super(pBaseState, pProperties);
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pLevel.getBlockState(pPos.above()).is(FirmacivBlocks.OARLOCK.get())){
            pLevel.destroyBlock(pPos.above(), true);
        }
    }

}
