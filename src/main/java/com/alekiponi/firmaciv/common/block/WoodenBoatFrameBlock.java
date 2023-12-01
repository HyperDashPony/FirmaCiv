package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.common.blockentity.BoatFrameBlockEntity;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.util.FirmacivTags;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class WoodenBoatFrameBlock extends SquaredAngleBlock implements EntityBlock {
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
    @SuppressWarnings("deprecation")
    public void onRemove(final BlockState blockState, final Level level, final BlockPos blockPos,
            final BlockState newState, final boolean isMoving) {
        if (level.getBlockEntity(blockPos) instanceof BoatFrameBlockEntity frameBlockEntity) {
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
    @SuppressWarnings("deprecation")
    public InteractionResult use(final BlockState blockState, final Level level, final BlockPos blockPos,
            final Player player, final InteractionHand hand, final BlockHitResult hitResult) {
        // Don't do logic on client side
        if (level.isClientSide()) {
            return InteractionResult.FAIL;
        }

        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.FAIL;
        }

        // Quit early if we don't have the right BlockEntity
        if (!(level.getBlockEntity(blockPos) instanceof BoatFrameBlockEntity frameBlockEntity)) {
            return InteractionResult.PASS;
        }

        final ItemStack heldStack = player.getItemInHand(hand);

        final int processState = blockState.getValue(FRAME_PROCESSED);

        // Try extract
        if (heldStack.isEmpty()) {
            // Extract an item
            if (3 >= processState) {
                ItemHandlerHelper.giveItemToPlayer(player, frameBlockEntity.extractPlanks(1));
            } else {
                ItemHandlerHelper.giveItemToPlayer(player, frameBlockEntity.extractBolts(1));
            }

            // Set ourselves back to our base
            if (0 == processState) {
                final BlockState newState = FirmacivBlocks.BOAT_FRAME_ANGLED.get().defaultBlockState()
                        .setValue(SHAPE, blockState.getValue(SHAPE)).setValue(FACING, blockState.getValue(FACING));

                level.setBlock(blockPos, newState, 10);
                return InteractionResult.SUCCESS;
            }

            level.setBlock(blockPos, blockState.setValue(FRAME_PROCESSED, processState - 1), 10);

            return InteractionResult.PASS;
        }

        // Should we do plank stuff
        if (heldStack.is(FirmacivTags.Items.PLANKS)) {
            // Must be [0,3)
            if (3 > processState) {
                final ItemStack remainder = frameBlockEntity.insertPlanks(heldStack.split(1));

                // Couldn't put the item in
                if (!remainder.isEmpty()) {
                    heldStack.grow(1);
                    return InteractionResult.FAIL;
                }

                level.setBlock(blockPos, blockState.cycle(FRAME_PROCESSED), 10);
                level.playSound(null, blockPos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.5F,
                        level.getRandom().nextFloat() * 0.1F + 0.9F);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        // Should we do bolt stuff
        if (heldStack.is(FirmacivItems.COPPER_BOLT.get())) {
            // Must be [3,7)
            if (3 <= processState && processState < 7) {
                frameBlockEntity.insertBolts(heldStack.split(1));
                level.setBlock(blockPos, blockState.cycle(FRAME_PROCESSED), 10);
                level.playSound(null, blockPos, SoundEvents.METAL_PLACE, SoundSource.BLOCKS, 1.5F,
                        level.getRandom().nextFloat() * 0.1F + 0.9F);
                return InteractionResult.SUCCESS;
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final BlockPos blockPos, final BlockState blockState) {
        return new BoatFrameBlockEntity(blockPos, blockState);
    }
}