package com.vai.vai.vaidebet.vaibrazil

import android.app.Application
import com.vai.vai.vaidebet.vaibrazil.di.appModule
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
