package me.playgamesgo.debug.client.shape;

import me.playgamesgo.debug.DebugData;
import me.playgamesgo.debug.data.DebugPos;
import me.playgamesgo.debug.client.render.DebugRenderContext;
import me.playgamesgo.debug.data.RenderLayer;
import me.playgamesgo.debug.client.render.RenderType;
import me.playgamesgo.debug.shape.LineShape;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FabricLineShape extends LineShape implements FabricShape {
    public FabricLineShape(LineShape.Type type, List<DebugPos> points, int color, RenderLayer layer, float lineWidth) {
        super(type, points, color, layer, lineWidth);
    }

    public FabricLineShape(@NotNull PacketByteBuf buffer) {
        this(buffer.readEnumConstant(LineShape.Type.class),
                buffer.readList(buf -> new DebugPos(buf.readDouble(), buf.readDouble(), buf.readDouble())),
                buffer.readInt(),
                buffer.readEnumConstant(RenderLayer.class),
                buffer.readFloat());
    }

    @Override
    public void render(@NotNull DebugRenderContext context) {
        context.submit(this::render0, RenderType.LINES, layer);
    }

    private void render0(@NotNull DebugRenderContext context) {
        context.color(color);
        context.lineWidth(lineWidth);
        switch (type) {
            case SINGLE -> {
                for (DebugPos point : points) {
                    context.vertex(DebugData.fromDebugPos(point));
                }
            }
            case STRIP -> {
                for (int i = 0; i < points.size() - 1; i++) {
                    context.vertex(DebugData.fromDebugPos(points.get(i)));
                    context.vertex(DebugData.fromDebugPos(points.get(i + 1)));
                }
            }
            case LOOP -> {
                for (int i = 0; i < points.size() - 1; i++) {
                    context.vertex(DebugData.fromDebugPos(points.get(i)));
                    context.vertex(DebugData.fromDebugPos(points.get(i + 1)));
                }
                context.vertex(DebugData.fromDebugPos(points.getLast()));
                context.vertex(DebugData.fromDebugPos(points.getFirst()));
            }
        }
    }
}