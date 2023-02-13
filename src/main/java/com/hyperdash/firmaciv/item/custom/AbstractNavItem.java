package com.hyperdash.firmaciv.item.custom;

import com.hyperdash.firmaciv.Firmaciv;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.text.DecimalFormat;

import static com.hyperdash.firmaciv.item.custom.NavToolkitItem.getNavStrings;

public class AbstractNavItem extends Item{
    public AbstractNavItem(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand, NavType navType) {

        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        pPlayer.getCooldowns().addCooldown(this, 200);

        if (!pLevel.isClientSide) {
            Vec3 positionClicked = pPlayer.getEyePosition();

            outputCoordinate(positionClicked, pPlayer, navType);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    public void outputCoordinate(Vec3 position, Player player, NavType navType){

        String[] navStrings = getNavStrings(position);

        String locationText = navStrings[NavSelection.LATITUDE.ordinal()];
        String simpleLocationText = navStrings[NavSelection.LAT_SIMPLE.ordinal()];
        TranslatableComponent copyMessage = new TranslatableComponent("copy_latitude");

        switch (navType){
            case LAT:
                break;
            case LON:
                locationText = navStrings[NavSelection.LONGITUDE.ordinal()];
                simpleLocationText = navStrings[NavSelection.LONGITUDE.ordinal()];
                copyMessage = new TranslatableComponent("copy_longitude");
                break;
            case ALT:
                locationText = navStrings[NavSelection.ALTITUDE.ordinal()];
                simpleLocationText = navStrings[NavSelection.ALTITUDE.ordinal()];
                copyMessage = new TranslatableComponent("copy_altitude");
                break;
            case LAT_LON:
                locationText =
                        navStrings[AbstractNavItem.NavSelection.LATITUDE.ordinal()] + " | " +
                        navStrings[AbstractNavItem.NavSelection.LONGITUDE.ordinal()];

                simpleLocationText =
                        navStrings[AbstractNavItem.NavSelection.LAT_SIMPLE.ordinal()] + ", " +
                        navStrings[AbstractNavItem.NavSelection.LON_SIMPLE.ordinal()];
                copyMessage = new TranslatableComponent("copy_latlon");
                break;
        }

        String finalSimpleLocationText = simpleLocationText;
        TranslatableComponent finalCopyMessage = copyMessage;

        Component locationMessage = new TextComponent(
                locationText).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, finalSimpleLocationText))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, finalCopyMessage)));

        player.sendMessage(locationMessage, player.getUUID());
    }

    public static double[] getNavLocation(Vec3 position){

        double latitude = (Math.floor(position.get(Direction.Axis.Z) - 20000) / 40000)*90;
        double longitude = (Math.floor(position.get(Direction.Axis.X)) / 40000)*90;
        double altitude = (position.get(Direction.Axis.Y)) - 64;

        return new double[]{latitude, longitude, altitude};
    }

    public static String[] getNavStrings(Vec3 position){
        double randomScale = 0.1;
        int altRandomScale = 4;

        double[] navLocation = getNavLocation(position);

        double latitude = navLocation[NavSelection.LATITUDE.ordinal()] + Math.random()*randomScale;
        double longitude = navLocation[NavSelection.LONGITUDE.ordinal()] + Math.random()*randomScale;
        double altitude = navLocation[NavSelection.ALTITUDE.ordinal()] + Math.random()*altRandomScale;

        DecimalFormat dfAlt = new DecimalFormat("###");
        DecimalFormat df = new DecimalFormat("###.##");

        String latStr = "Latitude: " + df.format(Math.abs(latitude)) + " Degrees " + (latitude > 0 ? "South" : "North");
        String lonStr = "Longitude: " + df.format(Math.abs(longitude)) + " Degrees " + (longitude > 0 ? "East" : "West");
        String altStr = "Altitude: " + dfAlt.format(Math.abs(altitude)) + " Meters " + (altitude > 0 ? "Above" : "Below") + " Sea Level";

        String latSim = "Lat: " + df.format(Math.abs(latitude)) + " " + (latitude > 0 ? "S" : "N");
        String lonSim = "Lon: " + df.format(Math.abs(longitude)) + " " + (longitude > 0 ? "E" : "W");
        String altSim = "Alt: " + dfAlt.format(altitude);

        return new String[]{latStr, lonStr, altStr, latSim, lonSim, altSim};

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
