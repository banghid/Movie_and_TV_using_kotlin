package com.example.movieandtv.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.database.Cursor

@Dao
interface FavMovieDAO {

    @Query("SELECT * FROM fav_movie WHERE category = :category")
    fun getAllMovies(category: String): List<MovieModelDB>

    @Insert(onConflict = REPLACE)
    fun insert(movieData: MovieModelDB)

    @Delete
    fun delete(movieData: MovieModelDB)

    @Query("SELECT * FROM fav_movie WHERE id = :idMovie LIMIT 1")
    fun getById(idMovie: Int): MovieModelDB

    @Query("SELECT * FROM fav_movie")
    fun getAllFav(): List<MovieModelDB>

    @Query("SELECT * FROM fav_movie")
    fun getAllFavWidget(): Cursor


}