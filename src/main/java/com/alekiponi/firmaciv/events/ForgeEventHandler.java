package com.alekiponi.firmaciv.events;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.AbstractCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.EmptyCompartmentEntity;
import com.alekiponi.firmaciv.events.config.FirmacivConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = Firmaciv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {

    public static final Logger LOGGER = LogUtils.getLogger();
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

    /*
    @SubscribeEvent
    public static void onPlayerLeave(LivingHurtEvent event){
        if(event.getSource().is(DamageTypes.IN_WALL)){
            if(event.getEntity().getVehicle() instanceof EmptyCompartmentEntity){
                event.setCanceled(true);
            }
        }
    }*/

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
        if(event.getEntity().getVehicle() instanceof EmptyCompartmentEntity){
            event.getEntity().stopRiding();
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        final Entity entity = event.getTarget();
        if (entity instanceof LivingEntity living) {
            if (living.isPassenger() && living.getVehicle() instanceof EmptyCompartmentEntity && event.getEntity().isSecondaryUseActive()) {
                living.stopRiding();
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
        if (entity instanceof Boat) {
            if (FirmacivConfig.SERVER.disableVanillaBoatFunctionality.get()) {
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.FAIL);
            }
        }
    }


}
