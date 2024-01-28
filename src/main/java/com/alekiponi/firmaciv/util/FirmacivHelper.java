package com.alekiponi.firmaciv.util;

import com.alekiponi.firmaciv.common.block.FirmacivBlocks;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractVehicle;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.MastEntity;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistryWood;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static net.minecraftforge.fml.loading.FMLEnvironment.dist;

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

    public static void tickTakeEntitiesForARide(AbstractVehicle thisVehicle){
        if(thisVehicle instanceof AbstractFirmacivBoatEntity && (thisVehicle.getStatus() == AbstractVehicle.Status.UNDER_WATER || thisVehicle.getStatus() == AbstractVehicle.Status.UNDER_FLOWING_WATER)){
            return;
        }
        if(thisVehicle.getDeltaMovement().length() > 0.01){
            List<Entity> entitiesToTakeWith = thisVehicle.getEntitiesToTakeWith();

            if (!entitiesToTakeWith.isEmpty()) {
                for (final Entity entity : entitiesToTakeWith) {
                    if (thisVehicle.level().isClientSide() && entity instanceof Player player && !entity.isPassenger() && (Math.abs(thisVehicle.getBoundingBox().maxY-player.getBoundingBox().minY) < 0.01)) {
                        if(thisVehicle.getSmoothSpeedMS() > 4){
                            if(Math.abs(player.getDeltaMovement().y) > 0.05 ){
                                player.setPos(thisVehicle.getDismountLocationForPassenger(player));
                            }
                            //player.setPos(player.getPosition(0).add(this.getDeltaMovement()));
                        } else {
                            if(player.xxa > 0 || player.zza > 0 || player.yya > 0 || Math.abs(player.getDeltaMovement().y) > 0.05){
                                player.setDeltaMovement(player.getDeltaMovement().multiply(1.0,1,1.0).add(thisVehicle.getDeltaMovement().multiply(0.45,0,0.45)));
                            } else {
                                player.setPos(player.getPosition(0).add(thisVehicle.getDeltaMovement()));
                                if(player.getDeltaMovement().length() > thisVehicle.getDeltaMovement().length()+0.01){
                                    player.setDeltaMovement(Vec3.ZERO);
                                }
                            }
                        }

                    }
                    if (!(entity instanceof AbstractVehicle) && !entity.isPassenger() && !(entity instanceof Player))  {
                        entity.setDeltaMovement(entity.getDeltaMovement().add(thisVehicle.getDeltaMovement().multiply(0.45, 0, 0.45)));
                    }
                }
            }
        }
    }

    public static void tickClimbMast(MastEntity thisMast){
        List<net.minecraft.world.entity.Entity> playersToMoveWithMast = new ArrayList<Entity>();

        playersToMoveWithMast.addAll(thisMast.level()
                .getEntities(thisMast, thisMast.getBoundingBox().inflate(0, 0, 0).move(0, 0, 0), EntitySelector.pushableBy(thisMast)));

        for (Entity entity : playersToMoveWithMast) {
            if ((entity instanceof LocalPlayer player)) {
                player.move(MoverType.SELF, thisMast.getRootVehicle().getDeltaMovement().multiply(1, 0, 1).add(0,0,0));
                if (player.input.jumping) {
                    player.setDeltaMovement(player.getDeltaMovement().multiply(1,0,1).add(0,0.1,0));
                } else if(player.input.shiftKeyDown){
                    player.setDeltaMovement(player.getDeltaMovement().multiply(1,0,1).add(0,0,0));
                } else {
                    player.setDeltaMovement(player.getDeltaMovement().multiply(1,0,1).add(0,-0.1,0));
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


}