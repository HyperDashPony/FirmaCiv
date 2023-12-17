package com.alekiponi.firmaciv.events;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.block.CanoeComponentBlock;
import com.alekiponi.firmaciv.common.blockentity.FirmacivBlockEntities;
import com.alekiponi.firmaciv.events.config.FirmacivConfig;
import com.alekiponi.firmaciv.util.FirmacivTags;
import net.dries007.tfc.util.events.StartFireEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent.BlockToolModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

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

        final Level level = event.getContext().getLevel();
        final BlockState blockState = event.getState();
        final ItemStack heldStack = event.getHeldItemStack();
        final BlockPos blockPos = event.getPos();

        // Item is a saw so we should attempt a conversion
        if (heldStack.is(FirmacivTags.Items.SAWS)) {

            {
                final Player player = event.getPlayer();
                // Only do the early return if not crouching when we have a player
                if (player != null && !player.isCrouching()) return;
            }

            final boolean isConvertibleBlock = blockState.is(
                    FirmacivConfig.SERVER.canoeWoodRestriction.get() ? FirmacivTags.Blocks.CAN_MAKE_CANOE : FirmacivTags.Blocks.CAN_MAKE_CANOE_UNRESTRICTED);

            if (isConvertibleBlock) {
                final Direction.Axis axis = blockState.getValue(BlockStateProperties.AXIS);

                if (!axis.isHorizontal()) return;

                if (!CanoeComponentBlock.isValidShape(level, blockPos)) return;

                final Block canoeComponentBlock = CanoeComponentBlock.getByStripped(blockState.getBlock());
                final BlockState canoeComponentState = canoeComponentBlock.defaultBlockState()
                        .setValue(CanoeComponentBlock.AXIS, axis);

                event.setFinalState(canoeComponentState);

                // Cannot modify level
                if (event.isSimulated()) return;

                level.setBlock(blockPos.relative(axis, 1), canoeComponentState.setValue(CanoeComponentBlock.SHAPE,
                                CanoeComponentBlock.Shape.endFromAxisAndAxisDirection(axis, Direction.AxisDirection.POSITIVE)),
                        Block.UPDATE_ALL_IMMEDIATE);

                level.setBlock(blockPos.relative(axis, -1), canoeComponentState.setValue(CanoeComponentBlock.SHAPE,
                                CanoeComponentBlock.Shape.endFromAxisAndAxisDirection(axis, Direction.AxisDirection.NEGATIVE)),
                        Block.UPDATE_ALL_IMMEDIATE);
                return;
            }
        }

        // All other axe strip events try to process the canoe component
        if (blockState.is(FirmacivTags.Blocks.CANOE_COMPONENT_BLOCKS)) {
            processCanoeComponent(blockState, heldStack).ifPresent(finalState -> {
                event.setFinalState(finalState);
                // Cannot modify level so we shouldn't add particles
                if (event.isSimulated()) return;

                level.addDestroyBlockEffect(event.getPos(), finalState);
            });
        }
    }

    /**
     * Attempts to process a canoe component
     *
     * @param blockState The canoe component state to process
     * @param heldStack  The held stack that's being used to process the component
     * @return A blockstate if it could be processed
     */
    private static Optional<BlockState> processCanoeComponent(final BlockState blockState, final ItemStack heldStack) {
        if (heldStack.is(FirmacivTags.Items.SAWS)) {
            if (3 < blockState.getValue(CanoeComponentBlock.CANOE_CARVED)) return Optional.empty();

            return Optional.of(blockState.cycle(CanoeComponentBlock.CANOE_CARVED));
        }

        if (heldStack.is(FirmacivTags.Items.AXES)) {

            if (4 > blockState.getValue(CanoeComponentBlock.CANOE_CARVED)) return Optional.empty();

            if (9 < blockState.getValue(CanoeComponentBlock.CANOE_CARVED)) return Optional.empty();

            return Optional.of(blockState.cycle(CanoeComponentBlock.CANOE_CARVED));
        }

        return Optional.empty();
    }
}