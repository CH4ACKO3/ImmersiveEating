package org.toame.food.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.toame.food.additions.definiton.FoodDefinitionManager;

import static org.toame.food.compat.Helper.isCustomItem;

public class Punchy {
    public static void disablePunchyForCustomItem(CallbackInfoReturnable<Boolean> cir) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        /*
        1.是模组自定义的物品
        2.不在使用
         */
        if (isCustomItem(mc.player.getMainHandItem())&&!mc.player.isUsingItem()) {
            cir.setReturnValue(false);
        }
    }
}
