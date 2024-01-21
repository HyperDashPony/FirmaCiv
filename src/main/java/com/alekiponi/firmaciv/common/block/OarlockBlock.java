package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.vehicle.RowboatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
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

public class OarlockBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE_NORTH = Stream.of(
                    Block.box(3, 0, 0, 13, 3, 3))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_SOUTH = Stream.of(
                    Block.box(3, 0, 13, 13, 3, 16))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_WEST = Stream.of(
                    Block.box(0, 0, 3, 3, 3, 13))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_EAST = Stream.of(
                    Block.box(13, 0, 3, 16, 3, 13))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    protected OarlockBlock(Properties pProperties) {
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
            BlockState frameState = pLevel.getBlockState(pPos.below());
            if (validateOarlocks(pLevel, pPos, pState) && validateFrames(pLevel, pPos, pState)) {
                destroyOarlocks(pLevel, pPos, pState);
                spawnRowboat(pLevel, pPos, pState, frameState);
            }
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    public void destroyOarlocks(Level level, BlockPos thispos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        level.destroyBlock(thispos, false);
        level.destroyBlock(thispos.relative(direction.getOpposite()), false);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isSupportedByWatercraftFrame(pLevel, pPos);
    }

    private void spawnRowboat(Level pLevel, BlockPos thispos, BlockState blockState, BlockState framestate) {
        Direction direction = blockState.getValue(FACING);
        Direction.Axis axis = direction.getClockWise().getAxis();
        if(framestate.getBlock() instanceof AngledWoodenBoatFrameBlock boatFrameBlock){
            RowboatEntity rowboat = FirmacivEntities.ROWBOATS.get(boatFrameBlock.wood).get().create(pLevel);
            rowboat.setPos(getSpawnPosition(pLevel, thispos, blockState));
            if (axis == Direction.Axis.X) {
                rowboat.setYRot(90F);
            }

            pLevel.addFreshEntity(rowboat);
        }
        //String woodName = boatFrameBlock.getPlankAsItemStack().getItem().toString().split("planks/")[1];
        //BoatVariant variant = BoatVariant.byName(woodName);
        //RowboatEntity rowboat = FirmacivEntities.ROWBOATS.get(variant).get().create(pLevel);

    }

    public boolean validateOarlocks(Level level, BlockPos thispos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        thispos = thispos.relative(direction.getOpposite());
        if (level.getBlockState(thispos).is(FirmacivBlocks.OARLOCK.get())) {
            return (level.getBlockState(thispos)).getValue(FACING) == direction.getOpposite();
        }
        return false;
    }

    public boolean validateFrames(Level level, BlockPos thispos, BlockState blockState) {
        Direction structureDirection = blockState.getValue(FACING).getClockWise();
        Direction.Axis structureAxis = structureDirection.getAxis();
        Direction crossDirection = structureDirection.getClockWise();

        thispos = thispos.below();
        thispos = thispos.relative(structureDirection);

        BlockState frameState = level.getBlockState(thispos);
        ItemStack plankItem = ShipbuildingMultiblocks.validatePlanks(frameState);
        if (plankItem.isEmpty()) {
            return false;
        }

        return ShipbuildingMultiblocks.validateShipHull(level, thispos, structureDirection, ShipbuildingMultiblocks.Multiblock.ROWBOAT, plankItem);

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
