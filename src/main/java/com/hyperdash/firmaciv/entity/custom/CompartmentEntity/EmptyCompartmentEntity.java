package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import com.google.common.collect.Lists;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.util.FirmacivTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
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

    public EmptyCompartmentEntity(final EntityType<?> entityType, final Level level) {
        super(entityType, level);
    }

    @Override
    protected boolean canAddPassenger(final Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    protected int getMaxPassengers() {
        return 1;
    }

    @Override
    protected void positionRider(final Entity entity, final Entity.MoveFunction moveFunction) {
        super.positionRider(entity, moveFunction);
        moveFunction.accept(entity, this.getX() + 0f, this.getY() - 0.57f, this.getZ() + 0f);
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).yBodyRot = this.yRotO;
        }
        this.clampRotation(entity);
    }

    protected void clampRotation(final Entity entity) {
        entity.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
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
        if (!item.isEmpty() && this.getPassengers().isEmpty()) {
            if (item.is(FirmacivTags.Items.CHESTS)) {
                newCompartment = FirmacivEntities.CHEST_COMPARTMENT_ENTITY.get().create(player.level());
            } else if (item.is(FirmacivTags.Items.WORKBENCHES)) {
                newCompartment = FirmacivEntities.WORKBENCH_COMPARTMENT_ENTITY.get().create(player.level());
            }
        }

        if (newCompartment != null) {
            swapCompartments(newCompartment);
            newCompartment.setYRot(ridingThisPart.getYRot());
            newCompartment.setBlockTypeItem(item.split(1));
            this.playSound(SoundEvents.WOOD_PLACE, 1, player.level().getRandom().nextFloat() * 0.1F + 0.9F);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (!this.level().isClientSide)
            return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;

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