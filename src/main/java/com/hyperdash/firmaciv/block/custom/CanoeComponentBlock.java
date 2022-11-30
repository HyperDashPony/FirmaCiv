package com.hyperdash.firmaciv.block.custom;

import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CanoeComponentBlock extends HorizontalDirectionalBlock {





    @Nullable
    public static BlockPattern canoeFull;
    @Nullable
    private BlockPattern canoeMissingInside;
    @Nullable
    private BlockPattern canoeMissingOutsideRight;
    @Nullable
    private BlockPattern canoeMissingOutsideLeft;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(0,0,0,16,9,16))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public VoxelShape getShape(BlockState pstate, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public CanoeComponentBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AXIS, Direction.Axis.Z));
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pOldState.is(pState.getBlock())) {
            this.trySpawnCanoe(pLevel, pPos);
        }

    }

    public static void spawnCanoeWithAxe(Level pLevel, BlockPos pPos){
        trySpawnCanoe(pLevel, pPos);
    }

    public boolean canSpawnCanoe(LevelReader pLevel, BlockPos pPos) {
        return this.getOrCreateCanoeMissingOutsideLeft().find(pLevel, pPos) != null ||
                this.getOrCreateCanoeMissingOutsideRight().find(pLevel, pPos) != null ||
                this.getOrCreateCanoeMissingInside().find(pLevel, pPos) != null;
    }

    public static boolean isValidCanoeShape(LevelAccessor world, Block thisBlock, BlockPos thisBlockPos, Direction.Axis rotatedirs){

        boolean validCanoeShape = false;

        Direction.Axis axis = world.getBlockState(thisBlockPos).getValue(AXIS);

        BlockPos blockPos0 = thisBlockPos.relative(axis, 2);
        BlockPos blockPos1 = thisBlockPos.relative(axis, 1);
        BlockPos blockPos2 = thisBlockPos.relative(axis, -1);
        BlockPos blockPos3 = thisBlockPos.relative(axis, -2);

        BlockState blockState0 = world.getBlockState(blockPos0);
        BlockState blockState1 = world.getBlockState(blockPos0);

        if((world.getBlockState(blockPos0).is(thisBlock) || world.getBlockState(blockPos0).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get())) &&
                (world.getBlockState(blockPos1).is(thisBlock) || world.getBlockState(blockPos1).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()))) {

            blockState0 = world.getBlockState(blockPos0);
            blockState1 = world.getBlockState(blockPos1);
            validCanoeShape = true;

        } if((world.getBlockState(blockPos1).is(thisBlock) || world.getBlockState(blockPos1).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get())) &&
                (world.getBlockState(blockPos2).is(thisBlock) || world.getBlockState(blockPos2).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()))) {

            blockState0 = world.getBlockState(blockPos1);
            blockState1 = world.getBlockState(blockPos2);
            validCanoeShape = true;

        } if((world.getBlockState(blockPos2).is(thisBlock) || world.getBlockState(blockPos2).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get())) &&
                (world.getBlockState(blockPos3).is(thisBlock) || world.getBlockState(blockPos3).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()))) {

            blockState0 = world.getBlockState(blockPos2);
            blockState1 = world.getBlockState(blockPos3);
            validCanoeShape = true;

        }

        if(validCanoeShape){
            if(blockState0.getValue(BlockStateProperties.AXIS) == rotatedirs &&
                    blockState1.getValue(BlockStateProperties.AXIS) == rotatedirs){
                return true;
            }
        }

        return false;
    }


    private static BlockPos getMiddleBlockPos(Level pLevel, BlockPos pPos){
        String rotatedirs = pLevel.getBlockState(pPos).getValue(FACING).getName();

        if (rotatedirs == "east" || rotatedirs == "west") {
            if (pLevel.getBlockState(pPos.west()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()) &&
                    pLevel.getBlockState(pPos.east()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get())) {
                return pPos;
            }
        } if (rotatedirs == "north" || rotatedirs == "south") {
            if (pLevel.getBlockState(pPos.north()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()) &&
                    pLevel.getBlockState(pPos.south()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get())) {
                return pPos;
            }
        } if (rotatedirs == "east" || rotatedirs == "west") {
            if (pLevel.getBlockState(pPos.west()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()) &&
                    pLevel.getBlockState(pPos.west().west()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get())) {
                return pPos.west();
            } else if (pLevel.getBlockState(pPos.east()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()) &&
                    pLevel.getBlockState(pPos.east().east()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get())) {
                return pPos.east();
            }
        } if (rotatedirs == "north" || rotatedirs == "south") {
            if (pLevel.getBlockState(pPos.north()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()) &&
                    pLevel.getBlockState(pPos.north().north()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get())) {
                return pPos.north();
            } else if (pLevel.getBlockState(pPos.south()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()) &&
                    pLevel.getBlockState(pPos.south().south()).is(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get())) {
                return pPos.south();
            }
        }

        return pPos;
    }

    private static void trySpawnCanoe(Level pLevel, BlockPos pPos) {

        BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = getOrCreateCanoeFull().find(pLevel, pPos);
        if (blockpattern$blockpatternmatch != null) {

            String rotatedirs = pLevel.getBlockState(pPos).getValue(FACING).getName();
            BlockPos middleblockpos = getMiddleBlockPos(pLevel, pPos);

            for(int i = 0; i < getOrCreateCanoeFull().getHeight(); ++i) {
                BlockInWorld blockinworld = blockpattern$blockpatternmatch.getBlock(0, i, 0);
                pLevel.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                pLevel.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
            }

            CanoeEntity canoe = FirmacivEntities.CANOE_ENTITY.get().create(pLevel);

            if (rotatedirs == "east" || rotatedirs == "west") {
                canoe.moveTo((double)middleblockpos.getX() + 0.5D, (double)middleblockpos.getY() + 0.05D, (double)middleblockpos.getZ() + 0.5D, 90.0F, 0.0F);
            } else {
                canoe.moveTo((double)middleblockpos.getX() + 0.5D, (double)middleblockpos.getY() + 0.05D, (double)middleblockpos.getZ() + 0.5D, 0.0F, 0.0F);
            }


            pLevel.addFreshEntity(canoe);

            for(ServerPlayer serverplayer : pLevel.getEntitiesOfClass(ServerPlayer.class, canoe.getBoundingBox().inflate(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, canoe);
            }

            for(int l = 0; l < getOrCreateCanoeFull().getHeight(); ++l) {
                BlockInWorld blockinworld3 = blockpattern$blockpatternmatch.getBlock(0, l, 0);
                pLevel.blockUpdated(blockinworld3.getPos(), Blocks.AIR);
            }
        }

    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(AXIS, pContext.getHorizontalDirection().getAxis());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(AXIS);
    }

    private static BlockPattern getOrCreateCanoeFull() {
        if (canoeFull == null) {
            canoeFull = BlockPatternBuilder.start().aisle("#", "#", "#").where('#',
                    BlockInWorld.hasState(BlockStatePredicate.forBlock(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()))).build();
        }

        return canoeFull;
    }

    private BlockPattern getOrCreateCanoeMissingInside() {
        if (this.canoeMissingInside == null) {
            this.canoeMissingInside = BlockPatternBuilder.start().aisle("#", " ", "#").where('#',
                    BlockInWorld.hasState(BlockStatePredicate.forBlock(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()))).build();
        }

        return this.canoeMissingInside;
    }

    private BlockPattern getOrCreateCanoeMissingOutsideRight() {
        if (this.canoeMissingOutsideRight == null) {
            this.canoeMissingOutsideRight = BlockPatternBuilder.start().aisle("#", "#", " ").where('#',
                    BlockInWorld.hasState(BlockStatePredicate.forBlock(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()))).build();
        }

        return this.canoeMissingOutsideRight;
    }

    private BlockPattern getOrCreateCanoeMissingOutsideLeft() {
        if (this.canoeMissingOutsideLeft == null) {
            this.canoeMissingOutsideLeft = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#',
                    BlockInWorld.hasState(BlockStatePredicate.forBlock(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get()))).build();
        }

        return this.canoeMissingOutsideLeft;
    }

}
