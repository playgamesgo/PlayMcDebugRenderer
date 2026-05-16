package me.playgamesgo.debug.client.shape;

import me.playgamesgo.debug.client.render.DebugRenderContext;
import me.playgamesgo.debug.shape.Shape;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface FabricShape extends Shape {
    void render(@NotNull DebugRenderContext context);

    enum Type {
        LINE(FabricLineShape::new),
        SPLINE(FabricSplineShape::new),
        QUAD(FabricQuadShape::new),
        BOX(FabricBoxShape::new);

        private final Function<PacketByteBuf, FabricShape> deserializer;

        Type(@NotNull Function<PacketByteBuf, FabricShape> deserializer) {
            this.deserializer = deserializer;
        }

        public @NotNull FabricShape deserialize(@NotNull PacketByteBuf buffer) {
            return deserializer.apply(buffer);
        }
    }
}
