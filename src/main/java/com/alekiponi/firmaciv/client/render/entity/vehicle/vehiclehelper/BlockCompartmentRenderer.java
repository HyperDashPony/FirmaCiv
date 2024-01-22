package com.alekiponi.firmaciv.client.render.entity.vehicle.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

/**
 * Generic unspecialized renderer for compartments that render normal blocks such as a furnace or barrel
 */
public class BlockCompartmentRenderer extends CompartmentRenderer<AbstractCompartmentEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public BlockCompartmentRenderer(final EntityRendererProvider.Context context) {
        super(context);

        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    protected void renderCompartmentContents(final AbstractCompartmentEntity compartmentEntity,
            final float partialTicks, final PoseStack poseStack, final MultiBufferSource bufferSource,
            final int packedLight) {
        //noinspection deprecation
        this.blockRenderer.renderSingleBlock(compartmentEntity.getDisplayBlockState(), poseStack, bufferSource,
                packedLight, OverlayTexture.NO_OVERLAY);
    }
}