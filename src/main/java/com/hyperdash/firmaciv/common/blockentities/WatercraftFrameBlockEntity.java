package com.hyperdash.firmaciv.common.blockentities;

import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WatercraftFrameBlockEntity extends BlockEntity {
    public static final int PLANKS_NEEDED = 4;
    public static final int BOLTS_NEEDED = 4;
    private final NonNullList<ItemStack> plankItems;
    private final NonNullList<ItemStack> boltItems;

    public WatercraftFrameBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FirmacivBlockEntities.WATERCRAFT_FRAME_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.plankItems = NonNullList.withSize(PLANKS_NEEDED, ItemStack.EMPTY);
        this.boltItems = NonNullList.withSize(BOLTS_NEEDED, ItemStack.EMPTY);
    }

    public void loadAdditional(CompoundTag nbt) {
        ContainerHelper.loadAllItems(nbt.getCompound("plankItems"), this.plankItems);
        ContainerHelper.loadAllItems(nbt.getCompound("boltItems"), this.boltItems);
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        nbt.put("plankItems", ContainerHelper.saveAllItems(new CompoundTag(), this.plankItems));
        nbt.put("boltItems", ContainerHelper.saveAllItems(new CompoundTag(), this.boltItems));
        super.saveAdditional(nbt);
    }

    @Override
    public final void load(CompoundTag tag) {
        this.loadAdditional(tag);
        super.load(tag);
    }

    public void ejectInventory() {
        assert this.level != null;
        this.boltItems.forEach((stack) -> {
            Helpers.spawnItem(this.level, this.worldPosition, stack);
        });
        this.plankItems.forEach((stack) -> {
            Helpers.spawnItem(this.level, this.worldPosition, stack);
        });
    }


    public void deletePlankItems(int slot) {
        this.plankItems.set(slot, ItemStack.EMPTY);
    }

    public void deleteBoltItems(int slot) {
        this.boltItems.set(slot, ItemStack.EMPTY);
    }

    public void addPlankItems(ItemStack stack, int slot) {
        this.plankItems.set(slot, stack);
    }

    public void addBoltItems(ItemStack stack, int slot) {
        this.boltItems.set(slot, stack);
    }

    public NonNullList<ItemStack> getPlankItems() {
        return this.plankItems;
    }

    public NonNullList<ItemStack> getBoltItems() {
        return this.boltItems;
    }

}
