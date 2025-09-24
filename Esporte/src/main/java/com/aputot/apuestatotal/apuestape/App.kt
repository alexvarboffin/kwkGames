package com.aputot.apuestatotal.apuestape

import android.app.Application
import com.aputot.apuestatotal.apuestape.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}
