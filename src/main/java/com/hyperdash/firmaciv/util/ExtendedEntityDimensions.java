package com.hyperdash.firmaciv.util;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ExtendedEntityDimensions {
    public final float length;
    public final float width;

    public final float height;
    public final boolean fixed;

    public ExtendedEntityDimensions(float pWidth, float pHeight, float pLength, boolean pFixed) {
        this.length = pLength;
        this.width = pWidth;

        this.height = pHeight;
        this.fixed = pFixed;
    }

    public AABB makeBoundingBox(Vec3 pPos) {
        return this.makeBoundingBox(pPos.x, pPos.y, pPos.z);
    }

    public AABB makeBoundingBox(double pX, double pY, double pZ) {
        float distanceFrom = this.width / 2.0F;
        float f1 = this.height;
        return new AABB(pX - (double)distanceFrom, pY, pZ - (double)distanceFrom, pX + (double)distanceFrom, pY + (double)f1, pZ + (double)distanceFrom);
    }

    public static EntityDimensions scalable(float pWidth, float pHeight) {
        return new EntityDimensions(pWidth, pHeight, false);
    }

    public static EntityDimensions fixed(float pWidth, float pHeight) {
        return new EntityDimensions(pWidth, pHeight, true);
    }

    public String toString() {
        return "EntityDimensions w=" + this.width + ", h=" + this.height + ", fixed=" + this.fixed;
    }
}
