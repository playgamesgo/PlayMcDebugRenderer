package me.playgamesgo.debug;

import me.playgamesgo.debug.data.DebugPos;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;

public final class DebugData {
    public static DebugPos fromPoint(Point point) {
        return new DebugPos(point.x(), point.y(), point.z());
    }

    public static Vec fromDebugPos(DebugPos pos) {
        return new Vec(pos.x(), pos.y(), pos.z());
    }
}
