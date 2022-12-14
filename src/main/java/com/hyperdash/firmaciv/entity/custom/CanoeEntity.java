package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.block.FirmacivBlocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CanoeEntity extends FirmacivBoatEntity{
    public enum Type {

    }

    @Override
    public Item getDropItem() {
        return Items.STICK;
    }

    public CanoeEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public ItemStack getPickResult() {
        return null;
    }

}
