package cn.howxu.sqj;

import cn.howxu.sqj.common.registry.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(SylviaQingJiao.MODID)
public class SylviaQingJiao {
    public static final String MODID = "sqj";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SylviaQingJiao(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        modEventBus.addListener(ModItems::addTabs);
    }
}
