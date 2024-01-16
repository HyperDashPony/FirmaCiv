package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.CompartmentType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AnvilCompartmentEntity extends AbstractCompartmentEntity {

    public AnvilCompartmentEntity(EntityType<? extends AnvilCompartmentEntity> entityType, Level level) {
        super(entityType, level);
    }

    public AnvilCompartmentEntity(final CompartmentType<? extends AnvilCompartmentEntity> entityType, final Level level,
            final ItemStack itemStack) {
        this(entityType, level);
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            this.setDisplayBlockState(blockItem.getBlock().defaultBlockState());
        }
    }

    @Override
    protected SoundEvent getHurtSound(final DamageSource damageSource) {
        return SoundEvents.METAL_HIT;
    }
}