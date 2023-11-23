package com.hyperdash.firmaciv.common.blockentity;

import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WatercraftFrameBlockEntity extends BlockEntity {
    private ItemStack planks = ItemStack.EMPTY;
    private ItemStack bolts = ItemStack.EMPTY;

    public WatercraftFrameBlockEntity(final BlockPos blockPos, final BlockState blockState) {
        super(FirmacivBlockEntities.WATERCRAFT_FRAME_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    public void loadAdditional(final CompoundTag compoundTag) {
        planks = ItemStack.of(compoundTag.getCompound("Planks"));
        bolts = ItemStack.of(compoundTag.getCompound("Bolts"));
    }

    @Override
    public void saveAdditional(final CompoundTag compoundTag) {
        compoundTag.put("Planks", planks.serializeNBT());
        compoundTag.put("Bolts", bolts.serializeNBT());
        super.saveAdditional(compoundTag);
    }

    @Override
    public final void load(final CompoundTag compoundTag) {
        this.loadAdditional(compoundTag);
        super.load(compoundTag);
    }

    /**
     * Ejects the contents for this block
     */
    public void ejectContents() {
        assert this.level != null : "Level should not be null";
        Helpers.spawnItem(this.level, this.worldPosition, planks);
        Helpers.spawnItem(this.level, this.worldPosition, bolts);
    }

    /**
     * Adds the passed in stack to our plank total
     *
     * @param itemStack Stack of planks to add
     */
    public void insertPlanks(final ItemStack itemStack) {
        if (planks.isEmpty()) {
            planks = itemStack.copy();
        } else planks.grow(itemStack.getCount());
    }

    /**
     * Adds the passed in stack to our bolts total.
     *
     * @param itemStack Stack of bolts to add
     */
    public void insertBolts(final ItemStack itemStack) {
        if (bolts.isEmpty()) {
            bolts = itemStack.copy();
        } else bolts.grow(itemStack.getCount());
    }

    /**
     * Extracts planks from the amount we have
     */
    public ItemStack extractPlanks(final int amount) {
        return planks.split(amount);
    }

    /**
     * Extracts bolts from the amount we have
     */
    public ItemStack extractBolts(final int amount) {
        return bolts.split(amount);
    }
}