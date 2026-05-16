package me.playgamesgo.debug;

public interface DebugWriter {
    void writeInt(int value);
    void writeVarInt(int value);
    void writeDouble(double value);
    void writeFloat(float value);
    void writeString(String value);
    void writeBoolean(boolean value);
    void writeEnum(Enum<?> value);
}