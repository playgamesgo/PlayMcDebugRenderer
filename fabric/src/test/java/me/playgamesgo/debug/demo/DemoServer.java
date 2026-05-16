package me.playgamesgo.debug.demo;

import me.playgamesgo.debug.DebugMessage;
import me.playgamesgo.debug.DebugSender;
import me.playgamesgo.debug.data.DebugPos;
import me.playgamesgo.debug.data.RenderLayer;
import me.playgamesgo.debug.shape.LineShape;
import me.playgamesgo.debug.shape.Shape;
import me.playgamesgo.debug.shape.SplineShape;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public class DemoServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        ServerPlayerEvents.JOIN.register(player -> {
            DebugMessage.Builder builder = DebugMessage.builder();
            builder.set("debug:test_box", Shape.box()
                    .start(new DebugPos(10, 110, 10))
                    .end(new DebugPos(20, 120, 20))
                    .faceColor(0x66FF0000)
                    .faceLayer(RenderLayer.MIXED)
                    .edgeColor(0xFF00FF00)
                    .edgeLayer(RenderLayer.TOP)
                    .edgeWidth(4f)
                    .build());

            DebugMessage debugMessage = builder.set("debug:test_quad", Shape.quad()
                            .a(new DebugPos(0, 100, 0))
                            .b(new DebugPos(2, 100, 0))
                            .c(new DebugPos(2, 102, 0))
                            .d(new DebugPos(0, 102, 0))
                            .color(0xFFFF0000)
                            .renderLayer(RenderLayer.MIXED)
                            .build())
                    .set("debug:test_line", Shape.line()
                            .type(LineShape.Type.STRIP)
                            .point(new DebugPos(0, 100, 0))
                            .point(new DebugPos(5, 105, 5))
                            .point(new DebugPos(-5, 105, 5))
                            .color(0xFF00FF00)
                            .lineWidth(4f)
                            .layer(RenderLayer.MIXED)
                            .build())
                    .set("debug:test_bezier", Shape.spline()
                            .type(SplineShape.Type.BEZIER)
                            .point(new DebugPos(0, 100, 0))
                            .point(new DebugPos(3, 105, 5))
                            .point(new DebugPos(-5, 105, 5))
                            .color(0xFF0000FF)
                            .lineWidth(4f)
                            .layer(RenderLayer.MIXED)
                            .build())
                    .set("debug:test_catmull_rom", Shape.spline()
                            .type(SplineShape.Type.CATMULL_ROM)
                            .point(new DebugPos(0, 100, 0))
                            .point(new DebugPos(5, 105, 5))
                            .point(new DebugPos(-5, 105, 5))
                            .color(0xFF000000)
                            .lineWidth(4f)
                            .layer(RenderLayer.MIXED)
                            .build())
                    .set("debug:test_catmull_rom_loop", Shape.spline()
                            .type(SplineShape.Type.CATMULL_ROM)
                            .point(new DebugPos(0, 100, 0))
                            .point(new DebugPos(-5, 105, -5))
                            .point(new DebugPos(-5, 105, 5))
                            .color(0xFFFF00FF)
                            .lineWidth(4f)
                            .loop(true)
                            .layer(RenderLayer.MIXED)
                            .build())
                    .build();

            DebugSender.send(debugMessage, player);
        });
    }
}
