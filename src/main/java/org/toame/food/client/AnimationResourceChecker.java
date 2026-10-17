package org.toame.food.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.toame.food.Food;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class AnimationResourceChecker {
    private AnimationResourceChecker() {}
    public static boolean hasAnimation(ItemStack stack, String animationName) {
        if (stack == null || stack.isEmpty() || animationName == null || animationName.isBlank()) {
            return false;
        }
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        ResourceLocation animationResource = new ResourceLocation(Food.MODID, "animations/" + itemId.getPath() + ".animation.json");

        return Minecraft.getInstance().getResourceManager().getResource(animationResource).map(resource -> {
                    try (Reader reader = new InputStreamReader(resource.open(), StandardCharsets.UTF_8)) {
                        JsonElement root = JsonParser.parseReader(reader);
                        if (!root.isJsonObject()) {
                            return false;
                        }

                        JsonObject animations = root.getAsJsonObject().getAsJsonObject("animations");
                        return animations != null && animations.has(animationName);
                    } catch (Exception ignored) {
                        return false;
                    }
                })
                .orElse(false);
    }
}

