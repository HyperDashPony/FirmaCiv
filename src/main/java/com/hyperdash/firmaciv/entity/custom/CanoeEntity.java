package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.block.custom.CanoeComponentBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Locale;

public class CanoeEntity extends FirmacivBoatEntity{
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(CanoeEntity.class, EntityDataSerializers.INT);
    public enum Type {
        DOUGLAS_FIR( ),
        PINE(),
        CHESTNUT(),
        SPRUCE(),
        ASPEN(),
        KAPOK(),
        ROSEWOOD(),
        WHITE_CEDAR(),
        WILLOW();

        //private final String name;

        private Type() {
        }

        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public static CanoeEntity.Type byId(int pId) {
            CanoeEntity.Type[] acanoe$type = values();
            if (pId < 0 || pId >= acanoe$type.length) {
                pId = 0;
            }

            return acanoe$type[pId];
        }

        public static CanoeEntity.Type byName(String pName) {
            CanoeEntity.Type[] acanoe$type = values();

            for(int i = 0; i < acanoe$type.length; ++i) {
                if (acanoe$type[i].getName().equals(pName)) {
                    return acanoe$type[i];
                }
            }

            return acanoe$type[0];
        }

    }

    public void setType(CanoeEntity.Type pCanoeType) {
        this.entityData.set(DATA_ID_TYPE, pCanoeType.ordinal());
    }

    public CanoeEntity.Type getCanoeType() {
        return CanoeEntity.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    public Item getDropItem() {
        return null;
    }

    public CanoeEntity(EntityType<? extends FirmacivBoatEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.entityData.define(DATA_ID_TYPE, CanoeEntity.Type.DOUGLAS_FIR.ordinal());
    }

    public static ModelLayerLocation createCanoeModelName(CanoeEntity.Type pType) {
        return new ModelLayerLocation(new ResourceLocation(Firmaciv.MOD_ID, "watercraft/dugout_canoe/" + pType.getName()), "main");
    }

    @Override
    public ItemStack getPickResult() {
        return null;
    }

    public ResourceLocation getTextureLocation(){
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/" + getCanoeType().getName() + ".png");
    }

}
