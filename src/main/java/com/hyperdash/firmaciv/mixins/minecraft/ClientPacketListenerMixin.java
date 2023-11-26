package com.hyperdash.firmaciv.mixins.minecraft;

import com.hyperdash.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin{

    @Shadow
    private ClientLevel level;

    @Inject(method = "handleEntityLinkPacket", at = @At("TAIL"))
    private void injectCleatPacket(ClientboundSetEntityLinkPacket pPacket, CallbackInfo ci){
        Entity entity = this.level.getEntity(pPacket.getSourceId());
        if (entity instanceof VehicleCleatEntity) {
            ((VehicleCleatEntity)entity).setDelayedLeashHolderId(pPacket.getDestId());
        }
    }

}
