package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.util.FirmacivTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;

public class AngledBoatFrameBlock extends SquaredAngleBlock {

    public AngledBoatFrameBlock(final Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(final BlockState blockState, final Level level, final BlockPos blockPos,
            final Player player, final InteractionHand hand, final BlockHitResult hitResult) {

        final ItemStack heldStack = player.getItemInHand(hand);

        // Should we do plank stuff
        if (!heldStack.is(FirmacivTags.Items.PLANKS)) return InteractionResult.PASS;

        // We must replace ourselves with the correct wood version
        for (final RegistryObject<Block> registryObject : FirmacivBlocks.WOODEN_BOAT_FRAME_ANGLED.values()) {
            if (!(registryObject.get() instanceof AngledWoodenBoatFrameBlock woodenFrameBlock)) continue;

            // Must find the right block variant for this item
            if (!heldStack.is(woodenFrameBlock.getPlankAsItemStack().getItem())) continue;

            final BlockState newBlockState = woodenFrameBlock.defaultBlockState()
                    .setValue(SHAPE, blockState.getValue(SHAPE)).setValue(FACING, blockState.getValue(FACING));

            level.setBlock(blockPos, newBlockState, 10);

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
}