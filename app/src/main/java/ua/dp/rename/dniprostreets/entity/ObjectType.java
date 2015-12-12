package ua.dp.rename.dniprostreets.entity;

@SuppressWarnings("unused")
public enum ObjectType {

    STREET("street"),
    AVENUE("avenue"),
    AREA("area"),
    LAKE("lake"),
    PARK("park"),
    IMPASSE("impasse"),
    LANE("lane"),
    EMBANKMENT("embankment"),
    SLOPE("slope"),
    SQUARE("square"),
    ISLAND("island"),
    STATION("station"),
    UNKNOWN("unknown"),
    ;

    private String typeKey;

    ObjectType(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getTypeKey() {
        return typeKey;
    }
}
