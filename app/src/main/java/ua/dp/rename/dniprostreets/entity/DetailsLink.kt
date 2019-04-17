package ua.dp.rename.dniprostreets.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailsLink @JvmOverloads constructor(val url: String? = null) : Parcelable
