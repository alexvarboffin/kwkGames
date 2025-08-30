package com.walhalla.sdk.domain.repository

import com.walhalla.sdk.domain.model.UserProgress
import kotlinx.coroutines.flow.Flow

interface UserProgressRepository {
    fun getProgress(): Flow<UserProgress>
    suspend fun updateHighestUnlockedLevel(level: Int)
    suspend fun updateStars(level: Int, stars: Int)
}
