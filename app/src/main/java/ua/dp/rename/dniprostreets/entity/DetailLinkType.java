package ua.dp.rename.dniprostreets.entity;

public enum DetailLinkType {
    PERSON(0),
    HISTORY(1),
    GEOGRAPHY(2),
    ORGANIZATION(3)
    ;

    private int typeCode;

    DetailLinkType(int typeCode) {
        this.typeCode = typeCode;
    }
}
