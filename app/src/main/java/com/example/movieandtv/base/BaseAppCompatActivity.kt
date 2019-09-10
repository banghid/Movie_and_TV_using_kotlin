package com.example.movieandtv.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking;

@SuppressLint
class BaseAppCompatActivity : AppCompatActivity() {

    companion object {
        val KEY_FRAGMENT = "fragment"
        val KEY_TITLE = "title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidNetworking.initialize(this)
    }
}