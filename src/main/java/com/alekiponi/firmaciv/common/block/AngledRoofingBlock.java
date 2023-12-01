package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.blockentity.BoatFrameBlockEntity;
import com.alekiponi.firmaciv.util.FirmacivTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;

public class AngledRoofingBlock extends SquaredAngleBlock{
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
