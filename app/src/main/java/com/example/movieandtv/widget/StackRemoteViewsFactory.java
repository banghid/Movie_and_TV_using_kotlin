package com.example.movieandtv.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.movieandtv.R;
import com.example.movieandtv.database.FavMovieDatabase;
import com.example.movieandtv.database.MovieModelDB;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final ArrayList<MovieModelDB> favMovieItem = new ArrayList<>();
    private final Context appContext;
    private FavMovieDatabase favDatabase;

    public StackRemoteViewsFactory(Context context) {
        this.appContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (favDatabase == null) {
            favDatabase = FavMovieDatabase.Companion.getInstance(appContext);
            Log.d("SackRemoteFactory", "init database");
        }

        favMovieItem.addAll(favDatabase.favMovieDao().getAllFav());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return favMovieItem.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(appContext.getPackageName(), R.layout.fav_widget_item);

        URL url;
        Bitmap mBitmap = null;
        try {
            url = new URL("https://image.tmdb.org/t/p/w500/" + favMovieItem.get(position).getPosterPath());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            mBitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            Log.d("StackRemoteViews", "get Bitmap " + e.getMessage());
        }

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
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
