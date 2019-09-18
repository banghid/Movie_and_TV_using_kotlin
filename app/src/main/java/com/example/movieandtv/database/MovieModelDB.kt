package com.example.movieandtv.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "fav_movie")
data class MovieModelDB constructor(
    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "original_language")
    var originalLanguage: String? = null,

    @ColumnInfo(name = "original_title")
    var originalTitle: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

//    @ColumnInfo(name = "genre_ids")
//    var genreIds: List<Int?>? = null,

    @ColumnInfo(name = "poster_path")
    var posterPath: String? = null,

    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String? = null,

    @ColumnInfo(name = "release_date")
    var releaseDate: String? = null,

    @ColumnInfo(name = "popularity")
    var popularity: Double? = null,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "vote_count")
    var voteCount: Int? = null,

    @ColumnInfo(name = "category")
    var category: String? = null
) : Parcelable {
    @Ignore
    constructor() : this(id = 0)
}
