package com.alekiponi.firmaciv.common.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FirmacivBlockStateProperties {
    public static final IntegerProperty CANOE_CARVED = IntegerProperty.create("canoe_carved", 1, 13);
    public static final IntegerProperty FRAME_PROCESSED = IntegerProperty.create("frame_processed", 0, 7);
    public static final BooleanProperty END = BooleanProperty.create("end");
}