package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.item.ModItems;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CanoeEntity extends FirmacivBoatEntity{
    public CanoeEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get());
    }
}
