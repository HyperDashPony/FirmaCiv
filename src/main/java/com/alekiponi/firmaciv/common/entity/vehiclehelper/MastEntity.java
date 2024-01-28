package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import static com.alekiponi.firmaciv.util.FirmacivHelper.tickClimbMast;
import static net.minecraftforge.fml.loading.FMLEnvironment.dist;

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

        if(dist.isClient() && this.level().isClientSide()){
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
            tickClimbMast(this);

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
