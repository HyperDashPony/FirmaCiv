package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.alekiponi.firmaciv.network.PacketHandler;
import com.alekiponi.firmaciv.network.ServerboundSwitchEntityPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

public class SailSwitchEntity extends AbstractSwitchEntity{

    public SailSwitchEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if((stack.is(Tags.Items.DYES) || stack.is(Items.WATER_BUCKET)) && this.getRootVehicle() instanceof SloopEntity sloop){
            int index = 0;
            for(SailSwitchEntity switchEntity : sloop.getSailSwitches()){
                if(switchEntity == this){
                    break;
                }
                index++;
            }

            if(index == 0){
                //mainsail
                if(stack.is(Items.WATER_BUCKET)){
                    sloop.setMainsailDye(ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                }
                if(!stack.is(sloop.getMainsailDye().getItem())){
                    sloop.setMainsailDye(stack.split(1));
                    player.swing(hand);
                    return InteractionResult.SUCCESS;
                }
            } else if(index == 1){
                //jibsail
                if(stack.is(Items.WATER_BUCKET)){
                    sloop.setJibsailDye(ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                }
                if(!stack.is(sloop.getJibsailDye().getItem())){
                    sloop.setJibsailDye(stack.split(1));
                    player.swing(hand);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.interact(player, hand);
    }

}
