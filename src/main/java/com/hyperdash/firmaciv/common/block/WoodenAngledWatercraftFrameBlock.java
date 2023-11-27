package com.hyperdash.firmaciv.common.block;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistryWood;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WoodenAngledWatercraftFrameBlock extends AngledWatercraftFrameBlock {

    public final RegistryWood wood;

    public WoodenAngledWatercraftFrameBlock(final RegistryWood wood, final BlockState blockState,
            final Properties properties) {
        super(blockState, properties);
        this.wood = wood;
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
}