package com.example.movieandtv.notification

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
import android.widget.Toast
import com.example.movieandtv.R
import java.util.*

class DailyReminderReciever : BroadcastReceiver() {
    private val ID_DAILY_REMINDER = 101

    val EXTRA_MESSAGE_DAILY = "dailyremindertype"
    val EXTRA_TYPE_DAILY = "dailyremindertype"

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(EXTRA_MESSAGE_DAILY)
        val title = "Daily Reminder"

        showToast(context, title, message)
        showAlarmNotification(context, title, message, ID_DAILY_REMINDER)
    }

    private fun showToast(context: Context?, title: String?, message: String?) {
        Toast.makeText(context, "$title : $message", Toast.LENGTH_LONG).show()
    }

    private fun showAlarmNotification(
        context: Context?,
        title: String,
        message: String?,
        idNotification: Int
    ) {
        val CHANNEL_ID = "Channel_Daily"
        val CHANNEL_NAME = "Daily Reminder Alarm"

        val notificationManagerCompat =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_movie_white_24dp)
            .setContentTitle(title)
            .setContentText(message)
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

            notificationManagerCompat?.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat?.notify(idNotification, notification)


    }

    fun setDailyReminderAlarm(context: Context, type: String, time: String, message: String) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminderReciever::class.java)
        intent.putExtra(EXTRA_MESSAGE_DAILY, message)
        intent.putExtra(EXTRA_TYPE_DAILY, type)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )


        Log.d("DailyReceiver", "Alarm is on")
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminderReciever::class.java)
        val requestCode = ID_DAILY_REMINDER
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Reminder Canceled", Toast.LENGTH_SHORT).show()
    }
}