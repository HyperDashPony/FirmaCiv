package com.hyperdash.firmaciv.common.entity.vehiclehelper;

import com.hyperdash.firmaciv.common.entity.FirmacivBoatEntity;
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
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.UUID;

public class VehicleCleatEntity extends Entity {

    private static final EntityDataAccessor<Byte> DATA_MOB_FLAGS_ID = SynchedEntityData.defineId(VehicleCleatEntity.class, EntityDataSerializers.BYTE);

    public static final String LEASH_TAG = "Leash";
    @Nullable
    private Entity leashHolder;
    private int delayedLeashHolderId;
    @Nullable
    private CompoundTag leashInfoTag;

    public VehicleCleatEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public void tick() {
        if (!this.isPassenger()) {
            this.dropLeash(true, true);
            this.kill();
        }
        super.tick();
        if (!this.level().isClientSide) {
            this.tickLeash();
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
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    protected void tickLeash() {
        if (this.leashInfoTag != null) {
            this.restoreLeashFromSave();
        }

        if (this.leashHolder != null) {
            if (!this.isAlive() || !this.leashHolder.isAlive()) {
                this.dropLeash(true, true);
            }
            if (this.getVehicle().isPassenger() && this.getVehicle().getVehicle() instanceof FirmacivBoatEntity && this.leashHolder instanceof Player player) {
                if (this.distanceTo(this.leashHolder) > 4f) {

                    FirmacivBoatEntity thisVehicle = (FirmacivBoatEntity) this.getVehicle().getVehicle();
                    Vec3 vectorToVehicle = player.getPosition(0).vectorTo(thisVehicle.getPosition(0)).normalize();
                    Vec3 movementVector = new Vec3(vectorToVehicle.x * -0.1f, thisVehicle.getDeltaMovement().y, vectorToVehicle.z * -0.1f);


                    //float rotationTowardsPlayer = (float) Math.atan(player.getPosition(0).vectorTo(thisVehicle.getPosition(0)).z / player.getPosition(0).vectorTo(thisVehicle.getPosition(0)).x);
                    //float vehicleDeltaRotation = (float)Math.toDegrees((thisVehicle.getYRot() - rotationTowardsPlayer))/4.0f;

                    double d0 = player.getPosition(0).x - thisVehicle.getX();
                    double d2 = player.getPosition(0).z - thisVehicle.getZ();
                    double d3 = Math.sqrt(d0 * d0 + d2 * d2);

                    float finalRotation = Mth.wrapDegrees((float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F);
                    float intermediateRotation = thisVehicle.getYRot() + (Mth.wrapDegrees((finalRotation - thisVehicle.getYRot())) / 45);


                    double difference = (player.getY()) - thisVehicle.getY();
                    if (player.getY() > thisVehicle.getY() && difference >= 0.4 && difference <= 1.0 && thisVehicle.getDeltaMovement().length() < 0.02f) {
                        thisVehicle.setPos(thisVehicle.getX(), thisVehicle.getY() + 0.55f, thisVehicle.getZ());
                    }

                    for (Entity part : thisVehicle.getPassengers()) {
                        part.setYRot(thisVehicle.getYRot());
                    }
                    for (Entity compartment : thisVehicle.getCompartments()) {
                        compartment.setYRot(thisVehicle.getYRot());
                    }

                    thisVehicle.setDeltaMovement(movementVector);
                    thisVehicle.setYRot(finalRotation);


                }


            }
            if (this.distanceTo(this.leashHolder) > 10f && this.leashHolder instanceof Player player) {
                this.dropLeash(true, !player.getAbilities().instabuild);
            }

        }
        if (this.leashHolder != null) {
            if (this.leashHolder.isPassenger() && this.leashHolder.getVehicle() instanceof EmptyCompartmentEntity) {
                this.dropLeash(true, true);
            }
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
                ((ServerLevel) this.level()).getChunkSource().broadcast(this, new ClientboundSetEntityLinkPacket(this, (Entity) null));
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
            ((ServerLevel) this.level()).getChunkSource().broadcast(this, new ClientboundSetEntityLinkPacket(this, this.leashHolder));
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
            } else if (this.leashInfoTag.contains("X", 99) && this.leashInfoTag.contains("Y", 99) && this.leashInfoTag.contains("Z", 99)) {
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
        this.entityData.define(DATA_MOB_FLAGS_ID, (byte) 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Leash", 10)) {
            this.leashInfoTag = pCompound.getCompound("Leash");
        }
    }

    @Override
    public Vec3 getLeashOffset(float pPartialTick) {
        return new Vec3(0.0D, (double) this.getEyeHeight(), 0f);
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
}
