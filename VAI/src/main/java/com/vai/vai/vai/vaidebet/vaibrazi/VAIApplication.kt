package com.vai.vai.vai.vaidebet.vaibrazi

import android.app.Application
import com.vai.vai.vai.vaidebet.vaibrazi.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VAIApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VAIApplication)
            modules(appModule)
        }
    }
}
