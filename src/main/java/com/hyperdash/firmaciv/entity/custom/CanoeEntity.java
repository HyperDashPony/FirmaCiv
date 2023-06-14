package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.block.custom.CanoeComponentBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Locale;
import java.util.function.Supplier;

public class CanoeEntity extends FirmacivBoatEntity{
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(CanoeEntity.class, EntityDataSerializers.INT);
    public enum Type {
        ACACIA(TFCItems.LUMBER.get(Wood.ACACIA)),
        ASH(TFCItems.LUMBER.get(Wood.ASH)),
        ASPEN(TFCItems.LUMBER.get(Wood.ASPEN)),
        BIRCH(TFCItems.LUMBER.get(Wood.BIRCH)),
        BLACKWOOD(TFCItems.LUMBER.get(Wood.BLACKWOOD)),
        CHESTNUT(TFCItems.LUMBER.get(Wood.CHESTNUT)),
        DOUGLAS_FIR(TFCItems.LUMBER.get(Wood.DOUGLAS_FIR)),
        HICKORY(TFCItems.LUMBER.get(Wood.HICKORY)),
        KAPOK(TFCItems.LUMBER.get(Wood.KAPOK)),
        MAPLE(TFCItems.LUMBER.get(Wood.MAPLE)),
        OAK(TFCItems.LUMBER.get(Wood.OAK)),
        PALM(TFCItems.LUMBER.get(Wood.PALM)),
        PINE(TFCItems.LUMBER.get(Wood.PINE)),
        ROSEWOOD(TFCItems.LUMBER.get(Wood.ROSEWOOD)),
        SEQUOIA(TFCItems.LUMBER.get(Wood.SEQUOIA)),
        SPRUCE(TFCItems.LUMBER.get(Wood.SPRUCE)),
        SYCAMORE(TFCItems.LUMBER.get(Wood.SYCAMORE)),
        WHITE_CEDAR(TFCItems.LUMBER.get(Wood.WHITE_CEDAR)),
        WILLOW(TFCItems.LUMBER.get(Wood.WILLOW));

        //private final String name;

        private Type(Supplier<? extends Item> lumber) {
            this.lumber = lumber;
        }

        public final Supplier<? extends Item> lumber;

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


    public final int PASSENGER_NUMBER = 2;
    public void setType(CanoeEntity.Type pCanoeType) {
        this.entityData.set(DATA_ID_TYPE, pCanoeType.ordinal());
    }

    public CanoeEntity.Type getCanoeType() {
        return CanoeEntity.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    public Item getDropItem() {
        return getCanoeType().lumber.get();
    }

    public CanoeEntity(EntityType<? extends FirmacivBoatEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        String name = pEntityType.getRegistryName().getPath().split("/")[1];
        this.entityData.define(DATA_ID_TYPE, CanoeEntity.Type.byName(name).ordinal());
    }

    public static ModelLayerLocation createCanoeModelName(CanoeEntity.Type pType) {
        return new ModelLayerLocation(new ResourceLocation(Firmaciv.MOD_ID, "watercraft/dugout_canoe/" + pType.getName()), "main");
    }

    protected boolean canAddPassenger(Entity pPassenger) {
        return this.getPassengers().size() < PASSENGER_NUMBER && !this.isEyeInFluid(FluidTags.WATER);
    }

    @Override
    public ItemStack getPickResult() {
        return null;
    }

    public ResourceLocation getTextureLocation(){
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/dugout_canoe/" + getCanoeType().getName() + ".png");
    }

}
