package ua.dp.rename.dniprostreets.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityRegion(
      val id: String,
      val oldAreaName: String,
      val newAreaName: String,
      val objects: List<RenamedObject>
) : Parcelable

@Parcelize
data class RenamedObject(
      val type: ObjectType,
      val oldName: String,
      val newName: String,
      val link: DetailsLink?,
      val regionNewName: String,
      val regionOldName: String
) : Parcelable {

   fun hasLink(): Boolean = link?.url != null && link.url.isNotEmpty()
}

@Parcelize
data class DetailsLink(val url: String? = null) : Parcelable
