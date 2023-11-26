//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.hyperdash.firmaciv.client;

import com.hyperdash.firmaciv.common.entity.vehiclehelper.EmptyCompartmentEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

import java.awt.*;
import java.util.Locale;

public enum IngameOverlays {
    COMPARTMENT_STATUS(IngameOverlays::renderCompartmentStatus);

    public static final ResourceLocation TEXTURE = Helpers.identifier("textures/gui/icons/overlay.png");
    final IGuiOverlay overlay;
    private final String id;

    IngameOverlays(IGuiOverlay overlay) {
        this.id = this.name().toLowerCase(Locale.ROOT);
        this.overlay = overlay;
    }


    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        above(event, VanillaGuiOverlay.CROSSHAIR, COMPARTMENT_STATUS);
    }

    private static void above(RegisterGuiOverlaysEvent event, VanillaGuiOverlay vanilla, IngameOverlays overlay) {
        event.registerAbove(vanilla.id(), overlay.id, overlay.overlay);
    }

    private static void renderCompartmentStatus(ForgeGui gui, GuiGraphics graphics, float partialTick, int width,
                                                int height) {

        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            Player player = mc.player;
            if (setup(gui, mc)) {
                Entity entity = mc.crosshairPickEntity;
                PoseStack stack = graphics.pose();
                stack.pushPose();
                stack.translate((float) width / 2.0F, ((float) height / 2.0F - 20.0F), 0.0F);
                stack.scale(1.0F, 1.0F, 1.0F);
                String string = "";

                if(entity instanceof LivingEntity livingEntity){
                    if(livingEntity.getVehicle() instanceof EmptyCompartmentEntity emptyCompartmentEntity){
                        long remainingTicks = (long) (ICalendar.TICKS_IN_DAY*3) - (Calendars.SERVER.getTicks() - emptyCompartmentEntity.getPassengerRideTick());

                        if (remainingTicks <= ICalendar.TICKS_IN_DAY) {
                            string = "This rider is restless. ";
                        }
                        string += "Press " + mc.options.keyShift.getTranslatedKeyMessage().getString() +  " + " + mc.options.keyUse.getTranslatedKeyMessage().getString()  + " to eject.";
                    }
                }
                /*

                if (entity instanceof EmptyCompartmentEntity emptyCompartmentEntity) {
                    if (emptyCompartmentEntity.getTrueVehicle() != null) {
                        if (emptyCompartmentEntity.getTrueVehicle().getControllingCompartment() != null && emptyCompartmentEntity.getTrueVehicle().getControllingCompartment().is(emptyCompartmentEntity)) {
                            string = "Pilot Seat";
                        } else if (player.getItemInHand(player.getUsedItemHand()).is(FirmacivTags.Items.CHESTS)){
                            string = "Place Chest";
                        } else if (player.getItemInHand(player.getUsedItemHand()).is(FirmacivTags.Items.WORKBENCHES)){
                            string = "Place Workbench";
                        } else {
                            string = "Passenger Seat";
                        }
                    }
                }

                 */
                graphics.drawString(mc.font, string, -mc.font.width(string) / 2, 0, Color.WHITE.getRGB(), false);
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
