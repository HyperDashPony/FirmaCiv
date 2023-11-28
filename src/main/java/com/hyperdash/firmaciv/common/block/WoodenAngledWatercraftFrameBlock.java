package com.hyperdash.firmaciv.common.block;

import com.hyperdash.firmaciv.common.blockentity.WatercraftFrameBlockEntity;
import com.hyperdash.firmaciv.common.item.FirmacivItems;
import com.hyperdash.firmaciv.util.FirmacivTags;
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
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

import static com.hyperdash.firmaciv.common.block.AngledWatercraftFrameBlock.FRAME_PROCESSED;

public class WoodenAngledWatercraftFrameBlock extends SquaredAngleBlock implements EntityBlock {

    public final RegistryWood wood;

    public WoodenAngledWatercraftFrameBlock(final RegistryWood wood, final BlockState blockState,
            final Properties properties) {
        super(blockState, properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, Half.BOTTOM)
                        .setValue(SHAPE, StairsShape.STRAIGHT).setValue(WATERLOGGED, false)
                        // TODO state 0 is no longer actually used as no progress is represented as a separate block
                        .setValue(FRAME_PROCESSED, 0));
        this.wood = wood;
    }

    @Override
    public void onRemove(final BlockState blockState, final Level level, final BlockPos blockPos,
            final BlockState newState, final boolean isMoving) {
        if (level.getBlockEntity(blockPos) instanceof WatercraftFrameBlockEntity frameBlockEntity) {
            if (!blockState.is(newState.getBlock())) {
                frameBlockEntity.ejectContents();
            }
        }

        if (blockState.hasBlockEntity() && (!blockState.is(newState.getBlock()) || !newState.hasBlockEntity())) {
            level.removeBlockEntity(blockPos);
        }
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

        // Quit early if we don't have the right BlockEntity
        if (!(level.getBlockEntity(blockPos) instanceof WatercraftFrameBlockEntity frameBlockEntity)) {
            return InteractionResult.FAIL;
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

            // Set ourselves back to our base
            if (1 == processState) {
                final BlockState newState = FirmacivBlocks.WATERCRAFT_FRAME_ANGLED.get().defaultBlockState()
                        .setValue(SHAPE, blockState.getValue(SHAPE)).setValue(FACING, blockState.getValue(FACING));
                level.setBlock(blockPos, newState, 10);
                return InteractionResult.SUCCESS;
            }

            level.setBlock(blockPos, blockState.setValue(FRAME_PROCESSED, processState - 1), 10);

            return InteractionResult.SUCCESS;
        }

        // Should we do plank stuff
        if (heldStack.is(FirmacivTags.Items.PLANKS)) {
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

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getCloneItemStack(final BlockGetter blockGetter, final BlockPos blockPos,
            final BlockState blockState) {
        // We don't exist as an item so pass it the base version instead
        return FirmacivBlocks.WATERCRAFT_FRAME_ANGLED.get().getCloneItemStack(blockGetter, blockPos, blockState);
    }

    public Block getUnderlyingPlank() {
        return wood.getBlock(Wood.BlockType.PLANKS).get();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final BlockPos blockPos, final BlockState blockState) {
        return new WatercraftFrameBlockEntity(blockPos, blockState);
    }
}