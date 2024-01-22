package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.util.BoatVariant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;

public class CanoeEntity extends AbstractFirmacivBoatEntity {
    private static final EntityDataAccessor<Integer> DATA_ID_LENGTH = SynchedEntityData.defineId(CanoeEntity.class,
            EntityDataSerializers.INT);
    public final int PASSENGER_NUMBER = 5;
    public final int[] CLEATS = {2};
    protected final float DAMAGE_THRESHOLD = 80.0f;
    protected final float DAMAGE_RECOVERY = 2.0f;
    protected final float PASSENGER_SIZE_LIMIT = 0.9F;

    public final int[] COLLIDERS = {3, 4};

    public CanoeEntity(final EntityType<? extends AbstractFirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(FirmacivItems.CANOE_PADDLE.get()) && player.isCreative() && player.isSecondaryUseActive()) {
            if(this.getLength() == 5){
                this.setLength(3);
            } else {
                this.setLength(this.getLength() +1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.interact(player, hand);
    }

    @Override
    public BoatVariant getVariant() {
        return getVariant("canoe");
    }

    @Override
    public int[] getWindlassIndices() {
        return new int[0];
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
    public int[] getCanAddOnlyBlocksIndices() {
        return new int[0];
    }

    @Override
    public int[] getCleatIndices() {
        return CLEATS;
    }

    @Override
    public int[] getSailSwitchIndices() {
        return new int[0];
    }

    @Override
    public int[] getMastIndices() {
        return new int[0];
    }

    @Override
    public int[] getCanAddCannonsIndices() {
        return new int[0];
    }

    @Override
    public int[] getColliderIndices() {
        return COLLIDERS;
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
                localZ = 0.0f;
                localY += 0.0625f;
                if(this.getLength() == 3){
                    localX = 0.3f;
                } else if(this.getLength() == 4){
                    localX = 0.4f;
                } else if(this.getLength() == 5){
                    localX = 1.0f;
                }
            }
            case 1 -> {
                //rear seat
                localZ = 0.0f;
                localY += 0.0625f;
                if(this.getLength() == 3){
                    localX = -0.7f;
                } else if(this.getLength() == 4){
                    localX = -0.7f - 0.1f;
                } else if(this.getLength() == 5){
                    localX = -0.7f - 0.6f;
                }
            }
            //cleat
            case 2 -> {
                localZ = 0.0f;
                localY += 0.45f;
                if(this.getLength() == 3){
                    localX = 1.0f;
                } else if(this.getLength() == 4){
                    localX = 1.5f;
                } else if(this.getLength() == 5){
                    localX = 2.0f;
                }
            }
            case 3 -> {
                localZ = 0.0f;
                localY += 0.0;
                if(this.getLength() == 3){
                    localX = 0.7f;
                } else if(this.getLength() == 4){
                    localX = 1.3f;
                } else if(this.getLength() == 5){
                    localX = 1.7f;
                }
            }
            case 4 -> {
                localZ = 0.0f;
                localY += 0.0;
                if(this.getLength() == 3){
                    localX = -0.7f;
                } else if(this.getLength() == 4){
                    localX = -1.3f;
                } else if(this.getLength() == 5){
                    localX = -1.7f;
                }
            }
        }
        return new Vec3(localX, localY, localZ);
    }

    @Override
    public Item getDropItem() {
        return getVariant().getLumber().get();
    }

    public void setLength(int length){
        this.entityData.set(DATA_ID_LENGTH, Mth.clamp(length, 3, 5));
    }

    public int getLength(){
        return this.entityData.get(DATA_ID_LENGTH);
    }

    @Override
    public int getMaxPassengers() {
        return PASSENGER_NUMBER;
    }

    @Override
    public float getDamageThreshold() {
        return this.DAMAGE_THRESHOLD;
    }

    @Override
    public float getDamageRecovery() {
        return this.DAMAGE_RECOVERY;
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(this.getDropItem());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_LENGTH, 3);

    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLength(compoundTag.getInt("length"));
    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("length", this.getLength());
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(Firmaciv.MOD_ID,
                "textures/entity/watercraft/dugout_canoe/" + this.getVariant().getName() + ".png");
    }

    @Override
    public float getStepHeight(){
        return 0.0f;
    }
}

