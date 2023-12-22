package com.alekiponi.firmaciv.common.block;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FirmacivBlockStateProperties {
    public static final IntegerProperty CANOE_CARVED = IntegerProperty.create("canoe_carved", 0, 13);
    public static final IntegerProperty FRAME_PROCESSED = IntegerProperty.create("frame_processed", 0, 7);
    public static final EnumProperty<CanoeComponentBlock.Shape> CANOE_SHAPE = EnumProperty.create("shape",
            CanoeComponentBlock.Shape.class);
}