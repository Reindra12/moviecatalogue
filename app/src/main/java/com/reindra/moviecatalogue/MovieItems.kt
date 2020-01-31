package com.reindra.moviecatalogue

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class MovieItems(
    var id: String? = null,
    var poster_path: String? = null,
    var title: String? = null,
    var overview: String? = null

) : Parcelable
