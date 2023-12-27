package com.alekiponi.firmaciv.common.entity;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehiclePartEntity;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class CanoeEntity extends AbstractFirmacivBoatEntity {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(CanoeEntity.class,
            EntityDataSerializers.INT);
    public final int PASSENGER_NUMBER = 3;
    public final int[] CLEATS = {2};
    protected final float DAMAGE_THRESHOLD = 80.0f;
    protected final float DAMAGE_RECOVERY = 2.0f;
    protected final float PASSENGER_SIZE_LIMIT = 0.9F;

    public CanoeEntity(final EntityType<? extends AbstractFirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);

        final String name = entityType.toString().split("canoe.")[1];

        this.entityData.define(DATA_ID_TYPE, BoatVariant.byName(name).ordinal());
    }

    @Override
    public float getPassengerSizeLimit() {
        return PASSENGER_SIZE_LIMIT;
    }

    @Override
    public int[] getCleats() {
        return CLEATS;
    }


    @Override
    protected void tickControlBoat() {
        if (this.isVehicle()) {
            if (getControllingCompartment() != null) {
                boolean inputUp = this.getControllingCompartment().getInputUp();
                boolean inputDown = this.getControllingCompartment().getInputDown();
                boolean inputLeft = this.getControllingCompartment().getInputLeft();
                boolean inputRight = this.getControllingCompartment().getInputRight();
                float f = 0.0f;
                float paddleMultiplier = 1.0f;
                if(this.getControllingPassenger() instanceof Player player){
                    if (player.isHolding(FirmacivItems.CANOE_PADDLE.get())) {
                        paddleMultiplier = 1.6f;
                    }
                }


                int i = 0;
                for (Entity entity : this.getTruePassengers()) {
                    if (entity instanceof Player player2) {
                        if (player2.isHolding(FirmacivItems.CANOE_PADDLE.get())) {
                            i++;
                        }
                    }
                }
                if (i == 2) {
                    paddleMultiplier = 2.0f;
                }

                if (inputLeft) {
                    this.setDeltaRotation(this.getDeltaRotation()-1);
                }

                if (inputRight) {
                    this.setDeltaRotation(this.getDeltaRotation()+1);
                }

                if (inputRight != inputLeft && !inputUp && !inputDown) {
                    f += 0.0025F * paddleMultiplier;
                }

                this.setYRot(this.getYRot() + this.getDeltaRotation());

                if (inputUp) {
                    f += 0.0275F * paddleMultiplier;
                }

                if (inputDown) {
                    f -= 0.0125F * paddleMultiplier;
                }

                this.setDeltaMovement(this.getDeltaMovement()
                        .add(Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * f, 0.0D,
                                Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * f));
                this.setPaddleState(inputRight && !inputLeft || inputUp, inputLeft && !inputRight || inputUp);
            }

        }


    }

    @Nullable
    @Override
    public Entity getPilotVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getMaxPassengers()) {
            return this.getPassengers().get(1);
        }

        return null;
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
                        //forward seat
                        localX = 0.3f;
                        localZ = 0.0f;
                        localY += 0.0625f;
                    }
                    case 1 -> {
                        //rear seat
                        localX = -0.7f;
                        localZ = 0.0f;
                        localY += 0.0625f;
                    }
                    //cleat
                    case 2 -> {
                        localX = 1.0f;
                        localZ = 0.0f;
                        localY += 0.28f;
                    }
                }
            }
            final Vec3 vec3 = this.positionLocally(localX, localY, localZ);
            moveFunction.accept(passenger, this.getX() + vec3.x, this.getY() + (double) localY, this.getZ() + vec3.z);
            passenger.setPos(this.getX() + vec3.x, this.getY() + (double) localY, this.getZ() + vec3.z);
            if (!this.level().isClientSide() && passenger instanceof VehiclePartEntity) {
                passenger.setYRot(this.getYRot());
            } else if (!(passenger instanceof VehiclePartEntity)) {
                super.positionRider(passenger, moveFunction);
            }
        }
    }

    @Override
    public Item getDropItem() {
        return getVariant().getLumber().get();
    }

    public void setType(final BoatVariant boatVariant) {
        this.entityData.set(DATA_ID_TYPE, boatVariant.ordinal());
    }

    public BoatVariant getVariant() {
        return BoatVariant.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    public int getMaxPassengers() {
        return PASSENGER_NUMBER;
    }

    @Override
    protected float getDamageThreshold() {
        return this.DAMAGE_THRESHOLD;
    }

    @Override
    protected float getDamageRecovery() {
        return this.DAMAGE_RECOVERY;
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(this.getDropItem());
    }


    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/dugout_canoe/" + getVariant().getName() + ".png");
    }
}