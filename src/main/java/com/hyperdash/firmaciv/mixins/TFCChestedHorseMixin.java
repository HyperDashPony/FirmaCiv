package com.hyperdash.firmaciv.mixins;

import com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities.EmptyCompartmentEntity;
import net.dries007.tfc.common.entities.livestock.horse.TFCChestedHorse;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TFCChestedHorse.class)
public class TFCChestedHorseMixin  extends AbstractChestedHorse {

    protected TFCChestedHorseMixin(EntityType<? extends AbstractChestedHorse> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"))
    public void injectEjectEntity(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        if(!this.level().isClientSide() && this.isPassenger() && this.getVehicle() instanceof EmptyCompartmentEntity emptyCompartmentEntity && pPlayer.isSecondaryUseActive()){
            this.stopRiding();
            cir.setReturnValue(InteractionResult.SUCCESS);
        }

    }
}
