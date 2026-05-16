package me.playgamesgo.debug;

import net.minestom.server.network.NetworkBuffer;

import static net.minestom.server.network.NetworkBuffer.*;

public final class MinestomDebugWriter implements DebugWriter {
    private final NetworkBuffer buffer;

    public MinestomDebugWriter(NetworkBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void writeInt(int value) {
        this.buffer.write(INT, value);
    }

    @Override
    public void writeVarInt(int value) {
        this.buffer.write(VAR_INT, value);
    }

    @Override
    public void writeDouble(double value) {
        this.buffer.write(DOUBLE, value);
    }

    @Override
    public void writeFloat(float value) {
        this.buffer.write(FLOAT, value);
    }

    @Override
    public void writeString(String value) {
        this.buffer.write(STRING, value);
    }

    @Override
    public void writeBoolean(boolean value) {
        this.buffer.write(BOOLEAN, value);
    }

    @Override
    public void writeEnum(Enum<?> value) {
        this.buffer.write(NetworkBuffer.Enum(value.getClass()), value);
    }
}
