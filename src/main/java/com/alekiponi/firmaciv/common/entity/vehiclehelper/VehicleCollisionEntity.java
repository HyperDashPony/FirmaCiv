package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.SloopEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VehicleCollisionEntity extends Entity {

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
        Player nearestPlayer = this.level().getNearestPlayer(this, 32*16);
        if(nearestPlayer != null){
            Vec3 newPos = nearestPlayer.getPosition(0).multiply(1, 0, 1);
            this.setPos(newPos);
        }
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
