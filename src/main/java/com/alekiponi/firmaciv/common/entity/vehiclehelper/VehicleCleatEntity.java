package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.UUID;

public class VehicleCleatEntity extends net.minecraft.world.entity.Entity {

    protected static final EntityDataAccessor<Integer> DATA_ID_LEASHHOLDER_ID = SynchedEntityData.defineId(
            VehicleCleatEntity.class, EntityDataSerializers.INT);
    @Nullable
    private net.minecraft.world.entity.Entity leashHolder;
    private int delayedLeashHolderId;
    @Nullable
    private CompoundTag leashInfoTag;

    protected int lerpSteps;
    protected double lerpX;
    protected double lerpY;
    protected double lerpZ;
    protected double lerpYRot;
    protected double lerpXRot;

    public VehicleCleatEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void tick() {
        if (!this.isPassenger()) {
            this.dropLeash(true, true);
            this.kill();
        }
        super.tick();

        tickLerp();
        this.tickLeash();

        if (tickCount < 2) {
            if (!this.level().isClientSide()) {
                if (this.leashHolder != null) {
                    this.setLeashHolderId(this.leashHolder.getId());
                }
            } else {
                if (this.level().getEntity(this.getLeashHolderId()) != null) {
                    this.leashHolder = this.level().getEntity(this.getLeashHolderId());
                }
            }
        }

    }

