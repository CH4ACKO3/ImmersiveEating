package org.toame.food.mixin;

import net.minecraft.client.gui.GuiGraphics;
import org.toame.food.config.Config;
import net.minecraft.client.gui.Gui;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Gui.class)
public abstract class GuiMixin {
    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void food$hideHotbar(float pPartialTick, GuiGraphics pGuiGraphics, CallbackInfo ci) {
        if (Config.HIDE_HOTBAR) {
            ci.cancel();
        }
    }
}
