package com.hyperdash.firmaciv.block.entity.custom;

import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.block.custom.CanoeComponentBlock;
import com.hyperdash.firmaciv.block.custom.CanoeFire;
import com.hyperdash.firmaciv.block.entity.FirmacivBlockEntities;
import com.mojang.logging.LogUtils;
import net.dries007.tfc.common.blockentities.PitKilnBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blockentities.TFCBlockEntity;
import net.dries007.tfc.common.blocks.devices.PitKilnBlock;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendarTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;
import org.slf4j.Logger;

import java.util.Iterator;

import static com.hyperdash.firmaciv.block.custom.CanoeComponentBlock.trySpawnCanoe;

public class CanoeComponentBlockEntity extends TFCBlockEntity implements ICalendarTickable {


    private long litTick;
    private boolean isLit;

    public CanoeComponentBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FirmacivBlockEntities.CANOE_COMPONENT_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void onCalendarUpdate(long l) {

    }

    public boolean isLit() {
        return this.isLit;
    }

    public long getLitTick() {
        return this.litTick;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CanoeComponentBlockEntity canoeBlock) {
        Logger LOGGER = LogUtils.getLogger();


        if (canoeBlock.isLit) {
            BlockPos above = pos.above();
            if (level.isEmptyBlock(above) || level.getBlockState(above).is(Blocks.FIRE)) {
                //level.setBlockAndUpdate(above, FirmacivBlocks.CANOE_FIRE.get().defaultBlockState());
            }

            long remainingTicks = (long)(Integer) TFCConfig.SERVER.pitKilnTicks.get() - (Calendars.SERVER.getTicks() - canoeBlock.litTick);

            LOGGER.info("remainingTicks is  " + remainingTicks);

            if (remainingTicks <= 0L) {
                LOGGER.info("setting state");
                level.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
                level.setBlock(pos, canoeBlock.getBlockState().setValue(CanoeComponentBlock.CANOE_CARVED, 13), 4);
                canoeBlock.isLit = false;
                CanoeComponentBlock.trySpawnCanoe(level, pos, canoeBlock.getBlockState().getBlock());
            }
        }

    }

    public boolean tryLight() {
        if (this.level != null && !this.isLit()) {
            BlockPos above = this.worldPosition.above();
            if (BaseFireBlock.canBePlacedAt(this.level, above, Direction.UP)) {

                this.light();
                this.level.setBlockAndUpdate(this.worldPosition, (BlockState)this.level.getBlockState(this.worldPosition).setValue(CanoeComponentBlock.CANOE_CARVED, 12));
                //this.level.setBlockAndUpdate(above, FirmacivBlocks.CANOE_FIRE.get().defaultBlockState());

                return true;
            }
        }

        return false;
    }

    @VisibleForTesting
    public void light() {
        this.isLit = true;
        this.litTick = Calendars.SERVER.getTicks();
        this.markForBlockUpdate();
        BlockState newState = this.getBlockState().setValue(CanoeComponentBlock.CANOE_CARVED, 12);
        level.setBlock(this.getBlockPos(), newState, 4);
    }

    public long getTicksLeft() {
        assert this.level != null;

        return this.litTick + (long)(Integer)TFCConfig.SERVER.pitKilnTicks.get() - Calendars.get(this.level).getTicks();
    }

    @Override
    public long getLastUpdateTick() {
        return 0;
    }

    @Override
    public void setLastUpdateTick(long l) {

    }
}
