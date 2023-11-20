package com.hyperdash.firmaciv.entity.custom;


import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.item.FirmacivItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class RowboatEntity extends FirmacivBoatEntity {

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(RowboatEntity.class, EntityDataSerializers.INT);

    public RowboatEntity(final EntityType<? extends FirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);

        final String name = entityType.toString().split("rowboat.")[1];

        this.entityData.define(DATA_ID_TYPE, BoatVariant.byName(name).ordinal());
    }

    @Override
    public Item getDropItem() {
        return getVariant().getLumber().get();
    }

    public void setType(final BoatVariant boatVariant) {
        this.entityData.set(DATA_ID_TYPE, boatVariant.ordinal());
    }

    public BoatVariant getVariant() {
        return BoatVariant.byId(this.entityData.get(DATA_ID_TYPE));
    }


    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/rowboat/" + getVariant().getName() + ".png");
    }

}
