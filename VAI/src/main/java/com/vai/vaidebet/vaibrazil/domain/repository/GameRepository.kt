package com.vai.vaidebet.vaibrazil.domain.repository

import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getLevels(): Flow<List<GameLevel>>
    suspend fun getLevel(id: Int): GameLevel?
    suspend fun getTotalLevels(): Int
}