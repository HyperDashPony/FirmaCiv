package com.alekiponi.firmaciv.mixins.client;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {

    @Shadow
    private boolean handsBusy;

    @Shadow
    public Input input;

    public LocalPlayerMixin(ClientLevel level, GameProfile profile) {
        super(level, profile);
    }

    @Inject(method = "rideTick", at = @At("HEAD"))
    void injectMovementCapture(CallbackInfo ci) {
        if (this.getVehicle() instanceof EmptyCompartmentEntity compartmentEntity) {
            compartmentEntity.setInput(this.input.left, this.input.right, this.input.up, this.input.down);
            this.handsBusy |= this.input.left || this.input.right || this.input.up || this.input.down;
        }
    }

}
