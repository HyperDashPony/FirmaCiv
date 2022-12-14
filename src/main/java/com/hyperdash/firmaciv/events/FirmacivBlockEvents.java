package com.hyperdash.firmaciv.events;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.block.custom.CanoeComponentBlock;
import com.hyperdash.firmaciv.util.FirmacivTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.hyperdash.firmaciv.block.custom.CanoeComponentBlock.*;

@Mod.EventBusSubscriber(modid = Firmaciv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class FirmacivBlockEvents {

    private FirmacivBlockEvents() {}

    @SubscribeEvent
    public static void registerToolModificationEvents(BlockEvent.BlockToolModificationEvent event){
        if(event.getToolAction() == ToolActions.AXE_STRIP) {
            if(event.getState().is(FirmacivTags.Blocks.CAN_MAKE_CANOE)){
                if(event.getState().getValue(BlockStateProperties.AXIS).isHorizontal()){

                    Block strippedLogBlock = event.getState().getBlock();
                    BlockPos thisBlockPos = event.getPos();
                    LevelAccessor world = event.getWorld();
                    Direction.Axis axis = event.getState().getValue(BlockStateProperties.AXIS);

                    if (CanoeComponentBlock.isValidCanoeShape(world, strippedLogBlock, thisBlockPos)) {
                        world.playSound(event.getPlayer(), thisBlockPos, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);

                        world.destroyBlock(thisBlockPos, false);

                        Block canoeComponentBlock = getByStripped(strippedLogBlock);
                        canoeComponentBlock.defaultBlockState().setValue(AXIS, Direction.Axis.Z);

                        if(axis == Direction.Axis.X){
                            world.setBlock(thisBlockPos, canoeComponentBlock.defaultBlockState().setValue(AXIS, Direction.Axis.X).setValue(FACING, Direction.WEST), 2);
                        } else {
                            world.setBlock(thisBlockPos, canoeComponentBlock.defaultBlockState().setValue(AXIS, Direction.Axis.Z).setValue(FACING, Direction.NORTH), 2);
                        }

                        CanoeComponentBlock.spawnCanoeWithAxe(event.getPlayer().getLevel(), thisBlockPos, strippedLogBlock);


                    }

                }

            }

        }


    }

}
