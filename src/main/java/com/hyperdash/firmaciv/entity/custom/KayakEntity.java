package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.item.FirmacivItems;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class KayakEntity extends FirmacivBoatEntity {

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(KayakEntity.class, EntityDataSerializers.INT);

    public KayakEntity(EntityType<? extends FirmacivBoatEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected final float DAMAGE_THRESHOLD = 10.0f;
    protected final float DAMAGE_RECOVERY = 1.0f;

    protected Entity getPilotPassenger() {
        if (this.isVehicle() && this.getPassengers().size() == this.getPassengerNumber()) {
            return this.getPassengers().get(0);
        } else {
            return null;
        }
    }

    @Override
    public int getPassengerNumber(){
        return PASSENGER_NUMBER;
    }

    @Override
    protected float getDamageThreshold(){
        return this.DAMAGE_THRESHOLD;
    }

    @Override
    protected float getDamageRecovery(){
        return this.DAMAGE_RECOVERY;
    }

    public final int PASSENGER_NUMBER = 1;

    @Override
    protected void controlBoat() {
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {

            float f = 0.0f;
            float paddleMultiplier = 1.0f;
            if(player.isHolding(FirmacivItems.KAYAK_PADDLE.get())){
                paddleMultiplier = 2.0f;
            }

            if (this.getControllingCompartment().getInputLeft()) {
                --this.deltaRotation;
            }

            if (this.getControllingCompartment().getInputRight()) {
                ++this.deltaRotation;
            }

            if (this.getControllingCompartment().getInputRight()
                    != this.getControllingCompartment().getInputLeft() && !this.getControllingCompartment().getInputUp() && !this.getControllingCompartment().getInputDown()) {
                f += 0.0025F * paddleMultiplier;
            }

            this.setYRot(this.getYRot() + this.deltaRotation);

            if (this.getControllingCompartment().getInputUp()) {
                f += 0.0275F * paddleMultiplier;
            }

            if (this.getControllingCompartment().getInputDown()) {
                f -= 0.0125F * paddleMultiplier;
            }

            this.setDeltaMovement(this.getDeltaMovement().add(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f, 0.0D, Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f));
            this.setPaddleState(this.getControllingCompartment().getInputRight()
                    && !this.getControllingCompartment().getInputLeft() || this.getControllingCompartment().getInputUp(), this.getControllingCompartment().getInputLeft() && !this.getControllingCompartment().getInputRight()
                    || this.getControllingCompartment().getInputUp());
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

    public ResourceLocation getTextureLocation(){
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/kayak.png");
    }


}
