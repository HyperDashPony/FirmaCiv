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

    public static enum Type {
        OAK(Blocks.OAK_PLANKS, "oak");

        private final String name;
        private final Block planks;

        private Type(Block pPlanks, String pName) {
            this.name = pName;
            this.planks = pPlanks;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        public String toString() {
            return this.name;
        }

        /**
         * Get a boat type by its enum ordinal
         */
        public static FirmacivBoatEntity.Type byId(int pId) {
            FirmacivBoatEntity.Type[] aFirmacivBoat$type = values();
            if (pId < 0 || pId >= aFirmacivBoat$type.length) {
                pId = 0;
            }

            return aFirmacivBoat$type[pId];
        }

        public static FirmacivBoatEntity.Type byName(String pName) {
            FirmacivBoatEntity.Type[] aFirmacivBoat$type = values();

            for (int i = 0; i < aFirmacivBoat$type.length; ++i) {
                if (aFirmacivBoat$type[i].getName().equals(pName)) {
                    return aFirmacivBoat$type[i];
                }
            }

            return aFirmacivBoat$type[0];
        }
    }

}
