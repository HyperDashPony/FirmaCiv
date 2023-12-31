package com.alekiponi.firmaciv.mixins.client;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

    @Shadow
    private ClientLevel level;

    @Shadow
    private final Minecraft minecraft;

    protected ClientPacketListenerMixin(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Inject(method = "handleEntityLinkPacket", at = @At("TAIL"))
    private void injectCleatPacket(ClientboundSetEntityLinkPacket pPacket, CallbackInfo ci){
        PacketUtils.ensureRunningOnSameThread(pPacket, (ClientPacketListener)(Object)this, this.minecraft);
        Entity entity = this.level.getEntity(pPacket.getSourceId());
        if (entity instanceof VehicleCleatEntity cleat) {
            cleat.setDelayedLeashHolderId(pPacket.getDestId());
        }
    }

}
