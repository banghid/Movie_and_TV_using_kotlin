package com.example.movieandtv.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.example.movieandtv.R
import com.example.movieandtv.model.MovieItem
import com.example.movieandtv.model.MovieResponse
import com.example.movieandtv.service.AppService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ReleaseReciever : BroadcastReceiver() {

    private val TAG: String = "ReleaseReciever"
    private val ID = "id"
    private val TITLE = "title"

    private val listMovie = ArrayList<MovieItem>()
    private val mList = ArrayList<MovieItem>()
    private var id: Int? = 0
    private var delay: Int? = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive")
        id = intent?.getIntExtra(ID, 0)
        val title = intent?.getStringExtra(TITLE)
        setMovieRelease(context, id, title)
    }

    private fun setMovieRelease(context: Context?, id: Int?, title: String?) {
        Log.d(TAG, "setMovieRelease called")
        val CHANNEL_ID = "Channel_Daily"
        val CHANNEL_NAME = "Daily Reminder Alarm"

        val notificationManagerCompat =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_movie_white_24dp)
            .setContentTitle(title)
            .setContentText("Release Movie Today")
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(id!!, notification)

    }

    fun checkMovieRelease(context: Context) {
        Log.d(TAG, "checkMovieRelease called")

        @SuppressLint("SimpleDateFormat")
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        val dateNow = simpleDateFormat.format(date)

        val movieService = AppService()
        movieService.getMovieApi()?.getNewReleaseMovie(dateNow, dateNow)?.enqueue(object :
            Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d(TAG, "onresponse")
                if (response.body() != null) {
                    Log.d(TAG, "onresponse not null")

                    val result = response.body()!!.results
                    mList.clear()
                    mList.addAll(result!!)

                    for (movieItem in mList) {
                        if (movieItem.releaseDate.equals(dateNow)) {
                            Log.d(TAG, "release today is equal")
                            listMovie.add(movieItem)
                        } else {
                            Log.d(TAG, "release today not found")
                        }
                    }
                    setReleaseNotification(context, listMovie)
                } else {
                    Log.d(TAG, "onResponse: not found")
                }


            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG, "onFailure called")
            }
        })
    }

    fun setReleaseNotification(context: Context, movieItems: List<MovieItem>) {
        Log.d(TAG, "releaseNotification")

        for (movieItem in movieItems) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 8)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ReleaseReciever::class.java)
            intent.putExtra(ID, id)
            intent.putExtra(TITLE, movieItem.title)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                id!!,
                intent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis + delay!!,
                AlarmManager.INTERVAL_DAY, pendingIntent
            )

            id = id!! + 1
            delay = delay!! + 200
            Log.d(TAG, "called movie " + id + " " + movieItem.title)
        }
    }

    fun setOffReleaseNotification(context: Context) {
        Log.d(TAG, "cancelUpdateNotification: called")

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReleaseReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            202,
            intent, PendingIntent.FLAG_CANCEL_CURRENT
        )

        alarmManager.cancel(pendingIntent)
    }
}