package com.hyperdash.firmaciv.block.custom;

import com.hyperdash.firmaciv.FirmaCiv;
import com.hyperdash.firmaciv.block.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
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
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CanoeComponentBlock extends HorizontalDirectionalBlock {

    @Nullable
    private BlockPattern canoeFull;
    @Nullable
    private BlockPattern canoeMissingInside;
    @Nullable
    private BlockPattern canoeMissingOutsideRight;
    @Nullable
    private BlockPattern canoeMissingOutsideLeft;

    private static final Predicate<BlockState> PUMPKINS_PREDICATE = (properties) -> {
        return properties != null && (properties.is(Blocks.CARVED_PUMPKIN) || properties.is(Blocks.JACK_O_LANTERN));
    };
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

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
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pOldState.is(pState.getBlock())) {
            this.trySpawnCanoe(pLevel, pPos);
        }
    }

    public boolean canSpawnCanoe(LevelReader pLevel, BlockPos pPos) {
        return this.getOrCreateCanoeMissingOutsideLeft().find(pLevel, pPos) != null ||
                this.getOrCreateCanoeMissingOutsideRight().find(pLevel, pPos) != null ||
                this.getOrCreateCanoeMissingInside().find(pLevel, pPos) != null;
    }

    private void trySpawnCanoe(Level pLevel, BlockPos pPos) {
        BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = this.getOrCreateCanoeFull().find(pLevel, pPos);
        if (blockpattern$blockpatternmatch != null) {
            for(int i = 0; i < this.getOrCreateCanoeFull().getHeight(); ++i) {
                BlockInWorld blockinworld = blockpattern$blockpatternmatch.getBlock(0, i, 0);
                pLevel.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                pLevel.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
            }

            Boat boat = EntityType.BOAT.create(pLevel);
            BlockPos blockpos1 = blockpattern$blockpatternmatch.getBlock(0, 2, 0).getPos();
            boat.moveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.05D, (double)blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
            pLevel.addFreshEntity(boat);

            for(ServerPlayer serverplayer : pLevel.getEntitiesOfClass(ServerPlayer.class, boat.getBoundingBox().inflate(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, boat);
            }

            for(int l = 0; l < this.getOrCreateCanoeFull().getHeight(); ++l) {
                BlockInWorld blockinworld3 = blockpattern$blockpatternmatch.getBlock(0, l, 0);
                pLevel.blockUpdated(blockinworld3.getPos(), Blocks.AIR);
            }
        }

    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    private BlockPattern getOrCreateCanoeFull() {
        if (this.canoeFull == null) {
            this.canoeFull = BlockPatternBuilder.start().aisle("#", "#", "#").where('#',
                    BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.CANOE_COMPONENT_BLOCK.get()))).build();
        }

        return this.canoeFull;
    }

    private BlockPattern getOrCreateCanoeMissingInside() {
        if (this.canoeMissingInside == null) {
            this.canoeMissingInside = BlockPatternBuilder.start().aisle("#", " ", "#").where('#',
                    BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.CANOE_COMPONENT_BLOCK.get()))).build();
        }

        return this.canoeMissingInside;
    }

    private BlockPattern getOrCreateCanoeMissingOutsideRight() {
        if (this.canoeMissingOutsideRight == null) {
            this.canoeMissingOutsideRight = BlockPatternBuilder.start().aisle("#", "#", " ").where('#',
                    BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.CANOE_COMPONENT_BLOCK.get()))).build();
        }

        return this.canoeMissingOutsideRight;
    }

    private BlockPattern getOrCreateCanoeMissingOutsideLeft() {
        if (this.canoeMissingOutsideLeft == null) {
            this.canoeMissingOutsideLeft = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#',
                    BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.CANOE_COMPONENT_BLOCK.get()))).build();
        }

        return this.canoeMissingOutsideLeft;
    }

}
