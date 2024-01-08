package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import com.alekiponi.firmaciv.util.FirmacivHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
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
        } else if(tickCount < 10){
            this.refreshDimensions();
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
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        if(this.getRootVehicle() instanceof SloopEntity){
            return new EntityDimensions(1.5f, 0.75f, false);
        }
        if(this.getRootVehicle() instanceof CanoeEntity){
            return new EntityDimensions(1.125f, 0.625f, false);
        }
        return super.getDimensions(pPose);
    }

    protected void recalculateBoundingBox() {
        if(this.getRootVehicle() instanceof CanoeEntity canoe){
            float diameter = 1.125f;
            float bbRadius = diameter*0.5f;
            float height = 0.625f;
            Vec3 startingPoint = new Vec3(this.getX() - bbRadius, this.getY() - height*0.5f, this.getZ() - bbRadius);
            Vec3 endingPoint = new Vec3(this.getX() + bbRadius, this.getY() + height*0.5f, this.getZ() + bbRadius);
            this.setBoundingBox(new AABB(startingPoint, endingPoint));
            return;
        }
        if(this.getRootVehicle() instanceof SloopEntity){
            return;
        }
    }
}
