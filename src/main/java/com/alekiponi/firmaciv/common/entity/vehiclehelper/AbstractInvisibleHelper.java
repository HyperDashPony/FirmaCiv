package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractInvisibleHelper extends Entity {

    public AbstractInvisibleHelper(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected abstract void defineSynchedData();

    @Override
    protected abstract void readAdditionalSaveData(CompoundTag pCompound);

    @Override
    protected abstract void addAdditionalSaveData(CompoundTag pCompound);
}
