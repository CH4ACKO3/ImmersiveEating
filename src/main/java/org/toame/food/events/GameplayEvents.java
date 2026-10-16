package org.toame.food.events;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.toame.food.Food;
import org.toame.food.additions.Empty;
import org.toame.food.additions.definiton.FoodDefinitionManager;
import org.toame.food.network.Network;
import org.toame.food.network.packet.AnimationPacket;
import org.toame.food.utils.AnimationUtils;

import static org.toame.food.config.Config.ENABLE_RIGHT_BUTTON;

@Mod.EventBusSubscriber(modid = Food.MODID, value = Dist.CLIENT)
public final class GameplayEvents {

    @SubscribeEvent
    public static void onRightClick(InputEvent.InteractionKeyMappingTriggered event) {
        Minecraft mc = Minecraft.getInstance();
        if (!ENABLE_RIGHT_BUTTON.get() || mc.player == null || mc.player.isCrouching() || !event.isUseItem()) {
            return;
        }

        ItemStack stack = mc.player.getMainHandItem();
        if (!FoodDefinitionManager.init_ItemList.contains(stack.getItem())) {
            return;
        }
        if (Empty.isClientAnimationPlaying(stack,"switchin")){
            return;
        }
        event.setCanceled(true);
        startRightClickAnimation(stack);
    }

    public static boolean startRightClickAnimation() {
        Minecraft mc = Minecraft.getInstance();
        return mc.player != null && startRightClickAnimation(mc.player.getMainHandItem());
    }

    private static boolean startRightClickAnimation(ItemStack stack) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || AnimationUtils.isAnimationLocked()
                || AnimationUtils.isAnimationRequestPending()) {
            return false;
        }

        AnimationUtils.beginAnimationSession(mc);
        Network.CHANNEL.sendToServer(new AnimationPacket(AnimationUtils.getAnimationSessionId()));
        return true;
    }
}
