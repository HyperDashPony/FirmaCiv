package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.ContainerCompartmentEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class ShulkerBoxCompartmentEntity extends ContainerCompartmentEntity implements IEntityAdditionalSpawnData {

    public static final byte CONTAINER_OPEN = 1;
    public static final byte CONTAINER_CLOSE = 2;
    private static final int NULL_COLOR = -1;
    private final ChestLidController chestLidController = new ChestLidController();
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(final Level level, final BlockPos blockPos, final BlockState blockState) {
            ShulkerBoxCompartmentEntity.this.playSound(SoundEvents.SHULKER_BOX_OPEN);
        }

        @Override
        protected void onClose(final Level level, final BlockPos blockPos, final BlockState blockState) {
            ShulkerBoxCompartmentEntity.this.playSound(SoundEvents.SHULKER_BOX_CLOSE);
        }

        @Override
        protected void openerCountChanged(final Level level, final BlockPos blockPos, final BlockState blockState,
                final int count, final int openCount) {
            ShulkerBoxCompartmentEntity.this.signalOpenCount(level, openCount);
        }

        @Override
        protected boolean isOwnContainer(final Player player) {
            if (!(player.containerMenu instanceof ShulkerBoxMenu)) return false;

            final Container container = ((ShulkerBoxMenu) player.containerMenu).container;
            return container == ShulkerBoxCompartmentEntity.this;
        }
    };
    @Nullable
    private DyeColor color;

    public ShulkerBoxCompartmentEntity(final EntityType<? extends ContainerCompartmentEntity> entityType,
            final Level level) {
        super(entityType, level, 27);
    }

    public ShulkerBoxCompartmentEntity(final CompartmentType<? extends ContainerCompartmentEntity> entityType,
            final Level level, final ItemStack itemStack) {
        this(entityType, level);

        if (itemStack.getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof ShulkerBoxBlock shulkerBoxBlock) {
                this.color = shulkerBoxBlock.getColor();
                this.setDisplayBlockState(shulkerBoxBlock.defaultBlockState());
                final CompoundTag blockEntityTag = itemStack.getTagElement("BlockEntityTag");
                if (blockEntityTag != null) ContainerHelper.loadAllItems(blockEntityTag, this.getItemStacks());
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        this.chestLidController.tickLid();

        if (!this.isRemoved() && this.level().isClientSide()) {
            this.openersCounter.recheckOpeners(this.level(), this.blockPosition(), this.getDisplayBlockState());
        }
    }

    @Override
    public void remove(final RemovalReason removalReason) {
        if (!this.level().isClientSide && removalReason.shouldDestroy()) {
            Containers.dropItemStack(this.level(), this.getX(), this.getY(), this.getZ(), this.getDropStack());
            this.playSound(SoundEvents.STONE_BREAK);
        }

        this.setRemoved(removalReason);
        this.invalidateCaps();
    }

    @Override
    public void handleEntityEvent(final byte dataID) {
        switch (dataID) {
            case CONTAINER_OPEN -> this.chestLidController.shouldBeOpen(true);
            case CONTAINER_CLOSE -> this.chestLidController.shouldBeOpen(false);
        }

        super.handleEntityEvent(dataID);
    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.color == null) {
            compoundTag.putInt("Color", NULL_COLOR);
        } else {
            compoundTag.putInt("Color", color.getId());
        }
    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        final int colorID = compoundTag.getInt("Color");

        if (NULL_COLOR != colorID) {
            this.color = DyeColor.byId(colorID);
        }
    }

    @Override
    public void writeSpawnData(final FriendlyByteBuf buffer) {
        buffer.writeByte(this.color == null ? NULL_COLOR : color.getId());
    }

    @Override
    public void readSpawnData(final FriendlyByteBuf additionalData) {
        final byte colorID = additionalData.readByte();
        if (NULL_COLOR != colorID) {
            this.color = DyeColor.byId(colorID);
        }
    }

    @Override
    public void startOpen(final Player player) {
        if (!this.isRemoved() && !player.isSpectator() || !this.isPassenger()) {
            this.openersCounter.incrementOpeners(player, this.level(), this.blockPosition(),
                    this.getDisplayBlockState());
        }
    }

    @Override
    public void stopOpen(final Player player) {
        if (!this.isRemoved() && !player.isSpectator() || !this.isPassenger()) {
            this.openersCounter.decrementOpeners(player, this.level(), this.blockPosition(),
                    this.getDisplayBlockState());
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(final int id, final Inventory playerInventory) {
        return new ShulkerBoxMenu(id, playerInventory, this);
    }

    @Override
    public ItemStack getDropStack() {
        final ItemStack dropStack = new ItemStack(ShulkerBoxBlock.getBlockByColor(this.color));
        final CompoundTag compoundTag = new CompoundTag();

        ContainerHelper.saveAllItems(compoundTag, this.getItemStacks(), false);

        if (compoundTag.isEmpty()) {
            dropStack.removeTagKey("BlockEntityTag");
        } else {
            dropStack.addTagElement("BlockEntityTag", compoundTag);
        }

        return dropStack;
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ShulkerBoxBlock.getBlockByColor(this.color));
    }

    public float getOpenNess(final float partialTicks) {
        return this.chestLidController.getOpenness(partialTicks);
    }

    private void signalOpenCount(final Level level, final int openCount) {
        level.broadcastEntityEvent(this, openCount > 0 ? CONTAINER_OPEN : CONTAINER_CLOSE);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Nullable
    public DyeColor getColor() {
        return this.color;
    }
}