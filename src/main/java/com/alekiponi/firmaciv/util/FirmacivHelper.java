package com.alekiponi.firmaciv.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FirmacivHelper {
    @Nullable
    public static Entity getAnyEntityAtCrosshair(Entity entity, double range) {
        Vec3 from = entity.getEyePosition(1);
        Vec3 view = entity.getViewVector(1);
        Vec3 to = from.add(view.x * range, view.y * range, view.z * range);
        AABB aabb = entity.getBoundingBox().expandTowards(view.scale(range)).inflate(1, 1, 1);
        EntityHitResult hit = ProjectileUtil.getEntityHitResult(entity, from, to, aabb,
                e -> !e.isSpectator() && e.isPickable(), range * range);
        return hit != null && from.distanceTo(hit.getLocation()) < range ? hit.getEntity() : null;
    }

    public static double vec2ToWrappedDegrees(Vec2 vec2){
        double x = vec2.normalized().x;
        double y = vec2.normalized().y;
        double direction = 0;
        //direction = Math.round(Math.toDegrees(Math.atan2(x, y)));

        if(x == 0 && y > 0){
            direction = 0;
        } else if(x == 0 && y < 0){
            direction = 180;
        } else if (y == 0 && x > 0) {
            direction = -90;
        } else if(y == 0 && x < 0){
            direction = 90;
        } else if(y != 0 && x != 0){
            direction = Math.round(Math.toDegrees(Math.atan(x / y)));
            //quadrant correction because probably I'm bad at math?
            if (x <= 0 && y <= 0) {
                direction += 90;
            } else if (x >= 0 && y >= 0) {
                direction -= 90;
            } else if (x <= 0 && y >= 0) {
                direction += 90;
            } else if (x >= 0 && y <= 0) {
                direction -= 90;
            }
        }

        return Mth.wrapDegrees(direction);
    }

}
