package com.example.movieandtv.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow (
    var titleData:String?,
    var genreData:String?,
    var posterData:String?,
    var episodeData:String?,
    var overviewData:String?
):Parcelable