package cn.howxu.sqj.common.registry;

import cn.howxu.sqj.SylviaQingJiao;
import cn.howxu.sqj.common.item.Sword;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/4/8 12:58
 */
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SylviaQingJiao.MODID);

    public static final DeferredItem<Item> SWORD = ITEMS.registerItem("sword", Sword::new);

    public static void register(IEventBus modBus){
        ITEMS.register(modBus);
    }

    @SubscribeEvent
    public static void addTabs(BuildCreativeModeTabContentsEvent event){
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(SWORD.get());
        }
    }
}
