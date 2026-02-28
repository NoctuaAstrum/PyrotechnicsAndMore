package com.github.NoctuaAstrum.utils.data;

/**
 *
 * @param yaw minimal and maximal yaw rotation of the movement direction defined in a {@link MinMaxData}
 * @param pitch minimal and maximal pitch rotation of the movement direction defined in a {@link MinMaxData}
 * @param speed minimal and maximal speed of the movement defined in a {@link MinMaxData}
 */
public record VelocityData(MinMaxData yaw, MinMaxData pitch, MinMaxData speed) {
}
