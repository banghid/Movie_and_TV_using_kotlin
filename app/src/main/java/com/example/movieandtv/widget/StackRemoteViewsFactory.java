package com.example.movieandtv.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.movieandtv.R;
import com.example.movieandtv.database.FavMovieDatabase;
import com.example.movieandtv.database.MovieModelDB;
import com.squareup.picasso.Picasso;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Cursor cursor;
    private final Context appContext;
    private FavMovieDatabase favDatabase;

    public StackRemoteViewsFactory(Context context) {
        this.appContext = context;
    }

    @Override
    public void onCreate() {
        favDatabase = FavMovieDatabase.Companion.getInstance(appContext);
        Log.d("StackRemoteFactory", "init database");
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        cursor = favDatabase.favMovieDao().getAllFavWidget();
        Log.d("StackRemoteViewFac", "succes get data : " + cursor);

    }

    @Override
    public void onDestroy() {
        FavMovieDatabase.Companion.destroyInstance();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        MovieModelDB itemModel = getItem(position);
        RemoteViews remoteViews = new RemoteViews(appContext.getPackageName(), R.layout.fav_widget_item);

//        URL url;
        Bitmap mBitmap = null;
        try {

            mBitmap = Picasso.get().load("https://image.tmdb.org/t/p/w185/" + itemModel.getPosterPath()).get();

        } catch (Exception e) {
            Log.d("StackRemoteViews", "get Bitmap " + e.getMessage());
        }

        Log.d("StackRemoteViewFac", "output: " + itemModel.getOverview());

        remoteViews.setImageViewBitmap(R.id.imageView, mBitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteAppWidget.EXTRA_ITEM, position);

        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private MovieModelDB getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }

        return new MovieModelDB(cursor);
    }

}
