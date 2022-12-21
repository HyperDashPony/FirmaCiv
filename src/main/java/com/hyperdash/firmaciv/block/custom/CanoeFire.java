package com.hyperdash.firmaciv.block.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

import java.util.Random;
import java.util.stream.Stream;

import static com.hyperdash.firmaciv.block.custom.CanoeComponentBlock.CANOE_CARVED;
import static com.hyperdash.firmaciv.block.custom.CanoeComponentBlock.CANOE_HOLLOWED;

public class CanoeFire extends FireBlock {

    private static final Logger LOGGER = LogUtils.getLogger();

    public CanoeFire(Properties p_53425_) {
        super(p_53425_);
    }

    private static final VoxelShape SHAPE = Stream.of(
                    Block.box(0,-7,0,16,1,16))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public VoxelShape getShape(BlockState pstate, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }



}
