package com.alekiponi.firmaciv.mixins.tfc;

import com.alekiponi.firmaciv.events.config.FirmacivConfig;
import net.dries007.tfc.common.entities.misc.TFCBoat;
import net.dries007.tfc.common.items.TFCBoatItem;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Mixin(TFCBoatItem.class)
public class TFCBoatItemMixin extends BoatItem {

    @Shadow
    private final Supplier<? extends EntityType<TFCBoat>> boat;

    public TFCBoatItemMixin(boolean pHasChest, Boat.Type pType, Properties pProperties, Supplier<? extends EntityType<TFCBoat>> boat) {
        super(pHasChest, pType, pProperties);
        this.boat = boat;
    }

    @Shadow
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        HitResult hitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

        if (FirmacivConfig.SERVER.disableVanillaBoatFunctionality.get()) {
            return InteractionResultHolder.pass(itemstack);
        }

        if (hitresult.getType() == net.minecraft.world.phys.HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vec3 = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0)).inflate(1.0), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 vec31 = player.getEyePosition();
                Iterator var9 = list.iterator();

                while(var9.hasNext()) {
                    Entity entity = (Entity)var9.next();
                    AABB aabb = entity.getBoundingBox().inflate((double)entity.getPickRadius());
                    if (aabb.contains(vec31)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (hitresult.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
                TFCBoat boat = (TFCBoat)((EntityType)this.boat.get()).create(level);
                if (boat != null) {
                    boat.setPos(hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z);
                    boat.setYRot(player.getYRot());
                    if (!level.noCollision(boat, boat.getBoundingBox().inflate(-0.1))) {
                        return InteractionResultHolder.fail(itemstack);
                    } else {
                        if (!level.isClientSide) {
                            level.addFreshEntity(boat);
                            level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());
                            if (!player.getAbilities().instabuild) {
                                itemstack.shrink(1);
                            }
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                    }
                } else {
                    return InteractionResultHolder.fail(itemstack);
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }

}
