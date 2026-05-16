package me.playgamesgo.debug.shape;

import me.playgamesgo.debug.DebugWriter;
import me.playgamesgo.debug.data.DebugPos;
import me.playgamesgo.debug.data.RenderLayer;

import java.util.ArrayList;
import java.util.List;

public class SplineShape implements Shape {
    public final Type type;
    public final List<DebugPos> points;
    public final boolean loop;
    public final int color;
    public final RenderLayer layer;
    public final float lineWidth;

    private DebugPos averagePoint;

    public SplineShape(
            Type type,
            List<DebugPos> points,
            boolean loop,
            int color,
            RenderLayer layer,
            float lineWidth
    ) {
        this.type = type;
        this.points = points;
        this.loop = loop;
        this.color = color;
        this.layer = layer;
        this.lineWidth = lineWidth;

        this.averagePoint = new DebugPos(0, 0, 0);
        for (DebugPos point : points) {
            this.averagePoint = this.averagePoint.add(point);
        }
        this.averagePoint = this.averagePoint.multiply(1.0 / points.size());
    }

    public enum Type {
        CATMULL_ROM,
        BEZIER,
    }

    @Override
    public int id() {
        return 1;
    }

    @Override
    public double distanceTo(DebugPos pos) {
        return pos.squaredDistanceTo(averagePoint);
    }

    @Override
    public void write(DebugWriter writer) {
        writer.writeEnum(type);

        writer.writeVarInt(points.size());
        points.forEach(point -> {
            writer.writeDouble(point.x());
            writer.writeDouble(point.y());
            writer.writeDouble(point.z());
        });
        writer.writeBoolean(loop);
        writer.writeInt(color);
        writer.writeEnum(layer);
        writer.writeFloat(lineWidth);
    }

    public static class Builder {
        private Type type = Type.CATMULL_ROM;
        private final List<DebugPos> points = new ArrayList<>();
        private boolean loop = false;
        private int color = 0xFFFFFFFF;
        private RenderLayer layer = RenderLayer.INLINE;
        private float lineWidth = 3.0f;

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Builder point(DebugPos point) {
            this.points.add(point);
            return this;
        }

        public Builder loop(boolean loop) {
            this.loop = loop;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Builder layer(RenderLayer layer) {
            this.layer = layer;
            return this;
        }

        public Builder lineWidth(float lineWidth) {
            this.lineWidth = lineWidth;
            return this;
        }

        public SplineShape build() {
            return new SplineShape(type, points, loop, color, layer, lineWidth);
        }
    }
}
