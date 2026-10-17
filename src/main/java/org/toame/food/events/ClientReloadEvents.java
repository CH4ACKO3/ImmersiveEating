package org.toame.food.events;

import org.toame.food.Food;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;

@Mod.EventBusSubscriber(modid = Food.MODID, value = Dist.CLIENT)
public class ClientReloadEvents {

    private static boolean reloaded = false;


    @SubscribeEvent

    public static void onClientPlayerLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.getPlayer() != mc.player) {
            return;
        }
        if (reloaded) {
            return;
        }
        reloaded = true;
    }
}
