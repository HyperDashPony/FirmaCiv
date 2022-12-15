package com.hyperdash.firmaciv.block.custom;

import com.hyperdash.firmaciv.block.FirmacivBlockStateProperties;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.hyperdash.firmaciv.block.FirmacivBlocks.CANOE_COMPONENT_BLOCKS;

public class CanoeComponentBlock extends HorizontalDirectionalBlock {

    public enum CanoeWoodType
    {
        ACACIA(TFCBlocks.WOODS.get(Wood.ACACIA).get(Wood.BlockType.STRIPPED_LOG)),
        ASH(TFCBlocks.WOODS.get(Wood.ASH).get(Wood.BlockType.STRIPPED_LOG)),
        ASPEN(TFCBlocks.WOODS.get(Wood.ASPEN).get(Wood.BlockType.STRIPPED_LOG)),
        BIRCH(TFCBlocks.WOODS.get(Wood.BIRCH).get(Wood.BlockType.STRIPPED_LOG)),
        BLACKWOOD(TFCBlocks.WOODS.get(Wood.BLACKWOOD).get(Wood.BlockType.STRIPPED_LOG)),
        CHESTNUT(TFCBlocks.WOODS.get(Wood.CHESTNUT).get(Wood.BlockType.STRIPPED_LOG)),
        DOUGLAS_FIR(TFCBlocks.WOODS.get(Wood.DOUGLAS_FIR).get(Wood.BlockType.STRIPPED_LOG)),
        HICKORY(TFCBlocks.WOODS.get(Wood.HICKORY).get(Wood.BlockType.STRIPPED_LOG)),
        KAPOK(TFCBlocks.WOODS.get(Wood.KAPOK).get(Wood.BlockType.STRIPPED_LOG)),
        MAPLE(TFCBlocks.WOODS.get(Wood.MAPLE).get(Wood.BlockType.STRIPPED_LOG)),
        OAK(TFCBlocks.WOODS.get(Wood.OAK).get(Wood.BlockType.STRIPPED_LOG)),
        PALM(TFCBlocks.WOODS.get(Wood.PALM).get(Wood.BlockType.STRIPPED_LOG)),
        PINE(TFCBlocks.WOODS.get(Wood.PINE).get(Wood.BlockType.STRIPPED_LOG)),
        ROSEWOOD(TFCBlocks.WOODS.get(Wood.ROSEWOOD).get(Wood.BlockType.STRIPPED_LOG)),
        SEQUOIA(TFCBlocks.WOODS.get(Wood.SEQUOIA).get(Wood.BlockType.STRIPPED_LOG)),
        SPRUCE(TFCBlocks.WOODS.get(Wood.SPRUCE).get(Wood.BlockType.STRIPPED_LOG)),
        SYCAMORE(TFCBlocks.WOODS.get(Wood.SYCAMORE).get(Wood.BlockType.STRIPPED_LOG)),
        WHITE_CEDAR(TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.STRIPPED_LOG)),
        WILLOW(TFCBlocks.WOODS.get(Wood.WILLOW).get(Wood.BlockType.STRIPPED_LOG));


        public final Supplier<? extends Block> stripped;

