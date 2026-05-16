package me.playgamesgo.debug;

import net.kyori.adventure.audience.Audience;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.common.PluginMessagePacket;
import net.minestom.server.utils.PacketSendingUtils;

public class DebugSender {
    /**
     * Sends a debug message to the specified audience.
     *
     * @param msg      The debug message to send.
     * @param audience The audience to send the message to.
     */
    public static void send(DebugMessage msg, Audience audience) {
        PluginMessagePacket packet = new PluginMessagePacket("debug:shapes",
                NetworkBuffer.makeArray(buffer -> msg.encode(new MinestomDebugWriter(buffer))));
        PacketSendingUtils.sendPacket(audience, packet);
    }
}
