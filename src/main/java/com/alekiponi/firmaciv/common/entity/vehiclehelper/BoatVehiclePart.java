package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractFirmacivBoatEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.AbstractVehicle;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class BoatVehiclePart extends AbstractVehiclePart{


    public BoatVehiclePart(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void tickAddAppropriateHelper(AbstractVehicle vehicle) {
        if(vehicle instanceof AbstractFirmacivBoatEntity boatEntity){
            boolean alreadyHasPassenger = false;
            // Try adding a cleat
            alreadyHasPassenger = tickAddCleat(vehicle);

            //Try adding a collider
            if (!alreadyHasPassenger) {
                alreadyHasPassenger = tickAddCollider(vehicle);
            }

            // Try adding a switch
            if (!alreadyHasPassenger) {
                alreadyHasPassenger = tickAddSailSwitch(boatEntity);
            }

            // Try adding a Windlass
            if (!alreadyHasPassenger) {
                tickAddWindlass(boatEntity);
                alreadyHasPassenger = tickAddWindlass(boatEntity);
            }

            // Try adding a Mast
            if(!alreadyHasPassenger){
                tickAddMast(boatEntity);
            }

            // Try adding a compartment
            if (!alreadyHasPassenger) {
                alreadyHasPassenger = tickAddCompartment(vehicle);
            }
        }

    }

    protected boolean tickAddSailSwitch(AbstractFirmacivBoatEntity boat) {
        for (int i : boat.getSailSwitchIndices()) {
            if (boat.getPassengers().get(i).is(this) && !boat.getPassengers().get(i).isVehicle()) {

                final AbstractSwitchEntity switchEntity = FirmacivEntities.SAIL_SWITCH_ENTITY.get()
                        .create(this.level());
                assert switchEntity != null;
                switchEntity.setPos(this.getX(), this.getY(), this.getZ());
                if (!switchEntity.startRiding(this)) {
                    Firmaciv.LOGGER.error("New Switch: {} unable to ride Vehicle Part: {}", switchEntity, this);
                }
                this.level().addFreshEntity(switchEntity);
                return true;

            }
        }
        return false;
    }

    protected boolean tickAddWindlass(AbstractFirmacivBoatEntity boat) {
        for (int i : boat.getWindlassIndices()) {
            if (boat.getPassengers().get(i).is(this) && !boat.getPassengers().get(i).isVehicle()) {


                final WindlassSwitchEntity windlass = FirmacivEntities.WINDLASS_SWITCH_ENTITY.get()
                        .create(this.level());
                assert windlass != null;
                windlass.setPos(this.getX(), this.getY(), this.getZ());
                if (!windlass.startRiding(this)) {
                    Firmaciv.LOGGER.error("New Windlass: {} unable to ride Vehicle Part: {}", windlass, this);
                }
                this.level().addFreshEntity(windlass);
                return true;
            }
        }
        return false;
    }

    protected boolean tickAddMast(AbstractFirmacivBoatEntity boat) {
        for (int i : boat.getMastIndices()) {
            if (boat.getPassengers().get(i).is(this) && !boat.getPassengers().get(i).isVehicle()) {

                final MastEntity mast = FirmacivEntities.MAST_ENTITY.get()
                        .create(this.level());
                assert mast != null;
                mast.setPos(this.getX(), this.getY(), this.getZ());
                mast.setYRot(this.getVehicle().getYRot());
                if (!mast.startRiding(this)) {
                    Firmaciv.LOGGER.error("New Mast: {} unable to ride Vehicle Part: {}", mast, this);
                }
                this.level().addFreshEntity(mast);
                return true;
            }
        }
        return false;
    }
}
