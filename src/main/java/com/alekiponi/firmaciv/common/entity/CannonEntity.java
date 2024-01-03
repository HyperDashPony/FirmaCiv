package com.alekiponi.firmaciv.common.entity;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCollisionEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import com.alekiponi.firmaciv.network.PacketHandler;
import com.alekiponi.firmaciv.network.ServerboundSwitchEntityPacket;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CannonEntity extends Entity {
    protected int lerpSteps;
    protected double lerpX;
    protected double lerpY;
    protected double lerpZ;
    protected double lerpYRot;
    protected double lerpXRot;

    public CannonEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick(){
        if (!this.isPassenger()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
            if (this.isInWater() || this.level().getFluidState(this.blockPosition())
                    .is(TFCFluids.SALT_WATER.getSource())) {
                this.setDeltaMovement(0.0D, -0.01D, 0.0D);
                this.setYRot(this.getYRot() + 0.4f);
            }
            if (!this.onGround() || this.getDeltaMovement()
                    .horizontalDistanceSqr() > (double) 1.0E-5F || (this.tickCount + this.getId()) % 4 == 0) {
                this.move(MoverType.SELF, this.getDeltaMovement());
                float f1 = 0.98F;

                this.setDeltaMovement(this.getDeltaMovement().multiply(f1, 0.98D, f1));
                if (this.onGround()) {
                    Vec3 vec31 = this.getDeltaMovement();
                    if (vec31.y < 0.0D) {
                        this.setDeltaMovement(vec31.multiply(1.0D, -0.5D, 1.0D));
                    }
                }
            }

            this.updateInWaterStateAndDoFluidPushing();
        }
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        this.fire();
        return InteractionResult.SUCCESS;

    }

    public void fire(){
        final CannonballEntity cannonball = FirmacivEntities.CANNONBALL_ENTITY.get()
                .create(this.level());
        assert cannonball != null;
        cannonball.setPos(this.getX(), this.getY(), this.getZ());

        cannonball.setDeltaMovement((Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * 3.0), 0.0D,
                Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * 3.0);

        cannonball.setDeltaMovement(cannonball.getDeltaMovement().add(0,0.3,0));

        this.playSound(SoundEvents.GENERIC_EXPLODE, 1.5f, this.level().getRandom().nextFloat() * 0.05F + 0.01F);

        if(!this.level().getBlockState(cannonball.blockPosition()).isAir()){
            cannonball.explode(2);
            cannonball.discard();
        }

        BlockPos blockpos = this.blockPosition();
        Direction thisDir = Direction.orderedByNearest(this)[0];
        for(int i = 0; i < 5; i++){
            if(!this.level().getBlockState(blockpos.relative(thisDir)).isAir()){
                cannonball.explode(2);
                cannonball.discard();
            }
        }
        cannonball.setPos(this.getPosition(0).add(cannonball.getDeltaMovement()).add(0,0.3,0));

        this.level().addFreshEntity(cannonball);
    }

    protected void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double) this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double) this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double) this.lerpSteps;
            double d3 = Mth.wrapDegrees(this.lerpYRot - (double) this.getYRot());
            this.setYRot(this.getYRot() + (float) d3 / (float) this.lerpSteps);
            this.setXRot(this.getXRot() + (float) (this.lerpXRot - (double) this.getXRot()) / (float) this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d1, d2);
            //this.setRot(this.getYRot(), this.getXRot());
        }
    }

    @Override
    public void lerpTo(final double posX, final double posY, final double posZ, final float yaw, final float pitch,
                       final int pPosRotationIncrements, final boolean teleport) {
        this.lerpX = posX;
        this.lerpY = posY;
        this.lerpZ = posZ;
        this.lerpYRot = yaw;
        this.lerpXRot = pitch;
        this.lerpSteps = 10;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }
}
