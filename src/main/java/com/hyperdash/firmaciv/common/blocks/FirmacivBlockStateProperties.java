package com.hyperdash.firmaciv.common.blocks;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FirmacivBlockStateProperties {


    public static final IntegerProperty CANOE_CARVED_1;
    // does nothing to the appearance of the block
    public static final IntegerProperty CANOE_CARVED_2;
    public static final IntegerProperty CANOE_CARVED_3;
    public static final IntegerProperty CANOE_CARVED_4;
    public static final IntegerProperty CANOE_CARVED_5;
    // stops reducing the height of the block at this stage
    public static final IntegerProperty CANOE_CARVED_6;
    public static final IntegerProperty CANOE_CARVED_7;
    public static final IntegerProperty CANOE_CARVED_8;

    public static final IntegerProperty CANOE_CARVED_9;

    public static final IntegerProperty CANOE_CARVED_10;
    public static final IntegerProperty CANOE_CARVED_11;
    public static final IntegerProperty CANOE_CARVED_12;
    public static final IntegerProperty CANOE_CARVED_13;

    public static final IntegerProperty FRAME_PROCESSED_0;
    public static final IntegerProperty FRAME_PROCESSED_1;
    // does nothing to the appearance of the block
    public static final IntegerProperty FRAME_PROCESSED_2;
    public static final IntegerProperty FRAME_PROCESSED_3;
    public static final IntegerProperty FRAME_PROCESSED_4;
    public static final IntegerProperty FRAME_PROCESSED_5;
    // stops reducing the height of the block at this stage
    public static final IntegerProperty FRAME_PROCESSED_6;
    public static final IntegerProperty FRAME_PROCESSED_7;
    public static final IntegerProperty FRAME_PROCESSED_8;
    public static final BooleanProperty END;

    public static final BooleanProperty IS_STAIR_SHAPED;
    private static final IntegerProperty[] CANOE_CARVED;
    private static final IntegerProperty[] FRAME_PROCESSED;

    static {
        CANOE_CARVED_1 = IntegerProperty.create("canoe_carved", 0, 1);
        CANOE_CARVED_2 = IntegerProperty.create("canoe_carved", 1, 2);
        CANOE_CARVED_3 = IntegerProperty.create("canoe_carved", 1, 3);
        CANOE_CARVED_4 = IntegerProperty.create("canoe_carved", 1, 4);
        CANOE_CARVED_5 = IntegerProperty.create("canoe_carved", 1, 5);
        CANOE_CARVED_6 = IntegerProperty.create("canoe_carved", 1, 6);
        CANOE_CARVED_7 = IntegerProperty.create("canoe_carved", 1, 7);
        CANOE_CARVED_8 = IntegerProperty.create("canoe_carved", 1, 8);
        CANOE_CARVED_9 = IntegerProperty.create("canoe_carved", 1, 9);
        CANOE_CARVED_10 = IntegerProperty.create("canoe_carved", 1, 10);
        CANOE_CARVED_11 = IntegerProperty.create("canoe_carved", 1, 11);
        CANOE_CARVED_12 = IntegerProperty.create("canoe_carved", 1, 12);
        CANOE_CARVED_13 = IntegerProperty.create("canoe_carved", 1, 13);

        FRAME_PROCESSED_0 = IntegerProperty.create("frame_processed", 0, 1);
        FRAME_PROCESSED_1 = IntegerProperty.create("frame_processed", 0, 1);
        FRAME_PROCESSED_2 = IntegerProperty.create("frame_processed", 0, 2);
        FRAME_PROCESSED_3 = IntegerProperty.create("frame_processed", 0, 3);
        FRAME_PROCESSED_4 = IntegerProperty.create("frame_processed", 0, 4);
        FRAME_PROCESSED_5 = IntegerProperty.create("frame_processed", 0, 5);
        FRAME_PROCESSED_6 = IntegerProperty.create("frame_processed", 0, 6);
        FRAME_PROCESSED_7 = IntegerProperty.create("frame_processed", 0, 7);
        FRAME_PROCESSED_8 = IntegerProperty.create("frame_processed", 0, 8);

        END = BooleanProperty.create("end");
        IS_STAIR_SHAPED = BooleanProperty.create("is_stair_shaped");

        CANOE_CARVED = new IntegerProperty[]{CANOE_CARVED_1, CANOE_CARVED_2, CANOE_CARVED_3, CANOE_CARVED_4, CANOE_CARVED_5,
                CANOE_CARVED_6, CANOE_CARVED_7, CANOE_CARVED_8, CANOE_CARVED_9, CANOE_CARVED_10, CANOE_CARVED_11};

        FRAME_PROCESSED = new IntegerProperty[]{FRAME_PROCESSED_0, FRAME_PROCESSED_1, FRAME_PROCESSED_2, FRAME_PROCESSED_3, FRAME_PROCESSED_4,
                FRAME_PROCESSED_5, FRAME_PROCESSED_6, FRAME_PROCESSED_7, FRAME_PROCESSED_8};
    }

    public static IntegerProperty getCanoeCarvedProperty(int maxStage) {
        if (maxStage > 0 && maxStage <= CANOE_CARVED.length) {
            return CANOE_CARVED[maxStage - 1];
        } else {
            throw new IllegalArgumentException("No canoe_carved property for stages [0, " + maxStage + "]");
        }
    }

}
