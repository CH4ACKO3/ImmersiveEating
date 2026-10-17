package org.toame.food.init;

import org.toame.food.Food;
import org.toame.food.additions.Empty;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Food.MODID);


    /**
     * 注册模组事件、网络或资源处理器。
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final Supplier<Empty> EMPTY = ITEMS.register("empty", () -> new Empty(new Item.Properties()));

}
