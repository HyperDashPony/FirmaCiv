package com.hyperdash.firmaciv.entity.custom;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.FirmacivEntities;
import com.hyperdash.firmaciv.item.FirmacivItems;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class KayakEntity extends FirmacivBoatEntity {

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(KayakEntity.class, EntityDataSerializers.INT);

    public KayakEntity(EntityType<? extends FirmacivBoatEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void tick() {
        this.oldStatus = this.status;
        this.status = super.getStatus();
        if (this.status != FirmacivBoatEntity.Status.UNDER_WATER && this.status != FirmacivBoatEntity.Status.UNDER_FLOWING_WATER) {
            this.outOfControlTicks = 0.0F;
        } else {
            ++this.outOfControlTicks;
        }

        if (!this.level().isClientSide && this.outOfControlTicks >= 60.0F) {
            this.ejectPassengers();
        }

        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

        super.tick();

        if(this.status == Status.IN_WATER && !this.getPassengers().isEmpty()){
            if(Math.abs(this.deltaRotation) > 2){
                this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double)this.random.nextFloat(), this.getY() + 0.7D, this.getZ() + (double)this.random.nextFloat(), 0.0D, 0.0D, 0.0D);
                if (this.random.nextInt(20) == 0) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimSound(), this.getSoundSource(), 0.2F, 0.8F + 0.4F * this.random.nextFloat(), false);
                }
                if(Math.abs(this.deltaRotation) > 5 && (this.inputRight || this.inputLeft)){
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimHighSpeedSplashSound(), this.getSoundSource(), 0.2F, 0.8F + 0.4F * this.random.nextFloat(), false);


                    Vec3 splashOffset = this.getDeltaMovement().yRot(45);
                    if(this.inputLeft){
                        splashOffset = this.getDeltaMovement().yRot(-45);
                    }
                    splashOffset.normalize();

                    for(int i = 0; i < 8; i++){
                        this.level().addParticle(ParticleTypes.BUBBLE_POP, this.getX() + (double)this.random.nextFloat() + splashOffset.x*2 + this.getDeltaMovement().x*i, this.getY() + 0.7D, this.getZ() + (double)this.random.nextFloat() + splashOffset.z*2 + this.getDeltaMovement().x*i, 0.0D, 0.0D, 0.0D);
                        this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double)this.random.nextFloat() + splashOffset.x*2 + this.getDeltaMovement().x*i, this.getY() + 0.7D, this.getZ() + (double)this.random.nextFloat() + splashOffset.z*2 + this.getDeltaMovement().x*i, 0.0D, 0.0D, 0.0D);
                    }
                }

            } else if(this.getDeltaMovement().length() > 0.10){
                if (this.random.nextInt(8) == 0) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimSound(), this.getSoundSource(), 0.1F, 0.8F + 0.4F * this.random.nextFloat(), false);
                    this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double)this.random.nextFloat(), this.getY() + 0.7D, this.getZ() + (double)this.random.nextFloat(), 0.0D, 0.0D, 0.0D);
                }
            }
        }

        this.tickLerp();
        if (this.isControlledByLocalInstance()) {
            if (!(this.getFirstPassenger() instanceof Player)) {
                this.setPaddleState(false, false);
            }

            this.floatBoat();
            if (this.level().isClientSide) {
                this.controlBoat();
                this.level().sendPacketToServer(new ServerboundPaddleBoatPacket(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            this.setDeltaMovement(Vec3.ZERO);
        }

        super.tickBubbleColumn();

        for(int i = 0; i <= 1; ++i) {
            if (this.getPaddleState(i)) {
                if (!this.isSilent() && (double)(this.paddlePositions[i] % ((float)Math.PI * 2F)) <= (double)((float)Math.PI / 4F) && (double)((this.paddlePositions[i] + ((float)Math.PI / 8F)) % ((float)Math.PI * 2F)) >= (double)((float)Math.PI / 4F)) {
                    SoundEvent soundevent = this.getPaddleSound();
                    if (soundevent != null) {
                        Vec3 vec3 = this.getViewVector(1.0F);
                        double d0 = i == 1 ? -vec3.z : vec3.z;
                        double d1 = i == 1 ? vec3.x : -vec3.x;
                        this.level().playSound((Player)null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent, this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat());
                        this.level().gameEvent(this.getControllingPassenger(), GameEvent.SPLASH, new BlockPos((int)(this.getX() + d0), (int)this.getY(), (int)(this.getZ() + d1)));
                    }
                }

                this.paddlePositions[i] += ((float)Math.PI / 8F);
            } else {
                this.paddlePositions[i] = 0.0F;
            }
        }

        this.checkInsideBlocks();
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate((double)0.2F, (double)-0.01F, (double)0.2F), EntitySelector.pushableBy(this));
        if (!list.isEmpty()) {
            boolean flag = !this.level().isClientSide && !(this.getControllingPassenger() instanceof Player);

            for(int j = 0; j < list.size(); ++j) {
                Entity entity = list.get(j);
                if (!entity.hasPassenger(this)) {
                    if (flag && this.getPassengers().size() < 2 && !entity.isPassenger() && entity.getBbWidth() < this.getBbWidth() && entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity instanceof Player)) {
                        entity.startRiding(this);
                    } else {
                        this.push(entity);
                    }
                }
            }
        }

    }


    protected final float DAMAGE_THRESHOLD = 10.0f;
    protected final float DAMAGE_RECOVERY = 1.0f;

    protected float getDamageThreshold(){
        return this.DAMAGE_THRESHOLD;
    }

    protected float getDamageRecovery(){
        return this.DAMAGE_RECOVERY;
    }

    public final int PASSENGER_NUMBER = 1;

    protected void controlBoat() {
        if (this.isVehicle() && this.getPassengers().get(0) instanceof Player) {
            if(((Player) this.getPassengers().get(0)).isHolding(FirmacivItems.KAYAK_PADDLE.get())){
                float f = 0.0F;
                if (this.inputLeft) {
                    --this.deltaRotation;
                }

                if (this.inputRight) {
                    ++this.deltaRotation;
                }

                if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
                    f += 0.005F;
                }

                this.setYRot(this.getYRot() + this.deltaRotation);
                if (this.inputUp) {
                    f += 0.055F;
                }

                if (this.inputDown) {
                    f -= 0.025F;
                }

                this.setDeltaMovement(this.getDeltaMovement().add(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f, 0.0D, Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f));
                this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
            } else {
                float f = 0.0F;
                if (this.inputLeft) {
                    --this.deltaRotation;
                }

                if (this.inputRight) {
                    ++this.deltaRotation;
                }

                if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
                    f += 0.002F;
                }

                this.setYRot(this.getYRot() + this.deltaRotation);
                if (this.inputUp) {
                    f += 0.02F;
                }

                if (this.inputDown) {
                    f -= 0.01F;
                }

                this.setDeltaMovement(this.getDeltaMovement().add(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f, 0.0D, Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f));
                this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
            }
        }
    }

    @Override
    public Item getDropItem() {
        return FirmacivItems.KAYAK.get();
    }

    protected boolean canAddPassenger(Entity pPassenger) {
        return this.getPassengers().size() < PASSENGER_NUMBER && !this.isEyeInFluid(FluidTags.WATER) && pPassenger instanceof Player;
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(FirmacivItems.KAYAK.get());
    }

    public ResourceLocation getTextureLocation(){
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/kayak.png");
    }


}
