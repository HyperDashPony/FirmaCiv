package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.network.PacketHandler;
import com.alekiponi.firmaciv.network.ServerboundSwitchEntityPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class AbstractSwitchEntity extends AbstractInvisibleHelper {

    public AbstractSwitchEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected static final EntityDataAccessor<Boolean> DATA_ID_SWITCH = SynchedEntityData.defineId(
            AbstractSwitchEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        if(this.level().isClientSide()){
            PacketHandler.clientSendPacket(new ServerboundSwitchEntityPacket(!this.getSwitched(),this.getId()));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick(){
        if(!this.isPassenger()){
            this.kill();
        }
        super.tick();
    }


    public void setSwitched(boolean switched) {
        this.entityData.set(DATA_ID_SWITCH, switched);
    }

    public boolean getSwitched() {
        return this.entityData.get(DATA_ID_SWITCH);
    }


    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_SWITCH, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setSwitched(pCompound.getBoolean("switched"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putBoolean("switched", this.getSwitched());
    }
}
