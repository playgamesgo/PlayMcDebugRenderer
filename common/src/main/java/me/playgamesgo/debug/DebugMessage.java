package me.playgamesgo.debug;

import me.playgamesgo.debug.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * A message to send the client to show debug objects.
 *
 * @param ops The operations to perform.
 */
public record DebugMessage(List<Operation> ops) {
    /**
     * @return A new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Encodes this DebugMessage into a DebugWriter.
     */
    public void encode(DebugWriter writer) {
        writer.writeVarInt(ops.size());
        for (Operation op : ops) {
            op.write(writer);
        }
    }


    public static class Builder {
        private final List<Operation> ops = new ArrayList<>();

        /**
         * Sets a shape with the specified namespace ID.
         *
         * @param id    The namespace ID for this shape. If reused, the previous shape will be replaced.
         * @param shape The shape to associate with the namespace ID.
         * @return The builder.
         */
        public Builder set(String  id, Shape shape) {
            ops.add(new Operation.Set(id, shape));
            return this;
        }
        /**
         * Removes a shape with a specified namespace ID.
         *
         * @param id The namespace ID to remove.
         * @return The builder.
         */
        public Builder remove(String id) {
            ops.add(new Operation.Remove(id));
            return this;
        }

        /**
         * Removes all shapes associated with the specified namespace.
         *
         * @param namespace The namespace to clear.
         * @return The builder.
         */
        public Builder clear(String namespace) {
            ops.add(new Operation.ClearNameSpace(namespace));
            return this;
        }

        /**
         * Clears all shapes.
         *
         * @return The builder.
         */
        public Builder clear() {
            ops.add(new Operation.Clear());
            return this;
        }

        /**
         * @return Constructs a new {@link DebugMessage} with the provided builder parameters.
         */
        public DebugMessage build() {
            return new DebugMessage(ops);
        }
    }
}
