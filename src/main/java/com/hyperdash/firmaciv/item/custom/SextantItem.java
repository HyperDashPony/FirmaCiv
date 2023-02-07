package com.hyperdash.firmaciv.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

import java.util.Random;

public class SextantItem extends Item {
    public SextantItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();

            outputCoordinate(positionClicked, player);
        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                (player) -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return super.useOn(pContext);
    }

    private void outputCoordinate(BlockPos blockPos, Player player){
        int falseX = (int)Math.abs(Math.round(blockPos.getX() + Math.random()*20));
        int falseY = (int)Math.abs(Math.round(blockPos.getY() + Math.random()*20));

        player.sendMessage(new TextComponent(
                "Latitude: " + falseY + (blockPos.getZ() > 0 ? " North" : " South")
                        + ", " +
                "Longitude: " + falseX + (blockPos.getX() > 0 ? " West" : " East")
        ), player.getUUID());
    }

}
