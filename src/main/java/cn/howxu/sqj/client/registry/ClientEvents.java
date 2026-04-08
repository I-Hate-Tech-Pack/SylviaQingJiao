package cn.howxu.sqj.client.registry;

import cn.howxu.sqj.SylviaQingJiao;
import cn.howxu.sqj.client.model.SwordModel;
import cn.howxu.sqj.client.render.SwordRenderer;
import cn.howxu.sqj.common.registry.ModItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@EventBusSubscriber(modid = SylviaQingJiao.MODID, value = Dist.CLIENT)
public class ClientEvents {
    public static final ModelLayerLocation SWORD_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(SylviaQingJiao.MODID, "sword"), "main"
    );

    private static final SwordRenderer render = new SwordRenderer();


    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SWORD_LAYER, SwordModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return render;
            }
        }, ModItems.SWORD.get());
    }
}