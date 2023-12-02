package com.alekiponi.firmaciv.client;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.render.entity.CanoeRenderer;
import com.alekiponi.firmaciv.common.entity.BoatVariant;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.item.AbstractNavItem;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;

public class FirmacivClientEvents {

    public static void init() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(FirmacivClientEvents::clientSetup);
        bus.addListener(IngameOverlays::registerOverlays);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {

        for (BoatVariant variant : BoatVariant.values()) {
            EntityRenderers.register(FirmacivEntities.CANOES.get(variant).get(), CanoeRenderer::new);
        }


        ItemProperties.register(FirmacivItems.BAROMETER.get(), new ResourceLocation(Firmaciv.MOD_ID, "altitude"),
                new ClampedItemPropertyFunction() {
                    private double rotation;
                    private double rota;
                    private long lastUpdateTick;

                    public float unclampedCall(ItemStack pStack, @Nullable ClientLevel pLevel,
                                               @Nullable LivingEntity livingEntity, int p_174668_) {

                        Entity entity = livingEntity != null ? livingEntity : pStack.getEntityRepresentation();
                        if (entity == null) {
                            return 0.0F;
                        } else {
                            if (pLevel == null && entity.level() instanceof ClientLevel) {
                                pLevel = (ClientLevel) entity.level();
                            }

                            if (pLevel == null) {
                                return 0.0F;
                            } else {
                                double height;
                                if (pLevel.dimensionType().natural()) {
                                    assert livingEntity != null;
                                    height = (entity.getY() + 64) / (pLevel.getHeight());
                                } else {
                                    height = Math.random();
                                }

                                return (float) height;
                            }
                        }
                    }
                });


        ItemProperties.register(FirmacivItems.FIRMACIV_COMPASS.get(), new ResourceLocation(Firmaciv.MOD_ID, "firmaciv_compass_direction"),
                new ClampedItemPropertyFunction() {
                    private double rotation;
                    private double rota;
                    private long lastUpdateTick;

                    public float unclampedCall(ItemStack pStack, @Nullable ClientLevel pLevel,
                                               @Nullable LivingEntity livingEntity, int p_174668_) {

                        Entity entity = livingEntity != null ? livingEntity : pStack.getEntityRepresentation();
                        if (entity == null) {
                            return 0.0F;
                        } else {
                            if (pLevel == null && entity.level() instanceof ClientLevel) {
                                pLevel = (ClientLevel) entity.level();
                            }

                            if (pLevel == null) {
                                return 0.0F;
                            } else {
                                double direction;
                                if (pLevel.dimensionType().natural()) {
                                    assert livingEntity != null;
                                    direction = ((Mth.wrapDegrees(entity.getYRot()) + 180)%360)/360;
                                } else {
                                    direction = Math.random();
                                }

                                return (float) direction; //(2*3.1415926535f)
                            }
                        }
                    }
                });


        ItemProperties.register(FirmacivItems.NAV_CLOCK.get(), new ResourceLocation(Firmaciv.MOD_ID, "pm_time"),
                new ClampedItemPropertyFunction() {
                    private double rotation;
                    private double rota;
                    private long lastUpdateTick;

                    public float unclampedCall(ItemStack p_174665_, @Nullable ClientLevel p_174666_,
                                               @Nullable LivingEntity livingEntity, int p_174668_) {
                        net.minecraft.world.entity.Entity entity = livingEntity != null ? livingEntity : p_174665_.getEntityRepresentation();
                        if (entity == null) {
                            return 0.0F;
                        } else {
                            if (p_174666_ == null && entity.level() instanceof ClientLevel) {
                                p_174666_ = (ClientLevel) entity.level();
                            }

                            double longitude = Math.abs((AbstractNavItem.getNavLocation(
                                    entity.getEyePosition())[AbstractNavItem.NavSelection.LONGITUDE.ordinal()] % 180) / 180);

                            if (p_174666_ == null) {
                                return 0.0F;
                            } else {
                                double time;
                                if (p_174666_.dimensionType().natural()) {
                                    time = p_174666_.getTimeOfDay(1.0F);
                                    time += longitude;
                                    time %= 1.0F;
                                } else {
                                    time = Math.random();
                                }

                                time = this.wobble(p_174666_, time);
                                return (float) time;
                            }
                        }
                    }


                    private double wobble(Level p_117904_, double p_117905_) {
                        if (p_117904_.getGameTime() != this.lastUpdateTick) {
                            this.lastUpdateTick = p_117904_.getGameTime();
                            double d0 = p_117905_ - this.rotation;
                            d0 = Mth.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                            this.rota += d0 * 0.1D;
                            this.rota *= 0.9D;
                            this.rotation = Mth.positiveModulo(this.rotation + this.rota, 1.0D);
                        }

                        return this.rotation;
                    }
                });

    }
}
