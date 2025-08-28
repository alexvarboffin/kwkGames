package com.vai.vaidebet.vaibrazil.domain.repository

import com.vai.vaidebet.vaibrazil.domain.model.GameLevel

interface GameRepository {
    suspend fun getAllLevels(): List<GameLevel>
    suspend fun getLevelById(id: Int): GameLevel?
}