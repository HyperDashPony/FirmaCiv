//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.hyperdash.firmaciv.client;

import com.hyperdash.firmaciv.Firmaciv;
import com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities.EmptyCompartmentEntity;
import com.hyperdash.firmaciv.entity.custom.VehicleHelperEntities.VehicleCleatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dries007.tfc.common.entities.livestock.MammalProperties;
import net.dries007.tfc.common.entities.livestock.TFCAnimalProperties;
import net.dries007.tfc.common.entities.livestock.TFCAnimalProperties.Age;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.Locale;

@Mod.EventBusSubscriber(modid = Firmaciv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public enum IngameOverlays {
    COMPARTMENT_STATUS(IngameOverlays::renderCompartmentStatus);

    public static final ResourceLocation TEXTURE = Helpers.identifier("textures/gui/icons/overlay.png");
    final IGuiOverlay overlay;
    private final String id;

    IngameOverlays(IGuiOverlay overlay) {
        this.id = this.name().toLowerCase(Locale.ROOT);
        this.overlay = overlay;
    }


    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        above(event, VanillaGuiOverlay.CROSSHAIR, COMPARTMENT_STATUS);
    }

    private static void above(RegisterGuiOverlaysEvent event, VanillaGuiOverlay vanilla, IngameOverlays overlay) {
        event.registerAbove(vanilla.id(), overlay.id, overlay.overlay);
    }

    private static void renderCompartmentStatus(ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            Player player = mc.player;
            if (setup(gui, mc)) {
                Entity entity = mc.crosshairPickEntity;
                PoseStack stack = graphics.pose();
                stack.pushPose();
                String string = "Pilot Seat";
                graphics.drawString(mc.font, string, -mc.font.width(string) / 2, 0, Color.WHITE.getRGB(), false);
                stack.popPose();
                if (entity instanceof EmptyCompartmentEntity) {

                } else if (entity instanceof VehicleCleatEntity) {
                    TFCAnimalProperties animal = (TFCAnimalProperties) entity;
                    if (animal.getAdultFamiliarityCap() > 0.0F && player.closerThan(entity, 5.0)) {
                        stack = graphics.pose();
                        stack.pushPose();
                        stack.translate((float) width / 2.0F, (float) height / 2.0F - 45.0F, 0.0F);
                        stack.scale(1.5F, 1.5F, 1.5F);
                        float familiarity = Math.max(0.0F, Math.min(1.0F, animal.getFamiliarity()));
                        short u;
                        int fontColor;
                        if (familiarity >= animal.getAdultFamiliarityCap() && animal.getAgeType() != Age.CHILD) {
                            u = 132;
                            fontColor = Color.RED.getRGB();
                        } else if (familiarity >= 0.3F) {
                            u = 112;
                            fontColor = Color.WHITE.getRGB();
                        } else {
                            u = 92;
                            fontColor = Color.GRAY.getRGB();
                        }

                        if (TFCConfig.CLIENT.displayFamiliarityAsPercent.get()) {
                            string = String.format("%.2f", familiarity * 100.0F);
                            graphics.drawString(mc.font, string, -mc.font.width(string) / 2, 0, fontColor, false);
                        } else {
                            graphics.blit(TEXTURE, -8, 0, u, 40, 16, 16);
                            stack.translate(0.0F, 0.0F, -0.001F);
                            graphics.blit(TEXTURE, -6, 14 - (int) (12.0F * familiarity), familiarity == 1.0F ? 114 : 94, 74 - (int) (12.0F * familiarity), 12, (int) (12.0F * familiarity));
                        }

                        if (animal instanceof MammalProperties mammal) {
                            if (mammal.getPregnantTime() > 0L && mammal.isFertilized()) {
                                stack.translate(0.0F, -15.0F, 0.0F);
                                string = Component.translatable("tfc.tooltip.animal.pregnant", new Object[]{entity.getName().getString()}).getString();
                                graphics.drawString(mc.font, string, -mc.font.width(string) / 2, 0, Color.WHITE.getRGB(), false);
                            }
                        }

                        stack.popPose();
                    }
                }
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
