package com.horsewin.onewin.firstwin

import android.app.Application
import com.horsewin.onewin.firstwin.di.AppContainer
import com.horsewin.onewin.firstwin.di.AppContainerImpl

class HorseApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(applicationContext)
    }
}
