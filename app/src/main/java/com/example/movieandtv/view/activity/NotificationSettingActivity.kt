package com.example.movieandtv.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.movieandtv.R
import com.example.movieandtv.notification.DailyReminderReciever
import com.example.movieandtv.notification.NotificationPreference
import com.example.movieandtv.notification.ReleaseReciever
import com.example.movieandtv.utils.Utils.Companion.KEY_DAILY_REMINDER
import com.example.movieandtv.utils.Utils.Companion.KEY_RELEASE_REMINDER
import com.example.movieandtv.utils.Utils.Companion.STATE_DAILY_REMINDER
import com.example.movieandtv.utils.Utils.Companion.STATE_RELEASE_REMINDER
import kotlinx.android.synthetic.main.activity_notification_setting.*


@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class NotificationSettingActivity : AppCompatActivity() {

    private lateinit var dailyReminderReciever: DailyReminderReciever
    private lateinit var releaseReciever: ReleaseReciever

    private lateinit var notificationPreference: NotificationPreference
    private lateinit var spDailyReminder: SharedPreferences
    private lateinit var spReleaseReminder: SharedPreferences

    private lateinit var editorDailyReminder: SharedPreferences.Editor
    private lateinit var editorReleaseReminder: SharedPreferences.Editor

    companion object {
        val DAILY_REMINDER_ALARM = "dailyreminderalarm"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = resources.getString(R.string.notification_title)

        settings_switch_daily.setOnCheckedChangeListener { buttonView, isChecked ->
            editorDailyReminder = spDailyReminder.edit()
            if (isChecked) {
                editorDailyReminder.putBoolean(STATE_DAILY_REMINDER, true)
                editorDailyReminder.apply()
                setOnDailyReminder()
                Toast.makeText(applicationContext, "Daily Reminder Get ON", Toast.LENGTH_SHORT)
                    .show()


            } else {
                editorDailyReminder.putBoolean(STATE_DAILY_REMINDER, false)
                editorDailyReminder.apply()
                setOffDailyReminder()
                Toast.makeText(applicationContext, "Daily Reminder Get OFF", Toast.LENGTH_SHORT)
                    .show()

            }
        }

        settings_switch_release.setOnCheckedChangeListener { buttonView, isChecked ->
            editorReleaseReminder = spReleaseReminder.edit()
            if (isChecked) {
                editorReleaseReminder.putBoolean(STATE_RELEASE_REMINDER, true)
                editorReleaseReminder.apply()
                setOnReleaseReminder()
                Toast.makeText(applicationContext, "Release Reminder Get ON", Toast.LENGTH_SHORT)
                    .show()
            } else {
                editorReleaseReminder.putBoolean(STATE_RELEASE_REMINDER, false)
                editorReleaseReminder.apply()
                setOffReleaseReminder()
                Toast.makeText(
                    applicationContext,
                    "Release Reminder Get OFF",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        dailyReminderReciever = DailyReminderReciever()
        releaseReciever = ReleaseReciever()

        notificationPreference = NotificationPreference(this)
        setPreferences()

        if (settings_switch_daily.isChecked) {
            settings_switch_daily.text = resources.getString(R.string.on_dummy)
        } else {
            settings_switch_daily.text = resources.getString(R.string.off)
        }

        if (settings_switch_release.isChecked) {
            settings_switch_release.text = resources.getString(R.string.on_dummy)
        } else {
            settings_switch_release.text = resources.getString(R.string.off)
        }
    }

    private fun setPreferences() {
        spDailyReminder = getSharedPreferences(KEY_DAILY_REMINDER, Context.MODE_PRIVATE)
        val setStateDailyReminder = spDailyReminder.getBoolean(STATE_DAILY_REMINDER, false)
        settings_switch_daily.isChecked = setStateDailyReminder

        spReleaseReminder = getSharedPreferences(KEY_RELEASE_REMINDER, Context.MODE_PRIVATE)
        val setStateReleaseReminder = spReleaseReminder.getBoolean(STATE_RELEASE_REMINDER, false)
        settings_switch_release.isChecked = setStateReleaseReminder
    }

    override fun onPause() {
        super.onPause()
        this.finish()
    }

    private fun setOnDailyReminder() {
        val message = resources.getString(R.string.set_daily_reminder)
        val time = "07:00"
        notificationPreference.setTimeDailyReminder(time)
        notificationPreference.setMessageDailyReminder(message)
        dailyReminderReciever.setDailyReminderAlarm(
            this,
            DAILY_REMINDER_ALARM,
            time,
            message
        )
        Log.d("SettingActivity", "DailyReminder Alarm get on")
        settings_switch_daily.text = resources.getString(R.string.on_dummy)
    }

    private fun setOffDailyReminder() {
        dailyReminderReciever.cancelAlarm(applicationContext)
        Log.d("SettingActivity", "DailyReminder Alarm get off")
        settings_switch_daily.text = resources.getString(R.string.off)
    }

    private fun setOnReleaseReminder() {
        val message = "SET RELEASE REMINDER"
        val time = "22:30"
        notificationPreference.setTimeRelaseReminder(time)
        notificationPreference.setMessageReleaseReminderKey(message)
        releaseReciever.checkMovieRelease(applicationContext)
        settings_switch_release.text = resources.getString(R.string.on_dummy)
    }

    private fun setOffReleaseReminder() {
        releaseReciever.setOffReleaseNotification(applicationContext)
        settings_switch_release.text = resources.getString(R.string.off)
    }
}
