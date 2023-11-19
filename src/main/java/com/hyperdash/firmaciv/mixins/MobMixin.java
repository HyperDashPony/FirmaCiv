package com.hyperdash.firmaciv.mixins;

import com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities.EmptyCompartmentEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {

    @Shadow
    private Entity leashHolder;

    @Shadow
    private void restoreLeashFromSave() {
    }

    @Shadow
    public void dropLeash(boolean pBroadcastPacket, boolean pDropLeash) {
    }

    @Inject(method = "tickLeash", at = @At("TAIL"))
    protected void injectStopLeashing(CallbackInfo ci) {
        if (this.leashHolder != null) {
            if (this.leashHolder.isPassenger() && this.leashHolder.getVehicle() instanceof EmptyCompartmentEntity) {
                this.dropLeash(true, true);
            }
        }
    }

}
