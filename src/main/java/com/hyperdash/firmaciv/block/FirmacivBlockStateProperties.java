package com.hyperdash.firmaciv.block;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FirmacivBlockStateProperties {

    public static final IntegerProperty CANOE_CARVED_0;
    public static final IntegerProperty CANOE_CARVED_1;
    public static final IntegerProperty CANOE_CARVED_2;
    public static final IntegerProperty CANOE_CARVED_3;
    public static final IntegerProperty CANOE_CARVED_4;
    // stops reducing the height of the block at this stage
    public static final IntegerProperty CANOE_CARVED_5;
    public static final IntegerProperty CANOE_CARVED_6;
    public static final IntegerProperty CANOE_CARVED_7;
    public static final IntegerProperty CANOE_CARVED_8;

    public static final BooleanProperty CANOE_HOLLOWED;

    static{
        CANOE_CARVED_0 = BlockStateProperties.STAGE;
        CANOE_CARVED_1 = IntegerProperty.create("canoe_carved", 0, 1);
        CANOE_CARVED_2 = IntegerProperty.create("canoe_carved", 0, 2);
        CANOE_CARVED_3 = IntegerProperty.create("canoe_carved", 0, 3);
        CANOE_CARVED_4 = IntegerProperty.create("canoe_carved", 0, 4);
        CANOE_CARVED_5 = IntegerProperty.create("canoe_carved", 0, 5);
        CANOE_CARVED_6 = IntegerProperty.create("canoe_carved", 0, 6);
        CANOE_CARVED_7 = IntegerProperty.create("canoe_carved", 0, 7);
        CANOE_CARVED_8 = IntegerProperty.create("canoe_carved", 0, 8);

        CANOE_HOLLOWED = BooleanProperty.create("cane_hollowed");
    }

}
