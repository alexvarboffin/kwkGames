package com.walhalla.sdk.domain.repository

import com.walhalla.sdk.domain.model.GameLevel
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getLevels(): Flow<List<GameLevel>>
    suspend fun getLevel(id: Int): GameLevel?
    suspend fun getTotalLevels(): Int
}