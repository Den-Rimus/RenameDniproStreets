package ua.dp.rename.dniprostreets.entity;

import com.innahema.collections.query.queriables.Queryable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class ApiDataHolder {

    private CityRegion r61;
    private CityRegion r62;
    private CityRegion r63;
    private CityRegion r64;
    private CityRegion r65;
    private CityRegion r66;
    private CityRegion r67;
    private CityRegion r68;

    public static Map<String, CityRegion> getRegionsAsMap(List<CityRegion> regions) {
        final Map<String, CityRegion> asMap = Collections.<String, CityRegion>emptyMap();
        Queryable.from(regions).forEachR(region -> asMap.put(region.getId(), region));
        return asMap;
    }

    public List<CityRegion> getRegionsAsList() {
        r61.setId("r61");
        r62.setId("r62");
        r63.setId("r63");
        r64.setId("r64");
        r65.setId("r65");
        r66.setId("r66");
        r67.setId("r67");
        r68.setId("r68");

        return Arrays.asList(r61, r62, r63, r64, r65, r66, r67, r68);
    }
}
