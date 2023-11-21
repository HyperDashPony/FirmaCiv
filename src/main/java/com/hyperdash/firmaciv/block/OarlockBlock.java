package com.hyperdash.firmaciv.block;

import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.BoatVariant;
import com.hyperdash.firmaciv.entity.custom.RowboatEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class OarlockBlock extends HorizontalDirectionalBlock {

    private static final VoxelShape SHAPE_NORTH = Stream.of(
                    Block.box(3, 0, 0, 13, 3, 3))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_SOUTH = Stream.of(
                    Block.box(3, 0, 13, 13, 3, 16))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_EAST = Stream.of(
                    Block.box(0, 0, 3, 3, 3, 13))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_WEST = Stream.of(
                    Block.box(13, 0, 3, 16, 3, 13))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    @Nullable
    private BlockPattern rowboatPattern;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected OarlockBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pstate, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pstate.getValue(FACING);
        return switch (direction) {
            case DOWN, UP, NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_EAST;
            case EAST -> SHAPE_WEST;
        };
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pOldState.is(pState.getBlock())) {
            this.trySpawnRowboat(pLevel, pPos);
        }
        String yay = "not yay.";
        if (checkOppositeOarlock(pLevel, pPos, pState) && checkFrameBlocks(pLevel, pPos, pState)) {
            destroyMultiblock(pLevel, pPos, pState);
        }
        yay = yay;
    }

    public void destroyMultiblock(Level level, BlockPos thispos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        Direction.Axis axis = direction.getClockWise().getAxis();
        level.destroyBlock(thispos, false);
        level.destroyBlock(thispos.relative(direction.getOpposite()), false);
        thispos = thispos.below();
        level.destroyBlock(thispos, false);
        level.destroyBlock(thispos.relative(axis, 1), false);
        level.destroyBlock(thispos.relative(axis, -1), false);
        thispos = thispos.relative(direction.getOpposite());
        level.destroyBlock(thispos, false);
        level.destroyBlock(thispos.relative(axis, 1), false);
        level.destroyBlock(thispos.relative(axis, -1), false);
    }

    private void trySpawnRowboat(Level pLevel, BlockPos pPos) {
        BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = this.createRowboat(this).find(pLevel, pPos);
        if (blockpattern$blockpatternmatch != null) {
            RowboatEntity rowboat = FirmacivEntities.ROWBOATS.get(BoatVariant.MAPLE).get().create(pLevel);
            if (rowboat != null) {
                spawnRowboat(pLevel, blockpattern$blockpatternmatch, rowboat, blockpattern$blockpatternmatch.getBlock(0, 2, 0).getPos());
            }
        }

    }

    private static void spawnRowboat(Level pLevel, BlockPattern.BlockPatternMatch pPatternMatch, Entity pGolem, BlockPos pPos) {
        clearPatternBlocks(pLevel, pPatternMatch);
        pGolem.moveTo((double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.05D, (double) pPos.getZ() + 0.5D, 0.0F, 0.0F);
        pLevel.addFreshEntity(pGolem);

        for (ServerPlayer serverplayer : pLevel.getEntitiesOfClass(ServerPlayer.class, pGolem.getBoundingBox().inflate(5.0D))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, pGolem);
        }

        updatePatternBlocks(pLevel, pPatternMatch);
    }

    public static void clearPatternBlocks(Level pLevel, BlockPattern.BlockPatternMatch pPatternMatch) {
        for (int i = 0; i < pPatternMatch.getWidth(); ++i) {
            for (int j = 0; j < pPatternMatch.getHeight(); ++j) {
                BlockInWorld blockinworld = pPatternMatch.getBlock(i, j, 0);
                pLevel.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                pLevel.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
            }
        }

    }

    public static void updatePatternBlocks(Level pLevel, BlockPattern.BlockPatternMatch pPatternMatch) {
        for (int i = 0; i < pPatternMatch.getWidth(); ++i) {
            for (int j = 0; j < pPatternMatch.getHeight(); ++j) {
                BlockInWorld blockinworld = pPatternMatch.getBlock(i, j, 0);
                pLevel.blockUpdated(blockinworld.getPos(), Blocks.AIR);
            }
        }

    }

    public boolean checkOppositeOarlock(Level level, BlockPos thispos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        thispos = thispos.relative(direction.getOpposite());
        if (level.getBlockState(thispos).is(FirmacivBlocks.OARLOCK.get())) {
            return (level.getBlockState(thispos)).getValue(FACING) == direction.getOpposite();
        }
        return false;
    }

    public boolean checkFrameBlocks(Level level, BlockPos thispos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        Direction.Axis axis = direction.getClockWise().getAxis();
        thispos = thispos.below();
        BlockState frameState = level.getBlockState(thispos);
        if (frameState.is(FirmacivBlocks.WATERCRAFT_FRAME_ANGLED.get())) {
            if (frameState.getValue(FACING) == direction) {
                // the lower middle watercraft block is validated
                frameState = level.getBlockState(thispos.relative(direction.getOpposite()));
                if (frameState.getValue(FACING) == direction.getOpposite()) {
                    //the middle of the boat has been fully validated
                    //proceed left and right
                    BlockState frameState1 = level.getBlockState(thispos.relative(axis, 1));
                    BlockState frameState2 = level.getBlockState(thispos.relative(axis, -1));
                    Direction positiveDirection1 = direction.getCounterClockWise();
                    Direction negativeDirection2 = direction.getClockWise();
                    if (direction == Direction.NORTH || direction == Direction.EAST) {
                        positiveDirection1 = direction.getClockWise();
                        negativeDirection2 = direction.getCounterClockWise();
                    }
                    if (frameState1.is(FirmacivBlocks.WATERCRAFT_FRAME_ANGLED.get())) {
                        if (frameState1.getValue(FACING) == positiveDirection1) {
                            if (frameState2.is(FirmacivBlocks.WATERCRAFT_FRAME_ANGLED.get())) {
                                if (frameState2.getValue(FACING) == negativeDirection2) {
                                    //the bottom of the boat has been fully validated
                                    thispos = thispos.relative(direction.getOpposite());
                                    frameState1 = level.getBlockState(thispos.relative(axis, 1));
                                    frameState2 = level.getBlockState(thispos.relative(axis, -1));
                                    if (frameState1.is(FirmacivBlocks.WATERCRAFT_FRAME_ANGLED.get())) {
                                        if (frameState1.getValue(FACING) == positiveDirection1) {
                                            if (frameState2.is(FirmacivBlocks.WATERCRAFT_FRAME_ANGLED.get())) {
                                                if (frameState2.getValue(FACING) == negativeDirection2) {
                                                    //the top of the boat has been fully validated
                                                    //the WHOLE BOAT has been validated
                                                    return true;
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    private BlockPattern getOrCreateRowboat() {
        if (this.rowboatPattern == null) {
            this.rowboatPattern = BlockPatternBuilder.start().aisle("#", "#", "#").aisle("#", "#", "#").where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
        }

        return this.rowboatPattern;
    }

    private static BlockPattern createRowboat(Block oarlockBlock) {
        BlockPattern rowboat = BlockPatternBuilder.start().aisle("#", "#", "#").aisle("#", "#", "#").where('#',
                BlockInWorld.hasState(BlockStatePredicate.forBlock(oarlockBlock))).build();

        return rowboat;
    }

}
