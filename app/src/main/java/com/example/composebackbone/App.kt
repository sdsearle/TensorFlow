package com.example.tensorflow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Spencer Searle, github: sdsearle on 9/16/2023.
 */
@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}