//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alekiponi.firmaciv.client;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.SailSwitchEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.WindlassSwitchEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.VehicleCleatEntity;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.util.FirmacivHelper;
import com.alekiponi.firmaciv.util.FirmacivTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;

public enum IngameOverlays {
    COMPARTMENT_STATUS(IngameOverlays::renderCompartmentStatus),
    PASSENGER_STATUS(IngameOverlays::renderPassengerStatus),

    SAILING_ELEMENT(IngameOverlays::renderSailingElement);

    public static final ResourceLocation COMPARTMENT_ICONS = new ResourceLocation(Firmaciv.MOD_ID,
            "textures/gui/icons/compartment_icons.png");
    public static final ResourceLocation SAILING_ICONS = new ResourceLocation(Firmaciv.MOD_ID,
            "textures/gui/icons/sailing_icons.png");
    public static final ResourceLocation SPEEDOMETER_ICONS = new ResourceLocation(Firmaciv.MOD_ID,
            "textures/gui/icons/speedometer_icons.png");
    final IGuiOverlay overlay;
    private final String id;


    IngameOverlays(IGuiOverlay overlay) {
        this.id = this.name().toLowerCase(Locale.ROOT);
        this.overlay = overlay;
    }


    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        above(event, VanillaGuiOverlay.CROSSHAIR, COMPARTMENT_STATUS);
        above(event, VanillaGuiOverlay.CROSSHAIR, PASSENGER_STATUS);
        above(event, VanillaGuiOverlay.HOTBAR, SAILING_ELEMENT);
    }

    private static void above(RegisterGuiOverlaysEvent event, VanillaGuiOverlay vanilla, IngameOverlays overlay) {
        event.registerAbove(vanilla.id(), overlay.id, overlay.overlay);
    }

    private static void renderPassengerStatus(ForgeGui gui, GuiGraphics graphics, float partialTick, int width,
            int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            Player player = mc.player;

            if (setup(gui, mc) && !player.isSpectator() && mc.options.getCameraType().isFirstPerson()) {
                net.minecraft.world.entity.Entity entity = mc.crosshairPickEntity;
                PoseStack stack = graphics.pose();

                stack.pushPose();
                stack.translate((float) width / 2.0F, (float) height / 2.0F - 15.0F, 0.0F);
                stack.scale(1.0F, 1.0F, 1.0F);

                String string = "";

                if (entity == null) {
                    entity = FirmacivHelper.getAnyEntityAtCrosshair(player, 5f);
                }

                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.getVehicle() instanceof EmptyCompartmentEntity emptyCompartmentEntity) {
                        long remainingTicks = (long) (ICalendar.TICKS_IN_DAY * 3) - (Calendars.SERVER.getTicks() - emptyCompartmentEntity.getPassengerRideTick());

                        if (remainingTicks <= ICalendar.TICKS_IN_DAY) {
                            string = "This rider is restless. ";
                        }
                        string += "Press " + mc.options.keyShift.getTranslatedKeyMessage()
                                .getString() + " + " + mc.options.keyUse.getTranslatedKeyMessage()
                                .getString() + " to eject";
                    }
                }

                if (entity instanceof EmptyCompartmentEntity emptyCompartmentEntity) {
                    if (emptyCompartmentEntity.getFirstPassenger() instanceof LivingEntity livingEntity) {
                        long remainingTicks = (long) (ICalendar.TICKS_IN_DAY * 3) - (Calendars.SERVER.getTicks() - emptyCompartmentEntity.getPassengerRideTick());

                        if (remainingTicks <= ICalendar.TICKS_IN_DAY) {
                            string = "This rider is restless. ";
                        }
                        string += "Press " + mc.options.keyShift.getTranslatedKeyMessage()
                                .getString() + " + " + mc.options.keyUse.getTranslatedKeyMessage()
                                .getString() + " to eject";
                    }
                }

                if (!string.equals("")) {
                    graphics.drawString(mc.font, string, -mc.font.width(string) / 2, 0, Color.WHITE.getRGB(), true);
                }

                stack.popPose();
            }
        }
    }


    private static void renderSailingElement(ForgeGui gui, GuiGraphics graphics, float partialTick, int width,
            int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            Player player = mc.player;

            if (setup(gui, mc)  && !player.isSpectator()) {
                PoseStack stack = graphics.pose();
                stack.pushPose();
                if(player.getRootVehicle() instanceof SloopEntity sloopEntity){
                    if(sloopEntity.getControllingCompartment() != null && sloopEntity.getControllingCompartment().hasExactlyOnePlayerPassenger() && sloopEntity.getControllingCompartment().getFirstPassenger().equals(player)){

                        int offhandOffset = 3;
                        if(!player.getOffhandItem().isEmpty()){
                            offhandOffset = 26;
                        }
                        stack.scale(1.0F, 1.0F, 1.0F);

                        int x = width / 2;
                        int y = height - gui.rightHeight;

                        stack.translate((float)(x + 1), (float)(y + 4), 0.0F);
                        if ((float) height % 2.0 != 0) {
                            stack.translate(0f, 0.5f, 0.0f);
                        }
                        if ((float) width % 2.0 != 0) {
                            stack.translate(0.5f, 0f, 0.0f);
                        }

                        int windSpeed = (int)(sloopEntity.getLocalWindAngleAndSpeed()[1]*160);
                        DecimalFormat df = new DecimalFormat("###.#");
                        String displayBoatSpeed = df.format(sloopEntity.getSmoothSpeedMS()*3.6) + " kmh";
                        windSpeed = Mth.clamp(windSpeed, 1, 20);
                        int ticksBetweenFrames = Mth.clamp(Math.abs(windSpeed-20), 1, 20);
                        int ticks = sloopEntity.tickCount/ticksBetweenFrames;
                        int frameIndex = ticks%(32);

                        double speedMS = sloopEntity.getSmoothSpeedMS();
                        int speedometerIndex = Mth.clamp((int)(speedMS-2)*2,0,31);

                        int angle = Math.round((Mth.wrapDegrees(sloopEntity.getWindLocalRotation())/360)*64);
                        angle = angle+32;
                        if(angle == 64){
                            angle = 0;
                        }
                        // TODO config to add numerical speed instead, config for units
                        if(mc.options.renderDebug){
                            graphics.drawString(mc.font, displayBoatSpeed, -134, -8-offhandOffset, Color.WHITE.getRGB(), true);
                        }
                        graphics.blit(SAILING_ICONS, -126, 3-offhandOffset, 32*angle, (32)*(angle/8), 32, 32);
                        graphics.blit(SPEEDOMETER_ICONS, -126-8, 3-offhandOffset+(32-16), 16*speedometerIndex, (16)*(speedometerIndex/16), 16, 16);
                        graphics.blit(SPEEDOMETER_ICONS, -126-8, 3-offhandOffset+(32-32), 16*frameIndex, 32+(16)*(frameIndex/16), 16, 16);
                    }
                    if(player.getVehicle() instanceof EmptyCompartmentEntity compartment){
                        if(sloopEntity.getControllingCompartment() == compartment){

                        }
                    }
                }

                stack.popPose();
            }
        }
    }


    private static void renderCompartmentStatus(ForgeGui gui, GuiGraphics graphics, float partialTick, int width,
            int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            Player player = mc.player;
            if (setup(gui, mc) && !player.isSpectator() && mc.options.getCameraType().isFirstPerson()) {
                net.minecraft.world.entity.Entity entity = FirmacivHelper.getAnyEntityAtCrosshair(player, 3f);
                PoseStack stack = graphics.pose();

                stack.pushPose();

                if (entity instanceof EmptyCompartmentEntity emptyCompartmentEntity && emptyCompartmentEntity.isPassenger() && !emptyCompartmentEntity.isVehicle()) {
                    stack.scale(1.0F, 1.0F, 1.0F);
                    stack.translate((float) width / 2.0F - 5f - 12f, (float) height / 2.0F - 5F, 0.0F);
                    if ((float) height % 2.0 != 0) {
                        stack.translate(0f, 0.5f, 0.0f);
                    }
                    if ((float) width % 2.0 != 0) {
                        stack.translate(0.5f, 0f, 0.0f);
                    }
                    if (emptyCompartmentEntity.getTrueVehicle() != null && emptyCompartmentEntity.getTrueVehicle().getPilotVehiclePartAsEntity() != null) {
                        if (emptyCompartmentEntity.getTrueVehicle().getPilotVehiclePartAsEntity().getFirstPassenger()
                                .is(emptyCompartmentEntity)) {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, 0, 0, 9, 9);
                            if (emptyCompartmentEntity.getTrueVehicle() instanceof CanoeEntity && player.getItemInHand(
                                    player.getUsedItemHand()).is(FirmacivTags.Items.CAN_PLACE_IN_COMPARTMENTS)) {
                                graphics.blit(COMPARTMENT_ICONS, -12, 0, 9, 0, 9, 9);
                            }

                        } else if (player.getItemInHand(player.getUsedItemHand())
                                .is(FirmacivTags.Items.CAN_PLACE_IN_COMPARTMENTS)) {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, 9, 0, 9, 9);
                        } else if (player.getItemInHand(player.getUsedItemHand())
                                .is(FirmacivItems.CANNON.get()) && !emptyCompartmentEntity.canAddOnlyBLocks() && emptyCompartmentEntity.getRootVehicle() instanceof SloopEntity) {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, 9, 0, 9, 9);
                        }else if (!emptyCompartmentEntity.isVehicle() && !emptyCompartmentEntity.canAddOnlyBLocks()) {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, 36, 0, 9, 9);
                        } else if (!emptyCompartmentEntity.isVehicle() && emptyCompartmentEntity.canAddOnlyBLocks()) {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, 9, 0, 9, 9);
                        }
                    }
                } else if (entity instanceof VehicleCleatEntity vehicleCleatEntity  && vehicleCleatEntity.isPassenger() && !vehicleCleatEntity.isLeashed()) {
                    if ((float) height % 2.0 != 0) {
                        stack.translate(0f, 0.5f, 0.0f);
                    }
                    if ((float) width % 2.0 != 0) {
                        stack.translate(0.5f, 0f, 0.0f);
                    }
                    stack.scale(1.0F, 1.0F, 1.0F);
                    stack.translate((float) width / 2.0F - 5f - 12f, (float) height / 2.0F - 5F, 0.0F);
                    if (vehicleCleatEntity.getVehicle().getVehicle() != null) {
                        graphics.blit(COMPARTMENT_ICONS, 0, 0, 54, 0, 9, 9);
                    }
                } else if (entity instanceof SailSwitchEntity sailSwitch  && sailSwitch.isPassenger()) {
                    if ((float) height % 2.0 != 0) {
                        stack.translate(0f, 0.5f, 0.0f);
                    }
                    if ((float) width % 2.0 != 0) {
                        stack.translate(0.5f, 0f, 0.0f);
                    }
                    stack.scale(1.0F, 1.0F, 1.0F);
                    stack.translate((float) width / 2.0F - 5f - 12f, (float) height / 2.0F - 5F, 0.0F);
                    if (sailSwitch.getVehicle().getVehicle() != null) {
                        if(sailSwitch.getSwitched()){
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, 18, 0, 9, 9);
                            graphics.blit(COMPARTMENT_ICONS, 0, 10, 72, 0, 9, 9);
                        } else {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, 18, 0, 9, 9);
                            graphics.blit(COMPARTMENT_ICONS, 0, -10, 63, 0, 9, 9);
                        }

                    }
                } else if (entity instanceof WindlassSwitchEntity windlassSwitch  && windlassSwitch.isPassenger()) {
                    if ((float) height % 2.0 != 0) {
                        stack.translate(0f, 0.5f, 0.0f);
                    }
                    if ((float) width % 2.0 != 0) {
                        stack.translate(0.5f, 0f, 0.0f);
                    }
                    stack.scale(1.0F, 1.0F, 1.0F);
                    stack.translate((float) width / 2.0F - 5f - 12f, (float) height / 2.0F - 5F, 0.0F);
                    if (windlassSwitch.getVehicle().getVehicle() != null) {
                        if(!windlassSwitch.getSwitched()){
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, 81, 0, 9, 9);
                            graphics.blit(COMPARTMENT_ICONS, 0, 10, 72, 0, 9, 9);
                        } else {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, 81, 0, 9, 9);
                            graphics.blit(COMPARTMENT_ICONS, 0, -10, 63, 0, 9, 9);
                        }

                    }
                }

                stack.popPose();
            }
        }
    }

    public static boolean setup(ForgeGui gui, Minecraft minecraft) {
        if (!minecraft.options.hideGui && minecraft.getCameraEntity() instanceof Player) {
            gui.setupOverlayRenderState(true, false);
            return true;
        } else {
            return false;
        }
    }
}
