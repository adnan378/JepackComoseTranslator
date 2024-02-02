package com.hypersoft.translator

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d("testing", "created")
    }

    fun getContext() : Context{
        return this
    }
}