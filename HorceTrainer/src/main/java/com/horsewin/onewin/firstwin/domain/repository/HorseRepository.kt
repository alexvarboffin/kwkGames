package com.horsewin.onewin.firstwin.domain.repository

import com.horsewin.onewin.firstwin.domain.model.Horse
import kotlinx.coroutines.flow.Flow

interface HorseRepository {
    suspend fun addHorse(horse: Horse): Long
    suspend fun updateHorse(horse: Horse)
    fun getHorseById(horseId: Long): Flow<Horse?>
    fun getAllHorses(): Flow<List<Horse>>
    suspend fun deleteHorseById(horseId: Long)
}
