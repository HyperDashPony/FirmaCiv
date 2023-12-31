package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehiclePartEntity;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.util.BoatVariant;
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
    public int[][] getCompartmentRotationsArray() {
        return new int[0][];
    }

    @Override
    public int[] getCanAddOnlyBlocks() {
        return new int[0];
    }

    @Override
    public int[] getCleats() {
        return CLEATS;
    }

    @Override
    public int[] getSwitches() {
        return new int[0];
    }

    @Override
    public int[] getColliders() {
        return new int[0];
    }

    @Override
    public int getCompartmentRotation(int i) {
        return 0;
    }

    @Override
    protected float getPaddleMultiplier() {
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
        return paddleMultiplier;
    }

    @Override
    protected float getMomentumSubtractor(){
        return 0.010f;
    }

    @Nullable
    @Override
    public Entity getPilotVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getMaxPassengers()) {
            return this.getPassengers().get(1);
        }

        return null;
    }

    protected Vec3 positionRiderByIndex(int index){
        float localX = 0.0F;
        float localZ = 0.0F;
        float localY = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()));
        switch (index) {
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
        return new Vec3(localX, localY, localZ);
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