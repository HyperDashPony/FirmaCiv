package com.alekiponi.firmaciv.mixins.minecraft;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.EmptyCompartmentEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {


    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    //TODO double-check if this can be done with an event
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void injectStopSuffocationInBoats(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        if (pSource.is(DamageTypes.IN_WALL)) {
            if (getVehicle() instanceof EmptyCompartmentEntity) {
                cir.setReturnValue(false);
            }
        }
    }
}
