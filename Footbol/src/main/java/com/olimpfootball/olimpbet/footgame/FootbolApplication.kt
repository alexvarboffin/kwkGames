package com.olimpfootball.olimpbet.footgame

import android.app.Application
import com.olimpfootball.olimpbet.footgame.data.provideUserPreferencesRepository

class FootbolApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize your singleton repository here
        provideUserPreferencesRepository(applicationContext)
    }
}