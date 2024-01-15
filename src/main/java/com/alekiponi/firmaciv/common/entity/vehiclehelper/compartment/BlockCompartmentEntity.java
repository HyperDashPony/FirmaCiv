package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.CompartmentType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * This is a light class for compartments which are dumb blocks. It automatically sets the display blockstate if the
 * given {@link ItemStack} is a {@link BlockItem} as well as uses blockstate to get and play the break sound
 */
public class BlockCompartmentEntity extends AbstractCompartmentEntity {

    public BlockCompartmentEntity(final EntityType<? extends BlockCompartmentEntity> entityType, final Level level) {
        super(entityType, level);
    }

    public BlockCompartmentEntity(final CompartmentType<? extends BlockCompartmentEntity> entityType, final Level level,
            final ItemStack itemStack) {
        super(entityType, level);
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            this.setDisplayBlockState(blockItem.getBlock().defaultBlockState());
        }
    }

    @Override
    public void remove(final RemovalReason removalReason) {
        if (!this.level().isClientSide() && removalReason.shouldDestroy()) {
            final SoundEvent breakSound = this.getDisplayBlockState().getBlock()
                    .getSoundType(this.getDisplayBlockState(), this.level(), this.blockPosition(), this)
                    .getBreakSound();
            this.playSound(breakSound, 1, this.level().getRandom().nextFloat() * 0.1F + 0.9F);
        }

        super.remove(removalReason);
    }
}