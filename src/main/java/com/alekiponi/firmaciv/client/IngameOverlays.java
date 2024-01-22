//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alekiponi.firmaciv.client;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.entity.CannonEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.*;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.*;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.Tags;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Locale;

public enum IngameOverlays {
    COMPARTMENT_STATUS(IngameOverlays::renderCompartmentStatus),
    PASSENGER_STATUS(IngameOverlays::renderPassengerStatus),
    SAILING_ELEMENT(IngameOverlays::renderSailingElement),
    SLOOP_CONSTRUCTION(IngameOverlays::renderSloopConstructionStatus),
    CANNON_LOAD_STATE(IngameOverlays::renderCannonLoadState);


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

    public static final int COMPARTMENT_ICON_WIDTH = 9;
    public static enum CompIcon {
        HELM,
        BLOCK,
        SAIL,
        PADDLE,
        SEAT,
        EJECT,
        LEAD,
        ARROW_UP,
        ARROW_DOWN,
        ANCHOR,
        BRUSH,
        HAMMER
    }

    public static int iconOffset(CompIcon icon){
        return icon.ordinal()*9;
    }

    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        above(event, VanillaGuiOverlay.CROSSHAIR, COMPARTMENT_STATUS);
        above(event, VanillaGuiOverlay.CROSSHAIR, PASSENGER_STATUS);
        above(event, VanillaGuiOverlay.CROSSHAIR, SLOOP_CONSTRUCTION);
        above(event, VanillaGuiOverlay.CROSSHAIR, CANNON_LOAD_STATE);
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
                Entity entity = FirmacivHelper.getAnyEntityAtCrosshair(player, 2f);
                PoseStack stack = graphics.pose();

                Component press = Component.translatable("press_button");

                Component toEject = Component.translatable("eject_passengers");

                Component restlessPassenger = Component.translatable("restless_passenger");

                String string = "";

