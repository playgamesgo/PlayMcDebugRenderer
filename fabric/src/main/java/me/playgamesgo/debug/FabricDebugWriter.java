package me.playgamesgo.debug;

import net.minecraft.network.PacketByteBuf;

public final class FabricDebugWriter implements DebugWriter {
    private final PacketByteBuf buffer;

    public FabricDebugWriter(PacketByteBuf buffer) {
        this.buffer = buffer;
    }

    @Override
    public void writeInt(int value) {
        this.buffer.writeInt(value);
    }

    @Override
    public void writeVarInt(int value) {
        this.buffer.writeVarInt(value);
    }

    @Override
    public void writeDouble(double value) {
        this.buffer.writeDouble(value);
    }

    @Override
    public void writeFloat(float value) {
        this.buffer.writeFloat(value);
    }

    @Override
    public void writeString(String value) {
        this.buffer.writeString(value);
    }

    @Override
    public void writeBoolean(boolean value) {
        this.buffer.writeBoolean(value);
    }

    @Override
    public void writeEnum(Enum<?> value) {
        this.buffer.writeEnumConstant(value);
    }
}
