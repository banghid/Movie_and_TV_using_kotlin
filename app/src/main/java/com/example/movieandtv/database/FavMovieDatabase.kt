package com.example.movieandtv.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log

@Database(entities = arrayOf(MovieModelDB::class), version = 1, exportSchema = false)
abstract class FavMovieDatabase : RoomDatabase() {

    abstract fun favMovieDao(): FavMovieDAO

    companion object {
        private var INSTANCE: FavMovieDatabase? = null

        fun getInstance(context: Context): FavMovieDatabase? {
            try {

                if (INSTANCE == null) {
                    synchronized(FavMovieDatabase::class) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            FavMovieDatabase::class.java,
                            "favmovie.db"
                        ).allowMainThreadQueries().build()
                    }
                }
                Log.d("FavoriteDatabase build", "Success to instance ")
            } catch (e: Exception) {
                Log.d("FavoriteDatabase build", "Fail to instance " + e.message)
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}