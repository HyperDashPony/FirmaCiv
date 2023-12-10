package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class AnvilCompartmentEntity extends CompartmentEntity{

    public AnvilCompartmentEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected SoundEvent getHurtSound(final DamageSource damageSource) {
        return SoundEvents.METAL_HIT;
    }

}
