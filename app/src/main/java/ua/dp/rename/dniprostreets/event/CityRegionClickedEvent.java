package ua.dp.rename.dniprostreets.event;

import ua.dp.rename.dniprostreets.entity.CityRegion;

public class CityRegionClickedEvent {

    public final CityRegion cityRegion;

    public CityRegionClickedEvent(CityRegion cityRegion) {
        this.cityRegion = cityRegion;
    }
}
