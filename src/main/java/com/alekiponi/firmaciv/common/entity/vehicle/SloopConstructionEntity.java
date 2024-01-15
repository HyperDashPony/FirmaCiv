package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.firmaciv.util.BoatVariant;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SloopConstructionEntity extends SloopEntity{
    public SloopConstructionEntity(EntityType entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick(){
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public int getMaxPassengers() {
        return 0;
    }

    @Override
    public int[] getCleatIndices() {
        return new int[0];
    }

    @Override
    public BoatVariant getVariant() {
        return getVariant("sloop_construction");
    }

    @Override
    public int[] getColliderIndices() {
        return new int[0];
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
