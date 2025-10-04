package com.olimpfootball.olimparena.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

private lateinit var userPreferencesRepositoryInstance: UserPreferencesRepository

fun provideUserPreferencesRepository(context: Context): UserPreferencesRepository {
    if (!::userPreferencesRepositoryInstance.isInitialized) {
        userPreferencesRepositoryInstance = UserPreferencesRepository(context.applicationContext)
    }
    return userPreferencesRepositoryInstance
}

class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val IS_MUSIC_ON = booleanPreferencesKey("is_music_on")
    }

    val preferencesFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_MUSIC_ON] ?: true // Default to true
        }

    suspend fun setIsMusicOn(isMusicOn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_MUSIC_ON] = isMusicOn
        }
    }
}