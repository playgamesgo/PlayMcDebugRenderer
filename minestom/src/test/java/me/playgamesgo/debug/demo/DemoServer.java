package me.playgamesgo.debug.demo;

import me.playgamesgo.debug.DebugMessage;
import me.playgamesgo.debug.DebugSender;
import me.playgamesgo.debug.data.DebugPos;
import me.playgamesgo.debug.data.RenderLayer;
import me.playgamesgo.debug.shape.LineShape;
import me.playgamesgo.debug.shape.Shape;
import me.playgamesgo.debug.shape.SplineShape;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;

public class DemoServer {
    static void main() {
        var server = MinecraftServer.init();

        var instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        instance.setGenerator(unit -> unit.modifier().fillHeight(-10, -1, Block.STONE));
        instance.setChunkSupplier(LightingChunk::new);

        MinecraftServer.getGlobalEventHandler()
                .addListener(AsyncPlayerConfigurationEvent.class, event -> {
                    event.setSpawningInstance(instance);
                    event.getPlayer().setRespawnPoint(new Pos(0, 0, 0));
                })
                .addListener(PlayerSpawnEvent.class, DemoServer::onPlayerSpawn);

        server.start("0.0.0.0", 25565);
    }

    private static void onPlayerSpawn(PlayerSpawnEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.CREATIVE);
        player.setPermissionLevel(4);

        DebugMessage.Builder builder = DebugMessage.builder();
        builder.set("debug:test_box", Shape.box()
                .start(new DebugPos(10, 10, 10))
                .end(new DebugPos(20, 20, 20))
                .faceColor(0x66FF0000)
                .faceLayer(RenderLayer.MIXED)
                .edgeColor(0xFF00FF00)
                .edgeLayer(RenderLayer.TOP)
                .edgeWidth(4f)
                .build());


        DebugMessage debugMessage = builder.set("debug:test_quad", Shape.quad()
                        .a(new DebugPos(0, 0, 0))
                        .b(new DebugPos(2, 0, 0))
                        .c(new DebugPos(2, 2, 0))
                        .d(new DebugPos(0, 2, 0))
                        .color(0xFFFF0000)
                        .renderLayer(RenderLayer.MIXED)
                        .build())
                .set("debug:test_line", Shape.line()
                        .type(LineShape.Type.STRIP)
                        .point(new DebugPos(0, 0, 0))
                        .point(new DebugPos(5, 5, 5))
                        .point(new DebugPos(-5, 5, 5))
                        .color(0xFF00FF00)
                        .lineWidth(4f)
                        .layer(RenderLayer.MIXED)
                        .build())
                .set("debug:test_bezier", Shape.spline()
                        .type(SplineShape.Type.BEZIER)
                        .point(new DebugPos(0, 0, 0))
                        .point(new DebugPos(3, 5, 5))
                        .point(new DebugPos(-5, 5, 5))
                        .color(0xFF0000FF)
                        .lineWidth(4f)
                        .layer(RenderLayer.MIXED)
                        .build())
                .set("debug:test_catmull_rom", Shape.spline()
                        .type(SplineShape.Type.CATMULL_ROM)
                        .point(new DebugPos(0, 0, 0))
                        .point(new DebugPos(5, 5, 5))
                        .point(new DebugPos(-5, 5, 5))
                        .color(0xFF000000)
                        .lineWidth(4f)
                        .layer(RenderLayer.MIXED)
                        .build())
                .set("debug:test_catmull_rom_loop", Shape.spline()
                        .type(SplineShape.Type.CATMULL_ROM)
                        .point(new DebugPos(0, 0, 0))
                        .point(new DebugPos(-5, 5, -5))
                        .point(new DebugPos(-5, 5, 5))
                        .color(0xFFFF00FF)
                        .lineWidth(4f)
                        .loop(true)
                        .layer(RenderLayer.MIXED)
                        .build())
                .build();

        DebugSender.send(debugMessage, player);
    }
}
