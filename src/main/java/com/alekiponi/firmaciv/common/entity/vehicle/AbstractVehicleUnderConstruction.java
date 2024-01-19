package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.AbstractVehiclePart;
import com.alekiponi.firmaciv.util.BoatVariant;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class AbstractVehicleUnderConstruction extends AbstractVehicle{

    public AbstractVehicleUnderConstruction(EntityType entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public int getMaxPassengers() {
        return 1;
    }

    @Override
    public int[] getCleatIndices() {
        return new int[0];
    }

    public abstract BoatVariant getVariant();

    public BoatVariant getVariant(String boat_type) {
        return BoatVariant.byName(this.getType().toString().split(boat_type + ".")[1]);
    }

    @Override
    public void tick() {
        if (this.getPassengers().size() < this.getMaxPassengers()) {
            final AbstractVehiclePart newPart = FirmacivEntities.CONSTRUCTION_VEHICLE_PART.get().create(this.level());
            newPart.setPos(this.getX(), this.getY(), this.getZ());
            this.level().addFreshEntity(newPart);
            newPart.startRiding(this);
        }

        super.tick();
    }
    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        return InteractionResult.PASS;
    }

    public abstract void interactFromConstructionEntity(final Player player, final InteractionHand hand);

    @Override
    public int[] getColliderIndices() {
        return new int[0];
    }

    @Override
    public int[] getConstructionIndices() {
        return new int[]{0};
    }

    @Override
    public int[] getCanAddOnlyBlocksIndices() {
        return new int[0];
    }

    @Override
    public int getCompartmentRotation(int i) {
        return 0;
    }

    @Override
    public float getPassengerSizeLimit() {
        return 0;
    }

    @Override
    public int[][] getCompartmentRotationsArray() {
        return new int[0][];
    }

    @Override
    protected void tickCleatInput() {

    }

    @Override
    public float getDamageThreshold() {
        return 0;
    }

    @Override
    public float getDamageRecovery() {
        return 0;
    }
}