        CanoeWoodType(Supplier<? extends Block> stripped)
        {
            this.stripped = stripped;
        }
    }



    @Nullable
    public static BlockPattern canoeFull;


    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public static final IntegerProperty CANOE_CARVED = FirmacivBlockStateProperties.CANOE_CARVED_11;
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

    public final Supplier<? extends Block> strippedBlock;

    public CanoeComponentBlock(Properties properties, Supplier<? extends Block> strippedBlock)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(AXIS, Direction.Axis.Z).setValue(CANOE_CARVED,1));
        this.strippedBlock = strippedBlock;
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pOldState.is(pState.getBlock())) {
            this.trySpawnCanoe(pLevel, pPos, Wood.DOUGLAS_FIR.getBlock(Wood.BlockType.STRIPPED_LOG).get());
        }
    }

    public static Block getByStripped(Block strippedLogBlock)
    {
        return CANOE_COMPONENT_BLOCKS.values().stream()
                .filter(registryObject -> registryObject.get().strippedBlock.get() == strippedLogBlock)
                .map(registryObject -> registryObject.get()).findFirst().get();
    }

    public static void spawnCanoeWithAxe(Level pLevel, BlockPos pPos, Block strippedLogBlock){
        trySpawnCanoe(pLevel, pPos, strippedLogBlock);
    }

    public static boolean isValidCanoeShape(LevelAccessor world, Block strippedLogBlock, BlockPos pPos){

        Direction.Axis axis = world.getBlockState(pPos).getValue(AXIS);

        Block canoeComponentBlock = getByStripped(strippedLogBlock);

        BlockPos blockPos0 = pPos.relative(axis, 2);
        BlockPos blockPos1 = pPos.relative(axis, 1);
        BlockPos blockPos2 = pPos.relative(axis, -1);
        BlockPos blockPos3 = pPos.relative(axis, -2);

        BlockState blockState0;
        BlockState blockState1;

        if ((world.getBlockState(blockPos0).is(strippedLogBlock) || world.getBlockState(blockPos0).is(canoeComponentBlock)) &&
                (world.getBlockState(blockPos1).is(strippedLogBlock) || world.getBlockState(blockPos1).is(canoeComponentBlock))) {

            blockState0 = world.getBlockState(blockPos0);
            blockState1 = world.getBlockState(blockPos1);
            if(validPartRotation(blockState0, blockState1, axis)) { return true; }

        }
        if ((world.getBlockState(blockPos1).is(strippedLogBlock) || world.getBlockState(blockPos1).is(canoeComponentBlock)) &&
                (world.getBlockState(blockPos2).is(strippedLogBlock) || world.getBlockState(blockPos2).is(canoeComponentBlock))) {

            blockState0 = world.getBlockState(blockPos1);
            blockState1 = world.getBlockState(blockPos2);
            if(validPartRotation(blockState0, blockState1, axis)) { return true; }

        }
        if ((world.getBlockState(blockPos2).is(strippedLogBlock) || world.getBlockState(blockPos2).is(canoeComponentBlock)) &&
                (world.getBlockState(blockPos3).is(strippedLogBlock) || world.getBlockState(blockPos3).is(canoeComponentBlock))) {

            blockState0 = world.getBlockState(blockPos2);
            blockState1 = world.getBlockState(blockPos3);
            if(validPartRotation(blockState0, blockState1, axis)) { return true; }

        }

        return false;
    }

    private static boolean validPartRotation(BlockState blockState0, BlockState blockState1, Direction.Axis axis){
        if(blockState0.getValue(BlockStateProperties.AXIS) == axis &&
                blockState1.getValue(BlockStateProperties.AXIS) == axis){
            return true;
        }
        return false;
    }

    private static BlockPos getMiddleBlockPos(Level pLevel, BlockPos pPos, Block strippedLogBlock){
        Block canoeComponentBlock = getByStripped(strippedLogBlock);

        Direction.Axis axis = pLevel.getBlockState(pPos).getValue(AXIS);

        BlockPos blockPos0 = pPos.relative(axis, 2);
        BlockPos blockPos1 = pPos.relative(axis, 1);
        BlockPos blockPos2 = pPos.relative(axis, -1);
        BlockPos blockPos3 = pPos.relative(axis, -2);

        if (pLevel.getBlockState(blockPos0).is(canoeComponentBlock) &&
                pLevel.getBlockState(blockPos1).is(canoeComponentBlock)) {
            return pPos.relative(axis, 1);
        } if (pLevel.getBlockState(blockPos1).is(canoeComponentBlock) &&
                pLevel.getBlockState(blockPos2).is(canoeComponentBlock)) {
            return pPos;
        } if (pLevel.getBlockState(blockPos2).is(canoeComponentBlock) &&
                pLevel.getBlockState(blockPos3).is(canoeComponentBlock)) {
            return pPos.relative(axis, -1);
        }

        return pPos;
    }

    private static void trySpawnCanoe(Level pLevel, BlockPos pPos, Block strippedLogBlock) {

        BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = createCanoeFull(strippedLogBlock).find(pLevel, pPos);
        if (blockpattern$blockpatternmatch != null) {

            String rotatedirs = pLevel.getBlockState(pPos).getValue(FACING).getName();
            BlockPos middleblockpos = getMiddleBlockPos(pLevel, pPos, strippedLogBlock);

            for(int i = 0; i < createCanoeFull(strippedLogBlock).getHeight(); ++i) {
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

            for(int l = 0; l < createCanoeFull(strippedLogBlock).getHeight(); ++l) {
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
        pBuilder.add(CANOE_CARVED);
    }

    private static BlockPattern createCanoeFull(Block canoeComponentBlock) {
        BlockPattern canoeFull = BlockPatternBuilder.start().aisle("#", "#", "#").where('#',
                BlockInWorld.hasState(BlockStatePredicate.forBlock(getByStripped(canoeComponentBlock)))).build();

        return canoeFull;
    }


}
