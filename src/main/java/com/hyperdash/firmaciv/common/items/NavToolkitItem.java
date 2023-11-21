package com.hyperdash.firmaciv.common.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NavToolkitItem extends AbstractNavItem {
    public NavToolkitItem(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {

        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SPYGLASS_USE,
                SoundSource.NEUTRAL, 1F, 1);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.HONEY_DRINK,
                SoundSource.NEUTRAL, 0.75F, 1);

        if (!pLevel.isClientSide) {
            Vec3 positionClicked = pPlayer.getEyePosition();
            outputCoordinate(positionClicked, pPlayer);
        }

        return super.use(pLevel, pPlayer, pHand, NavType.LAT_LON);
    }

    private void outputCoordinate(Vec3 position, Player player) {
        //TODO: fix 1.20
        /*
        String[] navStrings = AbstractNavItem.getNavStrings(position);

        String altitudeText = navStrings[AbstractNavItem.NavSelection.ALTITUDE.ordinal()];

        String simpleAltitudeText = navStrings[AbstractNavItem.NavSelection.ALT_SIMPLE.ordinal()];

        Component altitudeMessage = new TextComponent(
                altitudeText).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, simpleAltitudeText))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("copy_altitude"))));

        player.sendMessage(altitudeMessage, player.getUUID());

         */
    }


}
