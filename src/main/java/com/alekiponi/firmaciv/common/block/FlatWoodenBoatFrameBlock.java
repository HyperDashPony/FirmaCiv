package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.common.item.FirmacivItems;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistryWood;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;

import static com.alekiponi.firmaciv.common.block.FirmacivBlockStateProperties.FRAME_PROCESSED_7;

public class FlatWoodenBoatFrameBlock extends FlatBoatFrameBlock {

    public static final IntegerProperty FRAME_PROCESSED = FirmacivBlockStateProperties.FRAME_PROCESSED_7;
    public final RegistryWood wood;

    public FlatWoodenBoatFrameBlock(final RegistryWood wood, final Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FRAME_PROCESSED, 0));
        this.wood = wood;
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FRAME_PROCESSED));
    }

    public static boolean validateProcessed(BlockState framestate, ItemStack plankitem) {
        // check if the plank item matches
        if (framestate.getBlock() instanceof FlatWoodenBoatFrameBlock wbfb && wbfb.getPlankAsItemStack()
                .is(plankitem.getItem())) {
            // check if the state matches
            return framestate.getValue(FRAME_PROCESSED_7) == 7;
        }
        return false;
    }

    @Override
    public InteractionResult use(final BlockState blockState, final Level level, final BlockPos blockPos,
            final Player player, final InteractionHand hand, final BlockHitResult hitResult) {

        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

        final ItemStack heldStack = player.getItemInHand(hand);

        final int processState = blockState.getValue(FRAME_PROCESSED);

        // Try extract
        if (heldStack.isEmpty() && !level.isClientSide) {
            // Extract an item
            if (processState <= 3) {
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(this.getUnderlyingPlank()));
            } else {
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(FirmacivItems.COPPER_BOLT.get()));
            }

            // Set ourselves back to our base
            if (processState == 0) {
                level.setBlock(blockPos, FirmacivBlocks.BOAT_FRAME_FLAT.get().defaultBlockState(),
                        UPDATE_CLIENTS | UPDATE_IMMEDIATE);
                return InteractionResult.SUCCESS;
            }

            level.setBlock(blockPos, blockState.setValue(FRAME_PROCESSED, processState - 1),
                    UPDATE_CLIENTS | UPDATE_IMMEDIATE);

            return InteractionResult.SUCCESS;
        }

        // Should we do plank stuff
        if (heldStack.is(this.getUnderlyingPlank().asItem())) {
            // Must be [0,3)
            if (processState < 3) {
                heldStack.shrink(1);
                level.setBlock(blockPos, blockState.cycle(FRAME_PROCESSED), UPDATE_CLIENTS | UPDATE_IMMEDIATE);
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
        return FirmacivBlocks.BOAT_FRAME_FLAT.get().getCloneItemStack(blockGetter, blockPos, blockState);
    }

    public Block getUnderlyingPlank() {
        return wood.getBlock(Wood.BlockType.PLANKS).get();
    }

    public ItemStack getPlankAsItemStack() {
        return wood.getBlock(Wood.BlockType.PLANKS).get().asItem().getDefaultInstance();
    }
}