package com.alekiponi.firmaciv.util;

import com.alekiponi.firmaciv.common.block.FirmacivBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistryWood;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

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

    public static double vec2ToWrappedDegrees(Vec2 vec2) {
        double x = vec2.normalized().x;
        double y = vec2.normalized().y;
        double direction = 0;
        if (y != 0 && x != 0) {
            direction = Math.round(Math.toDegrees(Math.atan(y / x)));
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

    public static float sailForceMultiplierTable(float sailForceAngle) {
        sailForceAngle = (Math.abs(Mth.wrapDegrees(sailForceAngle)));
        float multiplier = 0;
        if (sailForceAngle < 15) {
            multiplier = 0f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 20) {
            multiplier = 8f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 25) {
            multiplier = 15f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 30) {
            multiplier = 20f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 45) {
            multiplier = 23f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 60) {
            multiplier = 27f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 75) {
            multiplier = 32f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 90) {
            multiplier = 35f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 105) {
            multiplier = 32f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 120) {
            multiplier = 30f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 135) {
            multiplier = 27f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 150) {
            multiplier = 20f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 165) {
            multiplier = 10f;
            return multiplier / 35f;
        }
        if (sailForceAngle < 180) {
            multiplier = 0f;
            return multiplier / 35f;
        }
        return multiplier / 35f;
    }


    public static void tickHopPlayersOnboard(Entity thisEntity){
        if(thisEntity.level().isClientSide()){
            final List<Entity> entitiesToHop = thisEntity.level()
                    .getEntities(thisEntity, thisEntity.getBoundingBox().inflate(0.1, -0.01, 0.1), EntitySelector.pushableBy(thisEntity));

            if (!entitiesToHop.isEmpty()) {
                for (final Entity entity : entitiesToHop) {
                    if (entity instanceof LocalPlayer player && !player.isPassenger()) {
                        if (player.input.jumping) {
                            Vec3 newPlayerPos = player.getPosition(0).multiply(1, 0, 1);
                            newPlayerPos = newPlayerPos.add(0, thisEntity.getY() + thisEntity.getBoundingBox().getYsize() + 0.05, 0);
                            newPlayerPos = newPlayerPos.add((thisEntity.getX() - newPlayerPos.x()) * 0.2, 0, (thisEntity.getZ() - newPlayerPos.z()) * 0.2);
                            player.setPos(newPlayerPos);
                        }
                    }
                }
            }
        }

    }

    public static boolean everyNthTickUnique(int id, int tickCount, int n){
        if((id + tickCount) % n == 0){
            return true;
        }
        return false;
    }

    /**
     * Utility function to centralize all the mod interop relating to TFC woods.
     * This should be used anywhere we want to iterate over all the supported TFC woods
     *
     * @param woodConsumer A consumer that is passed each {@link RegistryWood}
     */
    public static void forAllTFCWoods(final Consumer<RegistryWood> woodConsumer) {
        for (final Wood tfcWood : Wood.values()) {
            woodConsumer.accept(tfcWood);
        }
    }

    /**
     * Creates a map for every TFC wood. See {@link FirmacivHelper#forAllTFCWoods(Consumer)} if you just want to
     * iterate over the wood types we support
     *
     * @param function   The function that's used to get the entry for each wood type. See
     *                   {@link FirmacivBlocks#WOODEN_BOAT_FRAME_ANGLED} for example usage
     * @param <MapValue> The value type of the map
     * @return A map of {@link RegistryWood} to the returned object of the function for that wood type
     */
    public static <MapValue> Map<RegistryWood, MapValue> TFCWoodMap(final Function<RegistryWood, MapValue> function) {
        final Map<RegistryWood, MapValue> map = new HashMap<>();

        forAllTFCWoods(wood -> map.put(wood, function.apply(wood)));

        return map;
    }
}