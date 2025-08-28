package com.vai.vaidebet.vaibrazil.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vai.vaidebet.vaibrazil.domain.model.UserProgress
import com.vai.vaidebet.vaibrazil.domain.repository.UserProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_progress")

class UserProgressRepositoryImpl(private val context: Context) : UserProgressRepository {

    private object PreferencesKeys {
        val HIGHEST_UNLOCKED_LEVEL = intPreferencesKey("highest_unlocked_level")
        val STARS = stringPreferencesKey("stars")
    }

    override fun getProgress(): Flow<UserProgress> {
        return context.dataStore.data.map { preferences ->
            val highestUnlockedLevel = preferences[PreferencesKeys.HIGHEST_UNLOCKED_LEVEL] ?: 1
            val starsJson = preferences[PreferencesKeys.STARS] ?: "{}"
            val stars = Json.decodeFromString<Map<String, Int>>(starsJson)
            UserProgress(highestUnlockedLevel, stars)
        }
    }

    override suspend fun updateHighestUnlockedLevel(level: Int) {
        context.dataStore.edit { preferences ->
            val currentHighest = preferences[PreferencesKeys.HIGHEST_UNLOCKED_LEVEL] ?: 1
            if (level > currentHighest) {
                preferences[PreferencesKeys.HIGHEST_UNLOCKED_LEVEL] = level
            }
        }
    }

    override suspend fun updateStars(level: Int, stars: Int) {
        context.dataStore.edit { preferences ->
            val starsJson = preferences[PreferencesKeys.STARS] ?: "{}"
            val currentStars = Json.decodeFromString<MutableMap<String, Int>>(starsJson)
            val existingStars = currentStars[level.toString()] ?: 0
            if (stars > existingStars) {
                currentStars[level.toString()] = stars
                preferences[PreferencesKeys.STARS] = Json.encodeToString(currentStars)
            }
        }
    }
}
