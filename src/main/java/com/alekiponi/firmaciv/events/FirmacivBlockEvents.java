package com.alekiponi.firmaciv.events;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.block.CanoeComponentBlock;
import com.alekiponi.firmaciv.common.blockentity.FirmacivBlockEntities;
import com.alekiponi.firmaciv.events.config.FirmacivConfig;
import com.alekiponi.firmaciv.util.FirmacivTags;
import net.dries007.tfc.util.events.StartFireEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent.BlockToolModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Firmaciv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class FirmacivBlockEvents {

    @SubscribeEvent
    public static void onStartFire(final StartFireEvent event) {
        final BlockState blockState = event.getState();
        final BlockPos blockPos = event.getPos();

        if (blockState.getBlock() instanceof CanoeComponentBlock) {
            event.getLevel().getBlockEntity(blockPos, FirmacivBlockEntities.CANOE_COMPONENT_BLOCK_ENTITY.get())
                    .ifPresent(canoe -> {
                        canoe.light();
                        event.setCanceled(true);
                    });
        }
    }

    @SubscribeEvent
    public static void onBlockToolModification(final BlockToolModificationEvent event) {
        // We only care about strip events
        if (event.getToolAction() != ToolActions.AXE_STRIP) return;

        final BlockState blockState = event.getState();
        final ItemStack heldStack = event.getHeldItemStack();

        // Item is a saw so we should attempt a conversion
        if (heldStack.is(FirmacivTags.Items.SAWS)) {
            final boolean isConvertibleBlock = blockState.is(
                    FirmacivConfig.SERVER.canoeWoodRestriction.get() ? FirmacivTags.Blocks.CAN_MAKE_CANOE : FirmacivTags.Blocks.CAN_MAKE_CANOE_UNRESTRICTED);

            if (isConvertibleBlock && blockState.getValue(BlockStateProperties.AXIS).isHorizontal()) {
                convertLogToCanoeComponent(event);
                return;
            }
        }

        // All other axe strip events try to process the canoe component
        if (blockState.is(FirmacivTags.Blocks.CANOE_COMPONENT_BLOCKS)) {
            if (blockState.getValue(BlockStateProperties.AXIS).isHorizontal()) {
                processCanoeComponent(event);
            }
        }
    }

    private static void processCanoeComponent(final BlockToolModificationEvent event) {
        final Block canoeComponentBlock = event.getState().getBlock();
        final BlockState canoeComponentBlockState = event.getState();
        final BlockPos thisBlockPos = event.getPos();
        final LevelAccessor world = event.getLevel();
        final Direction.Axis axis = event.getState().getValue(CanoeComponentBlock.AXIS);

        final int nextCanoeCarvedState = event.getState().getValue(CanoeComponentBlock.CANOE_CARVED) + 1;

        if (canoeComponentBlockState.getValue(CanoeComponentBlock.CANOE_CARVED) < 5 && event.getPlayer()
                .getItemInHand(event.getPlayer().getUsedItemHand()).is(FirmacivTags.Items.SAWS)) {
            event.getPlayer().swing(event.getPlayer().getUsedItemHand());
            world.setBlock(thisBlockPos,
                    event.getState().setValue(CanoeComponentBlock.CANOE_CARVED, nextCanoeCarvedState), 2);
            event.getPlayer().level().addDestroyBlockEffect(thisBlockPos, canoeComponentBlockState);
            event.getPlayer().getItemInHand(event.getContext().getHand()).getUseAnimation();
            world.playSound(event.getPlayer(), thisBlockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (nextCanoeCarvedState == 5) {
                Block.dropResources(canoeComponentBlockState, world, thisBlockPos.above(), null);
                //Block.dropResources(canoeComponentBlockState, event.getPlayer().getLevel(), thisBlockPos, null, event.getPlayer(), Item);

            }

        } else if (canoeComponentBlockState.getValue(
                CanoeComponentBlock.CANOE_CARVED) >= 5 && canoeComponentBlockState.getValue(
                CanoeComponentBlock.CANOE_CARVED) < 11 && event.getPlayer()
                .getItemInHand(event.getPlayer().getUsedItemHand()).is(FirmacivTags.Items.AXES)) {
            event.getPlayer().swing(event.getPlayer().getUsedItemHand());

            BlockPos blockPos1;
            // if there are three in a row then it's valid
            boolean flag = false;
            int row = 0;
            for (int i = -2; i <= 2; ++i) {
                blockPos1 = thisBlockPos.relative(axis, i);
                if ((world.getBlockState(blockPos1).is(canoeComponentBlock) && world.getBlockState(blockPos1)
                        .getValue(CanoeComponentBlock.CANOE_CARVED) >= 5)) {
                    row++;
                    flag = row >= 3;
                } else {
                    row = 0;
                }
            }

            if (flag) {
                world.setBlock(thisBlockPos,
                        event.getState().setValue(CanoeComponentBlock.CANOE_CARVED, nextCanoeCarvedState), 2);
                event.getPlayer().level().addDestroyBlockEffect(thisBlockPos, canoeComponentBlockState);
                event.getPlayer().getItemInHand(event.getContext().getHand()).getUseAnimation();
                world.playSound(event.getPlayer(), thisBlockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    private static void convertLogToCanoeComponent(final BlockToolModificationEvent event) {
        final Block strippedLogBlock = event.getState().getBlock();
        final BlockPos thisBlockPos = event.getPos();
        final LevelAccessor world = event.getLevel();
        final Level level = event.getPlayer().level();

        if (CanoeComponentBlock.isValidCanoeShape(world, strippedLogBlock, thisBlockPos)) {
            world.playSound(event.getPlayer(), thisBlockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            event.getPlayer().swing(event.getPlayer().getUsedItemHand());
            event.getPlayer().level().addDestroyBlockEffect(thisBlockPos, event.getState());

            final Block canoeComponentBlock = CanoeComponentBlock.getByStripped(strippedLogBlock);
            canoeComponentBlock.defaultBlockState().setValue(CanoeComponentBlock.AXIS, Direction.Axis.Z);
            final Direction.Axis axis = event.getState().getValue(CanoeComponentBlock.AXIS);

            world.setBlock(thisBlockPos,
                    CanoeComponentBlock.getStateForPlacement(level, strippedLogBlock, thisBlockPos), 2);

            final BlockPos blockPosAhead = thisBlockPos.relative(axis, 1);
            final BlockPos blockPosBehind = thisBlockPos.relative(axis, -1);

            if (world.getBlockState(blockPosAhead).is(canoeComponentBlock) && world.getBlockState(blockPosBehind)
                    .is(canoeComponentBlock)) {
                CanoeComponentBlock.setEndPieces(event.getPlayer().level(), thisBlockPos, canoeComponentBlock, true);
                CanoeComponentBlock.setEndPieces(event.getPlayer().level(), thisBlockPos.relative(axis, -1),
                        canoeComponentBlock, false);
            } else if (level.getBlockState(blockPosAhead).is(canoeComponentBlock)) {
                CanoeComponentBlock.setEndPieces(level, blockPosAhead, canoeComponentBlock, true);
            } else {
                CanoeComponentBlock.setEndPieces(level, blockPosBehind, canoeComponentBlock, false);
            }
        }
    }
}