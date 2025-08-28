package com.vai.vaidebet.vaibrazil.data.repository

import com.vai.vaidebet.vaibrazil.data.datasource.LocalDataSource
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository

class GameRepositoryImpl(
    private val localDataSource: LocalDataSource
) : GameRepository {
    
    override suspend fun getAllLevels(): List<GameLevel> {
        return localDataSource.getLevels()
    }
    
    override suspend fun getLevelById(id: Int): GameLevel? {
        return localDataSource.getLevels().find { it.id == id }
    }
}