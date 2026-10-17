package org.toame.food.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.toame.food.additions.definiton.FoodDefinitionManager;
import org.toame.food.utils.AnimationControllerIds;
import org.toame.food.init.ModItems;
import org.toame.food.network.ServerAnimationManager;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationController;

import java.util.UUID;
import java.util.function.Supplier;

public record AnimationPacket(UUID sessionId) {
    public static void encode(AnimationPacket packet, FriendlyByteBuf buffer) {
        buffer.writeUUID(packet.sessionId);
    }

    public static AnimationPacket decode(FriendlyByteBuf buffer) {
        return new AnimationPacket(buffer.readUUID());
    }

    public static void handle(AnimationPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sp = context.getSender();
            if (sp == null || packet.sessionId() == null) {
                return;
            }
            ItemStack stack = sp.getMainHandItem();
            if (stack.isEmpty() || stack.is(ModItems.EMPTY.get()) || !FoodDefinitionManager.init_ItemList.contains(stack.getItem())) {
                return;
            }
            long id = AnimationControllerIds.forPlayer(sp.getUUID());
            AnimationController<GeoAnimatable> controller = ModItems.EMPTY.get().getAnimatableInstanceCache()
                    .getManagerForId(id).getAnimationControllers().get("eat");
            if (controller != null && controller.isPlayingTriggeredAnimation()) {
                return;
            }
            if (!ServerAnimationManager.begin(sp, packet.sessionId(), InteractionHand.MAIN_HAND, stack, id)) {
                return;
            }
            ModItems.EMPTY.get().triggerAnim(sp, id, "eat", "eat");
        });
        context.setPacketHandled(true);
    }
}
