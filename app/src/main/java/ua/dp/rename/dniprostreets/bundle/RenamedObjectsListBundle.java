package ua.dp.rename.dniprostreets.bundle;

import android.os.Parcel;
import android.os.Parcelable;

public class RenamedObjectsListBundle implements Parcelable {

    private final String id;
    private final boolean globalSearch;

    public RenamedObjectsListBundle(String id) {
        this.id = id;
        this.globalSearch = false;
    }

    public RenamedObjectsListBundle() {
        this.id = null;
        this.globalSearch = true;
    }

    public String getId() {
        return id;
    }

    public boolean isGlobalSearch() {
        return globalSearch;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Parcelable
    ///////////////////////////////////////////////////////////////////////////

    protected RenamedObjectsListBundle(Parcel in) {
        id = in.readString();
        globalSearch = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (globalSearch ? 1 : 0));
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
