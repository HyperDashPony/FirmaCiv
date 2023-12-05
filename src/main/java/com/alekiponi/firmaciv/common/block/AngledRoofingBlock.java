package com.alekiponi.firmaciv.common.block;

public class AngledRoofingBlock extends SquaredAngleBlock {
    public AngledRoofingBlock(Properties properties) {
        super(properties);
    }

    /*
    //TODO implement swapping between stair and angled variants
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(final BlockState blockState, final Level level, final BlockPos blockPos,
                                 final Player player, final InteractionHand hand, final BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.PASS;

        if(player.getItemInHand(hand).isEmpty()){

        }

        return InteractionResult.PASS;
    }*/

}
