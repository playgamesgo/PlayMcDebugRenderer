package me.playgamesgo.debug.shape;

import me.playgamesgo.debug.DebugWriter;
import me.playgamesgo.debug.data.DebugPos;
import me.playgamesgo.debug.data.RenderLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * A line connected with multiple points.
 *
 */
public class LineShape implements Shape {
    public final Type type;
    public final List<DebugPos> points;
    public final int color;
    public final RenderLayer layer;
    public final float lineWidth;

    private DebugPos averagePoint;

    /**
     * @param points    The points the line should go through.
     * @param lineWidth The thickness of the line.
     * @param color     The color of the line, in ARGB format.
     * @param layer     The layer of the line.
     */
    public LineShape(
            Type type,
            List<DebugPos> points,
            int color,
            RenderLayer layer,
            float lineWidth
    ) {
        this.type = type;
        this.points = points;
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
        SINGLE,
        STRIP,
        LOOP
    }

    @Override
    public int id() {
        return 0;
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
        writer.writeInt(color);
        writer.writeEnum(layer);
        writer.writeFloat(lineWidth);
    }

    public static class Builder {
        private Type type = Type.SINGLE;
        private final List<DebugPos> points = new ArrayList<>();
        private float lineWidth = 4f;
        private int color = 0xFFFFFFFF;
        private RenderLayer layer = RenderLayer.INLINE;

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        /**
         * Adds a point to the line.
         * Lines will be rendered in order from the first point to the last.
         * There must be at least 2 points to render a line.
         *
         * @param point The point.
         * @return The builder.
         */
        public Builder point(DebugPos point) {
            points.add(point);
            return this;
        }

        /**
         * The color of the line in ARGB format.
         * <p>
         * Defaults to pure white if not set.
         *
         * @param color The color.
         * @return The builder.
         */
        public Builder color(int color) {
            this.color = color;
            return this;
        }

        /**
         * The {@link RenderLayer} of the line.
         * <p>
         * Defaults to {@link RenderLayer#INLINE} if not set.
         *
         * @param layer The layer.
         * @return The builder.
         */
        public Builder layer(RenderLayer layer) {
            this.layer = layer;
            return this;
        }

        public Builder lineWidth(float lineWidth) {
            this.lineWidth = lineWidth;
            return this;
        }

        /**
         * @return A {@link LineShape} constructed from the builder parameters.
         */
        public LineShape build() {
            if (points.size() < 2) throw new IllegalArgumentException("Line must have at least 2 points");
            return new LineShape(type, points, color, layer, lineWidth);
        }
    }
}