    public final InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.getLeashHolder() == pPlayer) {
            this.dropLeash(true, !pPlayer.getAbilities().instabuild);
            this.gameEvent(GameEvent.ENTITY_INTERACT, pPlayer);

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (itemstack.is(Items.LEAD) && this.canBeLeashed(pPlayer)) {
            this.setLeashedTo(pPlayer, true);
            itemstack.shrink(1);
            this.playSound(SoundEvents.LEASH_KNOT_PLACE, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    protected void tickLeash() {

        if (this.leashInfoTag != null) {
            this.restoreLeashFromSave();
        }
        net.minecraft.world.entity.Entity leashHolder = this.getLeashHolder();
        if (leashHolder != null) {
            if (!this.isAlive() || !leashHolder.isAlive()) {
                this.dropLeash(true, true);

            }
            if(leashHolder instanceof LeashFenceKnotEntity leashFenceKnotEntity){
                if(!leashFenceKnotEntity.survives()){
                    this.dropLeash(true, true);
                }
            }
            /*
            if (leashHolder.isPassenger() && leashHolder.getVehicle() instanceof EmptyCompartmentEntity) {
                this.dropLeash(true, true);
            }*/
            if (this.distanceTo(leashHolder) > 10f) {
                if(leashHolder instanceof Player player){
                    this.dropLeash(true, !player.getAbilities().instabuild);
                } else {
                    this.dropLeash(true,true);
                }
            }

        }

    }

    protected void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double) this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double) this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double) this.lerpSteps;
            double d3 = Mth.wrapDegrees(this.lerpYRot - (double) this.getYRot());
            this.setYRot(this.getYRot() + (float) d3 / (float) this.lerpSteps);
            this.setXRot(this.getXRot() + (float) (this.lerpXRot - (double) this.getXRot()) / (float) this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d1, d2);
            //this.setRot(this.getYRot(), this.getXRot());
        }
    }

    /**
     * Removes the leash from this entity
     */
    public void dropLeash(boolean pBroadcastPacket, boolean pDropLeash) {
        if (this.leashHolder != null) {
            if (!this.level().isClientSide && pDropLeash) {
                this.playSound(SoundEvents.LEASH_KNOT_BREAK, 1.0F, 1.0F);
                if (leashHolder instanceof Player player) {
                    ItemHandlerHelper.giveItemToPlayer(player, Items.LEAD.getDefaultInstance());
                } else {
                    this.spawnAtLocation(Items.LEAD);
                    if(leashHolder instanceof LeashFenceKnotEntity){
                        leashHolder.kill();
                    }
                }

            }
            this.leashHolder = null;
            this.leashInfoTag = null;


            if (!this.level().isClientSide && pBroadcastPacket && this.level() instanceof ServerLevel) {
                ((ServerLevel) this.level()).getChunkSource()
                        .broadcast(this, new ClientboundSetEntityLinkPacket(this, null));
            }
        }

    }

    protected void removeAfterChangingDimensions() {
        super.removeAfterChangingDimensions();
        this.dropLeash(true, false);
    }

    public boolean canBeLeashed(Player pPlayer) {
        return !this.isLeashed();
    }

    public boolean isLeashed() {
        return this.leashHolder != null;
    }

    @Nullable
    public net.minecraft.world.entity.Entity getLeashHolder() {
        if (this.leashHolder == null && this.delayedLeashHolderId != 0 && this.level().isClientSide) {
            this.leashHolder = this.level().getEntity(this.delayedLeashHolderId);
        }

        return this.leashHolder;
    }

    /**
     * Sets the entity to be leashed to.
     */
    public void setLeashedTo(net.minecraft.world.entity.Entity pLeashHolder, boolean pBroadcastPacket) {
        this.leashHolder = pLeashHolder;
        this.leashInfoTag = null;
        if (!this.level().isClientSide && pBroadcastPacket && this.level() instanceof ServerLevel) {
            ((ServerLevel) this.level()).getChunkSource()
                    .broadcast(this, new ClientboundSetEntityLinkPacket(this, this.leashHolder));
        }

    }

    public void setDelayedLeashHolderId(int pLeashHolderID) {
        this.delayedLeashHolderId = pLeashHolderID;
        this.dropLeash(false, false);
    }

    private void restoreLeashFromSave() {
        if (this.leashInfoTag != null && this.level() instanceof ServerLevel) {
            if (this.leashInfoTag.hasUUID("UUID")) {
                UUID uuid = this.leashInfoTag.getUUID("UUID");
                net.minecraft.world.entity.Entity entity = ((ServerLevel) this.level()).getEntity(uuid);
                if (entity != null) {
                    this.setLeashedTo(entity, true);
                    return;
                }
            } else if (this.leashInfoTag.contains("X", 99) && this.leashInfoTag.contains("Y",
                    99) && this.leashInfoTag.contains("Z", 99)) {
                BlockPos blockpos = NbtUtils.readBlockPos(this.leashInfoTag);
                this.setLeashedTo(LeashFenceKnotEntity.getOrCreateKnot(this.level(), blockpos), true);
                return;
            }

            if (this.tickCount > 100) {
                this.spawnAtLocation(Items.LEAD);
                this.leashInfoTag = null;
            }
        }

    }


    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_LEASHHOLDER_ID, -1);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Leash", 10)) {
            this.leashInfoTag = pCompound.getCompound("Leash");
        }
    }

    @Override
    public Vec3 getLeashOffset(float pPartialTick) {
        return new Vec3(0.0f, 0f, 0f);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        if (this.leashHolder != null) {
            CompoundTag compoundtag2 = new CompoundTag();
            if (this.leashHolder instanceof LivingEntity) {
                UUID uuid = this.leashHolder.getUUID();
                compoundtag2.putUUID("UUID", uuid);
            } else if (this.leashHolder instanceof HangingEntity) {
                BlockPos blockpos = ((HangingEntity) this.leashHolder).getPos();
                compoundtag2.putInt("X", blockpos.getX());
                compoundtag2.putInt("Y", blockpos.getY());
                compoundtag2.putInt("Z", blockpos.getZ());
            }

            pCompound.put("Leash", compoundtag2);
        } else if (this.leashInfoTag != null) {
            pCompound.put("Leash", this.leashInfoTag.copy());
        }
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        float bbRadius = 10 * 2 + 1;
        Vec3 startingPoint = new Vec3(this.getX() - bbRadius, this.getY() - bbRadius, this.getZ() - bbRadius);
        Vec3 endingPoint = new Vec3(this.getX() + bbRadius, this.getY() + bbRadius, this.getZ() + bbRadius);
        return new AABB(startingPoint, endingPoint);
    }

    public void setLeashHolderId(int id) {
        this.entityData.set(DATA_ID_LEASHHOLDER_ID, id);
    }

    public int getLeashHolderId() {
        return this.entityData.get(DATA_ID_LEASHHOLDER_ID);
    }
}
