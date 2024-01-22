package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.util.BoatVariant;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;

public class KayakEntity extends AbstractFirmacivBoatEntity {
    public final int PASSENGER_NUMBER = 1;
    protected final float DAMAGE_THRESHOLD = 10.0f;
    protected final float DAMAGE_RECOVERY = 1.0f;

    protected final float PASSENGER_SIZE_LIMIT = 0.6F;

    public KayakEntity(final EntityType<? extends AbstractFirmacivBoatEntity> entityType, final Level level) {
        super(entityType, level);
    }

    @Override
    public int[] getWindlassIndices() {
        return new int[0];
    }

    @Override
    public float getPassengerSizeLimit() {
        return this.PASSENGER_SIZE_LIMIT;
    }

    @Override
    public int[][] getCompartmentRotationsArray() {
        return new int[0][];
    }

    @Override
    public int[] getCanAddOnlyBlocksIndices() {
        return new int[0];
    }

    @Nullable
    @Override
    public Entity getPilotVehiclePartAsEntity() {
        if (this.isVehicle() && this.getPassengers().size() == this.getMaxPassengers()) {
            return this.getPassengers().get(0);
        }

        return null;
    }

    @Override
    public void tick() {

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - getDamageRecovery());
        }
        super.tick();
    }

    @Override
    public boolean hurt(final DamageSource damageSource, final float amount) {
        if (this.isInvulnerableTo(damageSource)) return false;

        if (this.level().isClientSide || this.isRemoved()) return true;

        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() + amount * 10.0F);
        this.markHurt();
        this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
        final boolean instantKill = damageSource.getEntity() instanceof Player && ((Player) damageSource.getEntity()).getAbilities().instabuild;

        if (instantKill || this.getDamage() > getDamageThreshold()) {
            if (!instantKill && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                this.spawnAtLocation(this.getDropItem());
            }
            this.discard();
        }

        return true;
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public BoatVariant getVariant() {
        return null;
    }

    @Override
    public int getMaxPassengers() {
        return this.PASSENGER_NUMBER;
    }

    @Override
    public int[] getCleatIndices() {
        return new int[0];
    }

    @Override
    public int[] getSailSwitchIndices() {
        return new int[0];
    }

    @Override
    public int[] getMastIndices() {
        return new int[0];
    }

    @Override
    public int[] getCanAddCannonsIndices() {
        return new int[0];
    }

    @Override
    public int[] getColliderIndices() {
        return new int[0];
    }

    @Override
    public int getCompartmentRotation(int i) {
        return 0;
    }

    protected Vec3 positionRiderByIndex(int index){
        float localX = 0.0F;
        float localZ = 0.0F;
        float localY = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()));
        return new Vec3(localX, localY, localZ);
    }

    @Override
    public float getDamageThreshold() {
        return this.DAMAGE_THRESHOLD;
    }

    @Override
    public float getDamageRecovery() {
        return this.DAMAGE_RECOVERY;
    }

    @Override
    protected float getPaddleMultiplier() {
        float paddleMultiplier = 1.0f;
        if(this.getControllingPassenger() instanceof Player player){
            if (player.isHolding(FirmacivItems.KAYAK_PADDLE.get())) {
                paddleMultiplier = 2.0f;
            }
        }
        return paddleMultiplier;
    }

    @Override
    protected float getMomentumSubtractor() {
        return 0.000f;
    }

    @Override
    public Item getDropItem() {
        return FirmacivItems.KAYAK.get();
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(FirmacivItems.KAYAK.get());
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(Firmaciv.MOD_ID, "textures/entity/watercraft/kayak.png");
    }

    @Override
    public float getStepHeight(){
        return 0.6f;
    }
}