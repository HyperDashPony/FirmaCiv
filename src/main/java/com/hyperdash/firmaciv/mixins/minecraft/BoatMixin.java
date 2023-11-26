package com.hyperdash.firmaciv.mixins.minecraft;

import com.hyperdash.firmaciv.events.config.FirmacivConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Boat.class)
public class BoatMixin extends Entity implements net.minecraftforge.common.extensions.IForgeBoat {

    public BoatMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected boolean canAddPassenger(Entity pPassenger) {
        if (FirmacivConfig.SERVER.disableVanillaBoatFunctionality.get()) {
            return false;
        } else {
            return this.getPassengers().size() < this.getMaxPassengers() && !this.canBoatInFluid(this.getEyeInFluidType());
        }
    }


    @Shadow
    protected void defineSynchedData() {

    }

    @Shadow
    protected int getMaxPassengers() {
        return 2;
    }

    @Shadow
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Shadow
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
}
