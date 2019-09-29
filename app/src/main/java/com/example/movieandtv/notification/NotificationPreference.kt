package com.example.movieandtv.notification

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class NotificationPreference {

    private val PREFERENCE_NAME = "reminderPreferences"
    val DAILY_REMINDER_KEY = "dailyreminder"
    val MESSAGE_DAILY_REMINDER_KEY = "dailyremindermessage"

    val RELEASE_REMINDER_KEY = "releasereminder"
    val MESSAGE_RELEASE_REMINDER_KEY = "releaseremindermessage"

    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor

    @SuppressLint("CommitPrefEdits")
    constructor(context: Context) {
        sharedPreferences = context.getSharedPreferences(
            PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
    }

    fun setTimeDailyReminder(time: String) {
        editor.putString(DAILY_REMINDER_KEY, time)
        editor.commit()
    }

    fun setMessageDailyReminder(message: String) {
        editor.putString(MESSAGE_DAILY_REMINDER_KEY, message)
    }

    fun setTimeRelaseReminder(time: String) {
        editor.putString(RELEASE_REMINDER_KEY, time)
        editor.commit()
    }

    fun setMessageReleaseReminderKey(message: String) {
        editor.putString(MESSAGE_RELEASE_REMINDER_KEY, message)
    }
}