                stack.pushPose();

                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.getVehicle() instanceof EmptyCompartmentEntity emptyCompartmentEntity) {

                        stack.translate((float) width / 2.0F, (float) height / 2.0F - 15.0F, 0.0F);
                        stack.scale(1.0F, 1.0F, 1.0F);
                        long remainingTicks = (long) (ICalendar.TICKS_IN_DAY * 3) - (Calendars.SERVER.getTicks() - emptyCompartmentEntity.getPassengerRideTick());

                        if (remainingTicks <= ICalendar.TICKS_IN_DAY) {
                            string = restlessPassenger.getString() + " ";
                        }
                        string += press.getString() + " " + mc.options.keyShift.getTranslatedKeyMessage()
                                .getString() + " + " + mc.options.keyUse.getTranslatedKeyMessage()
                                .getString() + " " + toEject.getString();
                    }
                } else if (entity instanceof EmptyCompartmentEntity emptyCompartmentEntity) {
                    stack.translate((float) width / 2.0F, (float) height / 2.0F - 15.0F, 0.0F);
                    stack.scale(1.0F, 1.0F, 1.0F);
                    if (emptyCompartmentEntity.getFirstPassenger() instanceof LivingEntity livingEntity) {
                        long remainingTicks = (long) (ICalendar.TICKS_IN_DAY * 3) - (Calendars.SERVER.getTicks() - emptyCompartmentEntity.getPassengerRideTick());

                        if (remainingTicks <= ICalendar.TICKS_IN_DAY) {
                            string = restlessPassenger.getString() + " ";
                        }
                        string += press.getString() + " " + mc.options.keyShift.getTranslatedKeyMessage()
                                .getString() + " + " + mc.options.keyUse.getTranslatedKeyMessage()
                                .getString() + " " + toEject.getString();
                    }
                }

                if (!string.equals("")) {
                    graphics.drawString(mc.font, string, -mc.font.width(string) / 2, 0, Color.WHITE.getRGB(), true);
                }

                //Component copyMessage = Component.translatable("eject_passengers_1");
                //player.displayClientMessage(copyMessage, true);

                stack.popPose();
            }
        }
    }

    private static void renderSloopConstructionStatus(ForgeGui gui, GuiGraphics graphics, float partialTick, int width,
                                              int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            Player player = mc.player;

            if (setup(gui, mc) && !player.isSpectator() && mc.options.getCameraType().isFirstPerson()) {
                Entity entity = FirmacivHelper.getAnyEntityAtCrosshair(player, 2f);
                PoseStack stack = graphics.pose();

                stack.pushPose();

                if (entity instanceof ConstructionEntity constructionEntity && constructionEntity.getRootVehicle() instanceof SloopUnderConstructionEntity sloop) {
                    stack.translate((float) width / 2.0F, (float) height / 2.0F - 15.0F, 0.0F);
                    stack.scale(1.0F, 1.0F, 1.0F);

                    int countLeft = sloop.getNumberItemsLeft();
                    Item item = sloop.getCurrentRequiredItem();

                    graphics.renderItem(item.getDefaultInstance().copyWithCount(countLeft), 0,0);
                    graphics.renderItemDecorations(mc.font, item.getDefaultInstance().copyWithCount(countLeft), 0,0);
                }

                stack.popPose();
            }
        }
    }

    private static void renderCannonLoadState(ForgeGui gui, GuiGraphics graphics, float partialTick, int width,
                                                      int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            Player player = mc.player;

            if (setup(gui, mc) && !player.isSpectator() && mc.options.getCameraType().isFirstPerson()) {
                Entity entity = FirmacivHelper.getAnyEntityAtCrosshair(player, 2f);
                PoseStack stack = graphics.pose();

                stack.pushPose();

                if (entity instanceof CannonEntity cannon) {
                    stack.translate((float) width / 2.0F, (float) height / 2.0F - 15.0F, 0.0F);
                    stack.scale(1.0F, 1.0F, 1.0F);

                    Item cannonBall = cannon.cannonBallItem;
                    Item gunpowder = cannon.gunpowderItem;
                    Item paper = cannon.paperItem;

                    if(cannon.getCannonball().getCount() == 0){
                        graphics.renderItem(cannonBall.getDefaultInstance(), 0,0);
                    }
                    if(cannon.getPaper().getCount() == 0){
                        graphics.renderItem(paper.getDefaultInstance(), 16,0);
                    }
                    if(cannon.getGunpowder().getCount() == 0){
                        graphics.renderItem(gunpowder.getDefaultInstance(), 32,0);
                    }
                    if(cannon.getPaper().getCount() == cannon.getCannonball().getCount() &&
                            cannon.getCannonball().getCount() == cannon.getGunpowder().getCount() &&
                            cannon.getGunpowder().getCount() == 1 && cannon.getFuseTime() < 0){
                        graphics.renderItem(Items.FLINT_AND_STEEL.getDefaultInstance(), 0,0);
                    }

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
                        String displayBoatSpeed = df.format(sloopEntity.getSmoothSpeedMS()*3.6) + " km/h";
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
                net.minecraft.world.entity.Entity entity = FirmacivHelper.getAnyEntityAtCrosshair(player, 4f);
                PoseStack stack = graphics.pose();

                stack.pushPose();

                if(entity instanceof AbstractFirmacivBoatEntity || entity instanceof VehicleCollisionEntity){
                    AbstractFirmacivBoatEntity vehicle;
                    if(entity instanceof VehicleCollisionEntity collider && collider.getRootVehicle() instanceof AbstractFirmacivBoatEntity){
                        entity = collider.getRootVehicle();
                    }
                    vehicle = (AbstractFirmacivBoatEntity)entity;
                    if(vehicle instanceof KayakEntity){
                        stack.popPose();
                        return;
                    }
                    for(ItemStack item : player.getHandSlots()){
                        if(item.is(vehicle.getDropItem())){
                            stack = setupCompartmentStack(stack, width, height);
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.HAMMER), 0, 9, 9);
                            break;
                        }
                        if(item.is(Tags.Items.DYES) || item.is(Items.WATER_BUCKET)){
                            stack = setupCompartmentStack(stack, width, height);
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.BRUSH), 0, 9, 9);
                            break;
                        }
                    }
                }

                if (entity instanceof EmptyCompartmentEntity emptyCompartmentEntity && emptyCompartmentEntity.isPassenger() && !emptyCompartmentEntity.isVehicle()) {
                    stack = setupCompartmentStack(stack, width, height);
                    if (emptyCompartmentEntity.getTrueVehicle() != null && emptyCompartmentEntity.getTrueVehicle().getPilotVehiclePartAsEntity() != null) {
                        if (emptyCompartmentEntity.getTrueVehicle().getPilotVehiclePartAsEntity().getFirstPassenger()
                                .is(emptyCompartmentEntity)) {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.HELM), 0, 9, 9);
                            if (emptyCompartmentEntity.getTrueVehicle() instanceof CanoeEntity && player.getItemInHand(
                                    player.getUsedItemHand()).is(FirmacivTags.Items.CAN_PLACE_IN_COMPARTMENTS)) {
                                graphics.blit(COMPARTMENT_ICONS, -12, 0, iconOffset(CompIcon.BLOCK), 0, 9, 9);
                            }

                        } else if (player.getItemInHand(player.getUsedItemHand())
                                .is(FirmacivTags.Items.CAN_PLACE_IN_COMPARTMENTS)) {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.BLOCK), 0, 9, 9);
                        } else if (player.getItemInHand(player.getUsedItemHand())
                                .is(FirmacivItems.CANNON.get()) && !emptyCompartmentEntity.canAddOnlyBLocks() && emptyCompartmentEntity.canAddCannons() && emptyCompartmentEntity.getRootVehicle() instanceof SloopEntity) {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.BLOCK), 0, 9, 9);
                        }else if (!emptyCompartmentEntity.isVehicle() && !emptyCompartmentEntity.canAddOnlyBLocks()) {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.SEAT), 0, 9, 9);
                        } else if (!emptyCompartmentEntity.isVehicle() && emptyCompartmentEntity.canAddOnlyBLocks()) {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.BLOCK), 0, 9, 9);
                        }
                    }
                } else if (entity instanceof VehicleCleatEntity vehicleCleatEntity  && vehicleCleatEntity.isPassenger() && !vehicleCleatEntity.isLeashed()) {
                    stack = setupCompartmentStack(stack, width, height);
                    if (vehicleCleatEntity.getVehicle().getVehicle() != null) {
                        graphics.blit(COMPARTMENT_ICONS, 0, 0, 54, 0, 9, 9);
                    }
                } else if (entity instanceof SailSwitchEntity sailSwitch  && sailSwitch.isPassenger()) {
                    stack = setupCompartmentStack(stack, width, height);
                    boolean flag = false;
                    for(ItemStack item : player.getHandSlots()){
                        if(item.is(Tags.Items.DYES) || item.is(Items.WATER_BUCKET)){
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.BRUSH), 0, 9, 9);
                            flag = true;
                            break;
                        }
                    }
                    if (sailSwitch.getVehicle().getVehicle() != null && !flag) {
                        if(sailSwitch.getSwitched()){
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.SAIL), 0, 9, 9);
                            graphics.blit(COMPARTMENT_ICONS, 0, 10, iconOffset(CompIcon.ARROW_DOWN), 0, 9, 9);
                        } else {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.SAIL), 0, 9, 9);
                            graphics.blit(COMPARTMENT_ICONS, 0, -10, iconOffset(CompIcon.ARROW_UP), 0, 9, 9);
                        }

                    }
                } else if (entity instanceof WindlassSwitchEntity windlassSwitch  && windlassSwitch.isPassenger()) {
                    stack = setupCompartmentStack(stack, width, height);
                    if (windlassSwitch.getVehicle().getVehicle() != null) {
                        if(!windlassSwitch.getSwitched()){
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.ANCHOR), 0, 9, 9);
                            graphics.blit(COMPARTMENT_ICONS, 0, 10, iconOffset(CompIcon.ARROW_DOWN), 0, 9, 9);
                        } else {
                            graphics.blit(COMPARTMENT_ICONS, 0, 0, iconOffset(CompIcon.ANCHOR), 0, 9, 9);
                            graphics.blit(COMPARTMENT_ICONS, 0, -10, iconOffset(CompIcon.ARROW_UP), 0, 9, 9);
                        }

                    }
                }

                stack.popPose();
            }
        }
    }

    public static PoseStack setupCompartmentStack(PoseStack stack, int width, int height){
        stack.scale(1.0F, 1.0F, 1.0F);
        stack.translate((float) width / 2.0F - 5f - 12f, (float) height / 2.0F - 5F, 0.0F);
        if ((float) height % 2.0 != 0) {
            stack.translate(0f, 0.5f, 0.0f);
        }
        if ((float) width % 2.0 != 0) {
            stack.translate(0.5f, 0f, 0.0f);
        }
        return stack;
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
