package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import com.hyperdash.firmaciv.entity.FirmacivEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class VehiclePartEntity extends Entity {

    public VehiclePartEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        emptyTicks = 0;
        selfDestructTicks = 5;
    }

    private int emptyTicks = 0;
    private int selfDestructTicks = 5;
    @Override
    public void tick() {

        if(this.getPassengers().isEmpty()){
            emptyTicks++;
            if(emptyTicks > 2){
                this.ejectPassengers();
                EmptyCompartmentEntity newCompartment = FirmacivEntities.EMPTY_COMPARTMENT_ENTITY.get().create(this.level());
                newCompartment.startRiding(this);
                this.level().addFreshEntity(newCompartment);
            }
        } else {
            emptyTicks = 0;
        }


        if(!this.isPassenger()) {
            selfDestructTicks--;
            if(selfDestructTicks == 0){
                this.ejectPassengers();
                this.remove(RemovalReason.DISCARDED);
            }

        }


        super.tick();

    }
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
