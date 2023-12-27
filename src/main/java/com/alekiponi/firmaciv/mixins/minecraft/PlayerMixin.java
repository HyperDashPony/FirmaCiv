package com.alekiponi.firmaciv.mixins.minecraft;


import com.alekiponi.firmaciv.common.entity.vehiclehelper.AbstractCompartmentEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity{

    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public abstract void causeFoodExhaustion(float pExhaustion);

    @Shadow
    public abstract void resetAttackStrengthTicker();

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void injectAttackSoundCancellation(Entity pTarget, CallbackInfo ci){
        if(pTarget instanceof AbstractCompartmentEntity){
            if (!net.minecraftforge.common.ForgeHooks.onPlayerAttackTarget((Player)(Object)this, pTarget))
            {
                ci.cancel();
            }
            float damage = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float f1 = 0;

            float f2 = 1.0f;
            damage *= 0.2F + f2 * f2 * 0.8F;
            f1 *= f2;
            if (damage > 0.0F || f1 > 0.0F) {
                boolean flag5 = pTarget.hurt(this.damageSources().playerAttack((Player)(Object)this), damage);
                if (flag5) {
                    this.setLastHurtMob(pTarget);
                    this.causeFoodExhaustion(0.1F);
                }
            }
            this.resetAttackStrengthTicker();
            ci.cancel();
        }
    }


}
