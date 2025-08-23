package com.olimpfootball.olimpbet.footgame

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olimpfootball.olimpbet.footgame.data.provideUserPreferencesRepository

class GameViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(provideUserPreferencesRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}