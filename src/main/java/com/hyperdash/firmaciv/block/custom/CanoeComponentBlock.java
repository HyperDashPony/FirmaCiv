package com.hyperdash.firmaciv.block.custom;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.block.FirmacivBlockStateProperties;
import com.hyperdash.firmaciv.block.entity.FirmacivBlockEntities;
import com.hyperdash.firmaciv.block.entity.custom.CanoeComponentBlockEntity;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import com.hyperdash.firmaciv.util.FirmacivTags;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.hyperdash.firmaciv.block.FirmacivBlocks.CANOE_COMPONENT_BLOCKS;

public class CanoeComponentBlock extends BaseEntityBlock {

    public void animateTick(BlockState stateIn, Level level, BlockPos pos, Random rand) {
        if ((int)stateIn.getValue(CANOE_CARVED) == 12) {
            double x = (double)((float)pos.getX() + rand.nextFloat());
            double y = (double)((float)pos.getY() + rand.nextFloat());
            double z = (double)((float)pos.getZ() + rand.nextFloat());

            for(int i = 0; i < rand.nextInt(3); ++i) {
                level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, x, y, z, 0.0, (double)(0.1F + rand.nextFloat() / 8.0F), 0.0);
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, FirmacivBlockEntities.CANOE_COMPONENT_BLOCK_ENTITY.get(),
                CanoeComponentBlockEntity::serverTick);
    }

