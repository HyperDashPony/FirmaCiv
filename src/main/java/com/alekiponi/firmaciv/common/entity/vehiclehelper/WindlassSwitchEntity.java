package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WindlassSwitchEntity extends AbstractSwitchEntity {

    protected static final EntityDataAccessor<Float> DATA_ID_ANCHOR_DISTANCE = SynchedEntityData.defineId(
            WindlassSwitchEntity.class, EntityDataSerializers.FLOAT);

    protected static final EntityDataAccessor<Boolean> DATA_ID_ANCHORED = SynchedEntityData.defineId(
            WindlassSwitchEntity.class, EntityDataSerializers.BOOLEAN);
    public WindlassSwitchEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick(){
        super.tick();
        if (!this.isVehicle()){
            final AnchorEntity anchor = FirmacivEntities.ANCHOR_ENTITY.get()
                    .create(this.level());
            assert anchor != null;
            anchor.setPos(this.getX(), this.getY(), this.getZ());
            if (!anchor.startRiding(this)) {
                Firmaciv.LOGGER.error("New Anchor: {} unable to ride Windlass: {}", anchor, this);
            }
            this.level().addFreshEntity(anchor);
        }

        if(this.getFirstPassenger() instanceof AnchorEntity anchor){
            if(this.getAnchorDistance() > 0 && !this.level().getBlockState(anchor.blockPosition()).isAir()
                    && this.level().getFluidState(anchor.blockPosition()).isEmpty()){
                this.setAnchored(true);
            } else {
                this.setAnchored(false);
            }
        }

        if(this.getSwitched() && this.getAnchorDistance() < 100f && !this.getAnchored()){
            this.setAnchorDistance(this.getAnchorDistance() + 0.1f);
        } else if(this.getAnchorDistance() > 0 && !this.getSwitched()){
            this.setAnchorDistance(this.getAnchorDistance() - 0.1f);
        }
    }

    @Override
    protected void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
        if (this.hasPassenger(pPassenger)) {
            pCallback.accept(pPassenger, this.getX()+0.05, this.getY()-this.getAnchorDistance(), this.getZ()+0.05);

        }
    }

    @Override
    public Vec3 getRopeHoldPosition(float pPartialTicks) {
        return this.getPosition(pPartialTicks).add(0.1D, 0.35D, 0.1D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_ANCHOR_DISTANCE, 0f);
        this.entityData.define(DATA_ID_ANCHORED, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setAnchorDistance(pCompound.getFloat("anchorDistance"));
        this.setAnchored(pCompound.getBoolean("isAnchored"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("anchorDistance", this.getAnchorDistance());
        pCompound.putBoolean("isAnchored", this.getAnchored());
    }

    public void setAnchorDistance(float distance) {
        this.entityData.set(DATA_ID_ANCHOR_DISTANCE, Mth.clamp(distance, 0f,100f));
    }

    public float getAnchorDistance() {
        return this.entityData.get(DATA_ID_ANCHOR_DISTANCE);
    }

    public void setAnchored(boolean anchored) {
        this.entityData.set(DATA_ID_ANCHORED, anchored);
    }

    public boolean getAnchored() {
        return this.entityData.get(DATA_ID_ANCHORED);
    }

}
