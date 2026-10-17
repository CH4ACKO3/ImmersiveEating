package org.toame.food;

import com.mojang.logging.LogUtils;
import org.toame.food.additions.definiton.FoodDefinitionManager;
import org.toame.food.init.ModItems;
import org.toame.food.network.Network;
import net.minecraftforge.event.AddReloadListenerEvent;
import org.toame.food.config.Config;
import org.toame.food.unique.UniqueItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;


@Mod(Food.MODID)
public class Food {
    public static final String MODID = "food";
    public static final String AUTHOR = "XianYue";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static boolean DEBUG = false;


    public Food() {
        IEventBus modEventBus = net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.addListener(Food::registerReloadListeners);
        ModItems.register(modEventBus);
        UniqueItem.initUniqueItemMap();
        net.minecraftforge.fml.ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, "immersive_eating.toml");
        Network.register();
    }

    public static void registerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new FoodDefinitionManager());
    }
}
