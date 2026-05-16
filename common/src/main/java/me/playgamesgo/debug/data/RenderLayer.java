package me.playgamesgo.debug.data;

/**
 * The Layer determines what layer debug objects should be rendered on.
 */
public enum RenderLayer {
    /**
     * Objects should be rendered normally.
     */
    INLINE,
    /**
     * Objects should be rendered on-top of everything else.
     */
    TOP,
    /**
     * Objects are rendered on top with lower opacity when behind another object
     */
    MIXED
}
