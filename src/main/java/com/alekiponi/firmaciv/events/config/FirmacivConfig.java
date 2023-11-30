package com.alekiponi.firmaciv.events.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

public class FirmacivConfig {

    public static final ServerConfig SERVER;

    static {
        SERVER = register(ModConfig.Type.SERVER, ServerConfig::new);
    }

    public FirmacivConfig() {
    }

    public static void init() {

    }

    private static <C> C register(ModConfig.Type type, Function<ForgeConfigSpec.Builder, C> factory) {
        Pair<C, ForgeConfigSpec> specPair = (new ForgeConfigSpec.Builder()).configure(factory);

        ModLoadingContext.get().registerConfig(type, specPair.getRight());

        return specPair.getLeft();
    }


}
