package me.playgamesgo.debug;

import me.playgamesgo.debug.shape.Shape;

public interface Operation {
    void write(DebugWriter buffer);

    record Set(String id, Shape shape) implements Operation {
        private static final int ID = 0;

        @Override
        public void write(DebugWriter buffer) {
            buffer.writeVarInt(ID);
            buffer.writeString(id);
            buffer.writeVarInt(shape.id());
            shape.write(buffer);
        }
    }

    record Remove(String id) implements Operation {
        private static final int ID = 1;

        @Override
        public void write(DebugWriter buffer) {
            buffer.writeVarInt(ID);
            buffer.writeString(id);
        }
    }

    record ClearNameSpace(String namespace) implements Operation {
        private static final int ID = 2;

        @Override
        public void write(DebugWriter buffer) {
            buffer.writeVarInt(ID);
            buffer.writeString(namespace);
        }
    }

    final class Clear implements Operation {
        private static final int ID = 3;

        @Override
        public void write(DebugWriter buffer) {
            buffer.writeVarInt(ID);
        }
    }
}
