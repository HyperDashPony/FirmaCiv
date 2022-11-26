package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.item.ModItems;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;

public class CanoeEntity extends FirmacivBoatEntity{

    public CanoeEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(FirmacivBlocks.CANOE_COMPONENT_BLOCK.get());
    }

}
