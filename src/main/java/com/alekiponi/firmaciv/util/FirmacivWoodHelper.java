package com.alekiponi.firmaciv.util;

import com.alekiponi.firmaciv.common.block.FirmacivBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistryWood;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class FirmacivWoodHelper {
    /**
     * Utility function to centralize all the mod interop relating to TFC woods.
     * This should be used anywhere we want to iterate over all the supported TFC woods
     *
     * @param woodConsumer A consumer that is passed each {@link RegistryWood}
     */
    public static void forAllTFCWoods(final Consumer<RegistryWood> woodConsumer) {
        for (final Wood tfcWood : Wood.values()) {
            woodConsumer.accept(tfcWood);
        }
    }

    /**
     * Creates a map for every TFC wood. See {@link FirmacivWoodHelper#forAllTFCWoods(Consumer)} if you just want to
     * iterate over the wood types we support
     *
     * @param function   The function that's used to get the entry for each wood type. See
     *                   {@link FirmacivBlocks#WOODEN_BOAT_FRAME_ANGLED} for example usage
     * @param <MapValue> The value type of the map
     * @return A map of {@link RegistryWood} to the returned object of the function for that wood type
     */
    public static <MapValue> Map<RegistryWood, MapValue> TFCWoodMap(final Function<RegistryWood, MapValue> function) {
        final Map<RegistryWood, MapValue> map = new HashMap<>();

        forAllTFCWoods(wood -> map.put(wood, function.apply(wood)));

        return map;
    }
}
