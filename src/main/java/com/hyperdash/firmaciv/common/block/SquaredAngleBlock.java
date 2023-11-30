package com.hyperdash.firmaciv.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public abstract class SquaredAngleBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape TOP_AABB = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape OCTET_NNN = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
    protected static final VoxelShape OCTET_NNP = Block.box(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
    protected static final VoxelShape OCTET_NPN = Block.box(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
    protected static final VoxelShape OCTET_NPP = Block.box(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape OCTET_PNN = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
    protected static final VoxelShape OCTET_PNP = Block.box(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape OCTET_PPN = Block.box(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    protected static final VoxelShape OCTET_PPP = Block.box(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape[] TOP_SHAPES = makeShapes(TOP_AABB, OCTET_NNN, OCTET_PNN, OCTET_NNP, OCTET_PNP);
    protected static final VoxelShape[] BOTTOM_SHAPES = makeShapes(BOTTOM_AABB, OCTET_NPN, OCTET_PPN, OCTET_NPP,
            OCTET_PPP);
    private static final int[] SHAPE_BY_STATE = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
    // Forge Start
    protected final Supplier<BlockState> stateSupplier;

    public SquaredAngleBlock(final Supplier<BlockState> stateSupplier, final Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SHAPE, StairsShape.STRAIGHT)
                        .setValue(WATERLOGGED, false));
        this.stateSupplier = stateSupplier;
    }

    private static VoxelShape[] makeShapes(final VoxelShape slabShape, final VoxelShape northWestCorner,
            final VoxelShape northEastCorner, final VoxelShape southWestCorner, final VoxelShape southEastCorner) {
        return IntStream.range(0, 16).mapToObj(
                (bitfield) -> makeStairShape(bitfield, slabShape, northWestCorner, northEastCorner, southWestCorner,
                        southEastCorner)).toArray(VoxelShape[]::new);
    }

    /**
     * Combines the shapes according to the mode set in the bitfield
     */
    private static VoxelShape makeStairShape(final int bitfield, final VoxelShape slabShape,
            final VoxelShape northWestCorner, final VoxelShape northEastCorner, final VoxelShape southWestCorner,
            final VoxelShape southEastCorner) {

        final VoxelShape northWestCornerOpposite;
        final VoxelShape northEastCornerOpposite;
        final VoxelShape southWestCornerOpposite;
        final VoxelShape southEastCornerOpposite;
        {
            final double move = slabShape == TOP_AABB ? 0.5 : -0.5;

            northWestCornerOpposite = northWestCorner.move(0, move, 0);
            northEastCornerOpposite = northEastCorner.move(0, move, 0);
            southWestCornerOpposite = southWestCorner.move(0, move, 0);
            southEastCornerOpposite = southEastCorner.move(0, move, 0);
        }

        VoxelShape voxelshape = slabShape;
        if ((bitfield & 1) != 0) {
            voxelshape = Shapes.or(slabShape, northWestCorner);
            voxelshape = Shapes.join(voxelshape, northWestCornerOpposite, BooleanOp.ONLY_FIRST);
        }

        if ((bitfield & 2) != 0) {
            voxelshape = Shapes.or(voxelshape, northEastCorner);
            voxelshape = Shapes.join(voxelshape, northEastCornerOpposite, BooleanOp.ONLY_FIRST);
        }

        if ((bitfield & 4) != 0) {
            voxelshape = Shapes.or(voxelshape, southWestCorner);
            voxelshape = Shapes.join(voxelshape, southWestCornerOpposite, BooleanOp.ONLY_FIRST);
        }

        if ((bitfield & 8) != 0) {
            voxelshape = Shapes.or(voxelshape, southEastCorner);
            voxelshape = Shapes.join(voxelshape, southEastCornerOpposite, BooleanOp.ONLY_FIRST);
        }

        return voxelshape;
    }

    /**
     * Returns a stair shape property based on the surrounding stairs from the given blockstate and position
     */
    private static StairsShape getStairsShape(final BlockState blockState, final BlockGetter blockGetter,
            final BlockPos blockPos) {
        final Direction direction = blockState.getValue(FACING);
        final BlockState blockstate = blockGetter.getBlockState(blockPos.relative(direction));
        if (isRoof(blockstate)) {
            final Direction direction1 = blockstate.getValue(FACING);
            if (direction1.getAxis() != blockState.getValue(FACING).getAxis() && canTakeShape(blockState, blockGetter,
                    blockPos, direction1.getOpposite())) {
                if (direction1 == direction.getCounterClockWise()) {
                    return StairsShape.OUTER_LEFT;
                }

                return StairsShape.OUTER_RIGHT;
            }
        }

        final BlockState blockstate1 = blockGetter.getBlockState(blockPos.relative(direction.getOpposite()));
        if (isRoof(blockstate1)) {
            final Direction direction2 = blockstate1.getValue(FACING);
            if (direction2.getAxis() != blockState.getValue(FACING).getAxis() && canTakeShape(blockState, blockGetter,
                    blockPos, direction2)) {
                if (direction2 == direction.getCounterClockWise()) {
                    return StairsShape.INNER_LEFT;
                }

                return StairsShape.INNER_RIGHT;
            }
        }

        return StairsShape.STRAIGHT;
    }

    private static boolean canTakeShape(final BlockState blockState, final BlockGetter blockGetter,
            final BlockPos blockPos, final Direction direction) {
        final BlockState blockstate = blockGetter.getBlockState(blockPos.relative(direction));
        return !isRoof(blockstate) || blockstate.getValue(FACING) != blockState.getValue(FACING);
    }

    public static boolean isRoof(final BlockState blockState) {
        return blockState.getBlock() instanceof SquaredAngleBlock;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean useShapeForLightOcclusion(final BlockState blockState) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(final BlockState blockState, final BlockGetter blockGetter, final BlockPos blockPos,
            final CollisionContext context) {
        return BOTTOM_SHAPES[SHAPE_BY_STATE[this.getShapeIndex(blockState)]];
    }

    private int getShapeIndex(final BlockState blockState) {
        return blockState.getValue(SHAPE).ordinal() * 4 + blockState.getValue(FACING).get2DDataValue();
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext placeContext) {
        final BlockPos blockpos = placeContext.getClickedPos();
        final FluidState fluidstate = placeContext.getLevel().getFluidState(blockpos);
        final BlockState blockstate = this.defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        return blockstate.setValue(SHAPE, getStairsShape(blockstate, placeContext.getLevel(), blockpos));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(final BlockState blockState, final Direction direction,
            final BlockState neighborState, final LevelAccessor levelAccessor, final BlockPos blockPos,
            final BlockPos neighborPos) {

        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        if (direction.getAxis().isHorizontal())
            return blockState.setValue(SHAPE, getStairsShape(blockState, levelAccessor, blockPos));

        return super.updateShape(blockState, direction, neighborState, levelAccessor, blockPos, neighborPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(final BlockState blockState, final Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(final BlockState blockState, final Mirror mirror) {
        final Direction direction = blockState.getValue(FACING);
        final StairsShape stairsshape = blockState.getValue(SHAPE);
        switch (mirror) {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z) {
                    return switch (stairsshape) {
                        case INNER_LEFT ->
                                blockState.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
                        case INNER_RIGHT ->
                                blockState.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
                        case OUTER_LEFT ->
                                blockState.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT ->
                                blockState.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
                        default -> blockState.rotate(Rotation.CLOCKWISE_180);
                    };
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X) {
                    return switch (stairsshape) {
                        case INNER_LEFT ->
                                blockState.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
                        case INNER_RIGHT ->
                                blockState.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
                        case OUTER_LEFT ->
                                blockState.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT ->
                                blockState.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
                        case STRAIGHT -> blockState.rotate(Rotation.CLOCKWISE_180);
                    };
                }
        }

        return super.mirror(blockState, mirror);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SHAPE, WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(final BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isPathfindable(final BlockState blockState, final BlockGetter blockGetter, final BlockPos blockPos,
            final PathComputationType computationType) {
        return false;
    }

    private Block getModelBlock() {
        return getModelState().getBlock();
    }

    private BlockState getModelState() {
        return stateSupplier.get();
    }
}