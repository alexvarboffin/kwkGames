package com.vai.vaidebet.vaibrazil.domain.repository

import com.vai.vaidebet.vaibrazil.domain.model.GameLevel

interface GameRepository {
    suspend fun getLevels(): List<GameLevel>
    suspend fun getLevel(id: Int): GameLevel?
}
