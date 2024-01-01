package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import com.alekiponi.firmaciv.util.FirmacivHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class VehicleCollisionEntity extends AbstractInvisibleHelper {

    public VehicleCollisionEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected static final EntityDataAccessor<Integer> DATA_ID_PLAYER_UUID = SynchedEntityData.defineId(
            VehicleCollisionEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {

        FirmacivHelper.tickHopPlayersOnboard(this);

        if(!this.isPassenger()){
            this.kill();
        }

        super.tick();
    }

    @Override
    public boolean canCollideWith(final Entity other) {
        return canVehicleCollide(this, other);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    public static boolean canVehicleCollide(final Entity vehicle, final Entity entity) {
        if(entity instanceof AbstractFirmacivBoatEntity || entity instanceof AbstractCompartmentEntity){
            return false;
        }

        return (entity.canBeCollidedWith() || entity.isPushable()) && !vehicle.isPassengerOfSameVehicle(entity);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
}
