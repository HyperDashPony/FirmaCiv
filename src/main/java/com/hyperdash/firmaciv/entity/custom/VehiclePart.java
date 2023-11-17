package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.CompartmentEntity.EmptyCompartmentEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.entity.PartEntity;

public class VehiclePart extends PartEntity<FirmacivBoatEntity> {
    public VehiclePart(FirmacivBoatEntity parent) {
        super(parent);
    }

    public boolean isPushable() {
        return false;
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getPassengers().isEmpty()){

            EmptyCompartmentEntity newCompartment = FirmacivEntities.EMPTY_COMPARTMENT_ENTITY.get().create(this.level());
            this.level().addFreshEntity(newCompartment);
            newCompartment.startRiding(this);
        }
        if(!this.isPassenger()){
            this.remove(RemovalReason.DISCARDED);
        }


    }

    @Override
    protected void defineSynchedData() {

    }

    public boolean canCollideWith(Entity pEntity) {
        return false;
    }



    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
}
