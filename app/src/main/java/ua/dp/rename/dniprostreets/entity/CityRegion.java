package ua.dp.rename.dniprostreets.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;

import java.util.List;

@DefaultSerializer(CompatibleFieldSerializer.class)
public class CityRegion implements Parcelable {

    private String id;
    private String oldAreaName;
    private String newAreaName;
    private List<RenamedObject> objects;

    public CityRegion() {
    }

    public String getOldAreaName() {
        return oldAreaName;
    }

    public String getNewAreaName() {
        return newAreaName;
    }

    public List<RenamedObject> getObjects() {
        return objects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Parcelable
    ///////////////////////////////////////////////////////////////////////////

    protected CityRegion(Parcel in) {
        id = in.readString();
        oldAreaName = in.readString();
        newAreaName = in.readString();
        objects = in.createTypedArrayList(RenamedObject.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(oldAreaName);
        dest.writeString(newAreaName);
        dest.writeTypedList(objects);
    }

    public static final Creator<CityRegion> CREATOR = new Creator<CityRegion>() {
        @Override
        public CityRegion createFromParcel(Parcel in) {
            return new CityRegion(in);
        }

        @Override
        public CityRegion[] newArray(int size) {
            return new CityRegion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
