package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.util.FirmacivTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class FlatBoatFrameBlock extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape HALF_SHAPE = Block.box(0, 0, 0, 16, 8, 16);

    public FlatBoatFrameBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(final BlockState blockState, final Level level, final BlockPos blockPos,
            final Player player, final InteractionHand hand, final BlockHitResult hitResult) {

        final ItemStack heldStack = player.getItemInHand(hand);

        // Should we do plank stuff
        if (!heldStack.is(FirmacivTags.Items.PLANKS)) return InteractionResult.PASS;

        // We must replace ourselves with the correct wood version
        for (final RegistryObject<FlatWoodenBoatFrameBlock> registryObject : FirmacivBlocks.WOODEN_BOAT_FRAME_FLAT.values()) {
            final FlatWoodenBoatFrameBlock woodenFrameBlock = registryObject.get();

            // Must find the right block variant for this item
            if (!heldStack.is(woodenFrameBlock.getUnderlyingPlank().asItem())) continue;

            level.setBlock(blockPos, woodenFrameBlock.defaultBlockState(), UPDATE_CLIENTS | UPDATE_IMMEDIATE);

            if(!player.getAbilities().instabuild){
                heldStack.shrink(1);
            }

            level.playSound(null, blockPos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.5F,
                    level.getRandom().nextFloat() * 0.1F + 0.9F);
            return InteractionResult.SUCCESS;
        }

        Firmaciv.LOGGER.error("Couldn't find a frame for the item {} even though it's contained in {}",
                heldStack.getItem(), FirmacivTags.Items.PLANKS);

        return InteractionResult.PASS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(final BlockState blockState, final Direction direction,
            final BlockState neighborState, final LevelAccessor levelAccessor, final BlockPos blockPos,
            final BlockPos neighborPos) {

        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return super.updateShape(blockState, direction, neighborState, levelAccessor, blockPos, neighborPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean useShapeForLightOcclusion(final BlockState pState) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(final BlockState blockState, final BlockGetter blockGetter, final BlockPos blockPos,
            final CollisionContext collisionContext) {
        return HALF_SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(final BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext placeContext) {
        final FluidState fluidState = placeContext.getLevel().getFluidState(placeContext.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isPathfindable(final BlockState blockState, final BlockGetter blockGetter, final BlockPos blockPos,
            final PathComputationType computationType) {
        return computationType == PathComputationType.WATER && blockGetter.getFluidState(blockPos).is(FluidTags.WATER);
    }
}