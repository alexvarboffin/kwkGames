package com.obarena.olimpob

import android.app.Application
import com.obarena.olimpob.data.provideUserPreferencesRepository

class FootbolApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize your singleton repository here
        provideUserPreferencesRepository(applicationContext)
    }
}