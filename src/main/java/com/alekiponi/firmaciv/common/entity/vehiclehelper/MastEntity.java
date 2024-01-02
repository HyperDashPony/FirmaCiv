package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper.InvisibleHelperRenderer;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class MastEntity extends AbstractInvisibleHelper {
    public MastEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick(){
        super.tick();
        if (!this.isPassenger()) {
            this.kill();
        }

        if(this.level().isClientSide()){
            /*
            final List<Entity> playersToAllowClimbing = this.level()
                    .getEntities(this, this.getBoundingBox().inflate(0.0, 0.0, 0.0), EntitySelector.pushableBy(this));

            if (!playersToAllowClimbing.isEmpty()) {
                for (final Entity entity : playersToAllowClimbing) {
                    if (entity instanceof LocalPlayer player) {
                        if (player.input.jumping) {
                            player.setPos(player.getPosition(0).add(0,0.1,0));
                            //SoundEvents.LADDER_STEP
                        }
                    }
                }
            }*/

            List<net.minecraft.world.entity.Entity> playersToMoveWithMast = new ArrayList<Entity>();

            playersToMoveWithMast.addAll(this.level()
                    .getEntities(this, this.getBoundingBox().inflate(0, 0, 0).move(0, 0, 0), EntitySelector.pushableBy(this)));

            for (Entity entity : playersToMoveWithMast) {
                if ((entity instanceof LocalPlayer player)) {
                    player.move(MoverType.SELF, this.getRootVehicle().getDeltaMovement().multiply(1, 0, 1).add(0,0,0));
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

    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
}
