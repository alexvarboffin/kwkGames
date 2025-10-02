package com.olimpfootball.olimpbet.football

import android.app.Application
import com.olimpfootball.olimpbet.football.data.provideUserPreferencesRepository

class FootbolApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize your singleton repository here
        provideUserPreferencesRepository(applicationContext)
    }
}