package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class VehicleSwitchEntity extends AbstractInvisibleHelper {

    public VehicleSwitchEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /*
    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        if(this.get)
    }*/


    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
}
