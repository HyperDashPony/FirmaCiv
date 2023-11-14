package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import com.google.common.collect.Lists;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EmptyCompartmentEntity extends AbstractCompartmentEntity{

    protected boolean inputLeft;
    protected boolean inputRight;
    protected boolean inputUp;
    protected boolean inputDown;

    public boolean getInputLeft(){return inputLeft;}
    public boolean getInputRight(){return inputRight;}
    public boolean getInputUp(){return inputUp;}
    public boolean getInputDown(){return inputDown;}
    public EmptyCompartmentEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected boolean canAddPassenger(Entity pPassenger) {
        return this.getPassengers().size() == 0;
    }

    protected int getMaxPassengers() {
        return 1;
    }

    protected void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
        super.positionRider(pPassenger, pCallback);
        pCallback.accept(pPassenger, this.getX() + 0f, this.getY()-0.6f, this.getZ()+ 0f);
        if (pPassenger instanceof LivingEntity) {
            ((LivingEntity)pPassenger).yBodyRot = this.yRotO;
        }
        this.clampRotation(pPassenger);

    }

    protected void clampRotation(Entity pEntityToUpdate) {
        pEntityToUpdate.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(pEntityToUpdate.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        pEntityToUpdate.yRotO += f1 - f;
        pEntityToUpdate.setYRot(pEntityToUpdate.getYRot() + f1 - f);
        pEntityToUpdate.setYHeadRot(pEntityToUpdate.getYRot());
    }

    public void setInput(boolean pInputLeft, boolean pInputRight, boolean pInputUp, boolean pInputDown) {
        this.inputLeft = pInputLeft;
        this.inputRight = pInputRight;
        this.inputUp = pInputUp;
        this.inputDown = pInputDown;
    }

    public void onPassengerTurned(Entity pEntityToUpdate) {
        this.clampRotation(pEntityToUpdate);
    }

    public boolean isPushable() {
        return false;
    }

    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {

        ItemStack item = pPlayer.getItemInHand(pHand);
        AbstractCompartmentEntity newCompartment = null;
        if (pPlayer.isSecondaryUseActive()) {
            if(!getPassengers().isEmpty() && !(getPassengers().get(0) instanceof Player)){
                this.ejectPassengers();
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        if (item.getItem() == Items.CHEST) {
            newCompartment = FirmacivEntities.CHEST_COMPARTMENT_ENTITY.get().create(pPlayer.level());
        } else if (item.getItem() == Items.CRAFTING_TABLE) {
            newCompartment = FirmacivEntities.WORKBENCH_COMPARTMENT_ENTITY.get().create(pPlayer.level());
        }
        if (newCompartment != null) {
            newCompartment.setBlockTypeItem(item.split(1));
            newCompartment.setPos(this.position());
            newCompartment.setYRot(this.getYRot());
            newCompartment.setXRot(this.getXRot());
            newCompartment.setDeltaMovement(this.getDeltaMovement());
            newCompartment.setPassengerIndex(this.passengerIndex);
            this.level().addFreshEntity(newCompartment);
            this.playSound(SoundEvents.WOOD_PLACE, 1.0F, pPlayer.level().getRandom().nextFloat() * 0.1F + 0.9F);
            this.discard();
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            if (!this.level().isClientSide) {
                return pPlayer.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
            } else {
                return InteractionResult.SUCCESS;
            }
        }


    }

    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        Vec3 vec3 = getCollisionHorizontalEscapeVector((double)(this.getBbWidth() * Mth.SQRT_OF_TWO), (double)pLivingEntity.getBbWidth(), pLivingEntity.getYRot());
        double d0 = this.getX() + vec3.x;
        double d1 = this.getZ() + vec3.z;
        BlockPos blockpos = BlockPos.containing(d0, this.getBoundingBox().maxY, d1);
        BlockPos blockpos1 = blockpos.below();
        if (!this.level().isWaterAt(blockpos1)) {
            List<Vec3> list = Lists.newArrayList();
            double d2 = this.level().getBlockFloorHeight(blockpos);
            if (DismountHelper.isBlockFloorValid(d2)) {
                list.add(new Vec3(d0, (double)blockpos.getY() + d2, d1));
            }

            double d3 = this.level().getBlockFloorHeight(blockpos1);
            if (DismountHelper.isBlockFloorValid(d3)) {
                list.add(new Vec3(d0, (double)blockpos1.getY() + d3, d1));
            }

            for(Pose pose : pLivingEntity.getDismountPoses()) {
                for(Vec3 vec31 : list) {
                    if (DismountHelper.canDismountTo(this.level(), vec31, pLivingEntity, pose)) {
                        pLivingEntity.setPose(pose);
                        return vec31;
                    }
                }
            }
        }

        return super.getDismountLocationForPassenger(pLivingEntity);
    }


}
