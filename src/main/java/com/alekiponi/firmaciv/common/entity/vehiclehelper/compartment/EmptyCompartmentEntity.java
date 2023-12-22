package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment;

import com.alekiponi.firmaciv.common.entity.CannonEntity;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.RowboatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.network.PacketHandler;
import com.alekiponi.firmaciv.network.ServerboundCompartmentInputPacket;
import com.google.common.collect.Lists;
import net.dries007.tfc.common.entities.predator.Predator;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class EmptyCompartmentEntity extends AbstractCompartmentEntity {
    protected static final EntityDataAccessor<Long> DATA_ID_PASSENGER_RIDE_TICK = SynchedEntityData.defineId(
            EmptyCompartmentEntity.class, EntityDataSerializers.LONG);

    protected static final EntityDataAccessor<Boolean> DATA_ID_INPUT_LEFT = SynchedEntityData.defineId(
            EmptyCompartmentEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> DATA_ID_INPUT_RIGHT = SynchedEntityData.defineId(
            EmptyCompartmentEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> DATA_ID_INPUT_UP = SynchedEntityData.defineId(
            EmptyCompartmentEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> DATA_ID_INPUT_DOWN = SynchedEntityData.defineId(
            EmptyCompartmentEntity.class, EntityDataSerializers.BOOLEAN);

    protected boolean canAddNonPlayers;
    protected boolean canAddOnlyBlocks;


    public EmptyCompartmentEntity(final EntityType<? extends EmptyCompartmentEntity> entityType, final Level level) {
        super(entityType, level);
        canAddNonPlayers = true;
        canAddOnlyBlocks = false;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_PASSENGER_RIDE_TICK, Long.MAX_VALUE);
        this.entityData.define(DATA_ID_INPUT_LEFT, false);
        this.entityData.define(DATA_ID_INPUT_RIGHT, false);
        this.entityData.define(DATA_ID_INPUT_UP, false);
        this.entityData.define(DATA_ID_INPUT_DOWN, false);
        super.defineSynchedData();
    }

    public long getPassengerRideTick() {
        return this.entityData.get(DATA_ID_PASSENGER_RIDE_TICK);
    }

    public void setPassengerRideTick(final long stillTick) {
        this.entityData.set(DATA_ID_PASSENGER_RIDE_TICK, stillTick);
    }


    public boolean canAddNonPlayers() {
        return canAddNonPlayers;
    }

    public boolean canAddOnlyBLocks() {
        return canAddOnlyBlocks;
    }


    protected void addPassenger(Entity pPassenger) {
        super.addPassenger(pPassenger);
        if (this.isPassenger()) {
            this.setYRot(this.getVehicle().getYRot());
        }
    }

    @Override
    protected boolean canAddPassenger(final Entity passenger) {
        return this.getPassengers().isEmpty() && !this.isRemoved();
    }

    @Override
    protected void positionRider(final Entity passenger, final Entity.MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);
        float localX = 0.0F;
        float localZ = 0.0F;
        float localY = (float) ((this.isRemoved() ? 0.01 : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
        if (passenger instanceof Player) {
            localY = 0;
            if (this.getTrueVehicle() instanceof RowboatEntity rowboatEntity) {
                localY = 0.25f;
                if (rowboatEntity.getPilotVehiclePartAsEntity() != ridingThisPart) {
                    localX = -0.25f;
                }
            }
            if (this.getTrueVehicle() instanceof SloopEntity sloopEntity) {
                localY += 0.0f;
            }
        }

        if (passenger.getBbHeight() <= 0.7) {
            localY -= 0.2f;
        }
        if (passenger instanceof CannonEntity) {
            localY += 0.15f;
        }
        if (passenger.getBbWidth() > 0.9f) {
            localX += 0.2f;
            if (this.getTrueVehicle() instanceof RowboatEntity) {
                localX -= 0.6f;
            }
        }


        final double eyepos = passenger.getEyePosition().get(Direction.Axis.Y);
        final double thisY = this.getY() + 1.1f;
        if (eyepos < thisY) {
            localY += (float) Math.abs(eyepos - thisY);
        }
        final Vec3 vec3 = this.positionPassengerLocally(localX, localY - 0.57f, localZ);
        moveFunction.accept(passenger, this.getX() + vec3.x, this.getY() - 0.57 + localY, this.getZ() + vec3.z);

        if (passenger instanceof LivingEntity livingEntity) {
            livingEntity.setYBodyRot(this.getYRot());
            livingEntity.setYHeadRot(livingEntity.getYHeadRot() + this.getYRot());
            this.clampRotation(livingEntity);
        } else if(passenger instanceof CannonEntity cannon){
            cannon.setYRot(-this.getYRot()-180);
            if(cannon.getXRot() > 5){
                cannon.setXRot(5);
            }
        }
        //this.clampRotation(passenger);
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.6d * 0.75D;
    }

    protected Vec3 positionPassengerLocally(float localX, float localY, float localZ) {
        return (new Vec3(localX, localY, localZ)).yRot(
                -this.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
    }

    @Override
    public void tick() {
        if (this.getTrueVehicle() != null) {
            if (tickCount < 10 && this.getTrueVehicle()
                    .getPilotVehiclePartAsEntity() != null && !(this.getTrueVehicle() instanceof CanoeEntity)) {
                canAddNonPlayers = !(this.getTrueVehicle().getPilotVehiclePartAsEntity() == this.getVehicle());
            }
            if (tickCount < 10 && this.isPassenger()) {
                for(AbstractCompartmentEntity compartment : this.getTrueVehicle().getCanAddOnlyBlocks()){
                    if(compartment.getVehicle() == this.getVehicle()){
                        canAddOnlyBlocks = true;
                    }
                }
            }
        }

        if(everyNthTickUnique(5)){
            if(this.isVehicle() && !this.level().isClientSide()){
                if(this.getFirstPassenger() != null && this.getTrueVehicle() != null && this.getFirstPassenger().getBbWidth() > this.getTrueVehicle().getPassengerSizeLimit()){
                    this.ejectPassengers();
                }
            }

            final List<Entity> list = this.level()
                    .getEntities(this, this.getBoundingBox().inflate(0.2, -0.01, 0.2), EntitySelector.pushableBy(this));

            if (!list.isEmpty() && this.canAddNonPlayers() && !this.canAddOnlyBLocks() && !this.level()
                    .isClientSide() && this.getTrueVehicle() != null) {
                for (final Entity entity : list) {
                    if (!entity.hasPassenger(this)) {
                        float maxSize = 0.6f;
                        maxSize = this.getTrueVehicle().getPassengerSizeLimit();
                        if (this.getPassengers()
                                .size() == 0 && !entity.isPassenger() && entity.getBbWidth() <= maxSize) {
                            if(entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity instanceof Player)){
                                if (!(entity instanceof Predator)) {
                                    entity.startRiding(this);
                                    this.setPassengerRideTick(Calendars.SERVER.getTicks());
                                }
                            }

                        }
                    }

                }
            }
            if (this.isVehicle() && !(this.getFirstPassenger() instanceof Player)) {
                long remainingTicks = (long) (ICalendar.TICKS_IN_DAY * 3) - (Calendars.SERVER.getTicks() - this.getPassengerRideTick());

                if (remainingTicks <= 0L) {
                    this.ejectPassengers();
                }
            }
        }


        if(!(this.getFirstPassenger() instanceof Player)){
            this.setInputLeft(false);
            this.setInputRight(false);
            this.setInputUp(false);
            this.setInputDown(false);
        }

        super.tick();

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setPassengerRideTick(pCompound.getLong("passengerRideTick"));

        pCompound.getBoolean("inputLeft");
        pCompound.getBoolean("inputRight");
        pCompound.getBoolean("inputUp");
        pCompound.getBoolean("inputDown");

        super.readAdditionalSaveData(pCompound);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putLong("passengerRideTick", this.getPassengerRideTick());

        pCompound.putBoolean("inputLeft", this.getInputLeft());
        pCompound.putBoolean("inputRight", this.getInputRight());
        pCompound.putBoolean("inputUp", this.getInputUp());
        pCompound.putBoolean("inputDown", this.getInputDown());

        super.readAdditionalSaveData(pCompound);
    }

    protected void clampRotation(final Entity entity) {
        entity.setYBodyRot(this.getYRot());
        final float f = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
        final float f1 = Mth.clamp(f, -105.0F, 105.0F);
        entity.yRotO += f1 - f;
        entity.setYRot(entity.getYRot() + f1 - f);
        entity.setYHeadRot(entity.getYRot());
    }


    public void setInput(final boolean inputLeft, final boolean inputRight, final boolean inputUp,
            final boolean inputDown) {
        if(this.getFirstPassenger() instanceof Player){
            boolean shouldUpdateServer = false;
            if(this.getInputLeft() != inputLeft){
                this.setInputLeft(inputLeft);
                shouldUpdateServer = true;
            }
            if(this.getInputRight() != inputRight){
                this.setInputRight(inputRight);
                shouldUpdateServer = true;
            }
            if(this.getInputUp() != inputUp){
                this.setInputUp(inputUp);
                shouldUpdateServer = true;
            }
            if(this.getInputDown() != inputDown){
                this.setInputDown(inputDown);
                shouldUpdateServer = true;
            }
            if(this.level().isClientSide() && shouldUpdateServer){
                PacketHandler.clientSendPacket(new ServerboundCompartmentInputPacket(inputLeft, inputRight, inputUp, inputDown, this.getId()));
            }
        } else {
            this.setInputLeft(false);
            this.setInputRight(false);
            this.setInputUp(false);
            this.setInputDown(false);
            PacketHandler.clientSendPacket(new ServerboundCompartmentInputPacket(false, false, false, false, this.getId()));
        }

    }

    public boolean getInputLeft() {
        return this.entityData.get(DATA_ID_INPUT_LEFT);
    }

    public boolean getInputRight() {

        return this.entityData.get(DATA_ID_INPUT_RIGHT);
    }

    public boolean getInputUp(){
        return this.entityData.get(DATA_ID_INPUT_UP);
    }

    public boolean getInputDown() {

        return this.entityData.get(DATA_ID_INPUT_DOWN);
    }

    public void setInputLeft(boolean input) {

        this.entityData.set(DATA_ID_INPUT_LEFT, input);
    }

    public void setInputRight(boolean input) {

        this.entityData.set(DATA_ID_INPUT_RIGHT, input);
    }

    public void setInputUp(boolean input) {
        this.entityData.set(DATA_ID_INPUT_UP, input);
    }

    public void setInputDown(boolean input) {

        this.entityData.set(DATA_ID_INPUT_DOWN, input);
    }

    @Override
    public void onPassengerTurned(final Entity entity) {
        this.clampRotation(entity);
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        final ItemStack heldStack = player.getItemInHand(hand);

        if (this.canAddNonPlayers() && !this.canAddOnlyBLocks() && heldStack.is(
                FirmacivItems.CANNON.get()) && this.getRootVehicle() instanceof SloopEntity) {
            CannonEntity cannon = FirmacivEntities.CANNON_ENTITY.get().create(this.level());
            cannon.moveTo(this.getPosition(0));
            cannon.setYRot(-this.getYRot() - 180);
            cannon.startRiding(this);
            if (!this.level().isClientSide()) {
                this.level().addFreshEntity(cannon);
            }
            player.awardStat(Stats.ITEM_USED.get(FirmacivItems.CANNON.get()));
            if (!player.getAbilities().instabuild) {
                heldStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }

        if (player.isSecondaryUseActive()) {
            if (!getPassengers().isEmpty() && !(getPassengers().get(0) instanceof Player)) {
                this.ejectPassengers();
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        if (ridingThisPart == null) return InteractionResult.PASS;

        final Optional<CompartmentType<?>> compartmentType = CompartmentType.fromStack(heldStack);

        if (compartmentType.isPresent()) {
            final AbstractCompartmentEntity compartmentEntity = compartmentType.get()
                    .create(this.level(), heldStack.split(1));
            this.swapCompartments(compartmentEntity);
            // TODO per type placement sounds
            this.playSound(SoundEvents.WOOD_PLACE, 1, player.level().getRandom().nextFloat() * 0.1F + 0.9F);
            this.gameEvent(GameEvent.EQUIP);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (!this.level().isClientSide && !this.canAddOnlyBLocks()) {
            return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(final LivingEntity passenger) {
        if(this.getTrueVehicle().getDeltaMovement().length() > 0.01){
            return this.getTrueVehicle().getDismountLocationForPassenger(passenger);
        }
        double y = this.getTrueVehicle().getDismountLocationForPassenger(passenger).y();

        final Vec3 escapeVector = getCollisionHorizontalEscapeVector(this.getBbWidth() * Mth.SQRT_OF_TWO,
                passenger.getBbWidth(), passenger.getYRot());

        final double escapeX = this.getX() + escapeVector.x;
        final double escapeZ = this.getZ() + escapeVector.z;

        final BlockPos escapePos = BlockPos.containing(escapeX, this.getBoundingBox().maxY, escapeZ);
        final BlockPos escapePosBelow = escapePos.below();

        if (this.level().isWaterAt(escapePosBelow)) return super.getDismountLocationForPassenger(passenger);

        final List<Vec3> dismountOffsets = Lists.newArrayList();
        {
            final double floorHeight = this.level().getBlockFloorHeight(escapePos);
            if (DismountHelper.isBlockFloorValid(floorHeight)) {
                dismountOffsets.add(new Vec3(escapeX, escapePos.getY() + floorHeight, escapeZ));
            }
        }
        {
            final double floorHeight = this.level().getBlockFloorHeight(escapePosBelow);
            if (DismountHelper.isBlockFloorValid(floorHeight)) {
                dismountOffsets.add(new Vec3(escapeX, escapePosBelow.getY() + floorHeight, escapeZ));
            }
        }

        for (final Pose dismountPose : passenger.getDismountPoses()) {
            for (final Vec3 output : dismountOffsets) {
                if (!DismountHelper.canDismountTo(this.level(), output, passenger, dismountPose)) continue;

                passenger.setPose(dismountPose);
                return new Vec3(output.x, y, output.z);
            }
        }

        return super.getDismountLocationForPassenger(passenger);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        final Entity entity = this.getFirstPassenger();

        if (!(entity instanceof LivingEntity livingentity)) return null;

        return livingentity;
    }
}