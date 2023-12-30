package com.alekiponi.firmaciv.common.entity.vehicle;


import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehiclePartEntity;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.util.BoatVariant;
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

public class RowboatEntity extends AbstractFirmacivBoatEntity {

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(RowboatEntity.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<ItemStack> DATA_OARS = SynchedEntityData.defineId(RowboatEntity.class,
            EntityDataSerializers.ITEM_STACK);
    public final int PASSENGER_NUMBER = 6;

    public final int[] CLEATS = {5};

    public final int[][] COMPARTMENT_ROTATIONS = {{0, 180}};

    public final int[] CAN_ADD_ONLY_BLOCKS = {2, 1};
    protected final float PASSENGER_SIZE_LIMIT = 1.4F;

    protected final float DAMAGE_THRESHOLD = 80.0f;
    protected final float DAMAGE_RECOVERY = 2.0f;


    public RowboatEntity(final EntityType<? extends AbstractFirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);

        final String name = entityType.toString().split("rowboat.")[1];

        this.entityData.define(DATA_ID_TYPE, BoatVariant.byName(name).ordinal());
    }

    @Override
    public float getPassengerSizeLimit() {
        return PASSENGER_SIZE_LIMIT;
    }

    @Override
    public int getMaxPassengers() {
        return this.PASSENGER_NUMBER;
    }

    @Override
    public int[] getCleats() {
        return this.CLEATS;
    }

    @Override
    public int[] getColliders() {
        return new int[0];
    }

    @Override
    public int[] getCanAddOnlyBlocks() {
        return CAN_ADD_ONLY_BLOCKS;
    }

    protected Vec3 positionRiderByIndex(int index){
        float localX = 0.0F;
        float localZ = 0.0F;
        float localY = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()));
        switch (index) {
            case 0 -> {
                // front / pilot seat
                localX = 1.1f;
                localZ = 0.0f;
                localY += 0.2f;
            }
            case 1 -> {
                // back right seat
                localX = -0.95f;
                localZ = 0.375f;
                localY += 0.1f;
            }
            case 2 -> {
                // back left seat
                localX = -0.95f;
                localZ = -0.375f;
                localY += 0.1f;
            }
            case 3 -> {
                // middle right seat
                localX = -0.1f;
                localZ = 0.375f;
                localY += 0.1f;
            }
            case 4 -> {
                // middle left seat
                localX = -0.1f;
                localZ = -0.375f;
                localY += 0.1f;
            }
            case 5 -> {
                // cleat
                localX = 1.7f;
                localZ = 0f;
                localY += 0.6f;
            }
        }
        return new Vec3(localX, localY, localZ);
    }

    @Override
    protected float getDamageThreshold() {
        return DAMAGE_THRESHOLD;
    }

    @Override
    protected float getDamageRecovery() {
        return DAMAGE_RECOVERY;
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
    protected float getPaddleMultiplier() {
        float paddleMultiplier = 1.0f;
        if (this.getOars().getCount() > 0) {
            paddleMultiplier = 1.6f;
        }
        return paddleMultiplier;
    }

    @Override
    protected float getMomentumSubtractor() {
        return 0.005f;
    }


    @Override
    public int getCompartmentRotation(int i) {
        return COMPARTMENT_ROTATIONS[i][0];
    }

    @Override
    public int[][] getCompartmentRotationsArray() {
        return COMPARTMENT_ROTATIONS;
    }

    @Override
    public Item getDropItem() {
        return getVariant().getLumber().get();
    }

    @Nullable
    @Override
    public Entity getPilotVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getMaxPassengers()) {
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