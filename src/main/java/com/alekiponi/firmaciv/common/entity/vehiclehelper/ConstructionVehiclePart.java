package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractVehicle;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ConstructionVehiclePart extends AbstractVehiclePart{

    public ConstructionVehiclePart(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void tickAddAppropriateHelper(AbstractVehicle vehicle){
        tickAddConstruction(vehicle);
    }

    protected boolean tickAddConstruction(AbstractVehicle vehicle) {
        for (int i : vehicle.getConstructionIndices()) {
            if (vehicle.getPassengers().get(i).is(this) && !vehicle.getPassengers().get(i).isVehicle()) {
                final ConstructionEntity constructionEntity = FirmacivEntities.CONSTRUCTION_ENTITY.get()
                        .create(this.level());
                assert constructionEntity != null;
                constructionEntity.setPos(this.getX(), this.getY(), this.getZ());
                constructionEntity.setYRot(this.getVehicle().getYRot());
                if (!constructionEntity.startRiding(this)) {
                    Firmaciv.LOGGER.error("New Construction Entity: {} unable to ride Vehicle Part: {}", constructionEntity, this);
                }
                this.level().addFreshEntity(constructionEntity);
                return true;
            }
        }
        return false;
    }
}
