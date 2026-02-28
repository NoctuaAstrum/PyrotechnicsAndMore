package com.github.NoctuaAstrum.utils.data;

/**
 *
 * @param x x position, offset, acceleration, impulse, damping or axis
 * @param y y position, offset, acceleration, impulse, damping or axis
 * @param z z position, offset, acceleration, impulse, damping or axis
 */
public record XYZData(double x, double y, double z) {
    /**
     * Same as {@link XYZData}, but {@link XYZData#z()} is 0
     */
    public XYZData(double x, double y){
        this(x,y,0);
    }
}
