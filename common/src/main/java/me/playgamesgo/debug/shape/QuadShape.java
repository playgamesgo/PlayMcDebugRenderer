package me.playgamesgo.debug.shape;

import me.playgamesgo.debug.DebugWriter;
import me.playgamesgo.debug.data.DebugPos;
import me.playgamesgo.debug.data.RenderLayer;

import java.util.Objects;

public class QuadShape implements Shape {
    public final DebugPos a;
    public final DebugPos b;
    public final DebugPos c;
    public final DebugPos d;
    public final int color;
    public final RenderLayer renderLayer;

    private final DebugPos averagePoint;

    public QuadShape(
            DebugPos a,
            DebugPos b,
            DebugPos c,
            DebugPos d,
            int color,
            RenderLayer renderLayer
    ) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.color = color;
        this.renderLayer = renderLayer;


        this.averagePoint = a.add(b).add(c).add(d).multiply(0.25);
    }

    @Override
    public int id() {
        return 2;
    }

    @Override
    public double distanceTo(DebugPos pos) {
        return pos.squaredDistanceTo(averagePoint);
    }

    @Override
    public void write(DebugWriter writer) {
        writer.writeDouble(a.x());
        writer.writeDouble(a.y());
        writer.writeDouble(a.z());
        writer.writeDouble(b.x());
        writer.writeDouble(b.y());
        writer.writeDouble(b.z());
        writer.writeDouble(c.x());
        writer.writeDouble(c.y());
        writer.writeDouble(c.z());
        writer.writeDouble(d.x());
        writer.writeDouble(d.y());
        writer.writeDouble(d.z());
        writer.writeInt(color);
        writer.writeEnum(renderLayer);
    }

    public static class Builder {
        private DebugPos a;
        private DebugPos b;
        private DebugPos c;
        private DebugPos d;
        private int color = 0xFFFFFFFF;
        private RenderLayer renderLayer = RenderLayer.INLINE;

        public Builder a(DebugPos a) {
            this.a = a;
            return this;
        }

        public Builder b(DebugPos b) {
            this.b = b;
            return this;
        }

        public Builder c(DebugPos c) {
            this.c = c;
            return this;
        }

        public Builder d(DebugPos d) {
            this.d = d;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Builder renderLayer(RenderLayer renderLayer) {
            this.renderLayer = renderLayer;
            return this;
        }

        public QuadShape build() {
            if (Objects.isNull(a) || Objects.isNull(b) || Objects.isNull(c) || Objects.isNull(d)) {
                throw new IllegalArgumentException("All points must be non-null");
            }

            return new QuadShape(a, b, c, d, color, renderLayer);
        }
    }
}
