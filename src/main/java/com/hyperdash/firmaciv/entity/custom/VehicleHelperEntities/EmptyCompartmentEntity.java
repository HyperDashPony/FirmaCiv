package com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities;

import com.google.common.collect.Lists;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import com.hyperdash.firmaciv.entity.custom.KayakEntity;
import com.hyperdash.firmaciv.entity.custom.RowboatEntity;
import com.hyperdash.firmaciv.util.FirmacivTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EmptyCompartmentEntity extends AbstractCompartmentEntity {
    protected boolean inputLeft;
    protected boolean inputRight;
    protected boolean inputUp;
    protected boolean inputDown;

    protected boolean canAddNonPlayers;


    public EmptyCompartmentEntity(final EntityType<?> entityType, final Level level) {
        super(entityType, level);
        canAddNonPlayers = true;
    }

    public boolean canAddNonPlayers(){
        return canAddNonPlayers;
    }

    @Override
    protected boolean canAddPassenger(final Entity passenger) {
        return this.getPassengers().isEmpty() && !this.isRemoved();
    }

    protected int getMaxPassengers() {
        return 1;
    }

    @Override
    protected void positionRider(final Entity passenger, final Entity.MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);
        float localX = 0.0F;
        float localZ = 0.0F;
        float localY = (float)((this.isRemoved() ? 0.01 : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
        if (passenger instanceof Player) {
            localY = 0;
            if(this.getTrueVehicle() instanceof RowboatEntity rowboatEntity){
                localY = 0.25f;
                if(rowboatEntity.getPilotVehiclePartAsEntity() != ridingThisPart){
                    localX = -0.25f;
                }
            }
        }
        if (passenger.getBbHeight() <= 0.7) {
            localY -= 0.2f;
        } if(passenger.getBbWidth() > 0.9f && !(passenger instanceof Player)){
            localX += 0.2f;
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
        }
        this.clampRotation(passenger);
    }

    protected Vec3 positionPassengerLocally(float localX, float localY, float localZ){
        return (new Vec3(localX, localY, localZ)).yRot(-this.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
    }

    @Override
    public void tick() {
        if(this.getTrueVehicle() != null){
            if(tickCount < 10 && this.getTrueVehicle().getPilotVehiclePartAsEntity() != null && !(this.getTrueVehicle() instanceof CanoeEntity)){
                canAddNonPlayers = !(this.getTrueVehicle().getPilotVehiclePartAsEntity() == this.getVehicle());
            }
        }

        this.checkInsideBlocks();
        final List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(0.2, -0.01, 0.2), EntitySelector.pushableBy(this));

        if (!list.isEmpty() && this.canAddNonPlayers) {
            for (final Entity entity : list) {
                final boolean flag = !this.level().isClientSide && !(this.getControllingPassenger() instanceof Player);
                if (!flag) break;

                if (!entity.hasPassenger(this)) {

                    float maxSize = 1.0f;
                    if (this.getTrueVehicle() instanceof CanoeEntity) {
                        maxSize = 0.9f;
                    } else if (this.getTrueVehicle() instanceof KayakEntity) {
                        maxSize = 0.6f;
                    } else if (this.getTrueVehicle() instanceof RowboatEntity) {
                        maxSize = 1.4f;
                    }
                    if (this.getPassengers().size() < 2 && !entity.isPassenger() && entity.getBbWidth() <= maxSize && entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity instanceof Player)) {
                        entity.startRiding(this);
                    }
                }
            }
        }
        super.tick();
    }

    protected void clampRotation(final Entity entity) {
        entity.setYBodyRot(this.getYRot());
        final float f = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
        final float f1 = Mth.clamp(f, -105.0F, 105.0F);
        entity.yRotO += f1 - f;
        entity.setYRot(entity.getYRot() + f1 - f);
        entity.setYHeadRot(entity.getYRot());
    }


    public void setInput(final boolean inputLeft, final boolean inputRight, final boolean inputUp, final boolean inputDown) {
        this.inputLeft = inputLeft;
        this.inputRight = inputRight;
        this.inputUp = inputUp;
        this.inputDown = inputDown;
    }

    public boolean getInputLeft() {
        return inputLeft;
    }

    public boolean getInputRight() {
        return inputRight;
    }

    public boolean getInputUp() {
        return inputUp;
    }

    public boolean getInputDown() {
        return inputDown;
    }

    @Override
    public void onPassengerTurned(final Entity entity) {
        this.clampRotation(entity);
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        final ItemStack item = player.getItemInHand(hand);

        if (player.isSecondaryUseActive()) {
            if (!getPassengers().isEmpty() && !(getPassengers().get(0) instanceof Player)) {
                this.ejectPassengers();
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        AbstractCompartmentEntity newCompartment = null;
        if (canAddNonPlayers && !item.isEmpty() && this.getPassengers().isEmpty()) {
            if (item.is(FirmacivTags.Items.CHESTS)) {
                newCompartment = FirmacivEntities.CHEST_COMPARTMENT_ENTITY.get().create(player.level());
            } /* else if (item.is(FirmacivTags.Items.WORKBENCHES)) {
                newCompartment = FirmacivEntities.WORKBENCH_COMPARTMENT_ENTITY.get().create(player.level());
            } */
        }

        if (ridingThisPart == null) return InteractionResult.PASS;

        if (newCompartment != null) {
            swapCompartments(newCompartment);
            newCompartment.setYRot(newCompartment.ridingThisPart.getYRot() + ridingThisPart.compartmentRotation);
            newCompartment.setBlockTypeItem(item.split(1));
            this.playSound(SoundEvents.WOOD_PLACE, 1.0F, player.level().getRandom().nextFloat() * 0.1F + 0.9F);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (!this.level().isClientSide) {
            return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(final LivingEntity passenger) {
        final Vec3 escapeVector = getCollisionHorizontalEscapeVector(this.getBbWidth() * Mth.SQRT_OF_TWO, passenger.getBbWidth(), passenger.getYRot());

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
                return output;
            }
        }

        return super.getDismountLocationForPassenger(passenger);
    }
}