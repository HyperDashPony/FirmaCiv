package com.alekiponi.firmaciv.common.item;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.text.DecimalFormat;

public class AbstractNavItem extends Item {
    public AbstractNavItem(Properties pProperties) {
        super(pProperties);
    }

    public static double[] getNavLocation(Vec3 position) {
        double latitude = (Math.floor(position.get(Direction.Axis.Z) - 10000) / 20000) * 90;
        double longitude = (Math.floor(position.get(Direction.Axis.X)) / 10000) * 90;
        double altitude = (position.get(Direction.Axis.Y)) - 64;

        return new double[]{latitude, longitude, altitude};
    }

    public static String[] getNavStrings(Vec3 position) {
        double randomScale = 0.1;
        int altRandomScale = 4;

        double[] navLocation = getNavLocation(position);

        double latitude = navLocation[NavSelection.LATITUDE.ordinal()] + Math.random() * randomScale;
        double longitude = navLocation[NavSelection.LONGITUDE.ordinal()] + Math.random() * randomScale;
        double altitude = navLocation[NavSelection.ALTITUDE.ordinal()] + Math.random() * altRandomScale;

        Component latitudeComponent = Component.translatable("latitude");

        Component longitudeComponent = Component.translatable("longitude");

        Component altitudeComponent = Component.translatable("altitude");

        Component degreesComponent = Component.translatable("degrees");

        Component seaLevelComponent = Component.translatable("sea_level");

        Component northComponent = Component.translatable("north");

        Component southComponent = Component.translatable("south");

        Component eastComponent = Component.translatable("east");

        Component westComponent = Component.translatable("west");

        Component above = Component.translatable("above");

        Component below = Component.translatable("below");

        Component meters = Component.translatable("meters");

        DecimalFormat dfAlt = new DecimalFormat("###");
        DecimalFormat df = new DecimalFormat("###.##");

        String latStr = latitudeComponent.getString() + ": " + df.format(Math.abs(latitude))
                + " " + degreesComponent.getString() +" " + (latitude > 0 ? southComponent.getString() : northComponent.getString());

        String lonStr = longitudeComponent.getString() + ": " + df.format(
                Math.abs(longitude)) + " " + degreesComponent.getString() + " " + (longitude > 0 ? eastComponent.getString() : westComponent.getString());

        String altStr = altitudeComponent.getString() + ": " + dfAlt.format(
                Math.abs(altitude)) + " " + meters.getString()+ " " + (altitude > 0 ? above.getString() : below.getString()) + " " + seaLevelComponent.getString();

        String latSim = latitudeComponent.getString(3) + ": " + df.format(Math.abs(latitude)) + " " + (latitude > 0 ? southComponent.getString(1) : northComponent.getString(1));
        String lonSim = longitudeComponent.getString(3) + ": " + df.format(Math.abs(longitude)) + " " + (longitude > 0 ? eastComponent.getString(1) : westComponent.getString(1));
        String altSim = altitudeComponent.getString(3) + ": " + dfAlt.format(altitude);

        return new String[]{latStr, lonStr, altStr, latSim, lonSim, altSim};

    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand,
            NavType navType) {

        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        pPlayer.getCooldowns().addCooldown(this, 200);

        if (!pLevel.isClientSide) {
            Vec3 positionClicked = pPlayer.getEyePosition();

            outputCoordinate(positionClicked, pPlayer, navType);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    public void outputCoordinate(Vec3 position, Player player, NavType navType) {

        String[] navStrings = getNavStrings(position);

        String locationText = navStrings[NavSelection.LATITUDE.ordinal()];
        String simpleLocationText = navStrings[NavSelection.LAT_SIMPLE.ordinal()];
        Component copyMessage = Component.translatable("copy_latitude");


        switch (navType) {
            case LAT:
                break;
            case LON:
                locationText = navStrings[NavSelection.LONGITUDE.ordinal()];
                simpleLocationText = navStrings[NavSelection.LON_SIMPLE.ordinal()];
                copyMessage = Component.translatable("copy_longitude");

                break;
            case ALT:
                locationText = navStrings[NavSelection.ALTITUDE.ordinal()];
                simpleLocationText = navStrings[NavSelection.ALT_SIMPLE.ordinal()];
                copyMessage = Component.translatable("copy_altitude");
                break;
            case LAT_LON:
                locationText =
                        navStrings[AbstractNavItem.NavSelection.LATITUDE.ordinal()] + " | " +
                                navStrings[AbstractNavItem.NavSelection.LONGITUDE.ordinal()];

                simpleLocationText =
                        navStrings[AbstractNavItem.NavSelection.LAT_SIMPLE.ordinal()] + ", " +
                                navStrings[AbstractNavItem.NavSelection.LON_SIMPLE.ordinal()];
                break;
        }

        String finalSimpleLocationText = simpleLocationText;
        Component finalCopyMessage = copyMessage;


        Component locationMessage = Component.literal(
                locationText).withStyle(style -> style.withClickEvent(
                        new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, finalSimpleLocationText))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, finalCopyMessage)));


        player.sendSystemMessage(locationMessage);


    }

    public enum NavSelection {
        LATITUDE,
        LONGITUDE,
        ALTITUDE,
        LAT_SIMPLE,
        LON_SIMPLE,
        ALT_SIMPLE
    }

    public enum NavType {
        LAT,
        LON,
        ALT,
        LAT_LON
    }

}
