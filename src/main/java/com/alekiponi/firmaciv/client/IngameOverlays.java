//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alekiponi.firmaciv.client;

import com.alekiponi.firmaciv.common.entity.CanoeEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.EmptyCompartmentEntity;
import com.alekiponi.firmaciv.util.FirmacivHelper;
import com.alekiponi.firmaciv.util.FirmacivTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

import java.awt.*;
import java.util.Locale;

public enum IngameOverlays {
    COMPARTMENT_STATUS(IngameOverlays::renderCompartmentStatus),
    PASSENGER_STATUS(IngameOverlays::renderPassengerStatus),

    SAILING_ELEMENT(IngameOverlays::renderSailingElement);

    public static final ResourceLocation TEXTURE = new ResourceLocation("firmaciv", "textures/gui/icons/compartment_icons.png");;
    final IGuiOverlay overlay;
    private final String id;



    IngameOverlays(IGuiOverlay overlay) {
        this.id = this.name().toLowerCase(Locale.ROOT);
        this.overlay = overlay;
    }


    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        above(event, VanillaGuiOverlay.CROSSHAIR, COMPARTMENT_STATUS);
        above(event, VanillaGuiOverlay.CROSSHAIR, PASSENGER_STATUS);
        //above(event, VanillaGuiOverlay.CROSSHAIR, SAILING_ELEMENT);
    }

    private static void above(RegisterGuiOverlaysEvent event, VanillaGuiOverlay vanilla, IngameOverlays overlay) {
        event.registerAbove(vanilla.id(), overlay.id, overlay.overlay);
    }

    private static void renderPassengerStatus(ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            Player player = mc.player;


            if (setup(gui, mc)) {
                Entity entity = mc.crosshairPickEntity;
                PoseStack stack = graphics.pose();

                stack.pushPose();
                stack.translate((float)width / 2.0F, (float)height / 2.0F - 15.0F, 0.0F);
                stack.scale(1.0F, 1.0F, 1.0F);

                String string = "";

                if(entity == null){
                    entity = FirmacivHelper.getAnyEntityAtCrosshair(player, 5f);
                }

                if(entity instanceof LivingEntity livingEntity){
                    if(livingEntity.getVehicle() instanceof EmptyCompartmentEntity emptyCompartmentEntity){
                        long remainingTicks = (long) (ICalendar.TICKS_IN_DAY*3) - (Calendars.SERVER.getTicks() - emptyCompartmentEntity.getPassengerRideTick());

                        if (remainingTicks <= ICalendar.TICKS_IN_DAY) {
                            string = "This rider is restless. ";
                        }
                        string += "Press " + mc.options.keyShift.getTranslatedKeyMessage().getString() +  " + " + mc.options.keyUse.getTranslatedKeyMessage().getString()  + " to eject";
                    }
                }

                if (entity instanceof EmptyCompartmentEntity emptyCompartmentEntity) {
                    if(emptyCompartmentEntity.getFirstPassenger() instanceof LivingEntity livingEntity){
                        long remainingTicks = (long) (ICalendar.TICKS_IN_DAY*3) - (Calendars.SERVER.getTicks() - emptyCompartmentEntity.getPassengerRideTick());

                        if (remainingTicks <= ICalendar.TICKS_IN_DAY) {
                            string = "This rider is restless. ";
                        }
                        string += "Press " + mc.options.keyShift.getTranslatedKeyMessage().getString() +  " + " + mc.options.keyUse.getTranslatedKeyMessage().getString()  + " to eject";
                    }
                }

                if(!string.equals("")){
                    graphics.drawString(mc.font, string, -mc.font.width(string) / 2, 0, Color.WHITE.getRGB(), false);
                }

                stack.popPose();
            }
        }
    }


    private static void renderSailingElement(ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            Player player = mc.player;
            Vec2 windVector = Climate.getWindVector(player.level(),player.blockPosition());
            double direction = Math.round(Math.toDegrees(Math.atan(windVector.x/windVector.y)));
            double speed = Math.round(windVector.length()*320*0.5399568);

            if (setup(gui, mc)) {
                PoseStack stack = graphics.pose();

                stack.pushPose();
                stack.translate((float)width / 2.0F, (float)height / 2.0F - 15.0F, 0.0F);
                stack.scale(1.0F, 1.0F, 1.0F);

                String string = "speed: " + speed + " knots, direction: " + direction;

                graphics.drawString(mc.font, string, -mc.font.width(string) / 2, 0, Color.WHITE.getRGB(), false);

                stack.popPose();
            }
        }
    }



    private static void renderCompartmentStatus(ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            Player player = mc.player;

            if (setup(gui, mc)) {
                Entity entity = FirmacivHelper.getAnyEntityAtCrosshair(player, 5f);
                PoseStack stack = graphics.pose();

                stack.pushPose();
                stack.translate((float)width / 2.0F - 5f - 12f, (float)height / 2.0F - 4.5F, 0.0F);
                stack.scale(0.5F, 0.5F, 0.5F);

                if (entity instanceof EmptyCompartmentEntity emptyCompartmentEntity) {

                    if (emptyCompartmentEntity.getTrueVehicle() != null) {
                        if (emptyCompartmentEntity.getTrueVehicle().getPilotVehiclePartAsEntity().getFirstPassenger().is(emptyCompartmentEntity)) {
                            graphics.blit(TEXTURE, 0, 0, 0, 0, 18, 18);
                            if(emptyCompartmentEntity.getTrueVehicle() instanceof CanoeEntity && player.getItemInHand(player.getUsedItemHand()).is(FirmacivTags.Items.CAN_PLACE_IN_COMPARTMENTS)){
                                graphics.blit(TEXTURE, -23, 0, 18, 0, 18, 18);
                            }

                        } else if (player.getItemInHand(player.getUsedItemHand()).is(FirmacivTags.Items.CAN_PLACE_IN_COMPARTMENTS)){
                            graphics.blit(TEXTURE, 0, 0, 18, 0, 18, 18);

                        } /*else if (player.getItemInHand(player.getUsedItemHand()).is(FirmacivTags.Items.WORKBENCHES)){
                            graphics.blit(TEXTURE, 0, 0, 18, 0, 18, 18);

                        } */ else if (!emptyCompartmentEntity.isVehicle() && !emptyCompartmentEntity.canAddOnlyBLocks()){
                            graphics.blit(TEXTURE, 0, 0, 72, 0, 18, 18);
                        } else if (!emptyCompartmentEntity.isVehicle() && emptyCompartmentEntity.canAddOnlyBLocks()){
                            graphics.blit(TEXTURE, 0, 0, 18, 0, 18, 18);
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
