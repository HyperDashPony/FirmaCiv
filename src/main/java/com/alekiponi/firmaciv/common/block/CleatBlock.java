package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopUnderConstructionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

import static com.alekiponi.firmaciv.common.block.FirmacivBlockStateProperties.FRAME_PROCESSED_7;

public class CleatBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE_NORTH = Stream.of(
                    Block.box(5, 0, 2, 11, 2, 4))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_SOUTH = Stream.of(
                    Block.box(5, 0, 12, 11, 2, 14))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_WEST = Stream.of(
                    Block.box(2, 0, 5, 4, 2, 11))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_EAST = Stream.of(
                    Block.box(12, 0, 5, 14, 2, 11))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    protected CleatBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public static boolean isSupportedByWatercraftFrame(LevelReader pLevel, BlockPos thispos) {
        if (pLevel.getBlockState(thispos.below())
                .getBlock() instanceof AngledWoodenBoatFrameBlock woodenBoatFrameBlock && pLevel.getBlockState(
                thispos.below()).getValue(FRAME_PROCESSED_7) == 7) {
            return AngledWoodenBoatFrameBlock.getConstantShape(pLevel.getBlockState(
                    thispos.below())) == AngledWoodenBoatFrameBlock.ConstantShape.INNER || AngledWoodenBoatFrameBlock.getConstantShape(pLevel.getBlockState(
                    thispos.below())) == AngledWoodenBoatFrameBlock.ConstantShape.STRAIGHT;
        }
        return false;
    }

    private static Vec3 getSpawnPosition(Level pLevel, BlockPos thispos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        thispos = thispos.below();
        BlockPos otherpos = thispos.relative(direction.getOpposite());
        Vec3 origin = new Vec3(((thispos.getX() + otherpos.getX()) / 2.0f) + 0.5f, thispos.getY() + 0.5f,
                ((thispos.getZ() + otherpos.getZ()) / 2.0f) + 0.5f);
        return origin;
    }


    @Override
    public VoxelShape getShape(BlockState pstate, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pstate.getValue(FACING);
        return switch (direction) {
            case DOWN, UP, NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
        };
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pOldState.is(pState.getBlock())) {
            validateMultiblock(pLevel, pPos, pState);
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    public void destroyMultiblock(Level level, BlockPos thispos, BlockState blockState) {

    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isSupportedByWatercraftFrame(pLevel, pPos);
    }

    private void spawnConstructionSloop(Level pLevel, BlockPos thispos, BlockState blockState) {

    }

    public void validateMultiblock(Level level, BlockPos thispos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        Direction.Axis axis = direction.getClockWise().getAxis();
        BlockPos crosspos = thispos.relative(direction.getOpposite(), 3);
        Direction structureDirection;
        BlockPos[] cleats = new BlockPos[4];
        cleats[0] = thispos;
        if (level.getBlockState(crosspos).is(FirmacivBlocks.CLEAT.get()) && level.getBlockState(crosspos).getValue(FACING) == direction.getOpposite()) {
            cleats[1] = crosspos;

            BlockPos forwardPos = thispos.relative(axis, 4);
            BlockPos backwardPos = thispos.relative(axis, -4);

            if (level.getBlockState(forwardPos).is(FirmacivBlocks.CLEAT.get()) && level.getBlockState(forwardPos).getValue(FACING) == direction) {
                crosspos = forwardPos.relative(direction.getOpposite(), 3);
                if (level.getBlockState(crosspos).is(FirmacivBlocks.CLEAT.get()) && level.getBlockState(crosspos).getValue(FACING) == direction.getOpposite()) {
                    cleats[2] = thispos;
                    cleats[3] = cleats[1];
                    cleats[0] = forwardPos;
                    cleats[1] = crosspos;
                }
            } else if (level.getBlockState(backwardPos).is(FirmacivBlocks.CLEAT.get()) && level.getBlockState(backwardPos).getValue(FACING) == direction) {
                crosspos = backwardPos.relative(direction.getOpposite(), 3);
                if (level.getBlockState(crosspos).is(FirmacivBlocks.CLEAT.get()) && level.getBlockState(crosspos).getValue(FACING) == direction.getOpposite()) {
                    cleats[2] = backwardPos;
                    cleats[3] = crosspos;
                }
            }
        }

        for (BlockPos blockPos : cleats) {
            if (blockPos == null) {
                return;
            }
        }

        // we only know the axis at this point so let's get the direction by checking forward and back front middles
        Direction.Axis crossAxis = Direction.Axis.X;
        if (axis == Direction.Axis.X) {
            crossAxis = Direction.Axis.Z;
        }

        // flip the structure if it needs to be flipped
        if (cleats[0].get(crossAxis) > cleats[1].get(crossAxis)) {
            BlockPos[] newCleats = new BlockPos[4];
            newCleats[0] = cleats[1];
            newCleats[1] = cleats[0];
            newCleats[2] = cleats[3];
            newCleats[3] = cleats[2];
            cleats = newCleats;
        }

        if (level.getBlockState(cleats[0].below().relative(crossAxis, 1).relative(axis, 2)).getBlock() instanceof AngledWoodenBoatFrameBlock) {
            structureDirection = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        } else {
            structureDirection = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE);
            BlockPos[] newCleats = new BlockPos[4];
            newCleats[0] = cleats[2];
            newCleats[1] = cleats[3];
            newCleats[2] = cleats[0];
            newCleats[3] = cleats[1];
            cleats = newCleats;
        }

        BlockPos origin = cleats[0].relative(structureDirection, 2).below();
        if (structureDirection == Direction.WEST || structureDirection == Direction.SOUTH) {
            origin = cleats[1].relative(structureDirection, 2).below();
        }

        ItemStack plankItem = ShipbuildingMultiblocks.validatePlanks(level.getBlockState(thispos.below()));
        BlockState framestate = level.getBlockState(thispos.below());
        if (plankItem.isEmpty()) {
            return;
        }

        if (ShipbuildingMultiblocks.validateShipHull(level, origin, structureDirection, ShipbuildingMultiblocks.Multiblock.SLOOP, plankItem) && framestate.getBlock() instanceof AngledWoodenBoatFrameBlock boatFrameBlock) {
            // destroy cleats
            for (BlockPos pos : cleats) {
                level.destroyBlock(pos, false);
            }
            // spawn sloop construction entity
            BlockPos pos1 = origin.relative(structureDirection.getOpposite(), 3).relative(structureDirection.getClockWise(), 1);
            BlockPos pos2 = origin.relative(structureDirection.getOpposite(), 5).relative(structureDirection.getClockWise(), 3);
            Vec3 spawnPosition = new Vec3(pos1.getX() + pos2.getX(), pos1.getY() + pos2.getY(), pos1.getZ() + pos2.getZ()).multiply(0.5, 0.5, 0.5);

            SloopUnderConstructionEntity sloop = FirmacivEntities.SLOOPS_UNDER_CONSTRUCTION.get(boatFrameBlock.wood).get().create(level);
            sloop.setPos(spawnPosition);
            if (structureDirection == Direction.NORTH) {
                sloop.setYRot(180F);
            } else if (structureDirection == Direction.EAST) {
                sloop.setYRot(-90F);
            } else if (structureDirection == Direction.WEST) {
                sloop.setYRot(90F);
            }
            level.addFreshEntity(sloop);


        }


    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getClickedFace();
        BlockPos blockpos = pContext.getClickedPos();
        LevelAccessor level = pContext.getLevel();
        BlockState blockstate = this.defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite());


        if (level.getBlockState(blockpos.below()).getBlock() instanceof AngledWoodenBoatFrameBlock) {
            Direction[] directions = AngledWoodenBoatFrameBlock.getSolid(level.getBlockState(blockpos.below()));
            if (directions.length == 0) {
                return blockstate;
            }
            for (Direction dir : directions) {
                if (dir == pContext.getHorizontalDirection().getOpposite()) {
                    return blockstate;
                }
            }
            blockstate = blockstate.setValue(FACING, directions[0]);
        }
        return blockstate;
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

}
