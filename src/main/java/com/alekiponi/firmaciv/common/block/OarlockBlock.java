package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.common.entity.BoatVariant;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.RowboatEntity;
import com.alekiponi.firmaciv.events.config.FirmacivConfig;
import com.alekiponi.firmaciv.util.FirmacivTags;
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
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.stream.Stream;

import static com.alekiponi.firmaciv.common.block.FirmacivBlockStateProperties.FRAME_PROCESSED;

public class OarlockBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
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

    protected OarlockBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public static boolean isSupportedByWatercraftFrame(LevelReader pLevel, BlockPos thispos) {
        return pLevel.getBlockState(thispos.below())
                .getBlock() instanceof WoodenBoatFrameBlock woodenBoatFrameBlock && pLevel.getBlockState(
                thispos.below()).getValue(FRAME_PROCESSED) == 7;
    }

    private static Vec3 getSpawnPosition(Level pLevel, BlockPos thispos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        thispos = thispos.below();
        BlockPos otherpos = thispos.relative(direction.getOpposite());
        Vec3 origin = new Vec3(((thispos.getX() + otherpos.getX()) / 2.0f) + 0.5f, thispos.getY() + 0.5f,
                ((thispos.getZ() + otherpos.getZ()) / 2.0f) + 0.5f);
        return origin;
    }

    private static boolean validateFrameState(BlockState framestate, ItemStack plankitem) {
        // check if the plank item matches
        if (framestate.getBlock() instanceof WoodenBoatFrameBlock wbfb && wbfb.getPlankAsItemStack()
                .is(plankitem.getItem())) {
            // check if the state matches
            return framestate.getValue(FRAME_PROCESSED) == 7;
        }
        return false;

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
            if (checkOppositeOarlock(pLevel, pPos, pState) && checkFrameBlocks(pLevel, pPos, pState)) {
                spawnRowboat(pLevel, pPos, pState);
            }
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    public void destroyMultiblock(Level level, BlockPos thispos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        Direction.Axis axis = direction.getClockWise().getAxis();
        level.removeBlockEntity(thispos);
        level.destroyBlock(thispos, false);
        level.destroyBlock(thispos.relative(direction.getOpposite()), false);
        thispos = thispos.below();
        level.removeBlockEntity(thispos);
        level.removeBlockEntity(thispos.relative(axis, 1));
        level.removeBlockEntity(thispos.relative(axis, -1));
        level.destroyBlock(thispos, false);
        level.destroyBlock(thispos.relative(axis, 1), false);
        level.destroyBlock(thispos.relative(axis, -1), false);
        thispos = thispos.relative(direction.getOpposite());
        level.removeBlockEntity(thispos);
        level.removeBlockEntity(thispos.relative(axis, 1));
        level.removeBlockEntity(thispos.relative(axis, -1));
        level.destroyBlock(thispos, false);
        level.destroyBlock(thispos.relative(axis, 1), false);
        level.destroyBlock(thispos.relative(axis, -1), false);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isSupportedByWatercraftFrame(pLevel, pPos);
    }

    private void spawnRowboat(Level pLevel, BlockPos thispos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        Direction.Axis axis = direction.getClockWise().getAxis();
        WoodenBoatFrameBlock boatFrameBlock = (WoodenBoatFrameBlock) pLevel.getBlockState(thispos.below()).getBlock();
        String woodName = boatFrameBlock.getPlankAsItemStack().getItem().toString().split("planks/")[1];
        BoatVariant variant = BoatVariant.byName(woodName);
        RowboatEntity rowboat = FirmacivEntities.ROWBOATS.get(variant).get().create(pLevel);
        //FirmacivEntities.CANOES.get(ccb.variant).get().create(pLevel)
        rowboat.moveTo(getSpawnPosition(pLevel, thispos, blockState));
        if (axis == Direction.Axis.X) {
            rowboat.setYRot(90F);
        }

        pLevel.addFreshEntity(rowboat);
        destroyMultiblock(pLevel, thispos, blockState);
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
        Direction.Axis structureAxis = direction.getClockWise().getAxis();
        thispos = thispos.below();
        BlockState frameState = level.getBlockState(thispos);

        ItemStack plankItem = ItemStack.EMPTY;
        if (frameState.getBlock() instanceof WoodenBoatFrameBlock woodenBoatFrameBlock) {
            plankItem = woodenBoatFrameBlock.getPlankAsItemStack();
            if (FirmacivConfig.SERVER.shipWoodRestriction.get()) {
                if (!plankItem.is(FirmacivTags.Items.PLANKS_THAT_MAKE_SHIPS)) {
                    return false;
                }
            } else {
                if (!plankItem.is(FirmacivTags.Items.PLANKS)) {
                    return false;
                }
            }


            if (frameState.getValue(FACING) == direction) {
                // the lower middle watercraft block is validated
                frameState = level.getBlockState(thispos.relative(direction.getOpposite()));
                if (frameState.getValue(FACING) == direction.getOpposite() && validateFrameState(frameState,
                        plankItem)) {
                    //the middle of the boat has been fully validated
                    //proceed left and right
                    BlockState frameState1 = level.getBlockState(thispos.relative(structureAxis, 1));
                    BlockState frameState2 = level.getBlockState(thispos.relative(structureAxis, -1));
                    Direction positiveDirection1 = direction.getCounterClockWise();
                    Direction negativeDirection2 = direction.getClockWise();
                    if (direction == Direction.NORTH || direction == Direction.EAST) {
                        positiveDirection1 = direction.getClockWise();
                        negativeDirection2 = direction.getCounterClockWise();
                    }
                    if (validateFrameState(frameState1, plankItem)) {
                        if (frameState1.getValue(FACING) == positiveDirection1
                                || (frameState1.getValue(FACING).getAxis() != structureAxis && (frameState1.getValue(
                                SquaredAngleBlock.SHAPE) == StairsShape.INNER_RIGHT || frameState1.getValue(
                                SquaredAngleBlock.SHAPE) == StairsShape.INNER_LEFT))) {
                            if (validateFrameState(frameState2, plankItem)) {
                                if (frameState2.getValue(FACING) == negativeDirection2
                                        || (frameState2.getValue(FACING)
                                        .getAxis() != structureAxis && (frameState2.getValue(
                                        SquaredAngleBlock.SHAPE) == StairsShape.INNER_RIGHT || frameState2.getValue(
                                        SquaredAngleBlock.SHAPE) == StairsShape.INNER_LEFT))) {
                                    //the bottom of the boat has been fully validated
                                    thispos = thispos.relative(direction.getOpposite());
                                    frameState1 = level.getBlockState(thispos.relative(structureAxis, 1));
                                    frameState2 = level.getBlockState(thispos.relative(structureAxis, -1));
                                    if (validateFrameState(frameState1, plankItem)) {
                                        if (frameState1.getValue(FACING) == positiveDirection1
                                                || (frameState1.getValue(FACING)
                                                .getAxis() != structureAxis && (frameState1.getValue(
                                                SquaredAngleBlock.SHAPE) == StairsShape.INNER_RIGHT || frameState1.getValue(
                                                SquaredAngleBlock.SHAPE) == StairsShape.INNER_LEFT))) {
                                            if (validateFrameState(frameState2, plankItem)) {
                                                //the top of the boat has been fully validated
                                                //the WHOLE BOAT has been validated
                                                return frameState2.getValue(FACING) == negativeDirection2
                                                        || (frameState2.getValue(FACING)
                                                        .getAxis() != structureAxis && (frameState2.getValue(
                                                        SquaredAngleBlock.SHAPE) == StairsShape.INNER_RIGHT || frameState2.getValue(
                                                        SquaredAngleBlock.SHAPE) == StairsShape.INNER_LEFT));
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
        Direction direction = pContext.getClickedFace();
        BlockPos blockpos = pContext.getClickedPos();
        LevelAccessor level = pContext.getLevel();
        BlockState blockstate = this.defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite());
        if (level.getBlockState(blockpos.below()).getBlock() instanceof WoodenBoatFrameBlock) {
            blockstate = blockstate.setValue(FACING, level.getBlockState(blockpos.below()).getValue(FACING));
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
