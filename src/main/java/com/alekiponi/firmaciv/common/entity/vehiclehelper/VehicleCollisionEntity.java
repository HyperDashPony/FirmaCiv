package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

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
        /*
        Player nearestPlayer = this.level().getNearestPlayer(this, 32*16);
        if(nearestPlayer != null){
            Vec3 newPos = nearestPlayer.getPosition(0).multiply(1, 0, 1);
            this.setPos(newPos);
        }*/

        final List<Entity> list = this.level()
                .getEntities(this, this.getBoundingBox().inflate(0.1, -0.01, 0.1), EntitySelector.pushableBy(this));

        if (!list.isEmpty()) {
            for (final Entity entity : list) {
                if(entity instanceof Player player){
                    if(player.getDeltaMovement().y() > 0){
                        Vec3 newPlayerPos = player.getPosition(0).multiply(1,0,1);
                        newPlayerPos = newPlayerPos.add(0,this.getY() + this.getBoundingBox().getYsize()+0.05,0);
                        newPlayerPos = newPlayerPos.add((this.getX() - newPlayerPos.x())*0.2,0,  (this.getZ() - newPlayerPos.z())*0.2);
                        player.setPos(newPlayerPos);
                    }

                }
            }
        }

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
    public void playerTouch(Player pPlayer) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
}
