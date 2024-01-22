package com.alekiponi.firmaciv.mixins.minecraft;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeadItem.class)
public abstract class LeadItemMixin {


    //TODO clean this up and dont cancel the original
    @Inject(method = "bindPlayerMobs", at = @At("HEAD"), cancellable = true)
    private static void injectBindPlayerCleat(Player pPlayer, Level pLevel, BlockPos pPos, CallbackInfoReturnable<InteractionResult> cir){
        LeashFenceKnotEntity leashfenceknotentity = null;
        boolean flag = false;
        double d0 = 7.0D;
        int i = pPos.getX();
        int j = pPos.getY();
        int k = pPos.getZ();

        for(VehicleCleatEntity cleat : pLevel.getEntitiesOfClass(VehicleCleatEntity.class, new AABB((double)i - 7.0D, (double)j - 7.0D, (double)k - 7.0D, (double)i + 7.0D, (double)j + 7.0D, (double)k + 7.0D))) {
            if (cleat.getLeashHolder() == pPlayer) {
                if (leashfenceknotentity == null) {
                    leashfenceknotentity = LeashFenceKnotEntity.getOrCreateKnot(pLevel, pPos);
                    leashfenceknotentity.playPlacementSound();
                }

                cleat.setLeashedTo(leashfenceknotentity, true);
                flag = true;
            }
        }

        for(Mob mob : pLevel.getEntitiesOfClass(Mob.class, new AABB((double)i - 7.0D, (double)j - 7.0D, (double)k - 7.0D, (double)i + 7.0D, (double)j + 7.0D, (double)k + 7.0D))) {
            if (mob.getLeashHolder() == pPlayer) {
                if (leashfenceknotentity == null) {
                    leashfenceknotentity = LeashFenceKnotEntity.getOrCreateKnot(pLevel, pPos);
                    leashfenceknotentity.playPlacementSound();
                }

                mob.setLeashedTo(leashfenceknotentity, true);
                flag = true;
            }
        }

        if (flag) {
            pLevel.gameEvent(GameEvent.BLOCK_ATTACH, pPos, GameEvent.Context.of(pPlayer));
        }

        cir.setReturnValue(flag ? InteractionResult.SUCCESS : InteractionResult.PASS);

    }


}
