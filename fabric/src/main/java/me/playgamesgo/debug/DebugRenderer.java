package me.playgamesgo.debug;

import me.playgamesgo.debug.client.shape.FabricShape;
import me.playgamesgo.debug.network.DebugHelloPacket;
import me.playgamesgo.debug.network.DebugShapesPacket;
import me.playgamesgo.debug.client.render.ClientRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class DebugRenderer implements ClientModInitializer, ModInitializer {
    public static final int PROTOCOL_VERSION = 3;
    private ClientRenderer renderer;

    @Override
    public void onInitializeClient() {
        renderer = new ClientRenderer();

        // Setup listeners
        ClientPlayConnectionEvents.JOIN.register(this::handleJoinGame);
        ClientPlayConnectionEvents.DISCONNECT.register(this::handleDisconnect);

        WorldRenderEvents.END_MAIN.register(this::handleRender);
        ClientPlayNetworking.registerGlobalReceiver(DebugShapesPacket.PACKET_ID, this::handlePacket);
    }

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(DebugHelloPacket.PACKET_ID, DebugHelloPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(DebugShapesPacket.PACKET_ID, DebugShapesPacket.PACKET_CODEC);
    }

    private void handleRender(WorldRenderContext ctx) {
        renderer.render(ctx.matrices(), MinecraftClient.getInstance().gameRenderer.getCamera(), ctx.consumers());
    }

    private void handleJoinGame(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        sender.sendPacket(new DebugHelloPacket(PROTOCOL_VERSION));
    }

    private void handleDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
        renderer.clear();
    }

    private void handlePacket(@NotNull DebugShapesPacket packet, @NotNull ClientPlayNetworking.Context context) {
        for (var operation : packet.operations()) {
            switch (operation) {
                case Operation.Set op -> renderer.add(Identifier.of(op.id()), ((FabricShape) op.shape()));
                case Operation.Remove op -> renderer.remove(Identifier.of(op.id()));
                case Operation.ClearNameSpace op -> renderer.remove(op.namespace());
                case Operation.Clear op -> renderer.clear();
                default -> throw new IllegalStateException("Unexpected value: " + operation);
            }
        }
    }
}