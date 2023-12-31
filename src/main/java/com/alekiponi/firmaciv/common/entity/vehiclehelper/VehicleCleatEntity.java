package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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

public class VehicleCleatEntity extends Entity {

    public static final String LEASH_TAG = "Leash";
    @Nullable
    private Entity leashHolder;
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
        if(!this.level().isClientSide()){

        }
        this.tickLeash();


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
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    protected void tickLeash() {


        if (this.leashInfoTag != null) {
            this.restoreLeashFromSave();
        }
        Entity leashHolder = this.getLeashHolder();
        if (leashHolder != null) {
            if (!this.isAlive() || !leashHolder.isAlive()) {
                this.dropLeash(true, true);
            }

            if (this.getVehicle().isPassenger() && this.getVehicle()
                    .getVehicle() instanceof AbstractFirmacivBoatEntity thisVehicle) {
                if(leashHolder instanceof Player){
                    if (this.distanceTo(leashHolder) > 4f) {
                        Vec3 vectorToVehicle = leashHolder.getPosition(0).vectorTo(thisVehicle.getPosition(0)).normalize();
                        Vec3 movementVector = new Vec3(vectorToVehicle.x * -0.1f, thisVehicle.getDeltaMovement().y,
                                vectorToVehicle.z * -0.1f);
                        double vehicleSize = Mth.clamp(thisVehicle.getBbWidth(), 1, 100);
                        movementVector = movementVector.multiply(1/vehicleSize, 0, 1/vehicleSize);

                        double d0 = leashHolder.getPosition(0).x - this.getX();
                        double d2 = leashHolder.getPosition(0).z - this.getZ();

                        float finalRotation = Mth.wrapDegrees(
                                (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI))- 90.0F);

                        double difference = (leashHolder.getY()) - thisVehicle.getY();
                        if (leashHolder.getY() > thisVehicle.getY() && difference >= 0.4 && difference <= 1.0 && thisVehicle.getDeltaMovement()
                                .length() < 0.02f && leashHolder instanceof Player) {
                            thisVehicle.setPos(thisVehicle.getX(), thisVehicle.getY() + 0.55f, thisVehicle.getZ());
                        }


                        float approach = Mth.approachDegrees(thisVehicle.getYRot(),finalRotation,6);

                        thisVehicle.setDeltaMovement(thisVehicle.getDeltaMovement().add(movementVector));
                        thisVehicle.setDeltaRotation(-1*(thisVehicle.getYRot() - approach));

                    }
                } if(leashHolder instanceof HangingEntity){
                    Vec3 vectorToVehicle = leashHolder.getPosition(0).vectorTo(thisVehicle.getPosition(0)).normalize();
                    Vec3 movementVector = new Vec3(vectorToVehicle.x * -0.005f, thisVehicle.getDeltaMovement().y,
                            vectorToVehicle.z * -0.005f);
                    double d0 = leashHolder.getPosition(0).x - this.getX();
                    double d2 = leashHolder.getPosition(0).z - this.getZ();

                    float finalRotation = Mth.wrapDegrees(
                            (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI))- 90.0F);

                    float approach = Mth.approachDegrees(thisVehicle.getYRot(),finalRotation,4f);
                    if(Mth.degreesDifferenceAbs(thisVehicle.getYRot(), finalRotation) < 4){
                        thisVehicle.setDeltaRotation(0);
                        thisVehicle.setYRot(thisVehicle.getYRot());
                    } else {
                        thisVehicle.setDeltaRotation(-1*(thisVehicle.getYRot() - approach));
                    }
                    if(this.distanceTo(leashHolder) > 1){
                        thisVehicle.setDeltaMovement(movementVector);
                    }

                }


            }
            if (leashHolder instanceof Player player && this.distanceTo(leashHolder) > 10f) {
                this.dropLeash(true, !player.getAbilities().instabuild);
            }

        }
        if (leashHolder != null) {
            if (leashHolder.isPassenger() && leashHolder.getVehicle() instanceof EmptyCompartmentEntity) {
                this.dropLeash(true, true);
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
                if (leashHolder instanceof Player player) {
                    ItemHandlerHelper.giveItemToPlayer(player, Items.LEAD.getDefaultInstance());
                } else {
                    this.spawnAtLocation(Items.LEAD);
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
    public Entity getLeashHolder() {
        if (this.leashHolder == null && this.delayedLeashHolderId != 0 && this.level().isClientSide) {
            this.leashHolder = this.level().getEntity(this.delayedLeashHolderId);
        }

        return this.leashHolder;
    }

    /**
     * Sets the entity to be leashed to.
     */
    public void setLeashedTo(Entity pLeashHolder, boolean pBroadcastPacket) {
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
                Entity entity = ((ServerLevel) this.level()).getEntity(uuid);
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

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Leash", 10)) {
            this.leashInfoTag = pCompound.getCompound("Leash");
        }
    }

    @Override
    public Vec3 getLeashOffset(float pPartialTick) {
        return new Vec3(0.0D, this.getEyeHeight(), 0f);
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
}
