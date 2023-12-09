package com.alekiponi.firmaciv.common.blockentity;

import com.alekiponi.firmaciv.common.block.CanoeComponentBlock;
import net.dries007.tfc.common.blockentities.TFCBlockEntity;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.calendar.Calendars;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static com.alekiponi.firmaciv.common.block.CanoeComponentBlock.CANOE_CARVED;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;

public class CanoeComponentBlockEntity extends TFCBlockEntity {
    private long litTick;
    private boolean isLit;

    public CanoeComponentBlockEntity(final BlockPos blockPos, final BlockState blockState) {
        super(FirmacivBlockEntities.CANOE_COMPONENT_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    public static void serverTick(final Level level, final BlockPos blockPos, final BlockState blockState,
            final CanoeComponentBlockEntity canoeBlockEntity) {
        if (!canoeBlockEntity.isLit) return;

        final long remainingTicks = TFCConfig.SERVER.pitKilnTicks.get() - (Calendars.SERVER.getTicks() - canoeBlockEntity.litTick);

        if (remainingTicks <= 0) {
            final BlockState newState = blockState.setValue(CANOE_CARVED, 13);
            level.setBlock(blockPos, newState, 4);
            CanoeComponentBlock.trySpawnCanoe(level, blockPos, blockState.getBlock());
            canoeBlockEntity.isLit = false;
        }
    }

    public boolean isLit() {
        return this.isLit;
    }

    public long getLitTick() {
        return this.litTick;
    }

    public void light() {
        assert this.level != null : "Level should not be null";

        if (this.isLit) return;

        this.isLit = true;
        this.litTick = Calendars.SERVER.getTicks();
        this.markForBlockUpdate();
        final BlockState newState = this.getBlockState().setValue(CANOE_CARVED, 12);
        this.level.setBlock(this.getBlockPos(), newState, 4);

        final BlockPos pPos = this.getBlockPos();

        final Direction.Axis axis = this.level.getBlockState(pPos).getValue(AXIS);

        final BlockPos blockPosAhead = pPos.relative(axis, 1);
        final BlockPos blockPosBehind = pPos.relative(axis, -1);

        if (this.level.getBlockEntity(blockPosAhead) instanceof CanoeComponentBlockEntity canoeBlockEntity) {
            if (this.level.getBlockState(blockPosAhead).getValue(CANOE_CARVED) == 11) {
                canoeBlockEntity.light();
            }
        }

        if (this.level.getBlockEntity(blockPosBehind) instanceof CanoeComponentBlockEntity canoeBlockEntity) {
            if (this.level.getBlockState(blockPosBehind).getValue(CANOE_CARVED) == 11) {
                canoeBlockEntity.light();
            }
        }
    }

    public void loadAdditional(final CompoundTag compoundTag) {
        this.isLit = compoundTag.getBoolean("isLit");
        this.litTick = compoundTag.getLong("litTick");
        super.loadAdditional(compoundTag);
    }

    public void saveAdditional(final CompoundTag compoundTag) {
        compoundTag.putBoolean("isLit", this.isLit);
        compoundTag.putLong("litTick", this.litTick);
        super.saveAdditional(compoundTag);
    }

    public long getTicksLeft() {
        assert this.level != null;

        return this.litTick + TFCConfig.SERVER.pitKilnTicks.get() - Calendars.get(this.level).getTicks();
    }
}
