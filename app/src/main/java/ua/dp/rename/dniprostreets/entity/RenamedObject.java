package ua.dp.rename.dniprostreets.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;

import java.util.Comparator;

@DefaultSerializer(CompatibleFieldSerializer.class)
public class RenamedObject implements Parcelable {

   private ObjectType type;
   private String oldName;
   private String newName;
   private DetailsLink link;
   private String regionNewName;
   private String regionOldName;

   public RenamedObject() {
   }

   public ObjectType getType() {
      return type;
   }

   public String getOldName() {
      return oldName;
   }

   public String getNewName() {
      return newName;
   }

   public DetailsLink getLink() {
      return link;
   }

   public String getRegionNewName() {
      return regionNewName;
   }

   public String getRegionOldName() {
      return regionOldName;
   }

   public void setRegionNewName(String regionNewName) {
      this.regionNewName = regionNewName;
   }

   public void setRegionOldName(@Nullable String regionOldName) {
      this.regionOldName = regionOldName;
   }

   public boolean hasLink() {
      return link != null && link.getUrl() != null && !link.getUrl().isEmpty();
   }

   ///////////////////////////////////////////////////////////////////////////
   // Parcelable
   ///////////////////////////////////////////////////////////////////////////

   protected RenamedObject(Parcel in) {
      type = (ObjectType) in.readSerializable();
      oldName = in.readString();
      newName = in.readString();
      link = in.readParcelable(DetailsLink.class.getClassLoader());
      regionNewName = in.readString();
      regionOldName = in.readString();
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeSerializable(type);
      dest.writeString(oldName);
      dest.writeString(newName);
      dest.writeParcelable(link, flags);
      dest.writeString(regionNewName);
      dest.writeString(regionOldName);
   }

   @Override
   public int describeContents() {
      return 0;
   }

   public static final Creator<RenamedObject> CREATOR = new Creator<RenamedObject>() {
      @Override
      public RenamedObject createFromParcel(Parcel in) {
         return new RenamedObject(in);
      }

      @Override
      public RenamedObject[] newArray(int size) {
         return new RenamedObject[size];
      }
   };

   ///////////////////////////////////////////////////////////////////////////
   // Sorting and stuff
   ///////////////////////////////////////////////////////////////////////////

   public static Comparator<RenamedObject> ALPHABETICAL_COMPARATOR = (lhs, rhs) ->
         lhs.getNewName().compareToIgnoreCase(rhs.getNewName());

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      RenamedObject that = (RenamedObject) o;

      if (type != that.type) return false;
      return oldName.equals(that.oldName);
   }

   @Override
   public int hashCode() {
      int result = type.hashCode();
      result = 31 * result + oldName.hashCode();
      return result;
   }
}
