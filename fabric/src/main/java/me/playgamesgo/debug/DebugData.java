package me.playgamesgo.debug;

import me.playgamesgo.debug.data.DebugPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public final class DebugData {
    public static DebugPos fromVec(Vec3d vec) {
        return new DebugPos(vec.x, vec.y, vec.z);
    }

    public static DebugPos fromBlockPos(BlockPos pos) {
        return new DebugPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Vec3d fromDebugPos(DebugPos pos) {
        return new Vec3d(pos.x(), pos.y(), pos.z());
    }
}
