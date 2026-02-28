package com.github.NoctuaAstrum.utils.data;

/**
 *
 * @param min minimum values for {@code x} {@code y} and {@code y} defined in a {@link XYZData}
 * @param max maximum values for {@code x} {@code y} and {@code y} defined in a {@link XYZData}
 */
public record MinMaxXYZData(XYZData min, XYZData max) {
}
