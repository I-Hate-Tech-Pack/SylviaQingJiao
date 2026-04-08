package cn.howxu.sqj.client.render;

import cn.howxu.sqj.SylviaQingJiao;
import cn.howxu.sqj.client.model.SwordModel;
import cn.howxu.sqj.client.registry.ClientEvents;
import cn.howxu.sqj.common.item.Sword;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SwordRenderer extends BlockEntityWithoutLevelRenderer {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(SylviaQingJiao.MODID, "textures/item/sword.png");
    private SwordModel model;

    public SwordRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    private SwordModel getModel() {
        if (this.model == null) {
            this.model = new SwordModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientEvents.SWORD_LAYER));
        }
        return this.model;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);

        // --- 动画核心逻辑开始 ---
        // 只有在第一人称或第三人称时，才需要拉弦动画
        if (displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND ) {
            Entity player = Minecraft.getInstance().cameraEntity;
            if (player instanceof LivingEntity living && living.getUseItem() == stack) {
                // 计算当前蓄力了多久
                int useTicks = living.getTicksUsingItem();
                float pull = Sword.getUseTime(useTicks);
                // 示例：拉弦时，剑向后平移并微微旋转
                // pull 是 0.0 到 1.0
                poseStack.translate(0, 0, -pull * 0.2F); // 向后拉动 0.2 格
                if (pull > 0.9F) {
                    poseStack.translate((living.getRandom().nextFloat() - 0.5F) * 0.02F, 0, 0);
                }

            }
        }
        // --- 动画核心逻辑结束 ---

        poseStack.mulPose(Axis.XP.rotationDegrees(180F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180F));
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        getModel().renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 0xFFFFFFFF);
        poseStack.popPose();
    }
}