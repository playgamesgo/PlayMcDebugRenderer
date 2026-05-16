package me.playgamesgo.debug.data;

public record DebugPos(double x, double y, double z) {
    public static final DebugPos ZERO = new DebugPos(0, 0, 0);

    public DebugPos add(DebugPos other) {
        return new DebugPos(x + other.x, y + other.y, z + other.z);
    }

    public DebugPos subtract(DebugPos other) {
        return new DebugPos(x - other.x, y - other.y, z - other.z);
    }

    public DebugPos multiply(double scalar) {
        return new DebugPos(x * scalar, y * scalar, z * scalar);
    }

    public DebugPos divide(double scalar) {
        return new DebugPos(x / scalar, y / scalar, z / scalar);
    }

    public double squaredDistanceTo(DebugPos pos) {
        double dx = x - pos.x;
        double dy = y - pos.y;
        double dz = z - pos.z;
        return dx * dx + dy * dy + dz * dz;
    }
}
