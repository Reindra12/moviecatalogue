package com.reindra.moviecatalogue.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Favorite (
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var title: String,
    var date: String,
    var rate: String,
    var synopsis: String,
    var poster: String? = null,
    var category: String
    ) : Parcelable
