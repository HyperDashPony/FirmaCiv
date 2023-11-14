package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import com.hyperdash.firmaciv.entity.FirmacivEntities;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

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


}
