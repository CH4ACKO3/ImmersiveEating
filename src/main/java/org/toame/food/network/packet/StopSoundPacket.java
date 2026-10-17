package org.toame.food.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import org.toame.food.client.ClientSoundHandler;
import java.util.UUID;
import java.util.function.Supplier;

public record StopSoundPacket(UUID sessionId, ResourceLocation soundId) {
    public static void encode(StopSoundPacket packet, FriendlyByteBuf buffer) { buffer.writeUUID(packet.sessionId); buffer.writeResourceLocation(packet.soundId); }
    public static StopSoundPacket decode(FriendlyByteBuf buffer) { return new StopSoundPacket(buffer.readUUID(), buffer.readResourceLocation()); }
    public static void handle(StopSoundPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> ClientSoundHandler.stop(packet));
        context.setPacketHandled(true);
    }
}

