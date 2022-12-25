package com.hyperdash.firmaciv.entity.custom;

import com.google.common.collect.Maps;
import com.hyperdash.firmaciv.Firmaciv;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public enum BoatEntityVariant {
    ACACIA(0),
    ASH(1),
    ASPEN(2),
    BIRCH(3),
    BLACKWOOD(4),
    CHESTNUT(5),
    DOUGLAS_FIR(6),
    HICKORY(7),
    KAPOK(8),
    MAPLE(9),
    OAK(10),
    PALM(11),
    PINE(12),
    ROSEWOOD(13),
    SEQUOIA(14),
    SPRUCE(15),
    SYCAMORE(16),
    WHITE_CEDAR(17),
    WILLOW(18);

    private static final BoatEntityVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(BoatEntityVariant::getId)).toArray(BoatEntityVariant[]::new);
    private final int id;

    BoatEntityVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static BoatEntityVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static final Map<BoatEntityVariant, String> TEXTURE_NAME_BY_VARIANT =
            Util.make(Maps.newEnumMap(BoatEntityVariant.class), (entityVariantMap) -> {
                entityVariantMap.put(BoatEntityVariant.ACACIA, "acacia");
                entityVariantMap.put(BoatEntityVariant.ASH, "ash");
                entityVariantMap.put(BoatEntityVariant.ASPEN, "aspen");
                entityVariantMap.put(BoatEntityVariant.BIRCH, "birch");
                entityVariantMap.put(BoatEntityVariant.BLACKWOOD, "blackwood");
                entityVariantMap.put(BoatEntityVariant.CHESTNUT, "chestnut");
                entityVariantMap.put(BoatEntityVariant.DOUGLAS_FIR, "douglas_fir");
                entityVariantMap.put(BoatEntityVariant.HICKORY, "hickory");
                entityVariantMap.put(BoatEntityVariant.KAPOK, "maple");
                entityVariantMap.put(BoatEntityVariant.OAK, "oak");
                entityVariantMap.put(BoatEntityVariant.PALM, "palm");
                entityVariantMap.put(BoatEntityVariant.PINE, "pine");
                entityVariantMap.put(BoatEntityVariant.ROSEWOOD, "rosewood");
                entityVariantMap.put(BoatEntityVariant.SEQUOIA, "sequoia");
                entityVariantMap.put(BoatEntityVariant.SPRUCE, "spruce");
                entityVariantMap.put(BoatEntityVariant.SYCAMORE, "sycamore");
                entityVariantMap.put(BoatEntityVariant.WHITE_CEDAR, "white_cedar");
                entityVariantMap.put(BoatEntityVariant.WILLOW, "willow");
            });
/*
    public ResourceLocation getTextureLocation(FirmacivBoatEntity entity){

        if(entity instanceof CanoeEntity){
            return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/canoe/" + ;
        }

    }


 */
}
