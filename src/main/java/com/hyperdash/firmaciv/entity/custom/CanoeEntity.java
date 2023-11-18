package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.Firmaciv;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CanoeEntity extends FirmacivBoatEntity {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(CanoeEntity.class, EntityDataSerializers.INT);

    public final int PASSENGER_NUMBER = 2;

    public CanoeEntity(final EntityType<? extends FirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);

        final String name = entityType.toString().split("canoe.")[1];

        this.entityData.define(DATA_ID_TYPE, BoatVariant.byName(name).ordinal());

//        EmptyCompartmentEntity newCompartment = FirmacivEntities.EMPTY_COMPARTMENT_ENTITY.get().create(level);
//        addPassenger(newCompartment);
    }

    public static ModelLayerLocation createCanoeModelName(final BoatVariant boatVariant) {
        return new ModelLayerLocation(new ResourceLocation(Firmaciv.MOD_ID, "watercraft/dugout_canoe/" + boatVariant.getName()), "main");
    }

    public void setType(final BoatVariant boatVariant) {
        this.entityData.set(DATA_ID_TYPE, boatVariant.ordinal());
    }

    public BoatVariant getVariant() {
        return BoatVariant.byId(this.entityData.get(DATA_ID_TYPE));
    }


    /*
    protected void controlBoat() {
        if (this.isVehicle() && this.getPassengers().get(0) instanceof EmptyCompartmentEntity) {
            if(((Player)this.getPassengers().get(0)).isHolding(FirmacivItems.CANOE_PADDLE.get())){
                if(this.getPassengers().size() == 2 && this.getPassengers().get(1) instanceof Player && ((Player)this.getPassengers().get(1)).isHolding(FirmacivItems.CANOE_PADDLE.get())){
                    float f = 0.0F;
                    if (this.inputLeft) {
                        --this.deltaRotation;
                    }

                    if (this.inputRight) {
                        ++this.deltaRotation;
                    }

                    if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
                        f += 0.004F;
                    }

                    this.setYRot(this.getYRot() + this.deltaRotation);
                    if (this.inputUp) {
                        f += 0.06F;
                    }

                    if (this.inputDown) {
                        f -= 0.03F;
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
                        f += 0.004F;
                    }

                    this.setYRot(this.getYRot() + this.deltaRotation);
                    if (this.inputUp) {
                        f += 0.05F;
                    }

                    if (this.inputDown) {
                        f -= 0.02F;
                    }

                    this.setDeltaMovement(this.getDeltaMovement().add(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f, 0.0D, Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f));
                    this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
                }




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
*/

    @Override
    public Item getDropItem() {
        return getVariant().getLumber().get();
    }

    /*
    protected boolean canAddPassenger(Entity pPassenger) {
        if(this.getPassengers().size() == 1 && !(pPassenger instanceof Player)){
            return false;
        }
        return this.getPassengers().size() < PASSENGER_NUMBER && !this.isEyeInFluid(FluidTags.WATER);
    }*/

    @Override
    public ItemStack getPickResult() {
        return null;
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/" + getVariant().getName() + ".png");
    }
}