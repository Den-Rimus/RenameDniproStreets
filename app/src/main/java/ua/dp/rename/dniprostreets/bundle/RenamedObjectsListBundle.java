package ua.dp.rename.dniprostreets.bundle;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import ua.dp.rename.dniprostreets.entity.CityRegion;
import ua.dp.rename.dniprostreets.entity.RenamedObject;

public class RenamedObjectsListBundle implements Parcelable {

    private final String title;
    private final List<RenamedObject> dataSet;
    private final boolean globalSearch;

    public RenamedObjectsListBundle(CityRegion cityRegion) {
        this.title = cityRegion.getOldAreaName();
        this.dataSet = cityRegion.getObjects();
        this.globalSearch = false;
    }

    public RenamedObjectsListBundle(String title, List<RenamedObject> dataSet) {
        this.title = title;
        this.dataSet = dataSet;
        this.globalSearch = true;
    }

    public String getTitle() {
        return title;
    }

    public List<RenamedObject> getDataSet() {
        return dataSet;
    }

    public boolean isGlobalSearch() {
        return globalSearch;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Parcelable
    ///////////////////////////////////////////////////////////////////////////

    protected RenamedObjectsListBundle(Parcel in) {
        title = in.readString();
        dataSet = in.createTypedArrayList(RenamedObject.CREATOR);
        globalSearch = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeTypedList(dataSet);
        dest.writeByte(globalSearch ? (byte) 1 : 0);
    }

    public static final Creator<RenamedObjectsListBundle> CREATOR = new Creator<RenamedObjectsListBundle>() {
        @Override
        public RenamedObjectsListBundle createFromParcel(Parcel in) {
            return new RenamedObjectsListBundle(in);
        }

        @Override
        public RenamedObjectsListBundle[] newArray(int size) {
            return new RenamedObjectsListBundle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
