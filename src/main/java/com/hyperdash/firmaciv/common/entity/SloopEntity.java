package com.hyperdash.firmaciv.common.entity;

import com.hyperdash.firmaciv.common.entity.vehiclehelper.VehiclePartEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SloopEntity extends FirmacivBoatEntity{


    public final int PASSENGER_NUMBER = 13;

    public final int[] CLEATS = {};

    public final int[] CAN_ADD_ONLY_BLOCKS = {1,2,3,4,5,6};

    public SloopEntity(EntityType<? extends FirmacivBoatEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public int getPassengerNumber() {
        return this.PASSENGER_NUMBER;
    }

    @Override
    public int[] getCleats(){return this.CLEATS;}

    @Override
    public int[] getCanAddOnlyBlocks(){return CAN_ADD_ONLY_BLOCKS;}


    @Override
    public AABB getBoundingBoxForCulling() {
        Vec3 startingPoint = new Vec3(this.getX()-7, this.getY()-7, this.getZ()-7);
        Vec3 endingPoint = new Vec3(this.getX()+7, this.getY()+7, this.getZ()+7);
      return new AABB(startingPoint, endingPoint);
    }


    @Override
    protected void positionRider(final Entity passenger, final Entity.MoveFunction moveFunction) {
        if (this.hasPassenger(passenger)) {
            float localX = 0.0F;
            float localZ = 0.0F;
            float localY = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
            if (this.getPassengers().size() > 1) {
                switch (this.getPassengers().indexOf(passenger)) {
                    case 0 -> {
                        // front / pilot seat
                        localZ = 1.1f;
                        localX = 0.0f;
                        localY += 5f;
                    }
                    case 1 -> {
                        // hold lower left
                        localZ = -0.35f;
                        localX = -0.4f;
                        localY += -0.1f;
                    }
                    case 2 -> {
                        // hold lower right
                        localZ = 0.35f;
                        localX = -0.4f;
                        localY += -0.1f;
                    }
                    case 3 -> {
                        // hold middle left
                        localZ = -0.35f;
                        localX = 0.475f;
                        localY += -0.1f;
                    }
                    case 4 -> {
                        //hold middle right
                        localZ = 0.35f;
                        localX = 0.475f;
                        localY += -0.1f;
                    }
                    case 5 ->{
                        //hold upper left
                        localZ = -0.35f;
                        localX = 1.35f;
                        localY += -0.1f;
                    }
                    case 6 -> {
                        //hold upper right
                        localZ = 0.35f;
                        localX = 1.35f;
                        localY += -0.1f;
                    }
                    case 7 -> {
                        localX = -1.3f;
                        localZ = -1.4f;
                        localY += 0.6f;
                    }
                    case 8 -> {
                        localX = -0.5f;
                        localZ = -1.4f;
                        localY += 0.6f;
                    }
                    case 9 -> {
                        localX = 0.3f;
                        localZ = -1.4f;
                        localY += 0.6f;
                    }
                    case 10 -> {
                        localX = 2.0f;
                        localZ = -5f;
                        localY += 1f;
                    }
                    case 11 ->{
                        localX = 3.0f;
                        localZ = -5f;
                        localY += 1f;
                    }
                    case 12 ->{
                        localX = 4.0f;
                        localZ = -5f;
                        localY += 1f;
                    }
                }
            }
            final Vec3 vec3 = this.positionVehiclePartEntityLocally(localX, localY, localZ);
            moveFunction.accept(passenger, this.getX() + vec3.x, this.getY() + (double) localY, this.getZ() + vec3.z);
            passenger.setPos(this.getX() + vec3.x, this.getY() + (double) localY, this.getZ() + vec3.z);
            if (!this.level().isClientSide() && passenger instanceof VehiclePartEntity) {
                passenger.setYRot(this.getYRot());
            } else if (!(passenger instanceof VehiclePartEntity)) {
                super.positionRider(passenger, moveFunction);
            }
        }
    }
}
