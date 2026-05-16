package me.playgamesgo.debug.client.shape;

import me.playgamesgo.debug.DebugData;
import me.playgamesgo.debug.data.DebugPos;
import me.playgamesgo.debug.client.render.DebugRenderContext;
import me.playgamesgo.debug.data.RenderLayer;
import me.playgamesgo.debug.client.render.RenderType;
import me.playgamesgo.debug.shape.QuadShape;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

public class FabricQuadShape extends QuadShape implements FabricShape {
    public FabricQuadShape(DebugPos a, DebugPos b, DebugPos c, DebugPos d, int color, RenderLayer renderLayer) {
        super(a, b, c, d, color, renderLayer);
    }

    public FabricQuadShape(@NotNull PacketByteBuf buffer) {
        this(
                new DebugPos(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
                new DebugPos(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
                new DebugPos(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
                new DebugPos(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
                buffer.readInt(), buffer.readEnumConstant(RenderLayer.class)
        );
    }

    @Override
    public void render(@NotNull DebugRenderContext context) {
        context.submit(this::render0, RenderType.QUADS, renderLayer);
    }

    private void render0(@NotNull DebugRenderContext context) {
        context.color(color);
        context.vertex(DebugData.fromDebugPos(a));
        context.vertex(DebugData.fromDebugPos(b));
        context.vertex(DebugData.fromDebugPos(c));
        context.vertex(DebugData.fromDebugPos(d));
    }
}
