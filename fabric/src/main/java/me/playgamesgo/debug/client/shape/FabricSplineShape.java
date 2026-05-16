package me.playgamesgo.debug.client.shape;

import me.playgamesgo.debug.DebugData;
import me.playgamesgo.debug.data.DebugPos;
import me.playgamesgo.debug.data.RenderLayer;
import me.playgamesgo.debug.client.shape.util.BezierCurve;
import me.playgamesgo.debug.client.shape.util.CatmullRomSpline;
import me.playgamesgo.debug.shape.LineShape;
import me.playgamesgo.debug.shape.SplineShape;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FabricSplineShape extends FabricLineShape {
    public FabricSplineShape(@NotNull SplineShape.Type type, @NotNull List<DebugPos> points, boolean loop, int color,
                             @NotNull RenderLayer renderLayer, float lineWidth) {
        super(LineShape.Type.STRIP, createPoints(type, points, loop).stream().map(DebugData::fromVec).toList(), color, renderLayer, lineWidth);
    }

    public FabricSplineShape(@NotNull PacketByteBuf buffer) {
        super(LineShape.Type.STRIP, createPointsFromBuffer(buffer), buffer.readInt(),
                buffer.readEnumConstant(RenderLayer.class), buffer.readFloat());
    }

    @Override
    public int id() {
        return 1;
    }

    private static @NotNull List<DebugPos> createPointsFromBuffer(@NotNull PacketByteBuf buffer) {
        var type = buffer.readEnumConstant(SplineShape.Type.class);
        var points = buffer.readList(buf -> new DebugPos(buf.readDouble(), buf.readDouble(), buf.readDouble()));
        var loop = buffer.readBoolean();

        return createPoints(type, points, loop).stream().map(DebugData::fromVec).toList();
    }

    private static @NotNull List<Vec3d> createPoints(@NotNull SplineShape.Type type, @NotNull List<DebugPos> points, boolean loop) {
        return switch (type) {
            case CATMULL_ROM -> CatmullRomSpline.getCatmullRomChain(points.stream().map(DebugData::fromDebugPos).toList(), loop);
            case BEZIER -> BezierCurve.getBezierChain(points.stream().map(DebugData::fromDebugPos).toList());
        };
    }
}
