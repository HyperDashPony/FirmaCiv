package com.alekiponi.firmaciv.mixins.tfc;

import net.dries007.tfc.common.entities.misc.TFCBoat;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TFCBoat.class)
public class TFCBoatMixin extends Boat {

    public TFCBoatMixin(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        return super.interact(player, hand);
    }


}
