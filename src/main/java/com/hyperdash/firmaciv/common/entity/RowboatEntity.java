package com.hyperdash.firmaciv.common.entity;


import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.common.entity.vehiclehelper.VehiclePartEntity;
import com.hyperdash.firmaciv.common.items.FirmacivItems;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class RowboatEntity extends FirmacivBoatEntity {

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(RowboatEntity.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<ItemStack> DATA_OARS = SynchedEntityData.defineId(RowboatEntity.class,
            EntityDataSerializers.ITEM_STACK);
    public final int PASSENGER_NUMBER = 3;

    public RowboatEntity(final EntityType<? extends FirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);

        final String name = entityType.toString().split("rowboat.")[1];

        this.entityData.define(DATA_ID_TYPE, BoatVariant.byName(name).ordinal());
    }

    @Override
    public int getPassengerNumber() {
        return PASSENGER_NUMBER;
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
                        localX = 1.0f;
                        localZ = 0.0f;
                    }
                    case 1 -> {
                        localX = -0.95f;
                        localZ = 0.4f;
                    }
                    case 2 -> {
                        localX = -0.95f;
                        localZ = -0.4f;
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

    @Override
    public void remove(final RemovalReason removalReason) {
        if (!this.level().isClientSide && removalReason.shouldDestroy()) {
            this.playSound(SoundEvents.WOOD_BREAK, 1.0F, this.level().getRandom().nextFloat() * 0.1F + 0.9F);
            spawnAtLocation(this.getOars().split(1));
            spawnAtLocation(this.getOars().split(1));
        }

        super.remove(removalReason);
    }

    @Override
    protected void controlBoat() {
        if (this.isVehicle()) {
            if (getControllingCompartment() != null) {
                boolean inputUp = this.getControllingCompartment().getInputUp();
                boolean inputDown = this.getControllingCompartment().getInputDown();
                boolean inputLeft = this.getControllingCompartment().getInputLeft();
                boolean inputRight = this.getControllingCompartment().getInputRight();
                if (getControllingPassenger() instanceof LocalPlayer) {
                    Minecraft mc = Minecraft.getInstance();
                    if (mc.options.getCameraType() != CameraType.THIRD_PERSON_FRONT) {
                        inputDown = this.getControllingCompartment().getInputUp();
                        inputUp = this.getControllingCompartment().getInputDown();
                        inputLeft = this.getControllingCompartment().getInputRight();
                        inputRight = this.getControllingCompartment().getInputLeft();
                    }
                }
                float paddleMultiplier = 1.0f;
                if (this.getOars().getCount() > 0) {
                    paddleMultiplier = 1.6f;
                    if (this.getOars().getCount() == 1) {
                        if (this.getDeltaMovement().length() > 0.1f) {
                            ++this.deltaRotation;
                        }

                    }
                }

                float f = 0.0F;
                if (inputLeft) {
                    --this.deltaRotation;
                }

                if (inputRight) {
                    ++this.deltaRotation;
                }

                if (inputRight != inputLeft && !inputUp && !inputDown) {
                    f += 0.005F;
                }

                this.setYRot(this.getYRot() + this.deltaRotation);
                if (inputUp) {
                    f += 0.055F;
                }

                if (inputDown) {
                    f -= 0.025F;
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

    public ItemStack getOars() {
        return this.entityData.get(DATA_OARS);
    }

    public void setOars(final ItemStack itemStack) {
        this.entityData.set(DATA_OARS, itemStack.copy());
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        final ItemStack item = player.getItemInHand(hand);

        if (item.is(FirmacivItems.OAR.get()) && this.getOars().getCount() < 2) {
            this.addOar();
            item.split(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public void addOar() {
        ItemStack newItemStack = this.getOars();
        int numberOfOars = newItemStack.getCount();
        newItemStack = new ItemStack(FirmacivItems.OAR.get(), numberOfOars + 1);
        this.setOars(newItemStack);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_OARS, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setOars(ItemStack.of(compoundTag.getCompound("dataOars")));
    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.put("dataOars", this.getOars().save(new CompoundTag()));
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/rowboat/" + getVariant().getName() + ".png");
    }
}