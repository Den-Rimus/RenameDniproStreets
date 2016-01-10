package ua.dp.rename.dniprostreets.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;

@DefaultSerializer(CompatibleFieldSerializer.class)
public class DetailsLink implements Parcelable {

    private String href;
    // TODO : implement adapter for link type deserializing

    public DetailsLink() {
    }

    public String getUrl() {
        return href;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Parcelable
    ///////////////////////////////////////////////////////////////////////////

    protected DetailsLink(Parcel in) {
        href = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(href);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DetailsLink> CREATOR = new Creator<DetailsLink>() {
        @Override
        public DetailsLink createFromParcel(Parcel in) {
            return new DetailsLink(in);
        }

        @Override
        public DetailsLink[] newArray(int size) {
            return new DetailsLink[size];
        }
    };
}