    public enum CanoeWoodType
    {
        ACACIA(TFCBlocks.WOODS.get(Wood.ACACIA).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.ACACIA)),
        ASH(TFCBlocks.WOODS.get(Wood.ASH).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.ASH)),
        ASPEN(TFCBlocks.WOODS.get(Wood.ASPEN).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.ASPEN)),
        BIRCH(TFCBlocks.WOODS.get(Wood.BIRCH).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.BIRCH)),
        BLACKWOOD(TFCBlocks.WOODS.get(Wood.BLACKWOOD).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.BLACKWOOD)),
        CHESTNUT(TFCBlocks.WOODS.get(Wood.CHESTNUT).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.CHESTNUT)),
        DOUGLAS_FIR(TFCBlocks.WOODS.get(Wood.DOUGLAS_FIR).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.DOUGLAS_FIR)),
        HICKORY(TFCBlocks.WOODS.get(Wood.HICKORY).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.HICKORY)),
        KAPOK(TFCBlocks.WOODS.get(Wood.KAPOK).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.KAPOK)),
        MAPLE(TFCBlocks.WOODS.get(Wood.MAPLE).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.MAPLE)),
        OAK(TFCBlocks.WOODS.get(Wood.OAK).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.OAK)),
        PALM(TFCBlocks.WOODS.get(Wood.PALM).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.PALM)),
        PINE(TFCBlocks.WOODS.get(Wood.PINE).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.PINE)),
        ROSEWOOD(TFCBlocks.WOODS.get(Wood.ROSEWOOD).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.ROSEWOOD)),
        SEQUOIA(TFCBlocks.WOODS.get(Wood.SEQUOIA).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.SEQUOIA)),
        SPRUCE(TFCBlocks.WOODS.get(Wood.SPRUCE).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.SPRUCE)),
        SYCAMORE(TFCBlocks.WOODS.get(Wood.SYCAMORE).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.SYCAMORE)),
        WHITE_CEDAR(TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.WHITE_CEDAR)),
        WILLOW(TFCBlocks.WOODS.get(Wood.WILLOW).get(Wood.BlockType.STRIPPED_LOG),
                TFCItems.LUMBER.get(Wood.WILLOW));


        public final Supplier<? extends Block> stripped;
        public final Supplier<? extends Item> lumber;

        //public final Supplier<? extends Entity> canoe;

        CanoeWoodType(Supplier<? extends Block> stripped, Supplier<? extends Item> lumber)
        {
            this.lumber = lumber;
            this.stripped = stripped;
        }
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

    public VoxelShape getShape(BlockState pstate, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {

        int canoeCarvedState = pstate.getValue(CANOE_CARVED);

        switch(canoeCarvedState){
            case 1:
            case 2:
            case 3:
            case 4:
                return SHAPE_1;
            default:
                return SHAPE_FINAL;
        }
    }

    public final Supplier<? extends Block> strippedBlock;
    public final Supplier<? extends Item> lumberItem;

    public CanoeComponentBlock(Properties properties, Supplier<? extends Block> strippedBlock, Supplier<? extends Item> lumberItem)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(AXIS, Direction.Axis.Z).setValue(CANOE_CARVED,1).setValue(END, false));
        this.strippedBlock = strippedBlock;
        this.lumberItem = lumberItem;
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pOldState.is(pState.getBlock())) {
            this.trySpawnCanoe(pLevel, pPos, Wood.DOUGLAS_FIR.getBlock(Wood.BlockType.STRIPPED_LOG).get());
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(AXIS, pContext.getHorizontalDirection().getAxis());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(AXIS);
        pBuilder.add(CANOE_CARVED);
        pBuilder.add(END);
    }

    public Item getLumber()
    {
        return lumberItem.get();
    }

    public Block getStrippedLog()
    {
        return strippedBlock.get();
    }

    // static methods and fields below

    public static void spawnCanoeWithAxe(Level pLevel, BlockPos pPos, Block strippedLogBlock){
        trySpawnCanoe(pLevel, pPos, strippedLogBlock);
    }

    public static Block getByStripped(Block strippedLogBlock)
    {
        return CANOE_COMPONENT_BLOCKS.values().stream()
                .filter(registryObject -> registryObject.get().strippedBlock.get() == strippedLogBlock)
                .map(registryObject -> registryObject.get()).findFirst().get();
    }

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public static final IntegerProperty CANOE_CARVED = FirmacivBlockStateProperties.CANOE_CARVED_15;

    public static final BooleanProperty END = FirmacivBlockStateProperties.END;

    private static final VoxelShape SHAPE_FINAL = Stream.of(
                    Block.box(0,0,0,16,9,16))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_1 = Stream.of(
                    Block.box(0,0,0,16,16,16))
            .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

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

    private static BlockPos getMiddleBlockPos(Level pLevel, BlockPos pPos, Block canoeComponentBlock){

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

    public static BlockState getStateForPlacement(LevelAccessor world, Block strippedLogBlock, BlockPos pPos){

        Direction.Axis axis = world.getBlockState(pPos).getValue(AXIS);

        Block canoeComponentBlock = getByStripped(strippedLogBlock);

        BlockState finalBlockState = canoeComponentBlock.defaultBlockState().setValue(CANOE_CARVED, 1);
        finalBlockState = finalBlockState.setValue(END,false).setValue(AXIS, axis);

        if(axis == Direction.Axis.X){
            finalBlockState = finalBlockState.setValue(FACING, Direction.WEST);
        } else {
            finalBlockState = finalBlockState.setValue(FACING, Direction.NORTH);
        }

        BlockPos blockPos0 = pPos.relative(axis, 2);
        BlockPos blockPos1 = pPos.relative(axis, 1);
        BlockPos blockPos2 = pPos.relative(axis, -1);
        BlockPos blockPos3 = pPos.relative(axis, -2);

        if ((world.getBlockState(blockPos0).is(strippedLogBlock) || world.getBlockState(blockPos0).is(canoeComponentBlock)) &&
                (world.getBlockState(blockPos1).is(strippedLogBlock) || world.getBlockState(blockPos1).is(canoeComponentBlock))) {
            // positive relative to axis
            // if x, west
            // if z, north
            finalBlockState = finalBlockState.setValue(END,true);

            if(axis == Direction.Axis.X){
                finalBlockState = finalBlockState.setValue(FACING, Direction.WEST);
                return finalBlockState;
            } else {
                finalBlockState = finalBlockState.setValue(FACING, Direction.NORTH);
                return finalBlockState;
            }

        }
        if ((world.getBlockState(blockPos1).is(strippedLogBlock) || world.getBlockState(blockPos1).is(canoeComponentBlock)) &&
                (world.getBlockState(blockPos2).is(strippedLogBlock) || world.getBlockState(blockPos2).is(canoeComponentBlock))) {
            // middle
            return finalBlockState;

        }
        if ((world.getBlockState(blockPos2).is(strippedLogBlock) || world.getBlockState(blockPos2).is(canoeComponentBlock)) &&
                (world.getBlockState(blockPos3).is(strippedLogBlock) || world.getBlockState(blockPos3).is(canoeComponentBlock))) {
            // negative relative to axis
            // if x, east
            // if z, south
            finalBlockState = finalBlockState.setValue(END,true);

            if(axis == Direction.Axis.X){
                finalBlockState = finalBlockState.setValue(FACING, Direction.EAST);
                return finalBlockState;
            } else {
                finalBlockState = finalBlockState.setValue(FACING, Direction.SOUTH);
                return finalBlockState;
            }

        }

        return finalBlockState;
    }

    private static boolean areValidBlockStates(Level pLevel, BlockPos pPos, Block canoeComponentBlock){

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
        } if (pLevel.getBlockState(blockPos1).is(canoeComponentBlock) &&
                pLevel.getBlockState(blockPos2).is(canoeComponentBlock)) {

            if (pLevel.getBlockState(blockPos1).getValue(CANOE_CARVED) == 13 &&
                    pLevel.getBlockState(blockPos2).getValue(CANOE_CARVED) == 13) {
                return true;
            }
        } if (pLevel.getBlockState(blockPos2).is(canoeComponentBlock) &&
                pLevel.getBlockState(blockPos3).is(canoeComponentBlock)) {

            if (pLevel.getBlockState(blockPos2).getValue(CANOE_CARVED) == 13 &&
                    pLevel.getBlockState(blockPos3).getValue(CANOE_CARVED) == 13) {
                return true;
            }
        }

        return false;
    }

    public static void trySpawnCanoe(Level pLevel, BlockPos pPos, Block canoeComponentBlock) {

        if(!areValidBlockStates(pLevel, pPos, canoeComponentBlock)){
            return;
        }

        BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = createCanoeFull(canoeComponentBlock).find(pLevel, pPos);
        if (blockpattern$blockpatternmatch != null) {

            String rotatedirs = pLevel.getBlockState(pPos).getValue(FACING).getName();
            BlockPos middleblockpos = getMiddleBlockPos(pLevel, pPos, canoeComponentBlock);

            for(int i = 0; i < createCanoeFull(canoeComponentBlock).getHeight(); ++i) {
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

            for(int l = 0; l < createCanoeFull(canoeComponentBlock).getHeight(); ++l) {
                BlockInWorld blockinworld3 = blockpattern$blockpatternmatch.getBlock(0, l, 0);
                pLevel.blockUpdated(blockinworld3.getPos(), Blocks.AIR);
            }
        }

    }

    private static BlockPattern createCanoeFull(Block canoeComponentBlock) {
        BlockPattern canoeFull = BlockPatternBuilder.start().aisle("#", "#", "#").where('#',
                BlockInWorld.hasState(BlockStatePredicate.forBlock(canoeComponentBlock))).build();

        return canoeFull;
    }

}
