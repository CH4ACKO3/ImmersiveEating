package org.toame.food.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public record CustomUsingPacket( InteractionHand hand, Item customItem, int count) {
    public static void encode(CustomUsingPacket packet, FriendlyByteBuf buffer) {
        buffer.writeEnum(packet.hand);
        buffer.writeResourceLocation(ForgeRegistries.ITEMS.getKey(packet.customItem));
        buffer.writeInt(packet.count);
    }

    public static CustomUsingPacket decode(FriendlyByteBuf buffer) {
        InteractionHand hand = buffer.readEnum(InteractionHand.class);
        ResourceLocation itemId = buffer.readResourceLocation();
        return new CustomUsingPacket( hand, ForgeRegistries.ITEMS.getValue(itemId), buffer.readInt());
    }

    public static void handle(CustomUsingPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sp = context.getSender();
            if (packet.customItem == null || packet.customItem == net.minecraft.world.item.Items.AIR) {
                return;
            }

            ItemStack stack = new ItemStack(packet.customItem, packet.count);
            if (!stack.isEdible() || stack.getUseDuration() <= 0) {
                return;
            }

            stack.finishUsingItem(sp.level(), sp);
        });
        context.setPacketHandled(true);
    }
}
