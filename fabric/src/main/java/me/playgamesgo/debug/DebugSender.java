package me.playgamesgo.debug;

import me.playgamesgo.debug.network.DebugShapesPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class DebugSender {
    /**
     * Sends a debug message to the specified audience.
     *
     * @param msg      The debug message to send.
     * @param audience The audience to send the message to.
     */
    public static void send(DebugMessage msg, ServerPlayerEntity audience) {
        ServerPlayNetworking.send(audience, new DebugShapesPacket(msg.ops()));
    }
}
