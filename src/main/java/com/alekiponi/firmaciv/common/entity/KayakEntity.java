package com.alekiponi.firmaciv.common.entity;

import com.alekiponi.firmaciv.Firmaciv;
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

import javax.annotation.Nullable;

public class KayakEntity extends AbstractFirmacivBoatEntity {

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(KayakEntity.class,
            EntityDataSerializers.INT);
    public final int PASSENGER_NUMBER = 1;
    protected final float DAMAGE_THRESHOLD = 10.0f;
    protected final float DAMAGE_RECOVERY = 1.0f;

    protected final float PASSENGER_SIZE_LIMIT = 0.6F;

    public KayakEntity(final EntityType<? extends AbstractFirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);
    }

    @Override
    public float getPassengerSizeLimit() {
        return this.PASSENGER_SIZE_LIMIT;
    }

    @Nullable
    @Override
    public Entity getPilotVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getMaxPassengers()) {
            return this.getPassengers().get(0);
        }

        return null;
    }

    @Override
    public int getMaxPassengers() {
        return this.PASSENGER_NUMBER;
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
    protected void tickControlBoat() {
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
            if (getControllingCompartment() != null) {
                boolean inputUp = this.getControllingCompartment().getInputUp();
                boolean inputDown = this.getControllingCompartment().getInputDown();
                boolean inputLeft = this.getControllingCompartment().getInputLeft();
                boolean inputRight = this.getControllingCompartment().getInputRight();
                float f = 0.0f;
                float paddleMultiplier = 1.0f;
                if (player.isHolding(FirmacivItems.KAYAK_PADDLE.get())) {
                    paddleMultiplier = 2.0f;
                }

                if (inputLeft) {
                    --this.deltaRotation;
                }

                if (inputRight) {
                    ++this.deltaRotation;
                }

                if (inputRight != inputLeft && !inputUp && !inputDown) {
                    f += 0.0025F * paddleMultiplier;
                }

                this.setYRot(this.getYRot() + this.deltaRotation);

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

    @Override
    public Item getDropItem() {
        return FirmacivItems.KAYAK.get();
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(FirmacivItems.KAYAK.get());
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/kayak.png");
    }
}