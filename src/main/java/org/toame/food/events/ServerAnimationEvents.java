package org.toame.food.events;

import org.toame.food.Food;
import org.toame.food.init.ModItems;
import org.toame.food.network.ServerAnimationManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;

@Mod.EventBusSubscriber(modid = Food.MODID)
public final class ServerAnimationEvents {

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        ServerAnimationManager.clear(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (event.phase != net.minecraftforge.event.TickEvent.Phase.END || !(event.player instanceof ServerPlayer sp) || sp.level().isClientSide) {
            return;
        }
        ServerAnimationManager.AnimationState state = ServerAnimationManager.get(sp);
        if (state == null || ServerAnimationManager.matchesHeldStack(sp)) {
            return;
        }

        stopAndClear(sp);
    }
    private static void stopAndClear(ServerPlayer player) {
        ServerAnimationManager.AnimationState state = ServerAnimationManager.get(player);
        if (state != null && state.controllerId() != Long.MAX_VALUE) {
            ModItems.EMPTY.get().stopTriggeredAnim(player, state.controllerId(), "eat", "eat");
        }
        ServerAnimationManager.clear(player);
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        ServerAnimationManager.clearAll();
    }
}
