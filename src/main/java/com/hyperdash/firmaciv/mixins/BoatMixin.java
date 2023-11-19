package com.hyperdash.firmaciv.mixins;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Boat.class)
public class BoatMixin extends Entity {

    @Inject(method = "canAddPassenger", at = @At("HEAD"))
    public void injectPassengerAddFailure(Entity pPassenger, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(false);
    }

    public BoatMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    protected void defineSynchedData() {

    }

    @Shadow
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Shadow
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
}
