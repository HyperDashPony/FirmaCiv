package com.hyperdash.firmaciv.events.config;

import net.dries007.tfc.common.blocks.plant.fruit.FruitBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.capabilities.size.Size;
import net.dries007.tfc.config.animals.MammalConfig;
import net.dries007.tfc.config.animals.OviparousAnimalConfig;
import net.dries007.tfc.config.animals.ProducingMammalConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.EnumMap;
import java.util.function.Function;

public class ServerConfig {
    public final ForgeConfigSpec.BooleanValue canoeWoodRestriction;
    public final ForgeConfigSpec.BooleanValue forceReducedDebugInfo;

    ServerConfig(ForgeConfigSpec.Builder innerBuilder) {
        Function<String, ForgeConfigSpec.Builder> builder = (name) -> {
            return innerBuilder.translation("firmaciv.config.server." + name);
        };
        innerBuilder.push("general");
        this.canoeWoodRestriction = ((ForgeConfigSpec.Builder)builder.apply("canoeWoodRestriction")).comment("Force creation of canoes from specified wood types.").define("canoeWoodRestriction", true);
        this.forceReducedDebugInfo = ((ForgeConfigSpec.Builder)builder.apply("forceReducedDebugInfo")).comment("Force reduceDebugInfo gamerule.").define("forceReducedDebugInfo", true);

    }
}
