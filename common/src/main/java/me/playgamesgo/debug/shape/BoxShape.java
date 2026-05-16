package me.playgamesgo.debug.shape;

import me.playgamesgo.debug.DebugWriter;
import me.playgamesgo.debug.data.DebugPos;
import me.playgamesgo.debug.data.RenderLayer;

import java.util.Objects;

public class BoxShape implements Shape {
    public final DebugPos start;
    public final DebugPos end;
    public final int faceColor;
    public final RenderLayer faceLayer;
    public final int edgeColor;
    public final RenderLayer edgeLayer;
    public final float edgeWidth;

    public BoxShape(
            DebugPos start,
            DebugPos end,
            int faceColor,
            RenderLayer faceLayer,
            int edgeColor,
            RenderLayer edgeLayer,
            float edgeWidth
    ) {
        this.start = start;
        this.end = end;
        this.faceColor = faceColor;
        this.faceLayer = faceLayer;
        this.edgeColor = edgeColor;
        this.edgeLayer = edgeLayer;
        this.edgeWidth = edgeWidth;
    }

    @Override
    public int id() {
        return 3;
    }

    @Override
    public double distanceTo(DebugPos pos) {
        DebugPos center = start.add(end).multiply(0.5);
        return pos.squaredDistanceTo(center);
    }

    @Override
    public void write(DebugWriter writer) {
        writer.writeDouble(start.x());
        writer.writeDouble(start.y());
        writer.writeDouble(start.z());
        writer.writeDouble(end.x());
        writer.writeDouble(end.y());
        writer.writeDouble(end.z());
        writer.writeInt(faceColor);
        writer.writeEnum(faceLayer);
        writer.writeInt(edgeColor);
        writer.writeEnum(edgeLayer);
        writer.writeFloat(edgeWidth);
    }

    public static class Builder {
        private DebugPos start;
        private DebugPos end;
        private int faceColor = 0xFFFFFFFF;
        private RenderLayer faceLayer = RenderLayer.INLINE;
        private int edgeColor = 0xFFFFFFFF;
        private RenderLayer edgeLayer = RenderLayer.INLINE;
        private float edgeWidth = 4f;

        /**
         * The starting corner of the box. Must be set.
         *
         * @param start The position.
         * @return The builder.
         */
        public Builder start(DebugPos start) {
            this.start = start;
            return this;
        }

        /**
         * The ending corner of the box. Must be set.
         *
         * @param end The position.
         * @return The builder.
         */
        public Builder end(DebugPos end) {
            this.end = end;
            return this;
        }

        public Builder faceColor(int color) {
            this.faceColor = color;
            return this;
        }

        /**
         * The {@link RenderLayer} of the box.
         * <p>
         * Defaults to {@link RenderLayer#INLINE} if not set.
         *
         * @param layer The layer.
         * @return The builder.
         */
        public Builder faceLayer(RenderLayer layer) {
            this.faceLayer = layer;
            return this;
        }

        public Builder edgeColor(int color) {
            this.edgeColor = color;
            return this;
        }

        public Builder edgeLayer(RenderLayer layer) {
            this.edgeLayer = layer;
            return this;
        }

        public Builder edgeWidth(float width) {
            this.edgeWidth = width;
            return this;
        }

        public Shape build() {
            if (Objects.isNull(start) || Objects.isNull(end)) {
                throw new IllegalArgumentException("Start and end positions must be non-null");
            }

            return new BoxShape(start, end, faceColor, faceLayer, edgeColor, edgeLayer, edgeWidth);
        }
    }
}
