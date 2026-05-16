package me.playgamesgo.debug.client.render;

import me.playgamesgo.debug.DebugData;
import me.playgamesgo.debug.data.DebugPos;
import me.playgamesgo.debug.client.shape.FabricShape;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ClientRenderer {
    private final Map<Identifier, FabricShape> shapes = new ConcurrentHashMap<>();

    public void add(@NotNull Identifier id, @NotNull FabricShape shape) {
        shapes.put(id, shape);
    }

    public void remove(@NotNull Identifier id) {
        shapes.remove(id);
    }

    public void remove(@NotNull String namespace) {
        shapes.keySet().removeIf(id -> id.getNamespace().equals(namespace));
    }

    public void clear() {
        shapes.clear();
    }

    public void render(MatrixStack matrices, Camera camera, @NotNull VertexConsumerProvider consumers) {
        DebugPos cameraPos = DebugData.fromVec(camera.getCameraPos());

        matrices.push();
        matrices.translate(-cameraPos.x(), -cameraPos.y(), -cameraPos.z());

        Matrix4f pose = matrices.peek().getPositionMatrix();
        DebugRenderContext context = new DebugRenderContext(pose, consumers);

        ArrayList<FabricShape> ordered = new ArrayList<>(shapes.values());
        ordered.sort((a, b) -> {
            double aDist = a.distanceTo(cameraPos);
            double bDist = b.distanceTo(cameraPos);
            return Double.compare(bDist, aDist);
        });

        for (var shape : ordered) {
            shape.render(context);
        }

        matrices.pop();
    }
}