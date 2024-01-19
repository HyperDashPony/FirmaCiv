package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehicle.AbstractVehicleUnderConstruction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ConstructionEntity extends Entity {

    public ConstructionEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick(){
        super.tick();
        if (!this.isPassenger()) {
            this.kill();
        }
    }

    public float getSpin(float pPartialTicks) {
        return (this.tickCount + pPartialTicks) / 20.0F;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        if(this.getRootVehicle() instanceof AbstractVehicleUnderConstruction constructionEntity){
            constructionEntity.interactFromConstructionEntity(player, hand);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

}
