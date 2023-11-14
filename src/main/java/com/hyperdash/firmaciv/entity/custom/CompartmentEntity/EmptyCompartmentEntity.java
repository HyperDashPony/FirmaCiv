package com.hyperdash.firmaciv.entity.custom.CompartmentEntity;

import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.entity.custom.CanoeEntity;
import net.dries007.tfc.common.container.RestrictedChestContainer;
import net.dries007.tfc.common.container.TFCContainerTypes;
import net.dries007.tfc.common.entities.misc.TFCChestBoat;
import net.dries007.tfc.common.items.ChestBlockItem;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class EmptyCompartmentEntity extends AbstractCompartmentEntity{

    public EmptyCompartmentEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected boolean canAddPassenger(Entity pPassenger) {
        return this.getPassengers().size() == 0;
    }

    protected float getSinglePassengerXOffset() {
        return 00F;
    }

    protected int getMaxPassengers() {
        return 1;
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
