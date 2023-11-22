package com.hyperdash.firmaciv.mixins;

import com.hyperdash.firmaciv.common.entity.vehiclehelper.EmptyCompartmentEntity;
import com.hyperdash.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Mob.class)
public class MobMixin extends Entity {

    @Shadow
    private Entity leashHolder;

    @Shadow
    private int delayedLeashHolderId;
    @Nullable
    @Shadow
    private CompoundTag leashInfoTag;

    public MobMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    private void restoreLeashFromSave() {
    }

    @Shadow
    public void dropLeash(boolean pBroadcastPacket, boolean pDropLeash) {
    }

    @Inject(method = "tickLeash", at = @At("HEAD"))
    protected void injectStopLeashing(CallbackInfo ci) {
        if (this.leashHolder != null) {
            if (this.leashHolder.isPassenger() && this.leashHolder.getVehicle() instanceof EmptyCompartmentEntity) {
                this.dropLeash(true, true);
            }
        }
    }

    @Shadow
    protected void defineSynchedData() {

    }

    @Shadow
    public void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Shadow
    public void addAdditionalSaveData(CompoundTag pCompound) {

    }
}
