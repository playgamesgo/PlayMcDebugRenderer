package me.playgamesgo.debug.client.shape;

import me.playgamesgo.debug.data.DebugPos;
import me.playgamesgo.debug.client.render.DebugRenderContext;
import me.playgamesgo.debug.data.RenderLayer;
import me.playgamesgo.debug.client.render.RenderType;
import me.playgamesgo.debug.shape.BoxShape;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

public class FabricBoxShape extends BoxShape implements FabricShape {
    public FabricBoxShape(DebugPos start, DebugPos end, int faceColor, RenderLayer faceLayer, int edgeColor, RenderLayer edgeLayer, float edgeWidth) {
        super(start, end, faceColor, faceLayer, edgeColor, edgeLayer, edgeWidth);
    }

    public FabricBoxShape(@NotNull PacketByteBuf buffer) {
        this(
                new DebugPos(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
                new DebugPos(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
                buffer.readInt(), buffer.readEnumConstant(RenderLayer.class),
                buffer.readInt(), buffer.readEnumConstant(RenderLayer.class),
                buffer.readFloat()
        );
    }


    @Override
    public void render(@NotNull DebugRenderContext context) {
        if ((faceColor & 0xFF000000) != 0)
            context.submit(this::renderFaces, RenderType.QUADS, faceLayer);
        if ((edgeColor & 0xFF000000) != 0)
            context.submit(this::renderEdges, RenderType.LINES, edgeLayer);
    }

    private void renderFaces(@NotNull DebugRenderContext context) {
        context.color(faceColor);

        context.vertex((float) start.x(), (float) start.y(), (float) start.z());
        context.vertex((float) start.x(), (float) end.y(), (float) start.z());
        context.vertex((float) end.x(), (float) end.y(), (float) start.z());
        context.vertex((float) end.x(), (float) start.y(), (float) start.z());

        context.vertex((float) end.x(), (float) start.y(), (float) end.z());
        context.vertex((float) end.x(), (float) end.y(), (float) end.z());
        context.vertex((float) start.x(), (float) end.y(), (float) end.z());
        context.vertex((float) start.x(), (float) start.y(), (float) end.z());

        context.vertex((float) start.x(), (float) start.y(), (float) end.z());
        context.vertex((float) start.x(), (float) end.y(), (float) end.z());
        context.vertex((float) start.x(), (float) end.y(), (float) start.z());
        context.vertex((float) start.x(), (float) start.y(), (float) start.z());

        context.vertex((float) end.x(), (float) start.y(), (float) start.z());
        context.vertex((float) end.x(), (float) end.y(), (float) start.z());
        context.vertex((float) end.x(), (float) end.y(), (float) end.z());
        context.vertex((float) end.x(), (float) start.y(), (float) end.z());

        context.vertex((float) start.x(), (float) start.y(), (float) end.z());
        context.vertex((float) start.x(), (float) start.y(), (float) start.z());
        context.vertex((float) end.x(), (float) start.y(), (float) start.z());
        context.vertex((float) end.x(), (float) start.y(), (float) end.z());

        context.vertex((float) end.x(), (float) end.y(), (float) end.z());
        context.vertex((float) end.x(), (float) end.y(), (float) start.z());
        context.vertex((float) start.x(), (float) end.y(), (float) start.z());
        context.vertex((float) start.x(), (float) end.y(), (float) end.z());
    }

    private void renderEdges(@NotNull DebugRenderContext context) {
        context.color(edgeColor);

        context.vertex((float) start.x(), (float) start.y(), (float) start.z());
        context.vertex((float) start.x(), (float) end.y(), (float) start.z());

        context.vertex((float) start.x(), (float) end.y(), (float) start.z());
        context.vertex((float) end.x(), (float) end.y(), (float) start.z());

        context.vertex((float) end.x(), (float) end.y(), (float) start.z());
        context.vertex((float) end.x(), (float) start.y(), (float) start.z());

        context.vertex((float) end.x(), (float) start.y(), (float) start.z());
        context.vertex((float) start.x(), (float) start.y(), (float) start.z());

        context.vertex((float) start.x(), (float) start.y(), (float) end.z());
        context.vertex((float) start.x(), (float) end.y(), (float) end.z());

        context.vertex((float) start.x(), (float) end.y(), (float) end.z());
        context.vertex((float) end.x(), (float) end.y(), (float) end.z());

        context.vertex((float) end.x(), (float) end.y(), (float) end.z());
        context.vertex((float) end.x(), (float) start.y(), (float) end.z());

        context.vertex((float) end.x(), (float) start.y(), (float) end.z());
        context.vertex((float) start.x(), (float) start.y(), (float) end.z());

        context.vertex((float) start.x(), (float) start.y(), (float) start.z());
        context.vertex((float) start.x(), (float) start.y(), (float) end.z());

        context.vertex((float) start.x(), (float) end.y(), (float) start.z());
        context.vertex((float) start.x(), (float) end.y(), (float) end.z());

        context.vertex((float) end.x(), (float) end.y(), (float) start.z());
        context.vertex((float) end.x(), (float) end.y(), (float) end.z());

        context.vertex((float) end.x(), (float) start.y(), (float) start.z());
        context.vertex((float) end.x(), (float) start.y(), (float) end.z());
    }
}
