package com.vai.vaidebet.vaibrazil

import android.app.Application
import com.vai.vaidebet.vaibrazil.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class VAIApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Инициализация Koin
        startKoin {
            androidLogger()
            androidContext(this@VAIApplication)
            modules(appModule)
        }
    }
}