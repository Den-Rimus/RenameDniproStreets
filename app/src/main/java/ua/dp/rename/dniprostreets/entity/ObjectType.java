package ua.dp.rename.dniprostreets.entity;

import androidx.annotation.StringRes;

import ua.dp.rename.dniprostreets.R;

@SuppressWarnings("unused")
public enum ObjectType {

    STREET("street", R.string.object_type_caption_street),
    AVENUE("avenue", R.string.object_type_caption_avenue),
    AREA("area", R.string.object_type_caption_area),
    LAKE("lake", R.string.object_type_caption_lake),
    PARK("park", R.string.object_type_caption_park),
    IMPASSE("impasse", R.string.object_type_caption_impasse),
    LANE("lane", R.string.object_type_caption_lane),
    EMBANKMENT("embankment", R.string.object_type_caption_embankment),
    SLOPE("slope", R.string.object_type_caption_slope),
    SQUARE("square", R.string.object_type_caption_square),
    ISLAND("island", R.string.object_type_caption_island),
    STATION("station", R.string.object_type_caption_station),
    UNKNOWN("unknown", R.string.object_type_caption_unknown),
    ;

    private String typeKey;
    private int captionResId;

    ObjectType(String typeKey, @StringRes int captionResId) {
        this.typeKey = typeKey;
        this.captionResId = captionResId;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public int getCaptionResId() {
        return captionResId;
    }
}
