package com.esporte.olimp.vai.jojo.fon.gam.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val SELECTED_HORSE_ID = longPreferencesKey("selected_horse_id")
    }

    val selectedHorseId: Flow<Long?> = dataStore.data.map {
        it[SELECTED_HORSE_ID]
    }

    suspend fun setSelectedHorseId(horseId: Long) {
        dataStore.edit {
            it[SELECTED_HORSE_ID] = horseId
        }
    }
}
