package com.vai.vaidebet.vaibrazil.data.repository

import com.vai.vaidebet.vaibrazil.data.datasource.LocalDataSource
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository

class GameRepositoryImpl(private val localDataSource: LocalDataSource) : GameRepository {
    override suspend fun getLevels(): List<GameLevel> {
        return localDataSource.getLevels()
    }

    override suspend fun getLevel(id: Int): GameLevel? {
        return localDataSource.getLevels().find { it.id == id }
    }
}
