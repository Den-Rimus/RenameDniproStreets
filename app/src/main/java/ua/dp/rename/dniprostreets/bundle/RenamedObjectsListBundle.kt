package ua.dp.rename.dniprostreets.bundle

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RenamedObjectsListBundle @JvmOverloads constructor(
        val id: String? = null,
        val globalSearch: Boolean = false
) : Parcelable
