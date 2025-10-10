package com.esporte.olimp.yay

import android.app.Application
import com.esporte.olimp.yay.di.AppContainer
import com.esporte.olimp.yay.di.AppContainerImpl

class HorseApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(applicationContext)
    }
}
