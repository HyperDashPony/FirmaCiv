package com.hyperdash.firmaciv.mixins;

import com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities.EmptyCompartmentEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.minecraftforge.common.extensions.IForgeLivingEntity  {


    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        if(this.isPassenger() && this.getVehicle() instanceof EmptyCompartmentEntity emptyCompartmentEntity && pPlayer.isSecondaryUseActive()){
            this.stopRiding();
        }
        return InteractionResult.PASS;
    }

    @Shadow
    public void stopRiding() {
    }


}
