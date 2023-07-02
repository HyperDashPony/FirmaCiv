package com.hyperdash.firmaciv.mixins;

import com.hyperdash.firmaciv.item.FirmacivItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin implements ResourceManagerReloadListener {

    @Shadow
    private final ItemModelShaper itemModelShaper;

    public ItemRendererMixin(ItemModelShaper itemModelShaper) {
        this.itemModelShaper = itemModelShaper;
    }

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {
    }

    /*
    @Inject(method = "render", at = @At("HEAD"))
    void injectRenderChange(ItemStack pItemStack, ItemTransforms.TransformType pTransformType, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, int pCombinedOverlay, BakedModel pModel, CallbackInfo ci) {
        if (!pItemStack.isEmpty()) {
            pPoseStack.pushPose();
            boolean flag = pTransformType == ItemTransforms.TransformType.GUI || pTransformType == ItemTransforms.TransformType.FIXED;
            if (flag) {
                if (pItemStack.is(FirmacivItems.CANOE_PADDLE.get())) {
                    pModel = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("firmaciv:canoe_paddle#inventory"));
                } else if (pItemStack.is(FirmacivItems.KAYAK_PADDLE.get())) {
                    pModel = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("minecraft:kayak_paddle#inventory"));
                }
            }
        }
    }


    @Inject(method = "getModel", at = @At("HEAD"))
    public void getModel(ItemStack p_174265_, Level pLevel, LivingEntity p_174267_, int p_174268_, CallbackInfoReturnable<BakedModel> cir) {
        BakedModel bakedmodel;
        if (p_174265_.is(FirmacivItems.CANOE_PADDLE.get())) {
            bakedmodel = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("firmaciv:in_hand/canoe_paddle#inventory"));
        } else if (p_174265_.is(FirmacivItems.KAYAK_PADDLE.get())) {
            bakedmodel = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("firmaciv:in_hand/kayak_paddle#inventory"));
        }

    }

*/

}
