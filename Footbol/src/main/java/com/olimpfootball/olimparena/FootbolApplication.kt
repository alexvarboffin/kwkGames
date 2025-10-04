package com.olimpfootball.olimparena

import android.app.Application
import com.olimpfootball.olimparena.data.provideUserPreferencesRepository

class FootbolApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize your singleton repository here
        provideUserPreferencesRepository(applicationContext)
    }
}