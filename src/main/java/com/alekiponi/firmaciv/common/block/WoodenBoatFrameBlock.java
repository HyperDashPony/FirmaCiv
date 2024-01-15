package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.mojang.datafixers.kinds.Const;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistryWood;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

import static com.alekiponi.firmaciv.common.block.FirmacivBlockStateProperties.FRAME_PROCESSED_7;

public class WoodenBoatFrameBlock extends SquaredAngleBlock {
    public static final IntegerProperty FRAME_PROCESSED = FirmacivBlockStateProperties.FRAME_PROCESSED_7;

    public final RegistryWood wood;

    public WoodenBoatFrameBlock(final RegistryWood wood, final Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SHAPE, StairsShape.STRAIGHT)
                        .setValue(WATERLOGGED, false).setValue(FRAME_PROCESSED, 0));
        this.wood = wood;
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FRAME_PROCESSED));
    }

    public enum ConstantShapeDirection {
        NORTH_AND_EAST,
        NORTH_AND_WEST,
        SOUTH_AND_EAST,
        SOUTH_AND_WEST,
        NORTH_AND_SOUTH,
        EAST_AND_WEST,
    }

    public enum ConstantShape {
        STRAIGHT,
        INNER,
        OUTER
    }

    public static ConstantShape getConstantShape(BlockState state){
        if(isInner(state)){
            return ConstantShape.INNER;
        }
        if(isOuter(state)){
            return ConstantShape.OUTER;
        }
        return ConstantShape.STRAIGHT;
    }

    @Nullable
    public static ConstantShapeDirection getDirectionHeirarchy(BlockState state) {
        if (!(state.getBlock() instanceof WoodenBoatFrameBlock)) {
            return null;
        }
        if (state.getValue(FACING) == Direction.SOUTH) {
            if (state.getValue(SHAPE) == StairsShape.STRAIGHT) {
                return ConstantShapeDirection.EAST_AND_WEST;
            }
            if (state.getValue(SHAPE) == StairsShape.INNER_RIGHT) {
                return ConstantShapeDirection.NORTH_AND_EAST;
            }
            if (state.getValue(SHAPE) == StairsShape.INNER_LEFT) {
                return ConstantShapeDirection.NORTH_AND_WEST;
            }
            if (state.getValue(SHAPE) == StairsShape.OUTER_RIGHT) {
                return ConstantShapeDirection.SOUTH_AND_WEST;
            }
            if (state.getValue(SHAPE) == StairsShape.OUTER_LEFT) {
                return ConstantShapeDirection.SOUTH_AND_EAST;
            }
        }
        if (state.getValue(FACING) == Direction.NORTH) {
            if (state.getValue(SHAPE) == StairsShape.STRAIGHT) {
                return ConstantShapeDirection.EAST_AND_WEST;
            }
            if (state.getValue(SHAPE) == StairsShape.INNER_RIGHT) {
                return ConstantShapeDirection.SOUTH_AND_WEST;
            }
            if (state.getValue(SHAPE) == StairsShape.INNER_LEFT) {
                return ConstantShapeDirection.SOUTH_AND_EAST;
            }
            if (state.getValue(SHAPE) == StairsShape.OUTER_RIGHT) {
                return ConstantShapeDirection.NORTH_AND_EAST;
            }
            if (state.getValue(SHAPE) == StairsShape.OUTER_LEFT) {
                return ConstantShapeDirection.NORTH_AND_WEST;
            }
        }
        if (state.getValue(FACING) == Direction.EAST) {
            if (state.getValue(SHAPE) == StairsShape.STRAIGHT) {
                return ConstantShapeDirection.NORTH_AND_SOUTH;
            }
            if (state.getValue(SHAPE) == StairsShape.INNER_RIGHT) {
                return ConstantShapeDirection.NORTH_AND_WEST;
            }
            if (state.getValue(SHAPE) == StairsShape.INNER_LEFT) {
                return ConstantShapeDirection.SOUTH_AND_WEST;
            }
            if (state.getValue(SHAPE) == StairsShape.OUTER_RIGHT) {
                return ConstantShapeDirection.NORTH_AND_EAST;
            }
            if (state.getValue(SHAPE) == StairsShape.OUTER_LEFT) {
                return ConstantShapeDirection.SOUTH_AND_EAST;
            }
        }
        if (state.getValue(FACING) == Direction.WEST) {
            if (state.getValue(SHAPE) == StairsShape.STRAIGHT) {
                return ConstantShapeDirection.NORTH_AND_SOUTH;
            }
            if (state.getValue(SHAPE) == StairsShape.INNER_RIGHT) {
                return ConstantShapeDirection.SOUTH_AND_EAST;
            }
            if (state.getValue(SHAPE) == StairsShape.INNER_LEFT) {
                return ConstantShapeDirection.NORTH_AND_EAST;
            }
            if (state.getValue(SHAPE) == StairsShape.OUTER_RIGHT) {
                return ConstantShapeDirection.NORTH_AND_WEST;
            }
            if (state.getValue(SHAPE) == StairsShape.OUTER_LEFT) {
                return ConstantShapeDirection.SOUTH_AND_WEST;
            }
        }
        return null;
    }

    public static boolean isInner(BlockState state){
        return state.getValue(SHAPE) == StairsShape.INNER_LEFT || state.getValue(SHAPE) == StairsShape.INNER_RIGHT;
    }

    public static boolean isOuter(BlockState state){
        return state.getValue(SHAPE) == StairsShape.OUTER_LEFT || state.getValue(SHAPE) == StairsShape.OUTER_RIGHT;
    }

    public static boolean isStraight(BlockState state){
        return state.getValue(SHAPE) == StairsShape.STRAIGHT;
    }

    @Nullable
    public static ConstantShapeDirection rotateConstantDirection(ConstantShapeDirection constantDirection, Direction direction){
        if(direction == Direction.NORTH){
            return constantDirection;
        }
        if(direction == Direction.SOUTH){
            // clockwise twice / flip
            if(constantDirection == ConstantShapeDirection.NORTH_AND_SOUTH){
                return constantDirection;
            }
            if(constantDirection == ConstantShapeDirection.EAST_AND_WEST){
                return constantDirection;
            }

            if(constantDirection == ConstantShapeDirection.NORTH_AND_EAST){
                return ConstantShapeDirection.SOUTH_AND_WEST;
            }
            if(constantDirection == ConstantShapeDirection.NORTH_AND_WEST){
                return ConstantShapeDirection.SOUTH_AND_EAST;
            }
            if(constantDirection == ConstantShapeDirection.SOUTH_AND_EAST){
                return ConstantShapeDirection.NORTH_AND_WEST;
            }
            if(constantDirection == ConstantShapeDirection.SOUTH_AND_WEST){
                return ConstantShapeDirection.NORTH_AND_EAST;
            }
        }
        if(direction == Direction.EAST){
            // clockwise once
            if(constantDirection == ConstantShapeDirection.NORTH_AND_SOUTH){
                return ConstantShapeDirection.EAST_AND_WEST;
            }
            if(constantDirection == ConstantShapeDirection.EAST_AND_WEST){
                return ConstantShapeDirection.NORTH_AND_SOUTH;
            }

            if(constantDirection == ConstantShapeDirection.NORTH_AND_EAST){
                return ConstantShapeDirection.SOUTH_AND_EAST;
            }
            if(constantDirection == ConstantShapeDirection.NORTH_AND_WEST){
                return ConstantShapeDirection.NORTH_AND_EAST;
            }
            if(constantDirection == ConstantShapeDirection.SOUTH_AND_EAST){
                return ConstantShapeDirection.SOUTH_AND_WEST;
            }
            if(constantDirection == ConstantShapeDirection.SOUTH_AND_WEST){
                return ConstantShapeDirection.NORTH_AND_WEST;
            }
        }
        if(direction == Direction.WEST){
            // counterclockwise once
            if(constantDirection == ConstantShapeDirection.NORTH_AND_SOUTH){
                return ConstantShapeDirection.EAST_AND_WEST;
            }
            if(constantDirection == ConstantShapeDirection.EAST_AND_WEST){
                return ConstantShapeDirection.NORTH_AND_SOUTH;
            }

            if(constantDirection == ConstantShapeDirection.NORTH_AND_EAST){
                return ConstantShapeDirection.NORTH_AND_WEST;
            }
            if(constantDirection == ConstantShapeDirection.NORTH_AND_WEST){
                return ConstantShapeDirection.SOUTH_AND_WEST;
            }
            if(constantDirection == ConstantShapeDirection.SOUTH_AND_EAST){
                return ConstantShapeDirection.NORTH_AND_EAST;
            }
            if(constantDirection == ConstantShapeDirection.SOUTH_AND_WEST){
                return ConstantShapeDirection.SOUTH_AND_EAST;
            }
        }
        return null;
    }

    public static boolean validateForMultiblock(BlockState framestate, ItemStack plankitem) {
        // check if the plank item matches
        if (framestate.getBlock() instanceof WoodenBoatFrameBlock wbfb && wbfb.getPlankAsItemStack()
                .is(plankitem.getItem())) {
            // check if the state matches
            return framestate.getValue(FRAME_PROCESSED_7) == 7;
        }
        return false;

    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(final BlockState blockState, final Level level, final BlockPos blockPos,
                                 final Player player, final InteractionHand hand, final BlockHitResult hitResult) {

        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

        final ItemStack heldStack = player.getItemInHand(hand);

        final int processState = blockState.getValue(FRAME_PROCESSED);

        // Try extract
        if (heldStack.isEmpty() && !level.isClientSide) {
            // Extract an item
            if (processState <= 3) {
                ItemHandlerHelper.giveItemToPlayer(player, this.getPlankAsItemStack());
            } else {
                ItemHandlerHelper.giveItemToPlayer(player, FirmacivItems.COPPER_BOLT.get().getDefaultInstance());
            }

            // Set ourselves back to our base
            if (processState == 0) {
                final BlockState newState = FirmacivBlocks.BOAT_FRAME_ANGLED.get().defaultBlockState()
                        .setValue(SHAPE, blockState.getValue(SHAPE)).setValue(FACING, blockState.getValue(FACING));

                level.setBlock(blockPos, newState, 10);
                return InteractionResult.SUCCESS;
            }

            level.setBlock(blockPos, blockState.setValue(FRAME_PROCESSED, processState - 1), 10);

            return InteractionResult.SUCCESS;
        }

        // Should we do plank stuff
        if (heldStack.is(this.getPlankAsItemStack().getItem())) {
            // Must be [0,3)
            if (processState < 3) {
                heldStack.shrink(1);
                level.setBlock(blockPos, blockState.cycle(FRAME_PROCESSED), 10);
                level.playSound(null, blockPos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.5F,
                        level.getRandom().nextFloat() * 0.1F + 0.9F);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }

        // Should we do bolt stuff
        if (heldStack.is(FirmacivItems.COPPER_BOLT.get()) && player.getOffhandItem().is(TFCTags.Items.HAMMERS)) {
            // Must be [3,7)
            if (3 <= processState && processState < 7) {
                heldStack.shrink(1);
                level.setBlock(blockPos, blockState.cycle(FRAME_PROCESSED), 10);
                level.playSound(null, blockPos, SoundEvents.METAL_PLACE, SoundSource.BLOCKS, 1.5F,
                        level.getRandom().nextFloat() * 0.1F + 0.9F);
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
            return InteractionResult.FAIL;
        }

        return InteractionResult.PASS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getCloneItemStack(final BlockGetter blockGetter, final BlockPos blockPos,
                                       final BlockState blockState) {
        // We don't exist as an item so pass it the base version instead
        return FirmacivBlocks.BOAT_FRAME_ANGLED.get().getCloneItemStack(blockGetter, blockPos, blockState);
    }

    public Block getPlankAsBlock() {
        return wood.getBlock(Wood.BlockType.PLANKS).get();
    }

    public ItemStack getPlankAsItemStack() {
        return wood.getBlock(Wood.BlockType.PLANKS).get().asItem().getDefaultInstance();
    }
}