package com.alekiponi.firmaciv.mixins.tfc;

import com.alekiponi.firmaciv.events.config.FirmacivConfig;
import net.dries007.tfc.common.entities.misc.TFCBoat;
import net.dries007.tfc.common.entities.misc.TFCChestBoat;
import net.dries007.tfc.common.items.ChestBlockItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;

@Mixin(TFCBoat.class)
public class TFCBoatMixin extends Boat {

    @Mutable
    @Final
    @Shadow
    private final Supplier<EntityType<TFCChestBoat>> boatChest;

    public TFCBoatMixin(EntityType<? extends Boat> pEntityType, Level pLevel, Supplier<EntityType<TFCChestBoat>> boatChest, Supplier<EntityType<TFCChestBoat>> boatChest1) {
        super(pEntityType, pLevel);
        this.boatChest = boatChest1;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if(FirmacivConfig.SERVER.disableVanillaBoatFunctionality.get()){
            return InteractionResult.PASS;
        }
        ItemStack item = player.getItemInHand(hand);
        if (item.getItem() instanceof ChestBlockItem) {
            TFCChestBoat boat = (TFCChestBoat)((EntityType)this.boatChest.get()).create(player.level());
            if (boat != null) {
                boat.setPos(this.position());
                boat.setYRot(this.getYRot());
                boat.setXRot(this.getXRot());
                boat.setDeltaMovement(this.getDeltaMovement());
                if (this.hasCustomName()) {
                    boat.setCustomName(this.getCustomName());
                }

                boat.setChestItem(item.split(1));
                this.level().addFreshEntity(boat);
                this.discard();
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }

        return super.interact(player, hand);
    }


}
