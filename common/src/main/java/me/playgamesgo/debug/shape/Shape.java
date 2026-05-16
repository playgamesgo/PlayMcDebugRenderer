package me.playgamesgo.debug.shape;

import me.playgamesgo.debug.DebugWriter;
import me.playgamesgo.debug.data.DebugPos;

/**
 * A shape that can be rendered.
 * New shapes cannot be added without a rendered also being added to the client-side mod.
 */
public interface Shape {
    /**
     * @return A new {@link BoxShape.Builder}.
     */
    static BoxShape.Builder box() {
        return new BoxShape.Builder();
    }

    /**
     * @return A new {@link LineShape.Builder}.
     */
    static LineShape.Builder line() {
        return new LineShape.Builder();
    }

    static QuadShape.Builder quad() {
        return new QuadShape.Builder();
    }

    static SplineShape.Builder spline() {
        return new SplineShape.Builder();
    }

    int id();
    double distanceTo(DebugPos pos);
    void write(DebugWriter writer);
}
