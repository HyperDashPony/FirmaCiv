package com.hyperdash.firmaciv.block.blockentity.custom;

import com.hyperdash.firmaciv.block.custom.CanoeComponentBlock;
import com.hyperdash.firmaciv.block.blockentity.FirmacivBlockEntities;
import com.mojang.logging.LogUtils;
import net.dries007.tfc.common.blockentities.TFCBlockEntity;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendarTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.VisibleForTesting;
import org.slf4j.Logger;

import static com.hyperdash.firmaciv.block.custom.CanoeComponentBlock.*;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;

public class CanoeComponentBlockEntity extends TFCBlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();

    private long litTick;
    private boolean isLit;

    public CanoeComponentBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FirmacivBlockEntities.CANOE_COMPONENT_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public boolean isLit() {
        return this.isLit;
    }

    public long getLitTick() {
        return this.litTick;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CanoeComponentBlockEntity canoeBlock) {

        if (canoeBlock.isLit) {
            long remainingTicks = (long)(Integer) TFCConfig.SERVER.pitKilnTicks.get() - (Calendars.SERVER.getTicks() - canoeBlock.litTick);

            if (remainingTicks <= 0L) {

                BlockState newState = state.setValue(CANOE_CARVED, 13);
                level.setBlock(pos, newState, 4);
                CanoeComponentBlock.trySpawnCanoe(level, pos, state.getBlock());
                canoeBlock.isLit = false;
            }
        }

    }


    @VisibleForTesting
    public void light() {

        if(this.isLit){
            return;
        }

        this.isLit = true;
        this.litTick = Calendars.SERVER.getTicks();
        this.markForBlockUpdate();
        BlockState newState = this.getBlockState().setValue(CANOE_CARVED, 12);
        level.setBlock(this.getBlockPos(), newState, 4);

        BlockPos pPos = this.getBlockPos();
        Level pLevel = this.getLevel();

        Direction.Axis axis = pLevel.getBlockState(pPos).getValue(AXIS);

        BlockPos blockPos1 = pPos.relative(axis, 1);
        BlockPos blockPos2 = pPos.relative(axis, -1);

        if(pLevel.getBlockEntity(blockPos1) instanceof CanoeComponentBlockEntity){
            if(pLevel.getBlockState(blockPos1).getValue(CANOE_CARVED) == 11){
                ((CanoeComponentBlockEntity) pLevel.getBlockEntity(blockPos1)).light();
            }
        }
        if(pLevel.getBlockEntity(blockPos2) instanceof CanoeComponentBlockEntity){
            if(pLevel.getBlockState(blockPos2).getValue(CANOE_CARVED) == 11){
                ((CanoeComponentBlockEntity) pLevel.getBlockEntity(blockPos2)).light();
            }
        }
    }

    public long getTicksLeft() {
        assert this.level != null;

        return this.litTick + (long)(Integer)TFCConfig.SERVER.pitKilnTicks.get() - Calendars.get(this.level).getTicks();
    }
}
