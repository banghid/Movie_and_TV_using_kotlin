package com.example.movieandtv

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.example.movieandtv.database.FavMovieDatabase
import com.example.movieandtv.database.MovieModelDB
import java.util.*

class MovienTvProvider : ContentProvider() {

    companion object {
        val AUTHORITY = "com.example.movieandtv"
        val URI_FAVORITE: Uri = Uri.parse("content://" + AUTHORITY + "fav_movie")
    }

    private val CODE_MENU_DIR = 1
    private val CODE_MENU_ITEM = 2
    private val MATCHER: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    private fun initializeUriMaching() {
        MATCHER.addURI(AUTHORITY, "fav_movie", CODE_MENU_DIR)
        MATCHER.addURI(AUTHORITY, "fav_movie/*", CODE_MENU_ITEM)
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (MATCHER.match(uri)) {
            CODE_MENU_DIR -> {
                val context = context ?: return null
                val id = values!!.getAsInteger("id")!!

                FavMovieDatabase.getInstance(context)?.favMovieDao()
                    ?.insert(MovieModelDB.fromContentValues(values))
                return ContentUris.withAppendedId(uri, id.toLong())
            }

            CODE_MENU_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val code = MATCHER.match(uri)
        if (code == CODE_MENU_DIR || code == CODE_MENU_ITEM) {
            val context = context ?: return null

            val dao = FavMovieDatabase.getInstance(context)?.favMovieDao()
            val cursor: Cursor
            if (code == CODE_MENU_DIR) {
                cursor = dao!!.getAllFavCursor()
            } else {
                cursor = dao!!.getByIdCursor(ContentUris.parseId(uri).toInt())
            }

            cursor.setNotificationUri(context.contentResolver, uri)
            return cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun onCreate(): Boolean {
        initializeUriMaching()
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return when (MATCHER.match(uri)) {
            CODE_MENU_DIR -> "vnd.android.cursor.dir/$AUTHORITY.favorite_movie"
            CODE_MENU_ITEM -> "vnd.android.cursor.item/$AUTHORITY.favorite_movie"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun applyBatch(operations: ArrayList<ContentProviderOperation>): Array<ContentProviderResult?> {
        val context = context ?: return arrayOfNulls(0)
        val database = FavMovieDatabase.getInstance(context)
        database?.beginTransaction()
        try {
            val result = super.applyBatch(operations)
            database?.setTransactionSuccessful()
            return result
        } finally {
            database?.endTransaction()
        }
    }

    override fun bulkInsert(uri: Uri, values: Array<ContentValues>): Int {
        when (MATCHER.match(uri)) {
            CODE_MENU_DIR -> {
                val context = context ?: return 0
                val database = FavMovieDatabase.getInstance(context)
                val movie = arrayOfNulls<MovieModelDB>(values.size)
                for (i in values.indices) {
                    movie[i] = MovieModelDB.fromContentValues(values[i])
                }
                return database?.favMovieDao()?.insertAll(movie)!!.size
            }
            CODE_MENU_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}