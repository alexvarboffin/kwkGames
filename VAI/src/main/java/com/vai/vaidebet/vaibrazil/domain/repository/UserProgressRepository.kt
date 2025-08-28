package com.vai.vaidebet.vaibrazil.domain.repository

import com.vai.vaidebet.vaibrazil.domain.model.UserProgress
import kotlinx.coroutines.flow.Flow

interface UserProgressRepository {
    fun getProgress(): Flow<UserProgress>
    suspend fun updateHighestUnlockedLevel(level: Int)
    suspend fun updateStars(level: Int, stars: Int)
}
