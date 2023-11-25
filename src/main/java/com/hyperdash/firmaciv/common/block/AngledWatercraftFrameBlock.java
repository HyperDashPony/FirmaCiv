package com.hyperdash.firmaciv.common.block;

import com.hyperdash.firmaciv.common.blockentity.WatercraftFrameBlockEntity;
import com.hyperdash.firmaciv.common.item.FirmacivItems;
import com.hyperdash.firmaciv.util.FirmacivTags;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class AngledWatercraftFrameBlock extends SquaredAngleBlock implements EntityBlock {

    public static final IntegerProperty FRAME_PROCESSED = FirmacivBlockStateProperties.FRAME_PROCESSED_8;

    public AngledWatercraftFrameBlock(final BlockState blockState, final BlockBehaviour.Properties properties) {
        super(blockState, properties);
        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(HALF, Half.BOTTOM)
                        .setValue(SHAPE, StairsShape.STRAIGHT)
                        .setValue(WATERLOGGED, false)
                        .setValue(FRAME_PROCESSED, 0));
    }

    @Override
    public void onRemove(final BlockState blockState, final Level level, final BlockPos blockPos,
            final BlockState newState, final boolean isMoving) {
        if (level.getBlockEntity(blockPos) instanceof WatercraftFrameBlockEntity frameBlockEntity) {
            if (!Helpers.isBlock(blockState, newState.getBlock())) {
                frameBlockEntity.ejectContents();
            }
        }

        if (blockState.hasBlockEntity() && (!blockState.is(newState.getBlock()) || !newState.hasBlockEntity())) {
            level.removeBlockEntity(blockPos);
        }

        super.onRemove(blockState, level, blockPos, newState, isMoving);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FRAME_PROCESSED));
    }

    @Override
    public InteractionResult use(final BlockState blockState, final Level level, final BlockPos blockPos,
            final Player player, final InteractionHand hand, final BlockHitResult hitResult) {
        // Don't do logic on client side
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;

        final WatercraftFrameBlockEntity frameBlockEntity;
        {
            final BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (!(blockEntity instanceof WatercraftFrameBlockEntity)) {
                return InteractionResult.FAIL;
            } else frameBlockEntity = (WatercraftFrameBlockEntity) blockEntity;
        }

        final ItemStack heldStack = player.getItemInHand(hand);
        final int processState = blockState.getValue(FRAME_PROCESSED);

        // Try extract
        if (heldStack.isEmpty()) {
            // Extract an item
            if (4 >= processState) {
                ItemHandlerHelper.giveItemToPlayer(player, frameBlockEntity.extractPlanks(1));
            } else {
                ItemHandlerHelper.giveItemToPlayer(player, frameBlockEntity.extractBolts(1));
            }

            // Update the state
            if (0 < processState) {
                level.setBlock(blockPos, blockState.setValue(FRAME_PROCESSED, processState - 1), 10);
            }

            return InteractionResult.SUCCESS;
        }

        // Should we do plank stuff
        if (heldStack.is(FirmacivTags.Items.PLANKS)) {
            // TODO ensure the stored planks and the planks we are trying to add are the same.
            //  block also needs to be swapped to reflect the stored wood

            // Must be [0,4)
            if (4 > processState) {
                frameBlockEntity.insertPlanks(heldStack.split(1));
                level.setBlock(blockPos, blockState.cycle(FRAME_PROCESSED), 10);
                level.playSound(null, blockPos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.5F,
                        level.getRandom().nextFloat() * 0.1F + 0.9F);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.SUCCESS;
        }

        // Should we do bolt stuff
        if (heldStack.is(FirmacivItems.COPPER_BOLT.get())) {
            // Must be [4,8)
            if (4 <= processState && processState < 8) {
                frameBlockEntity.insertBolts(heldStack.split(1));
                level.setBlock(blockPos, blockState.cycle(FRAME_PROCESSED), 10);
                level.playSound(null, blockPos, SoundEvents.METAL_PLACE, SoundSource.BLOCKS, 1.5F,
                        level.getRandom().nextFloat() * 0.1F + 0.9F);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final BlockPos blockPos, final BlockState blockState) {
        return new WatercraftFrameBlockEntity(blockPos, blockState);
    }
}