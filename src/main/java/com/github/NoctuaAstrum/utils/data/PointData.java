package com.github.NoctuaAstrum.utils.data;

import java.util.Map;
/**
 *
 * @param dataMap contains all points, whose position is defined in a {@link XYZData}, as well as their name
 */
public record PointData(Map<String, XYZData> dataMap) {
}
