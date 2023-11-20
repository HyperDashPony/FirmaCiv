package com.hyperdash.firmaciv.entity.custom;


import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities.EmptyCompartmentEntity;
import com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities.VehiclePartEntity;
import com.hyperdash.firmaciv.item.FirmacivItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class RowboatEntity extends FirmacivBoatEntity {

    public final int PASSENGER_NUMBER = 3;
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(RowboatEntity.class, EntityDataSerializers.INT);

    @Override
    public int getPassengerNumber() {
        return PASSENGER_NUMBER;
    }

    public RowboatEntity(final EntityType<? extends FirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);

        final String name = entityType.toString().split("rowboat.")[1];

        this.entityData.define(DATA_ID_TYPE, BoatVariant.byName(name).ordinal());
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
                        localX = 0.7f;
                        localZ = 0.0f;
                    }
                    case 1 -> {
                        localX = -1.0f;
                        localZ = 0.7f;
                    }
                    case 3 -> {
                        localX = -1.0f;
                        localZ = -0.7f;
                    }
                }
            }
            final Vec3 vec3 = this.positionVehicleEntityLocally(localX, localY, localZ);
            moveFunction.accept(passenger, this.getX() + vec3.x, this.getY() + (double) localY, this.getZ() + vec3.z);
            passenger.setPos(this.getX() + vec3.x, this.getY() + (double) localY, this.getZ() + vec3.z);
            if (!this.level().isClientSide() && passenger instanceof VehiclePartEntity) {
                passenger.setYRot(this.getYRot());
            } else if (!(passenger instanceof VehiclePartEntity)){
                super.positionRider(passenger, moveFunction);
            }
        }
    }


    @Override
    public Item getDropItem() {
        return getVariant().getLumber().get();
    }

    @Nullable
    @Override
    public Entity getPilotVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getPassengerNumber()) {
            return this.getPassengers().get(0);
        }
        return null;
    }

    public void setType(final BoatVariant boatVariant) {
        this.entityData.set(DATA_ID_TYPE, boatVariant.ordinal());
    }

    public BoatVariant getVariant() {
        return BoatVariant.byId(this.entityData.get(DATA_ID_TYPE));
    }


    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/rowboat/" + getVariant().getName() + ".png");
    }

}
