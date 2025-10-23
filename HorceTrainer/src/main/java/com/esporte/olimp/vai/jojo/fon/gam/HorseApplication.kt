package com.esporte.olimp.vai.jojo.fon.gam

import android.app.Application
import com.esporte.olimp.vai.jojo.fon.gam.di.AppContainer
import com.esporte.olimp.vai.jojo.fon.gam.di.AppContainerImpl

class HorseApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(applicationContext)
    }
}
