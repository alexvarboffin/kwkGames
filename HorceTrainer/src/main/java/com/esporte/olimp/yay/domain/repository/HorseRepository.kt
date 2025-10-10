package com.esporte.olimp.yay.domain.repository

import com.esporte.olimp.yay.domain.model.Horse
import kotlinx.coroutines.flow.Flow

interface HorseRepository {
    suspend fun addHorse(horse: Horse): Long
    suspend fun updateHorse(horse: Horse)
    fun getHorseById(horseId: Long): Flow<Horse?>
    fun getAllHorses(): Flow<List<Horse>>
    suspend fun deleteHorseById(horseId: Long)
}
