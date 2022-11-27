package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.entity.FirmacivBoatRenderer.FirmacivBoatRenderer;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class FirmacivBoatEntity extends Boat {
    public FirmacivBoatEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }



}
