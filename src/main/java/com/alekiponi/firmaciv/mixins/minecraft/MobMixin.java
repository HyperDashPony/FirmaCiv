package com.alekiponi.firmaciv.mixins.minecraft;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.EmptyCompartmentEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin extends Entity {

    @Shadow
    private Entity leashHolder;

    public MobMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public void dropLeash(boolean pBroadcastPacket, boolean pDropLeash) {
    }

    @Inject(method = "tickLeash", at = @At("HEAD"))
    protected void injectStopLeashing(CallbackInfo ci) {
        if (this.leashHolder != null) {
            if (this.leashHolder.isPassenger() && this.leashHolder.getVehicle() instanceof EmptyCompartmentEntity) {
                this.dropLeash(true, true);
            }
        }
    }

}
