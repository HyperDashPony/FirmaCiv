package com.hyperdash.firmaciv.events;

import com.google.common.eventbus.Subscribe;
import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.KayakEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Firmaciv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerRenderEvents {

    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        Player player = (Player) event.getEntity();

        if(player.isPassenger() && player.getVehicle() instanceof KayakEntity){
            event.getRenderer().getModel().rightLeg.visible = false;
            event.getRenderer().getModel().leftLeg.visible = false;
            event.getRenderer().getModel().rightPants.visible = false;
            event.getRenderer().getModel().leftPants.visible = false;
        }


    }

    @SubscribeEvent
    public static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        Player player = (Player) event.getEntity();
        if(player.isPassenger() && player.getVehicle() instanceof KayakEntity){
            event.getRenderer().getModel().rightLeg.visible = false;
            event.getRenderer().getModel().leftLeg.visible = false;
            event.getRenderer().getModel().rightPants.visible = false;
            event.getRenderer().getModel().leftPants.visible = false;
        }


    }

}
