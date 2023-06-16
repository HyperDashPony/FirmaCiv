package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.item.FirmacivItems;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class KayakEntity extends FirmacivBoatEntity {

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(KayakEntity.class, EntityDataSerializers.INT);

    public KayakEntity(EntityType<? extends FirmacivBoatEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final int PASSENGER_NUMBER = 1;

    protected void controlBoat() {
        if (this.isVehicle() && this.getPassengers().get(0) instanceof Player) {
            if(((Player) this.getPassengers().get(0)).isHolding(FirmacivItems.KAYAK_PADDLE.get())){
                float f = 0.0F;
                if (this.inputLeft) {
                    --this.deltaRotation;
                }

                if (this.inputRight) {
                    ++this.deltaRotation;
                }

                if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
                    f += 0.005F;
                }

                this.setYRot(this.getYRot() + this.deltaRotation);
                if (this.inputUp) {
                    f += 0.055F;
                }

                if (this.inputDown) {
                    f -= 0.025F;
                }

                this.setDeltaMovement(this.getDeltaMovement().add(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f, 0.0D, Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f));
                this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
            } else {
                float f = 0.0F;
                if (this.inputLeft) {
                    --this.deltaRotation;
                }

                if (this.inputRight) {
                    ++this.deltaRotation;
                }

                if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
                    f += 0.002F;
                }

                this.setYRot(this.getYRot() + this.deltaRotation);
                if (this.inputUp) {
                    f += 0.02F;
                }

                if (this.inputDown) {
                    f -= 0.01F;
                }

                this.setDeltaMovement(this.getDeltaMovement().add(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f, 0.0D, Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f));
                this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
            }
        }
    }

    @Override
    public Item getDropItem() {
        return FirmacivItems.KAYAK.get();
    }

    protected boolean canAddPassenger(Entity pPassenger) {
        return this.getPassengers().size() < PASSENGER_NUMBER && !this.isEyeInFluid(FluidTags.WATER) && pPassenger instanceof Player;
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(FirmacivItems.KAYAK.get());
    }

    public ResourceLocation getTextureLocation(){
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/kayak.png");
    }


}
