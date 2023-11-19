package com.hyperdash.firmaciv.events;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.events.config.FirmacivConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Firmaciv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {


    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof final ServerLevel level) {

            final MinecraftServer server = level.getServer();

            final GameRules rules = level.getGameRules();

            if (FirmacivConfig.SERVER.forceReducedDebugInfo.get()) {
                rules.getRule(GameRules.RULE_REDUCEDDEBUGINFO).set(true, server);
            }

        }
    }

}
