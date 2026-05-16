package me.playgamesgo.debug.client.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public final class DebugRenderContext {
    private final Matrix4f positionMat;
    private final VertexConsumerProvider consumers;

    private RenderType renderType = null;
    private RenderLayer renderLayer = null;

    private float a = 1f, r = 1f, g = 1f, b = 1f;
    private float lineWidth = 1f;

    private boolean hasLast = false;
    private float lastX, lastY, lastZ;

    public DebugRenderContext(Matrix4f positionMat, @NotNull VertexConsumerProvider consumers) {
        this.positionMat = positionMat;
        this.consumers = consumers;
    }

    public void submit(@NotNull Consumer<DebugRenderContext> func,
                       @NotNull RenderType renderType,
                       @NotNull me.playgamesgo.debug.data.RenderLayer layer) {
        if (layer != me.playgamesgo.debug.data.RenderLayer.TOP) {
            this.renderType = renderType;
            this.renderLayer = pickVanillaLayer(renderType, false);
            func.accept(this);
            flush();
        }

        if (layer != me.playgamesgo.debug.data.RenderLayer.INLINE) {
            this.renderType = renderType;
            this.renderLayer = pickVanillaLayer(renderType, true);
            func.accept(this);
            flush();
        }
    }

    public void color(int argb) {
        this.a = ((argb >> 24) & 0xFF) / 255f;
        this.r = ((argb >> 16) & 0xFF) / 255f;
        this.g = ((argb >> 8) & 0xFF) / 255f;
        this.b = (argb & 0xFF) / 255f;
    }

    public void lineWidth(float width) {
        this.lineWidth = width;
    }

    public void vertex(@NotNull Vec3d point) {
        vertex((float) point.x, (float) point.y, (float) point.z);
    }

    public void vertex(float x, float y, float z) {
        if (renderType == RenderType.QUADS) {
            writeQuadVertex(x, y, z);
        } else if (renderType == RenderType.LINES) {
            writeLineVertex(x, y, z);
        } else {
            throw new IllegalStateException("Unknown render type: " + renderType);
        }
    }

    private void flush() {
        hasLast = false;
    }

    private void writeQuadVertex(float x, float y, float z) {
        consumers.getBuffer(renderLayer)
                .vertex(positionMat, x, y, z)
                .color(r, g, b, a);
    }

    private void writeLineVertex(float x, float y, float z) {
        if (!hasLast) {
            lastX = x;
            lastY = y;
            lastZ = z;
            hasLast = true;
            return;
        }

        float dx = x - lastX;
        float dy = y - lastY;
        float dz = z - lastZ;
        float len = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (len > 0) {
            dx /= len;
            dy /= len;
            dz /= len;
        }

        VertexConsumer buf = consumers.getBuffer(renderLayer);

        buf.vertex(positionMat, lastX, lastY, lastZ)
                .color(r, g, b, a)
                .lineWidth(lineWidth)
                .normal(dx, dy, dz);

        buf.vertex(positionMat, x, y, z)
                .color(r, g, b, a)
                .lineWidth(lineWidth)
                .normal(dx, dy, dz);

        hasLast = false;
    }

    private static RenderLayer pickVanillaLayer(RenderType type, boolean noDepth) {
        return switch (type) {
            case QUADS -> noDepth ? RenderLayers.debugQuads() : RenderLayers.debugFilledBox();
            case LINES -> RenderLayers.lines();
        };
    }
}