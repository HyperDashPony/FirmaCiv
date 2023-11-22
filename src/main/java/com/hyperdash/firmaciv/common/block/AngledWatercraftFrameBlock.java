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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
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

    @Deprecated
    public AngledWatercraftFrameBlock(BlockState pBaseState, BlockBehaviour.Properties pProperties) {
        super(pBaseState, pProperties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, Half.BOTTOM)
                        .setValue(SHAPE, StairsShape.STRAIGHT).setValue(WATERLOGGED, Boolean.FALSE)
                        .setValue(FRAME_PROCESSED, 0));
    }

    @Override
    public void onRemove(final BlockState blockState, final Level level, final BlockPos blockPos,
                         final BlockState newState, final boolean isMoving) {
        if (level.getBlockEntity(blockPos) instanceof WatercraftFrameBlockEntity frameBlockEntity) {
            if (!Helpers.isBlock(blockState, newState.getBlock())) {
                frameBlockEntity.ejectInventory();
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

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.FAIL;
        }

        final WatercraftFrameBlockEntity frameBlockEntity;
        {
            final BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (!(blockEntity instanceof WatercraftFrameBlockEntity)) {
                return InteractionResult.FAIL;
            } else frameBlockEntity = (WatercraftFrameBlockEntity) blockEntity;
        }

        final ItemStack held = player.getItemInHand(hand);
        final Item item = held.getItem();
        final int processState = blockState.getValue(FRAME_PROCESSED);

        // Try extract
        if (held.isEmpty()) {
            final ItemStack dropStack;

            if (4 < processState) {
                dropStack = frameBlockEntity.getBoltItems().get(processState - 5).copy();
                frameBlockEntity.deleteBoltItems(processState - 5);
            } else {
                dropStack = frameBlockEntity.getPlankItems().get(processState - 1).copy();
                frameBlockEntity.deletePlankItems(processState - 1);
            }

            if (!dropStack.isEmpty()) {
                ItemHandlerHelper.giveItemToPlayer(player, dropStack);
            }

            if (0 != processState) {
                level.setBlock(blockPos, blockState.setValue(FRAME_PROCESSED, processState - 1), 10);
            }

            return InteractionResult.SUCCESS;
        }

        if (4 > processState && item.getDefaultInstance().is(FirmacivTags.Items.PLANKS)) {
            frameBlockEntity.addPlankItems(held.split(1), processState);
            level.setBlock(blockPos, blockState.setValue(FRAME_PROCESSED, processState + 1), 10);
            level.playSound(null, blockPos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.5F,
                    level.getRandom().nextFloat() * 0.1F + 0.9F);
            return InteractionResult.SUCCESS;
        }

        if (4 <= processState && processState < 8 && item.getDefaultInstance().is(FirmacivItems.COPPER_BOLT.get())) {
            frameBlockEntity.addBoltItems(held.split(1), processState - 4);
            level.setBlock(blockPos, blockState.setValue(FRAME_PROCESSED, processState + 1), 10);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext blockPlaceContext) {
        final BlockState blockState = super.getStateForPlacement(blockPlaceContext);
        return blockState.setValue(FRAME_PROCESSED, 0);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final BlockPos blockPos, final BlockState blockState) {
        return new WatercraftFrameBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(final BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean triggerEvent(final BlockState blockState, final Level level, final BlockPos blockPos, final int pId,
                                int pParam) {
        super.triggerEvent(blockState, level, blockPos, pId, pParam);
        final BlockEntity blockentity = level.getBlockEntity(blockPos);
        return blockentity != null && blockentity.triggerEvent(pId, pParam);
    }
}