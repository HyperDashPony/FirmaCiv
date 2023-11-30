package com.alekiponi.firmaciv.events.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Function;

public class ServerConfig {
    public final ForgeConfigSpec.BooleanValue canoeWoodRestriction;
    public final ForgeConfigSpec.BooleanValue forceReducedDebugInfo;
    public final ForgeConfigSpec.BooleanValue disableVanillaBoatFunctionality;

    ServerConfig(ForgeConfigSpec.Builder innerBuilder) {
        Function<String, ForgeConfigSpec.Builder> builder = (name) -> {
            return innerBuilder.translation("firmaciv.config.server." + name);
        };
        innerBuilder.push("general");
        this.canoeWoodRestriction = builder.apply("canoeWoodRestriction")
                .comment("Force creation of canoes from specified wood types.").define("canoeWoodRestriction", true);
        this.forceReducedDebugInfo = builder.apply("forceReducedDebugInfo").comment("Force reduceDebugInfo gamerule.")
                .define("forceReducedDebugInfo", true);
        this.disableVanillaBoatFunctionality = builder.apply("disableVanillaBoatFunctionality").comment("Disables vanilla boat from working.")
                .define("disableVanillaBoatFunctionality", true);

    }
}
