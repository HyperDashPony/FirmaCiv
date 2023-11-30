package com.alekiponi.firmaciv.common.blockentity;

import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.util.FirmacivTags;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class WatercraftFrameBlockEntity extends BlockEntity {

    public static final int plankSlot = 0;
    public static final int boltSlot = 1;
    /**
     * ItemHandler to manage our two stacks as it's convenient
     */
    private final ItemStackHandler frameContents = new ItemStackHandler(2) {
        @Override
        public boolean isItemValid(final int slotIndex, final ItemStack itemStack) {
            if (plankSlot == slotIndex) {
                return itemStack.is(FirmacivTags.Items.PLANKS);
            }

            if (boltSlot == slotIndex) {
                return itemStack.is(FirmacivItems.COPPER_BOLT.get());
            }

            return super.isItemValid(slotIndex, itemStack);
        }
    };

    public WatercraftFrameBlockEntity(final BlockPos blockPos, final BlockState blockState) {
        super(FirmacivBlockEntities.WATERCRAFT_FRAME_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public void saveAdditional(final CompoundTag compoundTag) {
        compoundTag.put("Contents", frameContents.serializeNBT());
        super.saveAdditional(compoundTag);
    }

    @Override
    public final void load(final CompoundTag compoundTag) {
        frameContents.deserializeNBT(compoundTag.getCompound("Contents"));
        super.load(compoundTag);
    }

    /**
     * Ejects the contents for this block
     */
    public void ejectContents() {
        assert this.level != null : "Level should not be null";
        for (int slotIndex = 0; slotIndex < frameContents.getSlots(); slotIndex++) {
            Helpers.spawnItem(this.level, this.worldPosition, frameContents.getStackInSlot(slotIndex));
        }
    }

    /**
     * Adds the passed in stack to our plank total
     *
     * @param itemStack Stack of planks to add
     * @return Remainder of the inserted stack
     */
    public ItemStack insertPlanks(final ItemStack itemStack) {
        return frameContents.insertItem(plankSlot, itemStack, false);
    }

    /**
     * Adds the passed in stack to our bolts total.
     *
     * @param itemStack Stack of bolts to add
     * @return Remainder of the inserted stack
     */
    @SuppressWarnings("UnusedReturnValue")
    public ItemStack insertBolts(final ItemStack itemStack) {
        return frameContents.insertItem(boltSlot, itemStack, false);
    }

    /**
     * Extracts planks from the amount we have
     */
    public ItemStack extractPlanks(final int amount) {
        return frameContents.extractItem(plankSlot, amount, false);
    }

    /**
     * Extracts bolts from the amount we have
     */
    public ItemStack extractBolts(final int amount) {
        return frameContents.extractItem(boltSlot, amount, false);
    }
}