package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.common.blockentity.CanoeComponentBlockEntity;
import com.alekiponi.firmaciv.common.blockentity.FirmacivBlockEntities;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistryWood;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static com.alekiponi.firmaciv.common.block.FirmacivBlocks.CANOE_COMPONENT_BLOCKS;


public class CanoeComponentBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final IntegerProperty CANOE_CARVED = FirmacivBlockStateProperties.CANOE_CARVED_13;
    public static final BooleanProperty END = FirmacivBlockStateProperties.END;
    private static final VoxelShape SHAPE_FINAL = Stream.of(
                    Block.box(0, 0, 0, 16, 9, 16))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_1 = Stream.of(
                    Block.box(0, 0, 0, 16, 16, 16))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private final RegistryWood wood;

    public CanoeComponentBlock(final Properties properties, final RegistryWood wood) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(AXIS, Direction.Axis.Z).setValue(CANOE_CARVED, 1).setValue(END, false));
        this.wood = wood;
    }

    public static Block getByStripped(Block strippedLogBlock) {
        // TODO this was broken before I touched it! Don't blame me - Traister101: RegistryWood migration
        return CANOE_COMPONENT_BLOCKS.values().stream()
                .filter(registryObject -> registryObject.get().wood.getBlock(
                        Wood.BlockType.STRIPPED_LOG) == strippedLogBlock)
                .map(registryObject -> registryObject.get()).findFirst().get();
    }

    public static boolean isValidCanoeShape(LevelAccessor world, Block strippedLogBlock, BlockPos pPos) {

        Direction.Axis axis = world.getBlockState(pPos).getValue(AXIS);

        Block canoeComponentBlock = getByStripped(strippedLogBlock);

        BlockPos blockPos0 = pPos;

        int row = 0;
        for (int i = -2; i <= 2; ++i) {
            // check two blocks in each direction and look for a row of 3 blocks

            blockPos0 = pPos.relative(axis, i);
            if (world.getBlockState(blockPos0).is(strippedLogBlock) || world.getBlockState(blockPos0)
                    .is(canoeComponentBlock)) {

                if (world.getBlockState(blockPos0).getValue(AXIS) == axis) {
                    row++;
                    if (row == 3) {
                        return true;
                    }
                } else {
                    row = 0;
                }

            } else {
                row = 0;
            }

        }

        return false;
    }

    private static BlockPos getMiddleBlockPos(Level pLevel, BlockPos pPos, Block canoeComponentBlock) {

        Direction.Axis axis = pLevel.getBlockState(pPos).getValue(AXIS);

        BlockPos blockPos0 = pPos.relative(axis, 2);
        BlockPos blockPos1 = pPos.relative(axis, 1);
        BlockPos blockPos2 = pPos.relative(axis, -1);
        BlockPos blockPos3 = pPos.relative(axis, -2);

        if (pLevel.getBlockState(blockPos0).is(canoeComponentBlock) &&
                pLevel.getBlockState(blockPos1).is(canoeComponentBlock)) {
            return pPos.relative(axis, 1);
        }
        if (pLevel.getBlockState(blockPos1).is(canoeComponentBlock) &&
                pLevel.getBlockState(blockPos2).is(canoeComponentBlock)) {
            return pPos;
        }
        if (pLevel.getBlockState(blockPos2).is(canoeComponentBlock) &&
                pLevel.getBlockState(blockPos3).is(canoeComponentBlock)) {
            return pPos.relative(axis, -1);
        }

        return pPos;
    }

    public static BlockState getStateForPlacement(Level pLevel, Block strippedLogBlock, BlockPos pPos) {

        Direction.Axis axis = pLevel.getBlockState(pPos).getValue(AXIS);

        Block canoeComponentBlock = getByStripped(strippedLogBlock);

        BlockState finalBlockState = canoeComponentBlock.defaultBlockState()
                .setValue(CANOE_CARVED, 1)
                .setValue(END, true)
                .setValue(AXIS, axis)
                .setValue(FACING, ((axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH));

        BlockPos blockPos1 = pPos.relative(axis, 1);

        if ((pLevel.getBlockState(blockPos1).is(canoeComponentBlock) || pLevel.getBlockState(blockPos1)
                .is(strippedLogBlock)
                && pLevel.getBlockState(blockPos1).getValue(AXIS) == axis)) {
            // if it's a valid block and rotation positive, then flip it
            finalBlockState = finalBlockState.setValue(FACING,
                    ((axis == Direction.Axis.X) ? Direction.WEST : Direction.NORTH));
        }

        return finalBlockState;
    }

    public static void setEndPieces(Level pLevel, BlockPos pPos, Block canoeComponentBlock, boolean positiveDir) {

        if (!pLevel.getBlockState(pPos).is(canoeComponentBlock)) {
            return;
        }

        BlockState thisBlockState = pLevel.getBlockState(pPos);

        Direction.Axis axis = pLevel.getBlockState(pPos).getValue(AXIS);

        BlockPos blockPos1 = pPos.relative(axis, (positiveDir ? 1 : -1));

        if (pLevel.getBlockState(blockPos1).is(canoeComponentBlock) && pLevel.getBlockState(blockPos1)
                .getValue(AXIS) == axis) {

            thisBlockState = thisBlockState.setValue(END, false);
            pLevel.setBlock(pPos, thisBlockState, 4);

            setEndPieces(pLevel, blockPos1, canoeComponentBlock, positiveDir);
        } else {
            thisBlockState = thisBlockState.setValue(END, true);

            if (positiveDir) {
                thisBlockState = thisBlockState.setValue(FACING,
                        ((axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH));
            } else {
                thisBlockState = thisBlockState.setValue(FACING,
                        ((axis == Direction.Axis.X) ? Direction.WEST : Direction.NORTH));
            }

            pLevel.setBlock(pPos, thisBlockState, 4);
        }

    }

    private static boolean areValidBlockStates(Level pLevel, BlockPos pPos, Block canoeComponentBlock) {

        Direction.Axis axis = pLevel.getBlockState(pPos).getValue(AXIS);

        BlockPos blockPos0 = pPos.relative(axis, 2);
        BlockPos blockPos1 = pPos.relative(axis, 1);
        BlockPos blockPos2 = pPos.relative(axis, -1);
        BlockPos blockPos3 = pPos.relative(axis, -2);

        if (pLevel.getBlockState(blockPos0).is(canoeComponentBlock) &&
                pLevel.getBlockState(blockPos1).is(canoeComponentBlock)) {

            if (pLevel.getBlockState(blockPos0).getValue(CANOE_CARVED) == 13 &&
                    pLevel.getBlockState(blockPos1).getValue(CANOE_CARVED) == 13) {
                return true;
            }
        }
        if (pLevel.getBlockState(blockPos1).is(canoeComponentBlock) &&
                pLevel.getBlockState(blockPos2).is(canoeComponentBlock)) {

            if (pLevel.getBlockState(blockPos1).getValue(CANOE_CARVED) == 13 &&
                    pLevel.getBlockState(blockPos2).getValue(CANOE_CARVED) == 13) {
                return true;
            }
        }
        if (pLevel.getBlockState(blockPos2).is(canoeComponentBlock) &&
                pLevel.getBlockState(blockPos3).is(canoeComponentBlock)) {

            return pLevel.getBlockState(blockPos2).getValue(CANOE_CARVED) == 13 &&
                    pLevel.getBlockState(blockPos3).getValue(CANOE_CARVED) == 13;
        }

        return false;
    }

    public static void trySpawnCanoe(Level pLevel, BlockPos pPos, Block canoeComponentBlock) {

        if (!areValidBlockStates(pLevel, pPos, canoeComponentBlock)) {
            return;
        }

        BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = createCanoeFull(canoeComponentBlock).find(
                pLevel, pPos);
        if (blockpattern$blockpatternmatch != null) {

            Direction.Axis axis = pLevel.getBlockState(pPos).getValue(AXIS);
            BlockPos middleblockpos = getMiddleBlockPos(pLevel, pPos, canoeComponentBlock);

            for (int i = 0; i < createCanoeFull(canoeComponentBlock).getHeight(); ++i) {
                BlockInWorld blockinworld = blockpattern$blockpatternmatch.getBlock(0, i, 0);
                pLevel.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                pLevel.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
            }

            CanoeComponentBlock ccb = (CanoeComponentBlock) canoeComponentBlock;

            CanoeEntity canoe = FirmacivEntities.CANOES.get(ccb.wood).get().create(pLevel);

            if (axis == Direction.Axis.X) {
                canoe.moveTo((double) middleblockpos.getX() + 0.5D, (double) middleblockpos.getY() + 0.05D,
                        (double) middleblockpos.getZ() + 0.5D, 90.0F, 0.0F);
            } else {
                canoe.moveTo((double) middleblockpos.getX() + 0.5D, (double) middleblockpos.getY() + 0.05D,
                        (double) middleblockpos.getZ() + 0.5D, 0.0F, 0.0F);
            }

            pLevel.addFreshEntity(canoe);

            for (ServerPlayer serverplayer : pLevel.getEntitiesOfClass(ServerPlayer.class,
                    canoe.getBoundingBox().inflate(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, canoe);
            }

            for (int l = 0; l < createCanoeFull(canoeComponentBlock).getHeight(); ++l) {
                BlockInWorld blockinworld3 = blockpattern$blockpatternmatch.getBlock(0, l, 0);
                pLevel.blockUpdated(blockinworld3.getPos(), Blocks.AIR);
            }
        }

    }

    // static methods and fields below

    private static BlockPattern createCanoeFull(Block canoeComponentBlock) {
        BlockPattern canoeFull = BlockPatternBuilder.start().aisle("#", "#", "#").where('#',
                BlockInWorld.hasState(BlockStatePredicate.forBlock(canoeComponentBlock))).build();

        return canoeFull;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(CANOE_CARVED) == 12) {
            double x = (float) pPos.getX() + pRandom.nextFloat();
            double y = (float) pPos.getY() + pRandom.nextFloat();
            double z = (float) pPos.getZ() + pRandom.nextFloat();

            for (int i = 0; i < pRandom.nextInt(3); ++i) {
                pLevel.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, x, y, z, 0.0,
                        0.1F + pRandom.nextFloat() / 8.0F, 0.0);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CanoeComponentBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState,
            BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, FirmacivBlockEntities.CANOE_COMPONENT_BLOCK_ENTITY.get(),
                CanoeComponentBlockEntity::serverTick);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public VoxelShape getShape(BlockState pstate, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {

        int canoeCarvedState = pstate.getValue(CANOE_CARVED);

        return switch (canoeCarvedState) {
            case 1, 2, 3, 4 -> SHAPE_1;
            default -> SHAPE_FINAL;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(AXIS, pContext.getHorizontalDirection().getAxis());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(AXIS);
        pBuilder.add(CANOE_CARVED);
        pBuilder.add(END);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.hasBlockEntity() && (!pState.is(pNewState.getBlock()) || !pNewState.hasBlockEntity())) {
            pLevel.removeBlockEntity(pPos);
        }
        if (!pNewState.is(pState.getBlock()) && pState.getValue(CANOE_CARVED) < 13) {
            Block ccb = pState.getBlock();
            Direction.Axis axis = pState.getValue(AXIS);

            if (pLevel.getBlockState(pPos.relative(axis, 1)).is(ccb) && pLevel.getBlockState(pPos.relative(axis, 1))
                    .getValue(AXIS) == axis) {
                pLevel.destroyBlock(pPos.relative(axis, 1), true);
            }
            if (pLevel.getBlockState(pPos.relative(axis, -1)).is(ccb) && pLevel.getBlockState(pPos.relative(axis, -1))
                    .getValue(AXIS) == axis) {
                pLevel.destroyBlock(pPos.relative(axis, -1), true);
            }
        }
    }
}